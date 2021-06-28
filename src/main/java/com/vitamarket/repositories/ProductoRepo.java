package com.vitamarket.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.vitamarket.models.Producto;

@Repository
public interface ProductoRepo extends 
PagingAndSortingRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {

}
