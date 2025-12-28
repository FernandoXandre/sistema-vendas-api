package com.bololoko.scev.model.dto.material;

import java.math.BigDecimal;

import com.bololoko.scev.model.entity.UnidadeMedida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MaterialDTOReq(
			@NotBlank(message = "O nome do produto tem que ser preenchido.")
			String nomeMaterial,
			
			@NotNull(message = "O tipo de medida do material precisa ser preenchido.")
			UnidadeMedida tipoMedida,
			
			@NotNull(message = "A quantidade por embalagem do material deve ser preenchido.")
			double qtdPorUnidade,
			
			@NotNull(message = "A quantidade atual do estoque desse material deve ser preenchido.")
			double qtdEstoque,
			
			@NotNull(message = "O preco pago no material deve ser preenchido.")
			BigDecimal precoPago
		) {}
