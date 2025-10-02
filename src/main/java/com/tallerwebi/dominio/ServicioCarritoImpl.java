package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ObraDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("servicioCarrito")

public class ServicioCarritoImpl implements ServicioCarrito {

    private final List<Obra> obras;

    private final RepositorioObra repositorioObra;

//    @Autowired
    public ServicioCarritoImpl(RepositorioObra repositorioObra) {
        this.repositorioObra = repositorioObra;
        this.obras = new ArrayList<>();
    }


    @Override
    public void agregarObraAlCarrito(Obra obra) { //debe verificar si la obra existe y si hay stock en caso de la obra sea fisica.
        Obra obraEnRepo = repositorioObra.obtenerPorId(obra.getId());

        if (obraEnRepo != null && repositorioObra.hayStock(obra.getId())) {
            repositorioObra.descontarStock(obraEnRepo); //esto deberia descontarse cuando se confirma la compra?
            obras.add(obraEnRepo);

        }

    }

    @Override
    public void eliminarObraDelCarrito(Obra obra) {
        this.obras.remove(obra); // elimina solo la primera coincidencia

    }

    @Override
    public void vaciarCarrito() {
        this.obras.clear();
    }

    @Override
    public Integer getCantidadTotal() {
        return obtenerObras().size();
    }

    @Override
    public List<ObraDto> obtenerObras(){
       List<ObraDto> obrasEnCarrito = new ArrayList<>();
        for(Obra obra : obras){
            obrasEnCarrito.add(new ObraDto(obra));
        }
        return obrasEnCarrito;
    }



}
