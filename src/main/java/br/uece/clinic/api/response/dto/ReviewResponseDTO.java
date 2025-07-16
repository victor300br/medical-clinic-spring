package br.uece.clinic.api.response.dto;

import br.uece.clinic.api.model.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDTO {
    private Long id;
    private Long doctorId;
    private String doctorName;
    private Long patientId;
    private String patientName;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    public ReviewResponseDTO(Review review) {
        this.id = review.getId();
        this.doctorId = review.getDoctor().getId();
        this.doctorName = review.getDoctor().getName();
        this.patientId = review.getPatient().getId();
        this.patientName = review.getPatient().getName();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
    }
}