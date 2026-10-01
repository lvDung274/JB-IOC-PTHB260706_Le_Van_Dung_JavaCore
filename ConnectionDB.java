package untils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/db_cinema_booking";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "123456";

    // Viết cách hàm thực hiện mở kết nối đóng kết nối
    public static Connection openConnection() {
        try{
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(URL, USERNAME ,PASSWORD);
            return connection;
        }catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException(e);
        }
    }
}
