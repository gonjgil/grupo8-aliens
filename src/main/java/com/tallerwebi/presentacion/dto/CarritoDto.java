package com.tallerwebi.presentacion.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CarritoDto {
    private Long id;
    private Long usuarioId;
    private List<ItemCarritoDto> items;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String estado;
    private Double total;
    private Integer cantidadTotalItems;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public List<ItemCarritoDto> getItems() { return items; }
    public void setItems(List<ItemCarritoDto> items) { this.items = items; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public Integer getCantidadTotalItems() { return cantidadTotalItems; }
    public void setCantidadTotalItems(Integer cantidadTotalItems) { this.cantidadTotalItems = cantidadTotalItems; }
}