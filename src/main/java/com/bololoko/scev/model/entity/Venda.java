package com.bololoko.scev.model.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_venda")
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_venda;
	
	private Instant data_venda;
	private double total_venda;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "id_item_venda.venda", fetch = FetchType.LAZY)
	private Set<ItemVenda> itens = new HashSet<>();
	// GETTERS E SETTERS 

	public Instant getData_venda() {
		return data_venda;
	}

	public void setData_venda(Instant data_venda) {
		this.data_venda = data_venda;
	}

	public double getTotal_venda() {
		return total_venda;
	}

	public void setTotal_venda(double total_venda) {
		this.total_venda = total_venda;
	}

	public Long getId_venda() {
		return id_venda;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Set<ItemVenda> getItens() {
		return itens;
	}
	
}
