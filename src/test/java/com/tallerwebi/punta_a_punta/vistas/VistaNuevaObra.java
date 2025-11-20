package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class VistaNuevaObra extends VistaWeb {

    public VistaNuevaObra(Page page) {
        super(page);
    }

    public void ir() {
        page.navigate("http://localhost:8080/spring/obra/nueva");
    }

    public void cargarImagenObra(File file) {
        this.cargarImagenEnElElemento("#file_obra", file);
    }

    public void escribirTitulo(String titulo) {
        this.escribirEnElElemento("#titulo", titulo);
    }

    public void escribirDescripcion(String descripcion) {
        this.escribirEnElElemento("#descripcion", descripcion);
    }

    public void darClickEnBotonCrearObra() {
        this.darClickEnElElemento("#btn-guardar");
    }
}
