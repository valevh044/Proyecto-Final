package Datos;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import javax.swing.JOptionPane;
//djdjdjdjdjjd
public class Conexion {

    String url = "jdbc:mysql://localhost:3306/proyecto";
    String user = "root";
    String pwd = "";

    public Conexion() {
    }

    public Connection conectar() {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, pwd);
            return conn;

        } catch (SQLException err) {

            JOptionPane.showMessageDialog(null,err.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);

            return conn;
        }
    }
}
