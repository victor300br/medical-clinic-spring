package br.uece.clinic.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uece.clinic.api.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{
	Patient findByEmail(String email);

}
