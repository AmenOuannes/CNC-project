package equipe06.drawing;

import equipe06.Domaine.*;

import java.awt.Color;
import java.awt.Graphics;

public class Afficheur {
    private Controleur controleur;

    public Afficheur(Controleur controleur) {
        this.controleur = controleur;
    }

    public void DessinerPanneau(Graphics g, Controleur controleur) {
        Repere repere = controleur.getRepere();

        // Panneau principal de la table CNC (3 m x 1,5 m)
        int largeurTablePixels = repere.convertirEnPixels(3000); // 3000 mm (3 mètres)
        int hauteurTablePixels = repere.convertirEnPixels(1500); // 1500 mm (1.5 mètres)

        // Panneau prédéfini (0,9144 m x 1,2192 m)
        PanneauDTO panneau = controleur.getPanneau();
      
        
        int largeurPanneauPixels = repere.convertirEnPixels(panneau.getLargeur()*1000); // Convertir en mm
        int hauteurPanneauPixels = repere.convertirEnPixels(panneau.getLongueur()*1000); // Convertir en mm

        // Dessiner le panneau de la table CNC (3 m x 1.5 m)
        g.setColor(new Color(139, 69, 19)); // Couleur marron clair
        g.fillRect(50, 50, largeurTablePixels, hauteurTablePixels);

        // Dessiner le panneau prédéfini par-dessus le panneau CNC
        g.setColor(new Color(210, 180, 140)); // Couleur plus claire
        int xOffset = 50 + (largeurTablePixels - largeurPanneauPixels) / 2; // Centrer sur la largeur
        int yOffset = 50 + (hauteurTablePixels - hauteurPanneauPixels) / 2; // Centrer sur la hauteur
        g.fillRect(xOffset, yOffset, largeurPanneauPixels, hauteurPanneauPixels);
    }
}
