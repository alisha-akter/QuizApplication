import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TeacherLeaderboard extends JFrame {
    private JTextField codeField;
    private DefaultTableModel model;

    public TeacherLeaderboard() {
        setTitle("Teacher Leaderboard");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Enter Exam Code:"));
        codeField = new JTextField(10);
        JButton searchBtn = new JButton("Search");

        topPanel.add(codeField);
        topPanel.add(searchBtn);

        String[] columnNames = {"Student Name", "Email", "Correct Answers", "Wrong Answers", "Percentage"};
        model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        searchBtn.addActionListener(e -> loadLeaderboard(codeField.getText().trim()));

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        setVisible(true);
    }

    private void loadLeaderboard(String code) {
        model.setRowCount(0);
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid exam code.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT s.name, s.email, er.correct_answers, er.wrong_answers " +
                    "FROM students s " +
                    "JOIN exam_results er ON s.email = er.student_email " +
                    "WHERE er.exam_code = ? " +
                    "ORDER BY er.correct_answers DESC";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, code);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                int correct = rs.getInt("correct_answers");
                int wrong = rs.getInt("wrong_answers");
                double percentage = (correct + wrong) > 0 ?
                        ((double) correct / (correct + wrong)) * 100 : 0;

                model.addRow(new Object[]{
                        name,
                        email,
                        correct,
                        wrong,
                        String.format("%.2f%%", percentage)
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching leaderboard data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new TeacherLeaderboard();
    }
}