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
 * ajouter coupe - axiale 
 * 
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
    
    
}
