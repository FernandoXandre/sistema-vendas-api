package com.bololoko.scev.model.dto.venda;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record VendaItemDTOReq(

		@NotNull LocalDate dataVenda,
		
		@NotEmpty List<ItemVendaDTOReq> itens
		) {}
