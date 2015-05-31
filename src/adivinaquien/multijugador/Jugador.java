package adivinaquien.multijugador;

import adivinaquien.Persona;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Alias: HiloServidor Hilo encargado de recoger y gestionar las comunicaciones entre el servidor y los clientes Trabaja desde el lado del servidor
 *
 * @author Daniel Alegre
 */
class Jugador extends Thread {

    Servidor servidor;
    String nombre;
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output, canal;   // El canal sera la salida hacia el cliente del otro jugador
    Persona personaje;

    Jugador(Socket socket, String nombre, Servidor servidor) {
        this.servidor = servidor;
        this.socket = socket;
        this.nombre = nombre;
        this.canal = null;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            // Saludar
            output.writeUTF("BIENVENIDO " + nombre);
            System.out.println("BIENVENIDO " + nombre);
            output.flush();
            // Recibir personaje asignado esta partida
            Object obj = input.readObject();
            PaqueteTCP pack = (PaqueteTCP) obj;
            personaje = pack.personaje;
            // Informar de la espera de otro jugador
            output.writeUTF("MENSAJE Espere al otro jugador.");
            output.flush();
        } catch (ClassNotFoundException | IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            // Todos online - Arranca el juego
            output.writeUTF("MENSAJE Todos conectados!");
            // Si este jugador es el primero en mover (Jugador 1) se le avisa
            if (nombre.equals(servidor.getJugadorActual())) {
                output.writeUTF("MENSAJE " + nombre + " es tu turno!");
            }
            output.flush();
            // Bucle principal. Listener de 1 cliente con el servidor
            while (true) {
                System.out.println("..." + nombre + " Escuchando...");
                String comando = input.readUTF();
                System.out.println(">>> " + comando);
                // Segun el comando recibido, realizar las acciones oportunas (Protocolos)
                if (comando.startsWith("PREGUNTAR")) {
                    if (nombre.equals(servidor.getJugadorActual())) { // Verificar que es tu turno
                        System.out.println("Orden >> Pregunta");
                        String pregunta = input.readUTF();
                        String opcion = input.readUTF();
                        // Reenviar la pregunta al otro jugador. Este hilo recordemos une con el servidor y el servidor media entre jugadores.
                        canal.writeUTF("CONTESTAR");
                        canal.writeUTF(pregunta);
                        canal.writeUTF(opcion);
                        canal.flush();
                        System.out.println("Pregunta reenviada");
                        // Tras realizar la pregunta, poner al jugador en espera. Ha jugado su turno.
                        output.writeUTF("MENSAJE Espera tu turno, por favor.");
                        output.flush();
                    } else {
                        output.writeUTF("MENSAJE Espera tu turno, por favor.");
                        output.flush();
                    }
                } else if (comando.startsWith("RESPONDER")) {
                    boolean respuesta = input.readBoolean();
                    // Responder al jugador original
                    // O lo que es lo mismo, reenviar la respuesta a si, por ejemplo, tiene gafas.
                    canal.writeUTF("DESCARTAR");
                    canal.writeBoolean(respuesta);
                    canal.flush();
                    System.out.println("Respuesta enviada -> " + respuesta);
                    // Se ha gestionado una pregunta, cambio de turno.
                    if (servidor.getJugadorActual().equals("Jugador 1")) {
                        servidor.setJugadorActual("Jugador 2");
                    } else {
                        servidor.setJugadorActual("Jugador 1");
                    }
                    // Hemos reenviado una respuesta, nos toca jugar.
                    // Enviar mensaje a nuestro cliente.
                    output.writeUTF("MENSAJE " + nombre + " es tu turno!");
                    output.flush();
                } else if (comando.startsWith("MENSAJE")) {
                    // Actualmente sin uso. Mensajes Internos del Servidor.
                    System.out.println("**** " + comando + " ****");
                } else if (comando.startsWith("CHAT")) {
                    // El cliente local desea enviar un mensaje. Reenviar al otro jugador.
                    String chat = input.readUTF();
                    canal.writeUTF("CHAT");
                    canal.writeUTF(chat);
                    canal.flush();
                } else if (comando.startsWith("ADIVINAR")) {
                    // Reenviar Intento de Adivinar.
                    canal.writeUTF(comando);
                    canal.flush();
                } else if (comando.startsWith("RESULTADO")) {
                    // Recibir resultado de Adivinar (local)
                    // Responder al otro jugador si gana o pierde.
                    boolean respuesta = input.readBoolean();
                    if (respuesta) {
                        canal.writeUTF("VICTORIA");
                        System.out.println("Hurra!");
                    } else {
                        canal.writeUTF("DERROTA");
                        PaqueteTCP pack = new PaqueteTCP();
                        pack.personaje = personaje;
                        canal.writeObject(pack); // Si pierde se le informa de nuestro personaje
                        System.out.println("Oops!");
                    }
                    canal.flush();
                } else if (comando.startsWith("SALIR")) {
                    // Comando salir - Avisar al otro jugador de que salga tambien.
                    canal.writeUTF("SALIR");
                    canal.flush();
                    System.out.println("Orden >> SALIR");
                    break;
                } else {
                    // Comando desconocido.
                    System.out.println("???");
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                Thread.sleep(25); // Esperar a que se envien todos los mensajes y cerrar.
                canal.close();
                if (!socket.isClosed()) {
                    socket.close();
                }
                System.out.println(nombre + " se ha desconectado.");
            } catch (IOException | InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
