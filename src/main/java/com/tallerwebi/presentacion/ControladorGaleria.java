package com.tallerwebi.presentacion;

import java.util.ArrayList;
import java.util.List;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.enums.Categoria;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.excepcion.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorGaleria {

    @Autowired
    private ServicioGaleria servicioGaleria;
    
    @Autowired
    private ServicioCarrito servicioCarrito;

    public ControladorGaleria(ServicioGaleria servicioGaleria, ServicioCarrito servicioCarrito) {
        this.servicioGaleria = servicioGaleria;
        this.servicioCarrito = servicioCarrito;
    }

    @RequestMapping(path = "/galeria", method = RequestMethod.GET)
    public ModelAndView mostrarGaleria(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        try {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
            model.put("usuario", usuario);
            
            // Agregar cantidad de items en el carrito
            if (usuario != null) {
                Integer cantidadItems = servicioCarrito.contarItemsEnCarrito(usuario);
                model.put("cantidadItems", cantidadItems);
            } else {
                model.put("cantidadItems", 0);
            }
            
            List<ObraDto> obrasDto = this.servicioGaleria.obtener();
            
            model.put("obras", obrasDto);
            model.put("exito", "Hay obras.");
        } catch (NoHayObrasExistentes e) {
            model.put("obras", new ArrayList<>());
            model.put("error", "No hay obras.");
            return  new ModelAndView("galeria", model);
        }
        
        model.put("randomObras", servicioGaleria.ordenarRandom());
        model.put("autorObras", servicioGaleria.obtenerPorAutor("J. Doe"));
        model.put("temaObras", servicioGaleria.obtenerPorCategoria(Categoria.ABSTRACTO));

        return new ModelAndView("galeria", model);
    }
}
