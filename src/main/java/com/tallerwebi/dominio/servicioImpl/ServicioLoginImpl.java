package com.tallerwebi.dominio.servicioImpl;

import com.tallerwebi.dominio.ServicioPassword;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;
    private ServicioPassword servicioPassword;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario, ServicioPassword servicioPassword) {
        this.repositorioUsuario = repositorioUsuario;
        this.servicioPassword = servicioPassword;

    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        Usuario usuario = repositorioUsuario.buscarUsuario(email);
        if (usuario != null && servicioPassword.verificarPassword(password, usuario.getPassword())) {
            return usuario;
        }
        return null;
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }

        // validación extra: máximo 3 categorias
        if (usuario.getCategoriasFavoritas() != null && usuario.getCategoriasFavoritas().size() > 3) {
            throw new IllegalArgumentException("Solo se pueden elegir hasta 3 categorías favoritas");
        }

        String hash = servicioPassword.hashearPassword(usuario.getPassword());
        usuario.setPassword(hash);
        repositorioUsuario.guardar(usuario);
    }

}

