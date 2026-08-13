package Controlador;

import Modelo.AsignacionPaquete;
import Modelo.Conductor;
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
    private Conductor conductor;

    private int idConductor;
    private int idPaquete;
    private int idVehiculo;

    public ControladorConductor(
            FormConductor vista,
            int idConductor) {

        this.vista = vista;
        this.idConductor = idConductor;
        this.asignacionPaquete = new AsignacionPaquete();
        this.conductor = new Conductor();
        clienteConductor = new ClienteConductor();
        clienteConductor.conectar();

        cargarPaquetes();

        this.vista.tblPaquetes.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int rec = vista.tblPaquetes.getSelectedRow();
                idPaquete = Integer.parseInt(vista.tblPaquetes.getValueAt(rec, 0).toString());
                idVehiculo = Integer.parseInt(vista.tblPaquetes.getValueAt(rec, 4).toString());
                vista.cbEstadoAct.setSelectedItem(vista.tblPaquetes.getValueAt(rec, 3).toString());
            }
        });

        this.vista.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });
        this.vista.btnActualizar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cargarPaquetes();
                idPaquete = 0;
                idVehiculo = 0;
                vista.txtIncidencia.setText("");
            }
        });

        this.vista.btnSalir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clienteConductor.cerrarConexion();
                Vista.FormLog login = new Vista.FormLog();
                login.setVisible(true);
                vista.dispose();
            }
        });
    }

    public void cargarPaquetes() {

        try {

            DefaultTableModel modelo = asignacionPaquete.obtenerPaquetesConductor(idConductor);
            vista.tblPaquetes.setModel(modelo);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(vista, "Error al cargar paquetes: " + e.getMessage());
        }
    }

    public void guardarCambios() {

        if (idPaquete == 0) {

            JOptionPane.showMessageDialog(null, "Debe seleccionar un paquete", "ERROR", JOptionPane.ERROR_MESSAGE);

            return;
        }

        String estado = vista.cbEstadoAct
                .getSelectedItem()
                .toString()
                .trim();

        String incidencia = vista.txtIncidencia
                .getText()
                .trim();

        if (estado.equalsIgnoreCase("Incidencia")
                && incidencia.equals("")) {

            JOptionPane.showMessageDialog(null, "Debe escribir la incidencia", "ERROR", JOptionPane.ERROR_MESSAGE);

            return;
        }

        asignacionPaquete.actualizarEstadoPaquete(idPaquete, idVehiculo, estado);

        if (estado.equalsIgnoreCase("Incidencia")) {

            asignacionPaquete.registrarIncidencia(idPaquete, idConductor, incidencia
            );
        }

        String nombreConductor = conductor.obtenerNombreConductor(idConductor);

        String mensaje
                = nombreConductor
                + "|" + estado
                + "|" + incidencia;

        System.out.println("ENVIANDO: " + mensaje);
        clienteConductor.enviarMensaje(mensaje);
        cargarPaquetes();

        vista.txtIncidencia.setText("");

        idPaquete = 0;
        idVehiculo = 0;

    }
}
