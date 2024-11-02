/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;
import equipe06.Domaine.Utils.ElementCoupe;
import equipe06.Domaine.Coupe;
/**
 *
 * @author katia
 */
public class CoupeAxe extends Coupe {

    // Attributs spécifiques à CoupeAxe
    private float axe;
    private boolean composante;

    // Constructeur de CoupeAxe
    //public CoupeAxe(Outil outil, float margeProfondeur, float profondeur, float axe, boolean composante) {
        //super(outil, margeProfondeur, profondeur);  // Appelle le constructeur de la classe mère Coupe
        //this.axe = axe;
        //this.composante = composante;
    //}

        // Implémentation de la méthode abstraite coupe
    //@Override
    public CoupeAxe(ElementCoupe e) {
        super(e);
        assert e != null : "L'element de coupe est invalide.";
        assert e.getAxe() >= 0 : "La valeur de l'axe doit être superieure ou egale a zero.";
        
        this.axe = e.getAxe();
        this.composante = e.getComposante();
    }
    /*public Coupe coupe(ElementCoupe e) {
        // Utilise les méthodes getMarge() et getProfondeur() de ElementCoupe
        setMargeProfondeur(e.getMarge());
        setProfondeur(e.getProfondeur());
        return this; // Retourne l'instance de CoupeAxial après la coupe
    }*/
    // Getter et Setter pour Axe
    public float getAxe() {
        return axe;
    }

    public void setAxe(float axe) {
         if (axe < 0) {
            throw new IllegalArgumentException("La valeur de l'axe doit etre superieure ou egale a zero.");
        }
        this.axe = axe;
    }

    // Getter et Setter pour Composante
    public boolean getComposante() {
        return composante;
    }

    public void setComposante(boolean composante) {
        this.composante = composante;
    }

}
/*if (e == null) {
    throw new IllegalArgumentException("L'élément de coupe est invalide (null).");
}
if (e.getAxe() < 0) {
    throw new IllegalArgumentException("La valeur de l'axe doit être supérieure ou égale à zéro.");
}*/ //TODO : travailler avec Exception ou Assert ? 
