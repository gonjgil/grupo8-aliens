package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.entidades.Artista;

public class PerfilArtistaDTO {

        private Long id;


        private String nombre;
        private String biografia;
        private String urlFotoPerfil;
        private String urlFacebook;
        private String urlInstagram;
        private String urlTwitter;
        private Long usuarioId;
        private Boolean aceptaComisiones;

    public PerfilArtistaDTO(Long id, String nombre, String biografia, String urlFotoPerfil, String urlFacebook, String urlInstagram, String urlTwitter, Long usuarioId, Boolean aceptaComisiones) {
        this.id = id;
        this.nombre = nombre;
        this.biografia = biografia;
        this.urlFotoPerfil = urlFotoPerfil;
        this.urlFacebook = urlFacebook;
        this.urlInstagram = urlInstagram;
        this.urlTwitter = urlTwitter;
        this.usuarioId = usuarioId;
        this.aceptaComisiones = aceptaComisiones;
    }

    public PerfilArtistaDTO(Artista artista) {
        this.id = artista.getId();
        this.nombre = artista.getNombre();
        this.biografia = artista.getBiografia();
        this.urlFotoPerfil = artista.getUrlFotoPerfil();
        this.urlFacebook = artista.getUrlFacebook();
        this.urlInstagram = artista.getUrlInstagram();
        this.urlTwitter = artista.getUrlTwitter();
        this.aceptaComisiones = artista.getAceptaComisiones();
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

    public Boolean getAceptaComisiones() {
        return aceptaComisiones;
    }

    public void setAceptaComisiones(Boolean aceptaComisiones) {
        this.aceptaComisiones = aceptaComisiones;
    }

    public Artista toArtista() {
        Artista artista = new Artista();
        artista.setId(this.id);
        artista.setNombre(this.nombre);
        artista.setBiografia(this.biografia);
        artista.setUrlFotoPerfil(this.urlFotoPerfil);
        artista.setUrlFacebook(this.urlFacebook);
        artista.setUrlInstagram(this.urlInstagram);
        artista.setUrlTwitter(this.urlTwitter);
        artista.setAceptaComisiones(this.aceptaComisiones);
        return artista;
    }

    public Long getUsuarioId() { return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId;
    }
}