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
        this();
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
        return items.stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();
    }

    public Integer getCantidadTotalItems() {
        return items.stream()
                .mapToInt(ItemCarrito::getCantidad)
                .sum();
    }

    private ItemCarrito buscarItemPorObra(Obra obra) {
        return items.stream()
                .filter(item -> item.getObra().getId().equals(obra.getId()))
                .findFirst()
                .orElse(null);
    }

    // Getters y Setters
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