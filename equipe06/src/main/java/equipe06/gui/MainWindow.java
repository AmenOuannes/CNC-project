/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package equipe06.gui;
import javax.swing.*;
import java.awt.*;
import equipe06.gui.PanneauVue;
import equipe06.Domaine.Controleur;
/**
 *
 * @author ziedd
 */
public class MainWindow extends javax.swing.JFrame {
    private Controleur controleur;
    private PanneauVue panneauVue;
    private static double SCALE_FACTOR = 0.25; // Réduit la taille à 25%


    /**
     * Creates new form MainWindow
     */
   public MainWindow() {
    initComponents();
      // Initialiser `PanneauVue` et l'ajouter à l'endroit de `PanneauVisualisation`
      panneauVue = new PanneauVue();
      panneauVue.setPreferredSize(new Dimension(600, 300)); // Définir une taille préférée

    
    // Obtenir l'instance de Controleur et établir la communication
    controleur = Controleur.getInstance();
    controleur.setMainWindow(this); // Etablit la communication entre le contrôleur et MainWindow

    // Configurer `PanneauVisualisation`
    PanneauVisualisation.setLayout(new BorderLayout());
    PanneauVisualisation.setPreferredSize(new Dimension(600, 300)); // Ajuster la taille de PanneauVisualisation

    // Ajouter `panneauVue` dans `PanneauVisualisation`
    PanneauVisualisation.add(panneauVue, BorderLayout.CENTER);

    // Ajuster la fenêtre à la taille totale des composants
    pack();
}


   
     
    
        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanneauVisualisation = new javax.swing.JPanel();
        PanneauContrôle = new javax.swing.JPanel();
        DefCoupe = new javax.swing.JButton();
        ModCoupe = new javax.swing.JButton();
        SuppCoupe = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout PanneauVisualisationLayout = new javax.swing.GroupLayout(PanneauVisualisation);
        PanneauVisualisation.setLayout(PanneauVisualisationLayout);
        PanneauVisualisationLayout.setHorizontalGroup(
            PanneauVisualisationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
        );
        PanneauVisualisationLayout.setVerticalGroup(
            PanneauVisualisationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 304, Short.MAX_VALUE)
        );

        DefCoupe.setText("Définir une Coupe");
        DefCoupe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DefCoupeActionPerformed(evt);
            }
        });

        ModCoupe.setText("Modifier une Coupe");

        SuppCoupe.setText("Supprimer une Coupe");

        jButton1.setText("Quitter ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanneauContrôleLayout = new javax.swing.GroupLayout(PanneauContrôle);
        PanneauContrôle.setLayout(PanneauContrôleLayout);
        PanneauContrôleLayout.setHorizontalGroup(
            PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanneauContrôleLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ModCoupe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SuppCoupe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DefCoupe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );
        PanneauContrôleLayout.setVerticalGroup(
            PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanneauContrôleLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(DefCoupe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ModCoupe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SuppCoupe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(119, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(PanneauContrôle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(PanneauVisualisation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(PanneauContrôle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PanneauVisualisation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DefCoupeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DefCoupeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DefCoupeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DefCoupe;
    private javax.swing.JButton ModCoupe;
    private javax.swing.JPanel PanneauContrôle;
    private javax.swing.JPanel PanneauVisualisation;
    private javax.swing.JButton SuppCoupe;
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
