/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;
/**
 *
 * @author hedib
 */
public class CoupeAxeDTO extends CoupeDTO {

    // Attributs spécifiques à CoupeAxe
    private float axeDTO;
    private boolean composanteDTO;

    public CoupeAxeDTO(CoupeAxe coupeAxe) {
        super(coupeAxe);
        
        if (coupeAxe == null) {
            throw new IllegalArgumentException("La coupe Axe fournie est invalide.");
        }

        this.axeDTO = coupeAxe.getAxe();
        this.composanteDTO = coupeAxe.getComposante();
    }

    public float getAxe() {
        return axeDTO;
    }

    public void setAxe(float axeDTO) {
        if (axeDTO < 0) {
            throw new IllegalArgumentException("La valeur de l'axe doit être supérieure ou égale à zéro.");
        }
        this.axeDTO = axeDTO;
    }

    // Getter et Setter pour Composante
    public boolean getComposante() {
        return composanteDTO;
    }

    public void setComposante(boolean composanteDTO) {
        this.composanteDTO = composanteDTO;
    }

}

