package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;
import com.tallerwebi.presentacion.ItemCarritoDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioCarrito")
@Transactional
public class ServicioCarritoImpl implements ServicioCarrito {

    private final RepositorioCarrito repositorioCarrito;
    private final RepositorioObra repositorioObra;

    @Autowired
    public ServicioCarritoImpl(RepositorioCarrito repositorioCarrito, RepositorioObra repositorioObra) {
        this.repositorioCarrito = repositorioCarrito;
        this.repositorioObra = repositorioObra;
    }

    @Override
    public Carrito obtenerOCrearCarritoParaUsuario(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        if (carrito == null) {
            carrito = repositorioCarrito.crearCarritoParaUsuario(usuario);
        }
        return carrito;
    }

    @Override
    public boolean agregarObraAlCarrito(Usuario usuario, Long obraId) throws NoExisteLaObra, NoHayStockSuficiente {
        Obra obra = repositorioObra.obtenerPorId(obraId);
        if (obra == null) {
            throw new NoExisteLaObra();
        }
        if (!repositorioObra.hayStockSuficiente(obra)) {
            throw new NoHayStockSuficiente();
        }

        Carrito carrito = obtenerOCrearCarritoParaUsuario(usuario);
        carrito.agregarItem(obra);
        repositorioObra.descontarStock(obra);
        repositorioObra.guardar(obra);
        repositorioCarrito.guardar(carrito);
        return true;
    }

    @Override
    public void aumentarCantidadDeItem(Usuario usuario, Long obraId) throws NoHayStockSuficiente {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        Obra obra = repositorioObra.obtenerPorId(obraId);

        if (obra == null) throw new NoExisteLaObra();

        ItemCarrito item = carrito.buscarItemPorObra(obra);
        if (item != null) {
            if (item.getCantidad() + 1 > obra.getStock()) {
                throw new NoHayStockSuficiente();
            }
            item.setCantidad(item.getCantidad() + 1);
        }
        repositorioCarrito.guardar(carrito);
    }

    @Override
    public void disminuirCantidadDeItem(Usuario usuario, Long obraId) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        Obra obra = repositorioObra.obtenerPorId(obraId);

        if (obra == null) return;

        ItemCarrito item = carrito.buscarItemPorObra(obra);
        if (item != null) {
            item.setCantidad(item.getCantidad() - 1);
            if (item.getCantidad() <= 0) {
                carrito.removerItem(obra);
            }
        }
        repositorioCarrito.guardar(carrito);
    }

    @Override
    public void eliminarObraDelCarrito(Usuario usuario, Long obraId) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        if (carrito != null) {
            Obra obra = repositorioObra.obtenerPorId(obraId);
            if (obra != null) {
                carrito.removerItem(obra);
                repositorioObra.devolverStock(obra);
                repositorioObra.guardar(obra);
                repositorioCarrito.guardar(carrito);
            }
        }
    }

    @Override
    public void vaciarCarrito(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        if (carrito != null) {
            // Devolver stock de todos los items antes de limpiar
            for (ItemCarrito item : carrito.getItems()) {
                for (int i = 0; i < item.getCantidad(); i++) {
                    repositorioObra.devolverStock(item.getObra());
                }
                repositorioObra.guardar(item.getObra());
            }
            carrito.limpiar();
            repositorioCarrito.guardar(carrito);
        }
    }

    @Override
    public Carrito obtenerCarritoConItems(Usuario usuario) {
        return repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
    }

    @Override
    public Double calcularPrecioTotalCarrito(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        if (carrito != null) {
            return carrito.getTotal();
        }
        return 0.0;
    }

    @Override
    public Integer contarItemsEnCarrito(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        if (carrito != null) {
            return carrito.getCantidadTotalItems();
        }
        return 0;
    }

    @Override
    public List<Obra> obtenerObras(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        List<Obra> obrasEnCarrito = new ArrayList<>();

        for (ItemCarrito itemCarrito : carrito.getItems()) {
            obrasEnCarrito.add(itemCarrito.getObra());
        }
        return obrasEnCarrito;
    }

    @Override
    public List<ItemCarritoDto> obtenerItems(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        List<ItemCarritoDto> itemsEnCarrito = new ArrayList<>();

        for (ItemCarrito item : carrito.getItems()) {
            itemsEnCarrito.add(new ItemCarritoDto(item));
        }

        return itemsEnCarrito;
    }

}
