package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Obra;
import com.tallerwebi.dominio.RepositorioObra;

import com.tallerwebi.dominio.enums.Categoria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository("repositorioObra")
public class RepositorioObraImpl implements RepositorioObra {

    private SessionFactory sessionFactory;
    private List<Obra> obras;

    @Autowired
    public RepositorioObraImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

        this.obras = new ArrayList<>();
        this.obras.add(new Obra(1L, "Abstracta No. 1", "J. Doe", "https://placehold.co/400x400/ef4444/FFF?text=Obra+1", "Una obra de arte abstracta llena de colores vibrantes y formas dinámicas.", Set.of(Categoria.ABSTRACTO, Categoria.MODERNO)));
        this.obras.add(new Obra(2L, "Noche Cósmica", "S. Smith", "https://placehold.co/400x400/10b981/FFF?text=Obra+2", "Una representación artística del cosmos nocturno con estrellas brillantes y nebulosas.", Set.of(Categoria.COSMICO, Categoria.MODERNO)));
        this.obras.add(new Obra(3L, "Retrato de un Sueño", "A. García", "https://placehold.co/400x400/3b82f6/FFF?text=Obra+3", "Un retrato surrealista que captura la esencia de un sueño vívido.", Set.of(Categoria.RETRATO, Categoria.SURREALISMO)));
        this.obras.add(new Obra(4L, "Viaje al Infinito", "L. Wang", "https://placehold.co/400x400/f97316/FFF?text=Obra+4", "Una obra que explora la idea del infinito a través de patrones repetitivos y colores profundos.", Set.of(Categoria.ABSTRACTO, Categoria.COSMICO)));
        this.obras.add(new Obra(5L, "Horizonte Perdido", "M. Khan", "https://placehold.co/400x400/d946ef/FFF?text=Obra+5", "Una pintura que muestra un horizonte misterioso y evocador.", Set.of(Categoria.MODERNO, Categoria.SURREALISMO)));
        this.obras.add(new Obra(6L, "El Alma del Mar", "J. Doe", "https://placehold.co/400x400/84cc16/FFF?text=Obra+6", "Una obra que captura la esencia y el movimiento del mar.", Set.of(Categoria.RETRATO, Categoria.ABSTRACTO)));
        this.obras.add(new Obra(7L, "La Danza del Aire", "S. Smith", "https://placehold.co/400x400/06b6d4/FFF?text=Obra+7", "Una representación artística del aire en movimiento.", Set.of(Categoria.MODERNO, Categoria.ABSTRACTO)));
        this.obras.add(new Obra(8L, "Ciudad Silenciosa", "A. García", "https://placehold.co/400x400/ec4899/FFF?text=Obra+8", "Una pintura que muestra una ciudad en calma y silencio.", Set.of(Categoria.SURREALISMO, Categoria.MODERNO)));
        this.obras.add(new Obra(9L, "Despertar Cromático", "L. Wang", "https://placehold.co/400x400/a855f7/FFF?text=Obra+9", "Una obra llena de colores vivos que simbolizan un despertar.", Set.of(Categoria.ABSTRACTO, Categoria.MODERNO)));
        this.obras.add(new Obra(10L, "Geometría Natural", "M. Khan", "https://placehold.co/400x400/14b8a6/FFF?text=Obra+10", "Una pintura que combina formas geométricas con elementos naturales.", Set.of(Categoria.ABSTRACTO, Categoria.MODERNO)));
    }

    @Override
    public List<Obra> obtenerTodas() {
        return this.obras;
    }

    @Override
    public List<Obra> obtenerPorAutor(String autor) {
        List<Obra> resultado = new ArrayList<>();
        for (Obra obra : this.obras) {
            if (obra.getAutor().equalsIgnoreCase(autor)) {
                resultado.add(obra);
            }
        }
        return resultado;
    }

    @Override
    public List<Obra> obtenerPorCategoria(String categoria) {
        // Implementacion pendiente
        return Collections.emptyList();
    }

     @Override
     public Obra obtenerPorId(Long id) {
        for (Obra obra : this.obras) {
            if (obra.getId().equals(id)) {
                return obra;
            }
        }
        return null; // o lanzar una excepción si no se encuentra
     }
}