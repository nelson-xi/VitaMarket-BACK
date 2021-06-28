package com.vitamarket.security.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import lombok.*;

@Getter @Setter
public class RegistroDto {
	
	@NotBlank
	private String nombres;
	
	@NotBlank
	private String apellidos;

	@Email
	private String email;
	
	@NotBlank
	private String celular;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	private Set<String> roles = new HashSet<>();
	
}
