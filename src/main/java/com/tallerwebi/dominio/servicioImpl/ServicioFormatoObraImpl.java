package com.tallerwebi.dominio.servicioImpl;

import com.tallerwebi.dominio.ServicioFormatoObra;
import com.tallerwebi.dominio.entidades.FormatoObra;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.excepcion.NoExisteFormatoObra;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.repositorios.RepositorioFormatoObra;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("servicioFormatoObra")
@Transactional
public class ServicioFormatoObraImpl implements ServicioFormatoObra {

    RepositorioFormatoObra repositorioFormatoObra;
    RepositorioObra repositorioObra;

    public ServicioFormatoObraImpl(RepositorioFormatoObra repositorioFormatoObra, RepositorioObra repositorioObra) {
        this.repositorioObra = repositorioObra;
        this.repositorioFormatoObra = repositorioFormatoObra;
    }

    @Override
    public FormatoObra crearFormato(Long obraId, Formato formato, Double precio, Integer stock) throws NoExisteLaObra {
        Obra obra = repositorioObra.obtenerPorId(obraId);
        if (obra == null) {
            throw new NoExisteLaObra();
        }

        FormatoObra formatoObra = new FormatoObra(obra, formato, precio, stock);
        repositorioFormatoObra.guardar(formatoObra);
        obra.getFormatos().add(formatoObra);
        return formatoObra;
    }

    @Override
    public void eliminarFormato(Long formatoObraId) throws NoExisteFormatoObra {
        FormatoObra formato = repositorioFormatoObra.obtenerPorId(formatoObraId);
        if (formato == null) {
            throw new NoExisteFormatoObra();
        }
        repositorioFormatoObra.eliminar(formato);
        formato.getObra().getFormatos().remove(formato);
    }

    @Override
    public void actualizarPrecio(Long formatoObraId, Double nuevoPrecio) throws NoExisteFormatoObra {
        FormatoObra formato = repositorioFormatoObra.obtenerPorId(formatoObraId);
        if (formato == null) {
            throw new NoExisteFormatoObra();
        }
        formato.setPrecio(nuevoPrecio);
        repositorioFormatoObra.guardar(formato);
    }

    @Override
    public void actualizarStock(Long formatoObraId, Integer nuevoStock) throws NoExisteFormatoObra {
        FormatoObra formato = repositorioFormatoObra.obtenerPorId(formatoObraId);
        if (formato == null) {
            throw new NoExisteFormatoObra();
        }
        formato.setStock(nuevoStock);
        repositorioFormatoObra.guardar(formato);
    }

    @Override
    public FormatoObra obtenerPorId(Long formatoObraId) throws NoExisteFormatoObra {
        FormatoObra formato = repositorioFormatoObra.obtenerPorId(formatoObraId);
        if (formato == null) {
            throw new NoExisteFormatoObra();
        }
        return formato;
    }

    @Override
    public List<FormatoObra> obtenerFormatosPorObra(Long obraId) {
        return repositorioFormatoObra.obtenerFormatosPorObra(obraId);
    }

//    @Override
//    public Obra agregarFormatoObra(Long obraId, Formato formato, Double precio, Integer stock) throws NoExisteLaObra {
//        Obra obra = repositorioObra.obtenerPorId(obraId);
//        if (obra == null) {
//            throw new NoExisteLaObra();
//        }
//        FormatoObra nuevoFormato = new FormatoObra(obra, formato, precio, stock);
//        obra.agregarFormato(nuevoFormato);
//        return repositorioObra.guardar(obra);
//    }
//
//    @Override
//    public Obra eliminarFormatoObra(Long obraId, Formato formato) throws NoExisteLaObra, NoExisteFormatoObra {
//        Obra obra = repositorioObra.obtenerPorId(obraId);
//        if (obra == null) {
//            throw new NoExisteLaObra();
//        }
//
//        Optional<FormatoObra> formatoAEliminar = obra.getFormatos().stream()
//                .filter(f -> f.getFormato().equals(formato))
//                .findFirst();
//
//        if (!formatoAEliminar.isPresent()) {
//            throw new NoExisteFormatoObra("La obra no tiene el formato especificado.");
//        }
//
//        obra.getFormatos().remove(formatoAEliminar.get());
//        return repositorioObra.guardar(obra);
//    }
//
//    @Override
//    public Obra actualizarStockFormatoObra(Long obraId, Formato formato, Integer nuevoStock) {
//        Obra obra = repositorioObra.obtenerPorId(obraId);
//        if (obra == null) {
//            throw new NoExisteLaObra();
//        }
//
//        Optional<FormatoObra> formatoAActualizar = obra.getFormatos().stream()
//                .filter(f -> f.getFormato().equals(formato))
//                .findFirst();
//
//        if (!formatoAActualizar.isPresent()) {
//            throw new NoExisteFormatoObra("La obra no tiene el formato especificado.");
//        }
//
//        formatoAActualizar.get().setStock(nuevoStock);
//        return repositorioObra.guardar(obra);
//    }

}
