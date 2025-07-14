package br.uece.clinic.api.service;

import br.uece.clinic.api.exceptions.NotFoundException;
import br.uece.clinic.api.model.*;
import br.uece.clinic.api.repository.AppointmentRepository;
import br.uece.clinic.api.repository.ReviewRepository;
import br.uece.clinic.api.request.dto.ReviewRequestDTO;
import br.uece.clinic.api.response.dto.ReviewResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorService doctorService;

    @Transactional
    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequest) {
        Appointment appointment = appointmentRepository.findById(reviewRequest.getAppointmentId())
                .orElseThrow(() -> new NotFoundException(Appointment.class, "id", reviewRequest.getAppointmentId().toString()));
        
        if (appointment.getStatus() != Appointment.AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Only completed appointments can be reviewed");
        }

        if (reviewRepository.existsByAppointment(appointment)) {
            throw new IllegalStateException("This appointment already has a review");
        }

        Review review = new Review();
        review.setDoctor(appointment.getDoctor());
        review.setPatient(appointment.getPatient());
        review.setAppointment(appointment);
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());

        Review savedReview = reviewRepository.save(review);
        
        doctorService.updateDoctorRating(appointment.getDoctor().getId());
        
        return new ReviewResponseDTO(savedReview);
    }

    public List<ReviewResponseDTO> getDoctorReviews(Long doctorId) {
        List<Review> reviews = reviewRepository.findByDoctorIdOrderByCreatedAtDesc(doctorId);
        return reviews.stream()
                .map(ReviewResponseDTO::new)
                .collect(Collectors.toList());
    }
}