import javax.swing.*;
import java.awt.*;

public class StudentRegister extends JFrame {

    private JTextField nameField, emailField, rollField, deptField, regField;
    private JPasswordField passwordField;

    public StudentRegister() {
        setTitle("Student Registration");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = Color.decode("#e6f0ff");
        Color buttonColor = new Color(179, 198, 255);
        Dimension fieldSize = new Dimension(200, 35);
        Dimension buttonSize = new Dimension(160, 40);
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(backgroundColor);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Student Registration");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setForeground(Color.BLACK);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize fields
        nameField = new JTextField(); nameField.setPreferredSize(fieldSize);
        emailField = new JTextField(); emailField.setPreferredSize(fieldSize);
        rollField = new JTextField(); rollField.setPreferredSize(fieldSize);
        deptField = new JTextField(); deptField.setPreferredSize(fieldSize);
        regField = new JTextField(); regField.setPreferredSize(fieldSize);
        passwordField = new JPasswordField(); passwordField.setPreferredSize(fieldSize);

        String[] labels = {"Name:", "Email:", "Roll:", "Department:", "Registration No:", "Password:"};
        Component[] fields = {nameField, emailField, rollField, deptField, regField, passwordField};

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(labelFont);
            gbc.gridx = 0; gbc.gridy = i;
            formPanel.add(label, gbc);
            gbc.gridx = 1;
            formPanel.add(fields[i], gbc);
        }

        // Buttons
        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        registerBtn.setPreferredSize(buttonSize);
        backBtn.setPreferredSize(buttonSize);
        registerBtn.setFont(buttonFont);
        backBtn.setFont(buttonFont);
        registerBtn.setBackground(buttonColor);
        backBtn.setBackground(buttonColor);
        registerBtn.setForeground(Color.BLACK);
        backBtn.setForeground(Color.BLACK);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(registerBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(backBtn);

        backBtn.addActionListener(e -> {
            new Register(); // Replace with your actual Register frame
            dispose();
        });

        registerBtn.addActionListener(e -> registerStudent());

        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

    private void registerStudent() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String rollText = rollField.getText().trim();
        String department = deptField.getText().trim();
        String regText = regField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Check for empty input first
        if (name.isEmpty() || email.isEmpty() || rollText.isEmpty() ||
                department.isEmpty() || regText.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all the information.", "Missing Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int roll = Integer.parseInt(rollText);
            int regNo = Integer.parseInt(regText);

            if (roll < 0 || regNo < 0) {
                JOptionPane.showMessageDialog(this, "Please enter valid roll and registration numbers.", "Invalid Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // All valid, create student
            Student student = new Student(name, email, password, department, roll, regNo);
            student.register();
            dispose();
            new StudentPanel(email);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Roll and Registration No must be numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }

    }



    public static void main(String[] args) {
        new StudentRegister();
    }
}
