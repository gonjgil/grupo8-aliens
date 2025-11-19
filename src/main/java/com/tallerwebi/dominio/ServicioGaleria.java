package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.presentacion.dto.ObraDto;

public interface ServicioGaleria {
    List<Obra> obtener();
    List<Obra> ordenarRandom();
    List<Obra> obtenerPorAutor(Artista artista);
    List<Obra> obtenerPorCategoria(Categoria categoria);
    Obra obtenerPorId(Long id);
    List<Obra> obtenerObrasParaUsuario(Usuario usuario);
    Obra guardar(Obra obra, Artista artista, String urlImagen);
    void actualizarObra(Long idObra, ObraDto dto, List<String> categoriasSeleccionadas, String urlImagen) throws NoExisteLaObra;
    void eliminarObra(Obra obra);
    Integer obtenerLikesObra(Obra obra);
}
