package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.TipoImagen;
import org.springframework.web.multipart.MultipartFile;

public interface ServicioCloudinary {
    String subirImagen(MultipartFile archivo, TipoImagen tipoImagen);
}
