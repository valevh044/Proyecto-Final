package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteConductor {

    private Socket cliente;
    private PrintWriter salida;
    private BufferedReader entrada;

    public ClienteConductor() {
    }

    public boolean conectar() {

        try {

            cliente = new Socket("localhost", 6000);
            salida = new PrintWriter(cliente.getOutputStream(),true);
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()
                    )
            );

            System.out.println("Conductor conectado al servidor");
            return true;

        } catch (IOException e) {

            System.out.println("Error al conectar con el servidor: "+ e.getMessage());

            return false;
        }
    }

    public void enviarMensaje(String mensaje) {

        try {

            salida.println(mensaje);
            String respuesta = entrada.readLine();
            System.out.println(respuesta);

        } catch (IOException e) {

            System.out.println( "Error al enviar mensaje: " + e.getMessage());
        }
    }

    public void cerrarConexion() {

        try {

            if (cliente != null) {
                cliente.close();
            }

        } catch (IOException e) {

            System.out.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}