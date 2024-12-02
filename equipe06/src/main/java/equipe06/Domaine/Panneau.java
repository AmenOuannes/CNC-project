package equipe06.Domaine;

import java.util.UUID;
import java.awt.Point;
import equipe06.Domaine.Utils.ZoneInterdite;

/**
 * Classe représentant un panneau, avec ses dimensions et une zone interdite.
 */
public class Panneau implements Cloneable {

    private float longueur;
    private float largeur;
    private float profondeur;
    private ZoneInterdite zoneInterdite;

    // Constructeur
    public Panneau(float longueur, float largeur, float profondeur) {
        if (longueur < 0) {
            throw new IllegalArgumentException("La longueur du panneau doit être supérieure à zéro.");
        }
        if (largeur < 0) {
            throw new IllegalArgumentException("La largeur du panneau doit être supérieure à zéro.");
        }
        if (profondeur < 0) {
            throw new IllegalArgumentException("La profondeur du panneau doit être supérieure à zéro.");
        }

        this.longueur = longueur;
        this.largeur = largeur;
        this.profondeur = profondeur;
        this.zoneInterdite = new ZoneInterdite(10, 10, 0, 0); // Initialisation par défaut
    }

    // Getters pour les dimensions
    public float getLongueur() { return longueur; }
    public float getLargeur() { return largeur; }
    public float getProfondeur() { return profondeur; }

    // setters pour les dimensions
    public void setLongueur(float longueur) {
        if (longueur <= 0) {
            throw new IllegalArgumentException("La longueur du panneau doit être supérieure à zéro.");
        }
        this.longueur = longueur;
    }

    public void setLargeur(float largeur) {
        if (largeur <= 0) {
            throw new IllegalArgumentException("La largeur du panneau doit être supérieure à zéro.");
        }
        this.largeur = largeur;
    }

    public void setProfondeur(float profondeur) {
        if (profondeur <= 0) {
            throw new IllegalArgumentException("La profondeur du panneau doit être supérieure à zéro.");
        }
        this.profondeur = profondeur;
    }

    // Gestion de la zone interdite
    public ZoneInterdite getZoneInterdite() {
        return zoneInterdite;
    }

    public void setZoneInterdite(ZoneInterdite zoneInterdite) {
        if (zoneInterdite == null) {
            throw new IllegalArgumentException("La zone interdite ne peut pas être invalide.");
        }
        this.zoneInterdite = zoneInterdite;
    }

    public void ajouterZoneInterdite(ZoneInterdite zoneInterdite) {
        if (zoneInterdite == null) {
            throw new IllegalArgumentException("La zone interdite ne peut pas être invalide.");
        }
        this.zoneInterdite = zoneInterdite;
    }

    public void supprimerZoneInterdite() {
        this.zoneInterdite = null;
    }

    // La fonction qui vérifie si on est à l'intérieur du panneau ou pas
    public boolean inPanneau(float x, float y) {
        assert this != null : "Le panneau ne peut pas être invalide.";

        float maxY = this.getLargeur();
        int minY = 0;
        float maxX = this.getLongueur();
        return (x >= 0 && x <= maxX) && ((Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) >= minY &&
                (Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) <= maxY);
    }

    // X -> LONGUEUR, Y -> LARGEUR
    public boolean surPanneau(Point reference) {
        float y = Repere.getInstance().convertirEnMmDepuisPixels(reference.y);
        final float tolerance = 50;
        float x = Repere.getInstance().convertirEnMmDepuisPixels(reference.x);
        boolean BordGauche = (Math.abs(x - 0) < tolerance) &&
                ((Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) >= 0 &&
                        (Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) <= this.getLargeur());
        boolean BordDroit = (Math.abs(x - this.getLongueur()) < tolerance) &&
                ((Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) >= 0 &&
                        (Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) <= this.getLargeur());
        boolean BordBas = (Math.abs(y - Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60))) < tolerance) &&
                ((x >= 0 && x <= this.getLongueur()));
        boolean BordHaut = (Math.abs(y - (Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - this.getLargeur())) < tolerance) &&
                ((x >= 0 && x <= this.getLongueur()));
        return BordGauche || BordDroit || BordHaut || BordBas;
    }

    public boolean surCoins(Point reference) {
        float y = Repere.getInstance().convertirEnMmDepuisPixels(reference.y);
        final float tolerance = 100;
        float x = Repere.getInstance().convertirEnMmDepuisPixels(reference.x);
        boolean BordGauche = (Math.abs(x - 0) < tolerance) &&
                ((Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) >= 0 &&
                        (Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) <= this.getLargeur());
        boolean BordDroit = (Math.abs(x - this.getLongueur()) < tolerance) &&
                ((Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) >= 0 &&
                        (Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - y) <= this.getLargeur());
        boolean BordBas = (Math.abs(y - Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60))) < tolerance) &&
                ((x >= 0 && x <= this.getLongueur()));
        boolean BordHaut = (Math.abs(y - (Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60)) - this.getLargeur())) < tolerance) &&
                ((x >= 0 && x <= this.getLongueur()));
        return (BordGauche || BordDroit) && (BordHaut || BordBas);
    }

    // Méthode de clonage pour permettre la sauvegarde/restauration des états du panneau
    @Override
    public Panneau clone() {
        try {
            Panneau cloned = (Panneau) super.clone();
            
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Erreur de clonage du panneau", e);
        }
    }
} 
