package br.uece.clinic.api.controller;

import br.uece.clinic.api.request.dto.ReviewRequestDTO;
import br.uece.clinic.api.response.dto.ReviewResponseDTO;
import br.uece.clinic.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody ReviewRequestDTO reviewRequest) {
        ReviewResponseDTO response = reviewService.createReview(reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ReviewResponseDTO>> getDoctorReviews(@PathVariable Long doctorId) {
        List<ReviewResponseDTO> reviews = reviewService.getDoctorReviews(doctorId);
        return ResponseEntity.ok(reviews);
    }
}