package com.vitamarket.dto;

import javax.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductoCategoriaDto {
	
	@NotBlank
	private String nombre;
	
}
