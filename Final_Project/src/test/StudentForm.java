import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class StudentForm extends JFrame {
    JTextField nameField, emailField, dobField, marksField;
    JComboBox<String> courseBox;

    public StudentForm() {
        setTitle("Student Registration");
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));

        nameField = new JTextField();
        emailField = new JTextField();
        dobField = new JTextField();
        marksField = new JTextField();
        courseBox = new JComboBox<>();

        loadCourses();

        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Email:")); add(emailField);
        add(new JLabel("DOB (YYYY-MM-DD):")); add(dobField);
        add(new JLabel("Marks:")); add(marksField);
        add(new JLabel("Course:")); add(courseBox);

        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> registerStudent());
        add(new JLabel()); add(submitBtn);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadCourses() {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "DB connection failed.");
                return;
            }
            ResultSet rs = conn.createStatement().executeQuery("SELECT course_name FROM Courses");
            while (rs.next()) courseBox.addItem(rs.getString("course_name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerStudent() {
        String name = nameField.getText();
        String email = emailField.getText();
        String dob = dobField.getText();
        int marks = Integer.parseInt(marksField.getText());
        String selectedCourse = (String) courseBox.getSelectedItem();

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "DB connection failed.");
                return;
            }

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Students (name, email, dob, marks) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setDate(3, Date.valueOf(dob));
            ps.setInt(4, marks);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int studentId = rs.next() ? rs.getInt(1) : -1;

            PreparedStatement courseQuery = conn.prepareStatement("SELECT course_id FROM Courses WHERE course_name=?");
            courseQuery.setString(1, selectedCourse);
            ResultSet crs = courseQuery.executeQuery();
            int courseId = crs.next() ? crs.getInt("course_id") : -1;

            PreparedStatement apply = conn.prepareStatement(
                "INSERT INTO Applications (student_id, course_id, merit_score) VALUES (?, ?, ?)"
            );
            apply.setInt(1, studentId);
            apply.setInt(2, courseId);
            apply.setInt(3, marks);
            apply.executeUpdate();

            JOptionPane.showMessageDialog(this, "Application Submitted!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
