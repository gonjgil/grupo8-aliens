package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.entidades.CompraHecha;

import java.time.LocalDateTime;

public class CompraHechaDto {

    private Long id;
    private String nombreUsuario;
    private LocalDateTime fechaYHora;
    private Long pagoId;
    private Integer cantidadItems;
    private Double precioFinal;


    public CompraHechaDto(CompraHecha compraHecha) {
        this.id = compraHecha.getId();
        this.nombreUsuario = compraHecha.getUsuario().getNombre();
        this.fechaYHora = compraHecha.getFechaYHora();
        this.pagoId = compraHecha.getPagoId();
        this.cantidadItems = compraHecha.getItems().size();
        this.precioFinal = compraHecha.getPrecioFinal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(Double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public Long getPagoId() {
        return pagoId;
    }
    public void setPagoId(Long pagoId) {
        this.pagoId = pagoId;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public Integer getCantidadItems() {
        return cantidadItems;
    }
    public void setCantidadItems(Integer cantidadItems) {
        this.cantidadItems = cantidadItems;
    }
}
