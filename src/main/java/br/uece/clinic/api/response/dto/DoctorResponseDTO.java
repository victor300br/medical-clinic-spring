package br.uece.clinic.api.response.dto;

import java.util.List;
import java.util.stream.Collectors;

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
    private Double averageRating;
    private List<ReviewResponseDTO> lastReviews;
	

	public DoctorResponseDTO(Doctor doctor) {
		this.id = doctor.getId();
		this.name = doctor.getName();
		this.email = doctor.getEmail();
		this.healthPlan = doctor.getHealthPlan();
		this.speciality = doctor.getSpeciality();
		this.averageRating = doctor.getAverageRating();
        this.lastReviews = doctor.getReviews().stream()
                .sorted((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()))
                .limit(3) //eu coloquei 3 porque o professor nao especificou no documento a quantidade de review que deveria ter
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());

	}

}
