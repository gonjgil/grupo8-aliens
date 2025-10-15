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
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller @RequestMapping("/carrito")
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

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        modelo.put("usuario", usuario);

        return new ModelAndView("carrito", modelo);
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @GetMapping
    public ModelAndView verCarrito(HttpSession session) {
        ModelAndView mav = new ModelAndView("carrito");
        Set<Obra> items = servicioCarrito.obtenerItems();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");


        // Calcular el total sumando los precios de todas las obras
        double total = items.stream()
                .mapToDouble(Obra::getPrecio)
                .sum();

        mav.addObject("items", items);
        mav.addObject("total", total);
        mav.addObject("usuario", usuario);

        return mav;
    }


    // Agregar obra al carrito
    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam Long id, @RequestParam String titulo, @RequestParam Double precio,
                                   RedirectAttributes redirectAttributes, HttpSession session) {
        Object usuarioLogueado = session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            redirectAttributes.addFlashAttribute("error", "Inicia sesión para agregar obras al carrito.");
            return "redirect:/obra/" + id; // o la ruta de login correspondiente
        }

        Obra obra = new Obra();
        obra.setId(id);
        obra.setTitulo(titulo);
        obra.setPrecio(precio);

        servicioCarrito.agregar(obra);
        redirectAttributes.addFlashAttribute("mensaje", "Obra agregada al carrito correctamente!");
        return "redirect:/obra/" + id;
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarDelCarrito(@PathVariable Long id) {
        servicioCarrito.eliminar(id);
        return "redirect:/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciarCarrito() {
        servicioCarrito.vaciar();
        return "redirect:/carrito";
    }
}