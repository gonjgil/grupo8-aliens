package com.tallerwebi.dominio.servicioImpl;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.ServicioMercadoPago;
import com.tallerwebi.dominio.excepcion.CarritoVacioException;
import com.tallerwebi.dominio.excepcion.NoSeEncontraronResultadosException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ServicioMercadoPagoImpl implements ServicioMercadoPago {

    @PostConstruct
    public void init() {
        com.mercadopago.MercadoPagoConfig.setAccessToken(
                com.tallerwebi.config.MercadoPagoConfig.getAccessToken()
        );
    }

    @Override
    public Map<String, Object> crearPagoCarrito(List<Map<String, Object>> items) throws Exception {

        // Validar que tengamos items
        if (items == null || items.isEmpty()) {
            throw new CarritoVacioException("El carrito está vacío");
        }

        // Crear los items para MercadoPago
        List<PreferenceItemRequest> mpItems = mapearItems(items);

        // Crear la preferencia para carrito
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(mpItems)
                .externalReference("CARRITO-" + System.currentTimeMillis())
                .build();

        // Crear la preferencia usando el SDK real de MercadoPago
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        return this.crearRespuesta(preference, "Preferencia de carrito creada exitosamente");
    }

    @Override
    public Map<String, Object> crearPago(List<Map<String, Object>> items) throws Exception {

        // Validar que tengamos items
        if (items == null || items.isEmpty()) {
            throw new NoSeEncontraronResultadosException("No se encontraron items para procesar");
        }

        // Crear los items para MercadoPago
        List<PreferenceItemRequest> mpItems = this.mapearItems(items);

        // Para testing local, comentamos las URLs de callback que causan problemas con localhost
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(com.tallerwebi.config.MercadoPagoConfig.getSuccessUrl())
                .failure(com.tallerwebi.config.MercadoPagoConfig.getFailureUrl())
                .pending(com.tallerwebi.config.MercadoPagoConfig.getPendingUrl())
                .build();

        // Crear la preferencia (sin autoReturn para evitar problemas con URLs localhost)
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(mpItems)
                .backUrls(backUrls)
                .autoReturn("approved")
                .externalReference("GALERIA-" + System.currentTimeMillis())
                .build();

        // Crear la preferencia usando el SDK real de MercadoPago
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        return this.crearRespuesta(preference, "Preferencia creada exitosamente en MercadoPago");
    }

    private List<PreferenceItemRequest> mapearItems(List<Map<String, Object>> items) {
        List<PreferenceItemRequest> mpItems = new ArrayList<>();

        for (Map<String, Object> item : items) {
            PreferenceItemRequest mpItem = PreferenceItemRequest.builder()
                    .title((String) item.get("title"))
                    .quantity(((Number) item.get("quantity")).intValue())
                    .unitPrice(new BigDecimal(item.get("unit_price").toString()))
                    .currencyId("ARS")
                    .build();

            mpItems.add(mpItem);
        }

        return mpItems;
    }

    private Map<String, Object> crearRespuesta(Preference preference, String message) {
        // Preparar la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("init_point", preference.getInitPoint());
        response.put("sandbox_init_point", preference.getSandboxInitPoint());
        response.put("id", preference.getId());
        response.put("status", "created");
        response.put("message", message);
        return response;
    }
}
