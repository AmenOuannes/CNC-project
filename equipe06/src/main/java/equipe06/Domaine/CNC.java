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
        panneau = new Panneau(914.4f, 1219.2f, 0.5f); // Dimensions en mètres
      
        
        // Dimensions Panneau doivent etre positives
        assert panneau.getLargeur()>0 : "La largeur doit etre positive" ;
        assert panneau.getLongueur() > 0 : "La longueur doit etre positive" ;
        assert panneau.getProfondeur() > 0 : "L'epaisseur doit etre positive" ;
     
                 
        repere = new Repere(); // Repère pour gérer les conversions
        coupes = new Vector <Coupe>();

        
    }
    
    public Panneau getPanneau() {
        return panneau;
    }
    public Repere getRepere() {
        return repere;
    } //??
    public Vector<Coupe> getCoupes() {return coupes;}
    public Vector<Outil> getOutils() {return outils;}
    public Outil getOutil_courant() {return outil_courant;}
    
    
    public void creerCoupe(ElementCoupe e) {
        
        assert e != null : "l'element de la coupe ne peut pas etre invalide" ; 
        
        CoupeAxe ma_coupe = new CoupeAxe(e); // this is only for now, further we will build this using a switch case bloc
        
        if (CoupeValide(ma_coupe, this.panneau))
           {AjouterCoupe(ma_coupe);}
        else {
            
            assert false : "La coupe est invalide et ne peut pas etre ajoutée.";
            
            //throw CoupeInvalideError();
        }
    }
    
    public void ModifierCoupe(Coupe coupe) {
        // TODO : Implementation 

    }
    public boolean CoupeValide(Coupe coupe, Panneau panneau) {
        //to change when we have more coupes
        assert coupe != null : "La coupe ne peut pas etre invalide.";
        assert panneau != null : "Le panneau ne peut pas être invalide.";
        
        
        if(coupe instanceof CoupeAxe) {
            if(((CoupeAxe) coupe).getComposante()){
                return ((CoupeAxe) coupe).getAxe() < panneau.getLargeur();//check either largeur or longueur
            }
            else return ((CoupeAxe) coupe).getAxe() < panneau.getLongueur();
        }
        else return false;//for now
    }
    
    
    public boolean inPanneau(Point p, Panneau panneau){
        assert p != null : "Le point ne peut pas etre invalide.";
        assert panneau != null : "Le panneau ne peut pas etre invalide.";
         // TODO : Implementation 
        
        return true;
    }
    
    
    public void AjouterCoupe(Coupe coupe) {
        // S'assurer que l objet coupe est initialise et est valide
        assert coupe != null : "La coupe à ajouter ne peut pas etre invalide.";
        coupes.add(coupe);

    }

    
    
    
}
