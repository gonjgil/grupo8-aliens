package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaUsuario extends VistaWeb {

    public VistaUsuario(Page page) {
        super(page);
    }

    public void ir() {
        page.navigate("http://localhost:8080/spring/usuario");
    }

    public void darClickEnBotonNuevoArtista() {
        this.darClickEnElElemento("#btn-nuevo-artista");
    }
}
