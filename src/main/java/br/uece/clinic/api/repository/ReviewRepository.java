package br.uece.clinic.api.repository;

import br.uece.clinic.api.model.Appointment;
import br.uece.clinic.api.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByAppointment(Appointment appointment);
    
    List<Review> findByDoctorIdOrderByCreatedAtDesc(Long doctorId);
    
    List<Review> findTop3ByDoctorIdOrderByCreatedAtDesc(Long doctorId);
}