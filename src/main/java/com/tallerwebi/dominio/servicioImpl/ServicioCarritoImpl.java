package com.tallerwebi.dominio.servicioImpl;

import java.util.ArrayList;
import java.util.List;

import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.FormatoObra;
import com.tallerwebi.dominio.entidades.ItemCarrito;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;
import com.tallerwebi.dominio.repositorios.RepositorioCarrito;
import com.tallerwebi.dominio.repositorios.RepositorioFormatoObra;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.presentacion.dto.FormatoObraDto;
import com.tallerwebi.presentacion.dto.ItemCarritoDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioCarrito")
@Transactional
public class ServicioCarritoImpl implements ServicioCarrito {

    private final RepositorioCarrito repositorioCarrito;
    private final RepositorioObra repositorioObra;
    private final RepositorioFormatoObra repositorioFormatoObra;

    @Autowired
    public ServicioCarritoImpl(RepositorioCarrito repositorioCarrito, RepositorioObra repositorioObra,
                               RepositorioFormatoObra repositorioFormatoObra) {
        this.repositorioCarrito = repositorioCarrito;
        this.repositorioObra = repositorioObra;
        this.repositorioFormatoObra = repositorioFormatoObra;
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
    public boolean agregarObraAlCarrito(Usuario usuario, Long obraId, Formato formato) throws NoExisteLaObra, NoHayStockSuficiente {
        Obra obra = repositorioObra.obtenerPorId(obraId);
        if (obra == null) {
            throw new NoExisteLaObra();
        }

        FormatoObra formatoObra = repositorioFormatoObra.obtenerFormatoPorObraYFormato(obraId, formato);
        if (formatoObra == null || !formatoObra.hayStockSuficiente()) {
            throw new NoHayStockSuficiente();
        }

        Carrito carrito = obtenerOCrearCarritoParaUsuario(usuario);

        // Verificar si ya existe un item con la misma obra y formato
        ItemCarrito itemExistente = null;
        for (ItemCarrito item : carrito.getItems()) {
            if (item.getObra().getId().equals(obraId) && item.getFormato().equals(formato)) {
                itemExistente = item;
                break;
            }
        }

        if (itemExistente != null) {
            // Incrementar cantidad del item existente
            itemExistente.setCantidad(itemExistente.getCantidad() + 1);
        } else {
            // Crear nuevo item
            ItemCarrito nuevoItem = new ItemCarrito(carrito, obra, formato, formatoObra.getPrecio());
            carrito.getItems().add(nuevoItem);
        }

        formatoObra.descontarStock();
        repositorioFormatoObra.guardar(formatoObra);
        repositorioCarrito.guardar(carrito);
        return true;
    }

        @Override
    public void disminuirCantidadDeObraDelCarrito(Usuario usuario, Long obraId, Formato formato) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        if (carrito != null) {
            FormatoObra formatoObra = repositorioFormatoObra.obtenerFormatoPorObraYFormato(obraId, formato);
            if (formatoObra != null) {
                // Buscar el item específico por obra y formato
                ItemCarrito itemADisminuir = null;
                for (ItemCarrito item : carrito.getItems()) {
                    if (item.getObra().getId().equals(obraId) && item.getFormato().equals(formato)) {
                        itemADisminuir = item;
                        break;
                    }
                }

                if (itemADisminuir != null) {
                    if (itemADisminuir.getCantidad() > 1) {
                        // Disminuir cantidad
                        itemADisminuir.setCantidad(itemADisminuir.getCantidad() - 1);
                    } else {
                        // Eliminar item si cantidad es 1
                        carrito.getItems().remove(itemADisminuir);
                    }

                    // Devolver stock al formato
                    formatoObra.devolverStock();
                    repositorioFormatoObra.guardar(formatoObra);
                    repositorioCarrito.guardar(carrito);
                }
            }
        }
    }

    @Override
    public void eliminarObraDelCarrito(Usuario usuario, Long obraId, Formato formato) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        if (carrito != null) {
            FormatoObra formatoObra = repositorioFormatoObra.obtenerFormatoPorObraYFormato(obraId, formato);
            if (formatoObra != null) {
                // Buscar y eliminar el item específico
                ItemCarrito itemAEliminar = null;
                for (ItemCarrito item : carrito.getItems()) {
                    if (item.getObra().getId().equals(obraId) && item.getFormato().equals(formato)) {
                        itemAEliminar = item;
                        break;
                    }
                }

                if (itemAEliminar != null) {
                    // Devolver todo el stock del item
                    for (int i = 0; i < itemAEliminar.getCantidad(); i++) {
                        formatoObra.devolverStock();
                    }
                    carrito.getItems().remove(itemAEliminar);
                    repositorioFormatoObra.guardar(formatoObra);
                    repositorioCarrito.guardar(carrito);
                }
            }
        }
    }

    @Override
    public void vaciarCarrito(Usuario usuario) {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        if (carrito != null) {
            // Devolver stock de todos los items antes de limpiar
            for (ItemCarrito item : carrito.getItems()) {
                FormatoObra formatoObra = repositorioFormatoObra.obtenerFormatoPorObraYFormato(
                    item.getObra().getId(), item.getFormato());
                if (formatoObra != null) {
                    for (int i = 0; i < item.getCantidad(); i++) {
                        formatoObra.devolverStock();
                    }
                    repositorioFormatoObra.guardar(formatoObra);
                }
            }
            carrito.limpiarCarrito();
            repositorioCarrito.guardar(carrito);
        }
    }

    @Override
    public Carrito obtenerCarritoConItems(Usuario usuario) throws CarritoVacioException, CarritoNoEncontradoException {
        Carrito carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        if (carrito == null) {
            throw new CarritoNoEncontradoException("Carrito no encontrado para el usuario");
        }
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new CarritoVacioException("El carrito no puede ser vacio");
        }
        return carrito;
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

        if(carrito == null) {
            carrito = repositorioCarrito.crearCarritoParaUsuario(usuario);
        }

        List<ItemCarritoDto> itemsEnCarrito = new ArrayList<>();

        for (ItemCarrito item : carrito.getItems()) {
            itemsEnCarrito.add(new ItemCarritoDto(item));
        }

        return itemsEnCarrito;
    }

    @Override
    public Integer obtenerCantidadDeItemPorId(Usuario usuario, Obra obra) {
        Carrito carrito = null;
        if (usuario != null) {
            carrito = repositorioCarrito.obtenerCarritoActivoPorUsuario(usuario.getId());
        }
        Integer cantidadDeItemPorId = 0;
        if (carrito != null) {
            ItemCarrito itemCarrito = carrito.buscarItemPorObra(obra);
            if (itemCarrito != null) {
                cantidadDeItemPorId = itemCarrito.getCantidad();
            }
        }
        return cantidadDeItemPorId;
    }

    @Override
    public List<FormatoObraDto> obtenerFormatosDisponibles(Long obraId) {
        List<FormatoObra> formatos = repositorioFormatoObra.obtenerFormatosPorObra(obraId);
        List<FormatoObraDto> formatosDto = new ArrayList<>();

        for (FormatoObra formato : formatos) {
            if (formato.getDisponible()) {
                formatosDto.add(new FormatoObraDto(formato));
            }
        }

        return formatosDto;
    }
}
