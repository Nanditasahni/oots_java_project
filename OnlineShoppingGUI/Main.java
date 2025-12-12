import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Start on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            ShopData.loadSampleProducts();
            new LoginFrame();
        });
    }
}

