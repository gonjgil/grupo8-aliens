package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioGaleria;
import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.ServicioCloudinary;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.enums.TipoImagen;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.presentacion.dto.ObraDto;
import com.tallerwebi.presentacion.dto.PerfilArtistaDTO;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/obra")
public class ControladorObra {

    @Autowired
    private ServicioGaleria servicioGaleria;
    private ServicioCarrito servicioCarrito;
    private ServicioPerfilArtista servicioPerfilArtista;
    private ServicioCloudinary servicioCloudinary;

    @Autowired
    public ControladorObra(ServicioGaleria servicioGaleria, ServicioCarrito servicioCarrito, ServicioPerfilArtista servicioPerfilArtista, ServicioCloudinary servicioCloudinary) {
        this.servicioGaleria = servicioGaleria;
        this.servicioCarrito = servicioCarrito;
        this.servicioPerfilArtista = servicioPerfilArtista;
        this.servicioCloudinary = servicioCloudinary;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ModelAndView verObra(@PathVariable Long id, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        model.put("usuario", usuario);

        if (usuario != null) {
            Artista artistaUsuario = servicioPerfilArtista.obtenerArtistaPorUsuario(usuario);
            model.put("artistaUsuario", artistaUsuario);

            if (artistaUsuario != null) {
                boolean esArtistaDuenio = artistaUsuario.getUsuario().equals(usuario);
                model.put("esArtistaDuenio", esArtistaDuenio);
            }
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

    @RequestMapping(path = "/nueva", method = RequestMethod.GET)
    public ModelAndView nuevaObra(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        model.put("usuario", usuario);

        try {
            Artista artista = servicioPerfilArtista.obtenerArtistaPorUsuario(usuario);
            PerfilArtistaDTO artistaDto = new PerfilArtistaDTO(artista);
            model.put("artista", artistaDto);
            model.put("categorias", Categoria.values());
            return new ModelAndView("nueva_obra", model);
        } catch (NoExisteArtista e) {
            model.put("error", "Debes ser un artista registrado para agregar una obra.");
            return new ModelAndView("redirect:/galeria", model);
        }
    }


    @RequestMapping(path = "/crear", method = RequestMethod.POST)
    public String crearObra(@ModelAttribute ObraDto obraDto, @RequestParam("file_obra") MultipartFile archivo, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");

        try {
            Artista artista = servicioPerfilArtista.obtenerArtistaPorUsuario(usuario);
            String urlImagen = servicioCloudinary.subirImagen(archivo, TipoImagen.OBRA);
            Obra obraCreada = servicioGaleria.guardar(obraDto.toObra(), artista, urlImagen);
            return "redirect:/obra/" + obraCreada.getId();
        } catch (NoExisteArtista e) {
            return "redirect:/galeria";
        }
    }

    @RequestMapping(path = "/obra/{id}/agregar-formato", method = RequestMethod.POST)
    public String agregarFormato(
            @PathVariable Long id,
            @RequestParam Formato formato,
            @RequestParam Double precio,
            @RequestParam Integer stock) {

        servicioGaleria.agregarFormatoObra(id, formato, precio, stock);
        return "redirect:/obra/" + id;
    }
}
