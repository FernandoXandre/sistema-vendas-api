package com.bololoko.scev.model.dto.material;

import java.math.BigDecimal;

import com.bololoko.scev.model.entity.UnidadeMedida;

public record MaterialDTORes(
			Long idMaterial,
			String nomeMaterial,
			UnidadeMedida tipoMedida,
			double qtdPorUnidade,
			double qtdEstoque,
			BigDecimal precoPago
		) {}
