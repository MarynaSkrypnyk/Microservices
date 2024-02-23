package productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private static final AtomicInteger idCounter = new AtomicInteger();
    private int id;
    private String name;
    private String description;
    private double price;
    private double cashback;

    public Product(String name, String description, double price, double cashback) {
        this.id = idCounter.incrementAndGet();
        this.name = name;
        this.description = description;
        this.price = price;
        this.cashback = cashback;
    }
}

