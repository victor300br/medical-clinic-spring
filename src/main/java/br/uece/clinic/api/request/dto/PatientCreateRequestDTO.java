package br.uece.clinic.api.request.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(includeFieldNames = true, of = { "email" })
public class PatientCreateRequestDTO {
	
	@NotNull
	@Size(max = 255)
	@Pattern(regexp = "/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/")
    @Schema(type="string", example = "patient@gmail.com")
	private String email;


	@NotNull
	@Size(max = 255)
	private String name;
	
	@Size(max = 255)
	private Date dateOfBirth;

	@NotNull
	@Size(max = 255)
	private String healthPlan;

	@NotNull
	@Size(max = 255)
	private String password;

}
