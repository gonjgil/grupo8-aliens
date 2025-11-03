package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.Formato;

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
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Formato formato;
    
    private Integer cantidad;
    private Double precioUnitario;

    public ItemCarrito() {}

    public ItemCarrito(Carrito carrito, Obra obra, Formato formato, Double precioUnitario) {
        this.carrito = carrito;
        this.obra = obra;
        this.formato = formato;
        this.precioUnitario = precioUnitario;
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

    public Formato getFormato() { return formato; }
    public void setFormato(Formato formato) { this.formato = formato; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
}