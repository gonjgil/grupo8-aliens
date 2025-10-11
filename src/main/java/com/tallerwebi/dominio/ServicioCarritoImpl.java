package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.EstadoCarrito;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;
import com.tallerwebi.presentacion.ObraDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Carrito obtenerOCrearCarritoParaUsuario(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
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
        repositorioCarrito.guardar(carrito);
        return true;
    }

    @Override
    public void eliminarObraDelCarrito(Usuario usuario, Long obraId) {
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
    public void vaciarCarrito(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        if (carrito != null) {
            carrito.limpiar();
            repositorioCarrito.guardar(carrito);
        }
    }

    @Override
    public Carrito obtenerCarritoConItems(Usuario usuario) {
        return repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
    }

    @Override
    public Double calcularPrecioTotalCarrito(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        if(carrito != null){
            return carrito.getTotal();
        }
        return 0.0;
    }

    @Override
    public Integer contarItemsEnCarrito(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario);
        if(carrito != null) {
          return  carrito.getCantidadTotalItems();
        }
        return 0;
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


