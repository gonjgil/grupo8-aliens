package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.enums.Categoria;

public interface RepositorioObra {
    void guardar(Obra obra);
    List<Obra> obtenerTodas();
    List<Obra> obtenerPorAutor(String autor);
    List<Obra> obtenerPorCategoria(Categoria categoria);
    Obra obtenerPorId(Long id);
    boolean hayStockSuficiente(Obra obra);
    void descontarStock(Obra obra);
    void devolverStock(Obra obra);
}