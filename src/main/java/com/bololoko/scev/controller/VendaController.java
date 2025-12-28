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

import com.bololoko.scev.model.dto.venda.VendaItemDTOReq;
import com.bololoko.scev.model.dto.venda.VendaItemDTORes;
import com.bololoko.scev.service.VendaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {

	private final VendaService vendaService;
	
	// Registra venda
	@PostMapping
	public ResponseEntity<VendaItemDTORes> registrarVenda(@Valid @RequestBody VendaItemDTOReq vendaItemDTO) { 
		VendaItemDTORes novaVenda = vendaService.registrarVenda(vendaItemDTO);
		return new ResponseEntity<>(novaVenda, HttpStatus.CREATED);
	}
	
	
	// Busca todas as vendas
	@GetMapping
	public ResponseEntity<List<VendaItemDTORes>> listarTodasVendas() {
		List<VendaItemDTORes> vendas = vendaService.buscaTodasVendas();
		
		if(vendas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(vendas);
	}
	
	
	// Atualiza uma venda
	@PutMapping("/{id}")
	public ResponseEntity<VendaItemDTORes> atualizaVenda(@PathVariable Long idVenda, @Valid @RequestBody VendaItemDTOReq vendaDTOReq ) {
		VendaItemDTORes res = vendaService.atualizaVenda(idVenda, vendaDTOReq);
		return ResponseEntity.ok(res);
	}
	
	
	//Deleta uma venda 
	@DeleteMapping("/{id}")
	public ResponseEntity<VendaItemDTORes> deletaVenda(@PathVariable Long idVenda) {
		vendaService.deletarVenda(idVenda);
		return ResponseEntity.noContent().build();
	}
	
}
