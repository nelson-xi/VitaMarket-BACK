package com.vitamarket.security.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitamarket.security.enums.RolNombre;
import com.vitamarket.security.models.Rol;
import com.vitamarket.security.repositories.RolRepository;

@Service
@Transactional
public class RolService {
	
	@Autowired
    RolRepository repository;
	
	public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return repository.findByRolNombre(rolNombre);
    }
	
	public void save(Rol rol){
		repository.save(rol);
    }
	
}
