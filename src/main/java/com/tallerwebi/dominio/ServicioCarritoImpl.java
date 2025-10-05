package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.EstadoCarrito;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioCarrito")
public class ServicioCarritoImpl implements ServicioCarrito {

    private final RepositorioCarrito repositorioCarrito;
    private final RepositorioObra repositorioObra;

    @Autowired
    public ServicioCarritoImpl(RepositorioCarrito repositorioCarrito, RepositorioObra repositorioObra) {
        this.repositorioCarrito = repositorioCarrito;
        this.repositorioObra = repositorioObra;
    }

    @Override
    @Transactional
    public Carrito obtenerOCrearCarritoParaUsuario(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        if (carrito == null) {
            carrito = repositorioCarrito.crearCarritoParaUsuario(usuario);
        }
        return carrito;
    }

    @Override
    @Transactional
    public void agregarObraAlCarrito(Usuario usuario, Long obraId, Integer cantidad) throws NoExisteLaObra {
        Obra obra = repositorioObra.obtenerPorId(obraId);
        if (obra == null) {
            throw new NoExisteLaObra();
        }

        Carrito carrito = obtenerOCrearCarritoParaUsuario(usuario);
        carrito.agregarItem(obra, cantidad);
        repositorioCarrito.guardar(carrito);
    }

    @Override
    @Transactional
    public void removerObraDelCarrito(Usuario usuario, Long obraId) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        if (carrito != null) {
            Obra obra = repositorioObra.obtenerPorId(obraId);
            if (obra != null) {
                carrito.removerItem(obra);
                repositorioCarrito.guardar(carrito);
            }
        }
    }

    @Override
    @Transactional
    public void actualizarCantidadObra(Usuario usuario, Long obraId, Integer nuevaCantidad) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        if (carrito != null) {
            Obra obra = repositorioObra.obtenerPorId(obraId);
            if (obra != null) {
                carrito.actualizarCantidadItem(obra, nuevaCantidad);
                repositorioCarrito.guardar(carrito);
            }
        }
    }

    @Override
    @Transactional
    public void limpiarCarrito(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        if (carrito != null) {
            carrito.limpiar();
            repositorioCarrito.guardar(carrito);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Carrito obtenerCarritoConItems(Usuario usuario) {
        return repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calcularTotalCarrito(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        return (carrito != null) ? carrito.getTotal() : 0.0;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer contarItemsEnCarrito(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        return (carrito != null) ? carrito.getCantidadTotalItems() : 0;
    }

    @Override
    @Transactional
    public void finalizarCarrito(Usuario usuario) throws CarritoVacioException {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        if (carrito == null || carrito.getItems().isEmpty()) {
            throw new CarritoVacioException();
        }
        repositorioCarrito.actualizarEstado(carrito.getId(), EstadoCarrito.FINALIZADO);
    }
}