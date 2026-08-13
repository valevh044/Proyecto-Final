package Servidor;

import Vista.FormAdmin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloConductor extends Thread {

    private Socket cliente;
    private FormAdmin formAdmin;

    public HiloConductor(Socket cliente, FormAdmin formAdmin) {
        this.cliente = cliente;
        this.formAdmin = formAdmin;
    }

    @Override
    public void run() {

        try {

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(cliente.getInputStream())
            );

            PrintWriter salida = new PrintWriter(
                    cliente.getOutputStream(), true
            );

            String mensaje;

            while ((mensaje = entrada.readLine()) != null) {

                String[] datos = mensaje.split("\\|", -1);

                if (datos.length >= 3) {

                    String nombreConductor = datos[0];
                    String estadoPaquete = datos[1];
                    String incidencia = datos[2];

                    formAdmin.mostrarMensaje(
                            "Conductor/a " + nombreConductor
                            + " - Estado paquete: " + estadoPaquete
                            + "\nIncidencia: " + incidencia
                            + "\n"
                    );
                }

                salida.println(
                        "Servidor: mensaje recibido correctamente"
                );
            }

            formAdmin.mostrarMensaje(
                    "Conductor desconectado: "
                    + Thread.currentThread().getName()
            );

            cliente.close();

        } catch (IOException e) {

            formAdmin.mostrarMensaje(
                    "Error con conductor: " + e.getMessage()
            );
        }
    }
}
