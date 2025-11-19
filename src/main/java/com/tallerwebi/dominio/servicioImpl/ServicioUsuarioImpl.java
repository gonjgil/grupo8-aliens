package com.tallerwebi.dominio.servicioImpl;

import com.tallerwebi.dominio.ServicioPassword;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Direccion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.PasswordActualIncorrectoException;
import com.tallerwebi.dominio.excepcion.PasswordIdenticoException;
import com.tallerwebi.dominio.repositorios.RepositorioArtista;
import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.infraestructura.RepositorioArtistaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioArtista repositorioArtista;
    private final ServicioPassword servicioPassword;
    private final RepositorioCarrito repositorioCarrito;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario, RepositorioArtista repositorioArtista, ServicioPassword servicioPassword, RepositorioCarrito repositorioCarrito) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioArtista = repositorioArtista;
        this.servicioPassword = servicioPassword;
        this.repositorioCarrito = repositorioCarrito;
    }

    @Override
    public Direccion buscarDireccionDelUsuario(Usuario usuario, Long idDireccion) {
        return usuario.getDirecciones()
                .stream()
                .filter(d -> d.getId().equals(idDireccion))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void guardarOEditarDireccion(Usuario usuario, Direccion dirForm) {

        if (dirForm.getId() == null) {
            dirForm.setUsuario(usuario);
            usuario.agregarDireccion(dirForm);
        } else {
            Direccion original = buscarDireccionDelUsuario(usuario, dirForm.getId());
            if (original != null) {
                original.setTipoDomicilio(dirForm.getTipoDomicilio());
                original.setNombreCalle(dirForm.getNombreCalle());
                original.setAltura(dirForm.getAltura());
                original.setPiso(dirForm.getPiso());
                original.setDepartamento(dirForm.getDepartamento());
                original.setCodigoPostal(dirForm.getCodigoPostal());
                original.setCiudadBarrio(dirForm.getCiudadBarrio());
                original.setProvincia(dirForm.getProvincia());
                original.setNombreContacto(dirForm.getNombreContacto());
                original.setTelefonoContacto(dirForm.getTelefonoContacto());
            }
        }

        repositorioUsuario.modificar(usuario);
    }

    @Override
    public void eliminarDireccion(Usuario usuario, Long id) {
        Direccion direccion = buscarDireccionDelUsuario(usuario, id);
        if (direccion != null) {
            usuario.eliminarDireccion(direccion);
            repositorioUsuario.modificar(usuario);
        }
    }

    @Override
    public void marcarDireccionPredeterminada(Usuario usuario, Long id) {
        for (Direccion d : usuario.getDirecciones()) {
            if (d.getId().equals(id)) {
                d.setPredeterminada(true);
            } else {
                d.setPredeterminada(false);
            }
        }

        repositorioUsuario.modificar(usuario);
    }

    @Override
    public void cambiarPassword(Usuario usuario, String passwordActual, String nuevoPassword) throws PasswordActualIncorrectoException, PasswordIdenticoException {

        if (!servicioPassword.verificarPassword(passwordActual, usuario.getPassword())) {
            throw new PasswordActualIncorrectoException("La contraseña actual es incorrecta.");
        }

        if (servicioPassword.verificarPassword(nuevoPassword, usuario.getPassword())) {
            throw new PasswordIdenticoException("La nueva contraseña debe ser diferente a la actual.");
        }

        String passwordHasheado = servicioPassword.hashearPassword(nuevoPassword);
        usuario.setPassword(passwordHasheado);

        repositorioUsuario.guardar(usuario);
    }


    @Override
    @Transactional
    public void eliminarCuenta(Usuario usuario) {
        Artista artista = repositorioArtista.buscarArtistaPorUsuario(usuario);
        if (artista != null) {
            repositorioArtista.eliminar(artista);
        }
        repositorioCarrito.eliminarPorUsuario(usuario);
        repositorioUsuario.eliminar(usuario);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public Usuario actualizarDatosUsuario(Usuario usuarioExistente, Usuario datosFormulario) {
        usuarioExistente.setNombre(datosFormulario.getNombre());
        usuarioExistente.setEmail(datosFormulario.getEmail());
        usuarioExistente.setTelefono(datosFormulario.getTelefono());

        repositorioUsuario.guardar(usuarioExistente);

        return usuarioExistente;
    }

    @Override
    public Object obtenerDireccionesDeUsuario(String email) {
        return null;
    }

}
