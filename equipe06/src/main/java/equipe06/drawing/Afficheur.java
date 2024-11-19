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
    //TODO hedi dessiner Coupe Axe
    public void dessinerCoupe(Graphics g, int x, int y, int hauteurTable ){
           if (x != -1) {
        Repere repere = controleur.getRepere();
        float x_mm = x;
        x_mm = repere.convertirEnMmDepuisPixels(x_mm);  
        float y_mm = y;
        y_mm = repere.convertirEnMmDepuisPixels(y_mm);
        controleur.CreerCoupe((float) x_mm, (float) y_mm, true); // vertical est true
        // Transmettre la distance au contrôleur pour affichage dans MainWindow
        if(!controleur.getCoupes().isEmpty())
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2f)); //for now
            g2d.setColor(Color.BLACK); // Set color for the line
            controleur.mettreAJourDistanceX(x_mm);
            int ligneY1 = hauteurTable; // Starting point of the line
            g2d.drawLine(x, ligneY1, x, ligneY1 - repere.convertirEnPixelsDepuisMm(controleur.getPanneau().getLargeur())); // Draw the vertical line
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
    
    //TODO zied Change en rect
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
    }
    }
    //TODO dessiner un L : Katia
    public void dessinerL (Graphics g, int x1, int y1, int x2 , int y2) {
        if (x1 != -1|| y1 == -1 || x2 == -1 || y2 == -1) {
            System.out.println("Cordonées pas valides pour dessiner");
            return;
        }
        Repere repere = controleur.getRepere();
        int x1px = repere.convertirEnPixelsDepuisMm(x1);
        int x2px = repere.convertirEnPixelsDepuisMm(x2);
        int y1px = repere.convertirEnPixelsDepuisMm(y1);
        int y2px = repere.convertirEnPixelsDepuisMm(y2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2f));
        g2d.setColor(Color.BLACK);
        // dessiner le trait vertical
        g2d.drawLine(x1px, y1px, x2px, y1px);
        // dessiner trai horizontal
        g2d.drawLine(x2px, y2px, x2px, y1px);
    }
    //TODO Bordure Zied
    
    public void dessinerBordure(Graphics g, float bordureX, float bordureY, int hauteurTable) {
        if (bordureX != -1) {
        Repere repere = controleur.getRepere();
        int bordureXPx = repere.convertirEnPixelsDepuisMm(bordureX);
        int bordureYPx = repere.convertirEnPixelsDepuisMm(bordureY);
        int longueurOriginalePx = repere.convertirEnPixelsDepuisMm(controleur.getPanneau().getLongueur());
        int largeurOriginalePx = repere.convertirEnPixelsDepuisMm(controleur.getPanneau().getLargeur());
        int xOrigine = (longueurOriginalePx - bordureXPx) / 2;
        int yOrigine = hauteurTable - largeurOriginalePx + (largeurOriginalePx - bordureYPx) / 2;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRect(xOrigine, yOrigine, bordureXPx, bordureYPx);
        
    }

    }

    
    
    
} 