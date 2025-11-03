package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.entidades.Usuario;

import java.util.List;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    List<Usuario> obtenerTodos();
    void modificar(Usuario usuario);
}

