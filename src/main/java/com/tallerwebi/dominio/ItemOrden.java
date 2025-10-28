package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
@Table(name = "ItemOrden")
public class ItemOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    private OrdenCompra orden;

    @ManyToOne
    @JoinColumn(name = "obra_id")
    private Obra obra;
    private Integer cantidad;
    private Double precioUnitario;

    public ItemOrden() {}


    public ItemOrden(ItemCarrito itemCarrito) {
        this.obra = itemCarrito.getObra();
        this.cantidad = itemCarrito.getCantidad();
        this.precioUnitario = itemCarrito.getPrecioUnitario();
    }

    public Double getSubtotal() {

        return precioUnitario * cantidad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public OrdenCompra getOrden() { return orden; }
    public void setOrden(OrdenCompra orden) { this.orden = orden; }

    public Obra getObra() { return obra; }
    public void setObra(Obra obra) { this.obra = obra; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
}