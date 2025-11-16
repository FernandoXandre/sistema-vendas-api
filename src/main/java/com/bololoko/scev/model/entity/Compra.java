package com.bololoko.scev.model.entity;

import java.math.BigDecimal;
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
@Table(name = "tb_compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_compra; 
    
    private Instant data_compra; 
    private BigDecimal total_compra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor; 
    
    @OneToMany(mappedBy = "id_material_compra.compra", fetch = FetchType.LAZY)
    private Set<MaterialCompra> itens = new HashSet<>();
    
    // GETTERS E SETTERS 

	public Instant getData_compra() {
		return data_compra;
	}

	public void setData_compra(Instant data_compra) {
		this.data_compra = data_compra;
	}

	public BigDecimal getTotal_compra() {
		return total_compra;
	}

	public void setTotal_compra(BigDecimal total_compra) {
		this.total_compra = total_compra;
	}

	public Long getId_compra() {
		return id_compra;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public Set<MaterialCompra> getItens() {
		return itens;
	}
    
}