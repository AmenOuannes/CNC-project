/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine.Utils;

import equipe06.Domaine.Coupe;
import equipe06.Domaine.CoupeL;
import equipe06.Domaine.CoupeRec;
import equipe06.Domaine.CoupeAxe;
import equipe06.Domaine.Repere;
import java.awt.*;

import static java.util.Collections.min;

/**
 *
 * @author ziedd
 */
public class ZoneInterdite {
    
    private float longueur;
    private float largeur;
    private float distanceToX;
    private float distanceToY;
    private Point Origin;
    private Point Destination;

    // Constructeur
    public ZoneInterdite(Point Origin, Point Destination) {

        this.Origin = Origin;
        this.Destination = Destination;
    }

    // Getters et setters pour les attributs
    public float getLongueur() {
        return longueur;
    }

    public void setLongueur(float longueur) {
        if (longueur <= 0) {
            throw new IllegalArgumentException("La longueur doit etre superieure a zero.");
        }
        this.longueur = longueur;
    }

    public float getLargeur() {
        return largeur;
    }

    public void setLargeur(float largeur) {
        if (largeur <= 0) {
            throw new IllegalArgumentException("La largeur doit etre superieure a zero.");
        }
        this.largeur = largeur;
    }

    public float getDistanceToX() {
        return distanceToX;
    }

    public void setDistanceToX(float distanceToX) {
        if (distanceToX < 0) {
            throw new IllegalArgumentException("La distance par rapport à l'axe X doit etre non negative.");
        }
        this.distanceToX = distanceToX;
    }

    public float getDistanceToY() {
        return distanceToY;
    }

    public void setDistanceToY(float distanceToY) {
        if (distanceToY < 0) {
            throw new IllegalArgumentException("La distance par rapport à l'axe Y doit etre non negative.");
        }
        this.distanceToY = distanceToY;
    }

    // Méthode pour modifier la zone interdite
    public void modifierZone(float longueur, float largeur, float distanceToX, float distanceToY) {
        if (longueur <= 0) {
            throw new IllegalArgumentException("La longueur doit etre superieure a zero.");
        }
        if (largeur <= 0) {
            throw new IllegalArgumentException("La largeur doit etre superieure a zero.");
        }
        if (distanceToX < 0) {
            throw new IllegalArgumentException("La distance par rapport à l'axe X doit etre non negative.");
        }
        if (distanceToY < 0) {
            throw new IllegalArgumentException("La distance par rapport à l'axe Y doit etre non negative.");
        }
        this.longueur = longueur;
        this.largeur = largeur;
        this.distanceToX = distanceToX;
        this.distanceToY = distanceToY;
    }
    public Point getOrigin() {
        return Origin;
    }
    public void setOrigin(Point Origin) {
        this.Origin = Origin;
    }
    public Point getDestination() {
        return Destination;
    }
    public void setDestination(Point Destination) {
        this.Destination = Destination;
    }
      @Override
    public ZoneInterdite clone() {
        try {
            return (ZoneInterdite) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Erreur de clonage de ZoneInterdite", e);
        }
    }

    public boolean surZoneInterdite(Coupe coupe) {
        switch (coupe.getTypeCoupe()) {
            case "Rect", "Bordure":
                CoupeRec Rect = (CoupeRec) coupe;
                float RectOriginex = Repere.getInstance().convertirEnMmDepuisPixels(Rect.getPointOrigine().x);
                float RectDestinationx = Repere.getInstance().convertirEnMmDepuisPixels(Rect.getPointDestination().x);
                float RectOriginey = Repere.getInstance().convertirEnMmDepuisPixels(Rect.getPointOrigine().y);
                float RectDestinationy = Repere.getInstance().convertirEnMmDepuisPixels(Rect.getPointDestination().y);
                float Originx = Repere.getInstance().convertirEnMmDepuisPixels(Origin.x);
                float Destinationx = Repere.getInstance().convertirEnMmDepuisPixels(Destination.x);
                float Originy = Repere.getInstance().convertirEnMmDepuisPixels(Origin.y);
                float Destinationy = Repere.getInstance().convertirEnMmDepuisPixels(Destination.y);
                if (RectDestinationx < (float)Math.min(Originx, Destinationx) || (float)Math.max(Originx, Destinationx) < RectOriginex) {
                    return false;
                }

                // If one rectangle is above the other
                if (RectDestinationy < (float)Math.min(Originy, Destinationy) || (float)Math.max(Originy, Destinationy) < RectOriginey) {
                    return false;
                }


                // Otherwise, the rectangles intersect
                return true;
            case "L":
                CoupeL L = (CoupeL) coupe;
                float LOriginex = Repere.getInstance().convertirEnMmDepuisPixels(L.getPointOrigine().x);
                float LDestinationx = Repere.getInstance().convertirEnMmDepuisPixels(L.getPointDestination().x);
                float LOriginey = Repere.getInstance().convertirEnMmDepuisPixels(L.getPointOrigine().y);
                float LDestinationy = Repere.getInstance().convertirEnMmDepuisPixels(L.getPointDestination().y);
                float originx = Repere.getInstance().convertirEnMmDepuisPixels(Origin.x);
                float destinationx = Repere.getInstance().convertirEnMmDepuisPixels(Destination.x);
                float originy = Repere.getInstance().convertirEnMmDepuisPixels(Origin.y);
                float destinationy = Repere.getInstance().convertirEnMmDepuisPixels(Destination.y);
                if (LDestinationx < (float)Math.min(originx, destinationx) || (float)Math.max(originx, destinationx) < LOriginex) {
                    return false;
                }

                // If one rectangle is above the other
                if (LDestinationy < (float)Math.min(originy, destinationy) || (float)Math.max(originy, destinationy) < LOriginey) {
                    return false;
                }

                // Otherwise, the rectangles intersect
                return true;
            case "V":
                System.out.print("hani dkhalt");
                CoupeAxe V = (CoupeAxe) coupe;
                float originxx = Repere.getInstance().convertirEnMmDepuisPixels(Origin.x);
                float destinationxx = Repere.getInstance().convertirEnMmDepuisPixels(Destination.x);
                if (V.getAxe() >= (float)Math.min(originxx, destinationxx) && V.getAxe() <= (float)Math.max(originxx, destinationxx)) {
                    System.out.print("emchi nayek");
                    return true;
                }
            case "H":
                System.out.print("hani dkhalt");
                CoupeAxe H = (CoupeAxe) coupe;
                float originxxx = Repere.getInstance().convertirEnMmDepuisPixels(Origin.y);
                float destinationxxx = Repere.getInstance().convertirEnMmDepuisPixels(Destination.y);
                if (H.getAxe() >= (float)Math.min(originxxx, destinationxxx) && H.getAxe() <= (float)Math.max(originxxx, destinationxxx)) {
                    System.out.print("emchi nayek");
                    return true;
                }
        }
        return false;
    }
    
}
