import java.util.*;

public class User {
    private String username;
    private String password;
    private Cart cart;
    private List<Order> orders;

    public User(String username, String password) {
        this.username = username; this.password = password;
        this.cart = new Cart();
        this.orders = new ArrayList<>();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Cart getCart() { return cart; }
    public List<Order> getOrders() { return orders; }
}
