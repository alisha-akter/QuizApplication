import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class ExamFrame extends JFrame {

    // For MCQ
    private ArrayList<MCQ> mcqQuestions;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private ArrayList<Integer> mcqUserAnswers;
    private ArrayList<String> shortUserAnswers;

    // For Short Question
    private ArrayList<Short> shortQuestions;
    private JTextArea answerArea;

    // Common
    private int currentIndex = 0;
    private JLabel questionLabel;
    private JButton nextButton, backButton;

    private String currentType;
    private String studentEmail;
    private String examCode;

    public ExamFrame(String studentEmail, String examCode) {
        this.studentEmail = studentEmail;
        this.examCode = examCode;

        setTitle("Exam");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(questionLabel, BorderLayout.NORTH);

        nextButton = new JButton("Next");
        backButton = new JButton("Back");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            this.dispose();
            new StudentPanel(studentEmail).setVisible(true);
        });

        nextButton.addActionListener(e -> {
            saveCurrentAnswer();
            currentIndex++;

            if (currentType.equals("MCQ")) {
                if (currentIndex < mcqQuestions.size()) {
                    displayMCQ(currentIndex);
                } else {
                    int correct = calculateMCQScore();
                    int wrong = mcqQuestions.size() - correct;

                    Exam.recordExamResult(studentEmail, examCode, correct, wrong);
                    Exam.updateStudentScore(studentEmail, correct, wrong);

                    JOptionPane.showMessageDialog(this,
                            "Exam Finished!\nCorrect: " + correct + "\nWrong: " + wrong);
                    this.dispose();
                    new StudentPanel(studentEmail).setVisible(true);
                }
            } else if (currentType.equals("Short Question")) {
                if (currentIndex < shortQuestions.size()) {
                    displayShort(currentIndex);
                } else {
                    int correct = calculateShortScore();
                    int wrong = shortQuestions.size() - correct;

                    Exam.recordExamResult(studentEmail, examCode, correct, wrong);
                    Exam.updateStudentScore(studentEmail, correct, wrong);

                    JOptionPane.showMessageDialog(this,
                            "Exam Finished!\nCorrect: " + correct + "\nWrong: " + wrong);
                    this.dispose();
                    new StudentPanel(studentEmail).setVisible(true);
                }
            }
        });
    }

    private void saveCurrentAnswer() {
        if (currentType.equals("MCQ")) {
            for (int i = 0; i < optionButtons.length; i++) {
                if (optionButtons[i].isSelected()) {
                    mcqUserAnswers.set(currentIndex, i + 1);
                    break;
                }
            }
        } else {
            shortUserAnswers.set(currentIndex, answerArea.getText());
        }
    }

    private int calculateMCQScore() {
        int correct = 0;
        for (int i = 0; i < mcqQuestions.size(); i++) {
            MCQ question = mcqQuestions.get(i);
            int userAnswer = mcqUserAnswers.get(i);
            if (userAnswer > 0) {
                String userSelected = getSelectedOption(question, userAnswer);
                if (userSelected != null && userSelected.equals(question.correct)) {
                    correct++;
                }
            }
        }
        return correct;
    }

    private String getSelectedOption(MCQ question, int answerIndex) {
        switch(answerIndex) {
            case 1: return question.getOp1();
            case 2: return question.getOp2();
            case 3: return question.getOp3();
            case 4: return question.getOp4();
            default: return null;
        }
    }

    private int calculateShortScore() {
        int correct = 0;
        for (int i = 0; i < shortQuestions.size(); i++) {
            Short question = shortQuestions.get(i);
            String userAnswer = shortUserAnswers.get(i);
            if (userAnswer != null && !userAnswer.isEmpty() &&
                    userAnswer.equalsIgnoreCase(question.answer)) {
                correct++;
            }
        }
        return correct;
    }

    public void LoadQuestions(String type, String code, int difficulty) {
        currentType = type;
        currentIndex = 0;
        this.examCode = code;

        if (type.equals("MCQ")) {
            MCQ m = new MCQ();
            ArrayList<MCQ> allMcqs = m.load(code);
            Collections.shuffle(allMcqs);

            mcqQuestions = new ArrayList<>();
            mcqUserAnswers = new ArrayList<>();
            int currentDifficulty = 0;
            for (MCQ q : allMcqs) {
                if (currentDifficulty + q.difficulty <= difficulty) {
                    mcqQuestions.add(q);
                    mcqUserAnswers.add(0);
                    currentDifficulty += q.difficulty;
                }
            }

            if (mcqQuestions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No MCQ available for the given difficulty.");
                this.dispose();
                return;
            }

            setupMCQUI();
            displayMCQ(0);
        }
        else if (type.equals("Short Question")) {
            Short s = new Short();
            ArrayList<Short> allShorts = s.load(code);
            Collections.shuffle(allShorts);

            shortQuestions = new ArrayList<>();
            shortUserAnswers = new ArrayList<>();
            int currentDifficulty = 0;
            for (Short q : allShorts) {
                if (currentDifficulty + q.difficulty <= difficulty) {
                    shortQuestions.add(q);
                    shortUserAnswers.add("");
                    currentDifficulty += q.difficulty;
                }
            }

            if (shortQuestions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No Short Questions available for the given difficulty.");
                this.dispose();
                return;
            }

            setupShortUI();
            displayShort(0);
        }
    }

    private void setupMCQUI() {
        getContentPane().removeAll();

        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        optionButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }

        add(optionsPanel, BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private void setupShortUI() {
        getContentPane().removeAll();

        add(questionLabel, BorderLayout.NORTH);

        answerArea = new JTextArea(6, 40);
        answerArea.setLineWrap(true);
        answerArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(answerArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        add(scrollPane, BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        return buttonPanel;
    }

    private void displayMCQ(int index) {
        MCQ q = mcqQuestions.get(index);
        questionLabel.setText("<html>Q" + (index + 1) + ": " + q.questionText + "</html>");

        optionButtons[0].setText(q.getOp1());
        optionButtons[1].setText(q.getOp2());
        optionButtons[2].setText(q.getOp3());
        optionButtons[3].setText(q.getOp4());

        int selectedOption = mcqUserAnswers.get(index);
        if (selectedOption > 0) {
            optionButtons[selectedOption - 1].setSelected(true);
        } else {
            optionsGroup.clearSelection();
        }

        nextButton.setText((index == mcqQuestions.size() - 1) ? "Submit" : "Next");
    }

    private void displayShort(int index) {
        Short q = shortQuestions.get(index);
        questionLabel.setText("<html>Q" + (index + 1) + ": " + q.questionText + "</html>");
        answerArea.setText(shortUserAnswers.get(index));
        nextButton.setText((index == shortQuestions.size() - 1) ? "Submit" : "Next");
    }
}