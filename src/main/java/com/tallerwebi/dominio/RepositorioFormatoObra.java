package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.Formato;

import java.util.List;

public interface RepositorioFormatoObra {
    void guardar(FormatoObra formatoObra);
    FormatoObra obtenerPorId(Long id);
    List<FormatoObra> obtenerFormatosPorObra(Long obraId);
    FormatoObra obtenerFormatoPorObraYFormato(Long obraId, Formato formato);
    void eliminar(FormatoObra formatoObra);
    List<FormatoObra> obtenerTodos();
}