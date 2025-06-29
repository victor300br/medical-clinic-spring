package br.uece.clinic.api.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.uece.clinic.api.exceptions.ConflictException;
import br.uece.clinic.api.request.dto.DoctorCreateRequestDTO;
import br.uece.clinic.api.response.dto.DoctorResponseDTO;
import br.uece.clinic.api.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
		
	@Autowired
	DoctorService doctorService;

	@Operation(summary = "Add doctor")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "doctor created"),
							@ApiResponse(responseCode = "400"),
							@ApiResponse(responseCode = "409", description = "doctor already created"), })
	@PostMapping
	public ResponseEntity<Void> add(@RequestBody DoctorCreateRequestDTO DoctorRequestDTO)
			throws ConflictException, NoSuchAlgorithmException, UnsupportedEncodingException {
		doctorService.add(DoctorRequestDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
	
	@Operation(summary = "Update doctor")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Doctor updated"),
	    @ApiResponse(responseCode = "400"),
	    @ApiResponse(responseCode = "404", description = "Doctor not found")
	})
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody DoctorCreateRequestDTO doctorRequestDTO)
	        throws ConflictException {
	    doctorService.update(id, doctorRequestDTO);
	    return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "List all doctors")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "List of doctors")
	})
	@GetMapping
	public ResponseEntity<List<DoctorResponseDTO>> listAll() {
	    List<DoctorResponseDTO> doctors = doctorService.listAll();
	    return ResponseEntity.ok(doctors);
	}

	@Operation(summary = "Get doctor by ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Doctor found"),
	    @ApiResponse(responseCode = "404", description = "Doctor not found")
	})
	@GetMapping("/{id}")
	public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable Long id) {
	    DoctorResponseDTO doctor = doctorService.get(id);
	    return ResponseEntity.ok(doctor);
	}

	@Operation(summary = "Delete doctor by ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "204", description = "Doctor deleted"),
	    @ApiResponse(responseCode = "404", description = "Doctor not found")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDoctorById(@PathVariable Long id) {
	    doctorService.delete(id);
	    return ResponseEntity.noContent().build();
	}

}
