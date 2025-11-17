package com.tallerwebi.dominio.entidades;

import com.tallerwebi.dominio.enums.EstadoPago;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pago {

    @Id
    private Long id;
    private Long idTransaccion;
    private Boolean exitoso;
    private EstadoPago estado;
    private String metodoPago;

    public Pago(Boolean exitoso, Long idTransaccion , EstadoPago estado, String metodoPago) {
        this.idTransaccion = idTransaccion;
        this.exitoso = exitoso;
        this.estado = estado;
        this.metodoPago = metodoPago;
    }

    public Pago() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Boolean getExitoso() {
        return exitoso;
    }

    public void setExitoso(Boolean exitoso) {
        this.exitoso = exitoso;
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
