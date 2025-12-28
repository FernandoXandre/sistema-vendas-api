package com.bololoko.scev.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bololoko.scev.model.entity.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
	
	// query unica que busca todas as vendas com os itens do conjuntos e produtos
	@Query("SELECT DISTINCT v FROM Venda v " +
	           "LEFT JOIN FETCH v.itens i " +
	           "LEFT JOIN FETCH i.produto")
	List<Venda> findAllVendasComItemsEProdutos();
	
	// Para buscar por id para venda, com todos os detalhes
    @Query("SELECT v FROM Venda v " +
           "LEFT JOIN FETCH v.itens i " +
           "LEFT JOIN FETCH i.produto " +
           "WHERE v.id = :id")
    Optional<Venda> findByIdVendaComItemEProduto (@Param("id") Long id);
}
