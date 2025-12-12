import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

import java.util.Map;

public class ShopFrame extends JFrame {
    private User user;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JSpinner qtySpinner;

    public ShopFrame(User user) {
        this.user = user;
        setTitle("Online Shop - User: " + user.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top: toolbar
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton viewCartBtn = new JButton("View Cart");
        JButton logoutBtn = new JButton("Logout");
        top.add(new JLabel("Welcome, " + user.getUsername()));
        top.add(Box.createHorizontalStrut(20));
        top.add(viewCartBtn);
        top.add(logoutBtn);
        add(top, BorderLayout.NORTH);

        // Products table
        String[] cols = {"ID", "Name", "Price (Rs.)", "Stock"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        productTable = new JTable(tableModel);
        refreshProductTable();
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Bottom: controls
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(new JLabel("Qty:"));
        qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        bottom.add(qtySpinner);
        JButton addToCartBtn = new JButton("Add to Cart");
        bottom.add(addToCartBtn);
        add(bottom, BorderLayout.SOUTH);

        // Actions
        addToCartBtn.addActionListener(e -> addToCart());
        viewCartBtn.addActionListener(e -> openCartDialog());
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    private void refreshProductTable() {
        tableModel.setRowCount(0);
        for (Product p : ShopData.getProducts()) {
            tableModel.addRow(new Object[]{p.getId(), p.getName(), p.getPrice(), p.getStock()});
        }
    }

    private void addToCart() {
        int row = productTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a product first."); return; }
        int id = (int) tableModel.getValueAt(row, 0);
        Product p = null;
        for (Product pr : ShopData.getProducts()) if (pr.getId() == id) { p = pr; break; }
        if (p == null) { JOptionPane.showMessageDialog(this, "Product not found."); return; }
        int qty = (int) qtySpinner.getValue();
        if (qty <= 0) { JOptionPane.showMessageDialog(this, "Invalid qty."); return; }
        if (qty > p.getStock()) { JOptionPane.showMessageDialog(this, "Not enough stock."); return; }
        user.getCart().addProduct(p, qty);
        JOptionPane.showMessageDialog(this, "Added to cart: " + p.getName() + " x" + qty);
    }

    private void openCartDialog() {
        JDialog d = new JDialog(this, "Cart - " + user.getUsername(), true);
        d.setSize(560, 380);
        d.setLocationRelativeTo(this);
        d.setLayout(new BorderLayout());

        String[] cols = {"Product", "Price", "Qty", "Subtotal"};
        DefaultTableModel cartModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable cartTable = new JTable(cartModel);
        for (Map.Entry<Product, Integer> e : user.getCart().getItems().entrySet()) {
            Product p = e.getKey();
            int q = e.getValue();
            cartModel.addRow(new Object[]{p.getName(), p.getPrice(), q, p.getPrice()*q});
        }
        d.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton removeBtn = new JButton("Remove Selected");
        JButton checkoutBtn = new JButton("Checkout");
        JButton closeBtn = new JButton("Close");
        bottom.add(removeBtn);
        bottom.add(checkoutBtn);
        bottom.add(closeBtn);
        d.add(bottom, BorderLayout.SOUTH);

        removeBtn.addActionListener(ev -> {
            int sel = cartTable.getSelectedRow();
            if (sel == -1) { JOptionPane.showMessageDialog(d, "Select item to remove"); return; }
            String prodName = (String) cartModel.getValueAt(sel, 0);
            Product toRemove = null;
            for (Product p : user.getCart().getItems().keySet()) {
                if (p.getName().equals(prodName)) { toRemove = p; break; }
            }
            if (toRemove != null) {
                user.getCart().removeProduct(toRemove);
                cartModel.removeRow(sel);
                refreshProductTable();
            }
        });

        checkoutBtn.addActionListener(ev -> {
            if (user.getCart().isEmpty()) { JOptionPane.showMessageDialog(d, "Cart is empty"); return; }
            // reduce stock
            for (Map.Entry<Product, Integer> e2 : user.getCart().getItems().entrySet()) {
                Product p = e2.getKey();
                int q = e2.getValue();
                p.setStock(p.getStock() - q);
            }
            double total = user.getCart().getTotal();
            Order o = new Order(user.getCart().getItems(), total);
            user.getOrders().add(o);
            user.getCart().clear();
            JOptionPane.showMessageDialog(d, "Order placed!\n" + o.toString());
            d.dispose();
            refreshProductTable();
        });

        closeBtn.addActionListener(ev -> d.dispose());

        d.setVisible(true);
    }
}
