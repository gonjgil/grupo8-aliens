package com.tallerwebi.presentacion;

import java.util.ArrayList;
import java.util.List;

import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;

import com.tallerwebi.presentacion.dto.ObraDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.excepcion.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorGaleria {

    @Autowired
    private ServicioGaleria servicioGaleria;
    private ServicioPerfilArtista servicioPerfilArtista;

    @Autowired
    public ControladorGaleria(ServicioGaleria servicioGaleria, ServicioPerfilArtista servicioPerfilArtista) {
        this.servicioGaleria = servicioGaleria;
        this.servicioPerfilArtista = servicioPerfilArtista;
    }

    @RequestMapping(path = "/galeria", method = RequestMethod.GET)
    public ModelAndView mostrarGaleria(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        try {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
            model.put("usuario", usuario);

            //buscar el artista del usuario logueado (si tiene)
            if (usuario != null) {
                try {
                    Artista artistaUsuario = servicioPerfilArtista.obtenerArtistaPorUsuario(usuario);
                    model.put("artistaUsuario", artistaUsuario);
                } catch (NoExisteArtista e) {
                    // El usuario no es un artista, no hacer nada
                }
            }

            if(usuario == null) {
                model.put("obrasSpotlight", this.servicioGaleria.ordenarRandom());
                model.put("exito", "Hay obras.");
                return  new ModelAndView("galeria", model);
            }

            List<Obra> obrasSpotlight = this.servicioGaleria.obtenerObrasParaUsuario(usuario);
            List<ObraDto> obrasDto = new ArrayList<>();
            for (Obra obra : obrasSpotlight) {
                obrasDto.add(new ObraDto(obra));
            }

            model.put("obrasSpotlight", obrasDto);
            model.put("exito", "Hay obras.");
        } catch (NoHayObrasExistentes e) {
            model.put("obrasSpotlight", new ArrayList<>());
            model.put("error", "No hay obras.");
            return  new ModelAndView("galeria", model);
        }
        
        //model.put("randomObras", servicioGaleria.ordenarRandom());
        //model.put("autorObras", servicioGaleria.obtenerPorAutor("J. Doe"));
        //model.put("temaObras", servicioGaleria.obtenerPorCategoria(Categoria.ABSTRACTO));

        return new ModelAndView("galeria", model);
    }

}
