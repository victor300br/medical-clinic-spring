package br.uece.clinic.api.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentRequestDTO {
    private Long doctorId;
    private Long patientId;
    private LocalDate date;
    
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time; 
    
    private boolean acceptWaitingList;
}