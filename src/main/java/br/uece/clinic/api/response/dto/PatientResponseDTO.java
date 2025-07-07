package br.uece.clinic.api.response.dto;

import br.uece.clinic.api.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PatientResponseDTO {
	private Long id;
	private String name;
	private String email;
	private String healthPlan;
	private String dateOfBirth;
	
	public PatientResponseDTO(Patient patient) {
		this.id = patient.getId();
		this.name = patient.getName();
		this.email = patient.getEmail();
		this.healthPlan = patient.getHealthPlan();
		this.dateOfBirth = patient.getDateOfBirth().toString();
	}
}
