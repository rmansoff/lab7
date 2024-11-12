import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private List<Product> products = new ArrayList<>();

    public List<Product> getAllProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean updateProduct(String name, double newPrice) {
        Optional<Product> product = products.stream().filter(p -> p.getName().equals(name)).findFirst();
        if (product.isPresent()) {
            product.get().setPrice(newPrice);
            return true;
        }
        return false;
    }

    public boolean deleteProduct(String name) {
        return products.removeIf(product -> product.getName().equals(name));
    }
}
