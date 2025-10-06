package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ControladorCarrito {

    private final ServicioCarrito servicioCarrito;
    private final ServicioLogin servicioLogin;

    @Autowired
    public ControladorCarrito(ServicioCarrito servicioCarrito, ServicioLogin servicioLogin) {
        this.servicioCarrito = servicioCarrito;
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping("/carrito")
    public ModelAndView mostrarCarrito(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        
        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Carrito carrito = servicioCarrito.obtenerCarritoConItems(usuario);
        modelo.put("carrito", carrito);
        modelo.put("total", servicioCarrito.calcularTotalCarrito(usuario));
        modelo.put("cantidadItems", servicioCarrito.contarItemsEnCarrito(usuario));

        return new ModelAndView("carrito", modelo);
    }

    @PostMapping("/carrito/agregar")
    public String agregarAlCarrito(@RequestParam Long obraId, 
                                   @RequestParam(defaultValue = "1") Integer cantidad,
                                   @RequestParam(required = false) String redirect,
                                   HttpServletRequest request,
                                   RedirectAttributes redirectAttributes) {
        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            return "redirect:/login";
        }

        try {
            servicioCarrito.agregarObraAlCarrito(usuario, obraId, cantidad);
            redirectAttributes.addFlashAttribute("mensaje", "Obra agregada al carrito exitosamente");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (NoExisteLaObra e) {
            redirectAttributes.addFlashAttribute("mensaje", "La obra no existe");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }

        // Si viene de la vista obra individual, redirigir a galería
        String referer = request.getHeader("Referer");
        if (referer != null && referer.contains("/obra/") && !referer.contains("/galeria")) {
            return "redirect:/galeria";
        }
        
        // Si se especifica un redirect, usarlo
        if (redirect != null && !redirect.isEmpty()) {
            return "redirect:" + redirect;
        }

        // Por defecto, redirigir a la obra
        return "redirect:/obra/" + obraId;
    }

    @PostMapping("/carrito/remover")
    public String removerDelCarrito(@RequestParam Long obraId,
                                    HttpServletRequest request,
                                    RedirectAttributes redirectAttributes) {
        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            return "redirect:/login";
        }

        servicioCarrito.removerObraDelCarrito(usuario, obraId);
        redirectAttributes.addFlashAttribute("mensaje", "Obra removida del carrito");
        redirectAttributes.addFlashAttribute("tipoMensaje", "info");

        return "redirect:/carrito";
    }

    @PostMapping("/carrito/actualizar")
    public String actualizarCantidad(@RequestParam Long obraId,
                                     @RequestParam Integer cantidad,
                                     HttpServletRequest request,
                                     RedirectAttributes redirectAttributes) {
        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            return "redirect:/login";
        }

        servicioCarrito.actualizarCantidadObra(usuario, obraId, cantidad);
        redirectAttributes.addFlashAttribute("mensaje", "Cantidad actualizada");
        redirectAttributes.addFlashAttribute("tipoMensaje", "success");

        return "redirect:/carrito";
    }

    @PostMapping("/carrito/limpiar")
    public String limpiarCarrito(HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            return "redirect:/login";
        }

        servicioCarrito.limpiarCarrito(usuario);
        redirectAttributes.addFlashAttribute("mensaje", "Carrito vaciado");
        redirectAttributes.addFlashAttribute("tipoMensaje", "info");

        return "redirect:/carrito";
    }

    @GetMapping("/carrito/count")
    @ResponseBody
    public Integer contarItemsCarrito(HttpServletRequest request) {
        Usuario usuario = obtenerUsuarioLogueado(request);
        if (usuario == null) {
            return 0;
        }
        return servicioCarrito.contarItemsEnCarrito(usuario);
    }

    @PostMapping("/carrito/remover/ajax")
    @ResponseBody
    public Map<String, Object> removerDelCarritoAjax(@RequestParam Long obraId,
                                                     HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        Usuario usuario = obtenerUsuarioLogueado(request);
        
        if (usuario == null) {
            response.put("success", false);
            response.put("message", "Usuario no autenticado");
            return response;
        }

        try {
            servicioCarrito.removerObraDelCarrito(usuario, obraId);
            response.put("success", true);
            response.put("message", "Obra removida del carrito");
            response.put("carritoCount", servicioCarrito.contarItemsEnCarrito(usuario));
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al remover la obra");
        }
        
        return response;
    }

    @PostMapping("/carrito/agregar/ajax")
    @ResponseBody
    public Map<String, Object> agregarAlCarritoAjax(@RequestParam Long obraId,
                                                    @RequestParam(defaultValue = "1") Integer cantidad,
                                                    HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        Usuario usuario = obtenerUsuarioLogueado(request);
        
        if (usuario == null) {
            response.put("success", false);
            response.put("message", "Usuario no autenticado");
            return response;
        }

        try {
            servicioCarrito.agregarObraAlCarrito(usuario, obraId, cantidad);
            response.put("success", true);
            response.put("message", "Obra agregada al carrito");
            response.put("carritoCount", servicioCarrito.contarItemsEnCarrito(usuario));
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al agregar la obra");
        }
        
        return response;
    }

    private Usuario obtenerUsuarioLogueado(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        
        // Debug: imprimir información de la sesión
        System.out.println("=== DEBUG SESIÓN ===");
        System.out.println("Session ID: " + request.getSession().getId());
        System.out.println("Usuario en sesión: " + (usuario != null ? usuario.getEmail() : "null"));
        System.out.println("Atributos de sesión:");
        java.util.Enumeration<String> attributeNames = request.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            System.out.println("  " + attributeName + " = " + request.getSession().getAttribute(attributeName));
        }
        System.out.println("==================");
        
        return usuario;
    }
}