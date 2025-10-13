package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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

        return new ModelAndView("carrito", modelo);
    }

}