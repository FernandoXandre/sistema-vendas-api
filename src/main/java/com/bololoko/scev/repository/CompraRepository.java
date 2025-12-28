package com.bololoko.scev.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bololoko.scev.model.entity.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

	// Busca a compra por id trazendo os itens
	@Query("SELECT c FROM Compra c JOIN FETCH c.itens i JOIN FETCH i.material WHERE c.idCompra = :idCompra")
	Optional<Compra> findByIdCompraComItens(@Param("idCompra") Long idCompra);
	
	
	// Busca as compras com dados do fornecedor
	@Query("SELECT c FROM Compra c JOIN FETCH c.fornecedor")
	List<Compra> findAllComFornecedor();
 }
