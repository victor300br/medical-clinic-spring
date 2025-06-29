package br.uece.clinic.api.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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
}
