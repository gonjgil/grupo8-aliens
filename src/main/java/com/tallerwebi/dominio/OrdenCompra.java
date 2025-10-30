package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.EstadoOrdenCompra;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "OrdenCompra")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EstadoOrdenCompra estado;

    @OneToMany(mappedBy = "orden")
    private List<ItemOrden> items;

    @OneToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    private Double precioFinal;

    private Long pagoId;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private LocalDateTime fechaYHora;


    public OrdenCompra(Long id, Carrito carrito, Double precioFinal, Usuario usuario) {
        this.id = id;
        this.estado = EstadoOrdenCompra.PENDIENTE;
        this.items = new ArrayList<>();
        this.carrito = carrito;
        this.precioFinal = precioFinal;
        this.fechaYHora = LocalDateTime.now();
        this.usuario = usuario;
    }

    public OrdenCompra() {}

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

    public EstadoOrdenCompra getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrdenCompra estado) {
        this.estado = estado;
    }

    public List<ItemOrden> getItems() {
        return items;
    }

    public void setItems(List<ItemOrden> items) {
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
        OrdenCompra that = (OrdenCompra) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
