package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.dto.DatosLogin;
import com.tallerwebi.presentacion.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin){
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping("/login")
    public ModelAndView irALogin() {

        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
    }


@RequestMapping(path = "/validar-login", method = RequestMethod.POST)
public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
    ModelMap model = new ModelMap();

    Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());

    if (usuarioBuscado != null) {

        request.getSession().setMaxInactiveInterval(3600); // 1 hora
        request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
        request.getSession().setAttribute("usuarioLogueado", usuarioBuscado);

        return new ModelAndView("redirect:/galeria");
    } else {
        model.put("error", "Usuario o clave incorrecta");
        return new ModelAndView("login", model);
    }
}

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
        return new ModelAndView("redirect:/galeria");
    }


    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") UsuarioDto usuarioDto) {
        ModelMap model = new ModelMap();

        try {
            // Conversión DTO -> Entidad
            Usuario usuario = usuarioDto.toUsuario();

            // Validación
            if (usuario.getCategoriasFavoritas() != null && usuario.getCategoriasFavoritas().size() > 3) {
                model.put("error", "Solo se pueden elegir hasta 3 categorías favoritas.");
                model.put("usuario", usuarioDto);
                model.put("categoriasDisponibles", Categoria.values());
                return new ModelAndView("nuevo-usuario", model);
            }

            // Llamada al servicio
            servicioLogin.registrar(usuario);

            return new ModelAndView("redirect:/login");

        } catch (UsuarioExistente e) {
            model.put("error", "El usuario ya existe");
        } catch (Exception e) {
            model.put("error", "Error al registrar el nuevo usuario");
        }

        // En caso de error, se recarga la vista con los datos del DTO
        model.put("usuario", usuarioDto);
        model.put("categoriasDisponibles", Categoria.values());
        return new ModelAndView("nuevo-usuario", model);
    }


    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("usuario", new UsuarioDto());
        model.put("categoriasDisponibles", Categoria.values());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/galeria");
    }
}

