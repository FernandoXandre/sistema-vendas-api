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
@Table(name = "tb_material")
public class Material {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_material;
	
	private String nome;
	private String unidade_medida;
	private int qtd_estoque;
	private BigDecimal preco_pago;
	
	@OneToMany(mappedBy = "id_material_compra.material", fetch = FetchType.LAZY)
	private Set<MaterialCompra> itensCompra;
	
	// GETTERS E SETTERS 

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUnidade_medida() {
		return unidade_medida;
	}

	public void setUnidade_medida(String unidade_medida) {
		this.unidade_medida = unidade_medida;
	}

	public int getQtd_estoque() {
		return qtd_estoque;
	}

	public void setQtd_estoque(int qtd_estoque) {
		this.qtd_estoque = qtd_estoque;
	}

	public BigDecimal getPreco_pago() {
		return preco_pago;
	}

	public void setPreco_pago(BigDecimal preco_pago) {
		this.preco_pago = preco_pago;
	}

	public Long getId_material() {
		return id_material;
	}

	public Set<MaterialCompra> getItensCompra() {
		return itensCompra;
	}
	
}
