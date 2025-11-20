package com.tallerwebi.dominio;

import java.util.List;
import java.util.Map;

public interface ServicioMercadoPago {

    Map<String, Object> crearPagoCarrito(List<Map<String, Object>> items) throws Exception;
    Map<String, Object> crearPago(List<Map<String, Object>> items) throws Exception;
}
