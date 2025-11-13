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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public void actualizarFormatoObra(Long formatoObraId, Double nuevoPrecio, Integer nuevoStock) throws NoExisteFormatoObra {
        FormatoObra formato = repositorioFormatoObra.obtenerPorId(formatoObraId);
        if (formato == null) {
            throw new NoExisteFormatoObra();
        }
        formato.setPrecio(nuevoPrecio);
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

    @Override
    public List<Formato> obtenerFormatosFaltantesPorObra(Long obraId) {
        List<FormatoObra> formatosDeObra = repositorioFormatoObra.obtenerFormatosPorObra(obraId);

        List<Formato> formatosExistentes = formatosDeObra.stream()
                .map(FormatoObra::getFormato)
                .collect(Collectors.toList());

        return Arrays.stream(Formato.values())
                .filter(formato -> !formatosExistentes.contains(formato))
                .collect(Collectors.toList());
    }

}
