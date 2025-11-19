package com.tallerwebi.dominio.servicioImpl;


import com.tallerwebi.dominio.ServicioMail;
import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.entidades.ItemCompra;
import com.tallerwebi.dominio.entidades.Usuario;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;


import java.util.List;

@Service
public class ServicioMailImpl implements ServicioMail {

    private final Mailer mailer;
    private final SpringTemplateEngine templateEngine;

    private final String remitente;
    private final String nombreRemitente = "ArtRoom";

    @Autowired
    public ServicioMailImpl(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;

        String mailHost = System.getenv().getOrDefault("MAIL_HOST", "smtp.gmail.com");
        int mailPort = Integer.parseInt(System.getenv().getOrDefault("MAIL_PORT", "587"));
        this.remitente = System.getenv().getOrDefault("MAIL_USERNAME", "test@example.com");
        String mailPassword = System.getenv().getOrDefault("MAIL_PASSWORD", "password");

        this.mailer = MailerBuilder
                .withSMTPServer(mailHost, mailPort, remitente, mailPassword)
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer();
    }

    @Override
    public void enviarMail(String para, String asunto, String cuerpoHtml) {
        Email email = EmailBuilder.startingBlank()
                .from(nombreRemitente, remitente)
                .to(para)
                .withSubject(asunto)
                .withHTMLText(cuerpoHtml)
                .buildEmail();

        mailer.sendMail(email);
    }

    @Override
    public void enviarMailConfirmacionCompra(Usuario usuario, CompraHecha compra, List<ItemCompra> items) {
        Context context = new Context();
        context.setVariable("usuario", usuario);
        context.setVariable("compra", compra);
        context.setVariable("items", items);

        // Renderiza confirmación_compra.html
        String cuerpoHtml = templateEngine.process("confirmacion_compra", context);

        enviarMail(usuario.getEmail(), "Confirmación de tu compra", cuerpoHtml);
    }
}