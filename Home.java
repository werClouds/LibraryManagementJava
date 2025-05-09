import javax.swing.*;

public class Home extends JFrame {
    public Home(String username) {
        setTitle("Library Home");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setBounds(100, 50, 250, 30);
        add(welcomeLabel);
    }
}
