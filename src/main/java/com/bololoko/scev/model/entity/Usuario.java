package com.bololoko.scev.model.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_usuario")
public class Usuario  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	
	@NotBlank(message = "O email do usuario e obrigatorio")
	@Column(nullable = false, length = 30)
	@Email(message = "Email do usuario invalido")
	private String email;
	
	@NotBlank(message = "A senha do usuario e obrigatoria")
	@Column(nullable = false, length = 100)
	private String senha;
	
	@NotBlank(message = "O nome do usuario e obrigatorio")
	@Column(nullable = false, length = 50)
	private String nome;
	
	/*
	@NotNull(message = "A permissao do usuario e obrigatoria")
	@Column(nullable = false)
	private Perfil permissao;
	*/
	
	
	@NotBlank(message = "O CPF do usuario e obrigatorio")
	@Column(nullable = false, length = 11)
	private String CPF;
	
	@NotBlank(message = "O telefone do usuario e obrigatorio")
	@Column(nullable = false, length = 11)
	private String telefone;
	
	@NotNull(message = "O status de ativo do usuario e obrigatorio")
	@Column(nullable = false)
	private boolean ativo;
	
	@OneToMany(mappedBy = "idCompra", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Compra> compras = new HashSet<>();
	
	@OneToMany(mappedBy = "idVenda", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Venda> vendas = new HashSet<>();
}
