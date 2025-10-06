package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.ServicioCarritoImpl;
import com.tallerwebi.dominio.ServicioGaleriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller @RequestMapping("/carrito")
public class ControladorCarrito {

    @Autowired
    private ServicioCarritoImpl servicioCarrito;

    @GetMapping
    public ModelAndView verCarrito() {
        ModelAndView mav = new ModelAndView("carrito");
        mav.addObject("items", servicioCarrito.obtenerItems());
        return mav;
    }

    // Agregar obra al carrito
    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam Long id,
                                   @RequestParam String titulo) {
        Obra obra = new Obra();
        obra.setId(id);
        obra.setTitulo(titulo);

        servicioCarrito.agregar(obra);
        return "redirect:/galeria_alt"; // vuelve a la galería después de agregar
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
