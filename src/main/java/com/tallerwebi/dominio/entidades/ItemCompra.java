package com.tallerwebi.dominio.entidades;

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
    private Integer cantidad;
    private Double precioUnitario;

    public ItemCompra() {}


    public ItemCompra(ItemCarrito itemCarrito) {
        this.obra = itemCarrito.getObra();
        this.cantidad = itemCarrito.getCantidad();
        this.precioUnitario = itemCarrito.getPrecioUnitario();
    }

    public Double getSubtotal() {

        return precioUnitario * cantidad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CompraHecha getCompra() { return compra; }
    public void setCompra(CompraHecha orden) { this.compra = orden; }

    public Obra getObra() { return obra; }
    public void setObra(Obra obra) { this.obra = obra; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
}