package com.vitamarket.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitamarket.dto.Mensaje;
import com.vitamarket.dto.ProductoMarcaDto;
import com.vitamarket.models.ProductoMarca;
import com.vitamarket.services.ProductoMarcaService;

@RestController
@RequestMapping("api/productos/marcas")
@CrossOrigin(origins = "*")
public class ProductoMarcaController {
	
	@Autowired
	ProductoMarcaService marcaService;
	
	@GetMapping("")
	public ResponseEntity<List<ProductoMarca>> list() {
		List<ProductoMarca> list = marcaService.list();
		return new ResponseEntity<List<ProductoMarca>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {
		if (!marcaService.existsById(id)) {
			return new ResponseEntity<Mensaje>(new Mensaje("Registro no encontrado"), HttpStatus.NOT_FOUND);
		}
		ProductoMarca marca = marcaService.getOne(id).get();
		return new ResponseEntity<ProductoMarca>(marca, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("")
	public ResponseEntity<?> create(@Valid @RequestBody ProductoMarcaDto dto, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<Mensaje>(new Mensaje("Campos no válidos"), HttpStatus.BAD_REQUEST);
		}
		ProductoMarca marca = new ProductoMarca();
		marca.setNombre(dto.getNombre());
		marcaService.save(marca);
		return new ResponseEntity<Mensaje>(new Mensaje("Registro guardado"), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ProductoMarca nuevaMarca, BindingResult result){
		if(!marcaService.existsById(id)) {
            return new ResponseEntity<Mensaje>(new Mensaje("Registro no encontrado"), HttpStatus.NOT_FOUND);
        }
		if(result.hasErrors()) {
			return new ResponseEntity<Mensaje>(new Mensaje("Campos no válidos"), HttpStatus.BAD_REQUEST);
		}
		ProductoMarca marca = marcaService.getOne(id).get();
		marca.setNombre(nuevaMarca.getNombre());
		marcaService.save(marca);
        return new ResponseEntity<Mensaje>(new Mensaje("Registro guardado"), HttpStatus.OK);
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
        if(!marcaService.existsById(id)) {
        	return new ResponseEntity<Mensaje>(new Mensaje("Registro no encontrado"), HttpStatus.NOT_FOUND);
        }
        marcaService.delete(id);
        return new ResponseEntity<Mensaje>(new Mensaje("Registro eliminado"), HttpStatus.OK);
    }
	
}
