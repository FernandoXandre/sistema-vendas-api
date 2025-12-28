package com.bololoko.scev.model.dto.produto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record ProdutoDTORes (
			@NotNull Long idProduto,
			@NotNull String nomeProduto,
			String sabor,
			@NotNull BigDecimal precoUnidade
		){}
