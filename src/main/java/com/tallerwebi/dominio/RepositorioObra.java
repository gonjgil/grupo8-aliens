package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioObra {
    List<Obra> obtenerTodas();
    List<Obra> obtenerPorAutor(String autor);
    List<Obra> obtenerPorCategoria(String categoria);
    Obra obtenerPorId(Long id);
    boolean hayStock(Long obraId);
    void descontarStock(Obra obra);
    void aumentarStock(Obra obra);
}