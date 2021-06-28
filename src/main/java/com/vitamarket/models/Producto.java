package com.vitamarket.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="producto")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false, length = 500)
	private String descripcion;
	
	@Column(nullable = false)
	private Double precio;
	
	@Column(nullable = false)
	private Integer stock;
	
	@Column(nullable = false, name="imagen_url")
	private String imgUrl;

	@Column(nullable = false)
	private String marca;
	
	@Column(nullable = false)
	private String categoria;
	
	@CreationTimestamp
	@Column(name="create_date_time", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createDateTime;
	
	@UpdateTimestamp
	@Column(name="update_date_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date updateDateTime;

}

