/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;
import equipe06.Domaine.Utils.ElementCoupe;

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

    public Coupe(ElementCoupe e){
        this.Profondeur = e.getProfondeur();
        this.marge_profondeur = e.getMarge();
        this.outil = e.getOutil();
    }
    

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

