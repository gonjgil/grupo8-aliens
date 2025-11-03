package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.entidades.ItemOrden;
import com.tallerwebi.dominio.entidades.OrdenCompra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.EstadoOrdenCompra;

import java.time.LocalDateTime;
import java.util.List;

public class OrdenCompraDto {

    private Long id;
    private EstadoOrdenCompra estado;
    private List<ItemOrden> items;
    private Usuario usuario;
    private LocalDateTime fechaYHora;
    private Double precioFinal;


    public OrdenCompraDto(OrdenCompra ordenCompra) {
        this.id = ordenCompra.getId();
        this.estado = ordenCompra.getEstado();
        this.items = ordenCompra.getItems();
        this.usuario = ordenCompra.getUsuario();
        this.fechaYHora = ordenCompra.getFechaYHora();
        this.precioFinal = ordenCompra.getPrecioFinal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoOrdenCompra getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrdenCompra estado) {
        this.estado = estado;
    }

    public List<ItemOrden> getItems() {
        return items;
    }

    public void setItems(List<ItemOrden> items) {
        this.items = items;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
}
