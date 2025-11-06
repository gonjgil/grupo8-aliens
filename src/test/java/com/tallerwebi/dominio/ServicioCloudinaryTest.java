package com.tallerwebi.dominio;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.tallerwebi.dominio.enums.TipoImagen;
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
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        cloudinary = mock(Cloudinary.class, RETURNS_DEEP_STUBS);
        uploader = mock(Uploader.class);

        when(cloudinary.uploader()).thenReturn(uploader);

        servicioCloudinary = new ServicioCloudinaryImpl(cloudinary);
    }

    @Test
    public void queSubaLaImagenCorrectamenteYDevuelvaLaUrl() throws Exception {
        byte[] contenido = "imagen".getBytes();
        when(archivo.getBytes()).thenReturn(contenido);

        Map<String, Object> respuestaCloudinary = new HashMap<>();
        respuestaCloudinary.put("secure_url", "https://res.cloudinary.com/demo/image/upload/v123/perfiles_artistas/foto.jpg");

        when(uploader.upload(eq(contenido), ArgumentMatchers.anyMap())).thenReturn(respuestaCloudinary);

        String resultado = servicioCloudinary.subirImagen(archivo, TipoImagen.PERFIL_ARTISTA);

        assertThat(resultado, is("https://res.cloudinary.com/demo/image/upload/v123/perfiles_artistas/foto.jpg"));
        verify(uploader, times(1)).upload(eq(contenido), argThat(map -> map.containsKey("folder")));
        verify(archivo, times(1)).getBytes();
    }

    @Test
    public void queLanceRuntimeExceptionSiHayErrorAlSubir() throws Exception {
        when(archivo.getBytes()).thenThrow(new IOException("Error de lectura"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioCloudinary.subirImagen(archivo, TipoImagen.PERFIL_ARTISTA);
        });

        assertThat(exception.getMessage(), is("Error al subir imagen a Cloudinary"));
        verify(uploader, never()).upload(any(), anyMap());
    }

    @Test
    public void queLanceIllegalArgumentExceptionSiElArchivoEsNuloOEstaVacio() {
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> {
            servicioCloudinary.subirImagen(null, TipoImagen.PERFIL_ARTISTA);
        });
        assertThat(exception1.getMessage(), is("El archivo no puede estar vacío"));

        when(archivo.isEmpty()).thenReturn(true);
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {
            servicioCloudinary.subirImagen(archivo, TipoImagen.PERFIL_ARTISTA);
        });
        assertThat(exception2.getMessage(), is("El archivo no puede estar vacío"));
    }

    @Test
    public void queSePuedaSubirUnaImagenDeTipoObra() throws Exception {
        byte[] contenido = "imagen".getBytes();
        when(archivo.getBytes()).thenReturn(contenido);

        Map<String, Object> respuestaCloudinary = new HashMap<>();
        respuestaCloudinary.put("secure_url", "https://res.cloudinary.com/demo/image/upload/v123/obras/obra.jpg");

        when(uploader.upload(eq(contenido), ArgumentMatchers.anyMap())).thenReturn(respuestaCloudinary);

        String resultado = servicioCloudinary.subirImagen(archivo, TipoImagen.OBRA);

        assertThat(resultado, is("https://res.cloudinary.com/demo/image/upload/v123/obras/obra.jpg"));
        verify(uploader, times(1)).upload(eq(contenido), argThat(map -> map.containsKey("folder")));
        verify(archivo, times(1)).getBytes();
    }
}
