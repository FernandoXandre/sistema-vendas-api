package com.bololoko.scev.controller;

import java.util.List;

import org.springframework.http.HttpStatus;import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bololoko.scev.model.dto.produto.ProdutoDTOReq;
import com.bololoko.scev.model.dto.produto.ProdutoDTORes;
import com.bololoko.scev.service.ProdutoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {
	
	private final ProdutoService produtoService;
	
	// cadastra um novo produto 
	@PostMapping
	public ResponseEntity<ProdutoDTORes> cadastraProduto(@Valid @RequestBody ProdutoDTOReq produtoDTO){
		
		ProdutoDTORes res = produtoService.cadastrarProduto(produtoDTO);
		return ResponseEntity.ok(res);
	}
	
	
	// Busca todos os produtos
	@GetMapping
	public ResponseEntity<List<ProdutoDTORes>> buscaTodosProdutos() {
		
		List<ProdutoDTORes> listaProdutos = produtoService.listaProdutos();
		
		return ResponseEntity.status(HttpStatus.OK).body(listaProdutos);
	}
	
	
	// Busca produto por id
	@GetMapping("/{idProduto}")
	public ResponseEntity<ProdutoDTORes> buscaProdutoPorId(@PathVariable Long idProduto) {
		
		ProdutoDTORes produtoDTO = produtoService.buscaProdutoPorId(idProduto);
		
		return ResponseEntity.status(HttpStatus.OK).body(produtoDTO);
	}
	
	
	// Atualiza por id
	@PutMapping("/{idProduto}")
	public ResponseEntity<ProdutoDTORes> atualizaProdutoPorId(@PathVariable Long idProduto, @Valid @RequestBody ProdutoDTOReq produtoDTOReq) {
		
		ProdutoDTORes produtoDTO = produtoService.atualizaProduto(idProduto, produtoDTOReq);
		
		return ResponseEntity.status(HttpStatus.OK).body(produtoDTO);
	}
	
	
	// Deleta por id
	@DeleteMapping("/{idProduto}")
	public ResponseEntity<ProdutoDTORes> deletaProdutoPorId(@PathVariable Long idProduto) {
		
		produtoService.deletaProduto(idProduto);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}

