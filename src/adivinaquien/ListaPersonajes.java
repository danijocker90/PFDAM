package adivinaquien;

import java.util.ArrayList;

public class ListaPersonajes {

    /**
     * Metodo para generar cada uno de los personajes del juego
     *
     * @param lista
     * @return Lista con los personajes añadidos
     */
    public static ArrayList<Persona> cargarPersonajes(ArrayList<Persona> lista) {
        // El ultimo array representa las caracteristicas especiales: gafas, joyas, etc.
        // new String[]{} == Sin caracteristicas especiales.
        lista.add(new Persona("Alex", Constantes.SEXO_HOMBRE, Constantes.PELO_MARRON, Constantes.OJOS_MARRON, Constantes.V_BIGOTE,
                new String[]{}));
        lista.add(new Persona("Alfred", Constantes.SEXO_HOMBRE, Constantes.PELO_NARANJA, Constantes.OJOS_AZUL, Constantes.V_PERILLA,
                new String[]{}));
        lista.add(new Persona("Anita", Constantes.SEXO_MUJER, Constantes.PELO_AMARILLO, Constantes.OJOS_AZUL, null, // Sin vello = null
                new String[]{Constantes.C_COLETAS}));
        lista.add(new Persona("Anne", Constantes.SEXO_MUJER, Constantes.PELO_NEGRO, Constantes.OJOS_MARRON, null,
                new String[]{Constantes.A_PENDIENTES, Constantes.A_PINTALABIOS}));
        lista.add(new Persona("Bernard", Constantes.SEXO_HOMBRE, Constantes.PELO_MARRON, Constantes.OJOS_MARRON, null,
                new String[]{Constantes.A_SOMBRERO}));
        lista.add(new Persona("Bill", Constantes.SEXO_HOMBRE, Constantes.PELO_NARANJA, Constantes.OJOS_AZUL, Constantes.V_PERILLA,
                new String[]{Constantes.C_ARRUGAS, Constantes.C_CALVICIE, Constantes.C_CEJAS}));
        lista.add(new Persona("Charles", Constantes.SEXO_HOMBRE, Constantes.PELO_AMARILLO, Constantes.OJOS_VERDE, Constantes.V_BIGOTE,
                new String[]{}));
        lista.add(new Persona("Claire", Constantes.SEXO_MUJER, Constantes.PELO_NARANJA, Constantes.OJOS_MARRON, null,
                new String[]{Constantes.A_GAFAS, Constantes.A_PENDIENTES, Constantes.A_COLLAR, Constantes.A_PELO, Constantes.A_PINTALABIOS}));
        lista.add(new Persona("David", Constantes.SEXO_HOMBRE, Constantes.PELO_AMARILLO, Constantes.OJOS_MARRON, Constantes.V_BARBA,
                new String[]{}));
        lista.add(new Persona("Eric", Constantes.SEXO_HOMBRE, Constantes.PELO_AMARILLO, Constantes.OJOS_VERDE, null,
                new String[]{Constantes.A_SOMBRERO}));
        lista.add(new Persona("Frans", Constantes.SEXO_HOMBRE, Constantes.PELO_NARANJA, Constantes.OJOS_AZUL, null,
                new String[]{Constantes.A_COLLAR, Constantes.C_PECAS}));
        lista.add(new Persona("George", Constantes.SEXO_HOMBRE, Constantes.PELO_BLANCO, Constantes.OJOS_MARRON, null,
                new String[]{Constantes.C_ARRUGAS, Constantes.A_SOMBRERO}));
        lista.add(new Persona("Herman", Constantes.SEXO_HOMBRE, Constantes.PELO_NARANJA, Constantes.OJOS_MARRON, null,
                new String[]{Constantes.C_ARRUGAS, Constantes.C_CALVICIE, Constantes.C_CEJAS}));
        lista.add(new Persona("Joe", Constantes.SEXO_HOMBRE, Constantes.PELO_AMARILLO, Constantes.OJOS_AZUL, null,
                new String[]{Constantes.A_GAFAS}));
        lista.add(new Persona("Maria", Constantes.SEXO_MUJER, Constantes.PELO_MARRON, Constantes.OJOS_MARRON, null,
                new String[]{Constantes.A_PELO, Constantes.A_PENDIENTES, Constantes.A_PINTALABIOS}));
        lista.add(new Persona("Max", Constantes.SEXO_HOMBRE, Constantes.PELO_MARRON, Constantes.OJOS_VERDE, Constantes.V_BIGOTE,
                new String[]{}));
        lista.add(new Persona("Paul", Constantes.SEXO_HOMBRE, Constantes.PELO_BLANCO, Constantes.OJOS_AZUL, null,
                new String[]{Constantes.C_ARRUGAS, Constantes.A_GAFAS}));
        lista.add(new Persona("Peter", Constantes.SEXO_HOMBRE, Constantes.PELO_BLANCO, Constantes.OJOS_VERDE, null,
                new String[]{Constantes.C_ARRUGAS, Constantes.C_CEJAS}));
        lista.add(new Persona("Philip", Constantes.SEXO_HOMBRE, Constantes.PELO_NEGRO, Constantes.OJOS_MARRON, Constantes.V_BARBA,
                new String[]{}));
        lista.add(new Persona("Richard", Constantes.SEXO_HOMBRE, Constantes.PELO_MARRON, Constantes.OJOS_VERDE, Constantes.V_PERILLA,
                new String[]{Constantes.C_CALVICIE}));
        lista.add(new Persona("Robert", Constantes.SEXO_HOMBRE, Constantes.PELO_MARRON, Constantes.OJOS_MARRON, null,
                new String[]{}));
        lista.add(new Persona("Sam", Constantes.SEXO_HOMBRE, Constantes.PELO_BLANCO, Constantes.OJOS_AZUL, null,
                new String[]{Constantes.C_ARRUGAS, Constantes.A_GAFAS, Constantes.C_CALVICIE}));
        lista.add(new Persona("Susan", Constantes.SEXO_MUJER, Constantes.PELO_BLANCO, Constantes.OJOS_VERDE, null,
                new String[]{Constantes.A_PINTALABIOS, Constantes.A_COLLAR, Constantes.C_BERRUGA}));
        lista.add(new Persona("Tom", Constantes.SEXO_HOMBRE, Constantes.PELO_NEGRO, Constantes.OJOS_AZUL, null,
                new String[]{Constantes.A_GAFAS, Constantes.C_CALVICIE}));
        return lista;
    }

    private ListaPersonajes() {
    }
}
