import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class MySQLCurdOperation {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "123456789";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Optional in newer JDBC

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL!");

            // Create
            PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO users (name, email) VALUES (?, ?)");
            insertStmt.setString(1, "John Doe");
            insertStmt.setString(2, "john@example.com");
            insertStmt.executeUpdate();

            // Read
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getString("email"));
            }

            // Update
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET name=? WHERE id=?");
            updateStmt.setString(1, "Jane Doe");
            updateStmt.setInt(2, 1);
            updateStmt.executeUpdate();

            // Delete
            PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM users WHERE id=?");
            deleteStmt.setInt(1, 1);
            deleteStmt.executeUpdate();

            conn.close();
            System.out.println("Disconnected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}