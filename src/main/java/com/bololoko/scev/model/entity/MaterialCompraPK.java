package com.bololoko.scev.model.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable 
public class MaterialCompraPK implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@ManyToOne @JoinColumn(name = "compra_id")
    private Compra compra;

    @ManyToOne @JoinColumn(name = "material_id")
    private Material material;

    public MaterialCompraPK() {}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialCompraPK that = (MaterialCompraPK) o;
        return Objects.equals(compra, that.compra) &&
               Objects.equals(material, that.material);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(compra, material);
    }

    // GETTERS E SETTERS
    
	public Compra getCompra() {
		return compra;
	}

	public Material getMaterial() {
		return material;
	}
    
    
    
}