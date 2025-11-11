package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;

import java.util.HashSet;
import java.util.Set;

public class UsuarioDto {

    private Long id;
    private String email;
    private String password;
    private String rol;
    private Boolean activo = false;
    private Set<Categoria> categoriasFavoritas = new HashSet<>();

    public UsuarioDto() {}

    public UsuarioDto (Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.password = usuario.getPassword();
        this.rol = usuario.getRol();
        this.activo = getActivo();
        this.categoriasFavoritas = usuario.getCategoriasFavoritas();
    }

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
    public Set<Categoria> getCategoriasFavoritas() { return categoriasFavoritas; }
    public void setCategoriasFavoritas(Set<Categoria> categoriasFavoritas) {
        this.categoriasFavoritas = categoriasFavoritas;
    }

    public Usuario toUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(this.id);
        usuario.setEmail(this.email);
        usuario.setPassword(this.password);
        usuario.setRol(this.rol);
        usuario.setActivo(this.activo);
        usuario.setCategoriasFavoritas(this.categoriasFavoritas);
        return usuario;
    }
}