package com.bololoko.scev.model.dto.fornecedor;

import java.util.HashSet;
import java.util.Set;

import com.bololoko.scev.model.entity.Compra;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class FornecedorDTOReq {

	@NotBlank(message = "O nome do fornecedor é obrigatorio")
	@Column(nullable = false, length = 100)
	private String nomeFornecedor;
	
	
	@NotBlank(message = "O telefone do fornecedor é obrigatorio")
	@Column(nullable = false, length = 11)
	@PositiveOrZero(message = "O telefone do fornecedor nao pode ser negativo")
	private String telefone;
	
	@OneToMany(mappedBy = "fornecedor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Compra> compras = new HashSet<>();
}
