package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.Formato;

import javax.persistence.*;

@Entity
@Table(name = "formato_obra")
public class FormatoObra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "obra_id", nullable = false)
    private Obra obra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Formato formato;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Boolean disponible = true;

    public FormatoObra() {}

    public FormatoObra(Obra obra, Formato formato, Double precio, Integer stock) {
        this.obra = obra;
        this.formato = formato;
        this.precio = precio;
        this.stock = stock;
        this.disponible = true;
    }

    public boolean hayStockSuficiente() {
        return this.stock != null && this.stock >= 1 && this.disponible;
    }

    public void descontarStock() {
        if (this.stock != null && this.stock > 0) {
            this.stock--;
        }
    }

    public void devolverStock() {
        if (this.stock != null) {
            this.stock++;
        } else {
            this.stock = 1;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Obra getObra() { return obra; }
    public void setObra(Obra obra) { this.obra = obra; }

    public Formato getFormato() { return formato; }
    public void setFormato(Formato formato) { this.formato = formato; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
}