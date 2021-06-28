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
import com.vitamarket.dto.ProductoCategoriaDto;
import com.vitamarket.models.ProductoCategoria;
import com.vitamarket.services.ProductoCategoriaService;

@RestController
@RequestMapping("api/productos/categorias")
@CrossOrigin(origins = "*")
public class ProductoCategoriaController {
	
	@Autowired
	ProductoCategoriaService categoriaService;
	
	@GetMapping("")
	public ResponseEntity<List<ProductoCategoria>> list() {
		List<ProductoCategoria> list = categoriaService.list();
		return new ResponseEntity<List<ProductoCategoria>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {
		if (!categoriaService.existsById(id)) {
			return new ResponseEntity<Mensaje>(new Mensaje("Registro no encontrado"), HttpStatus.NOT_FOUND);
		}
		ProductoCategoria categoria = categoriaService.getOne(id).get();
		return new ResponseEntity<ProductoCategoria>(categoria, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("")
	public ResponseEntity<?> create(@Valid @RequestBody ProductoCategoriaDto dto, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<Mensaje>(new Mensaje("Campos no válidos"), HttpStatus.BAD_REQUEST);
		}
		ProductoCategoria categoria = new ProductoCategoria();
		categoria.setNombre(dto.getNombre());
		categoriaService.save(categoria);
		return new ResponseEntity<Mensaje>(new Mensaje("Registro guardado"), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ProductoCategoria nuevaCategoria, BindingResult result){
		if(!categoriaService.existsById(id)) {
            return new ResponseEntity<Mensaje>(new Mensaje("Registro no encontrado"), HttpStatus.NOT_FOUND);
        }
		if(result.hasErrors()) {
			return new ResponseEntity<Mensaje>(new Mensaje("Campos no válidos"), HttpStatus.BAD_REQUEST);
		}
		ProductoCategoria categoria = categoriaService.getOne(id).get();
		categoria.setNombre(nuevaCategoria.getNombre());
		categoriaService.save(categoria);
        return new ResponseEntity<Mensaje>(new Mensaje("Registro guardado"), HttpStatus.OK);
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
        if(!categoriaService.existsById(id)) {
        	return new ResponseEntity<Mensaje>(new Mensaje("Registro no encontrado"), HttpStatus.NOT_FOUND);
        }
        categoriaService.delete(id);
        return new ResponseEntity<Mensaje>(new Mensaje("Registro eliminado"), HttpStatus.OK);
    }
	
}
