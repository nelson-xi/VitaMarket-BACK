package com.vitamarket.security.dto;

import javax.validation.constraints.*;
import lombok.*;

@Getter @Setter
public class LoginDto {
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
}
