package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Direccion;
import com.tallerwebi.dominio.entidades.Usuario;

public interface ServicioUsuario {
    Direccion buscarDireccionDelUsuario(Usuario usuario, Long id);
    void guardarOEditarDireccion(Usuario usuario, Direccion dirForm);
    void eliminarDireccion(Usuario usuario, Long id);
    void marcarDireccionPredeterminada(Usuario usuario, Long id);
    void cambiarPassword(Usuario usuario, String passwordActual, String nuevoPassword);
    void eliminarCuenta(Usuario usuario);
    void actualizarUsuario(Usuario usuario);
    Usuario actualizarDatosUsuario(Usuario usuario, Usuario usuarioForm);
}
