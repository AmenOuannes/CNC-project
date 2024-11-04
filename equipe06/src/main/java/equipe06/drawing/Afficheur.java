package equipe06.drawing;

import equipe06.Domaine.*;

import java.awt.Color;
import java.awt.Graphics;

public class Afficheur {
    private Controleur controleur;

    public Afficheur(Controleur controleur) {
        this.controleur = controleur;
    }

    public void DessinerPanneau(Graphics g, double scale, int hauteurTable) {
        Repere repere = controleur.getRepere();

       /* // Panneau principal de la table CNC (3 m x 1,5 m)
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
        g.fillRect(xOffset, yOffset, largeurPanneauPixels, hauteurPanneauPixels);*/
           // Dessiner le panneau au-dessus de la table CNC en marron clair, positionné en bas à gauche
        g.setColor(new Color(205, 133, 63)); // Couleur marron clair pour le panneau
        PanneauDTO panneau = controleur.getPanneau();
        
        int panneauX = repere.convertirEnPixels(panneau.getLargeur()*scale); // Positionner le panneau à gauche (même x que la table CNC)
        int panneauY = repere.convertirEnPixels(panneau.getLongueur()*scale); // Positionner en bas (table hauteur - panneau hauteur)
        System.out.printf("panneauX: %d\n", panneauX);
        g.fillRect(50, 50 + hauteurTable-panneauY, panneauX, panneauY); // Dessiner le panneau

    // Dessiner une bordure noire autour du panneau
        g.setColor(Color.BLACK); // Couleur pour la bordure
        g.drawRect(50, 50 + hauteurTable-panneauY, panneauX, panneauY);
    }
    public void dessinerCoupe(Graphics g, int x, float scale, int hauteurTable){
           if (x != -1) {
        Repere repere = controleur.getRepere();
        float x_mm = x/scale;
        x_mm = repere.convertirEnMm(x_mm);  
        
        System.out.println("Distance calculée (x_mm) : " + x_mm); // verification console on peut l'enlever
        // Transmettre la distance au contrôleur pour affichage dans MainWindow
        controleur.mettreAJourDistanceX(x_mm);
        
        
        controleur.creerCoupeAxiale(x_mm,false);
        if(!controleur.getCoupes().isEmpty())
        {g.setColor(Color.RED); // Set color for the line
            int ligneY1 = 50 + hauteurTable; // Starting point of the line
            
            
            g.drawLine(x, ligneY1, x, ligneY1-repere.convertirEnPixels(controleur.getPanneau().getLongueur()*scale)); // Draw the vertical line
        }
    }
    }
} 