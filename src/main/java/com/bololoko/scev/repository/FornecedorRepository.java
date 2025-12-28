package com.bololoko.scev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bololoko.scev.model.entity.Fornecedor;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	Optional<Fornecedor> findByNomeFornecedor(String nomeFornecedor);
	Boolean existsByNomeFornecedor(String nome);
}
