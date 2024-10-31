package equipe06.Domaine.Utils;
//import equipe06.Domaine.Utils.Point;
import java.awt.Point;
import equipe06.Domaine.Outil;
/**
 *
 * @author hedi, katia
 */


public class ElementCoupe {

    // Attributs de la classe
    private Point pointOrigine;
    private Point pointDestination;
    private float profondeur;
    private float marge;
    private float axe;
    private boolean composante;
    private float bordureX;
    private float bordureY;
    private String typeCoupe;
    private Outil outil;

    // Constructeur par défaut
    public ElementCoupe() {
    }

    // Constructeur avec paramètres pour initialiser tous les attributs
    public ElementCoupe(Point pointOrigine, Point pointDestination, float profondeur, float marge, float axe,
                        boolean composante, float bordureX, float bordureY, String typeCoupe, Outil outil) {
        this.pointOrigine = pointOrigine;
        this.pointDestination = pointDestination;
        this.profondeur = profondeur;
        this.marge = marge;
        this.axe = axe;
        this.composante = composante;
        this.bordureX = bordureX;
        this.bordureY = bordureY;
        this.typeCoupe = typeCoupe;
        this.outil = outil;
    }

    // Getters et setters pour chaque attribut
    public Point getPointOrigine() {
        return pointOrigine;
    }

    public void setPointOrigine(Point pointOrigine) {
        this.pointOrigine = pointOrigine;
    }

    public Point getPointDestination() {
        return pointDestination;
    }

    public void setPointDestination(Point pointDestination) {
        this.pointDestination = pointDestination;
    }

    public float getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(float profondeur) {
        this.profondeur = profondeur;
    }

    public float getMarge() {
        return marge;
    }

    public void setMarge(float marge) {
        this.marge = marge;
    }

    public float getAxe() {
        return axe;
    }

    public void setAxe(float axe) {
        this.axe = axe;
    }

    public boolean getComposante() {
        return composante;
    }

    public void setComposante(boolean composante) {
        this.composante = composante;
    }

    public float getBordureX() {
        return bordureX;
    }

    public void setBordureX(float bordureX) {
        this.bordureX = bordureX;
    }

    public float getBordureY() {
        return bordureY;
    }

    public void setBordureY(float bordureY) {
        this.bordureY = bordureY;
    }

    public String getTypeCoupe() {
        return typeCoupe;
    }

    public void setTypeCoupe(String typeCoupe) {
        this.typeCoupe = typeCoupe;
    }
    public Outil getOutil() {
        return outil;
    }
    public void setOutil(Outil outil) {
        this.outil = outil;
    }

}