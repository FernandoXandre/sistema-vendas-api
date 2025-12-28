package com.bololoko.scev.model.dto.compra;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CompraMaterialDTOReq {

	
	@NotNull(message = "O campo de data da compra do material deve ser preenchido.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCompra;
	
	@NotBlank(message = "O campo de estabelecimento deve ser preenchido.")
	private String estabelecimento; // Nome do fornecedor que esta no banco. 

	@Valid
	@NotEmpty(message = "Os itens de materiais compradados devem ser preenchidos.")
	private List<ItemListaMaterialDTO> itens; // Lista com os itens comprados.
	
	

	//GETTERS E SETTERS

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}


	public String getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(String estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public List<ItemListaMaterialDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemListaMaterialDTO> itens) {
		this.itens = itens;
	}

}

