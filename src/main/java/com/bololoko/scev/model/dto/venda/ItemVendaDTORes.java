package com.bololoko.scev.model.dto.venda;

import java.math.BigDecimal;

public record ItemVendaDTORes(
		
			Long idProduto,
			String nomeProduto,
			int quantidade,
			BigDecimal precoUnitario,
			String sabor
		) {}
