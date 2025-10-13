package com.tallerwebi.dominio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class EuropeanaServiceTest {

    @Test
    void buscarObras_deberiaRetornarJsonValido() throws Exception  {
        String apiKey = "FAKE_KEY";
        String query = "art";
        String fakeJson = "{\"items\":[]}";
        HttpClient httpClient = mock(HttpClient.class);
        HttpResponse<String> httpResponse = mock(HttpResponse.class);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(fakeJson);
        
        EuropeanaService service = new EuropeanaService(apiKey, httpClient);

        String result = service.buscarObras(query);

        assertNotNull(result);
        assertTrue(result.contains("items"));
    }

    @Test
    void buscarObras_deberiaManejarErrorDeApi() throws Exception {
        String apiKey = "FAKE_KEY";
        String query = "art";
        HttpClient restTemplateMock = mock(HttpClient.class);
        
        when(restTemplateMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("API error"));

        EuropeanaService service = new EuropeanaService(apiKey, restTemplateMock);

        assertThrows(RuntimeException.class, () -> service.buscarObras(query));
    }
}
