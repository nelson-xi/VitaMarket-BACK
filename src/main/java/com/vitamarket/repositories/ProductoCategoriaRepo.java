package com.vitamarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitamarket.models.ProductoCategoria;

@Repository
public interface ProductoCategoriaRepo extends JpaRepository<ProductoCategoria, Long> {

}
