import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Exam extends JFrame {

    private JTextField codeField;
    private JTextField difficultyField;
    private JComboBox<String> typeComboBox;
    private JButton startButton, backButton;

    private final Color backgroundColor = Color.decode("#e6f0ff");
    private final Color buttonColor = new Color(179, 198, 255);
    private final Dimension fieldSize = new Dimension(250, 35);
    private final Dimension buttonSize = new Dimension(160, 40);
    private final Font labelFont = new Font("Arial", Font.PLAIN, 16);
    private final Font titleFont = new Font("Serif", Font.BOLD, 30);
    private final Font buttonFont = new Font("Arial", Font.BOLD, 16);

    private String studentEmail;

    public Exam(String email) {
        this.studentEmail = email;

        setTitle("Exam Options");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(backgroundColor);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Start Exam");
        title.setFont(titleFont);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        codeField = new JTextField();
        codeField.setPreferredSize(fieldSize);
        difficultyField = new JTextField();
        difficultyField.setPreferredSize(fieldSize);
        typeComboBox = new JComboBox<>(new String[]{"MCQ", "Short Question"});
        typeComboBox.setPreferredSize(fieldSize);
        typeComboBox.setFont(labelFont);

        String[] labels = {"Enter Code:", "Enter Difficulty:", "Enter Type:"};
        Component[] fields = {codeField, difficultyField, typeComboBox};

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(labelFont);
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(lbl, gbc);

            gbc.gridx = 1;
            formPanel.add(fields[i], gbc);
        }

        // Buttons
        startButton = new JButton("Start Exam");
        backButton = new JButton("Back");

        JButton[] buttons = {startButton, backButton};
        for (JButton btn : buttons) {
            btn.setPreferredSize(buttonSize);
            btn.setFont(buttonFont);
            btn.setBackground(buttonColor);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(backButton);

        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(buttonPanel);

        // Action Listeners
        startButton.addActionListener(e -> startExam());
        backButton.addActionListener(e -> {
            new StudentPanel(studentEmail).setVisible(true);
            dispose();
        });

        add(mainPanel);
        setVisible(true);
    }

    private void startExam() {
        String code = codeField.getText().trim();
        String type = (String) typeComboBox.getSelectedItem();
        String diffText = difficultyField.getText().trim();

        if (code.isEmpty() || diffText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int difficulty = Integer.parseInt(diffText);
            if (difficulty <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Difficulty must be a positive integer.",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            ExamFrame examFrame = new ExamFrame(studentEmail, code);  // Pass both email and code
            examFrame.setVisible(true);
            examFrame.LoadQuestions(type, code, difficulty);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Difficulty must be a valid number.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void updateStudentScore(String email, int correct, int wrong) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(
                     "UPDATE students SET correctAns = correctAns + ?, wrongAns = wrongAns + ? WHERE email = ?")) {

            pst.setInt(1, correct);
            pst.setInt(2, wrong);
            pst.setString(3, email);

            int rowsUpdated = pst.executeUpdate();
            System.out.println("Updated student score: " + rowsUpdated + " row(s) affected");

        } catch (SQLException e) {
            System.err.println("Error updating score: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void recordExamResult(String studentEmail, String examCode, int correct, int wrong) {
        try (Connection conn = DBConnection.getConnection()) {
            // First check if result already exists for this student and exam
            String checkSql = "SELECT COUNT(*) FROM exam_results WHERE student_email = ? AND exam_code = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, studentEmail);
            checkStmt.setString(2, examCode);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Update existing record
                String updateSql = "UPDATE exam_results SET correct_answers = ?, wrong_answers = ? " +
                        "WHERE student_email = ? AND exam_code = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, correct);
                updateStmt.setInt(2, wrong);
                updateStmt.setString(3, studentEmail);
                updateStmt.setString(4, examCode);
                updateStmt.executeUpdate();
            } else {
                // Insert new record
                String insertSql = "INSERT INTO exam_results (student_email, exam_code, correct_answers, wrong_answers) " +
                        "VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, studentEmail);
                insertStmt.setString(2, examCode);
                insertStmt.setInt(3, correct);
                insertStmt.setInt(4, wrong);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error recording exam result: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Exam("student@example.com"));
    }
}