package br.uece.clinic.api.response.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PatientLoginResponseDTO {
	private Long id;
	private String token;
}
