package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.EstadoPago;

import java.util.List;

public interface RepositorioCompraHecha {
    CompraHecha guardar(CompraHecha compraHecha);
    void eliminar(CompraHecha compraHecha);
    CompraHecha obtenerPorId(Long id);
    List<CompraHecha> obtenerTodasPorUsuario(Usuario usuario);
    List<CompraHecha> obtenerTodas();
    void actualizarEstado(Long id, EstadoPago estado);
}
