package com.bololoko.scev.model.dto.produto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoDTOReq(
			@NotBlank(message = "O nome do produto precisa ser preenchido.") 
			String nomeProduto,
			@NotNull(message = "O produto precisa de um preco por unidade.") 
			BigDecimal precoUnidade,
			
			String sabor
		) {}
