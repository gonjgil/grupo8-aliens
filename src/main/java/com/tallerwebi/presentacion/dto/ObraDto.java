package com.tallerwebi.presentacion.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.presentacion.FormatoObraDto;

public class ObraDto {
    private Long id;
    private String titulo;
    private String autor;
    private String imagenUrl;
    private String descripcion;
    private Integer stock;
    private Set<Usuario> usuariosQueDieronLike;
    private Set<Categoria> categorias = new HashSet<>();
    private List<FormatoObraDto> formatos;
    private PerfilArtistaDTO artista;

    public ObraDto(Obra obra) {
        this.id = obra.getId();
        this.titulo = obra.getTitulo();
        this.imagenUrl = obra.getImagenUrl();
        this.descripcion = obra.getDescripcion();
        this.stock = obra.getStock();
        this.usuariosQueDieronLike = obra.getUsuariosQueDieronLike() != null ? obra.getUsuariosQueDieronLike() : new HashSet<>();
        this.categorias = obra.getCategorias();
        this.autor = obra.getAutor();
        if (obra.getFormatos() != null) {
            this.formatos = obra.getFormatos().stream()
                .map(FormatoObraDto::new)
                .collect(Collectors.toList());
        }
        if (obra.getArtista() != null) {
            this.artista = new PerfilArtistaDTO(obra.getArtista());
        }
    }

    public Long getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Set<Usuario> getUsuariosQueDieronLike() { return this.usuariosQueDieronLike; }
    public void setUsuariosQueDieronLike(Set<Usuario> usuariosQueDieronLike) { this.usuariosQueDieronLike = usuariosQueDieronLike; }
    public int getCantidadLikes() {
        return this.usuariosQueDieronLike != null ? this.usuariosQueDieronLike.size() : 0;
    }

    public Set<Categoria> getCategorias() { return categorias; }
    public void setCategorias(Set<Categoria> categorias) { this.categorias = categorias; }

    public void setStock(Integer stock) {this.stock = stock; }
    public Integer getStock() {return stock;}

    public List<FormatoObraDto> getFormatos() { return formatos; }
    public void setFormatos(List<FormatoObraDto> formatos) { this.formatos = formatos; }

    public PerfilArtistaDTO getArtista() { return artista; }
    public void setArtista(PerfilArtistaDTO artista) { this.artista = artista; }

    public Obra toObra() {
        Obra obra = new Obra(this.titulo, this.autor, this.imagenUrl, this.descripcion, this.stock, this.categorias, this.artista.toArtista());
        obra.setId(this.id);
        obra.setUsuariosQueDieronLike(this.usuariosQueDieronLike);
        return obra;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ObraDto obraDto = (ObraDto) o;
        return Objects.equals(id, obraDto.id) && Objects.equals(titulo, obraDto.titulo) && Objects.equals(autor, obraDto.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, autor);
    }
}