
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/college_admission", "root", "123456789"
            );
        } catch (Exception e) {
            System.err.println("DB Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
