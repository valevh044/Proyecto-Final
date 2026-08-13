package Modelo;

import Datos.Conexion;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogSistema {

    private int id_log;
    private int id_usuario;

    public LogSistema() {
    }

    public int getId_log() {
        return id_log;
    }

    public void setId_log(int id_log) {
        this.id_log = id_log;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void Log() {

        Conexion conectar = new Conexion();

        try {

            String sql = "INSERT INTO log (fecha, id_usuario) "
                    + "VALUES (CURDATE(), ?)";

            PreparedStatement pstmt= conectar.conectar().prepareStatement(sql);
            pstmt.setInt(1, getId_usuario());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}