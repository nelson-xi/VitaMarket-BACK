package com.vitamarket.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitamarket.models.ProductoMarca;
import com.vitamarket.repositories.ProductoMarcaRepo;

@Service
@Transactional
public class ProductoMarcaService {
	
	@Autowired
	ProductoMarcaRepo repository;

	public List<ProductoMarca> list(){
        return repository.findAll();
    }
	
	public Optional<ProductoMarca> getOne(Long id){
        return repository.findById(id);
    }

	public ProductoMarca save(ProductoMarca marca){
        return repository.save(marca);
    }
	
	public void delete(Long id){
		repository.deleteById(id);
    }

	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
}
