package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.enums.TipoImagen;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.dominio.excepcion.NoExisteFormatoObra;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
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
    private ServicioFormatoObra servicioFormatoObra;

    @Autowired
    public ControladorObra(ServicioGaleria servicioGaleria, ServicioCarrito servicioCarrito, ServicioPerfilArtista servicioPerfilArtista, ServicioCloudinary servicioCloudinary, ServicioFormatoObra servicioFormatoObra) {
        this.servicioGaleria = servicioGaleria;
        this.servicioCarrito = servicioCarrito;
        this.servicioPerfilArtista = servicioPerfilArtista;
        this.servicioCloudinary = servicioCloudinary;
        this.servicioFormatoObra = servicioFormatoObra;
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

    @RequestMapping(path = "/obra/{obraId}/agregar-formato", method = RequestMethod.POST)
    public String agregarFormato(@PathVariable Long obraId, @RequestParam Formato formato, @RequestParam Double precio, @RequestParam Integer stock) {
        try {
            Obra obra = servicioGaleria.obtenerPorId(obraId);
            servicioFormatoObra.crearFormato(obraId, formato, precio, stock);
            return "redirect:/obra/" + obraId;
        } catch (NoExisteLaObra e) {
            return "redirect:/galeria";
        }
    }

    @RequestMapping(path = "/obra/{obraId}/eliminar-formato", method = RequestMethod.POST)
    public String eliminarFormato(@PathVariable Long obraId, @RequestParam Long formatoId) {
        try {
            Obra obra = servicioGaleria.obtenerPorId(obraId);
            servicioFormatoObra.eliminarFormato(formatoId);
            return "redirect:/obra/" + obraId;
        } catch (NoExisteLaObra | NoExisteFormatoObra e) {
            return "redirect:/galeria";
        }
    }

    @RequestMapping(path = "/obra/{obraId}/actualizar-precio", method = RequestMethod.POST)
    public String actualizarPrecio(@PathVariable Long obraId, @RequestParam Long formatoId, @RequestParam Double nuevoPrecio) {
        try {
            Obra obra = servicioGaleria.obtenerPorId(obraId);
            servicioFormatoObra.actualizarPrecio(formatoId, nuevoPrecio);
            return "redirect:/obra/" + obraId;
        } catch (NoExisteFormatoObra e) {
            return "redirect:/galeria";
        }
    }

    @RequestMapping(path = "/obra/{obraId}/actualizar-stock", method = RequestMethod.POST)
    public String actualizarStock(@PathVariable Long obraId, @RequestParam Long formatoId, @RequestParam Integer nuevoStock) {
        try {
            servicioFormatoObra.actualizarStock(formatoId, nuevoStock);
            return "redirect:/obra/" + obraId;
        } catch (NoExisteFormatoObra e) {
            return "redirect:/galeria";
        }
    }
}
