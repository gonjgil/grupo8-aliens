package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ItemCarrito;

public class ItemCarritoDto {
    private Long id;
    private Long obraId;
    private String obraTitulo;
    private String obraAutor;
    private String obraImagenUrl;
    private Double obraPrecio;
    private Integer cantidad;
    private Double subtotal;

    public ItemCarritoDto(ItemCarrito item) {
        this.id = item.getId();
        this.obraId = item.getObra().getId();
        this.obraTitulo = item.getObra().getTitulo();
        this.obraAutor = item.getObra().getAutor();
        this.obraImagenUrl = item.getObra().getImagenUrl();
        this.obraPrecio = item.getPrecioUnitario();
        this.cantidad = item.getCantidad();
        this.subtotal = item.getSubtotal();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getObraId() { return obraId; }
    public void setObraId(Long obraId) { this.obraId = obraId; }

    public String getObraTitulo() { return obraTitulo; }
    public void setObraTitulo(String obraTitulo) { this.obraTitulo = obraTitulo; }

    public String getObraAutor() { return obraAutor; }
    public void setObraAutor(String obraAutor) { this.obraAutor = obraAutor; }

    public String getObraImagenUrl() { return obraImagenUrl; }
    public void setObraImagenUrl(String obraImagenUrl) { this.obraImagenUrl = obraImagenUrl; }

    public Double getObraPrecio() { return obraPrecio; }
    public void setObraPrecio(Double obraPrecio) { this.obraPrecio = obraPrecio; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
}