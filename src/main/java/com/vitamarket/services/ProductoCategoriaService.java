package com.vitamarket.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitamarket.models.ProductoCategoria;
import com.vitamarket.repositories.ProductoCategoriaRepo;

@Service
@Transactional
public class ProductoCategoriaService {

	@Autowired
	ProductoCategoriaRepo repository;
	
	public List<ProductoCategoria> list(){
        return repository.findAll();
    }
	public Optional<ProductoCategoria> getOne(Long id){
        return repository.findById(id);
    }

	public ProductoCategoria save(ProductoCategoria categoria){
        return repository.save(categoria);
    }
	public void delete(Long id){
		repository.deleteById(id);
    }

	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
}
