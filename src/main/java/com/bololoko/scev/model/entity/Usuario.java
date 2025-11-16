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
@Table(name = "tb_usuario")
public class Usuario  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_usuario;
	
	private String email;
	private String senha;
	private String nome;
	private String permissao;
	private String CPF;
	private String telefone;
	private boolean ativo;
	
	@OneToMany(mappedBy = "id_compra", fetch = FetchType.LAZY)
	private Set<Compra> compras;
	
	@OneToMany(mappedBy = "id_venda", fetch = FetchType.LAZY)
	private Set<Venda> vendas;
	
	// GETTERS E SETTERS 

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Long getId_usuario() {
		return id_usuario;
	}

	public String getPermissao() {
		return permissao;
	}

	public Set<Compra> getCompras() {
		return compras;
	}

	public Set<Venda> getVendas() {
		return vendas;
	}
	
}
