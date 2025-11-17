package com.tallerwebi.dominio.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tallerwebi.dominio.enums.Categoria;

import javax.persistence.*;
import java.util.*;

@Entity
@JsonIgnoreProperties({"obrasLikeadas"})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String rol;
    private Boolean activo = false;
    private Long telefono;
    private String nombre;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones = new ArrayList<>();


    @ManyToMany(mappedBy = "usuariosQueDieronLike", fetch = FetchType.LAZY)
    private Set<Obra> obrasLikeadas = new HashSet<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getTelefono() {
        return telefono;
    }
    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Obra>
    getObrasLikeadas() { return obrasLikeadas; }
    public void setObrasLikeadas(Set<Obra> obrasLikeadas) { this.obrasLikeadas = obrasLikeadas; }

    public void activar() {
        activo = true;
    }

    @ElementCollection(targetClass = Categoria.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "usuario_categorias_favoritas", joinColumns = @JoinColumn(name = "usuario_id"))

    @Column(name = "categoria")
    private Set<Categoria> categoriasFavoritas = new HashSet<>();

    public Set<Categoria> getCategoriasFavoritas() {
        return categoriasFavoritas;
    }

    public void setCategoriasFavoritas(Set<Categoria> categoriasFavoritas) {
        this.categoriasFavoritas = categoriasFavoritas;
    }

    public List<Direccion> getDirecciones() {
        return this.direcciones;
    }

    public void agregarDireccion(Direccion direccion) {
        if (this.direcciones.isEmpty())
            direccion.setPredeterminada(true);
        direccion.setUsuario(this);
        this.direcciones.add(direccion);
    }

    public void eliminarDireccion(Direccion direccion) {
        direccion.setUsuario(null);
        this.direcciones.remove(direccion);
    }

    public Direccion getDireccionPredeterminada() {
        if (direcciones == null || direcciones.isEmpty()) return null;
        Direccion predeterminada = null;
        for (Direccion direccion : direcciones) {
            if (direccion.getPredeterminada())
                predeterminada = direccion;
        }
        return predeterminada;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
