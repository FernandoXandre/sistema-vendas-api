package com.bololoko.scev.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bololoko.scev.model.dto.fornecedor.FornecedorDTOReq;
import com.bololoko.scev.model.dto.fornecedor.FornecedorDTORes;
import com.bololoko.scev.model.entity.Fornecedor;
import com.bololoko.scev.model.exception.RecursoNaoEncontradoException;
import com.bololoko.scev.repository.FornecedorRepository;
import com.bololoko.scev.util.StringUtils;

public class FornecedorService {

	@Autowired
	FornecedorRepository fornecedorRepository;
	
	
	// Cadastra novo fornecedor
	@Transactional
	public FornecedorDTORes cadastrarNovo(FornecedorDTOReq fornecedorDTOReq ) {
		
		String nomePadronizado = StringUtils.padronizaString(fornecedorDTOReq.getNomeFornecedor());
		
		if(fornecedorRepository.existsByNomeFornecedor(nomePadronizado)) {
			throw new RuntimeException("Fornecedor ja cadastrado no sistema.");
		}

		
		// Cria o fornecedor 
		Fornecedor novoFornecedor = new Fornecedor();
		novoFornecedor.setNomeFornecedor(nomePadronizado);
		novoFornecedor.setTelefone(fornecedorDTOReq.getTelefone());
		
		Fornecedor fornecedorSalvo = fornecedorRepository.save(novoFornecedor);
		
		// Retorna o DTO de resposta 
		return new FornecedorDTORes(
				fornecedorSalvo.getIdFornecedor(),
				fornecedorSalvo.getNomeFornecedor(),
				fornecedorSalvo.getTelefone()
		);
	}
	
	
	// Busca todos os fornecedores 
	@Transactional
	public List<FornecedorDTORes> buscaTodosFornecedores() {
		return fornecedorRepository.findAll().stream()
				.map(f -> new FornecedorDTORes(
						f.getIdFornecedor(),
						f.getNomeFornecedor(),
						f.getTelefone()
				))
				.collect(Collectors.toList());
	}
	
	
	// Busca fornecedor por id
	@Transactional
	public FornecedorDTORes buscaFornecedorPorId(Long idFornecedor) {
		return fornecedorRepository.findById(idFornecedor)
				.map(f -> new FornecedorDTORes(
						f.getIdFornecedor(),
						f.getNomeFornecedor(),
						f.getTelefone()
				))
				.orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor com o id '" + idFornecedor + "' nao foi encontrado."));
	}
	
	
	// Atualiza fornecedor 
	@Transactional
	public FornecedorDTORes atualizaFornecedor(Long idFornecedor, FornecedorDTOReq req) {
		Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor)
				.orElseThrow(() -> new RecursoNaoEncontradoException(""));
		
		
		// Atualiza o fornecedor 
		fornecedor.setNomeFornecedor(req.getNomeFornecedor());
		fornecedor.setTelefone(req.getTelefone());
		
		// Salva no banco 
		Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
		
		// Retorna e converte em DTO de resposta 
		return new FornecedorDTORes(
				fornecedorSalvo.getIdFornecedor(),
				fornecedorSalvo.getNomeFornecedor(),
				fornecedorSalvo.getTelefone()
		);
	}
	
	
	// Deleta fornecedor
	@Transactional
	public void deletaFornecedor(Long idFornecedor) {
		if(fornecedorRepository.existsById(idFornecedor)) {
			fornecedorRepository.deleteById(idFornecedor);
			return;
		}
		
		throw new RecursoNaoEncontradoException("O fornecedor com o id '" + idFornecedor + "' nao foi encontrado.");
	}
}
