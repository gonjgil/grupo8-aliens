package com.tallerwebi.config;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dby2iubyy",
                "api_key", "115411136472126",
                "api_secret", "ZnoUWiUMqm5u0r4mp5A05RUyprI",
                "secure", true
        ));
    }

}
