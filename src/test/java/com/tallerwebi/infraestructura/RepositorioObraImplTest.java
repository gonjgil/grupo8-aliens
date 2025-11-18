package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.util.*;

import com.tallerwebi.dominio.entidades.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerwebi.dominio.repositorios.RepositorioObra;
import com.tallerwebi.dominio.enums.Categoria;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.infraestructura.config.HibernateTestInfraestructuraConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateTestInfraestructuraConfig.class })
public class RepositorioObraImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioObra repositorioObra;

    @BeforeEach
    public void init() {
        this.repositorioObra = new RepositorioObraImpl(this.sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaGuardarUnaObra() {
        Artista artista = persistirArtista("Artista de la Obra Guardada");

        Obra obra = generarObra1();
        obra.setTitulo("Titulo de la Obra Guardada");
        obra.setArtista(artista);
        
        this.repositorioObra.guardar(obra);

        String hql = "FROM Obra WHERE titulo = :titulo";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("titulo", "Titulo de la Obra Guardada");

        Obra obraObtenida = (Obra) query.getSingleResult();

        assertThat(obraObtenida, is(equalTo(obra)));
    }

    @Test
    @Transactional
    @Rollback
    public void alListarTodasLasObrasDeberiaObtenerLaListaCompleta() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();

        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        String hql = "FROM Obra";
        TypedQuery<Obra> query = this.sessionFactory.getCurrentSession().createQuery(hql, Obra.class);
        List<Obra> obrasObtenidas = query.getResultList();

        assertThat(obrasObtenidas.size(), is(equalTo(3)));
        assertThat(obrasObtenidas.get(0), is(equalTo(obra1)));
        assertThat(obrasObtenidas.get(1), is(equalTo(obra2)));
        assertThat(obrasObtenidas.get(2), is(equalTo(obra3)));
    }

    @Test
    @Transactional
    @Rollback
    public void alListarObrasPorCategoriaYNoExistirNingunaDeberiaObtenerUnaListaVacia() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.agregarCategoria(Categoria.ABSTRACTO);
        obra2.agregarCategoria(Categoria.SURREALISMO);
        obra3.agregarCategoria(Categoria.RETRATO);

        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        String hql = "FROM Obra WHERE :categoria MEMBER OF categorias";
        TypedQuery<Obra> query = this.sessionFactory.getCurrentSession().createQuery(hql, Obra.class);
        query.setParameter("categoria", Categoria.TEST);
        List<Obra> obrasObtenidas = query.getResultList();

        assertThat(obrasObtenidas.size(), is(equalTo(0)));
    }

    @Test
    @Transactional
    @Rollback
    public void alListarObrasPorAutorDeberiaObtenerSoloTodasSusObras() {
        Artista artista = persistirArtista("Autor de dos Obras");
        Artista artistaOtro = persistirArtista("Autor de una Obra");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.setArtista(artista);
        obra2.setArtista(artistaOtro);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        String hql = "SELECT o FROM Obra o LEFT JOIN o.artista a WHERE a.nombre = :autor";
        TypedQuery<Obra> query = this.sessionFactory.getCurrentSession().createQuery(hql, Obra.class);
        query.setParameter("autor", "Autor de dos Obras");
        List<Obra> obrasObtenidas = query.getResultList();

        assertThat(obrasObtenidas.size(), is(equalTo(2)));
        assertThat(obrasObtenidas.get(0), is(equalTo(obra1)));
        assertThat(obrasObtenidas.get(1), is(equalTo(obra3)));
    }
    
    @Test
    @Transactional
    @Rollback
    public void alListarObrasPorCategoriaDeberiaObtenerSoloTodasLasObrasDeEsaCategoria() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.agregarCategoria(Categoria.ABSTRACTO);
        obra3.agregarCategoria(Categoria.ABSTRACTO);

        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        String hql = "FROM Obra WHERE :categoria MEMBER OF categorias";
        TypedQuery<Obra> query = this.sessionFactory.getCurrentSession().createQuery(hql, Obra.class);
        query.setParameter("categoria", Categoria.ABSTRACTO);
        List<Obra> obrasObtenidas = query.getResultList();

        assertThat(obrasObtenidas.size(), is(equalTo(2)));
        assertThat(obrasObtenidas.get(0), is(equalTo(obra1)));
        assertThat(obrasObtenidas.get(1), is(equalTo(obra3)));
    }

    @Test
    @Transactional
    @Rollback
    public void alBuscarObraPorIdDeberiaObtenerLaObraCorrespondiente() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();

        obra1.setArtista(artista);

        this.repositorioObra.guardar(obra1);

        String hql = "FROM Obra WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", obra1.getId());
        Obra obraObtenida = (Obra) query.getSingleResult();

        assertThat(obraObtenida, is(equalTo(obra1)));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlBuscarPorTituloObtengaLasObrasCorrespondientes() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.setTitulo("Obra Especial");
        obra2.setTitulo("Otra Obra Especial");
        obra3.setTitulo("Obra Común");
        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        List<Obra> obrasObtenidas = this.repositorioObra.buscarPorString("Especial");

        assertThat(obrasObtenidas.size(), is(equalTo(2)));
        assertThat(obrasObtenidas.get(0), is(equalTo(obra1)));
        assertThat(obrasObtenidas.get(1), is(equalTo(obra2)));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlBuscarPorRangoDePrecioObtengaLasObrasCorrespondientes() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();

        FormatoObra formato1 = new FormatoObra(obra1, Formato.ORIGINAL, 1500.0, 5);
        FormatoObra formato2 = new FormatoObra(obra2, Formato.ORIGINAL, 2500.0, 5);
        FormatoObra formato3 = new FormatoObra(obra3, Formato.ORIGINAL, 3500.0, 5);
        
        obra1.agregarFormato(formato1);
        obra2.agregarFormato(formato2);
        obra3.agregarFormato(formato3);

        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        List<Obra> obrasObtenidas = this.repositorioObra.obtenerPorRangoDePrecio(2000.0, 4000.0);

        assertThat(obrasObtenidas.size(), is(equalTo(2)));
        assertThat(obrasObtenidas.get(0), is(equalTo(obra2)));
        assertThat(obrasObtenidas.get(1), is(equalTo(obra3)));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlBuscarPorDescripcionObtengaLasObrasCorrespondientes() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.setDescripcion("Esta es una obra maravillosa");
        obra2.setDescripcion("Una obra que destaca por su belleza");
        obra3.setDescripcion("Obra común sin nada especial");
        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        List<Obra> obrasObtenidas = this.repositorioObra.buscarPorString("obra");

        assertThat(obrasObtenidas.size(), is(equalTo(3)));
        assertThat(obrasObtenidas.get(0), is(equalTo(obra1)));
        assertThat(obrasObtenidas.get(1), is(equalTo(obra2)));
        assertThat(obrasObtenidas.get(2), is(equalTo(obra3)));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlBuscarPorDescripcionYNoEncuentreCoincidenciasDevuelvaUnaListaVacia() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();
        obra1.setDescripcion("Esta es una obra maravillosa");
        obra2.setDescripcion("Una obra que destaca por su belleza");
        obra3.setDescripcion("Obra común sin nada especial");
        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        List<Obra> obrasObtenidas = this.repositorioObra.buscarPorString("inexistente");

        assertThat(obrasObtenidas.size(), is(equalTo(0)));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlBuscarPorRangoDePrecioSinCoincidenciasDevuelvaUnaListaVacia() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();

        FormatoObra formato1 = new FormatoObra(obra1, Formato.ORIGINAL, 1500.0, 5);
        FormatoObra formato2 = new FormatoObra(obra2, Formato.ORIGINAL, 2500.0, 5);
        FormatoObra formato3 = new FormatoObra(obra3, Formato.ORIGINAL, 3500.0, 5);
        
        obra1.agregarFormato(formato1);
        obra2.agregarFormato(formato2);
        obra3.agregarFormato(formato3);

        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        List<Obra> obrasObtenidas = this.repositorioObra.obtenerPorRangoDePrecio(4000.0, 5000.0);

        assertThat(obrasObtenidas.size(), is(equalTo(0)));
    }

    @Test
    @Transactional
    @Rollback
    public void queAlBuscarPorTituloSinCoincidenciasDevuelvaUnaListaVacia() {
        Artista artista = persistirArtista("Artista 1");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        Obra obra3 = generarObra3();

        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);

        this.repositorioObra.guardar(obra1);
        this.repositorioObra.guardar(obra2);
        this.repositorioObra.guardar(obra3);

        List<Obra> obrasObtenidas = this.repositorioObra.buscarPorString("Inexistente");

        assertThat(obrasObtenidas.size(), is(equalTo(0)));
    }

    private Obra generarObra1() {
        Artista artista = new Artista();
        artista.setNombre("Artista de la Obra 1");
        Obra obra1 = new Obra();
        obra1.setTitulo("Titulo de la Obra 1");
        obra1.setArtista(artista);
        obra1.setDescripcion("Descripcion de la Obra 1");
        obra1.setImagenUrl("http://imagen.com/obra1.jpg");
        obra1.agregarCategoria(Categoria.ABSTRACTO);
        return obra1;
    }
    
    private Obra generarObra2() {
        Artista artista = new Artista();
        artista.setNombre("Artista de la Obra 2");
        Obra obra2 = new Obra();
        obra2.setTitulo("Titulo de la Obra 2");
        obra2.setArtista(artista);
        obra2.setDescripcion("Descripcion de la Obra 2");
        obra2.setImagenUrl("http://imagen.com/obra2.jpg");
        obra2.agregarCategoria(Categoria.SURREALISMO);
        return obra2;
    }

    private Obra generarObra3() {
        Artista artista = new Artista();
        artista.setNombre("Autor de la obra 3");
        Obra obra3 = new Obra();
        obra3.setTitulo("Titulo de la Obra 3");
        obra3.setArtista(artista);
        obra3.setDescripcion("Descripcion de la Obra 3");
        obra3.setImagenUrl("http://imagen.com/obra3.jpg");
        obra3.agregarCategoria(Categoria.ABSTRACTO);
        return obra3;
    }

    private Artista persistirArtista(String nombre) {
        Artista a = new Artista();
        a.setNombre(nombre);
        this.sessionFactory.getCurrentSession().save(a);
        return a;
    }

    @Test
    @Transactional
    @Rollback
    void queSePuedaEliminarUnaObraExistente() {
        Artista artista = new Artista();
        artista.setNombre("Artista");

        Obra obra = new Obra();
        obra.setTitulo("Obra para eliminar");
        obra.setArtista(artista);
        repositorioObra.guardar(obra);

        repositorioObra.eliminar(obra);

        Obra obraEliminada = sessionFactory.getCurrentSession().get(Obra.class, obra.getId());
        assertThat(obraEliminada, is(nullValue()));
    }

    @Test
    @Transactional
    @Rollback
    public void queObtengaLasObrasMasVendidasConSusCantidades() {
        Artista artista = persistirArtista("Artista Ventas");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        obra1.setArtista(artista);
        obra2.setArtista(artista);

        sessionFactory.getCurrentSession().save(obra1);
        sessionFactory.getCurrentSession().save(obra2);

        for (int i = 0; i < 3; i++) {
            ItemCompra ic = new ItemCompra();
            ic.setObra(obra1);
            ic.setFormato(Formato.ORIGINAL);
            sessionFactory.getCurrentSession().save(ic);
        }

        ItemCompra ic2 = new ItemCompra();
        ic2.setObra(obra2);
        ic2.setFormato(Formato.DIGITAL);
        sessionFactory.getCurrentSession().save(ic2);

        Map<Obra, Long> resultado = repositorioObra.obtenerMasVendidasPorArtista(artista);

        assertThat(resultado.size(), is(equalTo(2)));

        Iterator<Map.Entry<Obra, Long>> it = resultado.entrySet().iterator();

        Map.Entry<Obra, Long> primero = it.next();
        assertThat(primero.getKey(), is(equalTo(obra1)));
        assertThat(primero.getValue(), is(3L));

        Map.Entry<Obra, Long> segundo = it.next();
        assertThat(segundo.getKey(), is(equalTo(obra2)));
        assertThat(segundo.getValue(), is(1L));
    }

    @Test
    @Transactional
    @Rollback
    public void queDevuelvaUnMapVacioCuandoElArtistaNoTieneVentas() {
        Artista artista = persistirArtista("Artista Sin Ventas");

        Obra obra1 = generarObra1();
        obra1.setArtista(artista);
        sessionFactory.getCurrentSession().save(obra1);

        Map<Obra, Long> resultado = repositorioObra.obtenerMasVendidasPorArtista(artista);

        assertThat(resultado.isEmpty(), is(true));
    }

    @Test
    @Transactional
    @Rollback
    public void queDevuelvaMapVacioCuandoOcurreUnaIllegalArgumentException() {
        Artista artista = new Artista();

        Map<Obra, Long> resultado = repositorioObra.obtenerMasVendidasPorArtista(artista);

        assertThat(resultado.isEmpty(), is(true));
    }

    @Test
    @Transactional
    @Rollback
    public void queObtengaLasObrasMasLikeadasYSuCantidadDeLikes() {
        Artista artista = persistirArtista("Artista");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra1.setUsuariosQueDieronLike(new HashSet<>(generarUsuarios().subList(0, 2)));
        obra2.setUsuariosQueDieronLike(new HashSet<>(generarUsuarios().subList(1, 6)));

        sessionFactory.getCurrentSession().save(obra1);
        sessionFactory.getCurrentSession().save(obra2);

        List<Obra> resultado = repositorioObra.obtenerMasLikeadasPorArtista(artista);

        assertThat(resultado.size(), is(2));
        assertThat(resultado.get(0), is(equalTo(obra2)));
        assertThat(resultado.get(1), is(equalTo(obra1)));
        assertThat(resultado.get(0).getUsuariosQueDieronLike().size(), is(equalTo(5)));
        assertThat(resultado.get(1).getUsuariosQueDieronLike().size(), is(equalTo(2)));
    }

    private List<Usuario> generarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Usuario usuario = new Usuario();
            usuario.setId((long) (i + 1));
            usuario.setEmail((i+1)+"_asd@qwe.com");
            sessionFactory.getCurrentSession().save(usuario);
            usuarios.add(usuario);
        }

        return usuarios;
    }

    private void crearItemsCompras(Obra obra, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            Formato formato = Formato.ORIGINAL;
            ItemCompra ic = new ItemCompra();
            ic.setFormato(formato);
            ic.setObra(obra);
            sessionFactory.getCurrentSession().save(ic);
        }
    }

    @Test
    @Transactional
    @Rollback
    public void queObtengaLasTresCategoriasMasVendidasParaUnArtista() {
        Artista artista = persistirArtista("Artista Top3");

        Obra obra1 = generarObra1(); // ABSTRACTO
        Obra obra2 = generarObra2(); // SURREALISMO
        Obra obra3 = generarObra3(); // ABSTRACTO
        Obra obra4 = new Obra();     // MODERNO, SURREALISMO
        obra4.setTitulo("obra4");
        obra4.setCategorias(new HashSet<>(List.of(Categoria.MODERNO, Categoria.SURREALISMO)));
        obra1.setArtista(artista);
        obra2.setArtista(artista);
        obra3.setArtista(artista);
        obra4.setArtista(artista);

        sessionFactory.getCurrentSession().save(obra1);
        sessionFactory.getCurrentSession().save(obra2);
        sessionFactory.getCurrentSession().save(obra3);
        sessionFactory.getCurrentSession().save(obra4);

        crearItemsCompras(obra1, 4);
        crearItemsCompras(obra2, 1);
        crearItemsCompras(obra3, 3);
        crearItemsCompras(obra4, 2);

        Map<Categoria, Long> resultado = repositorioObra.obtenerTresCategoriasMasVendidasArtista(artista);

        assertThat(resultado.size(), is(3));
        Iterator<Map.Entry<Categoria, Long>> it = resultado.entrySet().iterator();

        Map.Entry<Categoria, Long> first = it.next();
        assertThat(first.getKey(), is(equalTo(Categoria.ABSTRACTO)));
        assertThat(first.getValue(), is(7L));

        Map.Entry<Categoria, Long> second = it.next();
        assertThat(second.getKey(), is(equalTo(Categoria.SURREALISMO)));
        assertThat(second.getValue(), is(3L));

        Map.Entry<Categoria, Long> third = it.next();
        assertThat(third.getKey(), is(equalTo(Categoria.MODERNO)));
        assertThat(third.getValue(), is(2L));
    }

    @Test
    @Transactional
    @Rollback
    public void queDevuelvaMenosDeTresCuandoHayMenosCategoriasVendidas() {
        Artista artista = persistirArtista("Artista 2 categorias");

        Obra obra1 = generarObra1(); // ABSTRACTO
        Obra obra2 = generarObra2(); // SURREALISMO
        obra1.setArtista(artista);
        obra2.setArtista(artista);
        sessionFactory.getCurrentSession().save(obra1);
        sessionFactory.getCurrentSession().save(obra2);

        crearItemsCompras(obra1, 2);
        crearItemsCompras(obra2, 1);

        Map<Categoria, Long> resultado = repositorioObra.obtenerTresCategoriasMasVendidasArtista(artista);

        assertThat(resultado.size(), is(2));
        assertThat(resultado.containsKey(Categoria.ABSTRACTO), is(true));
        assertThat(resultado.containsKey(Categoria.SURREALISMO), is(true));
    }

    @Test
    @Transactional
    @Rollback
    public void queDevuelvaMapaVacioCuandoNoHayVentasParaElArtista() {
        Artista artista = persistirArtista("Artista sin ventas");

        Obra obra1 = generarObra1();
        Obra obra2 = generarObra2();
        obra1.setArtista(artista);
        obra2.setArtista(artista);
        sessionFactory.getCurrentSession().save(obra1);
        sessionFactory.getCurrentSession().save(obra2);

        Map<Categoria, Long> resultado = repositorioObra.obtenerTresCategoriasMasVendidasArtista(artista);

        assertThat(resultado.isEmpty(), is(true));
    }

    @Test
    @Transactional
    @Rollback
    public void queObtengaLasTresCategoriasMasLikeadasParaUnArtista() {
        Artista artista = persistirArtista("Artista Likes");

        // Obra1 -> ABSTRACTO (3 likes)
        Obra obra1 = generarObra1();
        obra1.setArtista(artista);
        obra1.setUsuariosQueDieronLike(
                new HashSet<>(generarUsuarios().subList(0, 3))
        );

        // Obra2 -> SURREALISMO (6 likes)
        Obra obra2 = generarObra2();
        obra2.setArtista(artista);
        obra2.setUsuariosQueDieronLike(
                new HashSet<>(generarUsuarios().subList(1, 7))
        );

        // Obra3 -> ABSTRACTO (2 likes)
        Obra obra3 = generarObra3();
        obra3.setArtista(artista);
        obra3.setUsuariosQueDieronLike(
                new HashSet<>(generarUsuarios().subList(0, 2))
        );

        sessionFactory.getCurrentSession().save(obra1);
        sessionFactory.getCurrentSession().save(obra2);
        sessionFactory.getCurrentSession().save(obra3);

        Map<Categoria, Long> resultado =
                repositorioObra.obtenerTresCategoriasMasLikeadasArtista(artista);

        assertThat(resultado.size(), is(2));

        Iterator<Map.Entry<Categoria, Long>> it = resultado.entrySet().iterator();

        Map.Entry<Categoria, Long> first = it.next();
        assertThat(first.getKey(), is(equalTo(Categoria.SURREALISMO)));
        assertThat(first.getValue(), is(6L));

        Map.Entry<Categoria, Long> second = it.next();
        assertThat(second.getKey(), is(equalTo(Categoria.ABSTRACTO)));
        assertThat(second.getValue(), is(5L));
    }

    @Test
    @Transactional
    @Rollback
    public void queDevuelvaMapaVacioCuandoOcurreUnaIllegalArgumentExceptionEnLikes() {
        Artista artista = new Artista();

        Map<Categoria, Long> resultado =
                repositorioObra.obtenerTresCategoriasMasLikeadasArtista(artista);

        assertThat(resultado.isEmpty(), is(true));
    }

    @Test
    @Transactional
    @Rollback
    public void queObtengaLasObrasTrendingPorVentasEnOrdenDescendiente() {
        // given
        Artista artista = persistirArtista("Artista Trending Ventas");

        Obra obra1 = generarObra1();
        obra1.setArtista(artista);

        Obra obra2 = generarObra2();
        obra2.setArtista(artista);

        Obra obra3 = generarObra3();
        obra3.setArtista(artista);

        sessionFactory.getCurrentSession().save(obra1);
        sessionFactory.getCurrentSession().save(obra2);
        sessionFactory.getCurrentSession().save(obra3);

        crearItemsCompras(obra1, 5); // más vendida
        crearItemsCompras(obra2, 3);
        crearItemsCompras(obra3, 1);

        // when
        List<Obra> resultado = repositorioObra.obtenerTrendingVentasArtista(artista);

        // then
        assertThat(resultado.size(), is(3));
        assertThat(resultado.get(0), is(obra1));
        assertThat(resultado.get(1), is(obra2));
        assertThat(resultado.get(2), is(obra3));
    }

    @Test
    @Transactional
    @Rollback
    public void queDevuelvaListaVaciaSiElArtistaNoTieneVentas() {
        Artista artista = persistirArtista("Artista Sin Ventas");

        Obra obra = generarObra1();
        obra.setArtista(artista);
        sessionFactory.getCurrentSession().save(obra);

        List<Obra> resultado = repositorioObra.obtenerTrendingVentasArtista(artista);

        assertThat(resultado.isEmpty(), is(true));
    }

    @Test
    @Transactional
    @Rollback
    public void queDevuelvaListaVaciaCuandoOcurreUnaIllegalArgumentExceptionEnTrendingVentas() {
        Artista artistaNoPersistido = new Artista();

        List<Obra> resultado = repositorioObra.obtenerTrendingVentasArtista(artistaNoPersistido);

        assertThat(resultado.isEmpty(), is(true));
    }

    @Test
    @Transactional
    @Rollback
    public void queObtengaLasObrasTrendingPorLikesEnOrdenDescendiente() {
        // given
        Artista artista = persistirArtista("Artista Trending Likes");

        Usuario u1 = persistirUsuario("user1@mail.com");
        Usuario u2 = persistirUsuario("user2@mail.com");
        Usuario u3 = persistirUsuario("user3@mail.com");

        Obra obra1 = generarObra1();
        obra1.setArtista(artista);

        Obra obra2 = generarObra2();
        obra2.setArtista(artista);

        Obra obra3 = generarObra3();
        obra3.setArtista(artista);

        sessionFactory.getCurrentSession().save(obra1);
        sessionFactory.getCurrentSession().save(obra2);
        sessionFactory.getCurrentSession().save(obra3);

        obra1.getUsuariosQueDieronLike().add(u1); // 3 likes
        obra1.getUsuariosQueDieronLike().add(u2);
        obra1.getUsuariosQueDieronLike().add(u3);

        obra2.getUsuariosQueDieronLike().add(u1); // 1 like

        obra3.getUsuariosQueDieronLike().add(u1); // 2 likes
        obra3.getUsuariosQueDieronLike().add(u2);

        // when
        List<Obra> resultado = repositorioObra.obtenerTrendingLikesArtista(artista);

        // then
        assertThat(resultado.size(), is(3));
        assertThat(resultado.get(0), is(obra1));
        assertThat(resultado.get(1), is(obra3));
        assertThat(resultado.get(2), is(obra2));
    }

    private Usuario persistirUsuario(String mail) {
        Usuario u = new Usuario();
        u.setEmail(mail);
        this.sessionFactory.getCurrentSession().save(u);
        return u;
    }

    @Test
    @Transactional
    @Rollback
    public void queDevuelvaListaVaciaSiElArtistaNoTieneLikes() {
        Artista artista = persistirArtista("Artista Sin Likes");

        Obra obra = generarObra1();
        obra.setArtista(artista);
        sessionFactory.getCurrentSession().save(obra);

        List<Obra> resultado = repositorioObra.obtenerTrendingLikesArtista(artista);

        assertThat(resultado.size(), is(0));
    }

    @Test
    @Transactional
    @Rollback
    public void queDevuelvaListaVaciaCuandoOcurreUnaIllegalArgumentExceptionEnTrendingLikes() {
        Artista artistaNoPersistido = new Artista();

        List<Obra> resultado = repositorioObra.obtenerTrendingLikesArtista(artistaNoPersistido);

        assertThat(resultado.isEmpty(), is(true));
    }

}
