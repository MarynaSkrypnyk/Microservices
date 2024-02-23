package productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import productservice.util.ExternalService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    public boolean isTokenValid(String token) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(ExternalService.LOGIN.getUrl()))
                .header("Authorization", token)
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
        ValidationResult result = null;
        try {
            result = mapper.readValue(jsonString, ValidationResult.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result.isValid();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ValidationResult {
        private boolean valid;
    }
}
