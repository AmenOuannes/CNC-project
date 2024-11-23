/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;
import equipe06.Domaine.CNC;
import equipe06.drawing.Afficheur;
import equipe06.gui.MainWindow;
import java.awt.Point;
import java.util.UUID;
import java.util.Vector;

import equipe06.Domaine.Utils.ElementCoupe;

/**
 *
 * @author ziedd
 */
public class Controleur {
    
    private static Controleur instance; // Instance unique de Controleur
    private CNC cnc;
    private float epaisseurActuelle = 2.0f; // Par défaut, en pixels
    private MainWindow mainWindow;
    public boolean suprim = false;
    public static double scaleFactor = 0.25; // Réduit la taille à 25% les dimensions elli hab alihom ell prof kbar donc hatit ell facteur hedha juste tempo bech tawwa matkallaknech
    // Constructeur privé pour empêcher la création directe
    private Controleur() {
        this.cnc = new CNC();
    }

    // Méthode statique pour obtenir l'instance unique
    public static Controleur getInstance() {
        if (instance == null) {
            instance = new Controleur();
        }
        return instance;
    }

    // Méthode pour initialiser la référence à MainWindow
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        //transmettreDimensionsPanneau();
    }
    
    
    // cette methode permet au controleur de transmettre la valeur de x a mainwindow et de mettre a jours distancex (jtextframe ou s'affiche la valeur a cote bout modif coupe)
    /*public void mettreAJourDistanceX(float x) {
    if (mainWindow != null) {
        //System.out.println("MainWindow est bien référencée"); // verification console ne peut l'enlever
        mainWindow.afficherValeurDistanceX(x); // Appeler la méthode de MainWindow pour afficher la distance 
    }else {
        System.out.println("MainWindow est null"); // verification console ne peut l'enlever 
    }
}*/ //TODO: enlever par zied

    //getters
    public Repere getRepere() {return cnc.getRepere();}
    public Vector<CoupeDTO> getCoupes() {return cnc.getCoupes();}
    public PanneauDTO getPanneau() {return cnc.getPanneau();}
//------------------------------------------------------COUPES----------------------------------------------------------
     public void CreerCoupeAxiale(Point p, boolean composante, Point reference) {


        cnc.CreerCoupeAxe(Repere.getInstance().convertirEnMmDepuisPixels((int) p.getX()),
                    Repere.getInstance().convertirEnMmDepuisPixels((int) p.getY())
                , composante, reference);
    }

     
    public void SetCoupeBordure(float BordureXValue, float BordureYValue) {
        cnc.CreerCoupeBordure(BordureXValue, BordureYValue);
    }

     
    //TODO: get the uuid from the click on panneau to use in modifying or deleting coupe
    public UUID getUUID(){
        return UUID.randomUUID();//to change remove by @amen
    }
    // TODO: appel correct
    public void modifierCoupe(float axe) {
        cnc.ModifierCoupe(axe);
    }

    // TODO: corriger appel , faut le UUID
    public void supprimerCoupe() {
       if ( getUUID() != null )
        { cnc.supprimerCoupe(getUUID());
        }

    }
    public void CreerCoupeRect(Point origin, Point dest) {
        cnc.CreerCoupeRect(origin, dest);
    }

    public void CreerCoupeL(Point origin, Point destination) {
        cnc.CreerCoupeL(origin, destination);
    }


//---------------------------------------------PANNEAU & OUTILS---------------------------------------------------------
    public void SetPanneau(float longueurX, float largeurY, float profondeurZ) {
        if (longueurX <= 0 || largeurY <= 0 || profondeurZ <= 0) {
            throw new IllegalArgumentException("Les dimensions doivent être positives.");
        }
        cnc.creerPanneau(longueurX, largeurY, profondeurZ);
    }

    
    public void SetOutil(String nomOutil, float epaisseur) {
        cnc.ajouterOutil(nomOutil, epaisseur);    
    }

    
    // on appelle cette methode lors d'ajouter ou modifier un outil pour que la table des outils fait une mise a jour
    public void mettreAJourTableauOutils() {
        Vector<OutilDTO> outils = cnc.getOutils(); // Récupérer le vecteur d'OutilDTO depuis CNC
        mainWindow.afficherOutilsDansTable(outils); // Mettre à jour la table dans MainWindow
    }
    
    public void supprimerOutil(int index) {
        cnc.supprimerOutilParIndex(index); // Supprime l'outil en fonction de l'index
    }

    public float getEpaisseurOutil(String nomOutil) {
    for (OutilDTO outil : cnc.getOutils()) {
        if (outil.getNomDTO().equals(nomOutil)) {
            return outil.getLargeur_coupeDTO();
        }
    }
    // Retourne une valeur par défaut si l'outil n'est pas trouvé
    return 2.0f; // 2.0f correspond à une épaisseur par défaut
}
    public void setEpaisseurActuelle(float epaisseurPixels) {
    this.epaisseurActuelle = epaisseurPixels;
}


public float getEpaisseurActuelle() {
    return epaisseurActuelle;
}

}
    
    

