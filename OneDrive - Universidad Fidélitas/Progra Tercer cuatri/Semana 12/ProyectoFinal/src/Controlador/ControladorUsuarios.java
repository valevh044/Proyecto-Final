package Controlador;

import Modelo.Usuarios;
import Vista.FormUsuarios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorUsuarios {

    private Usuarios modeloUsuario;
    private FormUsuarios vista;

    public ControladorUsuarios(Usuarios modeloUsuario, FormUsuarios vista) {

        this.modeloUsuario = modeloUsuario;
        this.vista = vista;

        mostrarUsuarios();

        this.vista.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarUsuario();
                mostrarUsuarios();
                limpiarCampos();
            }
        });

        this.vista.btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarUsuario();
                mostrarUsuarios();
                limpiarCampos();
            }
        });

        this.vista.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
                mostrarUsuarios();
                limpiarCampos();
            }
        });
        this.vista.tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                int rec = vista.tblUsuarios.getSelectedRow();

                vista.txtCedula.setText(vista.tblUsuarios.getValueAt(rec, 0).toString());
                vista.txtNombre.setText(vista.tblUsuarios.getValueAt(rec, 1).toString() );
                vista.txtTelefono.setText(vista.tblUsuarios.getValueAt(rec, 2).toString() );
                vista.txtContraseña.setText( vista.tblUsuarios.getValueAt(rec, 3).toString());
                vista.cbRol.setSelectedItem(vista.tblUsuarios.getValueAt(rec, 4).toString() );
            }
        });
    }

    public void agregarUsuario() {

        modeloUsuario = new Usuarios();

        modeloUsuario.setCedula(vista.txtCedula.getText());
        modeloUsuario.setNombre(vista.txtNombre.getText());
        modeloUsuario.setTelefono(Integer.parseInt(vista.txtTelefono.getText()));
        modeloUsuario.setPass(vista.txtContraseña.getText());
        modeloUsuario.setTipoRol(vista.cbRol.getSelectedItem().toString());

        modeloUsuario.Usuario();
    }

    public void actualizarUsuario() {

        modeloUsuario = new Usuarios();

        modeloUsuario.setCedula(vista.txtCedula.getText());
        modeloUsuario.setNombre(vista.txtNombre.getText());
        modeloUsuario.setTelefono(Integer.parseInt(vista.txtTelefono.getText()));
        modeloUsuario.setPass(vista.txtContraseña.getText());
        modeloUsuario.setTipoRol(vista.cbRol.getSelectedItem().toString());

        modeloUsuario.ActualizarUsuarioBD();
    }

    public void eliminarUsuario() {

        modeloUsuario = new Usuarios();

        modeloUsuario.setCedula(
                vista.txtCedula.getText()
        );

        modeloUsuario.EliminarUsuarioBD();
    }

    public void mostrarUsuarios() {

        modeloUsuario = new Usuarios();

        modeloUsuario.mostrarUsuariosBD(
                vista.tblUsuarios
        );
    }

    public void limpiarCampos() {

        vista.txtCedula.setText("");
        vista.txtNombre.setText("");
        vista.txtTelefono.setText("");
        vista.txtContraseña.setText("");
        vista.cbRol.setSelectedIndex(0);
    }

}
