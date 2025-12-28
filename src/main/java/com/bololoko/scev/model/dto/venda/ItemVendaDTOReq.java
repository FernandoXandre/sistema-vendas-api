package com.bololoko.scev.model.dto.venda;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;


// record de resposta de  cada item da venda 

public record ItemVendaDTOReq(
		
			@NotNull String nomeProduto,
			@NotNull int quantidade,
			@NotNull @PositiveOrZero BigDecimal precoUnitario,
			String sabor
		) {
	public BigDecimal getSubtotal() {
		return this.precoUnitario().multiply(BigDecimal.valueOf(this.quantidade()));
	}
}	
