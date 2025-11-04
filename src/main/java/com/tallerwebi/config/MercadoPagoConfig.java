package com.tallerwebi.config;

import com.mercadopago.client.payment.PaymentClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfig {
    
    // credenciales de MercadoPago TEST harcodeadas para desarrollo, ver como inyectarlas en produccion con Spring
    private static final String ACCESS_TOKEN = "TEST-6153218614070129-100119-44856ce364468a90fdd1390c950cfb90-97406022";
    private static final String PUBLIC_KEY = "TEST-12b898d1-056f-4353-9d79-d6cfa383e217";
    private static final String BASE_URL = "http://localhost:8080/spring";


    static {
        // Inicializa el SDK global con el access token
        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);
    }

    private static void setAccessToken(String accessToken) {
    }

    @Bean
    public PaymentClient paymentClient() {
        return new PaymentClient();
    }


    public static String getAccessToken() {
        return ACCESS_TOKEN;
    }
    
    // el public key se usa solo en el frontend?
    public static String getPublicKey() {
        return PUBLIC_KEY;
    }
                                                                                                            
    // URL base
    public static String getBaseUrl() {
        return BASE_URL;
    }
    
    // endpoints
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