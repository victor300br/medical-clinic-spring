package br.uece.clinic.api.request.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorLoginRequestDTO {
    private String email;
    private String password;
}