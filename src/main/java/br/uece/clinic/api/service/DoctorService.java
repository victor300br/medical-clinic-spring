package br.uece.clinic.api.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.uece.clinic.api.exceptions.ConflictException;
import br.uece.clinic.api.exceptions.NotFoundException;
import br.uece.clinic.api.model.Doctor;
import br.uece.clinic.api.model.Review;
import br.uece.clinic.api.repository.DoctorRepository;
import br.uece.clinic.api.repository.ReviewRepository;
import br.uece.clinic.api.request.dto.DoctorCreateRequestDTO;
import br.uece.clinic.api.response.dto.DoctorResponseDTO;

@Service
public class DoctorService {
	
	@Autowired
	DoctorRepository doctorRepository;
	
	ReviewRepository reviewRepository;

	
	public void add(DoctorCreateRequestDTO doctorRequest)
			throws ConflictException, NoSuchAlgorithmException, UnsupportedEncodingException {

		if (doctorRepository.findByEmail(doctorRequest.getEmail()) != null)
			throw new ConflictException(doctorRequest);

		doctorRequest.setPassword(doctorRequest.getPassword());
		Doctor newDoctor = new Doctor(doctorRequest);

		doctorRepository.save(newDoctor);
	}
	
	public void update(Long id, DoctorCreateRequestDTO dto) {
	    Doctor doctor = doctorRepository.findById(id)
	    	.orElseThrow(() -> new NotFoundException(Doctor.class, "id", id.toString()));

	    doctor.setName(dto.getName());
	    doctor.setSpeciality(dto.getSpeciality());
	    doctor.setHealthPlan(dto.getHealthPlan());
	    doctor.setEmail(dto.getEmail());
	    doctor.setPassword(dto.getPassword());

	    doctorRepository.save(doctor);
	}
	
	public List<DoctorResponseDTO> listAll() {
	    return doctorRepository.findAll().stream()
	            .map(DoctorResponseDTO::new) 
	            .collect(Collectors.toList());
	}
	
	public DoctorResponseDTO get(Long id) {
	    Doctor doctor = doctorRepository.findById(id)
	        .orElseThrow(() -> new NotFoundException(Doctor.class, "id", id.toString()));
	    return new DoctorResponseDTO(doctor);
	}

	public void delete(Long id) {
	    if (!doctorRepository.existsById(id)) {
	        throw new NotFoundException(Doctor.class, "id", id.toString());
	    }
	    doctorRepository.deleteById(id);
	}
	
	public Doctor authenticate(String email, String password) {
	    Doctor doctor = doctorRepository.findByEmail(email);
	    if (doctor == null || !doctor.getPassword().equals(password)) {
	        throw new NotFoundException(Doctor.class, "email", email.toString());
	    }
	    return doctor;
	}
	
	public void updateDoctorRating(Long doctorId) {
	    Doctor doctor = doctorRepository.findById(doctorId)
	            .orElseThrow(() -> new NotFoundException(Doctor.class, "id", doctorId.toString()));
	    
	    Double averageRating = doctorRepository.calculateAverageRatingByDoctor(doctorId);
	    doctor.setAverageRating(averageRating != null ? averageRating : 0.0);
	    
	    doctorRepository.save(doctor);
	}

	public List<Review> getLastThreeReviews(Long doctorId) {
	    return reviewRepository.findTop3ByDoctorIdOrderByCreatedAtDesc(doctorId);
	}
}
