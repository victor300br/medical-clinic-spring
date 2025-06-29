package br.uece.clinic.api.response.dto;

import br.uece.clinic.api.model.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DoctorResponseDTO {
	private Long id;
	private String name;
	private String email;
	private String healthPlan;
	private String speciality;
	

	public DoctorResponseDTO(Doctor doctor) {
		this.id = doctor.getId();
		this.name = doctor.getName();
		this.email = doctor.getEmail();
		this.healthPlan = doctor.getHealthPlan();
		this.speciality = doctor.getSpeciality();

	}

}
