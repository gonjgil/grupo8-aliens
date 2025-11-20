package com.tallerwebi.dominio;

import com.tallerwebi.dominio.servicioImpl.ServicioPasswordImpl;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ServicioPasswordImplTest {

    private final ServicioPasswordImpl servicioPassword = new ServicioPasswordImpl();

    @Test
    public void queSePuedaHashearUnaPassword() {
        String password = "miClaveSegura123";

        String hash = servicioPassword.hashearPassword(password);

        assertThat(hash, is(notNullValue()));
        assertThat(hash, is(not(emptyString())));
        assertThat(hash, is(not(equalTo(password))));
    }

    @Test
    public void queSePuedaVerificarUnaPasswordCorrecta() {
        String password = "claveSegura";
        String hash = servicioPassword.hashearPassword(password);

        boolean resultado = servicioPassword.verificarPassword(password, hash);

        assertThat(resultado, is(true));
    }

    @Test
    public void queNoPaseLaVerificacionConPasswordIncorrecta() {
        String password = "correcta";
        String hash = servicioPassword.hashearPassword(password);

        boolean resultado = servicioPassword.verificarPassword("incorrecta", hash);

        assertThat(resultado, is(false));
    }

    @Test
    public void queElHashGeneradoSeaBcrypt() {
        String password = "123456";

        String hash = servicioPassword.hashearPassword(password);

        // Bcrypt: empieza con "$2" (puede ser $2a$, $2b$ o $2y$)
        assertThat(hash.startsWith("$2"), is(true));
    }
}
