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

        return new ModelAndView("carrito", modelo);
    }

}