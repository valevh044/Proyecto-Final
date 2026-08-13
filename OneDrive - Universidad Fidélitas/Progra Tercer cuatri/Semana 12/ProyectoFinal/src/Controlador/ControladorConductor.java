package Controlador;

import Modelo.AsignacionPaquete;
import Servidor.ClienteConductor;
import Vista.FormConductor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControladorConductor {

    private FormConductor vista;
    private AsignacionPaquete asignacionPaquete;
    private ClienteConductor clienteConductor;

    private int idConductor;
    private int idPaquete;
    private int idVehiculo;

    public ControladorConductor(
            FormConductor vista,
            int idConductor) {

        this.vista = vista;
        this.idConductor = idConductor;
        this.asignacionPaquete = new AsignacionPaquete();

        clienteConductor = new ClienteConductor();

        clienteConductor.conectar();

        cargarPaquetes();

        this.vista.tblPaquetes.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                int rec = vista.tblPaquetes.getSelectedRow();

                idPaquete = Integer.parseInt(
                        vista.tblPaquetes.getValueAt(rec, 0).toString()
                );

                idVehiculo = Integer.parseInt(
                        vista.tblPaquetes.getValueAt(rec, 4).toString()
                );

                vista.cbEstadoAct.setSelectedItem(
                        vista.tblPaquetes.getValueAt(rec, 3).toString()
                );
            }
        });

        this.vista.btnGuardar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                guardarCambios();
            }
        });
    }

    public void cargarPaquetes() {

        try {

            DefaultTableModel modelo
                    = asignacionPaquete.obtenerPaquetesConductor(
                            idConductor
                    );

            vista.tblPaquetes.setModel(modelo);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al cargar paquetes: "
                    + e.getMessage()
            );
        }
    }

    public void guardarCambios() {

        if (idPaquete == 0) {

            JOptionPane.showMessageDialog(
                    null,
                    "Debe seleccionar un paquete",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        String estado
                = vista.cbEstadoAct.getSelectedItem().toString();

        if (estado.equals("Incidencia")
                && vista.txtIncidencia.getText().equals("")) {

            JOptionPane.showMessageDialog(
                    null,
                    "Debe escribir la incidencia",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        asignacionPaquete.actualizarEstadoPaquete(
                idPaquete,
                idVehiculo,
                estado
        );

        if (estado.equals("Incidencia")) {

            asignacionPaquete.registrarIncidencia(
                    idPaquete,
                    idConductor,
                    vista.txtIncidencia.getText()
            );
        }


        // Enviar cambio al servidor

        String mensaje
                = "Conductor: " + idConductor
                + " / Paquete: " + idPaquete
                + " / Estado: " + estado;

        if (estado.equals("Incidencia")) {

            mensaje = mensaje
                    + " / Reporte: "
                    + vista.txtIncidencia.getText();
        }

        clienteConductor.enviarMensaje(mensaje);


        cargarPaquetes();

        vista.txtIncidencia.setText("");

        idPaquete = 0;
        idVehiculo = 0;
    }
}