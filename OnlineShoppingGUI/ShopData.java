import java.util.*;

public class ShopData {

    private static List<Product> products = new ArrayList<>();
    private static Map<String, User> users = new HashMap<>();

    public static void loadSampleProducts() {
        if (!products.isEmpty()) return;

        // ---- 15 ITEMS ----

        products.add(new Product(1, "T-Shirt", 499.0, 20));
        products.add(new Product(2, "Jeans", 1299.0, 15));
        products.add(new Product(3, "Hoodie", 1499.0, 12));
        products.add(new Product(4, "Jacket", 1999.0, 8));
        products.add(new Product(5, "Cap", 299.0, 25));

        products.add(new Product(6, "Sneakers", 2499.0, 10));
        products.add(new Product(7, "Sandals", 799.0, 18));
        products.add(new Product(8, "Sports Shoes", 2999.0, 14));

        products.add(new Product(9, "Headphones", 899.0, 8));
        products.add(new Product(10, "Smart Watch", 1999.0, 6));
        products.add(new Product(11, "Bluetooth Speaker", 1299.0, 10));
        products.add(new Product(12, "Power Bank", 999.0, 20));

        products.add(new Product(13, "Wallet", 499.0, 15));
        products.add(new Product(14, "Sunglasses", 999.0, 10));
        products.add(new Product(15, "Backpack", 1499.0, 9));
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static boolean registerUser(String username, String password) {
        if (username == null || username.isEmpty() || users.containsKey(username))
            return false;
        users.put(username, new User(username, password));
        return true;
    }

    public static User loginUser(String username, String password) {
        User u = users.get(username);
        if (u == null) return null;
        if (u.getPassword().equals(password)) return u;
        return null;
    }
}
