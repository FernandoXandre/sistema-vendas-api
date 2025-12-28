package com.bololoko.scev.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bololoko.scev.model.dto.compra.CompraDTOCompletaRes;
import com.bololoko.scev.model.dto.compra.CompraMaterialDTOReq;
import com.bololoko.scev.model.dto.compra.CompraSimplesDTORes;
import com.bololoko.scev.service.CompraMaterialService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraMaterialController {
	
	private final CompraMaterialService compraService;
	
	
	// Busca todas as compras de forma simples
	@GetMapping
	public ResponseEntity<List<CompraSimplesDTORes>> buscaTodasComprasSimples() {
		List<CompraSimplesDTORes> listaCompras = compraService.buscaTodasCompras();
		
		if(listaCompras.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(listaCompras);
	}
	
	
	// Busca compra por id uma compra completa
	@GetMapping("/{idCompra}")
	public ResponseEntity<CompraDTOCompletaRes> buscaCompraCompletaPorId(@PathVariable Long idCompra){
		CompraDTOCompletaRes compraCompleta = compraService.buscaCompraCompletaPorId(idCompra);
		
		return ResponseEntity.ok(compraCompleta);
	}
	
	
	// Registra uma compra, retorna os dados simples da compra
	@PostMapping
	public ResponseEntity<CompraSimplesDTORes> registraCompra(@Valid @RequestBody CompraMaterialDTOReq compraDTOReq) { 
		CompraSimplesDTORes compraSalva = compraService.registrarCompra(compraDTOReq);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(compraSalva);
			
	}
	
	
	// Atualiza uma Compra
	@PutMapping("/{idCompra}")
	public ResponseEntity<CompraSimplesDTORes> atualizaCompraPorId(@PathVariable Long idCompra, @Valid @RequestBody CompraMaterialDTOReq compraAtualizadaReq) {
		CompraSimplesDTORes compraSimplesSalva = compraService.atualizaCompraPorId(idCompra, compraAtualizadaReq);
		
		return ResponseEntity.status(HttpStatus.OK).body(compraSimplesSalva);
	}
	
	
	// Deleta Compra
	@DeleteMapping("/{idCompra}")
	public ResponseEntity<CompraSimplesDTORes> deletaCompraPorId(@PathVariable Long idCompra) {
		compraService.deletaCompra(idCompra);
		
		return ResponseEntity.noContent().build();
	}
}
