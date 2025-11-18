package com.tallerwebi.dominio.entidades;

import com.tallerwebi.dominio.enums.Provincia;

import javax.persistence.*;

@Entity
@Table(name = "Direccion")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String tipoDomicilio; // Casa, trabajo, vecino, etc
    private String nombreCalle;
    private Integer altura;
    private String piso;
    private String departamento;
    private String codigoPostal;
    private String ciudadBarrio;
    private Boolean predeterminada = false;
    private Double costoEnvio;

    @Enumerated(EnumType.STRING)
    private Provincia provincia;

    private String nombreContacto;
    private Integer telefonoContacto;

    public Direccion(Long id, Usuario usuario, String tipoDomicilio, String nombreCalle, Integer altura,
                     String piso, String departamento, String codigoPostal, String ciudadBarrio,
                     Provincia provincia, String nombreContacto, Integer telefonoContacto) {
        this.id = id;
        this.usuario = usuario;
        this.tipoDomicilio = tipoDomicilio;
        this.nombreCalle = nombreCalle;
        this.altura = altura;
        this.piso = piso;
        this.departamento = departamento;
        this.codigoPostal = codigoPostal;
        this.ciudadBarrio = ciudadBarrio;
        this.provincia = provincia;
        this.nombreContacto = nombreContacto;
        this.telefonoContacto = telefonoContacto;
        this.costoEnvio = calcularCostoEnvioPorCP(codigoPostal);
    }

    public Direccion() {
    }

    public Direccion(Long id) {
    this.id = id;
    }

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getTipoDomicilio() { return tipoDomicilio; }
    public void setTipoDomicilio(String tipoDomicilio) { this.tipoDomicilio = tipoDomicilio; }

    public String getNombreCalle() { return nombreCalle; }
    public void setNombreCalle(String nombreCalle) { this.nombreCalle = nombreCalle; }

    public Integer getAltura() { return altura; }
    public void setAltura(Integer altura) { this.altura = altura; }

    public String getPiso() { return piso; }
    public void setPiso(String piso) { this.piso = piso; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getCiudadBarrio() { return ciudadBarrio; }
    public void setCiudadBarrio(String ciudadBarrio) { this.ciudadBarrio = ciudadBarrio; }

    public Boolean getPredeterminada() { return this.predeterminada; }
    public void setPredeterminada(Boolean predeterminado) { this.predeterminada = predeterminado; }
    public Provincia getProvincia() { return provincia; }
    public void setProvincia(Provincia provincia) { this.provincia = provincia; }

    public String getNombreContacto() { return nombreContacto; }
    public void setNombreContacto(String nombreContacto) { this.nombreContacto = nombreContacto; }

    public Integer getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(Integer telefonoContacto) { this.telefonoContacto = telefonoContacto; }

    public Double getCostoEnvio() { return calcularCostoEnvioPorCP(this.codigoPostal); }
    public Double calcularCostoEnvioPorCP(String codigoPostal) {
        if (codigoPostal == null || codigoPostal.trim().length() < 4) {
            return 0.0;
        }

        int cp = Integer.parseInt(codigoPostal.substring(0, 4));

        // CABA
        if (cp >= 1000 && cp <= 1499) {
            return 8000.0;
        }

        // Provincia de Buenos Aires y Santa Fe
        if ((cp >= 1600 && cp <= 1999) || (cp >= 2000 && cp <= 2999)) {
            return 11000.0;
        }

        // Entre Ríos y Santa Fe
        if (cp >= 3000 && cp <= 3999) {
            return 13000.0;
        }

        // Tucumán, Salta, Jujuy, Santiago del Estero
        if (cp >= 4000 && cp <= 4999) {
            return 15000.0;
        }

        // Córdoba y Cuyo
        if (cp >= 5000 && cp <= 5999) {
            return 14000.0;
        }

        // La Rioja, Catamarca y Santiago del Estero
        if (cp >= 6000 && cp <= 6999) {
            return 15000.0;
        }

        // Chaco, Corrientes, Misiones y Formosa
        if (cp >= 7000 && cp <= 7999) {
            return 15000.0;
        }

        // Patagonia norte
        if (cp >= 8000 && cp <= 8999) {
            return 18000.0;
        }

        // Patagonia sur
        if (cp >= 9000 && cp <= 9999) {
            return 18000.0;
        }

        return 12000.0;
    }
}