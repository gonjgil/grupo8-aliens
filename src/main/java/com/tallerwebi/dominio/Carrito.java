package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tallerwebi.dominio.enums.EstadoCarrito;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Carrito")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ItemCarrito> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private EstadoCarrito estado;

    public Carrito() {
        this.estado = EstadoCarrito.ACTIVO;
    }

    public Carrito(Usuario usuario) {
        this.estado = EstadoCarrito.ACTIVO;
        this.usuario = usuario;
    }

    public void agregarItem(Obra obra) {
        ItemCarrito itemExistente = buscarItemPorObra(obra);
        if (itemExistente != null) {
            itemExistente.setCantidad(itemExistente.getCantidad() + 1);
        } else {
            ItemCarrito nuevoItem = new ItemCarrito(this, obra);
            this.items.add(nuevoItem);
        }
    }

    public void removerItem(Obra obra) {
        ItemCarrito itemExistente = buscarItemPorObra(obra);
        if (itemExistente != null) {
            itemExistente.setCantidad(itemExistente.getCantidad() - 1);
            if (itemExistente.getCantidad() <= 0) {
                items.remove(itemExistente);
            } else {
                itemExistente.setCantidad(itemExistente.getCantidad());
            }
        }
    }

    public void actualizarCantidadItem(Obra obra, Integer nuevaCantidad) {
        ItemCarrito item = buscarItemPorObra(obra);
        if (item != null) {
            if (nuevaCantidad <= 0) {
                removerItem(obra);
            } else {
                item.setCantidad(nuevaCantidad);
            }
        }
    }

    public void limpiar() {
        this.items.clear();
    }

    public Double getTotal() {
        Double total = 0.0;
        for (ItemCarrito item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public Integer getCantidadTotalItems() {
        Integer totalCantidad = 0;
        for (ItemCarrito item : items) {
            totalCantidad += item.getCantidad();
        }
        return totalCantidad;
    }

    private ItemCarrito buscarItemPorObra(Obra obra) {
        for (ItemCarrito item : items) {
            if (item.getObra().getId().equals(obra.getId())) {
                return item;
            }
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    public EstadoCarrito getEstado() {
        return estado;
    }

    public void setEstado(EstadoCarrito estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Carrito))
            return false;
        Carrito other = (Carrito) obj;
        return Objects.equals(id, other.id);
    }

}
