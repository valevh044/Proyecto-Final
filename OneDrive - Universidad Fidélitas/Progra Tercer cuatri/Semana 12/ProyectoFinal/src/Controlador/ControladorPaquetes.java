package Controlador;

import Modelo.Paquete;
import Vista.FormPaquetes;
import Datos.Conexion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class ControladorPaquetes {

    private Paquete modeloPaquete;
    private FormPaquetes vista;
    private Conexion conectar = new Conexion();
    

    public ControladorPaquetes(Paquete modeloPaquete, FormPaquetes vista) {
        this.modeloPaquete = modeloPaquete;
        this.vista = vista;
        mostrarDatos();

        this.vista.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuardarControlador();
                mostrarDatos();
                limpiarCampos();
            }
        });

        this.vista.btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarDatos();
                mostrarDatos();
                limpiarCampos();
            }
        });

        this.vista.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarDatos();
                mostrarDatos();
                limpiarCampos();
            }
        });
        this.vista.tblPaquetes.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int rec = vista.tblPaquetes.getSelectedRow();

                vista.txtIdpaquete.setText(vista.tblPaquetes.getValueAt(rec, 0).toString());
                vista.txtFecha.setText(vista.tblPaquetes.getValueAt(rec, 1).toString());
                vista.txtCodigo.setText(vista.tblPaquetes.getValueAt(rec, 2).toString());
                vista.txtDestino.setText(vista.tblPaquetes.getValueAt(rec, 3).toString());
                vista.cbEstado.setSelectedItem(vista.tblPaquetes.getValueAt(rec, 4).toString());
            }
        });

    }

    public void GuardarControlador() {

        modeloPaquete = new Paquete();
        modeloPaquete.setFecha(java.sql.Date.valueOf(vista.txtFecha.getText()));
        modeloPaquete.setCodigo_rastreo(vista.txtCodigo.getText().toString());
        modeloPaquete.setDestinatario(vista.txtDestino.getText().toString());
        modeloPaquete.setEstado(vista.cbEstado.getSelectedItem().toString());
        modeloPaquete.Guardar();

    }

    public void mostrarDatos() {
        modeloPaquete = new Paquete();
        modeloPaquete.mostrarDatos(vista.tblPaquetes);

    }

    public void modificarDatos() {

        modeloPaquete = new Paquete();

        modeloPaquete.setId_Paquete(Integer.parseInt(vista.txtIdpaquete.getText()));

        modeloPaquete.setFecha(java.sql.Date.valueOf(vista.txtFecha.getText()));

        modeloPaquete.setCodigo_rastreo(vista.txtCodigo.getText());

        modeloPaquete.setDestinatario(vista.txtDestino.getText());

        modeloPaquete.setEstado(vista.cbEstado.getSelectedItem().toString());

        modeloPaquete.Modificar();

    }

    public void eliminarDatos() {

    modeloPaquete = new Paquete();

    modeloPaquete.setId_Paquete(Integer.parseInt(vista.txtIdpaquete.getText()));

    modeloPaquete.Eliminar();

}
    public void limpiarCampos() {

    vista.txtIdpaquete.setText("");
    vista.txtFecha.setText("");
    vista.txtCodigo.setText("");
    vista.txtDestino.setText("");
    vista.cbEstado.setSelectedIndex(0);

}
}
