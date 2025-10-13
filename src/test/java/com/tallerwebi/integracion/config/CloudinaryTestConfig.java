package com.tallerwebi.integracion.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class CloudinaryTestConfig {
    @Bean
    public Cloudinary cloudinary(){
        return mock(Cloudinary.class);
    }
}
