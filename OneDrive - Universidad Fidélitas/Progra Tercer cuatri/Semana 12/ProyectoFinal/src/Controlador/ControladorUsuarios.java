package Controlador;

import Modelo.Usuarios;
import Modelo.LogSistema;
import Modelo.Roles;
import Vista.FormLog;
import Datos.Conexion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.io.FileOutputStream;

public class ControladorUsuarios {

    private Usuarios modeloUsuario;
    private LogSistema modeloLog;
    private Roles modeloRoles;
    private FormLog log;
    private Conexion conectar = new Conexion();

    public ControladorUsuarios(Usuarios modeloUsuario, FormLog Log) {
        this.modeloUsuario = modeloUsuario;
        this.log = log;

        this.log.btn_Ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioControlador();
                LogControlador();
                RolesControlador();
            }
        });
    }

    public void UsuarioControlador() {

        modeloUsuario = new Usuarios();
        modeloUsuario.setId_usuario(Integer.parseInt(log.txt_Id.getText().toString()));
        modeloUsuario.setNombre(log.txt_Nombre.getText());
        modeloUsuario.setPass(log.txt_Pass.getText());
        modeloUsuario.setTelefono(Integer.parseInt(log.txt_Telefono.getText().toString()));
        modeloUsuario.Usuario();

    }

    public void LogControlador() {

        modeloLog = new LogSistema();
        modeloLog.setId_log(Integer.parseInt(log.txt_Id.getText().toString()));
        modeloLog.setFecha(Integer.parseInt(log.txt_Fecha.getText().toString()));
        modeloLog.Log();

    }

    public void RolesControlador() {

        modeloRoles = new Roles();
        modeloRoles.setId_rol(Integer.parseInt(log.txt_Id.getText().toString()));
        modeloRoles.setTipoRol(log.cbRol.getSelectedItem().toString());
        modeloRoles.Roles();
    }
}
