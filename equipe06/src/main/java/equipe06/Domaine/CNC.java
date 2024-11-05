/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;

import equipe06.Domaine.Panneau;
import equipe06.Domaine.Repere;
import equipe06.Domaine.Coupe;
import equipe06.Domaine.*;
import equipe06.Domaine.Utils.ElementCoupe;

import java.awt.Point;
import java.util.Vector;


/**
 *
 * @author ziedd
 */
public class CNC {
    private Panneau panneau;
    private Repere repere;
    private Vector<Coupe> coupes;
    private Vector<Outil> outils;
    private Outil outil_courant;


    public CNC() {
        panneau = new Panneau(914.4f, 1219.2f, 0.5f); // Dimensions en mm
        repere = new Repere(); // Repère pour gérer les conversions
        coupes = new Vector <Coupe>();

        
    }
    
    public PanneauDTO getPanneau() {

        return new PanneauDTO(panneau);
    }

    public Repere getRepere() {
        return repere;
    }

    public Vector<CoupeDTO> getCoupes() {
        Vector<CoupeDTO> cDTO = new Vector<CoupeDTO>();
        for (Coupe coupe : coupes) cDTO.add(new CoupeDTO(coupe));
        return cDTO;
    }

    public Vector<OutilDTO> getOutils() {
        Vector<OutilDTO> v = null;
        for(Outil outil : outils) v.add(new OutilDTO(outil));
        return v;
    }

    public OutilDTO getOutil_courant() {
        return new OutilDTO(outil_courant);
    }
    
    
    public void creerCoupe(ElementCoupe e) {
        
        assert e != null : "l'element de la coupe ne peut pas etre invalide" ; 
        
        CoupeAxe ma_coupe = new CoupeAxe(e); // this is only for now, further we will build this using a switch case bloc
        
        if (inPanneau(e.getPointOrigine(), panneau))
           AjouterCoupe(ma_coupe);
        else {
            
            assert false : "La coupe est invalide et ne peut pas etre ajoutée.";//to change, throws you out of the app
            

        }
    } // to change in the next livrable
    /// à discuter attribut de la fonction coupe ou bien element
    public void ModifierCoupe(float axe) {
            CoupeAxe coupe = (CoupeAxe) coupes.get(0);
            coupe.setAxe(axe);


    } // to change in the next livrable
    public boolean CoupeValide(Coupe coupe, Panneau panneau) {

        assert coupe != null : "La coupe ne peut pas etre invalide.";
        assert panneau != null : "Le panneau ne peut pas être invalide.";

        //to change when we have more coupes
        if(coupe instanceof CoupeAxe) {
            if(((CoupeAxe) coupe).getComposante()){
                return ((CoupeAxe) coupe).getAxe() < panneau.getLongueur(); //check either largeur or longueur
            }
            else return ((CoupeAxe) coupe).getAxe() < panneau.getLargeur();
        }
        else return false;//for now
    } // to change in the next livrable
    
    
    public boolean inPanneau(Point p, Panneau panneau){
        assert p != null : "Le point ne peut pas etre invalide.";
        assert panneau != null : "Le panneau ne peut pas etre invalide.";
        int minX = 130;
        int maxX = (int) panneau.getLargeur() + 130;
        int minY = 0;
        int maxY = (int) panneau.getLongueur() - 130;
        return (p.x >= minX && p.x <= maxX) && ((1500 - p.y) >= minY && (1500 - p.y) <= maxY);
    }
    
    
    public void AjouterCoupe(Coupe coupe) {
        // S'assurer que l objet coupe est initialise et est valide

        coupes.add(coupe);

    }
    public void supprimerCoupe() {
        if(!coupes.isEmpty()) coupes.removeLast();

    }
    

    
    
}
