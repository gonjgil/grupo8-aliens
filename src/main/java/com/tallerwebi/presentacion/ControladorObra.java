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

import java.util.List;

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

        try {
            Obra obra = servicioGaleria.obtenerPorId(id);

            if (usuario != null) {
                Artista artistaUsuario = servicioPerfilArtista.obtenerArtistaPorUsuario(usuario);
                model.put("artistaUsuario", artistaUsuario);

                if (artistaUsuario != null) {
                    boolean esArtistaDuenio = obra.getArtista().getId().equals(artistaUsuario.getId());
                    model.put("esArtistaDuenio", esArtistaDuenio);
                }
            }

            List<Formato> formatosFaltantes = servicioFormatoObra.obtenerFormatosFaltantesPorObra(id);
            model.put("formatosFaltantes", formatosFaltantes);

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

    @GetMapping("/{id}/editar")
    public ModelAndView editarObra(@PathVariable("id") Long idObra, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        model.put("usuario", usuario);

        try {
            Obra obra = servicioGaleria.obtenerPorId(idObra);
            Artista artistaUsuario = servicioPerfilArtista.obtenerArtistaPorUsuario(usuario);

            if (usuario == null || artistaUsuario == null ||
                    !obra.getArtista().getId().equals(artistaUsuario.getId())) {
                model.put("error", "No tienes permiso para editar esta obra.");
                return new ModelAndView("redirect:/galeria", model);
            }

            model.put("obra", new ObraDto(obra));
            model.put("categorias", Categoria.values());
            return new ModelAndView("editar_obra", model);

        } catch (Exception e) {
            model.put("error", "La obra solicitada no existe.");
            return new ModelAndView("redirect:/galeria", model);
        }
    }

    @PostMapping("/{id}/actualizar")
    public String actualizarObra(
            @PathVariable("id") Long idObra, @ModelAttribute ObraDto dto,
            @RequestParam(value = "categorias", required = false) List<String> categoriasSeleccionadas,
            @RequestParam(value = "file_obra", required = false) MultipartFile archivo)
            throws NoExisteLaObra {

        try {
            String imagenUrl = null;
            if (archivo != null && !archivo.isEmpty()) {
                imagenUrl = servicioCloudinary.subirImagen(archivo, TipoImagen.OBRA);
            }

            servicioGaleria.actualizarObra(idObra, dto, categoriasSeleccionadas, imagenUrl);
            return "redirect:/obra/" + idObra;

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/galeria";
        }
    }

    @RequestMapping(path = "/{obraId}/agregar-formato", method = RequestMethod.POST)
    public String agregarFormato(@PathVariable Long obraId, @RequestParam Formato formato, @RequestParam Double precio, @RequestParam Integer stock) {
        try {
            Obra obra = servicioGaleria.obtenerPorId(obraId);
            servicioFormatoObra.crearFormato(obraId, formato, precio, stock);
            return "redirect:/obra/" + obraId;
        } catch (NoExisteLaObra e) {
            return "redirect:/galeria";
        }
    }

    @RequestMapping(path = "/{obraId}/eliminar-formato", method = RequestMethod.POST)
    public String eliminarFormato(@PathVariable Long obraId, @RequestParam Long formatoId) {
        try {
            Obra obra = servicioGaleria.obtenerPorId(obraId);
            servicioFormatoObra.eliminarFormato(formatoId);
            return "redirect:/obra/" + obraId;
        } catch (NoExisteLaObra | NoExisteFormatoObra e) {
            return "redirect:/galeria";
        }
    }

    @RequestMapping(path = "/{obraId}/actualizar-formato", method = RequestMethod.POST)
    public String actualizarFormatoObra(@PathVariable Long obraId, @RequestParam Long formatoId, @RequestParam Double nuevoPrecio, @RequestParam Integer nuevoStock) {
        try {
            Obra obra = servicioGaleria.obtenerPorId(obraId);
            servicioFormatoObra.actualizarFormatoObra(formatoId, nuevoPrecio, nuevoStock);
            return "redirect:/obra/" + obraId;
        } catch (NoExisteFormatoObra e) {
            return "redirect:/galeria";
        }
    }

    @RequestMapping(path = "/{id}/eliminar", method = RequestMethod.POST)
    public String eliminarObra(@PathVariable("id") Long idObra, HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        try {
            Artista artistaUsuario = servicioPerfilArtista.obtenerArtistaPorUsuario(usuario);
            Obra obra = servicioGaleria.obtenerPorId(idObra);

            if (!obra.getArtista().getId().equals(artistaUsuario.getId())) {
                return "redirect:/galeria";
            }

            servicioGaleria.eliminarObra(obra);
            return "redirect:/galeria";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/galeria";
        }
    }

}
