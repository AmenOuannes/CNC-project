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
        this.axeDTO = coupeAxe.getAxe();
        this.composanteDTO = coupeAxe.getComposante();
    }

    public float getAxe() {
        return axeDTO;
    }

    public void setAxe(float axeDTO) {
        this.axeDTO = axeDTO;
    }

    // Getter et Setter pour Composante
    public boolean setComposante() {
        return composanteDTO;
    }

    public void setComposante(boolean composanteDTO) {
        this.composanteDTO = composanteDTO;
    }

}

