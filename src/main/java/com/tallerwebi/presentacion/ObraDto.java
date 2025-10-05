package com.tallerwebi.presentacion;

import java.util.HashSet;
import java.util.Set;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.enums.Categoria;

public class ObraDto {
    private Long id;
    private String titulo;
    private String autor;
    private String imagenUrl;
    private String descripcion;
    private Set<Usuario> usuariosQueDieronLike;
    private Set<Categoria> categorias = new HashSet<>();
    private Double precio;

    public ObraDto(Obra obra) {
        this.id = obra.getId();
        this.titulo = obra.getTitulo();
        this.autor = obra.getAutor();
        this.imagenUrl = obra.getImagenUrl();
        this.descripcion = obra.getDescripcion();
        this.usuariosQueDieronLike = obra.getUsuariosQueDieronLike() != null ? obra.getUsuariosQueDieronLike() : new HashSet<>();
        this.categorias = obra.getCategorias();
        this.precio = obra.getPrecio();
    }

    public Long getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Set<Usuario> getUsuariosQueDieronLike() { return this.usuariosQueDieronLike; }
    public void setUsuariosQueDieronLike(Set<Usuario> usuariosQueDieronLike) { this.usuariosQueDieronLike = usuariosQueDieronLike; }
    public int getCantidadLikes() { 
        return this.usuariosQueDieronLike != null ? this.usuariosQueDieronLike.size() : 0; 
    }

    public Set<Categoria> getCategorias() { return categorias; }
    public void setCategorias(Set<Categoria> categorias) { this.categorias = categorias; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
}