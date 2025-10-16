package com.tallerwebi.dominio.enums;

public enum EstadoCarrito {
    
    ACTIVO("Activo"),
    FINALIZADO("Finalizado"),
    ABANDONADO("Abandonado");
    
    private final String estado;
    
    EstadoCarrito(String estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        return estado;
    }
    
}

