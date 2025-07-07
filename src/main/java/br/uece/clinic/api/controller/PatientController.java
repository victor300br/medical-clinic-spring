package br.uece.clinic.api.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import br.uece.clinic.api.response.dto.DoctorResponseDTO;
import br.uece.clinic.api.response.dto.PatientResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.uece.clinic.api.exceptions.ConflictException;
import br.uece.clinic.api.request.dto.PatientCreateRequestDTO;
import br.uece.clinic.api.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
	@Autowired
	PatientService patientService;

	@Operation(summary = "Add patient")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "patient created"),
							@ApiResponse(responseCode = "400"),
							@ApiResponse(responseCode = "409", description = "patient already created"), })
	@PostMapping
	public ResponseEntity<Void> add(@RequestBody PatientCreateRequestDTO PatientRequestDTO)
			throws ConflictException, NoSuchAlgorithmException, UnsupportedEncodingException {
		patientService.add(PatientRequestDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
	
	@Operation(summary = "Update Patient")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Patient updated"),
			@ApiResponse(responseCode = "400"),
			@ApiResponse(responseCode = "404", description = "Patient not found")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id,
									   @RequestBody PatientCreateRequestDTO patientRequestDTO) throws ConflictException {
		patientService.update(id, patientRequestDTO);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "List all patients")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of patients")
	})
	@GetMapping
	public ResponseEntity<List<PatientResponseDTO>> listAll() {
		List<PatientResponseDTO> patients = patientService.listAll();
		return ResponseEntity.ok(patients);
	}
	
	@Operation(summary = "Get patient by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Patient found"),
			@ApiResponse(responseCode = "404", description = "Patient not found")
	})
	@GetMapping("/{id}")
	public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id) {
		PatientResponseDTO patient = patientService.get(id);
		return ResponseEntity.ok(patient);
	}
	
	@Operation(summary = "Delete patient by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "patient deleted"),
			@ApiResponse(responseCode = "404", description = "patient not found")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePatientById(@PathVariable Long id) {
		patientService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
