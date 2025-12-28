package com.bololoko.scev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bololoko.scev.model.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	Optional<Produto> findByNomeProduto(String nomeProduto);
	Boolean existsByNomeProduto(String nomeProduto);

}
