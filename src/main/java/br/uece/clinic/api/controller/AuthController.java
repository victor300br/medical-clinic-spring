package br.uece.clinic.api.controller;

import br.uece.clinic.api.model.Doctor;
import br.uece.clinic.api.request.dto.DoctorLoginRequestDTO;
import br.uece.clinic.api.response.dto.DoctorLoginResponseDTO;
import br.uece.clinic.api.service.DoctorService;
import br.uece.clinic.api.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final DoctorService doctorService;
    private final JwtService jwtService;

    public AuthController(DoctorService doctorService, JwtService jwtService) {
        this.doctorService = doctorService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Login do médico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<DoctorLoginResponseDTO> login(@RequestBody DoctorLoginRequestDTO loginRequest) {
        Doctor doctor = doctorService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        
        UserDetails userDetails = User.builder()
                .username(doctor.getEmail())
                .password(doctor.getPassword())
                .roles("DOCTOR")
                .build();
        
        String token = jwtService.generateToken(userDetails, doctor.getId());
        
        return ResponseEntity.ok(new DoctorLoginResponseDTO(doctor.getId(), token));
    }
}
