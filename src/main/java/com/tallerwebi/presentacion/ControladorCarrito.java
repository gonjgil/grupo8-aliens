package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.NoExisteLaObra;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;
import com.tallerwebi.dominio.enums.Formato;

import com.tallerwebi.presentacion.dto.ItemCarritoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        List<ItemCarritoDto> items = servicioCarrito.obtenerItems(usuario);
        Double total = servicioCarrito.calcularPrecioTotalCarrito(usuario);

        modelo.put("items", items);
        modelo.put("total", total);
        modelo.put("usuario", usuario);

        return new ModelAndView("carrito", modelo);
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam Long id, 
                                   @RequestParam(required = false) String formato,
                                   RedirectAttributes redirectAttributes, 
                                   HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Inicia sesión para agregar obras al carrito.");
            return "redirect:/login";
        }

        try {
            // Si no se especifica formato, usar DIGITAL por defecto
            Formato formatoEnum = (formato != null && !formato.isEmpty()) ? 
                Formato.valueOf(formato.toUpperCase()) : Formato.DIGITAL;
            
            servicioCarrito.agregarObraAlCarrito(usuario, id, formatoEnum);
            redirectAttributes.addFlashAttribute("mensaje", "Obra agregada al carrito correctamente!");
        } catch (NoExisteLaObra e) {
            redirectAttributes.addFlashAttribute("error", "La obra no existe.");
        } catch (NoHayStockSuficiente e) {
            redirectAttributes.addFlashAttribute("error", "No hay stock suficiente para el formato seleccionado.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Formato no válido.");
        }

        return "redirect:/obra/" + id;
    }

    @PostMapping("/aumentar/{obraId}")
    public String aumentarCantidad(@PathVariable Long obraId, HttpSession session,
            @RequestParam(required = false) String redirect,
            @RequestParam String formato,
                    RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null)
            return "redirect:/login";

        try {
            Formato formatoEnum = Formato.valueOf(formato.toUpperCase());
            servicioCarrito.agregarObraAlCarrito(usuario, obraId,formatoEnum);
        } catch (NoHayStockSuficiente e) {
            redirectAttributes.addFlashAttribute("error", "No hay stock suficiente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Formato Invalido");
        }
        if ("obra".equals(redirect)) {
            return "redirect:/obra/" + obraId;
        }
        return "redirect:/carrito";
    }

    @PostMapping("/disminuir/{obraId}")
    public String disminuirCantidad(@PathVariable Long obraId, 
                                    @RequestParam String formato,
                                    HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        try {
            Formato formatoEnum = Formato.valueOf(formato.toUpperCase());
            servicioCarrito.disminuirCantidadDeObraDelCarrito(usuario, obraId, formatoEnum);
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/carrito";
    }

    @PostMapping("/eliminar/{obraId}")
    public String eliminarDelCarrito(@PathVariable Long obraId,
                                     @RequestParam String formato,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        try {
            Formato formatoEnum = Formato.valueOf(formato.toUpperCase());
            servicioCarrito.eliminarObraDelCarrito(usuario, obraId, formatoEnum);
            redirectAttributes.addFlashAttribute("mensaje", "Obra eliminada del carrito.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Formato no válido.");
        }
        
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

    @PostMapping("/finalizar")
    public ResponseEntity<?> finalizarCarrito(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
          servicioCarrito.finalizarCarrito(usuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contador")
    @ResponseBody
    public String obtenerContadorCarrito(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            return "{\"count\": 0}";
        }
        
        List<ItemCarritoDto> items = servicioCarrito.obtenerItems(usuario);
        int total = items.stream().mapToInt(ItemCarritoDto::getCantidad).sum();
        return "{\"count\": " + total + "}";
    }



}