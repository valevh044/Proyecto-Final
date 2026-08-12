package Modelo;

import Datos.Conexion;
import Modelo.Vehiculo;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Conductor {

    private int idConductor;
    private String nombre;

    public Conductor(int idConductor, String nombre) {
        this.idConductor = idConductor;
        this.nombre = nombre;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
