package com.vitamarket.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vitamarket.models.Pedido;

@Repository
public interface PedidoRepo extends JpaRepository<Pedido, Serializable>{
	public abstract Optional<Pedido> findById(Long id);
	public abstract List<Pedido> findByCliente(String username);
}
