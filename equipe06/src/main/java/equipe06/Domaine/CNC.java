package equipe06.Domaine;

import equipe06.Domaine.Panneau;
import equipe06.Domaine.Repere;
import equipe06.Domaine.Coupe;
import equipe06.Domaine.*;
import equipe06.Domaine.Utils.ElementCoupe;

import java.awt.Point;
import java.util.Vector;


public class CNC {
    private Panneau panneau;
    private Repere repere;
    private Vector<Coupe> coupes;
    private Vector<Point> points_de_reference;
    private Vector<Outil> outils;
    private Outil outil_courant;


    public CNC() {
        panneau = new Panneau(0,0,0);
        //panneau = new Panneau(1219.2f,914.4f , 0.5f); // Dimensions en mm //remove @zied
        //panneau = new Panneau(914.4f, 1219.2f, 0.5f); // Dimensions en mm //remove @zied
        repere = new Repere(); // Repère pour gérer les conversions
        coupes = new Vector <Coupe>();
        outils = new Vector<Outil>(12);
        
    }
    public void creerPanneau(float longueurX, float largeurY, float profondeurZ) {
        // Création de l'objet Panneau avec les attributs donnés
        this.panneau = new Panneau(longueurX, largeurY, profondeurZ);
    }
    
    public PanneauDTO getPanneau() {
        if (panneau == null) {
            throw new NullPointerException("Le panneau n'a pas encore été créé.");
        }
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
    // TODO fix redundancy
    public void ajouterOutil(String nom, float largeurCoupe){
        if (outils.size() < 12 /* || outil.exist*/){
            Outil outil = new Outil(nom, largeurCoupe);
            outils.add(outil);
            System.out.println("Outil ajouté avec succès : " + outil); //remove @zied
        } else {
            System.out.println("Le nombre maximum d'outils (12) est atteint. Impossible d'ajouter un nouvel outil."); //remove @zied
        }
    }
    public Vector<OutilDTO> getOutils() {
        Vector<OutilDTO> v = new Vector<>();
        for(Outil outil : outils) v.add(new OutilDTO(outil));
        return v;
    }

    public OutilDTO getOutil_courant() {

        return new OutilDTO(outil_courant);
    }
    //TODO: mettre a jour depuis la selection du outil courant depuis l'interface
    public void setOutil_courant(OutilDTO outil_courant) {}
    //TODO : rendre cette boucle en try catch
    // TODO check if outil est outilCourant
    public void supprimerOutilParIndex(int index) {
        if (index >= 0 && index < outils.size()) {
            outils.remove(index);
            System.out.println("Outil supprimé avec succès."); //control local remove @ zied
        } else {
            System.out.println("Index invalide. Impossible de supprimer l'outil."); //control local remove @ zied
        }
    }
    public void creerCoupeL(){
        //TODO coupe en L, attribut extraits du controleur
    }
    public void creerCoupeRect(){
        //TODO coupe rect, attribut extraits du controleur
    }
    public void ModifierCoupesOutilCourant(){
        //TODO modifier toutes les coupes par l'outil courant
    }
    public void CreerCoupeBordure(){
        //TODO creer une coupe bordure
    }
    // TODO :changer ça en fnct creer coupeAXE, correction sur l'ajout du point origine et destination dans le element coupe
    public void creerCoupe/*Axe*/(float axe,  float y, boolean composante) {
        Point pointOrigine = new Point((int)axe, (int)y); //change point
        Point pointDestination = new Point((int)axe, 0);
        ElementCoupe e = new ElementCoupe( // elle doit etre dans le cnc pas dans controleur
                pointOrigine, pointDestination, 5.0f, 0.3f, axe, composante, 0.0f, 0.0f, "CoupeAxiale", null
        );
        assert e != null : "l'element de la coupe ne peut pas etre invalide" ; 
        
        CoupeAxe ma_coupe = new CoupeAxe(e); // this is only for now, further we will build this using a switch case bloc
        
        if (panneau.inPanneau(e.getPointOrigine(), panneau))
           AjouterCoupe(ma_coupe);
        else {
            
            assert false : "La coupe est invalide et ne peut pas etre ajoutée.";//to change, throws you out of the app
            

        }
    }
    // TODO: Rendre modifier apte a modifier toute coupe possible
    // cette fonction fait appel au divers coupes
    public void ModifierCoupe(float axe) {
            CoupeAxe coupe = (CoupeAxe) coupes.get(0);
            coupe.setAxe(axe);


    }
    
    public void ModifierCoupeRectL() {
        //verifier si ma coupe est modifiée lors de la modification d'un axe
    }
    // TODO: fnct invalide pour le reste du travail
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
    //juste penser a enlever cette fonction d'ici 
/*    
    public boolean inPanneau(Point p, Panneau panneau){
        assert p != null : "Le point ne peut pas etre invalide.";
        assert panneau != null : "Le panneau ne peut pas etre invalide.";
        int minX = 130;
        int maxX = (int) panneau.getLargeur() + 130;
        int minY = 0;
        int maxY = (int) panneau.getLongueur() - 130;
        return (p.x >= minX && p.x <= maxX) && ((1500 - p.y) >= minY && (1500 - p.y) <= maxY);
    }
    
  */
    // TODO: changer en ajoutant les uuid
    public void AjouterCoupe(Coupe coupe) {
        // verifier les uuid, random et check in vector

        coupes.add(coupe);

    }
    // TODO: changer ça , delete en se basant sur l 'UUID
    public void supprimerCoupe() {
        if(!coupes.isEmpty()) coupes.removeLast();

    }
    

    
    
}
