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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitamarket.dto.Mensaje;
import com.vitamarket.dto.PedidoDto;
import com.vitamarket.models.Pedido;
import com.vitamarket.models.Producto;
import com.vitamarket.services.PedidoService;
import com.vitamarket.services.ProductoService;

@RestController
@RequestMapping("api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoContoller {

	@Autowired
	PedidoService pedidoService;

	@Autowired
	ProductoService productoService;

	@PreAuthorize("hasRole('USER')")
	@GetMapping("")
	public ResponseEntity<List<Pedido>> list() {
		List<Pedido> list = pedidoService.list();
		return new ResponseEntity<List<Pedido>>(list, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/get")
	public ResponseEntity<List<Pedido>> listByUsername(@RequestParam(value="username") String username) {
		List<Pedido> list = pedidoService.list(username);
		return new ResponseEntity<List<Pedido>>(list, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {
		if (!pedidoService.existsById(id)) {
			return new ResponseEntity<Mensaje>(new Mensaje("Pedido no encontrado"), HttpStatus.NOT_FOUND);
		}
		Pedido pedido = pedidoService.getOne(id).get();
		return new ResponseEntity<Pedido>(pedido, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("")
	public ResponseEntity<?> create(@Valid @RequestBody PedidoDto dto, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<Mensaje>(new Mensaje("Los datos ingresados no son válidos"), HttpStatus.BAD_REQUEST);
		}
		try {
			Long id = dto.getIdProducto();
			Producto producto = productoService.getOne(id).get();

			if (dto.getCantidad() <= producto.getStock()) {
				// Calcular el precio total
				double total = producto.getPrecio() * dto.getCantidad();
				dto.setTotal(total);

				// Recalcular el stock
				int diferencia = producto.getStock() - dto.getCantidad();
				producto.setStock(diferencia);
				productoService.save(producto);

				// Registrar el pedido
				Pedido pedido = new Pedido();
				pedido.setCliente(dto.getCliente());
				pedido.setDireccion(dto.getDireccion());
				pedido.setIdProducto(dto.getIdProducto());
				pedido.setCantidad(dto.getCantidad());
				pedido.setTotal(dto.getTotal());
				pedido.setEstado("Pendiente");
				pedidoService.save(pedido);
			} else {
				new ResponseEntity<Mensaje>(
						new Mensaje("Stock insuficiente. Actualmente: " + producto.getStock() + " unidades"),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
		}
		return new ResponseEntity<Mensaje>(new Mensaje("El pedido ha sido realizado"), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Pedido dto, BindingResult result) {
		if (!pedidoService.existsById(id)) {
			return new ResponseEntity<Mensaje>(new Mensaje("Pedido no encontrado"), HttpStatus.NOT_FOUND);
		}
		if (result.hasErrors()) {
			return new ResponseEntity<Mensaje>(new Mensaje("Los datos ingresados no son válidos"), HttpStatus.BAD_REQUEST);
		}
		Pedido pedido = pedidoService.getOne(id).get();
		pedido.setEstado(dto.getEstado());
		pedidoService.save(pedido);
		return new ResponseEntity<Mensaje>(new Mensaje("El pedido ha sido actualizado"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		if (!pedidoService.existsById(id)) {
			return new ResponseEntity<Mensaje>(new Mensaje("Pedido no encontrado"), HttpStatus.NOT_FOUND);
		}
		pedidoService.delete(id);
		return new ResponseEntity<Mensaje>(new Mensaje("El pedido ha sido eliminado"), HttpStatus.OK);
	}

}
