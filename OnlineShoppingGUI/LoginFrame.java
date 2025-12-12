import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Online Shopping - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 220);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0;
        form.add(new JLabel("Username:"), c);
        c.gridx = 1;
        usernameField = new JTextField(16);
        form.add(usernameField, c);

        c.gridx = 0; c.gridy = 1;
        form.add(new JLabel("Password:"), c);
        c.gridx = 1;
        passwordField = new JPasswordField(16);
        form.add(passwordField, c);

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        buttons.add(loginBtn);
        buttons.add(registerBtn);
        add(buttons, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> doLogin());
        registerBtn.addActionListener(e -> doRegister());

        setVisible(true);
    }

    private void doLogin() {
        String u = usernameField.getText().trim();
        String p = new String(passwordField.getPassword());
        if (u.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        User user = ShopData.loginUser(u, p);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // open shop frame for user
        SwingUtilities.invokeLater(() -> {
            new ShopFrame(user);
        });
        dispose();
    }

    private void doRegister() {
        String u = JOptionPane.showInputDialog(this, "Enter new username:");
        if (u == null) return;
        u = u.trim();
        if (u.isEmpty()) { JOptionPane.showMessageDialog(this, "Username cannot be empty"); return; }
        String p = JOptionPane.showInputDialog(this, "Enter password:");
        if (p == null) return;
        p = p.trim();
        boolean ok = ShopData.registerUser(u, p);
        if (ok) JOptionPane.showMessageDialog(this, "Registered successfully. Now login.");
        else JOptionPane.showMessageDialog(this, "Username exists or invalid.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
