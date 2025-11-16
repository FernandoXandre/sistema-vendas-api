package com.bololoko.scev.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_material_compra")
public class MaterialCompra {

    @EmbeddedId
    private MaterialCompraPK id_material_compra = new MaterialCompraPK();

    private Integer quantidade; 
    private BigDecimal preco_unidade_compra; 

    @MapsId("compra") 
    @ManyToOne(fetch = FetchType.LAZY)
    public Compra getCompra() { 
    		return id_material_compra.getCompra(); 	
    }
    
    @MapsId("material") 
    @ManyToOne(fetch = FetchType.LAZY)
    public Material getMaterial() { 
    		return id_material_compra.getMaterial(); 
    }
    
    // GETTERS E SETTERS 

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco_unidade_compra() {
		return preco_unidade_compra;
	}

	public void setPreco_unidade_compra(BigDecimal preco_unidade_compra) {
		this.preco_unidade_compra = preco_unidade_compra;
	}

	public MaterialCompraPK getId_material_compra() {
		return id_material_compra;
	}
    
    
}
