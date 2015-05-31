package adivinaquien.multijugador;

public class ClienteMultijugador {

    public static void main(String[] args) {
        try {
            ClienteMultijugador clienteMultijugador = new ClienteMultijugador("localhost");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private MultijugadorGUI gui;

    public ClienteMultijugador(String host) throws Exception {
        gui = new MultijugadorGUI(host);
        gui.setVisible(true);
        gui.jugar();
        System.out.println("Desconectado");
    }

}
