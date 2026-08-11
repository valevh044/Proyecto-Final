/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

/**
 *
 * @author Nelson Cardona
 */
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

            BufferedReader entrada = new BufferedReader(new InputStreamReader( cliente.getInputStream()) );
            String mensaje = entrada.readLine();
            System.out.println("Mensaje recibido: " + mensaje);

            PrintWriter salida = new PrintWriter(cliente.getOutputStream(),true);

            salida.println("Servidor: estado recibido correctamente");
            System.out.println("Conductor atendido por hilo: "+ Thread.currentThread().getName());

            cliente.close();

        } catch (IOException e) {

            System.out.println( "Error con conductor: "+ e.getMessage() );
        }
    }
}
