package Modelo;

import java.sql.PreparedStatement;
import Datos.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Roles {

    private int id_rol;
    private String tipoRol;

    public Roles() {
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getTipoRol() {
        return tipoRol;
    }

    public void setTipoRol(String tipoRol) {
        this.tipoRol = tipoRol;
    }

    public void Roles() {

        Conexion conectar = new Conexion();

        try {
            String sql = "INSERT INTO Roles VALUES (?,?)";

            PreparedStatement pstmt = conectar.conectar().prepareStatement(sql);
            pstmt.setInt(1, getId_rol());
            pstmt.setString(2, getTipoRol());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
