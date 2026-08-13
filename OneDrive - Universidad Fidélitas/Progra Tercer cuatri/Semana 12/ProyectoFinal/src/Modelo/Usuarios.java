package Modelo;

import Datos.Conexion;
import Vista.FormAdmin;
import Vista.FormConductor;
import Vista.FormDespachador;
import java.sql.Connection;
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

            Connection conn = conectar.conectar();
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

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, getCedula());
            pstmt.setString(2, getNombre());
            pstmt.setString(3, getPass());
            pstmt.setInt(4, getTelefono());
            pstmt.setInt(5, idRol);
            pstmt.executeUpdate();
            int idUsuario = 0;
            String sqlBuscar
                    = "SELECT id_usuario FROM usuarios "
                    + "WHERE cedula = ?";

            PreparedStatement pstmtBuscar= conn.prepareStatement(sqlBuscar);
            pstmtBuscar.setString(1, getCedula());
            ResultSet rs = pstmtBuscar.executeQuery();

            if (rs.next()) {
                idUsuario = rs.getInt("id_usuario");
            }

            rs.close();
            pstmtBuscar.close();

            if (idRol == 2) {

                String sqlConductor
                        = "INSERT INTO conductores "
                        + "(cedula, nombre, telefono, id_usuario) "
                        + "VALUES (?,?,?,?)";

                PreparedStatement pstmtConductor= conn.prepareStatement(sqlConductor);
                pstmtConductor.setString(1, getCedula());
                pstmtConductor.setString(2, getNombre());
                pstmtConductor.setInt(3, getTelefono());
                pstmtConductor.setInt(4, idUsuario);
                pstmtConductor.executeUpdate();
                pstmtConductor.close();
            }

            pstmt.close();
            conn.close();
            JOptionPane.showMessageDialog(null,"Usuario registrado correctamente" );

            return true;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al guardar en la base de datos: "  + e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }

    public void ActualizarUsuarioBD() {

        Conexion conectar = new Conexion();

        try {
            Connection conn = conectar.conectar();
            int idRol = 0;

            if (getTipoRol().equals("Administrador")) {
                idRol = 1;
            } else if (getTipoRol().equals("Conductor")) {
                idRol = 2;
            } else if (getTipoRol().equals("Despachador")) {
                idRol = 3;
            }

            String sql
                    = "UPDATE usuarios "
                    + "SET nombre = ?, password = ?, "
                    + "telefono = ?, id_rol = ? "
                    + "WHERE cedula = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, getNombre());
            pstmt.setString(2, getPass());
            pstmt.setInt(3, getTelefono());
            pstmt.setInt(4, idRol);
            pstmt.setString(5, getCedula());
            int filas = pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            if (filas > 0) {

                JOptionPane.showMessageDialog(null,"Usuario actualizado correctamente" );
            } else {
                JOptionPane.showMessageDialog(null,"La cédula no existe","ERROR",JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al actualizar usuario: "+ e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void EliminarUsuarioBD() {
        Conexion conectar = new Conexion();
        try {

            Connection conn = conectar.conectar();
            String sql = "DELETE FROM usuarios WHERE cedula = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, getCedula());
            int filas = pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            if (filas > 0) {

                JOptionPane.showMessageDialog(null,"Usuario eliminado correctamente");

            } else {

                JOptionPane.showMessageDialog(null,"La cédula no existe","ERROR",JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarUsuariosBD(JTable tablaUsuarios) {

        Conexion conectar = new Conexion();

        DefaultTableModel tabla= new DefaultTableModel();

        tabla.addColumn("CEDULA");
        tabla.addColumn("NOMBRE");
        tabla.addColumn("TELÉFONO");
        tabla.addColumn("CONTRASEÑA");
        tabla.addColumn("ROL");

        try {

            Connection conn = conectar.conectar();

            String sql
                    = "SELECT usuarios.cedula, "
                    + "usuarios.nombre, "
                    + "usuarios.telefono, "
                    + "usuarios.password, "
                    + "roles.tipo_rol "
                    + "FROM usuarios "
                    + "INNER JOIN roles "
                    + "ON usuarios.id_rol = roles.id_rol";

            PreparedStatement pstmt = conn.prepareStatement(sql);

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

            rs.close();
            pstmt.close();
            conn.close();
            tablaUsuarios.setModel(tabla);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar usuarios: "+ e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean IniciarSesion(String cedula,String pass) {
        Conexion conectar = new Conexion();

        try {

            Connection conn = conectar.conectar();

            String sql
                    = "SELECT id_usuario, nombre, id_rol "
                    + "FROM usuarios "
                    + "WHERE cedula = ? AND password = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cedula);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                int idUsuario = rs.getInt("id_usuario");
                int idRol = rs.getInt("id_rol");
                if (idRol == 1) {

                    rs.close();
                    pstmt.close();
                    conn.close();

                    LogSistema log = new LogSistema();

                    log.setId_usuario(idUsuario);
                    log.Log();
                    FormAdmin admin = new FormAdmin();
                    admin.setVisible(true);

                    return true;
                }

                
                if (idRol == 2) {

                    rs.close();
                    pstmt.close();

                    String sqlConductor
                            = "SELECT id_conductor "
                            + "FROM conductores "
                            + "WHERE id_usuario = ?";

                    PreparedStatement pstmtConductor = conn.prepareStatement(sqlConductor);

                    pstmtConductor.setInt(1,idUsuario);
                    ResultSet rsConductor= pstmtConductor.executeQuery();
                    if (rsConductor.next()) {

                        int idConductor= rsConductor.getInt( "id_conductor" );
                        rsConductor.close();
                        pstmtConductor.close();
                        conn.close();

                        LogSistema log = new LogSistema();

                        log.setId_usuario(idUsuario);
                        log.Log();

                        FormConductor conductor= new FormConductor(idConductor  );
                        conductor.setVisible(true);
                        return true;

                    } else {

                        rsConductor.close();
                        pstmtConductor.close();
                        conn.close();
                        JOptionPane.showMessageDialog(null,"El usuario conductor "
                                + "no tiene registro "
                                + "en conductores","ERROR",JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }

               
                if (idRol == 3) {

                    rs.close();
                    pstmt.close();
                    conn.close();

                    LogSistema log = new LogSistema();
                    log.setId_usuario(idUsuario);
                    log.Log();
                    FormDespachador despachador = new FormDespachador();
                    despachador.setVisible(true);

                    return true;
                }

                rs.close();
                pstmt.close();
                conn.close();

                JOptionPane.showMessageDialog(null,"El usuario no tiene " + "un rol válido","ERROR",JOptionPane.ERROR_MESSAGE);

                return false;

            } else {

                rs.close();
                pstmt.close();
                conn.close();
                JOptionPane.showMessageDialog(null,"Cédula o contraseña incorrecta","ERROR",JOptionPane.ERROR_MESSAGE
                );

                return false;
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al iniciar sesión: "+ e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE );

            return false;
        }
    }
}