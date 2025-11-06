package com.tallerwebi.dominio.repositorios;

import java.util.List;

import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.enums.Categoria;

public interface RepositorioObra {
    Obra guardar(Obra obra);
    List<Obra> obtenerTodas();
    List<Obra> obtenerPorAutor(String autor);
    List<Obra> obtenerPorCategoria(Categoria categoria);
    Obra obtenerPorId(Long id);
    //List<Obra> buscarPorTitulo(String titulo);
    List<Obra> obtenerPorRangoDePrecio(Double precioMin, Double precioMax);
    //List<Obra> buscarPorDescripcion(String descripcion);
    List<Obra> buscarPorString(String palabraBuscada);
}