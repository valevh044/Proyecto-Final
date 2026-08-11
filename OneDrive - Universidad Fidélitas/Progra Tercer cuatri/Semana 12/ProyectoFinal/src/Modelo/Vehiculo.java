package Modelo;

import Datos.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Vehiculo {

    private int id_vehiculo;
    private String placa;
    private String modelo;
    private String estado;
    private String tipo;
    private int id_conductor;

    public Vehiculo() {
    }

    public Vehiculo(int id_vehiculo, String placa, String modelo,
            String estado, String tipo, int id_conductor) {

        this.id_vehiculo = id_vehiculo;
        this.placa = placa;
        this.modelo = modelo;
        this.estado = estado;
        this.tipo = tipo;
        this.id_conductor = id_conductor;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId_conductor() {
        return id_conductor;
    }

    public void setId_conductor(int id_conductor) {
        this.id_conductor = id_conductor;
    }

    public void mostrarVehiculos(JTable tblVehiculos) {

        Conexion conectar = new Conexion();

        DefaultTableModel tabla = new DefaultTableModel();
        int indice;

        tabla.addColumn("ID VEHICULO");
        tabla.addColumn("PLACA");
        tabla.addColumn("MODELO");
        tabla.addColumn("ESTADO");
        tabla.addColumn("TIPO");

        tabla.setRowCount(contarVehiculos());
        try {

            PreparedStatement da
                    = conectar.conectar().prepareStatement("SELECT * FROM vehiculos");

            ResultSet tbl = da.executeQuery();

            indice = 0;

            while (tbl.next()) {

                tabla.setValueAt(tbl.getInt("id_vehiculo"), indice, 0);
                tabla.setValueAt(tbl.getString("placa"), indice, 1);
                tabla.setValueAt(tbl.getString("modelo"), indice, 2);
                tabla.setValueAt(tbl.getString("estado"), indice, 3);
                tabla.setValueAt(tbl.getString("tipo"), indice, 4);

                indice++;
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        tblVehiculos.setModel(tabla);
    }

    public void Guardar() {

        Conexion objConexion = new Conexion();
        String sql;

        try {

            sql = "INSERT INTO vehiculos "
                    + "(placa, modelo, estado, tipo) "
                    + "VALUES (?,?,?,?)";

            PreparedStatement pst = objConexion.conectar().prepareStatement(sql);
            pst.setString(1, getPlaca());
            pst.setString(2, getModelo());
            pst.setString(3, getEstado());
            pst.setString(4, getTipo());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vehículo registrado correctamente");

        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void Modificar() {

        Conexion objConexion = new Conexion();
        String sql;

        try {

            sql = "UPDATE vehiculos "
                    + "SET placa=?, modelo=?, estado=?, tipo=? "
                    + "WHERE id_vehiculo=?";

            PreparedStatement pst = objConexion.conectar().prepareStatement(sql);
            pst.setString(1, getPlaca());
            pst.setString(2, getModelo());
            pst.setString(3, getEstado());
            pst.setString(4, getTipo());
            pst.setInt(5, getId_vehiculo());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vehículo modificado correctamente");

        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void Eliminar() {

        Conexion objConexion = new Conexion();
        String sql;

        try {

            sql = "DELETE FROM vehiculos "
                    + "WHERE id_vehiculo=?";

            PreparedStatement pst = objConexion.conectar().prepareStatement(sql);
            pst.setInt(1, getId_vehiculo());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vehículo eliminado correctamente");

        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int contarVehiculos() {
        Conexion conectar = new Conexion();
        int indice = 0;

        try {

            PreparedStatement da = conectar.conectar().prepareStatement("SELECT * FROM vehiculos");
            ResultSet tbl = da.executeQuery();

            while (tbl.next()) {
                indice++;
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return indice;
    }
}
