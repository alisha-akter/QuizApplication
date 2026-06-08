import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    public Login() {
        setTitle("Login");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = Color.decode("#e6f0ff");
        Color buttonColor = new Color(179, 198, 255);

        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel formPanel = new JPanel();
        formPanel.setBackground(backgroundColor);
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel roleLabel = new JLabel("Role:");

        Dimension fieldSize = new Dimension(200, 35);
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(fieldSize);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(fieldSize);

        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Student", "Teacher"});
        roleCombo.setPreferredSize(fieldSize);

        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        Dimension buttonSize = new Dimension(160, 40);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        for (JButton btn : new JButton[]{loginButton, backButton}) {
            btn.setPreferredSize(buttonSize);
            btn.setFont(buttonFont);
            btn.setBackground(buttonColor);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
        }

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(roleLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(roleCombo, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(backButton);

        panel.add(Box.createRigidArea(new Dimension(0, 100)));
        panel.add(formPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(buttonPanel);

        // Back to main menu
        backButton.addActionListener(e -> {
            new MainFrame();
            dispose();
        });

        // Login button logic
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = (String) roleCombo.getSelectedItem();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter email and password.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (role.equals("Student")) {
                Student student = new Student("", email, password, "", 0, 0);
                if (student.login(email, password)) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    StudentPanel studentPanel = new StudentPanel(email);
                    studentPanel.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid student credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else if (role.equals("Teacher")) {
                Teacher teacher = new Teacher("", email, password, "");
                if (teacher.login(email, password)) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    TeacherPanel teacherPanel = new TeacherPanel();
                    teacherPanel.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid teacher credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }
}
