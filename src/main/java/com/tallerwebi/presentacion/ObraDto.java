package com.tallerwebi.presentacion;

import java.util.HashSet;
import java.util.Set;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.enums.Categoria;

public class ObraDto {
    private Long id;
    private Double precio;
    private String titulo;
    private String autor;
    private String imagenUrl;
    private String descripcion;
    private Set<Usuario> usuariosQueDieronLike;
    private Set<Categoria> categorias;

    public ObraDto(Obra obra) {
        this.id = obra.getId();
        this.precio = obra.getPrecio();
        this.titulo = obra.getTitulo();
        this.autor = obra.getAutor();
        this.imagenUrl = obra.getImagenUrl();
        this.descripcion = obra.getDescripcion();
        this.usuariosQueDieronLike = obra.getUsuariosQueDieronLike();
        this.categorias = obra.getCategorias();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

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
    public int getCantidadLikes() { return this.usuariosQueDieronLike.size(); }

    public Set<Categoria> getCategorias() { return categorias; }
    public void setCategorias(Set<Categoria> categorias) { this.categorias = categorias; }
}