package br.uece.clinic.api.repository;

import br.uece.clinic.api.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
	Doctor findByEmail(String email);

}
