package com.vitamarket.models;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="producto_categoria")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductoCategoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nombre;
	
}
