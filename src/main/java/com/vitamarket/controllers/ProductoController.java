package com.vitamarket.controllers;

import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;

import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vitamarket.dto.Mensaje;
import com.vitamarket.dto.ProductoDto;
import com.vitamarket.models.Producto;
import com.vitamarket.models.utils.PagingHeaders;
import com.vitamarket.models.utils.PagingResponse;
import com.vitamarket.services.ProductoService;

@RestController
@RequestMapping("api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

	@Autowired
	ProductoService productoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {
		if(!productoService.existsById(id)) {
			return new ResponseEntity<Mensaje>(new Mensaje("Producto no encontrado"), HttpStatus.NOT_FOUND);
		}
		Producto producto = productoService.getOne(id).get();
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}
	
	@Transactional
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Producto>> list(
	        @And({
	        		@Spec(path = "nombre", params = "nombre", spec = Like.class),
	        		@Spec(path = "marca", params = "marca", spec = Like.class),
	                @Spec(path = "categoria", params = "categoria", spec = Like.class),
	                @Spec(path = "precio", params = "precio", spec = In.class)
	        }) Specification<Producto> spec,
	        Sort sort,
	        @RequestHeader HttpHeaders headers) {
	    final PagingResponse response = productoService.get(spec, headers, sort);
	    return new ResponseEntity<>(response.getElements(), returnHttpHeaders(response), HttpStatus.OK);
	}
	
	public HttpHeaders returnHttpHeaders(PagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("")
	public ResponseEntity<?> create(@Valid @RequestBody ProductoDto dto, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<Mensaje>(new Mensaje("Verifique que los campos ingresados sean válidos"), HttpStatus.BAD_REQUEST);
		}
		Producto producto = new Producto();
		producto.setNombre(dto.getNombre());
		producto.setDescripcion(dto.getDescripcion());
		producto.setPrecio(dto.getPrecio());
		producto.setStock(dto.getStock());
		producto.setImgUrl(dto.getImgUrl());
		producto.setMarca(dto.getMarca());
		producto.setCategoria(dto.getCategoria());
		productoService.save(producto);
		return new ResponseEntity<Mensaje>(new Mensaje("El producto se ha registrado"), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ProductoDto dto, BindingResult result){
		if(!productoService.existsById(id)) {
            return new ResponseEntity<Mensaje>(new Mensaje("Producto no encontrado"), HttpStatus.NOT_FOUND);
        }
		if(result.hasErrors()) {
			return new ResponseEntity<Mensaje>(new Mensaje("Alguno de los campos ingresados no es válido"), HttpStatus.BAD_REQUEST);
		}
		Producto producto = productoService.getOne(id).get();
		producto.setNombre(dto.getNombre());
		producto.setDescripcion(dto.getDescripcion());
		producto.setPrecio(dto.getPrecio());
		producto.setStock(dto.getStock());
		producto.setImgUrl(dto.getImgUrl());
		producto.setMarca(dto.getMarca());
		producto.setCategoria(dto.getCategoria());
		productoService.save(producto);
        return new ResponseEntity<Mensaje>(new Mensaje("Los cambios se han guardado"), HttpStatus.OK);
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
        if(!productoService.existsById(id)) {
        	return new ResponseEntity<Mensaje>(new Mensaje("Producto no encontrado"), HttpStatus.NOT_FOUND);
        }
        productoService.delete(id);
        return new ResponseEntity<Mensaje>(new Mensaje("El producto ha sido eliminado"), HttpStatus.OK);
    }
	
}
