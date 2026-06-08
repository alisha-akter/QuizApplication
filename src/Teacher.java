import javax.swing.*;
import java.sql.*;

public class Teacher extends User {

    public Teacher(String name, String email, String password, String department) {
        super(name, email, password, department);
    }

    public void register() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO teachers (name, email, password, department) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, password);
            pst.setString(4, department);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Teacher registered successfully!");
                TeacherPanel teacherPanel = new TeacherPanel();
                teacherPanel.setVisible(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Registration failed!");
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        }
    }

    public boolean login(String email, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM teachers WHERE email = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                this.name = rs.getString("name");
                this.email = rs.getString("email");
                this.password = rs.getString("password");
                this.department = rs.getString("department");
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
