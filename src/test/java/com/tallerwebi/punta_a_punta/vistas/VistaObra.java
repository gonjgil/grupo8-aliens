package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaObra extends VistaWeb {
    public VistaObra(Page page) {
        super(page);
    }

    public void ir() {
        page.navigate("http://localhost:8080/spring/obra/101");
    }

    public String obtenerTituloDeObra() {
        return this.obtenerTextoDelElemento("#obra-titulo");
    }

    public String obtenerDescripcionDeObra() {
        return this.obtenerTextoDelElemento("#obra-descripcion");
    }
}
