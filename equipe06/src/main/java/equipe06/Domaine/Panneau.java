/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;
import java.util.UUID;
import java.awt.Point;
import java.util.Vector;

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
        if (longueur < 0) {
            throw new IllegalArgumentException("La longueur du panneau doit etre superieure a zero.");
        }
        if (largeur < 0) {
            throw new IllegalArgumentException("La largeur du panneau doit etre superieure a zero.");
        }
        if (profondeur < 0) {
            throw new IllegalArgumentException("La profondeur du panneau doit être superieure a zero.");
        }

        this.longueur = longueur;
        this.largeur = largeur;
        this.profondeur = profondeur;
        this.zoneInterdite = new ZoneInterdite(10, 10, 0, 0); // Initialisation par défaut
        //this.Uuid = UUID.randomUUID();
    }

    // Getters pour les dimensions
    public float getLongueur() { return longueur; }
    public float getLargeur() { return largeur; }
    public float getProfondeur() { return profondeur; }  
    public UUID getUUID() { return Uuid; } //enlever le UUID pour le panneau

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
    //la fonction qui verifie si on est a l'interieur de panneau ou pas
     public boolean inPanneau(float x, float y) //katia
{

        assert this != null : "Le panneau ne peut pas etre invalide.";

        float maxY =  this.getLargeur() /*+ 130*/;
        int minY = 0;
        float maxX =  this.getLongueur() /*- 130*/;
        // Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) = 1500 
        return (x >= 0 && x <= maxX) && ((Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) >= minY && (Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) <= maxY);
    }
    // X -> LONGUEUR, Y -> LARGEUR
    public boolean surPanneau(Point reference) {
        float y = Repere.getInstance().convertirEnMmDepuisPixels(reference.y);
        final float tolerance = 50;
        float x = Repere.getInstance().convertirEnMmDepuisPixels(reference.x);
        boolean BordGauche = (Math.abs(x-0) < tolerance) && ((Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) >= 0 && (Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) <= this.getLargeur());
        boolean BordDroit = (Math.abs(x-this.getLongueur()) < tolerance) && ((Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) >= 0 && (Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) <= this.getLargeur());
        boolean BordBas = (Math.abs(y-Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)))< tolerance) && ((x >= 0 && x <= this.getLongueur()));
        boolean BordHaut = (Math.abs( y-( Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60))-this.getLargeur() ) )< tolerance) && ( (x >= 0 && x <= this.getLongueur() ));
        return BordGauche || BordDroit || BordHaut || BordBas;
    }

    public boolean surCoins(Point reference) {
        float y = Repere.getInstance().convertirEnMmDepuisPixels(reference.y);
        final float tolerance = 100;
        float x = Repere.getInstance().convertirEnMmDepuisPixels(reference.x);
        boolean BordGauche = (Math.abs(x-0) < tolerance) && ((Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) >= 0 && (Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) <= this.getLargeur());
        boolean BordDroit = (Math.abs(x-this.getLongueur()) < tolerance) && ((Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) >= 0 && (Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) <= this.getLargeur());
        boolean BordBas = (Math.abs(y-Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)))< tolerance) && ((x >= 0 && x <= this.getLongueur()));
        boolean BordHaut = (Math.abs( y-( Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60))-this.getLargeur() ) )< tolerance) && ( (x >= 0 && x <= this.getLongueur() ));
        return (BordGauche || BordDroit) && (BordHaut || BordBas);
    }
}
 