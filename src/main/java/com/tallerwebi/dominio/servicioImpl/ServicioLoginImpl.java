package com.tallerwebi.dominio.servicioImpl;

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

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario(String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
//        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
//        if(usuarioEncontrado != null){
//            throw new UsuarioExistente();
//        }
//        repositorioUsuario.guardar(usuario);
//    }

        Usuario usuarioEncontrado = repositorioUsuario.buscar(usuario.getEmail());
        if (usuarioEncontrado != null) {
            throw new UsuarioExistente();
        }

        // validación extra: máximo 3 categorias
        if (usuario.getCategoriasFavoritas() != null && usuario.getCategoriasFavoritas().size() > 3) {
            throw new IllegalArgumentException("Solo se pueden elegir hasta 3 categorías favoritas");
        }

        repositorioUsuario.guardar(usuario);

    }
}
