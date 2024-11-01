//@author amen
package equipe06.drawing;

import equipe06.Domaine.Controleur;
import equipe06.Domaine.Panneau;

import java.awt.Graphics;

public abstract class Afficheur {
    private Controleur c;
    public Afficheur(Controleur c) {}
    public void DessinerPanneau(Graphics g, Controleur c) {}
    public void DessinerCoupes(Graphics g, Controleur c) {}
}
class AfficheurHaut extends Afficheur {
    private Controleur c;
    AfficheurHaut(Controleur c) {
        super(c);
    }
    public void DessinerCoupes(Graphics g, Controleur c) {}

}
