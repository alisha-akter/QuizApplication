import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class MCQ extends Question {
    public String op1;
    public String op2;
    public String op3;
    public String op4;
    public String correct;

    public MCQ() {}

    public MCQ(String title, String questionText, int difficulty, String code,
               String op1, String op2, String op3, String op4, String correct) {
        super(title, questionText, difficulty, code);
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;
        this.correct = correct;
    }

    public String getOp1()
    {
        return op1;
    }
    public String getOp2()
    {
        return op2;
    }
    public String getOp3()
    {
        return op3;
    }
    public String getOp4()
    {
        return op4;
    }
    public ArrayList<MCQ> load(String code) {
        ArrayList<MCQ> mcqs = new ArrayList<>();
        String sql = "SELECT * FROM mcq_questions WHERE code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, code);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String title = rs.getString("title");
                    String questionText = rs.getString("questionText");
                    int difficulty = rs.getInt("difficulty");
                    String op1 = rs.getString("op1");
                    String op2 = rs.getString("op2");
                    String op3 = rs.getString("op3");
                    String op4 = rs.getString("op4");
                    String correct = rs.getString("correct");

                    MCQ q = new MCQ(title, questionText, difficulty, code, op1, op2, op3, op4, correct);
                    mcqs.add(q);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load MCQ questions from database.");
        }

        return mcqs;
    }

    public void add() {
        String sql = "INSERT INTO mcq_questions (title, questionText, difficulty, code, op1, op2, op3, op4, correct) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, title);
            pst.setString(2, questionText);
            pst.setInt(3, difficulty);
            pst.setString(4, code);
            pst.setString(5, op1);
            pst.setString(6, op2);
            pst.setString(7, op3);
            pst.setString(8, op4);
            pst.setString(9, correct);

            int rows = pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to add MCQ question!");
        }
    }
}
