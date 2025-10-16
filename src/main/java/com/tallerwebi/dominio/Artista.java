package com.tallerwebi.dominio;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Artista")
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String biografia;
    private String urlFotoPerfil; // URL de la imagen del perfil
    private String urlFacebook;
    private String urlInstagram;
    private String urlTwitter;

    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Obra> obras = new ArrayList<>();

    public Artista(String nombre, String biografia, String urlFotoPerfil) {
        this.nombre = nombre;
        this.biografia = biografia;
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public Artista() {

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
