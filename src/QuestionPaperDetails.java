import javax.swing.*;
import java.awt.*;

public class QuestionPaperDetails extends JFrame {

    private Color backgroundColor = Color.decode("#e6f0ff");
    private Color buttonColor = new Color(179, 198, 255);
    private Font labelFont = new Font("Arial", Font.PLAIN, 16);
    private Dimension buttonSize = new Dimension(180, 45);

    private JTextField titleField;
    private JTextField codeField;
    private JTextField numberField;
    private JComboBox<String> typeComboBox;
    private JButton createQuestionButton;
    private JButton backButton;

    public QuestionPaperDetails() {
        setTitle("Question Paper Details");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(backgroundColor);
        getContentPane().setLayout(null);

        // Heading
        JLabel heading = new JLabel("Question Paper Details", SwingConstants.CENTER);
        heading.setFont(new Font("Serif", Font.PLAIN, 28));
        heading.setBounds(0, 50, 900, 40);
        getContentPane().add(heading);

        // Labels
        JLabel titleLabel = new JLabel("Title:");
        JLabel codeLabel = new JLabel("Code:");
        JLabel numberLabel = new JLabel("Number of questions:");
        JLabel typeLabel = new JLabel("Question type:");

        JLabel[] labels = {titleLabel, codeLabel, numberLabel, typeLabel};
        int labelX = 200, fieldX = 430, y = 140, gap = 60;

        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(labelFont);
            labels[i].setBounds(labelX, y + i * gap, 250, 30);
            getContentPane().add(labels[i]);
        }

        // Input Fields
        titleField = new JTextField();
        codeField = new JTextField();
        numberField = new JTextField();
        typeComboBox = new JComboBox<>(new String[]{"MCQ", "Short Question"});
        typeComboBox.setFont(labelFont);

        Component[] fields = {titleField, codeField, numberField, typeComboBox};
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof JTextField) {
                ((JTextField) fields[i]).setFont(labelFont);
            }
            fields[i].setBounds(fieldX, y + i * gap, 250, 30);
            getContentPane().add(fields[i]);
        }

        // Buttons
        createQuestionButton = new JButton("Create Question");
        backButton = new JButton("Back");

        JButton[] buttons = {createQuestionButton, backButton};
        int buttonY = 420;
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(labelFont);
            buttons[i].setBackground(buttonColor);
            buttons[i].setForeground(Color.BLACK);
            buttons[i].setFocusPainted(false);
            buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            buttons[i].setBounds(280 + i * 200, buttonY, buttonSize.width, buttonSize.height);
            getContentPane().add(buttons[i]);
        }

        // Button Actions
        createQuestionButton.addActionListener(e -> {
            try {
                String title = titleField.getText();
                String code = codeField.getText();
                int num = Integer.parseInt(numberField.getText());
                String type = (String) typeComboBox.getSelectedItem();

                if (title.isEmpty() || code.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter all the information.", "Missing Data", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (num <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number!");
                    return;
                }

                new AddQuestion(type, num, title, code);

                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number!");
            }
        });

        backButton.addActionListener(e -> {
            new TeacherPanel();
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuestionPaperDetails().setVisible(true));
    }
}
