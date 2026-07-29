package Servidor;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorQuickDelivery {

    public static void main(String[] args) {

        try {

            ServerSocket servidor = new ServerSocket(5000);

            System.out.println("Servidor QuickDelivery iniciado puerto 5000");
            System.out.println("Esperando conductores");

            while (true) {

                Socket cliente = servidor.accept();

                System.out.println("Conductor conectado");
                HiloConductor hilo = new HiloConductor(cliente);
                hilo.start();
            }

        } catch (IOException e) {

            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }

}
