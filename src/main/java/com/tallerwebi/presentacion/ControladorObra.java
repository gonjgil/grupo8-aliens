package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.infraestructura.ObraDto;

@Controller
@RequestMapping("/obra")
public class ControladorObra {

    @Autowired
    private ServicioGaleria servicioGaleria;

    public ControladorObra(ServicioGaleria servicioGaleria) {
        this.servicioGaleria = servicioGaleria;
    }

    // hablar con el profe sobre esta anotacion @PathVariable
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ModelAndView verObra(@PathVariable Long id) {
        ModelMap model = new ModelMap();
        ObraDto obraDto = this.servicioGaleria.obtenerPorId(id);
        model.put("obra", obraDto);
        return new ModelAndView("obra", model);
    }
}
