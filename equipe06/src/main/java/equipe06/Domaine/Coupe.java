/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;
import equipe06.Domaine.Utils.ElementCoupe;
//import equipe06.Domaine.Outils;
/**
 *
 * @author katia
 */
public abstract class Coupe {

    // Attributs privés
    //private Outils outils;
    private float marge_profondeur = 0.5f;
    private float Profondeur;
    public abstract Coupe coupe(ElementCoupe e);
    

     public float getMargeProfondeur() {
        return marge_profondeur;
    }

    public void setMargeProfondeur(float margeProfondeur) {
        this.marge_profondeur = marge_profondeur;
    }

    public float getProfondeur() {
        return Profondeur;
    }


    public void setProfondeur(float profondeur) {
        this.Profondeur = Profondeur;
    }
   

}

