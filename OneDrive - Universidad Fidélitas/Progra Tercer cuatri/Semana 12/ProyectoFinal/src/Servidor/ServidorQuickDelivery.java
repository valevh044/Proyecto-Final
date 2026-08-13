package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import Servidor.HiloConductor;
import Vista.FormAdmin;

public class ServidorQuickDelivery {

    private ServerSocket servidor;
    private FormAdmin formAdmin;

    public ServidorQuickDelivery(FormAdmin formAdmin) {
        this.formAdmin = formAdmin;
    }

    public void iniciarServidor() {

        try {

            servidor = new ServerSocket(6000);
            formAdmin.mostrarMensaje("Servidor QuickDelivery iniciado puerto 6000");
            formAdmin.mostrarMensaje("Esperando conductores...");

            while (true) {

                Socket cliente = servidor.accept();
                formAdmin.mostrarMensaje("Nuevo conductor conectado");
                HiloConductor hilo = new HiloConductor(cliente, formAdmin);
                hilo.start();
            }

        } catch (IOException e) {

            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }

    public void cerrarServidor() {
    try {
        if (servidor != null) {
            servidor.close();
        }
    } catch (IOException e) {
        System.out.println( "Error al cerrar servidor: "+ e.getMessage());
    }
}
}
