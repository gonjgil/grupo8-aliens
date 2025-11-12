package com.tallerwebi.dominio.servicioImpl;


import com.tallerwebi.dominio.ServicioMail;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.stereotype.Service;

@Service
public class ServicioMailImpl implements ServicioMail {
    private final Mailer mailer;
    private final String remitente;
    private final String nombreRemitente = "ArtRoom";

    public ServicioMailImpl() {

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
    public void enviarMail(String destinatario, String asunto, String cuerpo) {
        Email email = EmailBuilder.startingBlank()
                .from(nombreRemitente, remitente)
                .to(destinatario)
                .withSubject(asunto)
                .withPlainText(cuerpo)
                .buildEmail();

        mailer.sendMail(email);
    }
}