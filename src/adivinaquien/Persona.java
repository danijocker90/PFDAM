package adivinaquien;

import java.io.Serializable;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 * Clase Persona, para gestionar los personajes del juego.
 *
 * @author Daniel
 */
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    private final HashMap<String, Object> rasgos;

    public Persona(String nombre, String sexo, String pelo, String ojos, String vello, String[] otros) {
        rasgos = new HashMap<>(5);
        rasgos.put("NOMBRE", nombre);
        rasgos.put("SEXO", sexo);
        rasgos.put("PELO", pelo);
        rasgos.put("OJOS", ojos);
        if (vello != null) {
            rasgos.put("VELLO", vello);
        }
        rasgos.put("IMAGEN", new ImageIcon(getClass().getResource("imagenes/personas/" + nombre + ".png")));
        for (String rasgo : otros) {
            rasgos.put(rasgo.toUpperCase(), true);
        }
    }

    public HashMap<String, Object> getRasgos() {
        return rasgos;
    }

    public Object getRasgo(String key) {
        String clave = key.toUpperCase(); // Claves en mayusculas
        return rasgos.get(clave);
    }

    public void setRasgo(String key, Object value) {
        String clave = key.toUpperCase(); // Claves en mayusculas
        rasgos.put(clave, value);
    }

    public String getNombre() {
        return (String) rasgos.get("NOMBRE");
    }

    public String getSexo() {
        return (String) rasgos.get("SEXO");
    }

    public String getPelo() {
        return (String) rasgos.get("PELO");
    }

    public String getOjos() {
        return (String) rasgos.get("OJOS");
    }

    public String getVello() {
        if (rasgos.get("VELLO") == null) {
            return Constantes.V_SINVELLO;
        }
        return (String) rasgos.get("VELLO");
    }

    public ImageIcon getImagen() {
        return (ImageIcon) rasgos.get("IMAGEN");
    }

}
