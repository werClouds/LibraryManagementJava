import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class LogIn extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;

   public LogIn() {
    setTitle("Library Login");
    setSize(600, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Set layout to null for absolute positioning
    setLayout(null);

    // Load and scale image to fit the frame
    ImageIcon bgIcon = new ImageIcon(new ImageIcon("background.jpg")
                          .getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH));
    JLabel background = new JLabel(bgIcon);
    background.setBounds(0, 0, 600, 400);
    setContentPane(background);
    background.setLayout(null);  // Now you can add components to this background

    // Add components to the background label
    JLabel userLabel = new JLabel("Username:");
    userLabel.setBounds(50, 50, 100, 30);
    background.add(userLabel);

    usernameField = new JTextField();
    usernameField.setBounds(150, 50, 180, 30);
    background.add(usernameField);

    JLabel passLabel = new JLabel("Password:");
    passLabel.setBounds(50, 100, 100, 30);
    background.add(passLabel);

    passwordField = new JPasswordField();
    passwordField.setBounds(150, 100, 180, 30);
    background.add(passwordField);

    JButton loginButton = new JButton("Login");
    loginButton.setBounds(50, 160, 120, 30);
    background.add(loginButton);

    JButton registerButton = new JButton("Register");
    registerButton.setBounds(210, 160, 120, 30);
    background.add(registerButton);

    loginButton.addActionListener(e -> login());
    registerButton.addActionListener(e -> {
        this.dispose();
        new Register().setVisible(true);
    });
}

    // Method to set background image
    private void setBackgroundImage() {
        ImageIcon bgIcon = new ImageIcon("background.jpg");  // Ensure the correct path to your image file
        JLabel background = new JLabel(bgIcon);
        background.setBounds(0, 0, getWidth(), getHeight());  // Make the background cover the entire frame

        // Set content pane layout to null so that we can add the background directly
        setContentPane(new JPanel(null));
        getContentPane().add(background);  // Add the background label to the content pane
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
