/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;

import equipe06.Domaine.Panneau;
import equipe06.Domaine.Repere;

/**
 *
 * @author ziedd
 */
public class CNC {
    private Panneau panneau;
    private Repere repere;

    public CNC() {
        panneau = new Panneau(0914.4f, 1219.2f, 0.5f); // Dimensions en mètres
        repere = new Repere(); // Repère pour gérer les conversions
        
    }
    
    public Panneau getPanneau() {
        return panneau;
    }

    public Repere getRepere() {
        return repere;
    }
    
    
}
