package com.tallerwebi.presentacion;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioOrdenCompra;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.OrdenCompra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.EstadoOrdenCompra;
import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.presentacion.dto.ItemCarritoDto;
import com.tallerwebi.presentacion.dto.ItemOrdenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.enterprise.inject.Model;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/orden")
public class ControladorOrdenCompra {

    private ServicioOrdenCompra servicioOrdenCompra;
    private ServicioCarrito servicioCarrito;

    @Autowired
    public ControladorOrdenCompra(ServicioOrdenCompra servicioOrdenCompra, ServicioCarrito servicioCarrito) {
        this.servicioOrdenCompra = servicioOrdenCompra;
        this.servicioCarrito = servicioCarrito;
    }

    @PostMapping("/crear")
    public ModelAndView crearOrden(HttpSession session, RedirectAttributes redirectAttributes) {
        ModelMap model = new ModelMap();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            Carrito carrito = servicioCarrito.obtenerCarritoConItems(usuario);
            OrdenCompra orden = servicioOrdenCompra.crearOrdenDeCompraAPartirDeCarrito(carrito);
            EstadoOrdenCompra estado = servicioOrdenCompra.obtenerEstadoDeOrden(orden.getId());
            List<ItemOrdenDto> items = servicioOrdenCompra.obtenerItems(orden.getId());

            model.put("orden", orden);
            model.put("estado", estado);
            model.put("items", items);
            model.put("usuario", usuario);

            return new ModelAndView("ordenCompra", model);

        } catch (CarritoVacioException e) {
            redirectAttributes.addFlashAttribute("error", "El carrito se encuentra vacio.");
            return new ModelAndView("redirect:/carrito");
        } catch (CarritoNoEncontradoException e) {
            redirectAttributes.addFlashAttribute("error", "El carrito no fue encontrado.");
            return new ModelAndView("redirect:/carrito");
        } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }

    }


    @GetMapping("/{id}")
    public ModelAndView verOrden(@PathVariable Long ordenId, HttpSession session) throws MPException, MPApiException {
        ModelMap modelo = new ModelMap();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        OrdenCompra orden = servicioOrdenCompra.obtenerOrdenPorId(ordenId);
        EstadoOrdenCompra estado = servicioOrdenCompra.obtenerEstadoDeOrden(ordenId);
        List<ItemOrdenDto> items = servicioOrdenCompra.obtenerItems(ordenId);

        modelo.put("orden", orden);
        modelo.put("estado", estado);
        modelo.put("items", items);
        modelo.put("usuario", usuario);

        return new ModelAndView("ordenCompra", modelo);
    }


    public String reintentarPago(HttpSession session, RedirectAttributes redirectAttributes) {
        ModelMap modelo = new ModelMap();
        return "";
    }
}
