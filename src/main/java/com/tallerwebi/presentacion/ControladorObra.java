package com.tallerwebi.presentacion;

import java.util.List;

import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioLike;
import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dto.ObraDto;

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
    private ServicioCarrito servicioCarrito;
    private ServicioLike servicioLike;
    private ServicioPerfilArtista servicioPerfilArtista;
    
    @Autowired
    public ControladorObra(ServicioGaleria servicioGaleria, ServicioLike servicioLike, ServicioCarrito servicioCarrito, ServicioPerfilArtista servicioPerfilArtista) {
        this.servicioGaleria = servicioGaleria;
        this.servicioLike = servicioLike;
        this.servicioCarrito = servicioCarrito;
        this.servicioPerfilArtista = servicioPerfilArtista;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ModelAndView verObra(@PathVariable Long id, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        model.put("usuario", usuario);

        if (usuario != null) {
            Artista artistaUsuario = servicioPerfilArtista.obtenerArtistaPorUsuario(usuario);
            model.put("artistaUsuario", artistaUsuario);
        }

        try {
            Obra obra = servicioGaleria.obtenerPorId(id);

            Integer cantidad = servicioCarrito.obtenerCantidadDeItemPorId(usuario, obra);
            model.put("cantidad", cantidad);

            ObraDto obraDto = new ObraDto(obra);
            model.put("obra", obraDto);

            return new ModelAndView("obra", model);
        } catch (Exception e) {
            model.put("error", "No existe la obra solicitada.");
            return new ModelAndView("redirect:/galeria", model);
        }
    }
}
