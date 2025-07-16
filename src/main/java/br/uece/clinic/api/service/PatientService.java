package br.uece.clinic.api.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import br.uece.clinic.api.exceptions.NotFoundException;
import br.uece.clinic.api.response.dto.PatientResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.uece.clinic.api.exceptions.ConflictException;
import br.uece.clinic.api.model.Patient;
import br.uece.clinic.api.repository.PatientRepository;
import br.uece.clinic.api.request.dto.PatientCreateRequestDTO;

@Service
public class PatientService {
	
	@Autowired
	PatientRepository patientRepository;


	public void add(PatientCreateRequestDTO patientRequest)
			throws ConflictException, NoSuchAlgorithmException, UnsupportedEncodingException {

		if (patientRepository.findByEmail(patientRequest.getEmail()) != null)
			throw new ConflictException(patientRequest);

		patientRequest.setPassword(patientRequest.getPassword());
		Patient newPatient = new Patient(patientRequest);

		patientRepository.save(newPatient);
	}
	
	public void update(Long id, PatientCreateRequestDTO dto){
		Patient patient = patientRepository.findById(id).orElseThrow(() -> new NotFoundException(Patient.class, "id", id.toString()));
		
		patient.setName(dto.getName());
		patient.setEmail(dto.getEmail());
		patient.setPassword(dto.getPassword());
		patient.setDateOfBirth(dto.getDateOfBirth());
		patient.setHealthPlan(dto.getHealthPlan());
		
		patientRepository.save(patient);
		
	}
	
	public List<PatientResponseDTO> listAll(){
		return patientRepository.findAll().stream()
				.map(PatientResponseDTO::new)
				.collect(Collectors.toList());
	}
	
	public PatientResponseDTO get(Long id){
		Patient patient = patientRepository.findById(id).orElseThrow(() -> new NotFoundException(Patient.class, "id", id.toString()));
		return new PatientResponseDTO(patient);
	}
	
	public void delete(Long id){
		if(!patientRepository.existsById(id)){
			throw new NotFoundException(Patient.class, "id", id.toString());
		}
		patientRepository.deleteById(id);
	}
	
	public Patient authenticate(String email, String password) {
		Patient patient = patientRepository.findByEmail(email);
		if(patient == null || !patient.getPassword().equals(password)){
			throw new NotFoundException(Patient.class, "email", email.toString());
		}
		return patient;
	}
}
