package br.uece.clinic.api.model;

import java.util.Date;

import br.uece.clinic.api.request.dto.DoctorCreateRequestDTO;
import br.uece.clinic.api.request.dto.PatientCreateRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Patient", schema = "public")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public class Patient {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private Date dateOfBirth;
	
	@Column(nullable = true)
	private String healthPlan;
	
	public Patient(PatientCreateRequestDTO PatientRequestDTO) {
		this.email = PatientRequestDTO.getEmail();
		this.name = PatientRequestDTO.getName();
		this.password = PatientRequestDTO.getPassword();
		this.dateOfBirth = PatientRequestDTO.getDateOfBirth();
		this.healthPlan = PatientRequestDTO.getHealthPlan();
	}

}
