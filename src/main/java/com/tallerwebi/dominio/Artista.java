package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Artista {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    private String nacionalidad;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(length = 2000)
    private String biografia;
    private String fotoUrl;
    private String instagramUrl;
    private String twitterUrl;
    private String sitioWebUrl;
    private String estiloArtistico;
    private String contactoEmail;
    
    @OneToMany(mappedBy = "artista")
    private List<Obra> obras = new ArrayList<>();

    public Artista() { }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getSitioWebUrl() {
        return sitioWebUrl;
    }

    public void setSitioWebUrl(String sitioWebUrl) {
        this.sitioWebUrl = sitioWebUrl;
    }

    public String getEstiloArtistico() {
        return estiloArtistico;
    }

    public void setEstiloArtistico(String estiloArtistico) {
        this.estiloArtistico = estiloArtistico;
    }

    public String getContactoEmail() {
        return contactoEmail;
    }

    public void setContactoEmail(String contactoEmail) {
        this.contactoEmail = contactoEmail;
    }

    public List<Obra> getObras() {
        return obras;
    }

    public void agregarObra(Obra obra) {
        this.obras.add(obra);
        obra.setArtista(this); // asegura la relación bidireccional
    }

    public void quitarObra(Obra obra) {
        this.obras.remove(obra);
        obra.setArtista(null);
    }
}