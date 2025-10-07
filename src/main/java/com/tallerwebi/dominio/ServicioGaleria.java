package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.UsuarioAnonimoException;
import com.tallerwebi.presentacion.ObraDto;

public interface ServicioGaleria {
    List<Obra> obtener();
    List<Obra> ordenarRandom();
    List<Obra> obtenerPorAutor(String autor);
    List<ObraDto> obtenerPorCategoria(Categoria categoria);
    Obra obtenerPorId(Long id);
    void darLike(Obra obra, Usuario usuario);
    void quitarLike(Obra obra, Usuario usuario);
    void toggleLike(Long id, Usuario usuario) throws NoExisteLaObra, UsuarioAnonimoException;
}
