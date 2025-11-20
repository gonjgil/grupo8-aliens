package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaPerfilArtista extends VistaWeb {

    public VistaPerfilArtista(Page page) {
        super(page);
    }

    public void ir() {
        page.navigate("http://localhost:8080/spring/perfilArtista/ver/51");
    }

    public void darClickEnBotonNuevaObra() {
        this.darClickEnElElemento("#btn-nueva-obra");
    }
}
