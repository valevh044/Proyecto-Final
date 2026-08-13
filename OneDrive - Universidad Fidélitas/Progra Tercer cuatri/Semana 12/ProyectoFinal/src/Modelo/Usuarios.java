package Modelo;

import Datos.Conexion;
import Vista.FormAdmin;
import Vista.FormConductor;
import Vista.FormDespachador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Usuarios extends Roles {

    private int id_usuario;
    private String cedula;
    private String nombre;
    private String pass;
    private int telefono;

    public Usuarios() {
    }

    public Usuarios(int id_usuario, String cedula, String nombre,
            String pass, int telefono, String tipoRol) {

        super(tipoRol);

        this.id_usuario = id_usuario;
        this.cedula = cedula;
        this.nombre = nombre;
        this.pass = pass;
        this.telefono = telefono;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public boolean Usuario() {

        Conexion conectar = new Conexion();

        try {

            int idRol = 0;

            if (getTipoRol().equals("Administrador")) {
                idRol = 1;
            } else if (getTipoRol().equals("Conductor")) {
                idRol = 2;
            } else if (getTipoRol().equals("Despachador")) {
                idRol = 3;
            }

            String sql = "INSERT INTO usuarios "
                    + "(cedula, nombre, password, telefono, id_rol) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt
                    = conectar.conectar().prepareStatement(sql);

            pstmt.setString(1, getCedula());
            pstmt.setString(2, getNombre());
            pstmt.setString(3, getPass());
            pstmt.setInt(4, getTelefono());
            pstmt.setInt(5, idRol);

            pstmt.executeUpdate();

            if (idRol == 2) {

                String sqlConductor = "INSERT INTO conductores (cedula, nombre, telefono) VALUES (?,?,?)";

                PreparedStatement pstmtConductor
                        = conectar.conectar().prepareStatement(sqlConductor);

                pstmtConductor.setString(1, getCedula());

                pstmtConductor.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");

            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar en la base de datos: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void ActualizarUsuarioBD() {

        Conexion conectar = new Conexion();

        try {

            int idRol = 0;

            if (getTipoRol().equals("Administrador")) {
                idRol = 1;
            } else if (getTipoRol().equals("Conductor")) {
                idRol = 2;
            } else if (getTipoRol().equals("Despachador")) {
                idRol = 3;
            }

            String sql = "UPDATE usuarios " + "SET nombre = ?, password = ?, telefono = ?, id_rol = ? " + "WHERE cedula = ?";

            PreparedStatement pstmt = conectar.conectar().prepareStatement(sql);

            pstmt.setString(1, getNombre());
            pstmt.setString(2, getPass());
            pstmt.setInt(3, getTelefono());
            pstmt.setInt(4, idRol);
            pstmt.setString(5, getCedula());

            int filas = pstmt.executeUpdate();

            if (filas > 0) {

                JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente");

            } else {

                JOptionPane.showMessageDialog(null, "La cédula no existe", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void EliminarUsuarioBD() {

        Conexion conectar = new Conexion();

        try {

            String sql
                    = "DELETE FROM usuarios WHERE cedula = ?";

            PreparedStatement pstmt = conectar.conectar().prepareStatement(sql);

            pstmt.setString(1, getCedula());

            int filas = pstmt.executeUpdate();

            if (filas > 0) {

                JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");

            } else {

                JOptionPane.showMessageDialog(null, "La cédula no existe", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarUsuariosBD(JTable tablaUsuarios) {

        Conexion conectar = new Conexion();

        DefaultTableModel tabla
                = new DefaultTableModel();

        tabla.addColumn("CEDULA");
        tabla.addColumn("NOMBRE");
        tabla.addColumn("TELÉFONO");
        tabla.addColumn("CONTRASEÑA");
        tabla.addColumn("ROL");

        try {

            String sql
                    = "SELECT usuarios.cedula, "
                    + "usuarios.nombre, "
                    + "usuarios.telefono, "
                    + "usuarios.password, "
                    + "roles.tipo_rol "
                    + "FROM usuarios "
                    + "INNER JOIN roles "
                    + "ON usuarios.id_rol = roles.id_rol";

            PreparedStatement pstmt
                    = conectar.conectar().prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Object[] fila = new Object[5];

                fila[0] = rs.getString("cedula");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getInt("telefono");
                fila[3] = rs.getString("password");
                fila[4] = rs.getString("tipo_rol");

                tabla.addRow(fila);
            }

            tablaUsuarios.setModel(tabla);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error al consultar usuarios: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
