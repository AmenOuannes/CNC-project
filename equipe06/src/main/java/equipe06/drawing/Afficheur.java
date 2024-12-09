package equipe06.drawing;

import equipe06.Domaine.*;

import java.awt.*;

public class Afficheur {
    private Controleur controleur;

    public Afficheur(Controleur controleur) {
        this.controleur = controleur;
    }

    public void DessinerPanneau(Graphics g, int hauteurTable) {
        Repere repere = Repere.getInstance();
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
    public void dessinerCoupeAxiale(Graphics g,CoupeDTO coupe, int hauteurTable, int largeurTable, boolean xy){

          // if (x != -1) {
        Repere repere = Repere.getInstance();
        int axe_pixel = repere.convertirEnPixelsDepuisMm(coupe.getAxeDTO());
        //float x_mm = x;
        if(xy) {

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(coupe.getOutilDTO()));
            // Partie Largeur outil :

            g2d.setColor(Color.BLACK);
            if(coupe.invalide) g2d.setColor(Color.RED);
            //controleur.mettreAJourDistanceX(x_mm);
            int ligneY1 = hauteurTable; // Starting point of the line
            g2d.drawLine(axe_pixel, ligneY1, axe_pixel, ligneY1 - repere.convertirEnPixelsDepuisMm(controleur.getPanneau().getLargeur())); // Draw the vertical line
        }
        else{     
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(coupe.getOutilDTO())); //for now
            g2d.setColor(Color.BLACK);
            if(coupe.invalide) g2d.setColor(Color.RED);
            //controleur.mettreAJourDistanceX(x_mm);
            int ligneX1 = largeurTable; // Starting point of the line
            g2d.drawLine(0, axe_pixel, repere.convertirEnPixelsDepuisMm(controleur.getPanneau().getLongueur()), axe_pixel); // Draw the vertical line
        }

    }


    //rendre la fonction de modification declenche le Dessiner coupe au lieu de celle là
   /* public void dessinerCoupeModifie(Graphics g, int hauteurTable){
        CoupeDTO coupe = controleur.getCoupes().get(0);
        Graphics2D g2d = (Graphics2D) g;
        
        
        float epaisseur = controleur.getEpaisseurActuelle();
        g2d.setStroke(new BasicStroke(epaisseur)); // Appliquer l'épaisseur
        
        
        float axe = coupe.getAxeDTO();
        Repere repere = Repere.getInstance();
        int x = (int) ( repere.convertirEnPixelsDepuisMm(axe));
        System.out.printf(String.valueOf(x));
        g2d.setColor(Color.BLACK); // Set color for the line

        int ligneY1 = hauteurTable; // Starting point of the line
        g2d.drawLine(x, ligneY1, x, ligneY1-repere.convertirEnPixelsDepuisMm(controleur.getPanneau().getLongueur()));

    }*/
    
    //TODO zied Change en rect
    public void dessinerRectangleAVdeuxpoints (Graphics g, Point origine, Point destination, CoupeDTO coupe) {
        if (origine != null && destination != null) {
            // Extraire les coordonnées des points
            int x1px = origine.x;
            int y1px = origine.y;
            int x2px = destination.x;
            int y2px = destination.y; 
            Graphics2D g2d = (Graphics2D) g;

            g2d.setStroke(new BasicStroke(coupe.getOutilDTO())); // Appliquer l'épaisseur
            if(controleur.inPanneau(Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(x1px)), Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(y1px)))
                    && controleur.inPanneau(Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(x2px)),Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(y2px))))
                g2d.setColor(Color.BLACK);
            else  g2d.setColor(Color.RED);
            if(coupe.invalide) g2d.setColor(Color.RED);
            // Calculer la position (coin supérieur gauche) et les dimensions du rectangle
            int x = Math.min(x1px, x2px);
            int y = Math.min(y1px, y2px);
            int largeur = Math.abs(x2px - x1px);
            int hauteur = Math.abs(y2px - y1px);
            g2d.drawRect(x, y, largeur, hauteur);
    } 
    }
    
    
    public void dessinerZoneInterdite (Graphics g, Point origine, Point destination) {
        if (origine != null && destination != null) {
            // Extraire les coordonnées des points
            int x1px = origine.x;
            int y1px = origine.y;
            int x2px = destination.x;
            int y2px = destination.y; 
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            if(controleur.inPanneau(Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(x1px)), Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(y1px)))
                    && controleur.inPanneau(Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(x2px)),Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(y2px))))
                g2d.setColor(Color.BLACK);
            else  g2d.setColor(Color.RED);
            // Calculer la position (coin supérieur gauche) et les dimensions du rectangle
            int x = Math.min(x1px, x2px);
            int y = Math.min(y1px, y2px);
            int largeur = Math.abs(x2px - x1px);
            int hauteur = Math.abs(y2px - y1px);
            g2d.drawRect(x, y, largeur, hauteur);
            for (int i = x; i <= x + largeur; i += 5) {
                g2d.drawLine(i, y, i, y + hauteur); // Vertical line from top to bottom of the rectangle
            }
        } 
    }
        
        
        
    //TODO dessiner un L : Katia
    public void dessinerL (Graphics g, Point origine, Point destination, CoupeDTO coupe) {
        if (origine != null && destination != null) {
            // Extraire les coordonnées des point
        int x1 = origine.x;
        int y1= origine.y;
        int x2 = destination.x;
        int y2 = destination.y; 
        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(coupe.getOutilDTO())); // Appliquer l'épaisseur
            if(controleur.inPanneau(Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(x1)), Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(y1)))
                    && controleur.inPanneau(Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(x2)),Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(y2))))
                g2d.setColor(Color.BLACK);
            else  g2d.setColor(Color.RED);
            if(coupe.invalide) g2d.setColor(Color.RED);
        // dessiner le trait vertical
        //J 'ai essayé avec origine.x origine.y desti.x origi.y et tout ca marche pas aussi
            g2d.drawLine(x2, y2, x1 ,y2);
            // dessiner trai horizontal
            g2d.drawLine(x2, y2, x2, y1);
       }
    }

    //TODO Bordure Zied
    
    public void dessinerBordure(Graphics g, float bordureX, float bordureY, int hauteurTable, CoupeDTO coupe) {
        if (bordureX != -1) {
        Repere repere = Repere.getInstance();
        int bordureXPx = repere.convertirEnPixelsDepuisMm(bordureX);
        int bordureYPx = repere.convertirEnPixelsDepuisMm(bordureY);
        int longueurOriginalePx = repere.convertirEnPixelsDepuisMm(controleur.getPanneau().getLongueur());
        int largeurOriginalePx = repere.convertirEnPixelsDepuisMm(controleur.getPanneau().getLargeur());
        int xOrigine = (longueurOriginalePx - bordureXPx) / 2;
        int yOrigine = hauteurTable - largeurOriginalePx + (largeurOriginalePx - bordureYPx) / 2;
        if (xOrigine >= 0 && yOrigine >= 0 && bordureXPx <= longueurOriginalePx && bordureYPx <= largeurOriginalePx){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(coupe.getOutilDTO()));

            g2d.drawRect(xOrigine, yOrigine, bordureXPx, bordureYPx);
        }   
        else{
            System.out.println("out");
        }
    }
} 
    
    public void dessinerGrille(Graphics g, int hauteurPixelsTable, int largeurPixelsTable, float cote ) {
        g.setColor(Color.BLACK);
        Repere repere = Repere.getInstance();
        int intervallePixelsX = repere.convertirEnPixelsDepuisMm(cote);

        // Dessiner les lignes verticales (X)
        for (int i = 0; i <= largeurPixelsTable / intervallePixelsX; i++) {
            int x = i * intervallePixelsX;
            g.drawLine(x, 0, x, hauteurPixelsTable);
        }

        // Dessiner les lignes horizontales (Y)
        for (int i = 0; i <= hauteurPixelsTable / intervallePixelsX; i++) {
            int y = hauteurPixelsTable - i * intervallePixelsX;
            g.drawLine(0, y, largeurPixelsTable, y);
        }
    }
}