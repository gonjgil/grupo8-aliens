package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.entidades.Obra;

public class ObraDtoWrapper {
    ObraDto obra;
    Long cantidad;

    public ObraDtoWrapper(Obra obra, Long cantidad) {
        this.obra = new ObraDto(obra);
        this.cantidad = cantidad;
    }

    public ObraDto getObra() {
        return obra;
    }

    public void setObra(ObraDto obra) {
        this.obra = obra;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
