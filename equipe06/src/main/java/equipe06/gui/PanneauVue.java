package equipe06.gui;

import equipe06.Domaine.Repere;
import javax.swing.*;
import java.awt.*;

/**
 * Classe PanneauVue modifiée pour dessiner la table CNC et le panneau avec un facteur d'échelle.
 */
public class PanneauVue extends JPanel {

    private int largeurPixelsTable; // Dimensions en pixels de la table CNC
    private int hauteurPixelsTable;
    private int largeurPixelsPanneau; // Dimensions en pixels du panneau au-dessus de la table CNC
    private int hauteurPixelsPanneau;

    private Repere repere;

    private static final double SCALE_FACTOR = 0.1; // Facteur d'échelle de 10%

    public PanneauVue() {
        
        this.repere = new Repere();

        // Conversion des dimensions de la table CNC (3m x 1.5m) en appliquant un facteur d'échelle
        this.largeurPixelsTable = (int) (repere.convertirEnPixels(3000) * SCALE_FACTOR); // 3 mètres en mm
        this.hauteurPixelsTable = (int) (repere.convertirEnPixels(1500) * SCALE_FACTOR); // 1.5 mètres en mm

        // Conversion des dimensions du panneau au-dessus (0.9144m x 1.2192m) avec facteur d'échelle
        this.largeurPixelsPanneau = (int) (repere.convertirEnPixels(1219) * SCALE_FACTOR); // 1.2192 mètre en mm
        this.hauteurPixelsPanneau = (int) (repere.convertirEnPixels(914.4) * SCALE_FACTOR);  // 0.9144 mètre en mm

        // Définir la taille préférée du panneau basé sur la table CNC pour s'assurer qu'elle s'ajuste correctement
        this.setPreferredSize(new Dimension(largeurPixelsTable + 100, hauteurPixelsTable + 100));
    }

  @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Dessiner la table CNC en gris clair avec une bordure noire
    g.setColor(Color.LIGHT_GRAY); // Couleur gris clair pour la table CNC
    g.fillRect(50, 50, largeurPixelsTable, hauteurPixelsTable); // Dessiner la table CNC

    g.setColor(Color.BLACK); // Bordure noire
    g.drawRect(50, 50, largeurPixelsTable, hauteurPixelsTable); // Dessiner la bordure noire autour de la table CNC

    // Dessiner le panneau au-dessus de la table CNC en marron clair, positionné en bas à gauche
    g.setColor(new Color(205, 133, 63)); // Couleur marron clair pour le panneau
    int panneauX = 50; // Positionner le panneau à gauche (même x que la table CNC)
    int panneauY = 50 + hauteurPixelsTable - hauteurPixelsPanneau; // Positionner en bas (table hauteur - panneau hauteur)
    g.fillRect(panneauX, panneauY, largeurPixelsPanneau, hauteurPixelsPanneau); // Dessiner le panneau

    // Dessiner une bordure noire autour du panneau
    g.setColor(Color.BLACK); // Couleur pour la bordure
    g.drawRect(panneauX, panneauY, largeurPixelsPanneau, hauteurPixelsPanneau); // Dessiner la bordure noire autour du panneau
}

}
