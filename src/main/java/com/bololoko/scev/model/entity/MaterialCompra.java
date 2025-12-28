package com.bololoko.scev.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "tb_material_compra")
public class MaterialCompra {

    @EmbeddedId
    private MaterialCompraPK idMaterialCompra = new MaterialCompraPK();

    @NotNull(message = "A quantidade em MaterialCompra é obrigatoria")
    @Column(nullable = false, length = 5)
    @PositiveOrZero(message = "A quantidade de material comprado na classe MaterialCompra nao pode ser negativo")
    private int quantidade; 
    
    
    @NotNull(message = "O preco da unidade na MaterialCompra é obrigatorio")
    @Column(nullable = false, length = 10)
    @PositiveOrZero(message = "O preco do material comprado na classe MaterialCompra nao pode ser negativo")
    private BigDecimal precoUnidadeCompra; 

    @MapsId("compra") 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compra")
    private Compra compra; 	
    
    @MapsId("material") 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_material")
    private Material material; 
    
}
