package adivinaquien.multijugador;

import adivinaquien.Constantes;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Hilo Principal del Servidor Multijugador
 *
 * @author Daniel
 */
public class Servidor extends Thread {

    public static void main(String[] args) {
        new Servidor().start();
    }

    private String jugadorActual;
    private ServerSocket servidor;
    private Jugador jugador1, jugador2;

    public Servidor() {
        try {
            servidor = new ServerSocket(Constantes.PUERTO_SERVIDOR);
            System.out.println("Servidor Iniciado.");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public synchronized String getJugadorActual() {
        return jugadorActual;
    }

    public synchronized void setJugadorActual(String player) {
        jugadorActual = player;
    }

    @Override
    public void run() {
        try {
            while (true) {
                jugador1 = new Jugador(servidor.accept(), "Jugador 1", this);
                jugador2 = new Jugador(servidor.accept(), "Jugador 2", this);
                jugador1.canal = jugador2.output;
                jugador2.canal = jugador1.output;
                jugadorActual = jugador1.nombre;
                jugador1.start();
                jugador2.start();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                servidor.close();
                System.out.println("Servidor detenido.");
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

}
