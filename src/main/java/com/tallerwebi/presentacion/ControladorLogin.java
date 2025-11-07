package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.dto.DatosLogin;
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
            // Configurar la sesión para que persista
            request.getSession().setMaxInactiveInterval(3600); // 1 hora
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            request.getSession().setAttribute("usuarioLogueado", usuarioBuscado);
            return new ModelAndView("redirect:/galeria");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
        return new ModelAndView("redirect:/galeria");
    }

//    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
//    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario) {
//        ModelMap model = new ModelMap();
//        try{
//            servicioLogin.registrar(usuario);
//        } catch (UsuarioExistente e){
//            model.put("error", "El usuario ya existe");
//            return new ModelAndView("nuevo-usuario", model);
//        } catch (Exception e){
//            model.put("error", "Error al registrar el nuevo usuario");
//            return new ModelAndView("nuevo-usuario", model);
//        }
//        return new ModelAndView("redirect:/login");
//    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        try{
            // Validación backend: máximo 3 categorías
            if (usuario.getCategoriasFavoritas() != null && usuario.getCategoriasFavoritas().size() > 3) {
                model.put("error", "Solo se pueden elegir hasta 3 categorías favoritas.");
                model.put("usuario", usuario);
                model.put("categoriasDisponibles", Categoria.values());
                return new ModelAndView("nuevo-usuario", model);
            }
            servicioLogin.registrar(usuario);
        } catch (UsuarioExistente e){
            model.put("error", "El usuario ya existe");
            model.put("categoriasDisponibles", Categoria.values());
            return new ModelAndView("nuevo-usuario", model);
        } catch (Exception e){
            model.put("error", "Error al registrar el nuevo usuario");
            model.put("categoriasDisponibles", Categoria.values());
            return new ModelAndView("nuevo-usuario", model);
        }
        return new ModelAndView("redirect:/login");
    }

//    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
//    public ModelAndView nuevoUsuario() {
//        ModelMap model = new ModelMap();
//        model.put("usuario", new Usuario());
//        return new ModelAndView("nuevo-usuario", model);
//    }
    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());
        model.put("categoriasDisponibles", Categoria.values());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/galeria");
    }
}

