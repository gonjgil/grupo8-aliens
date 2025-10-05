package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.Categoria;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ElementCollection;
import javax.persistence.CollectionTable;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Column;
import javax.persistence.FetchType;

@Entity
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false, insertable = true, updatable = true)
    private String titulo;
    @Column(length = 100, nullable = false, insertable = true, updatable = true)
    private String autor;
    @Column(length = 200, nullable = false, insertable = true, updatable = true)
    private String imagenUrl;
    @Column(length = 500, nullable = false, insertable = true, updatable = true)
    private String descripcion;
    @Column(nullable = false, insertable = true, updatable = true)
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
    public String codigo;
    
    public Obra() { }

    public Obra(Long id, String titulo, String autor, String imagenUrl, String descripcion, Set<Categoria> categorias) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;
        this.categorias = categorias;
        this.precio = 5000.0; // precio por defecto
    }

    public Obra(Long id, String titulo, String autor, String imagenUrl, String descripcion, Set<Categoria> categorias, Double precio) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;
        this.categorias = categorias;
        this.precio = precio;
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

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Set<Usuario> getUsuariosQueDieronLike() { return usuariosQueDieronLike; }
    public void setUsuariosQueDieronLike(Set<Usuario> usuariosQueDieronLike) { this.usuariosQueDieronLike = usuariosQueDieronLike; }

    public int getCantidadLikes() { return usuariosQueDieronLike.size(); }
    
    public void darLike(Usuario usuario) { this.usuariosQueDieronLike.add(usuario); }
    
    public void quitarLike(Usuario usuario) { this.usuariosQueDieronLike.remove(usuario); }

    public Set<Categoria> getCategorias() { return categorias; }
    public void agregarCategoria(Categoria categoria) { this.categorias.add(categoria); }
}