package com.tallerwebi.presentacion;

import java.util.List;

import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioLike;
import com.tallerwebi.dominio.Usuario;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/obra")
public class ControladorObra {

    @Autowired
    private ServicioGaleria servicioGaleria;
    private ServicioLike servicioLike;
    private ServicioCarrito servicioCarrito;
    
    @Autowired
    public ControladorObra(ServicioGaleria servicioGaleria, ServicioLike servicioLike, ServicioCarrito servicioCarrito) {
        this.servicioGaleria = servicioGaleria;
        this.servicioLike = servicioLike;
        this.servicioCarrito = servicioCarrito;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ModelAndView verObra(@PathVariable Long id, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        model.put("usuario", usuario);
        
        try {
            ObraDto obra = new ObraDto(this.servicioGaleria.obtenerPorId(id));
            List<FormatoObraDto> formatosDisponibles = servicioCarrito.obtenerFormatosDisponibles(id);
            
            model.put("obra", obra);
            model.put("formatosDisponibles", formatosDisponibles);
            return new ModelAndView("obra", model);
        } catch (Exception e) {
            model.put("error", "No existe la obra solicitada.");
            return new ModelAndView("redirect:/galeria_alt", model);
        }
    }
}
