package com.tallerwebi.presentacion;

public class PerfilArtistaDTO {

        private Long id;


        private String nombre;
        private String biografia;
        private String urlFotoPerfil;
        private String urlFacebook;
        private String urlInstagram;
        private String urlTwitter;

    public PerfilArtistaDTO(Long id, String nombre, String biografia, String urlFotoPerfil, String urlFacebook, String urlInstagram, String urlTwitter) {
        this.id = id;
        this.nombre = nombre;
        this.biografia = biografia;
        this.urlFotoPerfil = urlFotoPerfil;
        this.urlFacebook = urlFacebook;
        this.urlInstagram = urlInstagram;
        this.urlTwitter = urlTwitter;
    }

    public PerfilArtistaDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public String getUrlFacebook() {
        return urlFacebook;
    }

    public void setUrlFacebook(String urlFacebook) {
        this.urlFacebook = urlFacebook;
    }

    public String getUrlInstagram() {
        return urlInstagram;
    }

    public void setUrlInstagram(String urlInstagram) {
        this.urlInstagram = urlInstagram;
    }

    public String getUrlTwitter() {
        return urlTwitter;
    }

    public void setUrlTwitter(String urlTwitter) {
        this.urlTwitter = urlTwitter;
    }
}



