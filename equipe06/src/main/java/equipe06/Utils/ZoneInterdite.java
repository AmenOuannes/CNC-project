/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Utils;

/**
 *
 * @author ziedd
 */
public class ZoneInterdite {
    
    private float longueur;
    private float largeur;
    private float distanceToX;
    private float distanceToY;

    // Constructeur
    public ZoneInterdite(float longueur, float largeur, float distanceToX, float distanceToY) {
        this.longueur = longueur;
        this.largeur = largeur;
        this.distanceToX = distanceToX;
        this.distanceToY = distanceToY;
    }

    // Getters et setters pour les attributs
    public float getLongueur() {
        return longueur;
    }

    public void setLongueur(float longueur) {
        this.longueur = longueur;
    }

    public float getLargeur() {
        return largeur;
    }

    public void setLargeur(float largeur) {
        this.largeur = largeur;
    }

    public float getDistanceToX() {
        return distanceToX;
    }

    public void setDistanceToX(float distanceToX) {
        this.distanceToX = distanceToX;
    }

    public float getDistanceToY() {
        return distanceToY;
    }

    public void setDistanceToY(float distanceToY) {
        this.distanceToY = distanceToY;
    }

    // Méthode pour modifier la zone interdite
    public void modifierZone(float longueur, float largeur, float distanceToX, float distanceToY) {
        this.longueur = longueur;
        this.largeur = largeur;
        this.distanceToX = distanceToX;
        this.distanceToY = distanceToY;
    }
    
}
