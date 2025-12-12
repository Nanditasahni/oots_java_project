import java.util.*;

public class Order {
    private static int NEXT_ID = 1000;
    private int id;
    private Map<Product, Integer> items;
    private double total;
    private Date date;

    public Order(Map<Product, Integer> items, double total) {
        this.id = NEXT_ID++;
        this.items = new LinkedHashMap<>(items);
        this.total = total;
        this.date = new Date();
    }

    public int getId() { return id; }
    public double getTotal() { return total; }
    public Date getDate() { return date; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(id).append(" - ").append(date).append("\n");
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            sb.append("  ").append(e.getKey().getName()).append(" x").append(e.getValue())
              .append(" = Rs.").append(e.getKey().getPrice() * e.getValue()).append("\n");
        }
        sb.append("Total: Rs.").append(total).append("\n");
        return sb.toString();
    }
}
