import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ivan-
 */
public class Connexio {
    // private static final String URL = "jdbc:mariadb://192.168.1.140:3306/llibres";
    private static final String URL = "jdbc:mariadb://localhost:3306/llibres"; // Para comprobar el proyecto desde VSC con XAMPP.
    private static final String USER = "root";
    // private static final String PASSWORD = "12345"; // Contraseña del usuario creado en mysql de la maquina virtual.
    private static final String PASSWORD = ""; // Sin contraseña para hacer la comprobación con XAMPP.

    /** 
     * @return Connection
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getConnexio() throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
            
