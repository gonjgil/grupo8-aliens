package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Artista;
import com.tallerwebi.dominio.ServicioCloudinary;
import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.excepcion.NoExisteArtista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/perfilArtista")
public class ControladorPerfilArtista {

    private ServicioPerfilArtista servicioPerfilArtista;
    private ServicioCloudinary servicioCloudinary;

    @Autowired
    public ControladorPerfilArtista(ServicioPerfilArtista servicioPerfilArtista, ServicioCloudinary servicioCloudinary) {
        this.servicioPerfilArtista = servicioPerfilArtista;
        this.servicioCloudinary = servicioCloudinary;
    }

    // Muestra el perfil de un artista
    @GetMapping(path = "/{verArtista}")
    public ModelAndView verPerfilArtista(@PathVariable("verArtista") Long idArtista, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        try {
            PerfilArtistaDTO artista = this.servicioPerfilArtista.obtenerPerfilArtista(idArtista);
            model.put("mostrarArtista", artista); //si la clave es "mostrarArtista" se enviará el perfil del artista
            return new ModelAndView("perfil_artista", model);
            // -->Agregar lógica para saber si el usuario logueado es el dueño del perfil
        } catch (NoExisteArtista e) { //si no lo es se lanza la excepcion
            model.put("error", "perfil no encontrado"); //si la clave es error se envia el mensaje el artista no existe
            return new ModelAndView("PerfilNoExiste", model);
        }

    }

    //GET para mostrar el formulario
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoArtista() {
        return "nuevo_artista";
    }


    //POST para recibir los datos y guardar
    @PostMapping("/crear")
    //@ModelAttribute = Spring construya automáticamente el DTO a partir de los parámetros de la solicitud.
    public String crearArtista(@ModelAttribute PerfilArtistaDTO dto, @RequestParam("fotoPerfil") MultipartFile archivo) {

        if (!archivo.isEmpty()) {
            String urlImagen = servicioCloudinary.subirImagen(archivo);
            dto.setUrlFotoPerfil(urlImagen);
        }

        Artista artista = servicioPerfilArtista.crearPerfilArtista(dto);
        return "redirect:/perfilArtista/" + artista.getId();

    }
}















