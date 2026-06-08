import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Quiz Application");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Background
        Color backgroundColor = Color.decode("#e6f0ff");
        Color buttonColor = new Color(179, 198, 255);

        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Questra");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Serif", Font.BOLD, 42));
        title.setForeground(Color.BLACK);

        JLabel subtitle = new JLabel("Craft Challenge Conquer"); // corrected spelling
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setFont(new Font("Serif", Font.PLAIN, 24));
        subtitle.setForeground(Color.BLACK);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(600, 10));
        separator.setForeground(Color.GRAY);

        JLabel slogan = new JLabel("Test your brain, Train your soul");
        slogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        slogan.setFont(new Font("Italic", Font.ITALIC, 18));
        slogan.setForeground(Color.BLACK);

        // Spacing
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(subtitle);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(separator);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(slogan);
        panel.add(Box.createRigidArea(new Dimension(0, 100)));

        // Buttons
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");

        Dimension buttonSize = new Dimension(160, 45);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        JButton[] buttons = {loginButton, registerButton, exitButton};
        for (JButton btn : buttons) {
            btn.setMaximumSize(buttonSize);
            btn.setFont(buttonFont);
            btn.setBackground(buttonColor);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(registerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(exitButton);

        // Button actions
        loginButton.addActionListener(e -> {
            new Login(); // open Login
            dispose();        // close MainFrame
        });
        registerButton.addActionListener(e -> {
            new Register();
            dispose();
        });

        exitButton.addActionListener(e -> System.exit(0)); // exit app

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
