import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class ATBServer {
    private HttpServer server;

    public ATBServer() throws IOException {
        // Створення HTTP сервера на порту 8080
        server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Ініціалізація контролера для обробки запитів на "/products"
        ProductController productController = new ProductController();

        // Створення контексту для маршруту "/products"
        server.createContext("/products", productController::handle);
    }

    // Метод для запуску сервера
    public void start() {
        server.setExecutor(null); // Використовуємо стандартний executor
        server.start();
        System.out.println("Server started on port 8080");
    }
}
