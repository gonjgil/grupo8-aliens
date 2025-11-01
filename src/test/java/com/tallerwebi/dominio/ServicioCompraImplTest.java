package com.tallerwebi.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import com.tallerwebi.dominio.servicioImpl.ServicioCompraImpl;
import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.enums.Formato;

public class ServicioCompraImplTest {

    @Test
    public void queSeVeanLosFormatosDisponiblesParaComprar() {
        List<Formato> formatos = new ServicioCompraImpl().mostrarFormatos();
        assertThat(formatos.size(), is(Formato.values().length));
        for (Formato formato : Formato.values()) {
            assertThat(formatos.contains(formato), is(true));
        }
    }

}
