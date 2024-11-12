import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ProductController implements HttpHandler {
    private final ProductService productService = new ProductService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();

        switch (requestMethod) {
            case "GET":
                getAllProducts(exchange);
                break;
            case "POST":
                addProduct(exchange);
                break;
            case "PUT":
                updateProduct(exchange);
                break;
            case "DELETE":
                deleteProduct(exchange);
                break;
            default:
                exchange.sendResponseHeaders(405, -1); // Method not allowed
                break;
        }
    }

    private String readRequestBody(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        return body.toString();
    }

    private void getAllProducts(HttpExchange exchange) throws IOException {
        List<Product> products = productService.getAllProducts();
        StringBuilder response = new StringBuilder("[");
        for (Product product : products) {
            response.append(product.toString()).append(",");
        }
        if (!products.isEmpty()) {
            response.deleteCharAt(response.length() - 1); // Видалити останню кому
        }
        response.append("]");
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.toString().getBytes(StandardCharsets.UTF_8));
        }
    }

    private void addProduct(HttpExchange exchange) throws IOException {
        String requestBody = readRequestBody(exchange);
        String[] parts = requestBody.split(",");
        String name = parts[0].split("=")[1];
        double price = Double.parseDouble(parts[1].split("=")[1]);
        productService.addProduct(new Product(name, price));
        exchange.sendResponseHeaders(201, -1); // Created
    }

    private void updateProduct(HttpExchange exchange) throws IOException {
        String requestBody = readRequestBody(exchange);
        String[] parts = requestBody.split(",");
        String name = parts[0];
        double price = Double.parseDouble(parts[1]);
        productService.updateProduct(name, price);
        exchange.sendResponseHeaders(200, -1); // OK
    }

    private void deleteProduct(HttpExchange exchange) throws IOException {
        String requestBody = readRequestBody(exchange);
        productService.deleteProduct(requestBody);
        exchange.sendResponseHeaders(200, -1); // OK
    }
}
