package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.entidades.Pago;
import com.tallerwebi.dominio.enums.EstadoPago;

public class PagoDto {

    private Boolean exitoso;
    private Long id;
    private EstadoPago estado;
    private String metodoPago;

    public PagoDto(Pago pago) {
        this.exitoso = pago.getExitoso();
        this.id = pago.getId();
        this.estado = pago.getEstado();
        this.metodoPago = pago.getMetodoPago();
    }

    public Boolean getExitoso() {
        return exitoso;
    }

    public void setExitoso(Boolean exitoso) {
        this.exitoso = exitoso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
