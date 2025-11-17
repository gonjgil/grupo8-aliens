package com.tallerwebi.presentacion;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioCompraHecha;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.entidades.ItemCompra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.PagoNoAprobadoException;
import com.tallerwebi.presentacion.dto.ItemCompraDto;
import com.tallerwebi.presentacion.dto.CompraHechaDto;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/compras")
public class ControladorCompraHecha {

    private ServicioCompraHecha servicioCompraHecha;

    @Autowired
    public ControladorCompraHecha(ServicioCompraHecha servicioCompraHecha) {
        this.servicioCompraHecha = servicioCompraHecha;

    }


    @GetMapping("/detalle/{id}")
    @ResponseBody
    public ResponseEntity<CompraHechaDto> obtenerDetalleCompra(@PathVariable Long id, HttpSession session) throws MPException, MPApiException {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CompraHecha compra = servicioCompraHecha.obtenerCompraPorId(id);
        if (compra == null || !compra.getUsuario().getId().equals(usuario.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<ItemCompraDto> itemsDto = new ArrayList<>();

        for (ItemCompra item : compra.getItems()) {
            itemsDto.add(new ItemCompraDto(item));
        }

        CompraHechaDto dto = new CompraHechaDto(compra, itemsDto);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/historial")
    public ModelAndView verMisCompras(HttpSession session, @RequestParam(defaultValue = "0") int pagina) {
        ModelMap modelo = new ModelMap();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
//        int tamanioPorPagina = 9;
        List<CompraHecha> compras = servicioCompraHecha.obtenerComprasPorUsuario(usuario);
        List<CompraHechaDto> comprasDto = new ArrayList<>();
        for (CompraHecha compra : compras) {
            compra.getItems().size();
            comprasDto.add(new CompraHechaDto(compra));
        }

        if (comprasDto.isEmpty()) {
            modelo.put("compras", Collections.emptyList());
        } else {
            modelo.put("compras", comprasDto);
        }

        modelo.put("usuario", usuario);
        return new ModelAndView("compras_historial", modelo);
    }

    @GetMapping("/error")
    public ModelAndView mostrarError() {
        return new ModelAndView("compras_error");
    }


}
