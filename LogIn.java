import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class LogIn extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;

    public LogIn() {
        setTitle("Library Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 100, 30);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 50, 180, 30);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 100, 30);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 180, 30);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 160, 120, 30);
        add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(210, 160, 120, 30);
        add(registerButton);

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> {
            this.dispose();
            new Register().setVisible(true);
        });
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        boolean found = false;

        try (Scanner scanner = new Scanner(new File("users.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading user file.");
        }

        if (found) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            this.dispose();
            new Home(username).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    public static void main(String[] args) {
        new LogIn().setVisible(true);
    }
}
