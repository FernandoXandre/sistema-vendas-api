package com.bololoko.scev.model.dto.venda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


// record de resposta da venda em si com o conjunto de itens

public record VendaItemDTORes(
		
		Long idVenda,
		LocalDate dataVenda,
		BigDecimal valorTotalVendido,
		List<ItemVendaDTORes> itens
) {}
