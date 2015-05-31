package adivinaquien;

import java.net.URL;

/**
 * Interfaz con Constantes utilizadas por el programa.
 *
 * @author Daniel
 */
public interface Constantes {

    public static final int PUERTO_SERVIDOR = 4455;

    public static final URL INTERROGANTE_NEGRO_URL = AdivinaQuien.class.getResource("imagenes/interroganteNegro.png");

    public static final String NOMBRE = "NOMBRE";
    public static final String IMAGEN = "IMAGEN";

    public static final String SEXO = "SEXO";
    public static final String[] S_LISTA = new String[]{
        Constantes.SEXO_HOMBRE, Constantes.SEXO_MUJER};
    public static final String SEXO_HOMBRE = "HOMBRE";
    public static final String SEXO_MUJER = "MUJER";

    public static final String OJOS = "OJOS";
    public static final String[] O_LISTA = new String[]{
        Constantes.OJOS_AZUL, Constantes.OJOS_MARRON, Constantes.OJOS_VERDE};
    public static final String OJOS_AZUL = "AZULES";
    public static final String OJOS_MARRON = "MARRONES";
    public static final String OJOS_VERDE = "VERDES";

    public static final String PELO = "PELO";
    public static final String[] P_LISTA = new String[]{
        Constantes.PELO_AMARILLO, Constantes.PELO_BLANCO, Constantes.PELO_MARRON, Constantes.PELO_NARANJA, Constantes.PELO_NEGRO};
    public static final String PELO_AMARILLO = "RUBIO";
    public static final String PELO_BLANCO = "BLANCO";
    public static final String PELO_MARRON = "CASTAÑO";
    public static final String PELO_NARANJA = "PELIRROJO";
    public static final String PELO_NEGRO = "MORENO";

    public static final String CARACTERISTICAS = "CARACTERISTICAS";
    public static final String[] C_LISTA = new String[]{
        Constantes.C_ARRUGAS, Constantes.C_BERRUGA, Constantes.C_CALVICIE, Constantes.C_CEJAS, Constantes.C_COLETAS, Constantes.C_PECAS};
    public static final String C_ARRUGAS = "ARRUGAS";
    public static final String C_BERRUGA = "BERRUGA";
    public static final String C_CALVICIE = "CALVICIE";
    public static final String C_CEJAS = "CEJAS ANCHAS";
    public static final String C_COLETAS = "COLETAS";
    public static final String C_PECAS = "PECAS";

    public static final String VELLO_FACIAL = "VELLO FACIAL";
    public static final String[] V_LISTA = new String[]{
        Constantes.V_BARBA, Constantes.V_BIGOTE, Constantes.V_PERILLA, Constantes.V_SINVELLO};
    public static final String V_BARBA = "BARBA";
    public static final String V_BIGOTE = "BIGOTE";
    public static final String V_PERILLA = "PERILLA";
    public static final String V_SINVELLO = "¿TIENE VELLO?";

    public static final String ACCESORIOS = "ACCESORIOS";
    public static final String[] A_LISTA = new String[]{
        Constantes.A_PELO, Constantes.A_COLLAR, Constantes.A_GAFAS, Constantes.A_PENDIENTES, Constantes.A_PINTALABIOS, Constantes.A_SOMBRERO};
    public static final String A_COLLAR = "COLLAR o COLGANTE";
    public static final String A_GAFAS = "GAFAS";
    public static final String A_PELO = "ACCESORIOS DE PELO";
    public static final String A_PENDIENTES = "PENDIENTES";
    public static final String A_PINTALABIOS = "PINTALABIOS";
    public static final String A_SOMBRERO = "SOMBRERO";

}
