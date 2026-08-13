package Controlador;

import Modelo.AsignacionPaquete;
import Vista.FormDespachador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControladorDespachador {

    private FormDespachador vista;
    private AsignacionPaquete asignacionPaquete;

    public ControladorDespachador(FormDespachador vista) {

        this.vista = vista;
        this.asignacionPaquete = new AsignacionPaquete();

        cargarTablaPaquetes();
        cargarTablaVehiculos();

        this.vista.tblPaquetes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int rec = vista.tblPaquetes.getSelectedRow();
                vista.txtPaquete.setText(vista.tblPaquetes.getValueAt(rec, 0).toString());
            }
        });

        this.vista.tblConductores.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int rec = vista.tblConductores.getSelectedRow();
                vista.txtVehiculo.setText(vista.tblConductores.getValueAt(rec, 0).toString());
            }
        });

        this.vista.btnAsignar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarPaquete();
            }
        });
        this.vista.btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarTablaPaquetes();
                cargarTablaVehiculos();
                vista.txtPaquete.setText("");
                vista.txtVehiculo.setText("");
            }
        });

        this.vista.btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vista.FormLog login = new Vista.FormLog();
                login.setVisible(true);
                vista.dispose();
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

    public void cargarTablaVehiculos() {

        try {

            DefaultTableModel modelo = asignacionPaquete.obtenerVehiculosDisponibles();
            vista.tblConductores.setModel(modelo);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(vista, "Error al cargar vehículos: " + e.getMessage());
        }
    }

    public void asignarPaquete() {

        if (vista.txtPaquete.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un paquete", "ERROR", JOptionPane.ERROR_MESSAGE);

            return;
        }

        if (vista.txtVehiculo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un vehículo", "ERROR", JOptionPane.ERROR_MESSAGE);

            return;
        }

        int idPaquete = Integer.parseInt(vista.txtPaquete.getText());
        int idVehiculo = Integer.parseInt(vista.txtVehiculo.getText());
        asignacionPaquete.asignarPaquete(idPaquete, idVehiculo);

        cargarTablaPaquetes();
        cargarTablaVehiculos();
        vista.txtPaquete.setText("");
        vista.txtVehiculo.setText("");
    }
}
