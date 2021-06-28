package com.vitamarket.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PedidoDto {

	@NotBlank
	private String cliente;
	
	@NotBlank
	private String direccion;
	
	@Positive
	private Long idProducto;
	
	@PositiveOrZero
	private Integer cantidad;

	private Double total;
	
	private String Estado;
	
}
