package adivinaquien.multijugador;

import adivinaquien.Constantes;
import adivinaquien.Persona;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * Clase-Hilo que controla el sistema de preguntas y descartes del juego.
 *
 * @author Daniel
 */
public class HiloMultijugador extends Thread {

    private boolean preguntado;
    private ArrayList<Persona> listado;
    private MultijugadorGUI frameJuego;
    private Component componente;

    public HiloMultijugador(MultijugadorGUI parentFrame, Component componente, boolean preguntado) throws IOException {
        frameJuego = parentFrame;
        this.componente = componente;
        this.listado = frameJuego.getPersonasList();
        this.preguntado = preguntado;
    }

    @Override
    public void run() {
        final JDialog dialogo = new JDialog(frameJuego, "Progreso", true);  // Dialogo modal para bloquear al usuario mientras trabaja el hilo.
        JProgressBar progreso = new JProgressBar(0, listado.size());        // Barra de progreso...
        dialogo.add(BorderLayout.CENTER, progreso);                         // Centrada
        dialogo.add(BorderLayout.NORTH, new JLabel("Calculando..."));       // Texto informativo por encima de la barra de progreso
        dialogo.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);      // Evitar que se cierre el dialogo
        dialogo.setSize(300, 75);
        dialogo.setUndecorated(true);               // El dialogo no dispone de barra de botones (cerrar, minimizar...)
        dialogo.setLocationRelativeTo(componente);  // Componente hara referencia al panel de opciones derecho, por lo que el dialogo estara centrado a la derecha

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                dialogo.setVisible(true);   // Arrancar y visualizar el dialogo en un hilo paralelo
                // para no bloquear la interfaz grafica y ver la barra de progreso
            }
        });
        t.start();
        for (int i = 0; i < listado.size(); i++) {
            switch (frameJuego.getPregunta()) {
                case Constantes.SEXO:
                    if (listado.get(i).getSexo().equalsIgnoreCase(frameJuego.getOpcion()) != preguntado) {
                        frameJuego.descartar(i);
                    }
                    break;
                case Constantes.PELO:
                    if (listado.get(i).getPelo().equalsIgnoreCase(frameJuego.getOpcion()) != preguntado) {
                        frameJuego.descartar(i);
                    }
                    break;
                case Constantes.OJOS:
                    if (listado.get(i).getOjos().equalsIgnoreCase(frameJuego.getOpcion()) != preguntado) {
                        frameJuego.descartar(i);
                    }
                    break;
                case Constantes.VELLO_FACIAL:
                    if (listado.get(i).getVello().equalsIgnoreCase(frameJuego.getOpcion()) != preguntado) {
                        frameJuego.descartar(i);
                    }
                    break;
                case Constantes.CARACTERISTICAS:
                case Constantes.ACCESORIOS:
                    boolean aux = listado.get(i).getRasgo(frameJuego.getOpcion()) != null;
                    if (aux != preguntado) {
                        frameJuego.descartar(i);
                    }
                    break;
                default:
                    break;
            }
            progreso.setValue(progreso.getValue() + 1);
            if (progreso.getValue() == progreso.getMaximum()) {
                progreso.setValue(progreso.getMinimum());
            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
        dialogo.dispose(); // Una vez terminado cerramos el dialogo y el usuario vuelve a poder interactuar
    }

}
