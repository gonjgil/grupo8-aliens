package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.UsuarioAnonimoException;

public interface ServicioGaleria {
    List<Obra> obtener();
    List<Obra> ordenarRandom();
    List<Obra> obtenerPorAutor(String autor);
    List<Obra> obtenerPorCategoria(Categoria categoria);
    Obra obtenerPorId(Long id);
    void darLike(Long obra, Usuario usuario);
    void quitarLike(Long obra, Usuario usuario);
    void toggleLike(Long id, Usuario usuario) throws NoExisteLaObra, UsuarioAnonimoException;
}
