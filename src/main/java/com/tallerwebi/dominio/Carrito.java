package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.EstadoCarrito;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Carrito")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemCarrito> items = new ArrayList<>();

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Enumerated(EnumType.STRING)
    private EstadoCarrito estado;

    public Carrito() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.estado = EstadoCarrito.ACTIVO;
    }

    public Carrito(Usuario usuario) {
        this(); // llamada al constructor vacio para inicializar fechas y estado
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
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void removerItem(Obra obra) {
        ItemCarrito itemExistente = buscarItemPorObra(obra);
   //     this.items.removeIf(item -> item.getObra().getId().equals(obra.getId()));
        if (itemExistente != null) {
            itemExistente.setCantidad(itemExistente.getCantidad() - 1);
        } else {
            items.remove(obra);
        }

        this.fechaActualizacion = LocalDateTime.now();
    }

    public void actualizarCantidadItem(Obra obra, Integer nuevaCantidad) {
        ItemCarrito item = buscarItemPorObra(obra);
        if (item != null) {
            if (nuevaCantidad <= 0) {
                removerItem(obra);
            } else {
                item.setCantidad(nuevaCantidad);
                this.fechaActualizacion = LocalDateTime.now();
            }
        }
    }

    public void limpiar() {
        this.items.clear();
        this.fechaActualizacion = LocalDateTime.now();
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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<ItemCarrito> getItems() { return items; }
    public void setItems(List<ItemCarrito> items) { this.items = items; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public EstadoCarrito getEstado() { return estado; }
    public void setEstado(EstadoCarrito estado) { this.estado = estado; }
}