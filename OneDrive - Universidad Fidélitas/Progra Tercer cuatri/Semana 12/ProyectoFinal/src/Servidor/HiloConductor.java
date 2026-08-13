package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloConductor extends Thread {

    private Socket cliente;

    public HiloConductor(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {

        try {

            BufferedReader entrada= new BufferedReader( new InputStreamReader( cliente.getInputStream()));
            PrintWriter salida= new PrintWriter(cliente.getOutputStream(),true);
            String mensaje;

            while ((mensaje = entrada.readLine()) != null) {

                System.out.println("Mensaje recibido: " + mensaje);
                System.out.println("Atendido por hilo: "+ Thread.currentThread().getName());
                salida.println( "Servidor: mensaje recibido correctamente" );
            }

            System.out.println("Conductor desconectado: "+ Thread.currentThread().getName());

            cliente.close();

        } catch (IOException e) {

            System.out.println("Error con conductor: " + e.getMessage());
        }
    }
}