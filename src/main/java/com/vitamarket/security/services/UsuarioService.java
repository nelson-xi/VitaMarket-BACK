package com.vitamarket.security.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitamarket.security.models.Usuario;
import com.vitamarket.security.repositories.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {
	
	@Autowired
	UsuarioRepository repository;
	
	public Optional<Usuario> getOne(Long id) {
		return repository.findById(id);
	}
	
	public Optional<Usuario> getByUsername(String username){
        return repository.findByUsername(username);
    }
	
	public boolean existsByUsername(String username){
        return repository.existsByUsername(username);
    }
	
	public boolean existsByEmail(String email){
        return repository.existsByEmail(email);
    }
	
	public void save(Usuario usuario){
		repository.save(usuario);
    }

}
