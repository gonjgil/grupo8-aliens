package com.tallerwebi.dominio;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Obra {
    @Id
    private Long id;
    private String titulo;
    private String autor;
    private String imagenUrl;
    private String descripcion;
    @ManyToMany
    @JoinTable(
            name = "obra_likes",
            joinColumns = @JoinColumn(name = "obra_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> usuariosQueDieronLike = new HashSet<>();
    
    public Obra() { }

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

    public Set<Usuario> getUsuariosQueDieronLike() { return usuariosQueDieronLike; }
    public void setUsuariosQueDieronLike(Set<Usuario> usuariosQueDieronLike) { this.usuariosQueDieronLike = usuariosQueDieronLike; }

    public int getCantidadLikes() { return usuariosQueDieronLike.size(); }
    
    public void darLike(Usuario usuario) { this.usuariosQueDieronLike.add(usuario); }
    
    public void quitarLike(Usuario usuario) { this.usuariosQueDieronLike.remove(usuario); }
}