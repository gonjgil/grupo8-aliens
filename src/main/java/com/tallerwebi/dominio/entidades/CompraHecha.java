package com.tallerwebi.dominio.entidades;

import com.tallerwebi.dominio.enums.EstadoPago;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CompraHecha")
public class CompraHecha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "compra")
    private List<ItemCompra> items;

    @OneToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    private Double precioFinal;

    private Long pagoId;

    private EstadoPago estadoPago;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private LocalDateTime fechaYHora;


    public CompraHecha(Long id, Carrito carrito, Double precioFinal, Usuario usuario) {
        this.id = id;
        this.items = new ArrayList<>();
        this.carrito = carrito;
        this.precioFinal = precioFinal;
        this.fechaYHora = LocalDateTime.now();
        this.usuario = usuario;
    }

    public CompraHecha() {}

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemCompra> getItems() {
        return items;
    }

    public void setItems(List<ItemCompra> items) {
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

    public void setPrecioFinal(Double precioTotal) {
        this.precioFinal = precioTotal;
    }

    public Long getPagoId() {
        return pagoId;
    }

    public void setPagoId(Long pagoId) {
        this.pagoId = pagoId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CompraHecha that = (CompraHecha) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
