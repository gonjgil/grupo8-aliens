package com.tallerwebi.dominio.entidades;

import com.tallerwebi.dominio.enums.Formato;

import javax.persistence.*;

@Entity
@Table(name = "ItemCompra")
public class ItemCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private CompraHecha compra;

    @ManyToOne
    @JoinColumn(name = "obra_id")
    private Obra obra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Formato formato;

    private Integer cantidad;
    private Double precioUnitario;

    public ItemCompra() {}


    public ItemCompra(ItemCarrito itemCarrito) {
        this.obra = itemCarrito.getObra();
        this.formato = itemCarrito.getFormato();
        this.cantidad = itemCarrito.getCantidad();
        this.precioUnitario = itemCarrito.getPrecioUnitario();
    }

    public Double getSubtotal() {

        return precioUnitario * cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompraHecha getCompra() {
        return compra;
    }

    public void setCompra(CompraHecha compra) {
        this.compra = compra;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}