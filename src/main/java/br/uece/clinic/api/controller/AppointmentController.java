package br.uece.clinic.api.controller;

import br.uece.clinic.api.request.dto.AvailableTimesRequestDTO;
import br.uece.clinic.api.request.dto.CompleteAppointmentRequestDTO;
import br.uece.clinic.api.request.dto.AppointmentRequestDTO;
import br.uece.clinic.api.response.dto.AppointmentResponseDTO;
import br.uece.clinic.api.response.dto.AvailableTimesResponseDTO;
import br.uece.clinic.api.service.AppointmentService;
import br.uece.clinic.api.exceptions.ConflictException;
import br.uece.clinic.api.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Operation(summary = "Get available appointment times")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Available times retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/available-times")
    public ResponseEntity<AvailableTimesResponseDTO> getAvailableTimes(@RequestBody AvailableTimesRequestDTO request) {
        AvailableTimesResponseDTO response = appointmentService.getAvailableTimes(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Schedule an appointment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Appointment scheduled"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "409", description = "Appointment conflict")
    })
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> scheduleAppointment(@RequestBody AppointmentRequestDTO request)
            throws ConflictException {
        AppointmentResponseDTO response = appointmentService.scheduleAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get appointments by patient ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Appointments retrieved"),
        @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getPatientAppointments(@PathVariable Long patientId)
            throws NotFoundException {
        List<AppointmentResponseDTO> appointments = appointmentService.getPatientAppointments(patientId);
        return ResponseEntity.ok(appointments);
    }

    @Operation(summary = "Cancel appointment by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Appointment cancelled"),
        @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) throws NotFoundException {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Complete an appointment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Appointment completed"),
        @ApiResponse(responseCode = "404", description = "Appointment not found"),
        @ApiResponse(responseCode = "400", description = "Appointment cannot be completed")
    })
    @PutMapping("/{id}/complete")
    public ResponseEntity<AppointmentResponseDTO> completeAppointment(
            @PathVariable Long id,
            @RequestBody CompleteAppointmentRequestDTO completeDTO) {
        
        AppointmentResponseDTO response = appointmentService.completeAppointment(id, completeDTO);
        return ResponseEntity.ok(response);
    }
}
