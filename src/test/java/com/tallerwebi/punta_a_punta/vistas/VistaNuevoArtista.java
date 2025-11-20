package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class VistaNuevoArtista extends VistaWeb {

    public VistaNuevoArtista(Page page) {
        super(page);
    }

    public void ir() {
        page.navigate("http://localhost:8080/spring/perfilArtista/nuevo");
    }

    public void cargarFotoPerfilArtista(File file) {
        this.cargarImagenEnElElemento("#fotoPerfil", file);
    }

    public void escribirNombre(String nombre) {
        this.escribirEnElElemento("#nombre", nombre);
    }

    public void escribirBiografia(String biografia) {
        this.escribirEnElElemento("#biografia", biografia);
    }

    public void escribirUrlFacebook(String facebook) {
        this.escribirEnElElemento("#urlFacebook", facebook);
    }

    public void escribirUrlInstagram(String instagram) {
        this.escribirEnElElemento("#urlInstagram", instagram);
    }

    public void escribirUrlTwitter(String twitter) {
        this.escribirEnElElemento("#urlTwitter", twitter);
    }

    public void darClickEnBotonGuardar() {
        this.darClickEnElElemento("#btn-guardar");
    }
}
