package com.tallerwebi.presentacion;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPApiException;
import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioCompraHecha;
import com.tallerwebi.dominio.ServicioMail;
import com.tallerwebi.dominio.ServicioMercadoPago;
import com.tallerwebi.dominio.entidades.Carrito;
import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.CarritoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.PagoNoAprobadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ControladorPagos {

    @Autowired
    private ServicioCompraHecha servicioCompraHecha;

    @Autowired
    private ServicioCarrito servicioCarrito;

    @Autowired
    private ServicioMercadoPago servicioMercadoPago;

    @Autowired
    private ServicioMail servicioMail;

    public ControladorPagos() {
    }

    public ControladorPagos(ServicioCompraHecha servicioCompraHecha, ServicioCarrito servicioCarrito, ServicioMail servicioMail) {
        this.servicioCompraHecha = servicioCompraHecha;
        this.servicioCarrito = servicioCarrito;
        this.servicioMail = servicioMail;
    }

    @PostConstruct
    public void init() {
        try {
            // Configurar MercadoPago con credenciales hardcodeadas para desarrollo
            com.mercadopago.MercadoPagoConfig.setAccessToken(com.tallerwebi.config.MercadoPagoConfig.getAccessToken());
        } catch (Exception e) {
            System.err.println("Error al configurar MercadoPago: " + e.getMessage());
        }
    }

    @PostMapping(value = "/pagos/carrito", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> crearPagoCarrito(@RequestBody Map<String, Object> requestBody) {
        try {
            // Obtener los items del carrito del request
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> items = (List<Map<String, Object>>) requestBody.get("items");
            Map<String, Object> response = servicioMercadoPago.crearPagoCarrito(items);
            return ResponseEntity.ok(response);

        } catch (MPApiException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al crear la preferencia del carrito: " + e.getMessage());
            errorResponse.put("statusCode", e.getStatusCode());
            errorResponse.put("details", "MPApiException");

            return ResponseEntity.badRequest().body(errorResponse);
        } catch (MPException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al crear la preferencia del carrito: " + e.getMessage());
            errorResponse.put("details", "MPException");

            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al crear la preferencia del carrito: " + e.getMessage());
            errorResponse.put("details", e.getClass().getSimpleName());

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping(value = "/pagos", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> crearPago(@RequestBody Map<String, Object> requestBody) {
        try {
            // Obtener los items del request
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> items = (List<Map<String, Object>>) requestBody.get("items");
            Map<String, Object> response = servicioMercadoPago.crearPago(items);
            return ResponseEntity.ok(response);

        } catch (MPApiException e) {

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al crear la preferencia de pago: " + e.getMessage());
            errorResponse.put("statusCode", e.getStatusCode());
            errorResponse.put("apiResponse", e.getApiResponse());
            errorResponse.put("details", "MPApiException");

            return ResponseEntity.badRequest().body(errorResponse);
        } catch (MPException e) {

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al crear la preferencia de pago: " + e.getMessage());
            errorResponse.put("details", "MPException");

            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al crear la preferencia de pago: " + e.getMessage());
            errorResponse.put("details", e.getClass().getSimpleName());

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/pagos/success")
    public ModelAndView pagoExitoso(@RequestParam("payment_id") Long paymentId, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            Carrito carrito = servicioCarrito.obtenerCarritoConItems(usuario);
            CompraHecha compra = servicioCompraHecha.crearResumenCompraAPartirDeCarrito(carrito, paymentId);

            if (compra == null || compra.getId() == null) {
                return new ModelAndView("redirect:/compras/error");
            }

            return new ModelAndView("redirect:/compras/historial");

        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("redirect:/compras/error");
        }
    }

    @GetMapping("/pagos/failure")
    public ResponseEntity<?> pagoFallido() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "El pago fue rechazado o cancelado");

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/pagos/pending")
    public ResponseEntity<?> pagoPendiente() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "El pago está pendiente de confirmación");
        
        return ResponseEntity.ok(response);
    }
}
