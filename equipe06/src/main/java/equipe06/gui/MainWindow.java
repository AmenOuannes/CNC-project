/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package equipe06.gui;
import javax.swing.*;
import java.awt.*;
import equipe06.gui.PanneauVue;
import equipe06.Domaine.Controleur;
import equipe06.Domaine.CoupeDTO;
/**
 *
 * @author ziedd
 */
public class MainWindow extends javax.swing.JFrame {
    public Controleur controleur;
    private PanneauVue panneauVue;
    private boolean messageAffiche = false;
    private static double SCALE_FACTOR = 0.25; // Réduit la taille à 25%

    /**
     * Creates new form MainWindow
     */
   public MainWindow() {
        initComponents();

        // Initialiser `PanneauVue` et l'ajouter à l'endroit de `PanneauVisualisation`
        panneauVue = new PanneauVue(this);
  
        // Obtenir l'instance de Controleur et établir la communication
        controleur = Controleur.getInstance();
        controleur.setMainWindow(this); // lien etablie ici pour la mise a jour distance x
        // Configurer `PanneauVisualisation`
        PanneauVisualisation.setLayout(new BorderLayout());

        // Ajouter `panneauVue` dans `PanneauVisualisation`
        PanneauVisualisation.add(panneauVue, BorderLayout.CENTER);
        // Ajout d'un `MouseListener` à `MainWindow` pour détecter les clics en dehors du panneau
        // si L'utilisateur clic sur le bouton cree coupe apres ca il ne clic pas sur le panneau un settext s'affiche
        // principe : détecter les clics en dehors du panneau quand la bool panneauvue.peutCreerCoupe = true
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (panneauVue.isAttenteClicPourCoupe() && !evt.getSource().equals(panneauVue)) {//probleme condition
                    message.setText("Veuillez cliquer sur le panneau pour créer la coupe.");
                }
                else {
        
            // Si la condition n'est pas remplie, effacer le message précédent
            message.setText("");
        }}
        });
        
        
        
        
        
        
        
        
        // Ajuster la fenêtre à la taille totale des composants
        pack();
    }
 /*   private void drawingPanelMouseClicked(java.awt.event.MouseEvent evt){
            java.awt.Point mousePoint = evt.getPoint();
            int Axe = mousePoint.x;
            //panneauVue.DessinerCoupe(Axe, g);
            
        }*/

   
    public JTextField getDistanceX() {
        return DistanceX;
    }
    
    // permet de pour mettre à jour le champ distancex avec la valeur de x 
    public void afficherValeurDistanceX(float x) {
        if(!controleur.getCoupes().isEmpty())
        {System.out.println("Appel de afficherValeurDistanceX avec x : " + x); // Vérification console
        DistanceX.setText(String.format("%.2f", x - 130)); // Afficher la valeur avec deux décimales
        }
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
        DistanceX = new javax.swing.JTextField();
        message = new javax.swing.JLabel();

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

        DefCoupe.setText("Créer une Coupe");
        DefCoupe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DefCoupeActionPerformed(evt);
            }
        });

        ModCoupe.setText("Modifier une Coupe");

        SuppCoupe.setText("Supprimer une Coupe");
        SuppCoupe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuppCoupeActionPerformed(evt);
            }
        });

        jButton1.setText("Quitter ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        DistanceX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DistanceXActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanneauContrôleLayout = new javax.swing.GroupLayout(PanneauContrôle);
        PanneauContrôle.setLayout(PanneauContrôleLayout);
        PanneauContrôleLayout.setHorizontalGroup(
            PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanneauContrôleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DefCoupe, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SuppCoupe)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addComponent(ModCoupe, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DistanceX, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 6, Short.MAX_VALUE))))
        );
        PanneauContrôleLayout.setVerticalGroup(
            PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanneauContrôleLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(DefCoupe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ModCoupe)
                    .addComponent(DistanceX, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SuppCoupe)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(114, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PanneauContrôle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(PanneauVisualisation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(184, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(PanneauContrôle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(57, Short.MAX_VALUE)
                        .addComponent(PanneauVisualisation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)))
                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DefCoupeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DefCoupeActionPerformed
        // TODO add your handling code here:
        panneauVue.activerCreationCoupe();  // Active la possibilité de créer une coupe
    }//GEN-LAST:event_DefCoupeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void DistanceXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DistanceXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DistanceXActionPerformed

    private void SuppCoupeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuppCoupeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SuppCoupeActionPerformed

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
    private javax.swing.JTextField DistanceX;
    private javax.swing.JButton ModCoupe;
    private javax.swing.JPanel PanneauContrôle;
    private javax.swing.JPanel PanneauVisualisation;
    private javax.swing.JButton SuppCoupe;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel message;
    // End of variables declaration//GEN-END:variables
}
