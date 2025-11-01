package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;

public interface ServicioGaleria {
    List<Obra> obtener();
    List<Obra> ordenarRandom();
    List<Obra> obtenerPorAutor(String autor);
    List<Obra> obtenerPorCategoria(Categoria categoria);
    Obra obtenerPorId(Long id);
    List<Obra> obtenerObrasParaUsuario(Usuario usuario);
}
