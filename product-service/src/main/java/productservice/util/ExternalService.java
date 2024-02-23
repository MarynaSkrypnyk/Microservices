package productservice.util;

import lombok.Getter;

@Getter
public enum ExternalService {
    LOGIN("http://localhost:8080/validate"),
    CASHBACK("http://localhost:8082/cashback?productCashback=");

    private final String url;

    ExternalService(String url) {
        this.url = url;
    }
}
