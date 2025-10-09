package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.EstadoCarrito;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.presentacion.ObraDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public boolean agregarObraAlCarrito(Usuario usuario, Long obraId) throws NoExisteLaObra {
        Obra obra = repositorioObra.obtenerPorId(obraId);
        if (obra == null) {
            throw new NoExisteLaObra();
        }
        if (!repositorioObra.hayStockSuficiente(obra)) {
            return false;
        }

        Carrito carrito = obtenerOCrearCarritoParaUsuario(usuario);
        carrito.agregarItem(obra);
        repositorioObra.descontarStock(obra);
        repositorioCarrito.guardar(carrito);
        return true;
    }

    @Override
    @Transactional
    public void removerObraDelCarrito(Usuario usuario, Long obraId) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        if (carrito != null) {
            Obra obra = repositorioObra.obtenerPorId(obraId);
            if (obra != null) {
                carrito.removerItem(obra);
                repositorioObra.devolverStock(obra);
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
    public void vaciarCarrito(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        if (carrito != null) {
            carrito.limpiar();
            repositorioCarrito.guardar(carrito);
        }
    }

    @Override
    @Transactional
    public Carrito obtenerCarritoConItems(Usuario usuario) {
        return repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
    }

    @Override
    @Transactional
    public Double calcularTotalCarrito(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        return (carrito != null) ? carrito.getTotal() : 0.0;
    }

    @Override
    @Transactional
    public Integer contarItemsEnCarrito(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        return (carrito != null) ? carrito.getCantidadTotalItems() : 0;
    }


    @Override
    public Integer getCantidadTotal() {
        return 0;
    }

        @Override
        @Transactional
        public void finalizarCarrito (Usuario usuario) throws CarritoVacioException {
            Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
            if (carrito == null || carrito.getItems().isEmpty()) {
                throw new CarritoVacioException();
            }
            repositorioCarrito.actualizarEstado(carrito.getId(), EstadoCarrito.FINALIZADO);
        }

        @Override
        public List<ObraDto> obtenerObras (Usuario usuario){
            Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
            List<ObraDto> obrasEnCarrito = new ArrayList<>();

            for (ItemCarrito itemCarrito : carrito.getItems()) {
                Obra obra = itemCarrito.getObra();
                obrasEnCarrito.add(new ObraDto(obra));
            }
            return obrasEnCarrito;
    }
}


