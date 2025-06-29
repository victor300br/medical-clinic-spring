package br.uece.clinic.api.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
