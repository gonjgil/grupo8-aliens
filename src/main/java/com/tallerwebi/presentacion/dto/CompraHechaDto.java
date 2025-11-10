package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.entidades.CompraHecha;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CompraHechaDto {

    private Long id;
    private String nombreUsuario;
    private String fechaYHora;
    private Long pagoId;
    private List<ItemCompraDto> items;
    private Integer cantidadItems;
    private Double precioFinal;


    public CompraHechaDto(CompraHecha compra, List<ItemCompraDto> items) {
        this.id = compra.getId();
        this.nombreUsuario = compra.getUsuario().getNombre();
        this.fechaYHora = compra.getFechaYHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.cantidadItems = compra.getItems().size();
        this.items = items;
        this.precioFinal = compra.getPrecioFinal();
    }

    public CompraHechaDto(CompraHecha compra) {
        this.id = compra.getId();
        this.nombreUsuario = compra.getUsuario().getNombre();
        this.fechaYHora = compra.getFechaYHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.cantidadItems = compra.getItems().size();
        this.precioFinal = compra.getPrecioFinal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(String fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Long getPagoId() {
        return pagoId;
    }

    public void setPagoId(Long pagoId) {
        this.pagoId = pagoId;
    }

    public List<ItemCompraDto> getItems() {
        return items;
    }

    public void setItems(List<ItemCompraDto> items) {
        this.items = items;
    }

    public Integer getCantidadItems() {
        return cantidadItems;
    }

    public void setCantidadItems(Integer cantidadItems) {
        this.cantidadItems = cantidadItems;
    }

    public Double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(Double precioFinal) {
        this.precioFinal = precioFinal;
    }
}
