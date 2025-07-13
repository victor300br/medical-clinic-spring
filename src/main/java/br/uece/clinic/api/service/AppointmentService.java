package br.uece.clinic.api.service;

import br.uece.clinic.api.exceptions.ConflictException;
import br.uece.clinic.api.exceptions.NotFoundException;
import br.uece.clinic.api.model.*;
import br.uece.clinic.api.repository.AppointmentRepository;
import br.uece.clinic.api.repository.DoctorRepository;
import br.uece.clinic.api.repository.PatientRepository;
import br.uece.clinic.api.request.dto.AvailableTimesRequestDTO;
import br.uece.clinic.api.request.dto.AppointmentRequestDTO;
import br.uece.clinic.api.response.dto.AppointmentResponseDTO;
import br.uece.clinic.api.response.dto.AvailableTimesResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private static final LocalTime[] AVAILABLE_TIMES = {
            LocalTime.of(9, 0),
            LocalTime.of(12, 0),
            LocalTime.of(15, 0)
    };

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public AvailableTimesResponseDTO getAvailableTimes(AvailableTimesRequestDTO request) {
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new NotFoundException(Doctor.class, "id", request.getDoctorId().toString()));
        
        List<LocalTime> availableTimes = new ArrayList<>();
        
        for (LocalTime time : AVAILABLE_TIMES) {
            boolean isTimeAvailable = !appointmentRepository.existsByDoctorAndDateAndTimeAndStatusIn(
                    doctor, 
                    request.getDate(), 
                    time,
                    List.of(Appointment.AppointmentStatus.SCHEDULED, Appointment.AppointmentStatus.WAITING_LIST));
            
            if (isTimeAvailable) {
                availableTimes.add(time);
            }
        }
        
        return new AvailableTimesResponseDTO(availableTimes);
    }

    @Transactional
    public AppointmentResponseDTO scheduleAppointment(AppointmentRequestDTO request) throws ConflictException {
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new NotFoundException(Doctor.class, "id", request.getDoctorId().toString()));
        
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new NotFoundException(Patient.class, "id", request.getPatientId().toString()));

        boolean hasExistingAppointment = appointmentRepository.existsByDoctorAndPatientAndDateAndStatusNot(
                doctor, patient, request.getDate(), Appointment.AppointmentStatus.CANCELLED);
        
        if (hasExistingAppointment) {
            throw new ConflictException("There is already an appointment for this patient with this doctor on the selected date");
        }

        if (request.getTime() != null) {
            return scheduleAtSpecificTime(doctor, patient, request);
        } else {
            return scheduleAtAnyAvailableTime(doctor, patient, request);
        }
    }

    private AppointmentResponseDTO scheduleAtSpecificTime(Doctor doctor, Patient patient, AppointmentRequestDTO request) throws ConflictException {
        boolean isTimeTaken = appointmentRepository.existsByDoctorAndDateAndTimeAndStatusIn(
                doctor, 
                request.getDate(), 
                request.getTime(),
                List.of(Appointment.AppointmentStatus.SCHEDULED, Appointment.AppointmentStatus.WAITING_LIST));
        
        if (!isTimeTaken) {
            Appointment appointment = createAppointment(doctor, patient, request.getDate(), request.getTime(), false);
            return new AppointmentResponseDTO(appointmentRepository.save(appointment));
        } else if (request.isAcceptWaitingList()) {
            Appointment appointment = createAppointment(doctor, patient, request.getDate(), null, true);
            return new AppointmentResponseDTO(appointmentRepository.save(appointment));
        } else {
            throw new ConflictException("Horário já ocupado");
        }
    }

    private AppointmentResponseDTO scheduleAtAnyAvailableTime(Doctor doctor, Patient patient, AppointmentRequestDTO request) throws ConflictException{
        AvailableTimesResponseDTO availableTimes = getAvailableTimes(
                new AvailableTimesRequestDTO(request.getDoctorId(), request.getDate()));
        
        if (!availableTimes.getAvailableTimes().isEmpty()) {
            Appointment appointment = createAppointment(
                    doctor, patient, request.getDate(), availableTimes.getAvailableTimes().get(0), false);
            return new AppointmentResponseDTO(appointmentRepository.save(appointment));
        } else if (request.isAcceptWaitingList()) {
            Appointment appointment = createAppointment(doctor, patient, request.getDate(), null, true);
            return new AppointmentResponseDTO(appointmentRepository.save(appointment));
        } else {
            throw new ConflictException("There are no available appointments for this doctor in the selected data");
        }
    }

    private Appointment createAppointment(Doctor doctor, Patient patient, LocalDate date, LocalTime time, boolean isWaitingList) {
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setStatus(isWaitingList ? 
                Appointment.AppointmentStatus.WAITING_LIST : 
                Appointment.AppointmentStatus.SCHEDULED);
        
        return appointment;
    }

    public List<AppointmentResponseDTO> getPatientAppointments(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new NotFoundException(Patient.class, "id", patientId.toString()));
        
        List<Appointment> appointments = appointmentRepository.findByPatient(patient);
        
        appointments.forEach(this::updateAppointmentStatusIfPassed);
        
        return appointments.stream()
                .map(AppointmentResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    private void updateAppointmentStatusIfPassed(Appointment appointment) {
        if (appointment.getStatus() == Appointment.AppointmentStatus.SCHEDULED && 
            isAppointmentTimePassed(appointment)) {
            
            appointment.setStatus(Appointment.AppointmentStatus.COMPLETED);
            appointmentRepository.save(appointment);
        }
    }

    private boolean isAppointmentTimePassed(Appointment appointment) {
        LocalDateTime appointmentDateTime = LocalDateTime.of(appointment.getDate(), appointment.getTime());
        return LocalDateTime.now().isAfter(appointmentDateTime);
    }

    @Transactional
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Appointment.class, "id", id.toString()));
        
        if (appointment.getStatus() != Appointment.AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Somente agendamentos marcados podem ser cancelados");
        }

        appointment.setStatus(Appointment.AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);

        List<Appointment> waitingList = appointmentRepository
                .findByDoctorAndDateAndStatus(appointment.getDoctor(), appointment.getDate(), 
                        Appointment.AppointmentStatus.WAITING_LIST);
        
        if (!waitingList.isEmpty()) {
            waitingList.sort(Comparator.comparing(Appointment::getId));
            Appointment nextAppointment = waitingList.get(0);
            
            nextAppointment.setTime(appointment.getTime());
            nextAppointment.setStatus(Appointment.AppointmentStatus.SCHEDULED);
            appointmentRepository.save(nextAppointment);
        }
    }
}