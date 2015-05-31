package adivinaquien;

// <editor-fold defaultstate="collapsed">
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
@SuppressWarnings("serial")
public class JuegoGUI extends javax.swing.JFrame {

    private int intentos; // Nº de preguntas necesitadas
    private Persona cpu; // Personaje asignado a la maquina
    private ImageIcon imagenPersona;
    private final ArrayList<JLabel> labelsPersonas;
    private ArrayList<Persona> personas;

    /**
     * Constructor de JuegoGUI
     */
    public JuegoGUI() {
        initComponents();
        intentos = 0;
        personas = new ArrayList<>(1);
        labelsPersonas = new ArrayList<>(1);
        personas = ListaPersonajes.cargarPersonajes(personas);
        asignarCPU();
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
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // <editor-fold defaultstate="collapsed">
                    // Proceso para intentar adivinar el personaje
                    // 1º Comprobar que no sea un interrogante -- Personaje descartado
                    if (personas.get(Integer.valueOf(label.getName())).getImagen().getImage() != new ImageIcon(Constantes.INTERROGANTE_NEGRO_URL).getImage()) {
                        // Comparar nombre del personaje
                        if (label.getText().equalsIgnoreCase(cpu.getNombre())) {
                            if (intentos == 0) {
                                JOptionPane.showMessageDialog(panelPersonas,
                                        "Wow! No has necesitado ninguna pista!!!"
                                        + "\nCorrecto!!! Era " + cpu.getNombre(),
                                        "Magia!",
                                        JOptionPane.PLAIN_MESSAGE,
                                        cpu.getImagen());
                            } else {
                                JOptionPane.showMessageDialog(panelPersonas,
                                        "Correcto! Era " + cpu.getNombre()
                                        + "\n(" + intentos + " pistas usadas.)",
                                        "Enhorabuena!",
                                        JOptionPane.PLAIN_MESSAGE,
                                        cpu.getImagen());
                            }
                        } else {
                            if (intentos == 0) {
                                JOptionPane.showMessageDialog(panelPersonas,
                                        "Venga ya...!"
                                        + "\n¿En serio pensabas acertar sin pistas?"
                                        + "\nEra " + cpu.getNombre() + "...",
                                        "Flipao!",
                                        JOptionPane.PLAIN_MESSAGE,
                                        cpu.getImagen());
                            } else {
                                JOptionPane.showMessageDialog(panelPersonas,
                                        "No!, lo siento...Era " + cpu.getNombre()
                                        + "\n(" + intentos + " pistas usadas.)",
                                        "Que pena...",
                                        JOptionPane.PLAIN_MESSAGE,
                                        cpu.getImagen());
                            }
                        }
                        dispose();
                    } // </editor-fold>
                }
            });
        }
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

        setUndecorated(true); // Pantalla Completa
        setExtendedState(MAXIMIZED_BOTH); // Maximizado
        setVisible(true);
        setResizable(false); // Apariencia fija
        comboPreguntas.setSelectedIndex(4); // Seleccionar el index 4 (Sexo) para inicializar las opciones de preguntas
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
     * Metodo que devuelve la categoria sobre la que se quiere preguntar
     *
     * @return Cadena de texto - Ejemplo: SEXO
     */
    public String getPregunta() {
        return comboPreguntas.getSelectedItem().toString();
    }

    /**
     * Metodo que devuelve la opcion seleccionada para preguntar
     *
     * @return Cadena de texto - Ejemplo: MUJER
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

    /**
     * Metodo que asigna un jugador al ordenador
     */
    private void asignarCPU() {
        Random aleatorio = new Random();
        cpu = personas.get(aleatorio.nextInt(personas.size()));
        System.out.println("CPU = " + cpu.getNombre());
    }

    public Persona getCPU() {
        return cpu;
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
        lbl_Pistas = new javax.swing.JLabel();

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

        lbl_Pistas.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        lbl_Pistas.setText("0 Pistas");

        javax.swing.GroupLayout panelControlesLayout = new javax.swing.GroupLayout(panelControles);
        panelControles.setLayout(panelControlesLayout);
        panelControlesLayout.setHorizontalGroup(
            panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelControlesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelControlesLayout.createSequentialGroup()
                        .addComponent(txtChat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResponderChat))
                    .addComponent(jSeparator1)
                    .addGroup(panelControlesLayout.createSequentialGroup()
                        .addComponent(comboPreguntas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPreguntar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelControlesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(panelOpciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(panelControlesLayout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(lbl_Pistas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(lbl_Pistas)
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
                .addComponent(panelPersonas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelControles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelPersonas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                    .addComponent(panelControles, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPreguntarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreguntarActionPerformed
        if (grupoOpciones.getSelection() != null) {
            new HiloPregunta(this, this.panelControles).start();
            intentos++;
            lbl_Pistas.setText(intentos + " Pistas");
        }
    }//GEN-LAST:event_btnPreguntarActionPerformed

    private void btnResponderChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResponderChatActionPerformed
        if (txtChat.getText().length() > 0) {
            txtAreaChat.append("Yo: " + txtChat.getText() + "\n");
        }
        txtChat.setText("");
    }//GEN-LAST:event_btnResponderChatActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
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

    // <editor-fold defaultstate="collapsed">
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JuegoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JuegoGUI().setVisible(true);
            }
        });
    }
    // </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPreguntar;
    private javax.swing.JButton btnResponderChat;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox comboPreguntas;
    private javax.swing.ButtonGroup grupoOpciones;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl_Pistas;
    private javax.swing.JPanel panelControles;
    private javax.swing.JPanel panelOpciones;
    private javax.swing.JPanel panelPersonas;
    private javax.swing.JTextArea txtAreaChat;
    private javax.swing.JTextField txtChat;
    // End of variables declaration//GEN-END:variables
}
