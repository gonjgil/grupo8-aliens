package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfilArtista;
import com.tallerwebi.dominio.entidades.Artista;
import com.tallerwebi.dominio.entidades.Direccion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class ControladorUsuario {

    private final ServicioUsuario servicioUsuario;
    private final ServicioPerfilArtista servicioPerfilArtista;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario, ServicioPerfilArtista servicioPerfilArtista) {
        this.servicioUsuario = servicioUsuario;
        this.servicioPerfilArtista = servicioPerfilArtista;
    }

    @GetMapping
    public ModelAndView verPerfilDeUsuario(HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null)
            return new ModelAndView("redirect:/login");

        ModelAndView mav = new ModelAndView("perfil_usuario");
        Artista artistaUsuario = servicioPerfilArtista.obtenerArtistaPorUsuario(usuario);
        mav.addObject("artistaUsuario", artistaUsuario);
        mav.addObject("usuario", usuario);

        return mav;
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

    @GetMapping("/direccion")
    public ModelAndView crearDireccion(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Direccion direccion = new Direccion();

        ModelAndView mav = new ModelAndView("crear-editar_direccion");
        mav.addObject("usuario", usuario);
        mav.addObject("direccion", direccion);
        return mav;
    }

    @GetMapping("/direccion/{id}")
    public ModelAndView editarDireccion(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Direccion direccion = servicioUsuario.buscarDireccionDelUsuario(usuario, id);
        if (direccion == null) {
            return new ModelAndView("redirect:/usuario");
        }

        ModelAndView mav = new ModelAndView("crear-editar_direccion");
        mav.addObject("usuario", usuario);
        mav.addObject("direccion", direccion);
        return mav;
    }


    @PostMapping("/direccion")
    @Transactional
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

    @GetMapping("/info")
    public ModelAndView verInformacionPersonal(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null)
            return new ModelAndView("redirect:/login");

        ModelAndView mav = new ModelAndView("usuario_info");
        mav.addObject("usuario", usuario);

        return mav;
    }

    @GetMapping("/configuracion")
    public ModelAndView configurarCuenta(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null)
            return new ModelAndView("redirect:/login");

        ModelAndView mav = new ModelAndView("usuario_configuracion");
        mav.addObject("usuario", usuario);

        return mav;
    }

    @PostMapping("/cambiar-password")
    public ModelAndView cambiarPassword(@RequestParam String passwordActual,
                                        @RequestParam String nuevoPassword,
                                        @RequestParam String confirmarPassword,
                                        HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null)
            return new ModelAndView("redirect:/login");

        ModelAndView mav = new ModelAndView("usuario_configuracion");
        mav.addObject("usuario", usuario);

        if (!nuevoPassword.equals(confirmarPassword)) {
            mav.addObject("error", "Las contraseñas no coinciden.");
            return mav;
        }

        try {
            servicioUsuario.cambiarPassword(usuario, passwordActual, nuevoPassword);
            mav.addObject("exito", "La contraseña fue actualizada correctamente.");
        } catch (Exception e) {
            mav.addObject("error", e.getMessage());
        }

        return mav;
    }

    @PostMapping("/eliminar-cuenta")
    public ModelAndView eliminarCuenta(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null)
            return new ModelAndView("redirect:/login");

        servicioUsuario.eliminarCuenta(usuario);
        session.invalidate();

        return new ModelAndView("redirect:/");
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuarioForm,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        Usuario usuarioActualizado = servicioUsuario.actualizarDatosUsuario(usuario, usuarioForm);

        session.setAttribute("usuarioLogueado", usuarioActualizado);
        redirectAttributes.addFlashAttribute("mensajeExito", "Perfil actualizado con éxito!");

        return "redirect:/usuario/info";
    }

}
