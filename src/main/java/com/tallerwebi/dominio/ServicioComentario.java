package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Comentario;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;

import java.util.List;

public interface ServicioComentario {
    void guardarComentario(Usuario usuario, Obra obra, String contenido);
    List<Comentario> obtenerComentariosDeObra(Long obraId);
}
