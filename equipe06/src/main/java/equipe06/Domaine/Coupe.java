/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;
import equipe06.Domaine.Utils.ElementCoupe;

import java.util.UUID;

/**
 *
 * @author katia
 */
public abstract class Coupe {

    // Attributs privés
    //private Outils outils;
    private float marge_profondeur = 0.5f;
    private float Profondeur;
    private Outil outil;
    //public UUID Uuid; add its methods

    public Coupe(ElementCoupe e){
        
        assert e != null : "l'element coupe ne pas etre invalide" ;
        assert e.getProfondeur() > 0 : "La profondeur doit etre positive.";
        assert e.getMarge() > 0 : "La marge de profondeur doit être positive.";
        assert e.getOutil() != null : "L'outil ne peut pas être invalide.";
        
        this.Profondeur = e.getProfondeur();
        this.marge_profondeur = e.getMarge();
        this.outil = e.getOutil();
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
    
    public Outil getOutil() {
        return outil;
    }
    public void setOutil(Outil outil) {

    assert outil != null : "L'outil ne peut pas etre invalide.";
    
    this.outil = outil;
}


}

