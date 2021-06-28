package com.vitamarket.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitamarket.security.enums.RolNombre;
import com.vitamarket.security.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
	Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
