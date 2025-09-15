package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.ServicioObra;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class ControladorGaleria {
    private ServicioObra servicioObra;

    public ControladorGaleria(ServicioObra servicioObra) {
        this.servicioObra = servicioObra;
    }

    @RequestMapping(path = "/galeria", method = RequestMethod.GET)
    public ModelAndView listarObras() {
        ModelMap model = new ModelMap();
        List<Obra> listaObras = servicioObra.listarObras();

        if (listaObras == null)
            listaObras = List.of();

        model.put("obras", listaObras);
        return new ModelAndView("galeria", model);
    }
}
