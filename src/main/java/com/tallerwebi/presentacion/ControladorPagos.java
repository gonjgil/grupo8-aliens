package com.tallerwebi.presentacion;

import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.exceptions.MPApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ControladorPagos {

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
            
            // Validar que tengamos items
            if (items == null || items.isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "El carrito está vacío");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // Crear los items para MercadoPago
            List<PreferenceItemRequest> mpItems = new ArrayList<>();
            for (Map<String, Object> item : items) {
                String title = (String) item.get("title");
                Integer quantity = ((Number) item.get("quantity")).intValue();
                BigDecimal unitPrice = new BigDecimal(item.get("unit_price").toString());
                
                PreferenceItemRequest mpItem = PreferenceItemRequest.builder()
                    .title(title)
                    .quantity(quantity)
                    .unitPrice(unitPrice)
                    .currencyId("ARS")
                    .build();
                mpItems.add(mpItem);
            }
            
            // Crear la preferencia para carrito
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(mpItems)
                .externalReference("CARRITO-" + System.currentTimeMillis())
                .build();
            
            // Crear la preferencia usando el SDK real de MercadoPago
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);
            
            // Preparar la respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("init_point", preference.getInitPoint());
            response.put("sandbox_init_point", preference.getSandboxInitPoint());
            response.put("id", preference.getId());
            response.put("status", "created");
            response.put("message", "Preferencia de carrito creada exitosamente");
            
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
            
            // Validar que tengamos items
            if (items == null || items.isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "No se encontraron items para procesar");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // Crear los items para MercadoPago
            List<PreferenceItemRequest> mpItems = new ArrayList<>();
            for (Map<String, Object> item : items) {
                String title = (String) item.get("title");
                Integer quantity = ((Number) item.get("quantity")).intValue();
                BigDecimal unitPrice = new BigDecimal(item.get("unit_price").toString());
                
                PreferenceItemRequest mpItem = PreferenceItemRequest.builder()
                    .title(title)
                    .quantity(quantity)
                    .unitPrice(unitPrice)
                    .currencyId("ARS")
                    .build();
                mpItems.add(mpItem);
            }
            
            // Para testing local, comentamos las URLs de callback que causan problemas con localhost
            // PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
            //     .success(com.tallerwebi.config.MercadoPagoConfig.getSuccessUrl())
            //     .failure(com.tallerwebi.config.MercadoPagoConfig.getFailureUrl())
            //     .pending(com.tallerwebi.config.MercadoPagoConfig.getPendingUrl())
            //     .build();
            
            // Crear la preferencia (sin autoReturn para evitar problemas con URLs localhost)
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(mpItems)
                .externalReference("GALERIA-" + System.currentTimeMillis())
                .build();
            
            // Crear la preferencia usando el SDK real de MercadoPago
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);
            
            // Preparar la respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("init_point", preference.getInitPoint());
            response.put("sandbox_init_point", preference.getSandboxInitPoint());
            response.put("id", preference.getId());
            response.put("status", "created");
            response.put("message", "Preferencia creada exitosamente en MercadoPago");
            
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
    public ResponseEntity<?> pagoExitoso(@RequestParam(required = false) String payment_id,
                                        @RequestParam(required = false) String status,
                                        @RequestParam(required = false) String merchant_order_id) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Pago procesado exitosamente");
        response.put("payment_id", payment_id);
        response.put("status", status);
        
        return ResponseEntity.ok(response);
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
