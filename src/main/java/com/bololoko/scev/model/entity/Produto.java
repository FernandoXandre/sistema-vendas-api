package com.bololoko.scev.model.entity;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produto;
    
    private String nome_produto;
    private String sabor; 
    private BigDecimal preco_unidade;
    
    @OneToMany(mappedBy = "id_item_venda.produto", fetch = FetchType.LAZY)
    private Set<ItemVenda> itensVenda;
    
    // GETTERS E SETTERS

	public String getNome_produto() {
		return nome_produto;
	}

	public void setNome_produto(String nome_produto) {
		this.nome_produto = nome_produto;
	}

	public String getSabor() {
		return sabor;
	}

	public void setSabor(String sabor) {
		this.sabor = sabor;
	}

	public BigDecimal getPreco_unidade() {
		return preco_unidade;
	}

	public void setPreco_unidade(BigDecimal preco_unidade) {
		this.preco_unidade = preco_unidade;
	}

	public Long getId_produto() {
		return id_produto;
	}

	public Set<ItemVenda> getItensVenda() {
		return itensVenda;
	}
    

}
