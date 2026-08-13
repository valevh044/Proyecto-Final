package Modelo;

import Datos.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AsignacionPaquete {

    public DefaultTableModel obtenerPaquetes() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.setColumnIdentifiers(
                new Object[]{"ID PAQUETE", "FECHA", "CÓDIGO DE RASTREO", "DESTINATARIO", "ESTADO"});

        Conexion conectar = new Conexion();

        try {

            String sql = "SELECT id_paquete, fecha, codigo_rastreo, destinatario, estado "
                    + "FROM paquetes "
                    + "WHERE estado = 'Pendiente'";

            PreparedStatement ps = conectar.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                modelo.addRow(
                        new Object[]{
                            rs.getInt("id_paquete"),
                            rs.getString("fecha"),
                            rs.getString("codigo_rastreo"),
                            rs.getString("destinatario"),
                            rs.getString("estado")
                        }
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al consultar paquetes: " + e.getMessage());
        }

        return modelo;
    }

    public DefaultTableModel obtenerVehiculosDisponibles() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(
                new Object[]{"ID VEHICULO", "PLACA", "TIPO", "ESTADO", "ID CONDUCTOR"});

        Conexion conectar = new Conexion();

        try {

            String sql = "SELECT id_vehiculo, placa, tipo, estado, id_conductor "
                    + "FROM vehiculos "
                    + "WHERE estado = 'Disponible'";

            PreparedStatement ps = conectar.conectar().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                modelo.addRow(
                        new Object[]{
                            rs.getInt("id_vehiculo"),
                            rs.getString("placa"),
                            rs.getString("tipo"),
                            rs.getString("estado"),
                            rs.getInt("id_conductor")
                        }
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al consultar vehículos: " + e.getMessage());
        }
        return modelo;
    }

    public void asignarPaquete(int idPaquete, int idVehiculo) {

        Conexion conectar = new Conexion();

        try {

            String sqlAsignacion
                    = "INSERT INTO asignaciones "
                    + "(fecha, id_paquete, id_vehiculo) "
                    + "VALUES (CURDATE(), ?, ?)";

            PreparedStatement psAsignacion = conectar.conectar().prepareStatement(sqlAsignacion);
            psAsignacion.setInt(1, idPaquete);
            psAsignacion.setInt(2, idVehiculo);
            psAsignacion.executeUpdate();

            String sqlPaquete
                    = "UPDATE paquetes "
                    + "SET estado = 'En tránsito' "
                    + "WHERE id_paquete = ?";

            PreparedStatement psPaquete = conectar.conectar().prepareStatement(sqlPaquete);
            psPaquete.setInt(1, idPaquete);
            psPaquete.executeUpdate();

            String sqlVehiculo
                    = "UPDATE vehiculos "
                    + "SET estado = 'En ruta' "
                    + "WHERE id_vehiculo = ?";

            PreparedStatement psVehiculo = conectar.conectar().prepareStatement(sqlVehiculo);
            psVehiculo.setInt(1, idVehiculo);
            psVehiculo.executeUpdate();

            JOptionPane.showMessageDialog(null, "Paquete asignado correctamente");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al asignar paquete: " + e.getMessage());
        }
    }

    public DefaultTableModel obtenerPaquetesConductor(int idConductor) {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.setColumnIdentifiers(
                new Object[]{
                    "ID PAQUETE",
                    "CÓDIGO DE RASTREO",
                    "DESTINATARIO",
                    "ESTADO",
                    "ID VEHICULO"
                });
        Conexion conectar = new Conexion();

        try {

            String sql = "SELECT paquetes.id_paquete, "
                    + "paquetes.codigo_rastreo, "
                    + "paquetes.destinatario, "
                    + "paquetes.estado, "
                    + "vehiculos.id_vehiculo "
                    + "FROM paquetes "
                    + "INNER JOIN asignaciones "
                    + "ON paquetes.id_paquete = asignaciones.id_paquete "
                    + "INNER JOIN vehiculos "
                    + "ON asignaciones.id_vehiculo = vehiculos.id_vehiculo "
                    + "WHERE vehiculos.id_conductor = ? "
                    + "AND paquetes.estado = 'En tránsito'";

            PreparedStatement ps = conectar.conectar().prepareStatement(sql);
            ps.setInt(1, idConductor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                modelo.addRow(
                        new Object[]{
                            rs.getInt("id_paquete"),
                            rs.getString("codigo_rastreo"),
                            rs.getString("destinatario"),
                            rs.getString("estado"),
                            rs.getInt("id_vehiculo")
                        }
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al consultar paquetes del conductor: " + e.getMessage());
        }

        return modelo;
    }

    public void actualizarEstadoPaquete(int idPaquete, int idVehiculo, String estado) {
        Conexion conectar = new Conexion();

        try {

            String sqlPaquete
                    = "UPDATE paquetes SET estado = ? "
                    + "WHERE id_paquete = ?";

            PreparedStatement psPaquete= conectar.conectar().prepareStatement(sqlPaquete);
            psPaquete.setString(1, estado);
            psPaquete.setInt(2, idPaquete);
            psPaquete.executeUpdate();

            if (estado.equals("Entregado") || estado.equals("Incidencia")) {

                String sqlVehiculo
                        = "UPDATE vehiculos SET estado = 'Disponible' "
                        + "WHERE id_vehiculo = ?";

                PreparedStatement psVehiculo = conectar.conectar().prepareStatement(sqlVehiculo);
                psVehiculo.setInt(1, idVehiculo);
                psVehiculo.executeUpdate();
            }

            JOptionPane.showMessageDialog(null,"Estado actualizado correctamente");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,"Error al actualizar estado: "+ e.getMessage());
        }
    }

    public void registrarIncidencia(int idPaquete, int idConductor, String reporte) {
        Conexion conectar = new Conexion();

        try {

            String sql
                    = "INSERT INTO incidencias "
                    + "(fecha, tipo, reporte, id_paquete, id_conductor) "
                    + "VALUES (CURDATE(), ?, ?, ?, ?)";

            PreparedStatement ps= conectar.conectar().prepareStatement(sql);

            ps.setString(1, "Incidencia");
            ps.setString(2, reporte);
            ps.setInt(3, idPaquete);
            ps.setInt(4, idConductor);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null,"Incidencia registrada correctamente");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,"Error al registrar incidencia: "+ e.getMessage());
        }
    }
}
