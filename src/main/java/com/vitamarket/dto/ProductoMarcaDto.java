package com.vitamarket.dto;

import javax.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductoMarcaDto {

	@NotBlank
	private String nombre;
	
}
