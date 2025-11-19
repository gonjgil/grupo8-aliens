package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioEstadistica;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.ServicioCloudinary;
import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.enums.TipoImagen;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import com.tallerwebi.presentacion.dto.CategoriaEstadisticaDto;
import com.tallerwebi.presentacion.dto.ObraDto;
import com.tallerwebi.presentacion.dto.PerfilArtistaDTO;
import com.tallerwebi.presentacion.dto.ObraDtoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/perfilArtista")
public class ControladorPerfilArtista {

    private ServicioPerfilArtista servicioPerfilArtista;
    private ServicioCloudinary servicioCloudinary;
    private ServicioEstadistica servicioEstadistica;

    @Autowired
    public ControladorPerfilArtista(ServicioPerfilArtista servicioPerfilArtista, ServicioCloudinary servicioCloudinary, ServicioEstadistica servicioEstadistica) {
        this.servicioPerfilArtista = servicioPerfilArtista;
        this.servicioCloudinary = servicioCloudinary;
        this.servicioEstadistica = servicioEstadistica;
    }

    // Muestra el perfil de un artista
    @GetMapping(path = "/ver/{idArtista}")
    public ModelAndView verPerfilArtista(@PathVariable("idArtista") Long idArtista, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        model.put("usuario", usuario);

        if (usuario != null) {
            Artista artistaUsuario = servicioPerfilArtista.obtenerArtistaPorUsuario(usuario);
            model.put("artistaUsuario", artistaUsuario);
        }

        List<ObraDto> obrasArtista = this.servicioPerfilArtista.obtenerObrasPorArtista(idArtista);
        model.put("obras", obrasArtista);

        try {
            PerfilArtistaDTO artista = this.servicioPerfilArtista.obtenerPerfilArtista(idArtista);
            model.put("mostrarArtista", artista); //si la clave es "mostrarArtista" se enviará el perfil del artista
            //  botón de editar solo se muestra si el usuario logueado es el dueño
            boolean esDuenio = (usuario != null && artista.getUsuarioId() != null
                    && usuario.getId().equals(artista.getUsuarioId()));
            model.put("esDuenio", esDuenio);
            return new ModelAndView("perfil_artista", model);
            // -->Agregar lógica para saber si el usuario logueado es el dueño del perfil
        } catch (NoExisteArtista e) { //si no lo es se lanza la excepcion
            model.put("error", "perfil no encontrado"); //si la clave es error se envia el mensaje el artista no existe
            return new ModelAndView("redirect:/galeria", model);
        }

    }

    //GET para mostrar el formulario
    @GetMapping("/nuevo")
    public ModelAndView mostrarFormularioNuevoArtista(HttpSession  session) {
        ModelAndView mav = new ModelAndView("nuevo_artista");
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null)
            return new ModelAndView("redirect:/login");

