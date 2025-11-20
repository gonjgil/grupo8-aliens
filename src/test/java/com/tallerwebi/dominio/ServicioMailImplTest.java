package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.enums.Formato;
import com.tallerwebi.dominio.servicioImpl.ServicioMailImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ServicioMailImplTest {

    private ServicioMailImpl servicioMail;
    private Mailer mailerMock;

    @BeforeEach
    void setUp() {
        servicioMail = new ServicioMailImpl(null);   // SpringTemplateEngine no se usa

        mailerMock = mock(Mailer.class);

        ReflectionTestUtils.setField(servicioMail, "mailer", mailerMock);
        ReflectionTestUtils.setField(servicioMail, "remitente", "test@example.com");
    }


    // -------------------------------------------------------------------------
    // TEST 1 — enviarMail
    // -------------------------------------------------------------------------
    @Test
    void deberiaEnviarMailCorrectamente() {

        String para = "destino@mail.com";
        String asunto = "Asunto";
        String cuerpo = "<p>Hola</p>";

        servicioMail.enviarMail(para, asunto, cuerpo);

        ArgumentCaptor<Email> captor = ArgumentCaptor.forClass(Email.class);
        verify(mailerMock, times(1)).sendMail(captor.capture());

        Email email = captor.getValue();

        assertThat(email.getSubject(), is(equalTo(asunto)));
        assertThat(email.getHTMLText(), is(equalTo(cuerpo)));
    }


    // -------------------------------------------------------------------------
    // TEST 2 — enviarMailConfirmacionCompra
    // -------------------------------------------------------------------------

    @Test
    void deberiaEnviarMailDeConfirmacionDeCompra() {

        // ---------- Datos del usuario ----------
        Usuario usuario = new Usuario();
        usuario.setEmail("cliente@correo.com");

        // ---------- Datos de la compra ----------
        CompraHecha compra = new CompraHecha();
        compra.setId(99L);
        compra.setPrecioFinal(15000.0);

        // ---------- Item 1 ----------
        Obra o1 = new Obra();
        o1.setTitulo("Obra Uno");
        o1.setImagenUrl("http://img.com/uno.jpg");

        ItemCompra i1 = new ItemCompra();
        i1.setObra(o1);
        i1.setCantidad(2);
        i1.setPrecioUnitario(3500.00);
        Formato f1 = Formato.ORIGINAL;
        i1.setFormato(f1);

        // ---------- Item 2 ----------
        Obra o2 = new Obra();
        o2.setTitulo("Obra Dos");
        o2.setImagenUrl("http://img.com/dos.jpg");

        ItemCompra i2 = new ItemCompra();
        i2.setObra(o2);
        i2.setCantidad(1);
        i2.setPrecioUnitario(8000.00);
        Formato f2 = Formato.DIGITAL;
        i2.setFormato(f2);

        // ---------- Lista ----------
        List<ItemCompra> items = List.of(i1, i2);

        // Act
        servicioMail.enviarMailConfirmacionCompra(usuario, compra, items);

        // Assert
        ArgumentCaptor<Email> captor = ArgumentCaptor.forClass(Email.class);
        verify(mailerMock, times(1)).sendMail(captor.capture());

        Email email = captor.getValue();
        String html = email.getHTMLText();

        // ---------- Validaciones del mail ----------
        assertThat(email.getSubject(), is(equalTo("Confirmación de compra")));

        // ID de compra
        assertThat(html.contains("#99"), is(true));

        // Total formateado (AR locale → sin decimales)
        assertThat(html.contains("$15.000"), is(true));

        // Título de obras
        assertThat(html.contains("Obra Uno"), is(true));
        assertThat(html.contains("Obra Dos"), is(true));

        // Subtotales
        assertThat(html.contains("$7.000"), is(true));
        assertThat(html.contains("$8.000"), is(true));

        // Formatos
        assertThat(html.contains("Original"), is(true));
        assertThat(html.contains("Digital"), is(true));

        // Cantidades
        assertThat(html.contains(">2<"), is(true));
        assertThat(html.contains(">1<"), is(true));
    }
}

