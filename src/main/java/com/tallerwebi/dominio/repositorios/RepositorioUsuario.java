package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.entidades.Usuario;

import java.util.List;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email);
    void guardar(Usuario usuario);
    List<Usuario> obtenerTodos();
    void modificar(Usuario usuario);
}

