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

