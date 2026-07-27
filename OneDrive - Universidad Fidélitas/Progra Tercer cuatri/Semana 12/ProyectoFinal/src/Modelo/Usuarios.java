package Modelo;

import java.sql.PreparedStatement;
import Datos.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Usuarios extends Roles {

    private int id_usuario;
    private String nombre;
    private String pass;
    private int telefono;

    public Usuarios() {

    }
    public Usuarios(int id_usuario, String nombre,String pass,int telefono,
            String TipoRol) {
        
        super(TipoRol);
        this.id_usuario = id_usuario;
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

    public void Usuario() {

        Conexion conectar = new Conexion();

        try {
            String sql = "INSERT INTO Usuarios VALUES (?,?)";

            PreparedStatement pstmt = conectar.conectar().prepareStatement(sql);
            pstmt.setInt(1, getId_usuario());
            pstmt.setString(2, getNombre());
            pstmt.setString(3, getPass());
            pstmt.setInt(4, getTelefono());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AgregarPersona(ArrayList lista, Usuarios objp) {
        lista.add(objp);
        JOptionPane.showMessageDialog(null, "Datos almacenados",
                 "Informacion", JOptionPane.INFORMATION_MESSAGE);
    }

    public void MostrarUsuarios(ArrayList<Usuarios> lista, JTable tabla) {
        DefaultTableModel tbl = new DefaultTableModel();
        int indice = 0;

        tbl.addColumn("CEDULA");
        tbl.addColumn("NOMBRE");
        tbl.addColumn("TELÉFONO");
        tbl.addColumn("CONTRASEÑA");
        tbl.addColumn("ROL");
        tbl.setRowCount(lista.size());

        for (indice = 0; indice < lista.size(); indice++) {
            tbl.setValueAt(lista.get(indice).getId_usuario(), indice, 0);
            tbl.setValueAt(lista.get(indice).getNombre(), indice, 1);
            tbl.setValueAt(lista.get(indice).getTelefono(), indice, 2);
            tbl.setValueAt(lista.get(indice).getPass(), indice, 3);
            tbl.setValueAt(lista.get(indice).getTipoRol(), indice, 4);
        }

        tabla.setModel(tbl);
    }

    public void ActualizarUsuarios(ArrayList<Usuarios> lista, Usuarios objUser) {
        int indice = 0;
        boolean bandera = false;

        for (indice = 0; indice < lista.size(); indice++) {
            if (lista.get(indice).getId_usuario() == objUser.getId_usuario()) {
                lista.get(indice).setNombre(objUser.getNombre());
                lista.get(indice).setTelefono(objUser.getTelefono());
                lista.get(indice).setPass(objUser.getPass());
                lista.get(indice).setTipoRol(objUser.getTipoRol());
                bandera = true;
            }
        }

        if (bandera == false) {
            JOptionPane.showMessageDialog(null, "Cédula no existe",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void EliminarUsuarios(ArrayList<Usuarios> lista, Usuarios objUser) {
        int indice = 0;
        boolean bandera = false;

        for (indice = 0; indice < lista.size(); indice++) {
            if (lista.get(indice).getId_usuario() == objUser.getId_usuario()) {
                lista.remove(indice);
                bandera = true;
            }
        }

        if (bandera == false) {
            JOptionPane.showMessageDialog(null, "Cédula no existe",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Datos eliminados",
                    "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public boolean buscarUsuario(ArrayList<Usuarios> lista, String usr, String pass) {
        int indice = 0;
        boolean bandera = false;

        for (indice = 0; indice < lista.size(); indice++) {
            if  (Integer.valueOf(lista.get(indice).getId_usuario()).equals(usr)
                        && lista.get(indice).getPass().equals(pass)) 
                    bandera = true;
                }
         return bandera;
            }
        }
