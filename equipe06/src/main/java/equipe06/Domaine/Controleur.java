/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;
import equipe06.Domaine.CNC;
import equipe06.drawing.Afficheur;
import equipe06.gui.MainWindow;
import java.awt.Point;
import java.util.Vector;

import equipe06.Domaine.Utils.ElementCoupe;

/**
 *
 * @author ziedd
 */
public class Controleur {
    
    private static Controleur instance; // Instance unique de Controleur
    private CNC cnc;
    private MainWindow mainWindow;
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
/*
il faut transmettre  tout ça dans le MainWindow, c'est lui qui s'occupe de ça

    // Méthode pour transmettre les dimensions de `Panneau` à `MainWindow`
    private void transmettreDimensionsPanneau() {
        Afficheur afficheur = new Afficheur(this);
        //dessinerpanneau de afficheur a faire ici
        // Appel de la méthode de MainWindow pour transmettre les dimensions
        if (mainWindow != null) {
            mainWindow.updatePanneauDimensions(largeurPixels, hauteurPixels);
        }
    }*/
//




    //getters
    public Repere getRepere() {return cnc.getRepere();}
    public Vector<CoupeDTO> getCoupes() {return cnc.getCoupes();}
    public PanneauDTO getPanneau() {return cnc.getPanneau();}

     // Implémentation de la méthode pour créer une coupe axiale - Proposition
    /*public void creerCoupeAxiale(float axe, boolean composante) {
        Point pointOrigine = new Point(0, 0); 
        Point pointDestination = new Point((int)axe, 0); 

        ElementCoupe elementCoupe = new ElementCoupe(
            pointOrigine, pointDestination, 5.0f, 0.3f, axe, composante, 0.0f, 0.0f, "CoupeAxiale", cnc.getOutil_courant()
        );

        cnc.creerCoupe(elementCoupe);
    }*/

   
    public void modifierCoupe() {
        // TODO : Implémentation pour modifier une coupe
    }

 
    public void supprimerCoupe(Coupe coupe) {
        this.cnc.supprimerCoupe(coupe);
    }
}
    
    

