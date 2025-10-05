package com.tallerwebi.presentacion;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = {ControladorObra.class, ControladorGaleria.class})
public class ControladorAtributosGlobales {
//    private final CarritoService carritoService;
//
//    public ControladorAtributosGlobales(CarritoService carritoService) {
//        this.carritoService = carritoService;
//    }

    // Esto agrega automáticamente "cartCount" al modelo de TODAS las vistas
    @ModelAttribute("cartCount")
    public int getCartCount() {
        // return carritoService.getCantidadTotal();
        return 1; // Valor de ejemplo
    }
}