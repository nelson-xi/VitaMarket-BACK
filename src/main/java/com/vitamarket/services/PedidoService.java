package com.vitamarket.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vitamarket.models.Pedido;
import com.vitamarket.repositories.PedidoRepo;

@Service
@Transactional
public class PedidoService {

	@Autowired
	PedidoRepo repository;
	
	public List<Pedido> list(){
        return repository.findAll();
    }
	
	public List<Pedido> list(String username){
        return repository.findByCliente(username);
    }
	
	public Optional<Pedido> getOne(Long id){
        return repository.findById(id);
    }

	public Pedido save(Pedido pedido){
        return repository.save(pedido);
    }
	
	public void delete(Long id){
		repository.deleteById(id);
    }

	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
}
