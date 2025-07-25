import java.awt.*;
import java.io.FileWriter;
import java.sql.*;
import javax.swing.*;

public class AdminPanel extends JFrame {
    JButton processBtn, exportBtn;

    public AdminPanel() {
        setTitle("Admin Panel");
        setSize(300, 150);
        setLayout(new GridLayout(2, 1));

        processBtn = new JButton("Process Admissions");
        exportBtn = new JButton("Export CSV");

        processBtn.addActionListener(e -> processAdmissions());
        exportBtn.addActionListener(e -> exportCSV());

        add(processBtn);
        add(exportBtn);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void processAdmissions() {
        try (Connection conn = DBConnection.getConnection()) {
            ResultSet courses = conn.createStatement().executeQuery("SELECT * FROM Courses");

            while (courses.next()) {
                int courseId = courses.getInt("course_id");
                int cutoff = courses.getInt("cutoff");
                int seats = courses.getInt("seats");

                PreparedStatement apps = conn.prepareStatement(
                    "SELECT * FROM Applications WHERE course_id=? AND status='Pending' ORDER BY merit_score DESC"
                );
                apps.setInt(1, courseId);
                ResultSet rs = apps.executeQuery();

                while (rs.next() && seats > 0) {
                    int appId = rs.getInt("application_id");
                    int merit = rs.getInt("merit_score");

                    String status = merit >= cutoff ? "Approved" : "Rejected";
                    if (status.equals("Approved")) seats--;

                    PreparedStatement update = conn.prepareStatement(
                        "UPDATE Applications SET status=? WHERE application_id=?"
                    );
                    update.setString(1, status);
                    update.setInt(2, appId);
                    update.executeUpdate();
                }

                PreparedStatement seatUpdate = conn.prepareStatement(
                    "UPDATE Courses SET seats=? WHERE course_id=?"
                );
                seatUpdate.setInt(1, seats);
                seatUpdate.setInt(2, courseId);
                seatUpdate.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Admissions processed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportCSV() {
        try (Connection conn = DBConnection.getConnection();
             FileWriter writer = new FileWriter("admission_list.csv")) {

            ResultSet rs = conn.createStatement().executeQuery(
                "SELECT s.name, s.email, c.course_name FROM Applications a " +
                "JOIN Students s ON a.student_id = s.student_id " +
                "JOIN Courses c ON a.course_id = c.course_id " +
                "WHERE a.status='Approved'"
            );

            writer.write("Name,Email,Course\n");
            while (rs.next()) {
                writer.write(rs.getString("name") + "," +
                             rs.getString("email") + "," +
                             rs.getString("course_name") + "\n");
            }

            JOptionPane.showMessageDialog(this, "Exported to admission_list.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
