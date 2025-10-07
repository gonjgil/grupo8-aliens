package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.Categoria;

import java.util.HashSet;
import java.util.Objects;
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
    private Double precio;

    @ManyToMany
    @JoinTable(
            name = "obra_likes",
            joinColumns = @JoinColumn(name = "obra_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> usuariosQueDieronLike = new HashSet<>();

    @ElementCollection(targetClass = Categoria.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "obra_categorias", joinColumns = @JoinColumn(name = "obra_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Set<Categoria> categorias = new HashSet<>();
    
    public Obra() { }

    public Obra(Long id, Double precio, String titulo, String autor, String imagenUrl, String descripcion, Set<Categoria> categorias) {
        this.id = id;
        this.precio = precio;
        this.titulo = titulo;
        this.autor = autor;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;
        this.categorias = categorias;
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

    public Set<Usuario> getUsuariosQueDieronLike() { return usuariosQueDieronLike; }
    public void setUsuariosQueDieronLike(Set<Usuario> usuariosQueDieronLike) { this.usuariosQueDieronLike = usuariosQueDieronLike; }

    public int getCantidadLikes() { return usuariosQueDieronLike.size(); }
    
    public void darLike(Usuario usuario) { this.usuariosQueDieronLike.add(usuario); }
    
    public void quitarLike(Usuario usuario) { this.usuariosQueDieronLike.remove(usuario); }

    public Set<Categoria> getCategorias() { return categorias; }
    public void setCategorias(Set<Categoria> categorias) { this.categorias = categorias; }

    public Double getPrecio() { return precio;  }
    public void setPrecio(Double precio) {  this.precio = precio;  }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Obra)) return false;
        Obra obra = (Obra) o;
        return Objects.equals(id, obra.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}