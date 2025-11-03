package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.entidades.OrdenCompra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.EstadoOrdenCompra;

import java.util.List;

public interface RepositorioOrdenCompra {
    void guardar(OrdenCompra ordenCompra);
    void eliminar(OrdenCompra ordenCompra);
    OrdenCompra obtenerPorId(Long id);
    List<OrdenCompra> obtenerPorEstado(EstadoOrdenCompra estado);
    List<OrdenCompra> obtenerTodasPorUsuario(Usuario usuario);
    void actualizarEstado(Long ordenId, EstadoOrdenCompra estado);
    List<OrdenCompra> obtenerTodas();
}
