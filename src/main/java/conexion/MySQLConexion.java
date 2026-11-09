package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConexion {

	private static final String URL = "jdbc:mysql://localhost:3306/clinica_demo?useSSL=false&serverTimezone=UTC";
    private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "");

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Conector MySQL 8+
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
			System.out.println("Error >> Driver MySQL no instalado!!");
		} catch (SQLException e) {
			System.out.println("Error >> Conexion con la BD: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error >> general : " + e.getMessage());
			// e.printStackTrace();
		}
        return con;
    }
}
