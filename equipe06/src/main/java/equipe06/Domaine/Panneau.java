/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;

import equipe06.Domaine.Utils.ZoneInterdite;
/**
 *
 * @author ziedd
 */
public class Panneau {
   
    private float longueur;
    private float largeur;
    private float profondeur;
    private ZoneInterdite zoneInterdite;

    // Constructeur
    public Panneau(float longueur, float largeur, float profondeur) {
        this.longueur = longueur;
        this.largeur = largeur;
        this.profondeur = profondeur;
    }

    // Getters pour les dimensions
    public float getLongueur() { return longueur; }
    public float getLargeur() { return largeur; }
    public float getProfondeur() { return profondeur; }    

    // setters pour les dimensions
    public void setLongueur(float longueur) {
        this.longueur = longueur;
    }
    public void setLargeur(float largeur) {
        this.largeur = largeur;
    }
    public void setProfondeur(float profondeur) {
        this.profondeur = profondeur;
    }

    // Gestion de la zone interdite
    public ZoneInterdite getZoneInterdite() {
        return zoneInterdite;
    }
    public void setZoneInterdite(ZoneInterdite zoneInterdite) {
        this.zoneInterdite = zoneInterdite;
    }

    public void ajouterZoneInterdite(ZoneInterdite zoneInterdite) {
        this.zoneInterdite = zoneInterdite;
    }

    public void supprimerZoneInterdite() {
        this.zoneInterdite = null;
    }
}
