package com.tallerwebi.dominio;

import org.springframework.web.multipart.MultipartFile;

public interface ServicioCloudinary {
    String subirImagen(MultipartFile archivo);
}
