package com.tallerwebi.dominio;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.tallerwebi.dominio.servicioImpl.ServicioCloudinaryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ServicioCloudinaryImplTest {

    @Mock
    private MultipartFile archivo;

    private Cloudinary cloudinary;
    private Uploader uploader;
    private ServicioCloudinaryImpl servicioCloudinary;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cloudinary = mock(Cloudinary.class, RETURNS_DEEP_STUBS);
        uploader = mock(Uploader.class);

        when(cloudinary.uploader()).thenReturn(uploader);

        servicioCloudinary = new ServicioCloudinaryImpl(cloudinary);
    }

    @Test
    void queSubaLaImagenCorrectamenteYDevuelvaLaUrl() throws Exception {
        byte[] contenido = "imagen".getBytes();
        when(archivo.getBytes()).thenReturn(contenido);

        Map<String, Object> respuestaCloudinary = new HashMap<>();
        respuestaCloudinary.put("secure_url", "https://res.cloudinary.com/demo/image/upload/v123/foto.jpg");

        when(uploader.upload(eq(contenido), ArgumentMatchers.anyMap())).thenReturn(respuestaCloudinary);

        String resultado = servicioCloudinary.subirImagen(archivo);

        assertThat(resultado, is("https://res.cloudinary.com/demo/image/upload/v123/foto.jpg"));
        verify(uploader, times(1)).upload(eq(contenido), argThat(map -> map.containsKey("folder")));
        verify(archivo, times(1)).getBytes();
    }

    @Test
    void queLanceRuntimeExceptionSiHayErrorAlSubir() throws Exception {
        when(archivo.getBytes()).thenThrow(new IOException("Error de lectura"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioCloudinary.subirImagen(archivo);
        });

        assertThat(exception.getMessage(), is("Error al subir imagen a Cloudinary"));
        verify(uploader, never()).upload(any(), anyMap());
    }
}
