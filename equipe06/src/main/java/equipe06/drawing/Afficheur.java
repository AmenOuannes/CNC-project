//@author amen
package equipe06.drawing;

import equipe06.Domaine.*;

import java.awt.Graphics;
import java.util.Vector;

public class Afficheur {
    private Controleur c;
    public Afficheur(Controleur c) {}
    public void DessinerPanneau(Graphics g, Controleur c) {
        PanneauDTO panneau = c.getPanneau();
        Repere repere = c.getRepere();
        int largeurPixels = repere.convertirEnPixels(panneau.getLargeur() * c.scaleFactor);
        int hauteurPixels = repere.convertirEnPixels(panneau.getLongueur() * c.scaleFactor);
        //do the draw

    }
    public void DessinerCoupes(Graphics g, Controleur c) {
        Vector<CoupeDTO> coupes = c.getCoupes();
        //do the draw

    }
}

