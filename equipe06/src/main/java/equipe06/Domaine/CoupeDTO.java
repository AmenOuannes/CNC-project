/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;
import equipe06.Domaine.Utils.ElementCoupe;

/**
 *
 * @author hedib
 */

public abstract class CoupeDTO {

    // Attributs privés
    //private Outils outils;
    private float marge_profondeurDTO = 0.5f;
    private float ProfondeurDTO;
    private Outil outilDTO;

    public CoupeDTO(Coupe coupe){
        this.ProfondeurDTO = coupe.getMargeProfondeur();
        this.marge_profondeurDTO = coupe.getProfondeur();
        this.outilDTO = coupe.getOutil();
    }
    

     public float getMargeProfondeurDTO() {
        return marge_profondeurDTO;
    }

    public void setMargeProfondeur(float margeProfondeurDTO) {
        this.marge_profondeurDTO = marge_profondeurDTO;
    }

    public float getProfondeur() {
        return ProfondeurDTO;
    }


    public void setProfondeur(float profondeurDTO) {
        this.ProfondeurDTO = ProfondeurDTO;
    }
   

}


