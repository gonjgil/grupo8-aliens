package com.tallerwebi.presentacion;

import java.util.HashSet;
import java.util.Objects;
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

    public ObraDto(Obra obra) {
        this.id = obra.getId();
        this.titulo = obra.getTitulo();
        this.autor = obra.getAutor();
        this.imagenUrl = obra.getImagenUrl();
        this.descripcion = obra.getDescripcion();
        this.usuariosQueDieronLike = obra.getUsuariosQueDieronLike();
        this.categorias = obra.getCategorias();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Obra toObra() {
        Obra obra = new Obra();
        obra.setId(this.id);
        obra.setTitulo(this.titulo);
        obra.setAutor(this.autor);
        obra.setImagenUrl(this.imagenUrl);
        obra.setDescripcion(this.descripcion);
        obra.setUsuariosQueDieronLike(this.usuariosQueDieronLike);
        obra.setCategorias(this.categorias);
        return obra;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ObraDto obraDto = (ObraDto) o;
        return Objects.equals(id, obraDto.id) && Objects.equals(titulo, obraDto.titulo) && Objects.equals(autor, obraDto.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, autor);
    }
}