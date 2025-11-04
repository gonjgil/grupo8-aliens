package com.tallerwebi.integracion.config;

import com.mercadopago.client.payment.PaymentClient;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfigPaymentClient {

        @Bean
        public PaymentClient paymentClient() {
            return Mockito.mock(PaymentClient.class);
        }



}
