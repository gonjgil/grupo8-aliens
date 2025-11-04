package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.FormatoObra;
import com.tallerwebi.dominio.enums.Formato;

public class FormatoObraDto {
    
    private Long id;
    private Formato formato;
    private Double precio;
    private Integer stock;
    private Boolean disponible;
    private String formatoNombre;

    public FormatoObraDto() {}

    public FormatoObraDto(FormatoObra formatoObra) {
        this.id = formatoObra.getId();
        this.formato = formatoObra.getFormato();
        this.precio = formatoObra.getPrecio();
        this.stock = formatoObra.getStock();
        this.disponible = formatoObra.getDisponible();
        this.formatoNombre = formatoObra.getFormato().getFormato();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Formato getFormato() { return formato; }
    public void setFormato(Formato formato) { this.formato = formato; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public String getFormatoNombre() { return formatoNombre; }
    public void setFormatoNombre(String formatoNombre) { this.formatoNombre = formatoNombre; }

    public boolean hayStock() {
        return stock != null && stock > 0 && disponible;
    }
}