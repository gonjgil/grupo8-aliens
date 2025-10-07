package com.tallerwebi.config;

public class MercadoPagoConfig {
    
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                // Credenciales de MercadoPago TEST actualizadas para desarrollo
    private static final String ACCESS_TOKEN = "TEST-6153218614070129-100119-44856ce364468a90fdd1390c950cfb90-97406022";
    private static final String PUBLIC_KEY = "TEST-12b898d1-056f-4353-9d79-d6cfa383e217";
    private static final String BASE_URL = "http://localhost:8080/spring";
    
    // Access Token de MercadoPago
    public static String getAccessToken() {
        return ACCESS_TOKEN;
    }
    
    // Public Key de MercadoPago (opcional, para frontend)
    public static String getPublicKey() {
        return PUBLIC_KEY;
    }
                                                                                                            
    // URL base para callbacks
    public static String getBaseUrl() {
        return BASE_URL;
    }
    
    // URLs de callback
    public static String getSuccessUrl() {
        return getBaseUrl() + "/api/pagos/success";
    }
    
    public static String getFailureUrl() {
        return getBaseUrl() + "/api/pagos/failure";
    }
    
    public static String getPendingUrl() {
        return getBaseUrl() + "/api/pagos/pending";
    }
}