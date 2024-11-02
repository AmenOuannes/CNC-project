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
        if (coupe == null) {
            throw new IllegalArgumentException("La coupe ne peut pas etre null.");
        }

        // Vérifier que la marge et la profondeur sont valides
        if (coupe.getMargeProfondeur() <= 0) {
            throw new IllegalArgumentException("La marge de profondeur doit etre positive.");
        }

        if (coupe.getProfondeur() <= 0) {
            throw new IllegalArgumentException("La profondeur doit etre positive.");
        }

        // Vérifier que l'outil est non nul
        if (coupe.getOutil() == null) {
            throw new IllegalArgumentException("L'outil ne peut pas etre invalide.");
        }

        this.ProfondeurDTO = coupe.getMargeProfondeur();
        this.marge_profondeurDTO = coupe.getProfondeur();
        this.outilDTO = coupe.getOutil();
    }
    

     public float getMargeProfondeurDTO() {
        return marge_profondeurDTO;
    }

    public void setMargeProfondeur(float margeProfondeurDTO) {
        if (margeProfondeurDTO <= 0) {
            throw new IllegalArgumentException("La marge de profondeur doit etre positive.");
        }
        this.marge_profondeurDTO = marge_profondeurDTO;
    }

    public float getProfondeur() {
        return ProfondeurDTO;
    }


    public void setProfondeur(float profondeurDTO) {
        if (profondeurDTO <= 0) {
            throw new IllegalArgumentException("La profondeur doit etre positive.");
        }
        this.ProfondeurDTO = ProfondeurDTO;
    }
    
    public void setOutilDTO(Outil outilDTO) {
        if (outilDTO == null) {
            throw new IllegalArgumentException("L'outil ne peut pas etre invalide.");
        }
        this.outilDTO = outilDTO;
    }
   

}


