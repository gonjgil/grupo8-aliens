package com.tallerwebi.dominio.entidades;

import com.tallerwebi.dominio.enums.Categoria;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
    private String autor;
    private String imagenUrl;
    private String descripcion;
    private Integer stock;

//    @Version
//    //Cada vez que Hibernate actualiza la entidad, incrementa automáticamente el campo version. Si otro usuario modificó la misma entidad en paralelo, Hibernate lanza una OptimisticLockException.
//    private Integer version;



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

    @ManyToOne
    @JoinColumn(name = "artista")
    private Artista artista;

    @OneToMany(mappedBy = "obra", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<FormatoObra> formatos = new HashSet<>();

    public Obra() { }

    public Obra(String titulo, String autor, String imagenUrl, String descripcion, Integer stock, Set<Categoria> categorias) {
        this.titulo = titulo;
        this.autor = autor;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;
        this.stock = stock;
        this.categorias = categorias;
    }

    public Obra(String titulo, String autor, String imagenUrl, String descripcion, Integer stock, Set<Categoria> categorias, Artista artista) {
        this.titulo = titulo;
        this.autor = autor;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;
        this.stock = stock;
        this.categorias = categorias;
        this.artista = artista;
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
    public void darLike(Usuario usuario) {
        this.usuariosQueDieronLike.add(usuario);
        usuario.getObrasLikeadas().add(this);
    }
    public void quitarLike(Usuario usuario) {
        this.usuariosQueDieronLike.remove(usuario);
        usuario.getObrasLikeadas().remove(this);
    }

    public Set<Categoria> getCategorias() { return categorias; }
    public void setCategorias(Set<Categoria> categorias) { this.categorias = categorias; }
    public void agregarCategoria(Categoria categoria) { this.categorias.add(categoria); }

     public Artista getArtista() { return artista; }
     public void setArtista(Artista artista) { this.artista = artista; }

    public Set<FormatoObra> getFormatos() { return formatos; }
    public void setFormatos(Set<FormatoObra> formatos) { this.formatos = formatos; }
    public void agregarFormato(FormatoObra formato) { 
        this.formatos.add(formato); 
        formato.setObra(this);
    }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }


    public boolean hayStockSuficiente() {
    if (this.getStock() != null && this.getStock() >= 1) {
        return true;
    }
    return false;
    }

    public void descontarStock() {
        if (this.getStock() != null) {
            this.setStock(this.getStock() - 1);
        }
    }

    public void devolverStock() {
        if( this.getStock() != null) {
            this.setStock(this.getStock() + 1);
        } else {
            this.setStock(1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Obra obra = (Obra) o;
        return Objects.equals(id, obra.id);
    }
}