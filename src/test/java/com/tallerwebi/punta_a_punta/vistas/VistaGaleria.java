package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaGaleria extends VistaWeb {

    public VistaGaleria(Page page) {
        super(page);
    }

    public void ir() {
        page.navigate("http://localhost:8080/spring/galeria");
    }

    public void darClickEnBotonPerfilUsuario() {
        this.darClickEnElElemento("#btn-perfil-usuario");
    }
}
