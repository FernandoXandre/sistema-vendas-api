package com.bololoko.scev.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bololoko.scev.model.dto.produto.ProdutoDTOReq;
import com.bololoko.scev.model.dto.produto.ProdutoDTORes;
import com.bololoko.scev.model.entity.Produto;
import com.bololoko.scev.model.exception.NumeroInvalidoException;
import com.bololoko.scev.model.exception.RecursoExistenteException;
import com.bololoko.scev.model.exception.RecursoNaoEncontradoException;
import com.bololoko.scev.repository.ProdutoRepository;
import com.bololoko.scev.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProdutoService {
	
	
	private final ProdutoRepository produtoRepository;
	
	// cadastrar novo produto
	@Transactional
	public ProdutoDTORes cadastrarProduto(ProdutoDTOReq produtoDTO) {
		
		String nomeProdutoTratado = StringUtils.padronizaString(produtoDTO.nomeProduto());
		String saborProdutoTratado = StringUtils.padronizaString(produtoDTO.sabor());
		
		if(produtoRepository.existsByNomeProduto(nomeProdutoTratado)) {
			throw new RecursoExistenteException("O produto com nome '" + produtoDTO.nomeProduto() + "' ja esta cadastrado no sistema.");
		}
		
		// Cria a entidade de produto para salvar no banco
		Produto produto = new Produto();
		produto.setNomeProduto(nomeProdutoTratado);
		produto.setSabor(saborProdutoTratado);
		produto.setPrecoUnidade(produtoDTO.precoUnidade());
		
		Produto produtoSalvo = produtoRepository.save(produto);
		
		
		// Retona e cria o DTO de resposta 
		return new ProdutoDTORes(
			produtoSalvo.getIdProduto(),
			produtoSalvo.getNomeProduto(),
			produtoSalvo.getSabor(),
			produtoSalvo.getPrecoUnidade()
		);
	}
	
	
	// Buscar todos os produtos
	@Transactional
	public List<ProdutoDTORes> listaProdutos() {
		
		// Busca todos os produtos e converte para DTO de resposta
		List<ProdutoDTORes> listaProdutosDTO = produtoRepository.findAll().stream()
				.map(produto -> new ProdutoDTORes(
						produto.getIdProduto(),
						produto.getNomeProduto(),
						produto.getSabor(),
						produto.getPrecoUnidade()
				))
				.collect(Collectors.toList());
		
		return listaProdutosDTO;
	}
	
	//Buscar produto por id
	@Transactional
	public ProdutoDTORes buscaProdutoPorId(String id) {
		
		Long idProduto;
		
		// Converte Id
		try {
			idProduto = Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new NumeroInvalidoException("O id informado não é valido.");
		};
		
		
		// busca o produto no banco de dados
		Produto produto = produtoRepository.findById(idProduto)
				.orElseThrow(() -> new RecursoNaoEncontradoException("O produto com o id '" + idProduto + "' nao foi encontrado."));
		
		// converte em DTO de resposta e retorna  
		return new ProdutoDTORes(
				produto.getIdProduto(), 
				produto.getNomeProduto(), 
				produto.getSabor(), 
				produto.getPrecoUnidade()
		);
	}
	
	
	// Atualiza produto
	@Transactional
	public ProdutoDTORes atualizaProduto(String id, ProdutoDTOReq produtoDTO) {
		
		Long idProduto;
		
		// Converte id
		try {
			idProduto = Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new NumeroInvalidoException("O id informado não é valido.");
		}
		
		// Trata o nome do produto
		String nomeProdutoTratado = StringUtils.padronizaString(produtoDTO.nomeProduto());
		
		// Pesquisa o produto por id
		Produto produto = produtoRepository.findById(idProduto)
				.orElseThrow(() -> new RecursoNaoEncontradoException("O produto com id '" + idProduto + "' nao existe para atualizar. Por favor verifique se o nome do produto esta correto."));
		
		
		// Atualiza o produto antigo
		produto.setNomeProduto(nomeProdutoTratado);
		produto.setSabor(produtoDTO.sabor());
		produto.setPrecoUnidade(produtoDTO.precoUnidade());
		
		// Salva o produto no banco
		Produto produtoSalvo = produtoRepository.save(produto);
		
		return new ProdutoDTORes(
				produtoSalvo.getIdProduto(),
				produtoSalvo.getNomeProduto(),
				produtoSalvo.getSabor(),
				produtoSalvo.getPrecoUnidade()
		);
		
	}
	
	
	// Deleta produto
	@Transactional
	public void deletaProduto(String id) {
		
		Long idProduto;
		
		try {
			idProduto = Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new NumeroInvalidoException("O id informado não é valido.");
		}
		
		// Verifica se o produto existe no banco
		if(!produtoRepository.existsById(idProduto)) {
			throw new RecursoNaoEncontradoException("O produto com o id '" + idProduto + "' nao existe.");
		}
		
		produtoRepository.deleteById(idProduto);
	}
	
	
	
}
