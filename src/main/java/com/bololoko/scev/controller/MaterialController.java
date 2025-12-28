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

import com.bololoko.scev.model.dto.material.MaterialDTOReq;
import com.bololoko.scev.model.dto.material.MaterialDTORes;
import com.bololoko.scev.service.MaterialService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/materiais")
@RequiredArgsConstructor
public class MaterialController {
	
	private final MaterialService materialService;
	
	
	// Cadastra novo material
	@PostMapping
	public ResponseEntity<MaterialDTORes> cadastraMaterial(@Valid @RequestBody MaterialDTOReq materialReq) {
		
		MaterialDTORes materialSalvo = materialService.cadastraMaterial(materialReq);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(materialSalvo);
		
	}
	
	
	// Busca todos materiais
	@GetMapping
	public ResponseEntity<List<MaterialDTORes>> buscaTodosMateriais() {
		List<MaterialDTORes> materiaisSalvos = materialService.buscaTodosMateriais();
		
		return ResponseEntity.status(HttpStatus.OK).body(materiaisSalvos);
	}
	
	
	// Busca material por id
	@GetMapping("/{idMaterial}")
	public ResponseEntity<MaterialDTORes> buscaMaterialPorId(@PathVariable Long idMaterial) {
		MaterialDTORes materialSalvo = materialService.buscaMaterialPorId(idMaterial);
		
		return ResponseEntity.status(HttpStatus.OK).body(materialSalvo);
	}
	
	
	// Atualiza Material por id 
	@PutMapping("/{idMaterial}")
	public ResponseEntity<MaterialDTORes> atualizaMaterialPorId(@PathVariable Long idMaterial, @Valid @RequestBody MaterialDTOReq materialDTOReq) {
		
		MaterialDTORes materialAtualizado = materialService.atualizaMaterialPorId(idMaterial, materialDTOReq);
		
		return ResponseEntity.status(HttpStatus.OK).body(materialAtualizado);
		
	}
	
	
	// Deleta material 
	@DeleteMapping("/{idMaterial}")
	public ResponseEntity<MaterialDTORes> deletaMaterial(@PathVariable Long idMaterial) {
		materialService.deletaMaterial(idMaterial);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
 }


