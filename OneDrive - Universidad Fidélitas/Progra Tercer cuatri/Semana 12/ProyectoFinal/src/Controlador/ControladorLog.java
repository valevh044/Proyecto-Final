package Controlador;

import Modelo.Usuarios;
import Vista.FormAdmin;
import Vista.FormLog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorLog {

    private FormLog vista;

    public ControladorLog(FormLog vista) {

        this.vista = vista;

        this.vista.btn_Validar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
    }

    public void iniciarSesion() {

        String cedula = vista.txt_Cedula.getText();
        String pass = vista.txt_Pass.getText();
        if (cedula.equals("") || pass.equals("")) {
            JOptionPane.showMessageDialog(null,"Debe ingresar cédula y contraseña","ERROR",JOptionPane.ERROR_MESSAGE);

            return;
        }

    
        if (cedula.equals("305630197")
                && pass.equals("1234")) {
            FormAdmin admin = new FormAdmin();
            admin.setVisible(true);
            vista.setVisible(false);
            return;
        }

        Usuarios usuario = new Usuarios();
        boolean ingreso= usuario.IniciarSesion(cedula, pass);
        if (ingreso == true) {
            vista.setVisible(false);
        }
    }
}