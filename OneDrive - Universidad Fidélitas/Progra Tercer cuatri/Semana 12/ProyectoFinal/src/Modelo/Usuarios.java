package Modelo;

import java.sql.PreparedStatement;
import Datos.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Usuarios {

    private int id_usuario;
    private String nombre;
    private String pass;
    private int telefono;

    public Usuarios() {

    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void Usuario() {

        Conexion conectar = new Conexion();

        try {
            String sql = "INSERT INTO Usuarios VALUES (?,?)";

            PreparedStatement pstmt = conectar.conectar().prepareStatement(sql);
            pstmt.setInt(1, getId_usuario());
            pstmt.setString(2, getNombre());
            pstmt.setString(3, getPass());
            pstmt.setInt(4, getTelefono());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
