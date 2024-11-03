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

public  class CoupeDTO {

    // Attributs privés
    //private Outils outils;
    private float marge_profondeurDTO = 0.5f;
    private float ProfondeurDTO;
    private  float axeDTO;
    private boolean composanteDTO;
    //private OutilDTO outil; //outilDTO

    public CoupeDTO(Coupe coupe){
        this.ProfondeurDTO = coupe.getMargeProfondeur();
        this.marge_profondeurDTO= coupe.getProfondeur();
        //this.outil = coupe.getOutil(); outilDTO
        if(coupe instanceof CoupeAxe){
            this.axeDTO = ((CoupeAxe) coupe).getAxe();
            this.composanteDTO = ((CoupeAxe) coupe).getComposante();
        }

    }
    
    public float getProfondeurDTO() {
        return ProfondeurDTO;
    }

     public float getMargeProfondeur() {
        return marge_profondeurDTO;
    }

    public float getAxeDTO(){
        assert axeDTO > 0;
        return this.axeDTO;
    }
    public boolean isComposanteDTO() {
        assert axeDTO > 0;
        return composanteDTO;
    }


}


