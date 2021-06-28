package com.vitamarket.security.controllers;

import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitamarket.dto.Mensaje;
import com.vitamarket.security.dto.JwtDto;
import com.vitamarket.security.dto.LoginDto;
import com.vitamarket.security.dto.RegistroDto;
import com.vitamarket.security.enums.RolNombre;
import com.vitamarket.security.jwt.JwtProvider;
import com.vitamarket.security.models.Rol;
import com.vitamarket.security.models.Usuario;
import com.vitamarket.security.services.RolService;
import com.vitamarket.security.services.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	RolService rolService;

	@Autowired
	JwtProvider jwtProvider;
	
	@PostMapping("/registro")
	public ResponseEntity<?> registro(@Valid @RequestBody RegistroDto dto, BindingResult result) {
		if(result.hasErrors()) {
            return new ResponseEntity<Mensaje>(new Mensaje("Error en los campos ingresados"),
            		HttpStatus.BAD_REQUEST);
		}
		if(usuarioService.existsByUsername(dto.getUsername())) {
			return new ResponseEntity<Mensaje>(new Mensaje("El nombre de usuario ingresado ya se encuentra en uso"),
					HttpStatus.BAD_REQUEST);
		}
		if(usuarioService.existsByEmail(dto.getEmail())) {
			return new ResponseEntity<Mensaje>(new Mensaje("El email ingresado ya se encuentra en uso"),
					HttpStatus.BAD_REQUEST);
		}
		Usuario usuario = new Usuario();
		usuario.setNombre(dto.getApellidos() + " " + dto.getNombres());
		usuario.setEmail(dto.getEmail());
		usuario.setCelular(dto.getCelular());
		usuario.setUsername(dto.getUsername());
		usuario.setPassword(passwordEncoder.encode(dto.getPassword()));

		Set<Rol> roles = new HashSet<>();
		roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
		if (dto.getRoles().contains("admin")) {
			roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
		}
		usuario.setRoles(roles);
		usuarioService.save(usuario);
		return new ResponseEntity<Mensaje>(new Mensaje("Registro exitoso"), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginDto dto, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<Mensaje>(new Mensaje("Error en los campos ingresados"), HttpStatus.BAD_REQUEST);
		}
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
	}
	
}
