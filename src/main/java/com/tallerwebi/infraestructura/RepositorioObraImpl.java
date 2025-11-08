package com.tallerwebi.infraestructura;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.entidades.Obra;
import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.enums.Categoria;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositorioObraImpl implements RepositorioObra {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioObraImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override // testeado
    public Obra guardar(Obra obra) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(obra);
        return obra;
    }

    @Override // testeado
    public List<Obra> obtenerTodas() {
        try {
            return this.sessionFactory.getCurrentSession()
                    .createQuery("FROM Obra", Obra.class)
                    .getResultList();
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    @Override // testeado
    public List<Obra> obtenerPorAutor(String autor) {
        try {
            return this.sessionFactory.getCurrentSession()
                    .createQuery("FROM Obra WHERE lower(autor) LIKE :autor", Obra.class)
                    .setParameter("autor", "%" + autor.toLowerCase() + "%")
                    .getResultList();
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    @Override // testeado
    public List<Obra> obtenerPorCategoria(Categoria categoria) {
        try {
            return this.sessionFactory.getCurrentSession()
                    .createQuery("SELECT DISTINCT o FROM Obra o JOIN o.categorias c WHERE c = :categoria", Obra.class)
                    .setParameter("categoria", Categoria.valueOf(categoria.name()))
                    .getResultList();
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    @Override // testeado
    public Obra obtenerPorId(Long id) {
        try {
            return this.sessionFactory.getCurrentSession()
                    // se usa left join fetch para inicializar la coleccion usuariosQueDieronLike y
                    // evitar problemas de LazyInitializationException
                    .createQuery("FROM Obra o LEFT JOIN FETCH o.usuariosQueDieronLike WHERE o.id = :id", Obra.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


//    @Override
//    public List<Obra> buscarPorTitulo(String titulo) {
//        try {
//            return this.sessionFactory.getCurrentSession()
//                    .createQuery("FROM Obra WHERE lower(titulo) LIKE :titulo", Obra.class)
//                    .setParameter("titulo", "%" + titulo.toLowerCase() + "%")
//                    .getResultList();
//        } catch (IllegalArgumentException e) {
//            return new ArrayList<>();
//        }
//    }

    @Override
    public List<Obra> obtenerPorRangoDePrecio(Double precioMin, Double precioMax) {
        try {
            // Buscar obras que tengan al menos un formato con precio en el rango especificado
            return this.sessionFactory.getCurrentSession()
                    .createQuery("SELECT DISTINCT o FROM Obra o JOIN o.formatos f WHERE f.precio BETWEEN :precioMin AND :precioMax", Obra.class)
                    .setParameter("precioMin", precioMin)
                    .setParameter("precioMax", precioMax)
                    .getResultList();
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

//    @Override
//    public List<Obra> buscarPorDescripcion(String descripcion) {
//        try {
//            return this.sessionFactory.getCurrentSession()
//                    .createQuery("FROM Obra WHERE lower(descripcion) LIKE :descripcion", Obra.class)
//                    .setParameter("descripcion", "%" + descripcion.toLowerCase() + "%")
//                    .getResultList();
//        } catch (IllegalArgumentException e) {
//            return new ArrayList<>();
//        }
//    }

    private String obtenerCategoriaEnumSiUnica(String entrada) {
        if (entrada == null || entrada.isBlank()) {
            return null;
        }

        String normalizadaEntrada = normalizar(entrada);
        List<Categoria> coincidencias = new ArrayList<>();

        for (Categoria c : Categoria.values()) {
            String normalizadaCategoria = normalizar(c.getCategoria());
            if (normalizadaCategoria.contains(normalizadaEntrada)) {
                coincidencias.add(c);
            }
        }

        // Solo devolver si hay UNA coincidencia exacta o parcial única
        return coincidencias.size() == 1 ? coincidencias.get(0).name() : null;
    }

    private String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "") // quita tildes
                .toLowerCase()
                .trim();
    }

    @Override
    public List<Obra> buscarPorString(String palabraBuscada) {
        try {
            String categoriaEnum = obtenerCategoriaEnumSiUnica(palabraBuscada);

            String query = "SELECT DISTINCT o FROM Obra o " +
                    "LEFT JOIN FETCH o.usuariosQueDieronLike u " + // <-- fetch join
                    "LEFT JOIN o.categorias c " +
                    "LEFT JOIN o.artista a " +
                    "WHERE lower(o.titulo) LIKE :palabra " +
                    "OR lower(o.descripcion) LIKE :palabra " +
                    "OR lower(a.nombre) LIKE :palabra";

            if (categoriaEnum != null) {
                query += " OR c = :categoriaEnum";
            }

            var q = this.sessionFactory.getCurrentSession()
                    .createQuery(query, Obra.class)
                    .setParameter("palabra", "%" + palabraBuscada.toLowerCase() + "%");

            if (categoriaEnum != null) {
                q.setParameter("categoriaEnum", Categoria.valueOf(categoriaEnum));
            }

            return q.getResultList();

        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }
}
