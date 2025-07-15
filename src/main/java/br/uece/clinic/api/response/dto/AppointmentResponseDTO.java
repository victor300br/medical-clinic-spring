package br.uece.clinic.api.response.dto;

import br.uece.clinic.api.model.Appointment;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class AppointmentResponseDTO {
    private Long id;
    private Long doctorId;
    private String doctorName;
    
    // Novos campos do paciente
    private Long patientId;
    private String patientName;
    
    private LocalDate date;
    private LocalTime time;
    private String status;
    private boolean isWaitingList;
    private String consultationNotes;
    private Double consultationFee;
    private boolean requiresPayment;
    
    public AppointmentResponseDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.doctorId = appointment.getDoctor().getId();
        this.doctorName = appointment.getDoctor().getName();
        
        // Preenche dados do paciente
        if (appointment.getPatient() != null) {
            this.patientId = appointment.getPatient().getId();
            this.patientName = appointment.getPatient().getName();
        }
        
        this.date = appointment.getDate();
        this.time = appointment.getTime();
        this.status = appointment.getStatus().toString();
        this.isWaitingList = appointment.getStatus() == Appointment.AppointmentStatus.WAITING_LIST;
        this.consultationNotes = appointment.getConsultationNotes();
        this.consultationFee = appointment.getConsultationFee();
        this.requiresPayment = appointment.getConsultationFee() != null;
    }
}
