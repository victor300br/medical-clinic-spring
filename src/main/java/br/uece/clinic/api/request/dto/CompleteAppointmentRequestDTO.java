package br.uece.clinic.api.request.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompleteAppointmentRequestDTO {
    private String consultationNotes;
    private Double consultationFee;
}