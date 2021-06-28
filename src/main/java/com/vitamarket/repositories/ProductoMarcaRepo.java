package com.vitamarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitamarket.models.ProductoMarca;

@Repository
public interface ProductoMarcaRepo extends JpaRepository<ProductoMarca, Long> {

}
