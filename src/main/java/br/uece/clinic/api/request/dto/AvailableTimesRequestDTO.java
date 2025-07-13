package br.uece.clinic.api.request.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class AvailableTimesRequestDTO {
    private Long doctorId;
    private LocalDate date;
    
    
    public AvailableTimesRequestDTO(Long doctorId, LocalDate date) {
        this.doctorId = doctorId;
        this.date = date;
    }
}