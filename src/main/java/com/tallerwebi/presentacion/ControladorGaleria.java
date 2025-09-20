package com.tallerwebi.presentacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.excepcion.NoHayObrasExistentes;
import com.tallerwebi.infraestructura.ObraDto;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/galeria")
// PREGUNTAR como hacer para definir distintas vistas y esto sirva tanto para el
// home como para las diferentes galerias?
public class ControladorGaleria {

    private List<ObraDto> obras;

    @Autowired
    private ServicioGaleria servicioGaleria;

    public ControladorGaleria(ServicioGaleria servicioGaleria) {
        this.obras = new ArrayList<>();
        this.servicioGaleria = servicioGaleria;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView mostrarGaleria() {

        ModelMap model = new ModelMap();

        try {
            List<ObraDto> obrasDto = this.servicioGaleria.obtener();
            model.put("obras", obrasDto);
            model.put("exito", "Hay obras.");
        } catch (NoHayObrasExistentes e) {
            model.put("obras", new ArrayList<>());
            model.put("error", "No hay obras.");
        }
        
        model.put("randomObras", servicioGaleria.ordenarRandom());
        model.put("autorObras", servicioGaleria.obtenerPorAutor("autorRandom"));
        model.put("temaObras", servicioGaleria.obtenerPorCategoria("temaRandom"));

        return new ModelAndView("galeria", model);
    }
}
