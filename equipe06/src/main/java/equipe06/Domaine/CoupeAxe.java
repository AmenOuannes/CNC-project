/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;
import equipe06.Domaine.Utils.ElementCoupe;
import equipe06.Domaine.Coupe;

import java.awt.*;
import java.util.UUID;
import java.util.Vector;

/**
 *
 * @author katia
 */
public class CoupeAxe extends Coupe {

    // Attributs spécifiques à CoupeAxe
    private float axe;
    private float axeRelatif;
    private boolean composante;
    private Point reference;
    private Point destination;
    private Vector<UUID> myRef;
    private ElementCoupe element;


    // Implémentation de la méthode abstraite coupe
    public CoupeAxe(ElementCoupe e, Vector<UUID> myRef, Point reference) {
        super(e);
        assert e != null : "L'element de coupe est invalide.";
        assert e.getAxe() >= 0 : "La valeur de l'axe doit être superieure ou egale a zero.";
        
        this.axe = e.getAxe();
        this.composante = e.getComposante();
        this.reference = reference;
        this.myRef = myRef;
        this.axeRelatif = e.getAxe();
        this.destination = e.getPointDestination();
        this.element = e;

    }

    // Getter et Setter pour Axe
    public float getAxe() {
        return axe;
    }

    public void setAxe(float axe) {
         if (axe < 0) {
            throw new IllegalArgumentException("La valeur de l'axe doit etre superieure ou egale a zero.");
        }
        this.axe = axe;
        int ymax = (int) Repere.getInstance().convertirEnPixelsDepuisPouces(60);

        /*if(this.getTypeCoupe()=="V") this.setDestination(new Point(Repere.getInstance().convertirEnPixelsDepuisMm(axe), ymax-1));
        else this.setDestination(new Point(1, ymax-Repere.getInstance().convertirEnPixelsDepuisMm(axe)));*/
    }

    // Getter et Setter pour Composante
    public boolean getComposante() {

        return composante;
    }

    public void setComposante(boolean composante) {

        this.composante = composante;
    }


    public Point getReference() {
        return reference;
    }

    public void ChangeReference(Point reference) {
        assert reference != null : "La nouvelle référence ne peut pas être null.";

        this.reference = reference;
    }
    public Vector<UUID> getMyRef() {
        return myRef;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }
    public void setAxeRelatif(boolean reference, Panneau panneau, float a) {
        if(this.getTypeCoupe()=="V") {
            this.axeRelatif = a;
            if (reference) axe = panneau.getLongueur()-a;
            else axe =a;
        }
        else {
            this.axeRelatif = a;
            float ymax = Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60));

            if (!reference)  {axe = ymax- panneau.getLargeur()+a;System.out.println("le fameux if");}
            else {axe = ymax- a;System.out.println("le fameux else");}
        }
    }
    
    
    public ElementCoupe getElement() {
        return element;
    }
}

