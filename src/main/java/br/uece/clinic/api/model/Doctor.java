package br.uece.clinic.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.uece.clinic.api.request.dto.DoctorCreateRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
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
	private String speciality;
	
	@Column(nullable = false)
	private String healthPlan;
	
    @Column(nullable = false, columnDefinition = "double precision default 0.0")
    private Double averageRating = 0.0;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

	
	public Doctor(DoctorCreateRequestDTO DoctorRequestDTO) {
		this.email = DoctorRequestDTO.getEmail();
		this.name = DoctorRequestDTO.getName();
		this.password = DoctorRequestDTO.getPassword();
		this.speciality = DoctorRequestDTO.getSpeciality();
		this.healthPlan = DoctorRequestDTO.getHealthPlan();
	}





}
