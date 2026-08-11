package Controlador;

import Datos.Conexion;
import Modelo.Vehiculo;
import Vista.FormVehiculos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControladorVehiculos {

    private Vehiculo modeloVehiculo;
    private FormVehiculos vista;
    private Conexion conectar = new Conexion();

    public ControladorVehiculos(Vehiculo modeloVehiculo, FormVehiculos vista) {
        this.modeloVehiculo = modeloVehiculo;
        this.vista = vista;
        mostrarDatos();

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
            public void mousePressed(MouseEvent e) {
                int rec = vista.tblVehiculos.getSelectedRow();

                vista.txtIdVehiculo.setText(vista.tblVehiculos.getValueAt(rec, 0).toString());
                vista.txtPlaca.setText(vista.tblVehiculos.getValueAt(rec, 1).toString());
                vista.txtModelo.setText(vista.tblVehiculos.getValueAt(rec, 2).toString());
                vista.cbEstado.setSelectedItem(vista.tblVehiculos.getValueAt(rec, 3).toString());
                vista.cbTipo.setSelectedItem(vista.tblVehiculos.getValueAt(rec, 4).toString());
            }
        });

    }

    public void GuardarControlador() {

        modeloVehiculo = new Vehiculo();
        modeloVehiculo.setPlaca(vista.txtPlaca.getText().toString());
        modeloVehiculo.setModelo(vista.txtModelo.getText().toString());
        modeloVehiculo.setEstado(vista.cbEstado.getSelectedItem().toString());
        modeloVehiculo.setTipo(vista.cbTipo.getSelectedItem().toString());
        modeloVehiculo.Guardar();

    }

    public void mostrarDatos() {
        modeloVehiculo = new Vehiculo();
        modeloVehiculo.mostrarVehiculos(vista.tblVehiculos);

    }

    public void modificarDatos() {

        modeloVehiculo = new Vehiculo();

        modeloVehiculo.setId_vehiculo(Integer.parseInt(vista.txtIdVehiculo.getText()));

        modeloVehiculo.setPlaca(vista.txtPlaca.getText());

        modeloVehiculo.setModelo(vista.txtModelo.getText());

        modeloVehiculo.setEstado(vista.cbEstado.getSelectedItem().toString());
        
        modeloVehiculo.setTipo(vista.cbTipo.getSelectedItem().toString());

        modeloVehiculo.Modificar();

    }

    public void eliminarDatos() {

        modeloVehiculo = new Vehiculo();

        modeloVehiculo.setId_vehiculo(Integer.parseInt(vista.txtIdVehiculo.getText()));

        modeloVehiculo.Eliminar();

    }
}
