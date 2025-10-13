package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class EuropeanaService {
    private final String apiKey;
    private final HttpClient httpClient;
    private static final String BASE_URL = "https://api.europeana.eu/record/v2/search.json";

    // Constructor para producción (Spring)
    public EuropeanaService() {
        this.apiKey = "nicheoph";
        this.httpClient = HttpClient.newHttpClient();
    }

    // Constructor para test
    public EuropeanaService(String apiKey, HttpClient httpClient) {
        this.apiKey = apiKey;
        this.httpClient = httpClient;
    }

    public String buscarObras(String query) {
        try {
            String url = BASE_URL + "?wskey=" + apiKey + "&query=" + query + "&media=true";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            throw new RuntimeException("Error consultando Europeana API", e);
        }
    }
}