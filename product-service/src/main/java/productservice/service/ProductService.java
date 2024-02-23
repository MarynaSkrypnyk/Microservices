package productservice.service;

import productservice.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final List<Product> products;
    private final CashbackService cashbackService = new CashbackService();

    public ProductService() {
        products = new ArrayList<>();
        products.add(new Product(1, "Iphone1", "New", 12.12, 10.10));
        products.add(new Product(2, "Iphone2", "was in use", 15.15, 15.15));
        products.add(new Product(3, "Iphone3", "was in use", 20.20, 20.20));
    }

    public void save(Product product) {
        products.add(product);
    }

    public Product updateProduct(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getId() == updatedProduct.getId()) {
                products.set(i, updatedProduct);
                return product;
            }
        }
        return updatedProduct;
    }

    public Product delete(int id) {
        Product foundProduct = products.stream().filter(product -> product.getId() == id).findAny().orElseThrow();
        products.remove(foundProduct);
        return foundProduct;
    }

    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                setCashback(product);
                return product;
            }
        }
        return null;
    }

    public List<Product> getAllProducts() {
        for (Product product : products) {
            setCashback(product);
        }
        return products;
    }

    private void setCashback(Product product) {
        product.setCashback(getCashback(product));
    }

    private double getCashback(Product product) {
        return cashbackService.getCashback(product.getName());
    }
}

