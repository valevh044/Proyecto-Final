package Modelo;

import Datos.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    // Constructor sobrecargado
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

            String sql = "INSERT INTO usuarios " + "(cedula, nombre, password, telefono, id_rol) " + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conectar.conectar().prepareStatement(sql);

            pstmt.setString(1, getCedula());
            pstmt.setString(2, getNombre());
            pstmt.setString(3, getPass());
            pstmt.setInt(4, getTelefono());
            pstmt.setInt(5, idRol);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al guardar en la base de datos: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public void AgregarPersona(ArrayList<Usuarios> lista, Usuarios objp) {

        boolean existe = false;

        for (int indice = 0; indice < lista.size(); indice++) {

            if (lista.get(indice).getCedula().equals(objp.getCedula())) {
                existe = true;
            }
        }

        if (existe == true) {

            JOptionPane.showMessageDialog(null, "La cédula ya existe", "ERROR", JOptionPane.ERROR_MESSAGE);

        } else {

            boolean guardado = objp.Usuario();

            if (guardado == true) {

                lista.add(objp);

                JOptionPane.showMessageDialog(null, "Datos almacenados", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

        }

    
}
    

    public void MostrarUsuarios(
            ArrayList<Usuarios> lista,
            JTable tabla) {

        DefaultTableModel tbl = new DefaultTableModel();
        int indice = 0;

        tbl.addColumn("CEDULA");
        tbl.addColumn("NOMBRE");
        tbl.addColumn("TELÉFONO");
        tbl.addColumn("CONTRASEÑA");
        tbl.addColumn("ROL");

        tbl.setRowCount(lista.size());

        for (indice = 0; indice < lista.size(); indice++) {

            tbl.setValueAt(lista.get(indice).getCedula(), indice, 0);
            tbl.setValueAt(lista.get(indice).getNombre(), indice, 1);
            tbl.setValueAt(lista.get(indice).getTelefono(), indice, 2);
            tbl.setValueAt(lista.get(indice).getPass(), indice, 3);
            tbl.setValueAt(lista.get(indice).getTipoRol(), indice, 4);
        }

        tabla.setModel(tbl);
    }

    public void ActualizarUsuarios(
            ArrayList<Usuarios> lista,
            Usuarios objUser) {

        int indice = 0;
        boolean bandera = false;

        for (indice = 0; indice < lista.size(); indice++) {

            if (lista.get(indice).getCedula().equals(objUser.getCedula())) {
                lista.get(indice).setNombre(objUser.getNombre());
                lista.get(indice).setTelefono(objUser.getTelefono());
                lista.get(indice).setPass(objUser.getPass());
                lista.get(indice).setTipoRol(objUser.getTipoRol());

                bandera = true;
            }
        }

        if (bandera == false) {

            JOptionPane.showMessageDialog(null, "Cédula no existe", "ERROR", JOptionPane.ERROR_MESSAGE);

        } else {

            Conexion conectar = new Conexion();

            try {

                int idRol = 0;

                if (objUser.getTipoRol().equals("Administrador")) {
                    idRol = 1;
                } else if (objUser.getTipoRol().equals("Conductor")) {
                    idRol = 2;
                } else if (objUser.getTipoRol().equals("Despachador")) {
                    idRol = 3;
                }

                String sql = "UPDATE usuarios " + "SET nombre = ?, password = ?, " + "telefono = ?, id_rol = ? " + "WHERE cedula = ?";

                PreparedStatement pstmt
                        = conectar.conectar().prepareStatement(sql);

                pstmt.setString(1, objUser.getNombre());
                pstmt.setString(2, objUser.getPass());
                pstmt.setInt(3, objUser.getTelefono());
                pstmt.setInt(4, idRol);
                pstmt.setString(5, objUser.getCedula());

                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Datos actualizados", "Información", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(null, "Error al actualizar en la base de datos: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public void EliminarUsuarios(
            ArrayList<Usuarios> lista,
            Usuarios objUser) {

        int indice = 0;
        boolean bandera = false;

        for (indice = 0; indice < lista.size(); indice++) {

            if (lista.get(indice).getCedula().equals(objUser.getCedula())) {

                lista.remove(indice);
                bandera = true;
                break;
            }
        }

        if (bandera == false) {

            JOptionPane.showMessageDialog(null, "Cédula no existe", "ERROR", JOptionPane.ERROR_MESSAGE);

        } else {

            Conexion conectar = new Conexion();

            try {

                String sql = "DELETE FROM usuarios "
                        + "WHERE cedula = ?";

                PreparedStatement pstmt = conectar.conectar().prepareStatement(sql);

                pstmt.setString(1, objUser.getCedula());

                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Datos eliminados", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(null, "Error al eliminar en la base de datos: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public void ConsultarUsuarios(ArrayList<Usuarios> lista) {

        Conexion conectar = new Conexion();

        try {

            lista.clear();

            String sql = "SELECT usuarios.cedula, "
                    + "usuarios.nombre, "
                    + "usuarios.telefono, "
                    + "usuarios.password, "
                    + "roles.tipo_rol "
                    + "FROM usuarios "
                    + "INNER JOIN roles "
                    + "ON usuarios.id_rol = roles.id_rol";

            PreparedStatement pstmt = conectar.conectar().prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Usuarios objUsuario = new Usuarios();

                objUsuario.setCedula(rs.getString("cedula"));
                objUsuario.setNombre(rs.getString("nombre"));
                objUsuario.setTelefono(rs.getInt("telefono"));
                objUsuario.setPass(rs.getString("password"));
                objUsuario.setTipoRol(rs.getString("tipo_rol"));
                lista.add(objUsuario);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al consultar usuarios: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean buscarUsuario(
            ArrayList<Usuarios> lista,
            String usr,
            String pass) {

        int indice = 0;
        boolean bandera = false;

        for (indice = 0; indice < lista.size(); indice++) {

            if (lista.get(indice).getCedula().equals(usr)
                    && lista.get(indice).getPass().equals(pass)) {

                bandera = true;
            }
        }

        return bandera;
    }
}
