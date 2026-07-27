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

}
