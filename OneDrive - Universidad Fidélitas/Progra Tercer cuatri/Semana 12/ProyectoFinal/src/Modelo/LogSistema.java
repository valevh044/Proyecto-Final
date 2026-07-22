package Modelo;

import java.sql.PreparedStatement;
import Datos.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class LogSistema {

    private int id_log;
    private int fecha;

    public LogSistema() {
    }

    public int getId_log() {
        return id_log;
    }

    public void setId_log(int id_log) {
        this.id_log = id_log;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public void Log() {

        Conexion conectar = new Conexion();

        try {
            String sql = "INSERT INTO Log VALUES (?,?)";

            PreparedStatement pstmt = conectar.conectar().prepareStatement(sql);
            pstmt.setInt(1, getId_log());
            pstmt.setInt(2, getFecha());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
