import javax.swing.*;
import java.awt.*;

public class StudentPanel extends JFrame {

    private final Color backgroundColor = Color.decode("#e6f0ff");
    private final Color buttonColor = new Color(179, 198, 255);
    private final Dimension buttonSize = new Dimension(160, 45);
    private final Font buttonFont = new Font("Arial", Font.BOLD, 16);

    private JButton btnGiveExam;
    private JButton btnViewLeaderBoard;
    private JButton btnBack;

    private String studentEmail; // ✅ Email field

    public StudentPanel(String email) {
        this.studentEmail = email; // ✅ Store email

        setTitle("Student Panel");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        getContentPane().setBackground(backgroundColor);
        getContentPane().setLayout(null); // absolute positioning

        // Title label
        JLabel title = new JLabel("Student Panel", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setForeground(Color.BLACK);
        title.setBounds(0, 50, 900, 50);
        getContentPane().add(title);

        // Buttons
        btnGiveExam = new JButton("Give Exam");
        btnViewLeaderBoard = new JButton("View LeaderBoard");
        btnBack = new JButton("Back");

        JButton[] buttons = {btnGiveExam, btnViewLeaderBoard, btnBack};
        for (JButton btn : buttons) {
            btn.setPreferredSize(buttonSize);
            btn.setFont(buttonFont);
            btn.setBackground(buttonColor);
            btn.setFocusPainted(false);
            btn.setForeground(Color.BLACK);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        }

        btnGiveExam.setBounds(370, 220, buttonSize.width, buttonSize.height);
        btnViewLeaderBoard.setBounds(370, 300, buttonSize.width, buttonSize.height);
        btnBack.setBounds(370, 380, buttonSize.width, buttonSize.height);

        getContentPane().add(btnGiveExam);
        getContentPane().add(btnViewLeaderBoard);
        getContentPane().add(btnBack);

        // Action listeners
        btnGiveExam.addActionListener(e -> {
            new Exam(studentEmail).setVisible(true); // ✅ Pass email correctly
            dispose();
        });

        btnViewLeaderBoard.addActionListener(e -> {
            new StudentLeaderboard(studentEmail).setVisible(true); // ✅ Pass email correctly
            dispose();
        });

        btnBack.addActionListener(e -> {
            // handle back action if needed (e.g., return to login screen)
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentPanel("student@example.com").setVisible(true); // ✅ Provide email here
        });
    }
}
