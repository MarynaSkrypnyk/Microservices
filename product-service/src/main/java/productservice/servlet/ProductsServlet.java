package productservice.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import productservice.model.Product;
import productservice.service.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@WebServlet("/products/*")
public class ProductsServlet extends HttpServlet {
    private final ProductService productService = new ProductService();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            response.setContentType("application/json");
            List<Product> all = productService.getAllProducts();
            String json = convertToJson(all);
            response.getWriter().println(json);
        } else {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");
            Product product = productService.getProductById(productId(pathInfo));
            String json = convertToJson(Collections.singletonList(product));
            response.getWriter().println(json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        StringBuilder reqBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                reqBody.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonData = reqBody.toString();
        Product product = mapper.readValue(jsonData, Product.class);
        productService.save(product);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().println("New product create");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String pathInfo = request.getPathInfo();
        Product newProduct = productService.getProductById(productId(pathInfo));
        StringBuilder reqBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {

                reqBody.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonData = reqBody.toString();
        Product newProductValue = mapper.readValue(jsonData, Product.class);

        newProduct.setName(newProductValue.getName());
        newProduct.setDescription(newProductValue.getDescription());
        newProduct.setPrice(newProductValue.getPrice());

        productService.updateProduct(newProduct);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Product was successful update");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String pathInfo = request.getPathInfo();
        Product product = productService.delete(productId(pathInfo));
        convertToJson(Collections.singletonList(product));

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Product successful delete from product base");
    }

    private String convertToJson(List<Product> products) {
        try {
            return mapper.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private int productId(String string) {
        String[] parts = string.split("/");
        String param = parts[1];
        int productId = Integer.parseInt(param);
        return productId;
    }
}

