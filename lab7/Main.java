public class Main {
    public static void main(String[] args) {
        try {
            // Створення та запуск сервера
            ATBServer server = new ATBServer();
            server.start(); // Викликає метод start() для запуску сервера
        } catch (Exception e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }
}
