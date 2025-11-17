package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Direccion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class ControladorUsuario {

    private final ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping("/direcciones")
    public ModelAndView verDirecciones(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null)
            return new ModelAndView("redirect:/login");

        ModelAndView mav = new ModelAndView("direcciones");
        mav.addObject("usuario", usuario);
        mav.addObject("direcciones", usuario.getDirecciones());

        return mav;
    }

    @GetMapping({"/direccion", "/direccion/{id}"})
    public ModelAndView formularioDireccion(@PathVariable(required = false) Long idDireccion, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null)
            return new ModelAndView("redirect:/login");

        Direccion direccion;
        if (idDireccion != null)
            direccion = servicioUsuario.buscarDireccionDelUsuario(usuario,idDireccion);
        else
            direccion = new Direccion();

        ModelAndView mav = new ModelAndView("crear-editar_direccion");
        mav.addObject("usuario", usuario);
        mav.addObject("direccion", direccion);
        return mav;
    }

    @PostMapping("/direccion")
    public ModelAndView guardarDireccion(@ModelAttribute Direccion direccion, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null)
            return new ModelAndView("redirect:/login");

        servicioUsuario.guardarOEditarDireccion(usuario, direccion);
        session.setAttribute("usuarioLogueado", usuario);

        return new ModelAndView("redirect:/usuario/direcciones");
    }


    @PostMapping("/direccion/eliminar/{id}")
    public ModelAndView eliminarDireccion(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null)
            return new ModelAndView("redirect:/login");

        servicioUsuario.eliminarDireccion(usuario, id);
        session.setAttribute("usuarioLogueado", usuario);

        return new ModelAndView("redirect:/usuario/direcciones");
    }

    @PostMapping("/direccion/{id}/predeterminada")
    public ModelAndView marcarComoPredeterminada(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null)
            return new ModelAndView("redirect:/login");

        servicioUsuario.marcarDireccionPredeterminada(usuario, id);
        session.setAttribute("usuarioLogueado", usuario);

        return new ModelAndView("redirect:/usuario/direcciones");
    }

}
