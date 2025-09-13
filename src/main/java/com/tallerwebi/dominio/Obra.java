package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class Obra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    private String dimensiones;
    private LocalDate fechaCreacion;
    private EstadoDisponibilidad estadoDisponibilidad;
    private int cantidadVistas;
    @ManyToMany
    private List<Categoria> categorias;
    @OneToMany(mappedBy="obra")
    private List<Formato> formatos;
    @OneToMany (mappedBy="obra")
    private List<Medio> medios;
    @OneToMany (mappedBy="obra")
    private List<Resenia> resenias;
    @OneToMany (mappedBy="obra")
    private List<Subasta> subastas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoDisponibilidad getEstadoDisponibilidad() {
        return estadoDisponibilidad;
    }

    public void setEstadoDisponibilidad(EstadoDisponibilidad estadoDisponibilidad) {
        this.estadoDisponibilidad = estadoDisponibilidad;
    }

    public int getCantidadVistas() {
        return cantidadVistas;
    }

    public void setCantidadVistas(int cantidadVistas) {
        this.cantidadVistas = cantidadVistas;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Formato> getFormatos() {
        return formatos;
    }

    public void setFormatos(List<Formato> formatos) {
        this.formatos = formatos;
    }

    public List<Medio> getMedios() {
        return medios;
    }

    public void setMedios(List<Medio> medios) {
        this.medios = medios;
    }

    public List<Resenia> getResenias() {
        return resenias;
    }

    public void setResenias(List<Resenia> resenias) {
        this.resenias = resenias;
    }

    public List<Subasta> getSubastas() {
        return subastas;
    }

    public void setSubastas(List<Subasta> subastas) {
        this.subastas = subastas;
    }
}
