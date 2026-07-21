
package Controlador;
import Modelo.Usuarios;
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
    private FormLog log;
    private Conexion conectar = new Conexion();
  
    public ControladorUsuarios(Usuarios modeloUsuario, FormLog Log) 
    {
        this.modeloUsuario = modeloUsuario;
        this.log = log;
        

        this.log.btn_Ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            }
        });
    }
}
