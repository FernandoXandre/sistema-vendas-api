package com.bololoko.scev.model.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "tb_material")
public class Material {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMaterial;
	
	@NotBlank(message = "O nome do material é obrigatorio")
	@Column(nullable = false, length = 50)
	private String nomeMaterial;
	
	@NotNull(message = "O tipo de medida do material precisa ser preenchido.")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 3)
	private UnidadeMedida tipoMedida;// KG, LT, PCT, ML...
	
	@NotNull(message = "A quantidade por unidade do material precisa ser preenchido.")
	@PositiveOrZero(message = "A quantidade por unidade deve ser maior ou igual a zero.")
	@Column(nullable = false)
	private double qtdPorUnidade;
	
	@NotNull(message = "A quantidade do material em estoque é obrigatoria")
	@Column(nullable = false, length = 5)
	@PositiveOrZero(message = "A quantidade em estoque do material nao pode ser negativo")
	private double qtdEstoque = 0;
	
	@NotNull(message = "O preco pago no material é obrigatorio")
	@Column(nullable = false, length = 10)
	@PositiveOrZero(message = "O preco pago no material nao pode ser negativo")
	private BigDecimal precoPago;
	
	@OneToMany(mappedBy = "idMaterialCompra.material", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<MaterialCompra> itensCompra = new HashSet<>();
	
}
