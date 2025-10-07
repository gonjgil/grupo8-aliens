package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.ServicioCarritoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller @RequestMapping("/carrito")
public class ControladorCarrito {

    @Autowired
    private ServicioCarritoImpl servicioCarrito;

    @GetMapping
    public ModelAndView verCarrito() {
        ModelAndView mav = new ModelAndView("carrito");
        Set<Obra> items = servicioCarrito.obtenerItems();

        // Calcular el total sumando los precios de todas las obras
        double total = items.stream()
                .mapToDouble(Obra::getPrecio)
                .sum();

        mav.addObject("items", items);
        mav.addObject("total", total);

        return mav;
    }


    // Agregar obra al carrito
    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam Long id,
                                   @RequestParam String titulo, @RequestParam Double precio, RedirectAttributes redirectAttributes) {
        Obra obra = new Obra();
        obra.setId(id);
        obra.setTitulo(titulo);
        obra.setPrecio(precio);

        servicioCarrito.agregar(obra);
        redirectAttributes.addFlashAttribute("mensaje", "Obra agregada al carrito correctamente!");
        return "redirect:/obra/" + id; // vuelve a la galería después de agregar
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
