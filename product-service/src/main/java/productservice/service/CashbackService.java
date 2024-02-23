package productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import productservice.model.Cashback;
import productservice.util.ExternalService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CashbackService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    public double getCashback(String productName) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(ExternalService.CASHBACK.getUrl() + productName))
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String jsonString = response.body();
        Cashback cashback = null;
        try {
            cashback = mapper.readValue(jsonString, Cashback.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return cashback.getCashback();
    }
}
