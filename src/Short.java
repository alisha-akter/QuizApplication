import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

public class Short extends Question {
    public String answer;
    public String correct;

    public Short() {}

    public Short(String title, String questionText, int difficulty, String code,
                 String answer, String correct) {
        super(title, questionText, difficulty, code);
        this.answer = answer;
        this.correct = correct;
    }

    public ArrayList<Short> load(String code) {
        ArrayList<Short> shorts = new ArrayList<>();
        String sql = "SELECT * FROM short_questions WHERE code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, code);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    String questionText = rs.getString("questionText");
                    int difficulty = rs.getInt("difficulty");
                    String answer = rs.getString("answer");
                    String correct = rs.getString("correct");

                    Short q = new Short(title, questionText, difficulty, code, answer, correct);
                    shorts.add(q);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load short questions from database.");
        }
        return shorts;
    }

    public void addQuestion() {
        String sql = "INSERT INTO short_questions (title, questionText, difficulty, code, answer, correct) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, title);
            pst.setString(2, questionText);
            pst.setInt(3, difficulty);
            pst.setString(4, code);
            pst.setString(5, answer);
            pst.setString(6, correct);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Short Question added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to add Short question!");
        }
    }
}
