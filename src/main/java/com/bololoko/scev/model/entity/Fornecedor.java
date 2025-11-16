package com.bololoko.scev.model.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_fornecedor")
public class Fornecedor {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_fornecedor;
	
	private String nome;
	private String telefone;
	
	@OneToMany(mappedBy = "fornecedor", fetch = FetchType.LAZY)
	private Set<Compra> compras;

	// GETTERS E SETTERS
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Long getId_fornecedor() {
		return id_fornecedor;
	}

	public Set<Compra> getCompras() {
		return compras;
	}
	
	
}
