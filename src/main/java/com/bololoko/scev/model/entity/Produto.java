package com.bololoko.scev.model.entity;

import java.math.BigDecimal;
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
@Table(name = "tb_produto")
public class Produto {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;	
    
    @NotBlank(message = "O nome do produto e obrigatorio")
    @Column(nullable = false, length = 50)
    private String nomeProduto;
    
    private String sabor;
    
    @NotNull(message = "O preco do produto e obrigatorio")
    @Column(nullable = false, length = 50)
    @PositiveOrZero(message = "O preco do produto nao pode ser negativo")
    private BigDecimal precoUnidade;
    
    @OneToMany(mappedBy = "idItemVenda.produto", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemVenda> itensVenda = new HashSet<>();
    
}
