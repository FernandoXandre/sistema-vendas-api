package com.bololoko.scev.model.dto.compra;

import java.time.LocalDate;
import java.util.List;

public record CompraDTOCompletaRes(
			Long idCompra,
			LocalDate dataCompra,
			String nomeFornecedor,
			List<ItemListaMaterialDTO> materiais
		) {}
