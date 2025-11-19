package com.tallerwebi.dominio.servicioImpl;


import com.tallerwebi.dominio.ServicioMail;
import com.tallerwebi.dominio.entidades.CompraHecha;
import com.tallerwebi.dominio.entidades.ItemCarrito;
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


import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
public class ServicioMailImpl implements ServicioMail {

    private final Mailer mailer;

    private final String remitente;
    private final String nombreRemitente = "ArtRoom";

    @Autowired
    public ServicioMailImpl(SpringTemplateEngine templateEngine) {

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

        NumberFormat formatoMoneda = NumberFormat.getNumberInstance(new Locale("es", "AR"));
        formatoMoneda.setMaximumFractionDigits(0);

        StringBuilder tablaItems = new StringBuilder();

        for (ItemCompra item : items) {

            // 🔹 Formateo del subtotal sin decimales
            String subtotalFormateado = "$" + formatoMoneda.format(item.getSubtotal());

            tablaItems.append(
                    "<tr>" +
                            "<td style='padding:12px;border-bottom:1px solid #eee;display:flex;align-items:center;gap:12px;'>" +
                            "<img src='" + item.getObra().getImagenUrl() + "' alt='Imagen' width='60' height='60' style='border-radius:6px;object-fit:cover;' />" +
                            "<span style='font-size:15px;color:#333;'>" + item.getObra().getTitulo() + "</span>" +
                            "</td>" +

                            // 🔹 Nueva columna FORMATO
                            "<td style='padding:12px;border-bottom:1px solid #eee;font-size:15px;color:#333;'>" +
                            item.getFormato() +
                            "</td>" +

                            "<td style='padding:12px;border-bottom:1px solid #eee;font-size:15px;color:#333;'>" +
                            item.getCantidad() +
                            "</td>" +

                            "<td style='padding:12px;border-bottom:1px solid #eee;font-size:15px;color:#333;text-align:right;'>" +
                            subtotalFormateado +
                            "</td>" +
                            "</tr>"
            );
        }

        // 🔹 Formateo precio final
        String totalFormateado = "$" + formatoMoneda.format(compra.getPrecioFinal());

        String cuerpoHtml =
                "<!DOCTYPE html>" +
                        "<html lang='es'>" +
                        "<body style='margin:0;padding:0;font-family:Arial, sans-serif;background-color:#f5f6fa;'>" +

                        "<table width='100%' cellpadding='0' cellspacing='0' style='padding:25px 0;background:#f5f6fa;'>" +
                        "<tr><td align='center'>" +

                        "<table width='600' cellpadding='0' cellspacing='0' style='background:white;border-radius:14px;overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.1);'>" +

                        /* HEADER */
                        "<tr>" +
                        "<td style='background:#111828;padding:28px;text-align:center;color:white;font-size:26px;font-weight:bold;letter-spacing:1px;'>" +
                        "ArtRoom" +
                        "</td>" +
                        "</tr>" +

                        /* SALUDO */
                        "<tr>" +
                        "<td style='padding:35px 40px 10px;color:#333;font-size:16px;line-height:1.6;'>" +
                        "<h2 style='margin:0 0 15px;font-size:22px;color:#111828;'>¡Gracias por tu compra!</h2>" +
                        "<p style='margin:0;'>Tu compra fue registrada y procesada con éxito. Aquí tienes los detalles:</p>" +
                        "</td>" +
                        "</tr>" +

                        /* INFO COMPRA */
                        "<tr>" +
                        "<td style='padding:0 40px 25px;color:#333;font-size:16px;'>" +
                        "<div style='background:#f1f2f6;padding:15px;border-radius:8px;border:1px solid #e0e0e0;margin-top:10px;'>" +
                        "<p style='margin:0 0 6px;'><strong>ID de compra:</strong> #" + compra.getId() + "</p>" +
                        "<p style='margin:3px 0 0;'><strong>Total pagado:</strong> " + totalFormateado + "</p>" +
                        "</div>" +
                        "</td>" +
                        "</tr>" +

                        /* TABLA DE ITEMS */
                        "<tr>" +
                        "<td style='padding:10px 40px 30px;'>" +

                        "<table width='100%' cellpadding='0' cellspacing='0' style='border-collapse:collapse;margin-top:12px;'>" +
                        "<thead>" +
                        "<tr style='background:#111828;color:white;font-size:14px;'>" +
                        "<th style='padding:12px;text-align:left;'>Obra</th>" +
                        "<th style='padding:12px;text-align:left;'>Formato</th>" +   // 🔹 Nueva columna
                        "<th style='padding:12px;text-align:left;'>Cantidad</th>" +
                        "<th style='padding:12px;text-align:right;'>Subtotal</th>" +
                        "</tr>" +
                        "</thead>" +
                        "<tbody>" +
                        tablaItems +
                        "</tbody>" +
                        "</table>" +

                        "</td>" +
                        "</tr>" +

                        /* FOOTER */
                        "<tr>" +
                        "<td style='background:#111828;color:#ccc;text-align:center;padding:20px;font-size:13px;'>" +
                        "© 2025 ArtRoom - Aliens" +
                        "</td>" +
                        "</tr>" +

                        "</table>" +
                        "</td></tr>" +
                        "</table>" +

                        "</body>" +
                        "</html>";

        enviarMail(usuario.getEmail(), "Confirmación de compra", cuerpoHtml);
    }


}