package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaLogin extends VistaWeb {

    public VistaLogin(Page page) {
        super(page);
    }

    public void ir() {
        page.navigate("http://localhost:8080/spring/login");
    }

//    public String obtenerTextoDeLaBarraDeNavegacion(){
//        return this.obtenerTextoDelElemento("hover:text-blue-500 transition");
//    }
//
//    public String obtenerMensajeDeError(){
//        return this.obtenerTextoDelElemento("text-center text-red-600 text-sm font-medium");
//    }

    public void escribirEMAIL(String email){
        this.escribirEnElElemento("#email", email);
    }

    public void escribirClave(String clave){
        this.escribirEnElElemento("#password", clave);
    }

    public void darClickEnIniciarSesion(){
        this.darClickEnElElemento("#btn-login");
    }

    public void darClickEnRegistrarse(){
        this.darClickEnElElemento("#btn-register");
    }
}
