package br.uece.clinic.api.repository;

import br.uece.clinic.api.model.Appointment;
import br.uece.clinic.api.model.Doctor;
import br.uece.clinic.api.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(Patient patient);
    
    boolean existsByDoctorAndPatientAndDateAndStatusNot(
            Doctor doctor, Patient patient, LocalDate date, Appointment.AppointmentStatus status);
    
    long countByDoctorAndDateAndTimeAndStatus(
            Doctor doctor, LocalDate date, LocalTime time, Appointment.AppointmentStatus status);
    
    List<Appointment> findByDoctorAndDateAndStatus(
            Doctor doctor, LocalDate date, Appointment.AppointmentStatus status);
    
    boolean existsByDoctorAndDateAndTimeAndStatusIn(
    		Doctor doctor, LocalDate date, LocalTime time, List<Appointment.AppointmentStatus> status);
}