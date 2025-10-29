package com.tallerwebi.dominio.enums;

public enum EstadoOrdenCompra {

    PENDIENTE ("Pendiente"),
    RECHAZADA("Rechazada"),
    ACEPTADA("Aceptada");

    private String descripcion;

    EstadoOrdenCompra(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {}
}
