package com.vitamarket.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.*;

@Entity
@Table(name="pedido")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String cliente;
	
	@Column(nullable = false)
	private String direccion;
	
	@Column(name = "id_producto", nullable = false)
	private Long idProducto;
	
	@Column(nullable = false)
	private Integer cantidad;
	
	@Column(nullable = false)
	private Double total;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date fecha;
	
	@Column(nullable = false)
	private String Estado;
	
}
