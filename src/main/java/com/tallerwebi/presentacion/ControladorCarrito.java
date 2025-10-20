package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/carrito")
public class ControladorCarrito {

    private final ServicioCarrito servicioCarrito;

    @Autowired
    public ControladorCarrito(ServicioCarrito servicioCarrito) {
        this.servicioCarrito = servicioCarrito;
    }

    @GetMapping
    public ModelAndView verCarrito(HttpSession session) {
        ModelMap modelo = new ModelMap();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        List<ObraDto> items = servicioCarrito.obtenerObras(usuario);
        Double total = servicioCarrito.calcularPrecioTotalCarrito(usuario);
        Integer cantidadItems = servicioCarrito.contarItemsEnCarrito(usuario);

        modelo.put("items", items);
        modelo.put("total", total);
        modelo.put("cantidadItems", cantidadItems);
        modelo.put("usuario", usuario);

        return new ModelAndView("carrito", modelo);
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam Long id, 
                                   RedirectAttributes redirectAttributes, 
                                   HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Inicia sesión para agregar obras al carrito.");
            return "redirect:/login";
        }

        try {
            servicioCarrito.agregarObraAlCarrito(usuario, id);
            redirectAttributes.addFlashAttribute("mensaje", "Obra agregada al carrito correctamente!");
        } catch (NoExisteLaObra e) {
            redirectAttributes.addFlashAttribute("error", "La obra no existe.");
        } catch (NoHayStockSuficiente e) {
            redirectAttributes.addFlashAttribute("error", "No hay stock suficiente.");
        }

        return "redirect:/obra/" + id;
    }

    @PostMapping("/eliminar/{obraId}")
    public String eliminarDelCarrito(@PathVariable Long obraId, 
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        servicioCarrito.eliminarObraDelCarrito(usuario, obraId);
        redirectAttributes.addFlashAttribute("mensaje", "Obra eliminada del carrito.");
        
        return "redirect:/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciarCarrito(HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        servicioCarrito.vaciarCarrito(usuario);
        redirectAttributes.addFlashAttribute("mensaje", "Carrito vaciado correctamente.");
        
        return "redirect:/carrito";
    }
}