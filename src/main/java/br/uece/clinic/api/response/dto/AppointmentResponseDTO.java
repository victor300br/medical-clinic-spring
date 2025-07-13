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
    private LocalDate date;
    private LocalTime time;
    private String status;
    private boolean isWaitingList;

    public AppointmentResponseDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.doctorId = appointment.getDoctor().getId();
        this.doctorName = appointment.getDoctor().getName();
        this.date = appointment.getDate();
        this.time = appointment.getTime();
        this.status = appointment.getStatus().toString();
        this.isWaitingList = appointment.getStatus() == Appointment.AppointmentStatus.WAITING_LIST;
    }
}