package equipe06.gui;

import equipe06.Domaine.Repere;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author ziedd
 * Classe PanneauVue modifiée pour dessiner la table CNC et le panneau.
 */
public class PanneauVue extends JPanel {
    
    private int largeurPixelsTable; // Dimensions en pixels de la table CNC
    private int hauteurPixelsTable;
    private int largeurPixelsPanneau; // Dimensions en pixels du panneau au-dessus de la table CNC
    private int hauteurPixelsPanneau;
    
    private Repere repere;

    public PanneauVue() {
        // Supprimer le titre du panneau de visualisation
        // this.setBorder(BorderFactory.createTitledBorder("Panneau de Visualisation"));
        
        // Optionnel : ajouter une bordure simple si besoin
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.repere = new Repere();

        // Conversion des dimensions de la table CNC (3m x 1.5m)
        this.largeurPixelsTable = repere.convertirEnPixels(3000); // 3 mètres en mm
        this.hauteurPixelsTable = repere.convertirEnPixels(1500); // 1.5 mètres en mm

        // Conversion des dimensions du panneau au-dessus (0.9144m x 1.2192m)
        this.largeurPixelsPanneau = repere.convertirEnPixels(1219.2); // 1.2192 mètre en mm
        this.hauteurPixelsPanneau = repere.convertirEnPixels(914.4);  // 0.9144 mètre en mm
    }

    @Override
  
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Dessiner la table CNC en marron clair
    g.setColor(new Color(205, 133, 63)); // Couleur marron clair
    g.fillRect(50, 50, largeurPixelsTable, hauteurPixelsTable); // Position (50, 50) pour voir les marges

    // Dessiner le panneau au-dessus de la table CNC en gris clair
    g.setColor(Color.LIGHT_GRAY);
    int panneauX = (largeurPixelsTable - largeurPixelsPanneau) / 2; // Centrer horizontalement
    int panneauY = (hauteurPixelsTable - hauteurPixelsPanneau) / 2; // Centrer verticalement
    g.fillRect(panneauX, panneauY, largeurPixelsPanneau, hauteurPixelsPanneau);

    // Dessiner des lignes de cadre autour pour visualiser les dimensions exactes
    g.setColor(Color.BLACK);
    g.drawRect(50, 50, largeurPixelsTable, hauteurPixelsTable); // Cadre de la table CNC
    g.drawRect(panneauX, panneauY, largeurPixelsPanneau, hauteurPixelsPanneau); // Cadre du panneau
}

}
