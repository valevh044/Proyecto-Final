/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

/**
 *
 * @author jonat
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ServidorQuickDelivery {

    public static void main(String[] args) {

        try {

            ServerSocket servidor = new ServerSocket(5000);

            System.out.println("Servidor QuickDelivery iniciado puerto 5000");
            System.out.println("Esperando conductores");

            while (true) {

                Socket cliente = servidor.accept();
                BufferedReader entrada = new BufferedReader( new InputStreamReader(cliente.getInputStream()));
                String mensaje = entrada.readLine();
                System.out.println("Mensaje recibido: " + mensaje);
                PrintWriter salida = new PrintWriter(cliente.getOutputStream(),true);
                salida.println("Servidor: estado recibido correctamente");
                System.out.println("Conductor conectado");
            }

        } catch (IOException e) {

            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }

}
