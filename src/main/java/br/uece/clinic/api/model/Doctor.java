package br.uece.clinic.api.model;

import java.io.Serializable;


import br.uece.clinic.api.request.dto.DoctorCreateRequestDTO;
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
@Table(name = "doctor", schema = "public")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public class Doctor implements Serializable {
	
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
	private String speciality;
	
	@Column(nullable = false)
	private String healthPlan;
	
	public Doctor(DoctorCreateRequestDTO DoctorRequestDTO) {
		this.email = DoctorRequestDTO.getEmail();
		this.name = DoctorRequestDTO.getName();
		this.password = DoctorRequestDTO.getPassword();
		this.speciality = DoctorRequestDTO.getSpeciality();
		this.healthPlan = DoctorRequestDTO.getHealthPlan();
	}





}
