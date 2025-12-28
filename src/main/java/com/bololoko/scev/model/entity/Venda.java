package com.bololoko.scev.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "tb_venda")
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idVenda;
	
	@NotNull(message = "A data de registro da venda e obrigatorio")
	@Column(nullable = false)
	private LocalDate dataVenda;
	
	@NotNull(message = "O total da venda e obrigatorio")
	@Column(nullable = false, length = 10)
	@PositiveOrZero(message = "O valor total da venda nao pode ser negativo")
	private BigDecimal totalVenda;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_usuario")
//	private Usuario usuario;
	
	@OneToMany(mappedBy = "idItemVenda.venda", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ItemVenda> itens = new HashSet<>();

}
