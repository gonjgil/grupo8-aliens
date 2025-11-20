package com.tallerwebi.dominio.servicioImpl;

import com.tallerwebi.dominio.ServicioComentario;
import com.tallerwebi.dominio.entidades.Comentario;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioComentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ServicioComentarioImpl implements ServicioComentario {

    @Autowired
    private RepositorioComentario repositorioComentario;

    @Autowired
    public ServicioComentarioImpl(RepositorioComentario repositorioComentario) {
        this.repositorioComentario = repositorioComentario;
    }

    @Override
    public void guardarComentario(Usuario usuario, Obra obra, String contenido) {
        Comentario comentario = new Comentario();
        comentario.setUsuario(usuario);
        comentario.setObra(obra);
        comentario.setContenido(contenido);
        comentario.setFecha(LocalDate.now());
        repositorioComentario.guardar(comentario);

    }

    @Override
    public List<Comentario> obtenerComentariosDeObra(Long obraId) {
        return repositorioComentario.obtenerPorObra(obraId);
    }
}
