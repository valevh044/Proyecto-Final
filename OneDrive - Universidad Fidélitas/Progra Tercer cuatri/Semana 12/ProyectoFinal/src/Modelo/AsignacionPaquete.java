package Modelo;

import Datos.Conexion;
import Vista.FormDespachador;
import Datos.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AsignacionPaquete {

    public DefaultTableModel obtenerPaquetes() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"ID PAQUETE", "FECHA", "CÓDIGO DE RASTREO", "DESTINATARIO", "Estado"});
        Conexion conectar = new Conexion();
        try {
            PreparedStatement ps = conectar.conectar().prepareStatement(
                    "SELECT id_Paquete, fecha, codigo_rastreo, destinatario, estado FROM paquetes");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("id_Paquete"),
                    rs.getString("fecha"),
                    rs.getString("codigo_rastreo"),
                    rs.getString("destinatario"),
                    rs.getString("estado")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelo;
    }
}
