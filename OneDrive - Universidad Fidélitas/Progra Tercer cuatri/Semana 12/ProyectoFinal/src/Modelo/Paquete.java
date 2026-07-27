package Modelo;

import Datos.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Paquete {

    private int id_Paquete;
    private java.sql.Date fecha;
    private String codigo_rastreo;
    private String destinatario;
    private String estado;

    public Paquete() {
    }

    public Paquete(int id_Paquete, Date fecha, String codigo_rastreo, String destinatario, String estado) {
        this.id_Paquete = id_Paquete;
        this.fecha = fecha;
        this.codigo_rastreo = codigo_rastreo;
        this.destinatario = destinatario;
        this.estado = estado;
    }


    public int getId_Paquete() {
        return id_Paquete;
    }

    public void setId_Paquete(int id_Paquete) {
        this.id_Paquete = id_Paquete;
    }

        public java.sql.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigo_rastreo() {
        return codigo_rastreo;
    }

    public void setCodigo_rastreo(String codigo_rastreo) {
        this.codigo_rastreo = codigo_rastreo;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void mostrarDatos(JTable tblPaquetes) {
        Conexion conectar = new Conexion();

        DefaultTableModel tabla = new DefaultTableModel();
        int indice;
        tabla.addColumn("ID PAQUETE");
        tabla.addColumn("FECHA");
        tabla.addColumn("CÓDIGO DE RASTREO");
        tabla.addColumn("DESTINATARIO");
        tabla.addColumn("ESTADO");
        tabla.setRowCount(contarRegistros());
        try {
            PreparedStatement da = conectar.conectar().prepareStatement("select * from Paquetes");
            ResultSet tbl = da.executeQuery();

            indice = 0;
            while (tbl.next()) {
                
                tabla.setValueAt(tbl.getInt("id_Paquete"), indice, 0);
                tabla.setValueAt(tbl.getDate("fecha"), indice, 1);
                tabla.setValueAt(tbl.getString("codigo_rastreo"), indice, 2);
                tabla.setValueAt(tbl.getString("destinatario"), indice, 3);
                tabla.setValueAt(tbl.getString("estado"), indice, 4);

                indice++;
            }
        } catch(SQLException e){

    JOptionPane.showMessageDialog(null,
            e.getMessage());


        }

        tblPaquetes.setModel(tabla);
    }

    public void Guardar() {
        Conexion objConexion = new Conexion();
        String sql;

        try {
            sql = "INSERT INTO paquetes(fecha, codigo_rastreo, destinatario, estado) VALUES (?,?,?,?)";
            PreparedStatement pst = objConexion.conectar().prepareStatement(sql);
            pst.setDate(1, getFecha());
            pst.setString(2, getCodigo_rastreo());
            pst.setString(3, getDestinatario());
            pst.setString(4, getEstado());
            pst.executeUpdate();
           JOptionPane.showMessageDialog(null,"Paquete registrado correctamente");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void Modificar() {
        Conexion objConexion = new Conexion();
        String sql;

        try {
            sql = "update Paquetes set fecha=?,codigo_rastreo=?,destinatario=?, estado=? where id_Paquete=?";
            PreparedStatement pst = objConexion.conectar().prepareStatement(sql);
            pst.setDate(1, getFecha());
            pst.setString(2, getCodigo_rastreo());
            pst.setString(3, getDestinatario());
            pst.setString(4, getEstado());
            pst.setInt(5, getId_Paquete());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Paquete modificado correctamente");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void Eliminar() {
        Conexion objConexion = new Conexion();
        String sql;

        try {
            sql = "delete from Paquetes where id_Paquete=?";
            PreparedStatement pst = objConexion.conectar().prepareStatement(sql);
            pst.setInt(1, getId_Paquete());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Paquete eliminado correctamente");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int contarRegistros() {
        Conexion conectar = new Conexion();
        int indice = 0;

        try {

            PreparedStatement da = conectar.conectar().prepareStatement("select * from Paquetes");
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
