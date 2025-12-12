import java.util.*;

public class Cart {
    // use LinkedHashMap to preserve insertion order
    private Map<Product, Integer> items = new LinkedHashMap<>();

    public void addProduct(Product p, int qty) {
        if (p == null || qty <= 0) return;
        items.put(p, items.getOrDefault(p, 0) + qty);
    }

    public void removeProduct(Product p) {
        if (p == null) return;
        items.remove(p);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            total += e.getKey().getPrice() * e.getValue();
        }
        return total;
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
