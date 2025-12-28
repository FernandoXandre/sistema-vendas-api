package com.bololoko.scev.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_item_venda" )
public class ItemVenda {
	
	@EmbeddedId
	private ItemVendaPK idItemVenda = new ItemVendaPK();
	
	@NotNull(message = "O preco da unidade é obrigatorio")
	@Column(nullable = false, length = 10)
	@PositiveOrZero(message = "O preco da unidade do produto vendido na classe ItemVenda nao pode ser negativo")
	private BigDecimal precoUnidadeVenda;
	
	
	@NotNull(message = "A quantidade vendida é obrigatoria")
	@Column(nullable = false, length = 10)
	@PositiveOrZero(message = "A quantidade de produtos vendidos na classe ItemVenda nao pode ser negativo")
	private int quantidade;
	
	@Column(nullable = true, length = 10)
	private String saborProduto;
	
	@MapsId("venda")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_venda")
	private Venda venda;
	
	@MapsId("produto")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_produto")
	private Produto produto;
	
}
