package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioCarrito;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/obra")
public class ControladorObra {

    @Autowired
    private ServicioGaleria servicioGaleria;
    
    @Autowired
    private ServicioCarrito servicioCarrito;

    public ControladorObra(ServicioGaleria servicioGaleria, ServicioCarrito servicioCarrito) {
        this.servicioGaleria = servicioGaleria;
        this.servicioCarrito = servicioCarrito;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ModelAndView verObra(@PathVariable Long id, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        model.put("usuario", usuario);
        
        // Agregar cantidad de items en el carrito
        if (usuario != null) {
            Integer cantidadItems = servicioCarrito.contarItemsEnCarrito(usuario);
            model.put("cantidadItems", cantidadItems);
        } else {
            model.put("cantidadItems", 0);
        }

        ObraDto obraDto = this.servicioGaleria.obtenerPorId(id);
        model.put("obra", obraDto);
        return new ModelAndView("obra", model);
    }

    @RequestMapping(path = "{id}/dar-like", method = RequestMethod.POST)
    public ModelAndView darLike(@PathVariable Long id, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        model.put("usuario", usuario);

        this.servicioGaleria.darLike(id, usuario);
        ObraDto obraDto = this.servicioGaleria.obtenerPorId(id);
        model.put("obra", obraDto);
        return new ModelAndView("obra", model);
    }
}
