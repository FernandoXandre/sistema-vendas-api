package com.bololoko.scev.model.dto.compra;

import java.math.BigDecimal;

import com.bololoko.scev.model.entity.UnidadeMedida;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ItemListaMaterialDTO {
	
	@NotNull(message = "O campo de nome do material deve ser preenchido.")
	private String nomeMaterial; // Nome do material que esta no banco.
	
	@NotNull(message = "O campo de preco do material deve ser preenchido.")
	private BigDecimal precoMaterial; // Preco pago.
	
	@NotNull(message = "O campo do tipo de medida do material deve ser preenchido.")
	private UnidadeMedida tipoMedidaMaterial; //KG, ML ETC.
	
	@NotNull(message = "O campo de quantidade comprada do material deve ser preenchido.")
	private int qtdComprada;
	
	@NotNull(message = "A quantidade atual do material em estoque precisa ser preenchida.")
	private double estoqueAtualMaterial;
	
	@NotNull(message = "O campo de quantidade por unidade do material deve ser preenchido.")
	private double qtdPorUnidade; // para complementar o tipo de medida, 1.0, 2.0, 3.0 (KG, LT)
	
}
