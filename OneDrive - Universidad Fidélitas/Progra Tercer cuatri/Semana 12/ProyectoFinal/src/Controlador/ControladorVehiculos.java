package Controlador;

import Modelo.Conductor;
import Modelo.Vehiculo;
import Vista.FormVehiculos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class ControladorVehiculos {

    private Vehiculo modeloVehiculo;
    private FormVehiculos vista;
    private Conductor conductor;

    public ControladorVehiculos(Vehiculo modeloVehiculo, FormVehiculos vista) {

        this.modeloVehiculo = modeloVehiculo;
        this.vista = vista;
        this.conductor = new Conductor();

        mostrarDatos();
        mostrarConductores();

        this.vista.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuardarControlador();
                mostrarDatos();
            }
        });

        this.vista.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarDatos();
                mostrarDatos();
            }
        });

        this.vista.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarDatos();
                mostrarDatos();
            }
        });

        this.vista.tblVehiculos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                int rec = vista.tblVehiculos.getSelectedRow();
                vista.txtIdVehiculo.setText(vista.tblVehiculos.getValueAt(rec, 0).toString());
                vista.txtPlaca.setText(vista.tblVehiculos.getValueAt(rec, 1).toString());
                vista.txtModelo.setText(vista.tblVehiculos.getValueAt(rec, 2).toString());
                vista.cbEstado.setSelectedItem(vista.tblVehiculos.getValueAt(rec, 3).toString());
                vista.cbTipo.setSelectedItem(vista.tblVehiculos.getValueAt(rec, 4).toString());
                vista.txtIdConductor.setText(vista.tblVehiculos.getValueAt(rec, 5).toString());
            }
        });

        this.vista.tblConductores.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int rec = vista.tblConductores.getSelectedRow();
                vista.txtIdConductor.setText(vista.tblConductores.getValueAt(rec, 0).toString());
            }
        });
    }

    public void GuardarControlador() {
        if (vista.txtIdConductor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un conductor", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        modeloVehiculo = new Vehiculo();
        modeloVehiculo.setPlaca(vista.txtPlaca.getText());
        modeloVehiculo.setModelo(vista.txtModelo.getText());
        modeloVehiculo.setEstado(vista.cbEstado.getSelectedItem().toString());
        modeloVehiculo.setTipo(vista.cbTipo.getSelectedItem().toString());
        modeloVehiculo.setId_conductor(Integer.parseInt(vista.txtIdConductor.getText()));
        modeloVehiculo.Guardar();
    }

    public void mostrarDatos() {

        modeloVehiculo = new Vehiculo();
        modeloVehiculo.mostrarVehiculos(vista.tblVehiculos);
    }

    public void mostrarConductores() {
        conductor.mostrarConductores(vista.tblConductores);
    }

    public void modificarDatos() {
        if (vista.txtIdConductor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un conductor", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (vista.txtIdVehiculo.getText().equals("")) {
            JOptionPane.showMessageDialog(null,"Debe seleccionar un vehículo","ERROR",JOptionPane.ERROR_MESSAGE );
            return;
        }
        modeloVehiculo = new Vehiculo();
        modeloVehiculo.setId_vehiculo(Integer.parseInt(vista.txtIdVehiculo.getText()));
        modeloVehiculo.setPlaca(vista.txtPlaca.getText());
        modeloVehiculo.setModelo(vista.txtModelo.getText());
        modeloVehiculo.setEstado(vista.cbEstado.getSelectedItem().toString());
        modeloVehiculo.setTipo(vista.cbTipo.getSelectedItem().toString());
        modeloVehiculo.setId_conductor(Integer.parseInt(vista.txtIdConductor.getText()));
        modeloVehiculo.Modificar();
    }

    public void eliminarDatos() {
        if (vista.txtIdVehiculo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un vehículo", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        modeloVehiculo = new Vehiculo();
        modeloVehiculo.setId_vehiculo(Integer.parseInt(vista.txtIdVehiculo.getText()));
        modeloVehiculo.Eliminar();
    }
}
