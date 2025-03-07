/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;
import java.util.UUID;
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
    private UUID Uuid;

    // Constructeur
    public Panneau(float longueur, float largeur, float profondeur) {
        if (longueur <= 0) {
            throw new IllegalArgumentException("La longueur du panneau doit etre superieure a zero.");
        }
        if (largeur <= 0) {
            throw new IllegalArgumentException("La largeur du panneau doit etre superieure a zero.");
        }
        if (profondeur <= 0) {
            throw new IllegalArgumentException("La profondeur du panneau doit être superieure a zero.");
        }

        this.longueur = longueur;
        this.largeur = largeur;
        this.profondeur = profondeur;
        this.Uuid = UUID.randomUUID();
    }

    // Getters pour les dimensions
    public float getLongueur() { return longueur; }
    public float getLargeur() { return largeur; }
    public float getProfondeur() { return profondeur; }  
    public UUID getUUID() { return Uuid; }

    // setters pour les dimensions
    public void setLongueur(float longueur) {
        if (longueur <= 0) {
             throw new IllegalArgumentException("La longueur du panneau doit etre superieure a zero.");
        }
        this.longueur = longueur;
    }
    public void setLargeur(float largeur) {
         if (largeur <= 0) {
            throw new IllegalArgumentException("La largeur du panneau doit etre superieure a zero.");
        }
        this.largeur = largeur;
    }
    public void setProfondeur(float profondeur) {
         if (profondeur <= 0) {
            throw new IllegalArgumentException("La profondeur du panneau doit être superieure a zero.");
        }
        this.profondeur = profondeur;
    }

    // Gestion de la zone interdite
    public ZoneInterdite getZoneInterdite() {
        return zoneInterdite;
    }
    public void setZoneInterdite(ZoneInterdite zoneInterdite) {
        if (zoneInterdite == null) {
            throw new IllegalArgumentException("La zone interdite ne peut pas etre invalide.");
        }
        this.zoneInterdite = zoneInterdite;
    }

    public void ajouterZoneInterdite(ZoneInterdite zoneInterdite) {
         if (zoneInterdite == null) {
            throw new IllegalArgumentException("La zone interdite ne peut pas etre invalide.");
        }
        this.zoneInterdite = zoneInterdite;
    }

    public void supprimerZoneInterdite() {
        this.zoneInterdite = null;
    }
}
