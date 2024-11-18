package equipe06.drawing;

import equipe06.Domaine.*;

import java.awt.*;

public class Afficheur {
    private Controleur controleur;

    public Afficheur(Controleur controleur) {
        this.controleur = controleur;
    }

    public void DessinerPanneau(Graphics g, int hauteurTable) {
        Repere repere = controleur.getRepere();
        // Dessiner le panneau au-dessus de la table CNC en marron clair, positionné en bas à gauche
        g.setColor(new Color(205, 133, 63)); // Couleur marron clair pour le panneau

        PanneauDTO panneau = controleur.getPanneau();        
        int panneauX = repere.convertirEnPixelsDepuisMm(panneau.getLongueur()); // Positionner le panneau à gauche (même x que la table CNC)
        int panneauY = repere.convertirEnPixelsDepuisMm(panneau.getLargeur()); // Positionner en bas (table hauteur - panneau hauteur)

        g.fillRect(0, hauteurTable-panneauY, panneauX, panneauY); // Dessiner le panneau

        // Dessiner une bordure noire autour du panneau
        g.setColor(Color.BLACK); // Couleur pour la bordure
        g.drawRect(0, hauteurTable-panneauY, panneauX, panneauY);
    }

    //rendre cette fonction capable à dessiner toutes les coupes possible
    //accepte l'outil courant comme critere s'epaisseur de ligne
    public void dessinerCoupe(Graphics g, int x, int y, int hauteurTable ){
           if (x != -1) {
        Repere repere = controleur.getRepere();
        float x_mm = x;
        x_mm = repere.convertirEnMmDepuisPixels(x_mm);  
        float y_mm = y;
        y_mm = repere.convertirEnMmDepuisPixels(y_mm);
        controleur.CreerCoupe((float) x_mm, (float) y_mm, true);
        // Transmettre la distance au contrôleur pour affichage dans MainWindow
        if(!controleur.getCoupes().isEmpty())
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2f)); //for now
            g2d.setColor(Color.BLACK); // Set color for the line
            controleur.mettreAJourDistanceX(x_mm);
            int ligneY1 = hauteurTable; // Starting point of the line
            g2d.drawLine(x, ligneY1, x, ligneY1 - repere.convertirEnPixelsDepuisMm(controleur.getPanneau().getLongueur())); // Draw the vertical line
        }
    }
    }
    //rendre la fonction de modification declenche le Dessiner coupe au lieu de celle là
    public void dessinerCoupeModifie(Graphics g, int hauteurTable){
        CoupeDTO coupe = controleur.getCoupes().get(0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2f));
        float axe = coupe.getAxeDTO();
        Repere repere = controleur.getRepere();
        int x = (int) ( repere.convertirEnPixelsDepuisMm(axe));
        System.out.printf(String.valueOf(x));
        g2d.setColor(Color.BLACK); // Set color for the line

        int ligneY1 = hauteurTable; // Starting point of the line
        g2d.drawLine(x, ligneY1, x, ligneY1-repere.convertirEnPixelsDepuisMm(controleur.getPanneau().getLongueur()));

    }
    
    
    public void dessinerRectangleAVdeuxpoints (Graphics g, int x1px, int y1px, int x2px, int y2px) {
        if (x1px != -1) {
        Repere repere = controleur.getRepere();
        float x1 = repere.convertirEnMmDepuisPixels(x1px);
        float y1 = repere.convertirEnMmDepuisPixels(y1px);
        float x2 = repere.convertirEnMmDepuisPixels(x2px);
        float y2 = repere.convertirEnMmDepuisPixels(y2px);
        
        //controleur.CreerCoupe();
        
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2f));
        g2d.setColor(Color.BLACK);
        
        // Calculer la position (coin supérieur gauche) et les dimensions du rectangle
        int x = Math.min(x1px, x2px);
        int y = Math.min(y1px, y2px);
        int largeur = Math.abs(x2px - x1px);
        int hauteur = Math.abs(y2px - y1px);
           
        g2d.drawRect(x, y, largeur, hauteur);

        
        
        
        
    
    
    
    
    }}
    
    
    
} 