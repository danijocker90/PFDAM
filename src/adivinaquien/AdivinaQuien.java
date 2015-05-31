package adivinaquien;

import adivinaquien.multijugador.Servidor;

/**
 * Juego Adivina Quien? programado en Java.
 *
 * @author Daniel
 */
public class AdivinaQuien {

    public static void main(String[] args) {
        new Servidor().start();
        new MenuGUI().setVisible(true);
    }

}
