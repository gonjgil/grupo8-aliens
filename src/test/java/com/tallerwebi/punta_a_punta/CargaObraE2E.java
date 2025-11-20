package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class CargaObraE2E {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;

    VistaLogin vistaLogin;
    VistaGaleria vistaGaleria;
    VistaUsuario vistaUsuario;
    VistaNuevoArtista vistaNuevoArtista;
    VistaPerfilArtista vistaPerfilArtista;
    VistaNuevaObra vistaNuevaObra;
    VistaObra vistaObra;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(500));
    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        ReiniciarDB.limpiarBaseDeDatos();

        context = browser.newContext();
        Page page = context.newPage();

        // 🔥 Ninguna vista navega en su constructor
        vistaLogin = new VistaLogin(page);
        vistaGaleria = new VistaGaleria(page);
        vistaUsuario = new VistaUsuario(page);
        vistaNuevoArtista = new VistaNuevoArtista(page);
        vistaPerfilArtista = new VistaPerfilArtista(page);
        vistaNuevaObra = new VistaNuevaObra(page);
        vistaObra = new VistaObra(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void deberiaPermitirCrearUnArtistaYUnaObra() throws Exception {
        dadoQueElUsuarioIniciaSesion("test@unlam.edu.ar", "test");
        entoncesDeberiaSerRedirigidoALaVistaDeGaleria();

        cuandoElUsuarioNavegaASuPerfil();
        entoncesDeberiaEstarEnLaVistaUsuario();

        cuandoElUsuarioAccedeACrearUnNuevoPerfilDeArtista();
        entoncesDeberiaEstarEnLaVistaNuevoArtista();

        dadoQueElUsuarioCompletaLosDatosDeArtista();
        cuandoElUsuarioGuardaElPerfilDeArtista();
        entoncesDeberiaSerRedirigidoALaVistaPerfilArtista();

        cuandoElUsuarioAccedeACrearUnaNuevaObra();
        entoncesDeberiaEstarEnLaVistaNuevaObra();

        dadoQueElUsuarioCompletaLosDatosDeLaObra();
        cuandoElUsuarioGuardaLaObra();
        entoncesLaObraDeberiaHaberseCreadoCorrectamente();
    }

    // ============================================================
    // HELPERS
    // ============================================================

    private void dadoQueElUsuarioIniciaSesion(String email, String clave) {
        vistaLogin.ir();  // 🔥 AHORA SÍ navega
        vistaLogin.escribirEMAIL(email);
        vistaLogin.escribirClave(clave);
        vistaLogin.darClickEnIniciarSesion();
    }

    private void entoncesDeberiaSerRedirigidoALaVistaDeGaleria() throws Exception {
        URL url = vistaGaleria.obtenerURLActual();
        assertThat(url.getPath(), matchesPattern("^/spring/galeria(?:;jsessionid=[^/\\s]+)?$"));
    }

    private void cuandoElUsuarioNavegaASuPerfil() {
        vistaGaleria.darClickEnBotonPerfilUsuario();
    }

    private void entoncesDeberiaEstarEnLaVistaUsuario() throws Exception {
        URL url = vistaUsuario.obtenerURLActual();
        assertThat(url.getPath(), matchesPattern("^/spring/usuario(?:;jsessionid=[^/\\s]+)?$"));
    }

    private void cuandoElUsuarioAccedeACrearUnNuevoPerfilDeArtista() {
        vistaUsuario.darClickEnBotonNuevoArtista();
    }

    private void entoncesDeberiaEstarEnLaVistaNuevoArtista() throws Exception {
        URL url = vistaNuevoArtista.obtenerURLActual();
        assertThat(url.getPath(), matchesPattern("^/spring/perfilArtista/nuevo(?:;jsessionid=[^/\\s]+)?$"));
    }

    private void dadoQueElUsuarioCompletaLosDatosDeArtista() {
        vistaNuevoArtista.escribirNombre("Juan Artista");
        vistaNuevoArtista.escribirBiografia("Biografía del artista E2E");
        vistaNuevoArtista.escribirUrlInstagram("https://instagram.com/e2e");
        vistaNuevoArtista.escribirUrlFacebook("https://facebook.com/e2e");
        vistaNuevoArtista.escribirUrlTwitter("https://twitter.com/e2e");
    }

    private void cuandoElUsuarioGuardaElPerfilDeArtista() {
        vistaNuevoArtista.darClickEnBotonGuardar();
    }

    private void entoncesDeberiaSerRedirigidoALaVistaPerfilArtista() throws Exception {
        URL url = vistaPerfilArtista.obtenerURLActual();
        assertThat(url.getPath(), matchesPattern("^/spring/perfilArtista/ver/\\d+(?:;jsessionid=[^/\\s]+)?$"));
    }

    private void cuandoElUsuarioAccedeACrearUnaNuevaObra() {
        vistaPerfilArtista.darClickEnBotonNuevaObra();
    }

    private void entoncesDeberiaEstarEnLaVistaNuevaObra() throws Exception {
        URL url = vistaNuevaObra.obtenerURLActual();
        assertThat(url.getPath(), matchesPattern("^/spring/obra/nueva(?:;jsessionid=[^/\\s]+)?$"));
    }

    private void dadoQueElUsuarioCompletaLosDatosDeLaObra() {
        URL recurso = Objects.requireNonNull(
                getClass().getClassLoader()
                        .getResource("com/tallerwebi/punta_a_punta/imagenes/01.jpg")
        );

        File imagen;
        try {
            imagen = Paths.get(recurso.toURI()).toFile();  // ← ESTO FUNCIONA EN WINDOWS
        } catch (URISyntaxException e) {
            throw new RuntimeException("No se pudo cargar la imagen de prueba", e);
        }

        vistaNuevaObra.cargarImagenObra(imagen);
        vistaNuevaObra.escribirTitulo("Obra E2E");
        vistaNuevaObra.escribirDescripcion("Descripcion de prueba E2E");
    }

    private void cuandoElUsuarioGuardaLaObra() {
        vistaNuevaObra.darClickEnBotonCrearObra();
    }

    private void entoncesLaObraDeberiaHaberseCreadoCorrectamente() throws Exception {
        URL url = vistaObra.obtenerURLActual();
        assertThat(url.getPath(), matchesPattern("^/spring/obra/\\d+(?:;jsessionid=[^/\\s]+)?$"));

        String titulo = vistaObra.obtenerTituloDeObra();
        assertThat(titulo, equalTo("Obra E2E"));

        String descripcion = vistaObra.obtenerDescripcionDeObra();
        assertThat(descripcion, equalTo("Descripcion de prueba E2E"));
    }
}

