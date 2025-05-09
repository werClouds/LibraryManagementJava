import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;

public class Register extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;

    public Register() {
        setTitle("Register");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel userLabel = new JLabel("New Username:");
        userLabel.setBounds(50, 50, 120, 30);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(180, 50, 150, 30);
        add(usernameField);

        JLabel passLabel = new JLabel("New Password:");
        passLabel.setBounds(50, 100, 120, 30);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 100, 150, 30);
        add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(130, 160, 120, 30);
        add(registerButton);

        registerButton.addActionListener(e -> register());
    }

    private void register() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + "," + password);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Registration successful!");
            this.dispose();
            new LogIn().setVisible(true);
        } catch (IOException e) {
			e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error writing to file." +
			e.getMessage());
        }
    }
}
