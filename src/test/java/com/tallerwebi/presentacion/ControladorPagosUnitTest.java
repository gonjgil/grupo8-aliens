package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import com.tallerwebi.dominio.ServicioCarrito;
import com.tallerwebi.dominio.ServicioCompraHecha;
import com.tallerwebi.dominio.ServicioMail;

class ControladorPagosUnitTest {

    @Mock
    private ServicioCompraHecha servicioCompraHecha;

    @Mock
    private ServicioCarrito servicioCarrito;

    @Mock
    private ServicioMail servicioMail;

    private ControladorPagos controlador;

    @BeforeEach
    void setUp() {
        controlador = new ControladorPagos(servicioCompraHecha, servicioCarrito, servicioMail);
    }

    @Test
    void testCrearPagoCarritoConItemsValidos() {
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        Map<String, Object> item = new HashMap<>();
        item.put("title", "Obra de Arte");
        item.put("quantity", 1);
        item.put("unit_price", "100.00");
        items.add(item);

        requestBody.put("items", items);

        ResponseEntity<?> response = controlador.crearPagoCarrito(requestBody);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCrearPagoCarritoConCarritoVacio() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("items", new ArrayList<>());

        ResponseEntity<?> response = controlador.crearPagoCarrito(requestBody);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());

        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("error"));
        assertEquals("El carrito está vacío", responseBody.get("error"));
    }

    @Test
    void testCrearPagoCarritoSinItems() {
        Map<String, Object> requestBody = new HashMap<>();

        ResponseEntity<?> response = controlador.crearPagoCarrito(requestBody);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());

        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("error"));
        assertEquals("El carrito está vacío", responseBody.get("error"));
    }

    @Test
    void testCrearPagoCarritoConMultiplesItems() {
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        Map<String, Object> item1 = new HashMap<>();
        item1.put("title", "Obra 1");
        item1.put("quantity", 2);
        item1.put("unit_price", "50.00");
        items.add(item1);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("title", "Obra 2");
        item2.put("quantity", 1);
        item2.put("unit_price", "75.00");
        items.add(item2);

        requestBody.put("items", items);

        ResponseEntity<?> response = controlador.crearPagoCarrito(requestBody);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCrearPagoConItemsValidos() {
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        Map<String, Object> item = new HashMap<>();
        item.put("title", "Obra Digital");
        item.put("quantity", 1);
        item.put("unit_price", "150.00");
        items.add(item);

        requestBody.put("items", items);

        ResponseEntity<?> response = controlador.crearPago(requestBody);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCrearPagoConCarritoVacio() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("items", new ArrayList<>());

        ResponseEntity<?> response = controlador.crearPago(requestBody);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());

        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("error"));
        assertEquals("No se encontraron items para procesar", responseBody.get("error"));
    }

    @Test
    void testCrearPagoSinItems() {
        Map<String, Object> requestBody = new HashMap<>();

        ResponseEntity<?> response = controlador.crearPago(requestBody);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());

        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("error"));
        assertEquals("No se encontraron items para procesar", responseBody.get("error"));
    }

    @Test
    void testCrearPagoConMultiplesItems() {
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        Map<String, Object> item1 = new HashMap<>();
        item1.put("title", "Cuadro Moderno");
        item1.put("quantity", 1);
        item1.put("unit_price", "200.00");
        items.add(item1);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("title", "Escultura");
        item2.put("quantity", 1);
        item2.put("unit_price", "300.00");
        items.add(item2);

        requestBody.put("items", items);

        ResponseEntity<?> response = controlador.crearPago(requestBody);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testPagoFallido() {
        ResponseEntity<?> response = controlador.pagoFallido();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("message"));
        assertEquals("El pago fue rechazado o cancelado", responseBody.get("message"));
    }

    @Test
    void testPagoPendiente() {
        ResponseEntity<?> response = controlador.pagoPendiente();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("message"));
        assertEquals("El pago está pendiente de confirmación", responseBody.get("message"));
    }

    @Test
    void testCrearPagoCarritoConPrecioDecimal() {
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        Map<String, Object> item = new HashMap<>();
        item.put("title", "Obra Especial");
        item.put("quantity", 3);
        item.put("unit_price", "99.99");
        items.add(item);

        requestBody.put("items", items);

        ResponseEntity<?> response = controlador.crearPagoCarrito(requestBody);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCrearPagoConPrecioDecimal() {
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        Map<String, Object> item = new HashMap<>();
        item.put("title", "Obra Premium");
        item.put("quantity", 2);
        item.put("unit_price", "249.50");
        items.add(item);

        requestBody.put("items", items);

        ResponseEntity<?> response = controlador.crearPago(requestBody);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCrearPagoCarritoConCantidadAlta() {
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        Map<String, Object> item = new HashMap<>();
        item.put("title", "Obra Masiva");
        item.put("quantity", 100);
        item.put("unit_price", "10.00");
        items.add(item);

        requestBody.put("items", items);

        ResponseEntity<?> response = controlador.crearPagoCarrito(requestBody);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCrearPagoConCantidadAlta() {
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        Map<String, Object> item = new HashMap<>();
        item.put("title", "Obra en Lote");
        item.put("quantity", 50);
        item.put("unit_price", "20.00");
        items.add(item);

        requestBody.put("items", items);

        ResponseEntity<?> response = controlador.crearPago(requestBody);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCrearPagoCarritoConItemTituloLargo() {
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        Map<String, Object> item = new HashMap<>();
        item.put("title", "Una obra de arte muy especial con titulo muy largo");
        item.put("quantity", 1);
        item.put("unit_price", "500.00");
        items.add(item);

        requestBody.put("items", items);

        ResponseEntity<?> response = controlador.crearPagoCarrito(requestBody);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCrearPagoConItemTituloLargo() {
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        Map<String, Object> item = new HashMap<>();
        item.put("title", "Otra obra de arte con titulo muy largo y descriptivo");
        item.put("quantity", 1);
        item.put("unit_price", "750.00");
        items.add(item);

        requestBody.put("items", items);

        ResponseEntity<?> response = controlador.crearPago(requestBody);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }
}
