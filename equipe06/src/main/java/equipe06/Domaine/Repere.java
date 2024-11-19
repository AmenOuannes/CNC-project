/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;

/**
 *
 * @author ziedd 
 */

public  class Repere {
    private static final float PPI = 6; // Pixels par pouce, à ajuster selon l'écran
    private static final float PPM = PPI / 25.4f; // Pixels par millimètre
    
    
    public int convertirEnPixelsDepuisMm(double valeurEnMm) {
        if (valeurEnMm < 0) {
            throw new IllegalArgumentException("La valeur en millimètres doit être positive.");
        }
        return (int) (valeurEnMm * PPM);
    }
    public float convertirEnPixelsDepuisPouces (float valeurEnPouces){
        if (valeurEnPouces < 0) {
            throw new IllegalArgumentException("La valeur en pouces doit être positive.");
        }
        return valeurEnPouces * PPI;
    }
    public float convertirEnMmDepuisPixels(float valeurEnPixels) {
        if (valeurEnPixels < 0) {
            throw new IllegalArgumentException("La valeur en pixels doit être positive.");
        }
        return valeurEnPixels / PPM;
    }
    public float convertirEnUnitesDepuisPixels(float valeurEnPixels) {
        return valeurEnPixels / PPM;
    }
}
