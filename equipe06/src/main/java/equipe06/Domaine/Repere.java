/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;

/**
 *
 * @author ziedd
 */
public class Repere {
    
    private static double PIXELS_PAR_MM = 3.78; // Conversion de mm en pixels
    private static double MM_PAR_POUCE = 25.4;  // Conversion de pouces en mm
    
    // Méthode pour convertir une longueur de mm en pixels
    public int convertirEnPixels(double valeurEnMm) {
        return (int) (valeurEnMm * PIXELS_PAR_MM);
    }

    // Méthode pour convertir des pixels en mm
    public double convertirEnMm(int valeurEnPixels) {
        return valeurEnPixels / PIXELS_PAR_MM;
    }

    // Méthode pour convertir des mm en pouces
    public double convertirEnPouces(double valeurEnMm) {
        return valeurEnMm / MM_PAR_POUCE;
    }

    // Méthode pour convertir des pouces en mm
    public double convertirEnMmDepuisPouces(double valeurEnPouces) {
        return valeurEnPouces * MM_PAR_POUCE;
    }
    
}
