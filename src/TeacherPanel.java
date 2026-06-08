import javax.swing.*;
import java.awt.*;

public class TeacherPanel extends JFrame {

    private Color backgroundColor = Color.decode("#e6f0ff");
    private Color buttonColor = new Color(179, 198, 255);
    private Dimension buttonSize = new Dimension(160, 45);
    private Font buttonFont = new Font("Arial", Font.BOLD, 16);

    private JButton btnNewQ;
    private JButton btnViewLeaderBoard;
    private JButton btnBack;

    public TeacherPanel() {
        setTitle("Teacher Panel");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        getContentPane().setBackground(backgroundColor);
        getContentPane().setLayout(null); // absolute positioning

        // Title label
        JLabel title = new JLabel("Teacher Panel", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setForeground(Color.BLACK);
        title.setBounds(0, 50, 900, 50); // full width, positioned near top
        getContentPane().add(title);

        // Buttons
        btnNewQ = new JButton("Set New Question");
        btnViewLeaderBoard = new JButton("View LeaderBoard");
        btnBack = new JButton("Back");

        JButton[] buttons = {btnNewQ, btnViewLeaderBoard, btnBack};
        for (JButton btn : buttons) {
            btn.setPreferredSize(buttonSize);
            btn.setFont(buttonFont);
            btn.setBackground(buttonColor);
            btn.setFocusPainted(false);
            btn.setForeground(Color.BLACK);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        }

        btnNewQ.setBounds(370, 220, buttonSize.width, buttonSize.height);
        btnViewLeaderBoard.setBounds(370, 300, buttonSize.width, buttonSize.height);
        btnBack.setBounds(370, 380, buttonSize.width, buttonSize.height);

        getContentPane().add(btnNewQ);
        getContentPane().add(btnViewLeaderBoard);
        getContentPane().add(btnBack);

        // Action listeners
        btnNewQ.addActionListener(e -> {
            new QuestionPaperDetails().setVisible(true);
            dispose();
        });
        btnViewLeaderBoard.addActionListener(e -> {
            new TeacherLeaderboard().setVisible(true); // Open the actual leaderboard
            dispose(); // optional: close current window
        });

        btnBack.addActionListener(e -> {
            new MainFrame().setVisible(true);
            dispose();
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TeacherPanel panel = new TeacherPanel();
            panel.setVisible(true);
        });
    }
}
