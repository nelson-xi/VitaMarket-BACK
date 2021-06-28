package com.vitamarket.dto;

import javax.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductoDto {

	@NotBlank
	private String nombre;
	
	@NotBlank
	private String descripcion;
	
	@PositiveOrZero
	private Double precio;
	
	@PositiveOrZero
	private Integer stock;
	
	@NotBlank
	private String imgUrl;
	
	@NotBlank
	private String marca;
	
	@NotBlank
	private String categoria;
	
}
