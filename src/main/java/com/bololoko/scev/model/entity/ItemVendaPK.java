package com.bololoko.scev.model.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Embeddable
public class ItemVendaPK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ManyToOne @JoinColumn(name = "id_venda", nullable = false)
	private Venda venda;
	
	@ManyToOne @JoinColumn(name = "id_produto", nullable = false)
	private Produto produto;
	
	public ItemVendaPK() {}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		ItemVendaPK that = (ItemVendaPK) o;
		return Objects.equals(venda, that.venda) &&
				Objects.equals(produto, that.produto);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(venda, produto);
	}

}
