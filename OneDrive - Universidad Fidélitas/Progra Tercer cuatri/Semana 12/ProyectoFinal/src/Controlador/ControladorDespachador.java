package Controlador;

import Datos.Conexion;
import Modelo.Paquete;
import Modelo.AsignacionPaquete;
import Vista.FormDespachador;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControladorDespachador {
    
    private FormDespachador vista;
    private AsignacionPaquete asignacionPaquete;

    public ControladorDespachador(FormDespachador vista) {
        this.vista = vista;
        this.asignacionPaquete = new AsignacionPaquete();
        cargarTablaPaquetes();
        
        this.vista.tblPaquetes.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int rec = vista.tblPaquetes.getSelectedRow();

                vista.txtPaquete.setText(vista.tblPaquetes.getValueAt(rec, 0).toString());
            }
        });
    }
    public void cargarTablaPaquetes() {
        try {
            DefaultTableModel modelo = asignacionPaquete.obtenerPaquetes();
            vista.getTblPaquetes().setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar paquetes: " + e.getMessage());
        }
    }
}

