import javax.swing.*;
import java.awt.*;

public class Register extends JFrame {
    public Register() {
        setTitle("Register");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Styling
        Color backgroundColor = Color.decode("#e6f0ff");
        Color buttonColor = new Color(179, 198, 255);

        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title
        JLabel title = new JLabel("Choose an option: ");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setForeground(Color.BLACK);

        panel.add(Box.createRigidArea(new Dimension(0, 100)));
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 80)));

        // Buttons
        JButton studentButton = new JButton("Register as Student");
        JButton teacherButton = new JButton("Register as Teacher");
        JButton backButton = new JButton("Back");

        Dimension buttonSize = new Dimension(220, 45);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        JButton[] buttons = {studentButton, teacherButton, backButton};
        for (JButton btn : buttons) {
            btn.setMaximumSize(buttonSize);
            btn.setFont(buttonFont);
            btn.setBackground(buttonColor);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(btn);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        // Back button action (go back to MainFrame)
        backButton.addActionListener(e -> {
            new MainFrame();
            dispose();
        });

        studentButton.addActionListener(e -> {
            new StudentRegister();
            dispose();
        });
        teacherButton.addActionListener(e -> {
            new TeacherRegister();
            dispose();
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Register();
    }
}