        mav.addObject("usuario", usuario);
        return mav;
    }

    //POST para recibir los datos y guardar
    @PostMapping("/crear")
    //@ModelAttribute = Spring construya automáticamente el DTO a partir de los parámetros de la solicitud.
    public String crearArtista(@ModelAttribute PerfilArtistaDTO dto, @RequestParam("fotoPerfil") MultipartFile archivo, HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");

        if (!archivo.isEmpty()) {
            String urlImagen = servicioCloudinary.subirImagen(archivo, TipoImagen.PERFIL_ARTISTA);
            dto.setUrlFotoPerfil(urlImagen);
        }

        Artista artista = servicioPerfilArtista.crearPerfilArtista(dto, usuario);
        return "redirect:/perfilArtista/ver/" + artista.getId();

    }

    //Formulario de edicion con datos actuales
    @GetMapping("/ver/{id}/editar")
    public ModelAndView mostrarFormularioDeEdicion(@PathVariable("id") Long idArtista, HttpServletRequest request){
        ModelMap model = new ModelMap();

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        model.put("usuario", usuario);

        try{
            PerfilArtistaDTO artista = this.servicioPerfilArtista.obtenerPerfilArtista(idArtista);

            if(usuario == null || !usuario.getId().equals(artista.getUsuarioId())){
                model.put("error", "No tienes permiso para editar este perfil.");
                return new ModelAndView("redirect:/galeria", model);
            }

            model.put("artista", artista);
            return new ModelAndView("editar_artista", model);
        } catch (NoExisteArtista e) {
            model.put("error", "perfil no encontrado"); //si la clave es error se envia el mensaje el artista no existe
            return new ModelAndView("redirect:/galeria", model);
        }
    }

    @PostMapping("ver/{id}/actualizar")
    public String actualizarPerfil(@PathVariable("id") Long idArtista, @ModelAttribute PerfilArtistaDTO dto, @RequestParam("fotoPerfil") MultipartFile archivo) throws NoExisteArtista{

        if (!archivo.isEmpty()) {
            String urlImagen = servicioCloudinary.subirImagen(archivo, TipoImagen.PERFIL_ARTISTA);
            dto.setUrlFotoPerfil(urlImagen);
        }else{
            PerfilArtistaDTO artistaActual = this.servicioPerfilArtista.obtenerPerfilArtista(idArtista);
            dto.setUrlFotoPerfil(artistaActual.getUrlFotoPerfil());
        }

        dto.setId(idArtista);
        servicioPerfilArtista.actualizarPerfilArtista(dto);

        return "redirect:/perfilArtista/ver/" + idArtista;
    }

    @RequestMapping(path = "ver/{id}/estadisticas", method = RequestMethod.GET)
    public ModelAndView mostrarEstadisticas(@PathVariable("id") Long idArtista, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        model.put("usuario", usuario);

        try{
            PerfilArtistaDTO artista = this.servicioPerfilArtista.obtenerPerfilArtista(idArtista);

            if(usuario == null || !usuario.getId().equals(artista.getUsuarioId())){
                model.put("error", "Acceso denegado");
                return new ModelAndView("redirect:/galeria", model);
            }

            model.put("artista", artista);

            List<ObraDtoWrapper> obrasMasVendidas = parseObraDtoWrapper(servicioEstadistica.obtenerMasVendidasArtista(artista.toArtista()));
            List<ObraDtoWrapper> obrasMasLikeadas = parseObraDtoWrapper(servicioEstadistica.obtenerMasLikeadasArtista(artista.toArtista()));
            List<CategoriaEstadisticaDto> categoriasMasVendidas = parseCategoriaEstadisticaDto(servicioEstadistica.obtenerTresCategoriasMasVendidasArtista(artista.toArtista()));
            List<CategoriaEstadisticaDto> categoriasMasLikeadas = parseCategoriaEstadisticaDto(servicioEstadistica.obtenerTresCategoriasMasLikeadasArtista(artista.toArtista()));
            List<ObraDto> trendingVendidas = parseObraDto(servicioEstadistica.obtenerTrendingVentasArtista(artista.toArtista()));
            List<ObraDto> trendingLikeadas = parseObraDto(servicioEstadistica.obtenerTrendingLikesArtista(artista.toArtista()));

            model.put("masVendidas", obrasMasVendidas);
            model.put("masLikeadas", obrasMasLikeadas);
            model.put("cat_masVendidas", categoriasMasVendidas);
            model.put("cat_masLikeadas", categoriasMasLikeadas);
            model.put("trendVendidas", trendingVendidas);
            model.put("trendLikeadas", trendingLikeadas);
            model.put("categoriaNombres", getCategoriaNombres());

            return new ModelAndView("estadisticas_artista", model);
        } catch (NoExisteArtista e) {
            model.put("error", "perfil no encontrado"); //si la clave es error se envia el mensaje el artista no existe
            return new ModelAndView("redirect:/galeria", model);
        }
    }

    private List<ObraDtoWrapper> parseObraDtoWrapper (Map<Obra, Long> map) {
        List<ObraDtoWrapper> list = new ArrayList<>();
        for (Map.Entry<Obra, Long> entry : map.entrySet()) {
            Obra obra = entry.getKey();
            Long cantidad = entry.getValue();
            list.add(new ObraDtoWrapper(obra, cantidad));
        }
        return list;
    }

    private List<CategoriaEstadisticaDto> parseCategoriaEstadisticaDto (Map<Categoria, Long> map) {
        List<CategoriaEstadisticaDto> list = new ArrayList<>();
        for (Map.Entry<Categoria, Long> entry : map.entrySet()) {
            Categoria categoria = entry.getKey();
            Long cantidad = entry.getValue();
            list.add(new CategoriaEstadisticaDto(categoria, cantidad));
        }
        return list;
    }

    private List<ObraDto> parseObraDto (List<Obra> list) {
        List<ObraDto> lista = new ArrayList<>();
        for (Obra obra : list) {
            lista.add(new ObraDto(obra));
        }
        return lista;
    }

    private Map<String, String> getCategoriaNombres() {
        Map<String, String> map = new HashMap<>();
        for (Categoria c : Categoria.values()) {
            map.put(c.name(), c.getCategoria());
        }
        return map;
    }
}















