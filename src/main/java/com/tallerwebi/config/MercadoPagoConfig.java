package com.tallerwebi.config;

import com.mercadopago.client.payment.PaymentClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfig {
    
    // credenciales de MercadoPago TEST harcodeadas para desarrollo, ver como inyectarlas en produccion con Spring
    private static final String ACCESS_TOKEN = "APP_USR-1109986656199883-111215-c876508d0dd62b9f2e4c8014e4a2987d-2985296777";
    private static final String PUBLIC_KEY = "APP_USR-c4bbba92-da84-4086-a168-c07b37cf879b";
    private static final String BASE_URL = "https://unmodest-adulatory-yetta.ngrok-free.dev/spring";


    static {
        // Inicializa el SDK global con el access token
        com.mercadopago.MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);
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