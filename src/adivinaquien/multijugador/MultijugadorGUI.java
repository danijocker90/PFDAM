package adivinaquien.multijugador;

// <editor-fold defaultstate="collapsed">
import adivinaquien.Constantes;
import adivinaquien.ListaPersonajes;
import adivinaquien.Persona;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;
// </editor-fold>

/**
 * Interfaz grafica del juego Adivina Quien?
 *
 * @author Daniel
 */
public class MultijugadorGUI extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private Persona personaje;
    private Persona personajeRival;
    private ImageIcon imagenPersona;
    private ArrayList<Persona> personas;
    private ArrayList<JLabel> labelsPersonas;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean escuchando;
    private boolean jugando;

    /**
     * Constructor del Multijugador
     *
     * @param host
     */
    public MultijugadorGUI(String host) {
        initComponents();
        jugando = false;
        try {
            System.out.println("Conectando...");
            socket = new Socket(host, Constantes.PUERTO_SERVIDOR);
            System.out.println("Conectado");
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            personas = new ArrayList<>(1);
            labelsPersonas = new ArrayList<>(1);
            personas = ListaPersonajes.cargarPersonajes(personas);
            cargarImagenes();
            addComponentListener(new java.awt.event.ComponentAdapter() {
                @Override
                public void componentResized(java.awt.event.ComponentEvent evt) {
                    redimensionar();
                }
            });
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowStateChanged(WindowEvent e) {
                    redimensionar();
                }
            });
            // Autoscroll chat
            DefaultCaret caret = (DefaultCaret) txtAreaChat.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

            btnSalir.setVisible(false); // No permitir salir hasta que todos conectan (Evita fallos)
            setUndecorated(true); // Pantalla Completa
            setExtendedState(MAXIMIZED_BOTH); // Maximizado
            setVisible(true);
            setResizable(false); // Apariencia fija
            comboPreguntas.setSelectedIndex(4); // Seleccionar el index 4 (Sexo) para inicializar las opciones de preguntas

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MultijugadorGUI.this, "No ha sido posible conectar con " + host, "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    // <editor-fold defaultstate="collapsed">
    /**
     * Metodo que devuelve la categoria sobre la que se quiere preguntar
     *
     * @return Cadena de texto ejemplo: SEXO
     */
    public String getPregunta() {
        return comboPreguntas.getSelectedItem().toString();
    }

    /**
     * Metodo que devuelve la opcion seleccionada para preguntar
     *
     * @return Cadena de texto ejemplo: MUJER
     */
    public String getOpcion() {
        return grupoOpciones.getSelection().getActionCommand();
    }

    public ArrayList<Persona> getPersonasList() {
        return personas;
    }

    public Persona getPersona(int index) {
        return personas.get(index);
    }

    public Persona getPersonaje() {
        return personaje;
    }

    public Socket getSocket() {
        return socket;
    }

    /**
     * Actualizar y redimensionar la imagen de una label concreta
     *
     * @param index Numero de referencia
     */
    private void actualizarImagen(int index) {
        Dimension resolucion = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = (int) (resolucion.getWidth() - panelControles.getWidth()) / 7;  // Monitor - Panel Opciones dividido entre 7 (6+1 personajes)
        int alto = (int) resolucion.getHeight() / 5;                                // Monitor dividido entre 5 (4+1 personajes)
        // Para redimensionar, pasamos de ImageIcon a Image y, una vez redimensionado, volvemos a ImageIcon.
        imagenPersona = personas.get(index).getImagen(); // No modificamos la imagen original, asi siempre tendremos acceso a la calidad original.
        imagenPersona = new ImageIcon(imagenPersona.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT));
        labelsPersonas.get(index).setIcon(imagenPersona);
    }

    /**
     * Metodo para redimensionar las imagenes de los personajes de acuerdo al tamaño de la pantalla. Tambien usado para actualizar las imagenes.
     */
    private void redimensionar() {
        Dimension resolucion = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = (int) (resolucion.getWidth() - panelControles.getWidth()) / 7;  // Monitor - Panel Opciones dividido entre 7 (6+1 personajes)
        int alto = (int) resolucion.getHeight() / 5;                                // Monitor dividido entre 5 (4+1 personajes)
        for (int i = 0; i < personas.size(); i++) {
            // Para redimensionar, pasamos de ImageIcon a Image y, una vez redimensionado, volvemos a ImageIcon.
            imagenPersona = personas.get(i).getImagen(); // No modificamos la imagen original, asi siempre tendremos acceso a la calidad original.
            imagenPersona = new ImageIcon(imagenPersona.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT));
            labelsPersonas.get(i).setIcon(imagenPersona);
        }
    }
    // </editor-fold>

    /**
     * Metodo para descartar un personaje determinado
     *
     * @param index Referencia del personaje a descartar
     * @return Devuelve el resultado del descarte, si tuvo exito o la persona ya habia sido descartada
     */
    public boolean descartar(int index) {
        if (personas.get(index).getImagen().getImage() != new ImageIcon(Constantes.INTERROGANTE_NEGRO_URL).getImage()) {
            personas.get(index).setRasgo(Constantes.IMAGEN, new ImageIcon(Constantes.INTERROGANTE_NEGRO_URL));
            actualizarImagen(index);
            return true;
        }
        return false;
    }

    /**
     * Metodo que asigna un jugador al comenzar
     */
    private void asignarJugador() {
        Random aleatorio = new Random();
        asignarJugador(aleatorio.nextInt(personas.size()));
    }

    /**
     * Metodo que asigna un jugador al comenzar
     */
    private void asignarJugador(int index) {
        personaje = personas.get(index);
        ImageIcon img = new ImageIcon(personaje.getImagen().getImage().getScaledInstance(lbl_Jugador.getWidth(), lbl_Jugador.getHeight(), Image.SCALE_DEFAULT));
        lbl_Jugador.setIcon(img);
        lbl_InfoJugador.setText("Tu pesonaje: " + personaje.getNombre());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoOpciones = new javax.swing.ButtonGroup();
        panelPersonas = new javax.swing.JPanel();
        panelControles = new javax.swing.JPanel();
        btnSalir = new javax.swing.JButton();
        comboPreguntas = new javax.swing.JComboBox();
        panelOpciones = new javax.swing.JPanel();
        btnPreguntar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaChat = new javax.swing.JTextArea();
        btnResponderChat = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        txtChat = new javax.swing.JTextField();
        panelJugador = new javax.swing.JPanel();
        lbl_InfoJugador = new javax.swing.JLabel();
        lbl_Jugador = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Adivina Quien?");
        setExtendedState(6);

        panelPersonas.setPreferredSize(new java.awt.Dimension(600, 600));
        panelPersonas.setLayout(new java.awt.GridLayout(4, 6));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adivinaquien/imagenes/exit.png"))); // NOI18N
        btnSalir.setToolTipText("Salir");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setPreferredSize(new java.awt.Dimension(32, 32));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        comboPreguntas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ACCESORIOS", "CARACTERISTICAS", "OJOS", "PELO", "SEXO", "VELLO FACIAL" }));
        comboPreguntas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboPreguntasItemStateChanged(evt);
            }
        });

        panelOpciones.setLayout(new java.awt.GridLayout(0, 1));

        btnPreguntar.setText("Preguntar");
        btnPreguntar.setEnabled(false);
        btnPreguntar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreguntarActionPerformed(evt);
            }
        });

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setAutoscrolls(true);

        txtAreaChat.setEditable(false);
        txtAreaChat.setLineWrap(true);
        txtAreaChat.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtAreaChat);

        btnResponderChat.setText("Enviar");
        btnResponderChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResponderChatActionPerformed(evt);
            }
        });

        txtChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChatActionPerformed(evt);
            }
        });

        lbl_InfoJugador.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_InfoJugador.setText("Tu Personaje: ");

        lbl_Jugador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Jugador.setOpaque(true);
        lbl_Jugador.setPreferredSize(new java.awt.Dimension(25, 25));

        javax.swing.GroupLayout panelJugadorLayout = new javax.swing.GroupLayout(panelJugador);
        panelJugador.setLayout(panelJugadorLayout);
        panelJugadorLayout.setHorizontalGroup(
            panelJugadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJugadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelJugadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_Jugador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_InfoJugador, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelJugadorLayout.setVerticalGroup(
            panelJugadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJugadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_InfoJugador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(lbl_Jugador, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelControlesLayout = new javax.swing.GroupLayout(panelControles);
        panelControles.setLayout(panelControlesLayout);
        panelControlesLayout.setHorizontalGroup(
            panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelControlesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelControlesLayout.createSequentialGroup()
                .addGroup(panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelControlesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelOpciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelControlesLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelControlesLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(comboPreguntas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPreguntar)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelControlesLayout.createSequentialGroup()
                                .addComponent(txtChat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnResponderChat))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelControlesLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        panelControlesLayout.setVerticalGroup(
            panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelControlesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboPreguntas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPreguntar))
                .addGap(18, 18, 18)
                .addComponent(panelOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelJugador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResponderChat))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPersonas, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelControles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelPersonas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                    .addComponent(panelControles, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPreguntarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreguntarActionPerformed
        if (grupoOpciones.getSelection() != null) {
            try {
                output.writeUTF("PREGUNTAR");   // Enviar comando
                output.writeUTF(getPregunta()); // Enviar pregunta-categoria-combobox
                output.writeUTF(getOpcion());   // Enviar opcion-combobox
                output.flush();
                System.out.println("Preguntando...");
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnPreguntarActionPerformed

    private void btnResponderChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResponderChatActionPerformed
        if (txtChat.getText().length() > 0) {
            try {
                output.writeUTF("CHAT"); // Comando
                output.writeUTF(txtChat.getText()); // Enviar texto
                output.flush();
                txtAreaChat.append("Yo: " + txtChat.getText() + "\n"); // Publicar como 'Yo' en el area de chat
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        txtChat.setText("");
    }//GEN-LAST:event_btnResponderChatActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        try {
            output.writeUTF("SALIR"); // Comando salir - Ambos jugadores se desconectaran
            output.flush();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChatActionPerformed
        btnResponderChatActionPerformed(evt); // Pulsar Enter == Enviar chat
    }//GEN-LAST:event_txtChatActionPerformed

// <editor-fold defaultstate="collapsed">
    private void comboPreguntasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboPreguntasItemStateChanged
        JRadioButton radio;
        String[] lista;
        grupoOpciones = new ButtonGroup();
        switch (evt.getItem().toString()) {
            case Constantes.SEXO:
                lista = Constantes.S_LISTA;
                break;
            case Constantes.PELO:
                lista = Constantes.P_LISTA;
                break;
            case Constantes.OJOS:
                lista = Constantes.O_LISTA;
                break;
            case Constantes.VELLO_FACIAL:
                lista = Constantes.V_LISTA;
                break;
            case Constantes.CARACTERISTICAS:
                lista = Constantes.C_LISTA;
                break;
            case Constantes.ACCESORIOS:
                lista = Constantes.A_LISTA;
                break;
            default:
                lista = new String[0];
                break;
        }
        for (String opcion : lista) {           // Por cada opcion de la lista...
            radio = new JRadioButton(opcion);   // Crear opcion (radio)
            radio.setActionCommand(opcion);     // Añadir descripcion (accion), se usara para identificar la opcion seleccionada
            grupoOpciones.add(radio);           // Añadir al grupo
        }
        // Rellenar el panel de opciones con los miembros del grupoOpciones
        panelOpciones.removeAll();
        for (Enumeration<AbstractButton> opciones = grupoOpciones.getElements(); opciones.hasMoreElements();) {
            AbstractButton opcion = opciones.nextElement();
            panelOpciones.add(opcion);
        }
        panelOpciones.revalidate();
    }//GEN-LAST:event_comboPreguntasItemStateChanged
// </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPreguntar;
    private javax.swing.JButton btnResponderChat;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox comboPreguntas;
    private javax.swing.ButtonGroup grupoOpciones;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl_InfoJugador;
    private javax.swing.JLabel lbl_Jugador;
    private javax.swing.JPanel panelControles;
    private javax.swing.JPanel panelJugador;
    private javax.swing.JPanel panelOpciones;
    private javax.swing.JPanel panelPersonas;
    private javax.swing.JTextArea txtAreaChat;
    private javax.swing.JTextField txtChat;
    // End of variables declaration//GEN-END:variables

    private void cargarImagenes() {
        for (int i = 0; i < personas.size(); i++) {
            final JLabel label = new JLabel();
            label.setName(String.valueOf(i));           // Referencia numerica de label.
            label.setText(personas.get(i).getNombre().toUpperCase()); // Texto bajo las imagenes - Nombre
            label.setIcon(personas.get(i).getImagen()); // Imagen de cada persona.
            // Centrar Imagenes y Texto
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setHorizontalTextPosition(SwingConstants.CENTER);
            label.setVerticalTextPosition(SwingConstants.BOTTOM);
            // Agregar las labels al panel
            labelsPersonas.add(label);
            panelPersonas.add(label);
            // Evento - Pulsar sobre una imagen para intentar adivinar el personaje. Solo 1 intento. Fallo = Game Over
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Permitir adivinar solo cuando el juego ya ha empezado y si se pulsa sobre un personaje, no un interrogante
                    if (jugando && personas.get(Integer.valueOf(label.getName())).getImagen().getImage() != new ImageIcon(Constantes.INTERROGANTE_NEGRO_URL).getImage()) {
                        try {
                            personajeRival = personas.get(Integer.valueOf(label.getName()));
                            output.writeUTF("ADIVINAR->" + personajeRival.getNombre()); // Enviar el comando Adivinar y el nombre del personaje
                            output.flush();
                            System.out.println("¿Es " + personajeRival.getNombre() + "?");
                        } catch (IOException ex) {
                            System.err.println(ex.getMessage());
                        }
                    }
                }
            });
        }
    }

    /**
     * Metodo que gestiona todas las comunicaciones cliente-servidor desde el cliente
     */
    public void jugar() {
        String respuesta;
        try {
            System.out.println("Leyendo...");
            respuesta = input.readUTF();
            System.out.println(">>> " + respuesta);
            if (respuesta.startsWith("BIENVENIDO")) {
                System.out.println("Asignando jugador...");
                asignarJugador();
                PaqueteTCP pack = new PaqueteTCP();
                pack.personaje = this.personaje;
                // Enviar el personaje al hilo del servidor para gestionar mas adelante este jugador
                output.writeObject(pack);
                output.flush();
                escuchando = true;
            }
            // Bucle del juego, no se detiene salvo que se ordene SALIR o el juego acabe
            OUTER:
            while (escuchando) {
                System.out.println("...Escuchando...");
                respuesta = input.readUTF();
                System.out.println(">>> " + respuesta);
                // Segun el valor de Respuesta - Se gestiona uno u otro numero de pasos. (Protocolos)
                if (respuesta.startsWith("MENSAJE")) {
                    // Mensaje - Se muestra en el cuadro de chat un texto informativo del servidor
                    if (respuesta.equals("MENSAJE Todos conectados!")) {
                        jugando = true; // Todos conectados, el juego comienza
                        btnPreguntar.setEnabled(true); // Permitir preguntar
                        btnSalir.setVisible(true); // Permitir salir
                    }
                    txtAreaChat.append("INFO: " + respuesta.substring(8) + "\n");
                    System.out.println("INFO: " + respuesta.substring(8));
                } else if (respuesta.startsWith("ADIVINAR")) {
                    // Adivinar - Analizar el nombre y segun si corresponde o no a nuestro personaje, actuar.
                    // Informar de que enviamos el resultado con el comando RESULTADO
                    // Devolver la respuesta (boolean) al servidor para que informe al otro jugador.
                    // Mientras tanto, mostrar la informacion a nuestro cliente.
                    String intento = respuesta.split("ADIVINAR->")[1];
                    System.out.println("Intentan Adivinar");
                    System.out.println(intento + " == " + personaje.getNombre());
                    output.writeUTF("RESULTADO");
                    if (intento.equals(personaje.getNombre())) {
                        output.writeBoolean(true);
                        output.flush();
                        JOptionPane.showMessageDialog(panelPersonas, "Oh no! Has perdido!\nTu rival ha adivinado tu personaje...",
                                "Game Over", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        output.writeBoolean(false);
                        output.flush();
                        JOptionPane.showMessageDialog(panelPersonas, "Hurra! Has ganado!\nTu rival ha fallado adivinando tu personaje...",
                                "Victoria", JOptionPane.PLAIN_MESSAGE);
                    }
                    break;
                } else {
                    String chat;
                    switch (respuesta) {
                        case "CONTESTAR":
                            // Constestar a la pregunta de otro jugador
                            String pregunta = input.readUTF();
                            String opcion = input.readUTF();
                            System.out.println(">>> " + pregunta + " -> " + opcion);
                            // Responder Si o No
                            output.writeUTF("RESPONDER");
                            switch (pregunta) {
                                case Constantes.SEXO:
                                    // Ejemplo: ¿Es chico? - Consultar si el sexo es igual a chico (opcion)
                                    output.writeBoolean(personaje.getSexo().equalsIgnoreCase(opcion));
                                    break;
                                case Constantes.PELO:
                                    output.writeBoolean(personaje.getPelo().equalsIgnoreCase(opcion));
                                    break;
                                case Constantes.OJOS:
                                    output.writeBoolean(personaje.getOjos().equalsIgnoreCase(opcion));
                                    break;
                                case Constantes.VELLO_FACIAL:
                                    output.writeBoolean(personaje.getVello().equalsIgnoreCase(opcion));
                                    break;
                                case Constantes.CARACTERISTICAS:
                                case Constantes.ACCESORIOS:
                                    // Los accesorios y caracteristicas se diferencian en que son opcionales
                                    // si tiene gafas tendra el rasgo gafas con valor true, sino pues null.
                                    Object rasgo = personaje.getRasgo(opcion);
                                    boolean aux = rasgo != null; // Null --> False. No tiene gafas
                                    output.writeBoolean(aux);
                                    break;
                                default:
                                    System.out.println("Pregunta Sin Respuesta");
                                    break;
                            }
                            output.flush();
                            System.out.println("Respuesta enviada");
                            break;
                        case "DESCARTAR":
                            // Leemos la respuesta a la pregunta. Nos llega Si/No.
                            // Enviamos la respuesta al Hilo local que la gestiona.
                            // Dicho hilo ira descartando los personajes.
                            boolean boo = input.readBoolean();
                            System.out.println("Respuesta recibida! ");
                            HiloMultijugador hm = new HiloMultijugador(this, panelControles, boo);
                            hm.start();
                            System.out.println("Descartado.");
                            break;
                        case "CHAT":
                            // Recibir mensajes de chat
                            chat = input.readUTF();
                            System.out.println("Te escriben... " + chat);
                            txtAreaChat.append("CHAT: " + chat + "\n");
                            break;
                        case "VICTORIA":
                            System.out.println("Victoria!");
                            JOptionPane.showMessageDialog(panelPersonas, "Correcto! Era " + personajeRival.getNombre(),
                                    "Enhorabuena!", JOptionPane.PLAIN_MESSAGE, personajeRival.getImagen());
                            break OUTER;
                        case "DERROTA": {
                            System.out.println("** Game Over **");
                            // Recibir personaje que resultaba correcto
                            Object obj = input.readObject();
                            PaqueteTCP pack = (PaqueteTCP) obj;
                            personajeRival = pack.personaje;
                            JOptionPane.showMessageDialog(panelPersonas, "No!, lo siento...Era " + personajeRival.getNombre(),
                                    "Que pena...", JOptionPane.PLAIN_MESSAGE, personajeRival.getImagen());
                            break OUTER;
                        }
                        case "SALIR":
                            break OUTER;
                    }
                }
            }
            output.writeUTF("SALIR");
            output.flush();
        } catch (IOException | ClassNotFoundException ex) {
        } finally {
            try {
                Thread.sleep(25); // Dar tiempo a que el otro jugador reciba los ultimos mensajes y cerrar
                output.close();
                if (!socket.isClosed()) {
                    socket.close();
                }
                dispose();
            } catch (IOException | InterruptedException ex) {
            }
        }
    }

}
