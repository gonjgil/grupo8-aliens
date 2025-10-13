package com.tallerwebi.presentacion;

import java.util.HashMap;
import java.util.Map;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.NoHayStockSuficiente;

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

    @PostMapping(value = "/carrito/agregar/ajax", produces = "application/json")
    @ResponseBody
    public Map<String, Object> agregarAlCarritoAjax(
        @RequestParam("obraId") Long obraId,
        @RequestParam(value = "cantidad", defaultValue = "1") Integer cantidad,
        HttpServletRequest request
    ) {
        Map<String, Object> response = new HashMap<>();
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario != null) {
            try {
                servicioCarrito.agregarObraAlCarrito(usuario, obraId);
                response.put("success", true);
                response.put("message", "Obra agregada al carrito");
            } catch (Exception e) {
                response.put("success", false);
                response.put("message", e.getMessage());
            }
        } else {
            response.put("success", false);
            response.put("message", "Debes iniciar sesión para agregar obras al carrito");
        }
        return response;
    }

}