import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Home extends JFrame {

    public Home(String username) {
        setTitle("User Home - Library");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(230, 240, 255));

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            this.dispose();
            new LogIn().setVisible(true);
        });
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        topPanel.add(logoutBtn, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Table with Status column instead of Action
        String[] columns = {"Book ID", "Book Name", "Borrow Date", "Due Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(null, columns) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(30);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[0].equals(username)) {
                    String bookId = parts[1];
                    String bookName = parts[2];
                    String borrowDateStr = parts[3];
                    String status = parts[4];
                    LocalDate borrowDate = LocalDate.parse(borrowDateStr, formatter);
                    LocalDate dueDate = borrowDate.plusDays(5);

                    model.addRow(new Object[]{bookId, bookName, borrowDate, dueDate, status});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not load transactions.");
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Review Panel
        JPanel reviewPanel = new JPanel(new FlowLayout());
        reviewPanel.setBackground(new Color(230, 240, 255));

        JLabel reviewLabel = new JLabel("Write a Review:");
        JTextField reviewField = new JTextField(20);
        JLabel ratingLabel = new JLabel("Rating (1-5):");
        JComboBox<Integer> ratingBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        JButton submitReviewBtn = new JButton("Submit");

        reviewPanel.add(reviewLabel);
        reviewPanel.add(reviewField);
        reviewPanel.add(ratingLabel);
        reviewPanel.add(ratingBox);
        reviewPanel.add(submitReviewBtn);
        add(reviewPanel, BorderLayout.SOUTH);

        submitReviewBtn.addActionListener(e -> {
            String review = reviewField.getText().trim();
            int rating = (int) ratingBox.getSelectedItem();
            if (!review.isEmpty()) {
                try (FileWriter fw = new FileWriter("reviews.txt", true)) {
                    fw.write(username + "," + rating + "," + review + "\n");
                    JOptionPane.showMessageDialog(this, "Review submitted!");
                    reviewField.setText("");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Could not save review.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a review before submitting.");
            }
        });
    }
}
