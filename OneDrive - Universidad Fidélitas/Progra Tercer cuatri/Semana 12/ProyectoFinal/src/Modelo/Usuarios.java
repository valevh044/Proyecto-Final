
package Modelo;
import java.sql.PreparedStatement;
import Datos.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Usuarios {
    
    private int id_rol;
    private String nombre;
    private int telefono;
    private String pass;
    private int fecha;
    private String tipoRol;
    
    public Usuarios(){
        
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }
    
    public String getTipoRol() {
        return tipoRol;
    }

    public void setTipoRol(String tipoRol) {
        this.tipoRol = tipoRol;
    }
    public void InicioSesion() 
    {
        
    Conexion conectar = new Conexion();
         
       try {
           String sql = "INSERT INTO Usuarios VALUES (?,?,?,?,?,?)";
    
           PreparedStatement pstmt = conectar.conectar().prepareStatement(sql); 
            pstmt.setInt(1, getId_rol());
            pstmt.setString(2, getNombre());
            pstmt.setInt(3, getTelefono());
            pstmt.setString(4,getPass());
            pstmt.setInt(5, getFecha());
            pstmt.setString(4,getTipoRol());
            pstmt.executeUpdate();
           
       } 
       catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}

