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
    } //vector<outilDTO>
    public OutilDTO getOutil_courant() {
        OutilDTO o = new OutilDTO(outil_courant);
        return o;
    } //outilDTO
    
    
    public void creerCoupe(ElementCoupe e) {
        
        assert e != null : "l'element de la coupe ne peut pas etre invalide" ; 
        
        CoupeAxe ma_coupe = new CoupeAxe(e); // this is only for now, further we will build this using a switch case bloc
        
        if (CoupeValide(ma_coupe, this.panneau))
           AjouterCoupe(ma_coupe);
        else {
            
            assert false : "La coupe est invalide et ne peut pas etre ajoutée.";//to change, throws you out of the app
            
            //throw CoupeInvalideError();
        }
    }
    /// à discuter attribut de la fonction coupe ou bien element
    public void ModifierCoupe(Coupe coupe) {
        Coupe remove = this.coupes.removeFirst();
        remove = null;
        this.coupes.addFirst(coupe);
    }
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
    }
    
    
    public boolean inPanneau(Point p, Panneau panneau){
        assert p != null : "Le point ne peut pas etre invalide.";
        assert panneau != null : "Le panneau ne peut pas etre invalide.";
        return p.x<panneau.getLargeur() && p.y<panneau.getLongueur(); //check between longueur et largeur
    }
    
    
    public void AjouterCoupe(Coupe coupe) {
        // S'assurer que l objet coupe est initialise et est valide
        assert coupe != null : "La coupe à ajouter ne peut pas etre invalide.";
        coupes.add(coupe);

    }
    public void supprimerCoupe(Coupe coupe) {
        coupes.remove(coupe);
    }
    
    
    
}
