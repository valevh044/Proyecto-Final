package Modelo;

import Datos.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;

public class Conductor {

    private int id_conductor;
    private String cedula;
    private String nombre;
    private String telefono;
    private int id_usuario;

    public Conductor() {
    }

    public int getId_conductor() {
        return id_conductor;
    }

    public void setId_conductor(int id_conductor) {
        this.id_conductor = id_conductor;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void mostrarConductores(JTable tblConductores) {

        Conexion conectar = new Conexion();

        DefaultTableModel tabla = new DefaultTableModel();

        tabla.addColumn("ID CONDUCTOR");
        tabla.addColumn("CÉDULA");
        tabla.addColumn("NOMBRE");
        tabla.addColumn("TELÉFONO");
        tabla.addColumn("ID USUARIO");

        try {

            String sql = "SELECT * FROM conductores";
            PreparedStatement pstmt = conectar.conectar().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Object[] fila = new Object[5];

                fila[0] = rs.getInt("id_conductor");
                fila[1] = rs.getString("cedula");
                fila[2] = rs.getString("nombre");
                fila[3] = rs.getString("telefono");
                fila[4] = rs.getInt("id_usuario");

                tabla.addRow(fila);
            }

            tblConductores.setModel(tabla);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al consultar conductores: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarConductores(JComboBox<String> cbConductor) {

        Conexion conectar = new Conexion();
        cbConductor.removeAllItems();

        try {

            String sql = "SELECT id_conductor, nombre FROM conductores";
            PreparedStatement pstmt= conectar.conectar().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                cbConductor.addItem( rs.getInt("id_conductor") + " - "+ rs.getString("nombre"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al cargar conductores: "+ e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
}
