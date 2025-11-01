package com.tallerwebi.dominio.entidades;

import javax.persistence.*;

@Entity
@Table(name = "ItemCarrito")
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "obra_id")
    private Obra obra;
    private Integer cantidad;
    private Double precioUnitario;

    public ItemCarrito() {}

    public ItemCarrito(Carrito carrito, Obra obra) {
        this.carrito = carrito;
        this.obra = obra;
        this.precioUnitario = obra.getPrecio();
        this.cantidad = 1;
    }

    public Double getSubtotal() {

        return precioUnitario * cantidad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Carrito getCarrito() { return carrito; }
    public void setCarrito(Carrito carrito) { this.carrito = carrito; }

    public Obra getObra() { return obra; }
    public void setObra(Obra obra) { this.obra = obra; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
}