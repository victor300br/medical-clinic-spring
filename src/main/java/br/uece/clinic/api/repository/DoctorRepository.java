package br.uece.clinic.api.repository;

import br.uece.clinic.api.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
	Doctor findByEmail(String email);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.doctor.id = :doctorId")
    Double calculateAverageRatingByDoctor(@Param("doctorId") Long doctorId);
}
