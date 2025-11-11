package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.entidades.Usuario;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email);
    void guardar(Usuario usuario);
    void modificar(Usuario usuario);
}

