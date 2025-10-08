package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.UsuarioAnonimoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioGaleria;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/obra")
public class ControladorObra {

    @Autowired
    private ServicioGaleria servicioGaleria;
    
    @Autowired

    public ControladorObra(ServicioGaleria servicioGaleria) {
        this.servicioGaleria = servicioGaleria;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ModelAndView verObra(@PathVariable Long id, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        model.put("usuario", usuario);
        
        try {
            Obra obra = this.servicioGaleria.obtenerPorId(id);
            ObraDto obraDto = new ObraDto(obra);
            model.put("obra", obraDto);
            return new ModelAndView("obra", model);
        } catch (Exception e) {
            model.put("error", "No existe la obra solicitada.");
            return new ModelAndView("redirect:/galeria_alt", model);
        }
    }

//    @RequestMapping(path = "{id}/dar-like", method = RequestMethod.POST)
//    public ModelAndView darLike(@PathVariable Long id, HttpServletRequest request) {
//        ModelMap model = new ModelMap();
//
//        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
//        model.put("usuario", usuario);
//
//        try {
//            this.servicioGaleria.darLike(id, usuario);
//            Obra obra = this.servicioGaleria.obtenerPorId(id);
//            ObraDto obraDto = new ObraDto(obra);
//            model.put("obra", obraDto);
//            return new ModelAndView("redirect:/obra/" + id, model);
//        } catch (UsuarioAnonimoException e) {
//            model.put("error", "Debe estar logueado para dar like.");
//            return new ModelAndView("redirect:/obra/" + id, model);
//        } catch (NoExisteLaObra e) {
//            model.put("error", "No existe la obra solicitada.");
//            return new ModelAndView("redirect:/galeria_alt", model);
//        }
//    }
//
//    @RequestMapping(path = "{id}/quitar-like", method = RequestMethod.POST)
//    public ModelAndView quitarLike(@PathVariable Long id, HttpServletRequest request) {
//        ModelMap model = new ModelMap();
//
//        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
//        model.put("usuario", usuario);
//
//        try {
//            this.servicioGaleria.quitarLike(id, usuario);
//            Obra obra = this.servicioGaleria.obtenerPorId(id);
//            ObraDto obraDto = new ObraDto(obra);
//            model.put("obra", obraDto);
//            return new ModelAndView("redirect:/obra/" + id, model);
//        } catch (UsuarioAnonimoException e) {
//            model.put("error", "Debe estar logueado para quitar like.");
//            return new ModelAndView("redirect:/obra/" + id, model);
//        } catch (NoExisteLaObra e) {
//            model.put("error", "No existe la obra solicitada.");
//            return new ModelAndView("redirect:/galeria_alt", model);
//        }
//    }

    @RequestMapping(path = "{id}/like", method = RequestMethod.POST)
    public ModelAndView toggleLike(@PathVariable Long id, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        ModelMap model = new ModelMap();
        model.put("usuario", usuario);

        try {
            this.servicioGaleria.toggleLike(id, usuario);
            Obra obra = this.servicioGaleria.obtenerPorId(id);
            model.put("obra", new ObraDto(obra));
            return new ModelAndView("redirect:/obra/" + id, model);

        } catch (UsuarioAnonimoException e) {
            model.put("error", "Debe estar logueado para dar/quitar like.");
            return new ModelAndView("redirect:/obra/" + id, model);
        } catch (NoExisteLaObra e) {
            model.put("error", "No existe la obra solicitada.");
            return new ModelAndView("redirect:/galeria", model);
        }
    }
}
