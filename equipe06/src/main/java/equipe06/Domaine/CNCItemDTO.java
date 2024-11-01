// @author amen
package equipe06.Domaine;

import java.util.Vector;

public class CNCItemDTO {
    private Vector<Outil> outils;
    private Outil outil;
    private Vector<Coupe> coupes;
    private Panneau panneau;
    public CNCItemDTO(CNC c) {
        this.coupes = c.getCoupes();
        this.panneau = c.getPanneau();
        this.outils = c.getOutils();
        this.outil =c.getOutil_courant();
    }
}
