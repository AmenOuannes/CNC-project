/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;
import equipe06.Domaine.Utils.ElementCoupe;

import java.awt.*;
import java.util.UUID;
import java.util.Vector;

/**
 *
 * @author katia
 */
public abstract class Coupe implements Cloneable{

    // Attributs privés
    //private Outils outils;
    private float marge_profondeur = 0.5f;
    private float Profondeur;
    private float outil;
    public UUID uuid;
    private String typeCoupe;
    public boolean invalide = false;


    public Coupe(ElementCoupe e) {
        
        assert e != null : "l'element coupe ne pas etre invalide" ;
        assert e.getProfondeur() > 0 : "La profondeur doit etre positive.";
        assert e.getMarge() > 0 : "La marge de profondeur doit être positive.";

        assert e.getTypeCoupe() != null : "La typeCoupe doit etre positive.";

        this.Profondeur = e.getProfondeur();
        this.marge_profondeur = e.getMarge();
        this.outil = e.getOutil();
        this.uuid = UUID.randomUUID();
        this.typeCoupe = e.getTypeCoupe();

    }
     @Override
    public Coupe clone() {
        try {
            return (Coupe) super.clone(); // Assurez-vous que tous les attributs sont clonés proprement
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Erreur de clonage de la coupe", e);
        }
    }
    

     public float getMargeProfondeur() {
        return marge_profondeur;
    }

    public void setMargeProfondeur(float margeProfondeur) {
        assert margeProfondeur > 0 : "La marge de profondeur doit etre positive.";
        this.marge_profondeur = margeProfondeur;
    }

    public float getProfondeur() {
        return Profondeur;
    }


    public void setProfondeur(float profondeur) {
        assert profondeur > 0 : "La profondeur doit etre positive.";
        this.Profondeur = profondeur;
    }
    
    public float getOutil() {
        return outil;
    }
    public void setOutil(float outil) {


    
    this.outil = outil;
}
public UUID getUUID() {
        return uuid;
}
public void setUUID(UUID uuid) {
    assert uuid != null : "Le UUID ne peut pas être null.";
        this.uuid = uuid;
}
public String getTypeCoupe() {
        return typeCoupe;
    }
}






