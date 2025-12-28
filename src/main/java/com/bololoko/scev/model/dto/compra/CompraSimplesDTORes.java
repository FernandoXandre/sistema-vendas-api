package com.bololoko.scev.model.dto.compra;

import java.time.LocalDate;

public record CompraSimplesDTORes(
			Long idCompra,
			LocalDate dataCompra,
			String nomeFornecedor
		) {}
