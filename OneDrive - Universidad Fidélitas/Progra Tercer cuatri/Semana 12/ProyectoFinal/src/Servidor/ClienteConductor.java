/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

import java.io.IOException;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author jonat
 */
public class ClienteConductor {
    public static void main(String[] args) {

        try {

            Socket cliente = new Socket("localhost", 5000);
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(),true);
            salida.println("Conductor conectado / Estado: Disponible");
            BufferedReader entrada = new BufferedReader( new InputStreamReader(cliente.getInputStream()));
            String respuesta = entrada.readLine();
            System.out.println(respuesta);
            System.out.println("Conductor conectado al servidor");

        } catch (IOException e) {
            System.out.println("Error al conectar con el servidor: "+ e.getMessage()  );
        }
    }
    
}
