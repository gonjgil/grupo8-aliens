package com.tallerwebi.presentacion;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioCompraHecha;
import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.presentacion.dto.ItemCompraDto;
import com.tallerwebi.presentacion.dto.CompraHechaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/compras")
public class ControladorCompraHecha {

    private ServicioCompraHecha servicioCompraHecha;
    private ServicioCarrito servicioCarrito;

    @Autowired
    public ControladorCompraHecha(ServicioCompraHecha servicioCompraHecha, ServicioCarrito servicioCarrito) {
        this.servicioCompraHecha = servicioCompraHecha;
        this.servicioCarrito = servicioCarrito;
    }

    @GetMapping("/{id}")
    public ModelAndView verCompra(@PathVariable Long ordenId, HttpSession session) throws MPException, MPApiException {
        ModelMap modelo = new ModelMap();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        CompraHecha orden = servicioCompraHecha.obtenerCompraPorId(ordenId);
        List<ItemCompraDto> items = servicioCompraHecha.obtenerItems(ordenId);

        modelo.put("orden", orden);
        modelo.put("items", items);
        modelo.put("usuario", usuario);

        return new ModelAndView("compras_historial", modelo);
    }

    @GetMapping("/historial")
    public ModelAndView verMisCompras(HttpSession session, @RequestParam(defaultValue = "0") int pagina) {
        ModelMap modelo = new ModelMap();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        int tamanioPorPagina = 6;
        List<CompraHechaDto> compras = servicioCompraHecha.obtenerComprasPorUsuario(usuario);

        int desde = pagina * tamanioPorPagina;
        int hasta = Math.min(desde + tamanioPorPagina, compras.size());
        List<CompraHechaDto> comprasEnPagina = compras.subList(desde, hasta);

        modelo.put("compras", comprasEnPagina);
        modelo.put("usuario", usuario);
        modelo.put("paginaActual", pagina);
        modelo.put("totalPaginas", (int) Math.ceil((double) compras.size() / tamanioPorPagina));
        return new ModelAndView("compras_historial", modelo);
    }
}
