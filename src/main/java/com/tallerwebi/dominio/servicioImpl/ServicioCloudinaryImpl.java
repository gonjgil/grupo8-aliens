package com.tallerwebi.dominio.servicioImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tallerwebi.dominio.ServicioCloudinary;
import com.tallerwebi.dominio.enums.TipoImagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service("servicioCloudinary")
public class ServicioCloudinaryImpl implements ServicioCloudinary {

    private final Cloudinary cloudinary;

    @Autowired
    public ServicioCloudinaryImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String subirImagen(MultipartFile archivo, TipoImagen tipoImagen) {
        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede estar vacío");
        }

        try{
            Map uploadResult = cloudinary.uploader().upload(archivo.getBytes(),
                    ObjectUtils.asMap("folder", tipoImagen.getCarpeta()));
            return (String) uploadResult.get("secure_url");
        } catch (IOException e){
            throw new RuntimeException("Error al subir imagen a Cloudinary", e);
        }
    }
}
