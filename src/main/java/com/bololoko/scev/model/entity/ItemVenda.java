package com.bololoko.scev.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_item_venda" )
public class ItemVenda {
	
	@EmbeddedId
	private ItemVendaPK id_item_venda = new ItemVendaPK();
	
	private BigDecimal preco_unidade_venda;
	private int quantidade;
	
	@MapsId("venda")
	@ManyToOne(fetch = FetchType.LAZY)
	public Venda getVenda() {
		return id_item_venda.getVenda();
	}
	
	@MapsId("produto")
	@ManyToOne(fetch = FetchType.LAZY)
	public Produto getProduto() {
		return id_item_venda.getProduto();
	}
	
	// GETTERS E SETTERS 

	public BigDecimal getPreco_unidade_venda() {
		return preco_unidade_venda;
	}

	public void setPreco_unidade_venda(BigDecimal preco_unidade_venda) {
		this.preco_unidade_venda = preco_unidade_venda;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public ItemVendaPK getId_item_venda() {
		return id_item_venda;
	}
	
}
