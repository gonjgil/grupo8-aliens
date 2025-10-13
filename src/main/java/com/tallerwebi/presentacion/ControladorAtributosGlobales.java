package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.Usuario;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ControllerAdvice(assignableTypes = { ControladorObra.class, ControladorGaleria.class, ControladorCarrito.class })
public class ControladorAtributosGlobales {

    private final ServicioCarrito servicioCarrito;

    public ControladorAtributosGlobales(ServicioCarrito servicioCarrito) {
        this.servicioCarrito = servicioCarrito;
    }

    // Esto agrega automáticamente "cartCount" al modelo de TODAS las vistas
    @ModelAttribute("cartCount")
    public int getContadorCarrito(Usuario usuario) {
        if (usuario != null) {
            return servicioCarrito.getCantidadTotal();
        }
        return 0;
    }

}