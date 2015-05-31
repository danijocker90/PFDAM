package adivinaquien;

import adivinaquien.multijugador.MultijugadorGUI;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Menu principal del juego. Formulario Inicial
 *
 * @author Daniel
 */
@SuppressWarnings("serial")
public class MenuGUI extends javax.swing.JFrame {

    MultijugadorGUI mp;
    String host;

    /**
     * Creates new form GameGUI
     */
    public MenuGUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        btnJugar = new javax.swing.JButton();
        btnUnirse = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnCrearPartida = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Adivina Quien?");
        setResizable(false);

        btnJugar.setBackground(new java.awt.Color(255, 51, 51));
        btnJugar.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        btnJugar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adivinaquien/imagenes/Play vs CPU.png"))); // NOI18N
        btnJugar.setToolTipText("1 Jugador");
        btnJugar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnJugar.setFocusPainted(false);
        btnJugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJugarActionPerformed(evt);
            }
        });

        btnUnirse.setBackground(new java.awt.Color(255, 204, 0));
        btnUnirse.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        btnUnirse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adivinaquien/imagenes/internet.png"))); // NOI18N
        btnUnirse.setToolTipText("Unirse a partida online");
        btnUnirse.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUnirse.setFocusPainted(false);
        btnUnirse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnirseActionPerformed(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adivinaquien/imagenes/Exit Large.png"))); // NOI18N
        btnSalir.setToolTipText("Salir");
        btnSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir.setFocusPainted(false);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnCrearPartida.setBackground(new java.awt.Color(255, 204, 0));
        btnCrearPartida.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        btnCrearPartida.setText("<html><center>Nueva<br/>Partida</center></html>");
        btnCrearPartida.setToolTipText("Crear una partida Multijugador");
        btnCrearPartida.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCrearPartida.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCrearPartida.setFocusPainted(false);
        btnCrearPartida.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCrearPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearPartidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnJugar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(btnUnirse, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCrearPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        panelPrincipalLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCrearPartida, btnUnirse});

        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnJugar)
                .addGap(18, 18, 18)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCrearPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUnirse, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 98, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelPrincipalLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCrearPartida, btnUnirse});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnJugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJugarActionPerformed
        JuegoGUI game = new JuegoGUI();
    }//GEN-LAST:event_btnJugarActionPerformed

    private void btnCrearPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearPartidaActionPerformed
        host = "localhost";
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mp = new MultijugadorGUI(host);
                    if (mp.isVisible()) {
                        mp.jugar();
                        System.out.println("Desconectado");
                    }
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });
        t.start();
    }//GEN-LAST:event_btnCrearPartidaActionPerformed

    private void btnUnirseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnirseActionPerformed
        host = JOptionPane.showInputDialog(this, "¿A qué servidor desea conectarse?\nHost Servidor:", "Servidor", JOptionPane.PLAIN_MESSAGE);
        final JDialog dialogo = new JDialog(this, "Conexión", true);
        dialogo.add(new JLabel("Conectando con: " + host + "..."));
        dialogo.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialogo.setSize(320, 70);
        dialogo.setLocationRelativeTo(this);
        if (host != null && !host.isEmpty()) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                dialogo.setVisible(true);
                            }
                        });
                        t.start();
                        mp = new MultijugadorGUI(host);
                        if (mp.isVisible()) {
                            dialogo.dispose();
                            MenuGUI.this.setState(ICONIFIED);
                            mp.jugar();
                            System.out.println("Desconectado");
                        }
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                    dialogo.dispose();
                }
            });
            t.start();
        }
    }//GEN-LAST:event_btnUnirseActionPerformed

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
            java.util.logging.Logger.getLogger(MenuGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuGUI().setVisible(true);
            }
        });
    }
    // </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrearPartida;
    private javax.swing.JButton btnJugar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnUnirse;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}
