/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.gui;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author ziedd
 */
public class PanneauVue extends JPanel {
    
    private int largeurPixels;
    private int hauteurPixels;
    
    public PanneauVue() {
        this.setBorder(BorderFactory.createTitledBorder("Panneau de Visualisation"));
    }
    
    public void setDimensions(int largeur, int hauteur) {
        this.largeurPixels = largeur;
        this.hauteurPixels = hauteur;
        this.setPreferredSize(new Dimension(largeurPixels, hauteurPixels));
        revalidate(); // Recalculer la taille du panneau dans l'interface
        repaint();
    }
    
    /*protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Dessiner le contour du panneau
        g2d.setColor(Color.BLACK);
        g2d.drawRect(10, 10, largeurPixels - 20, hauteurPixels - 20);
        }*/
    
}
