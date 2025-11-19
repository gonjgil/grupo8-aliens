package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.enums.Categoria;

public class CategoriaEstadisticaDto {
    Categoria categoria;
    Long cantidad;

    public CategoriaEstadisticaDto(Categoria categoria, Long cantidad) {
        this.categoria = categoria;
        this.cantidad = cantidad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
