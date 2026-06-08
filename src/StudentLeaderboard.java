import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class StudentLeaderboard extends JFrame {
    public StudentLeaderboard(String email) {
        setTitle("Student Leaderboard");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String[] columnNames = {"Name", "Email", "Correct Answers", "Wrong Answers", "Percentage"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(
                     "SELECT name, email, correctAns, wrongAns FROM students WHERE email = ?")) {

            pst.setString(1, email); // এই লাইনটি শুধুমাত্র তখনই কাজ করবে যদি উপরের query-তে ? থাকে
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String em = rs.getString("email");
                int correct = rs.getInt("correctAns");
                int wrong = rs.getInt("wrongAns");
                double percentage = (correct + wrong) > 0 ?
                        ((double) correct / (correct + wrong)) * 100 : 0;

                model.addRow(new Object[]{
                        name,
                        em,
                        correct,
                        wrong,
                        String.format("%.2f%%", percentage)
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading leaderboard: " + e.getMessage());
        }

        add(new JScrollPane(table));
        setVisible(true);
    }

    public static void main(String[] args) {
        new StudentLeaderboard("student@example.com");
    }
}