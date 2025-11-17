package com.tallerwebi.dominio.servicioImpl;

import com.tallerwebi.dominio.entidades.Direccion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
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

        repositorioUsuario.guardar(usuario);
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
}
