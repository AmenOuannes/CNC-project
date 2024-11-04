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
    
    
    // cette methode permet au controleur de transmettre la valeur de x a mainwindow et de mettre a jours distancex (jtextframe ou s'affiche la valeur a cote bout modif coupe)
    public void mettreAJourDistanceX(float x) {
    if (mainWindow != null) {
        System.out.println("MainWindow est bien référencée"); // verification console ne peut l'enlever 
        mainWindow.afficherValeurDistanceX(x); // Appeler la méthode de MainWindow pour afficher la distance 
    }else {
        System.out.println("MainWindow est null"); // verification console ne peut l'enlever 
    }
}
/*
il faut transmettre  tout ça dans le MainWindow, c'est lui qui s'occupe de ça

  
    private void transmettreDimensionsPanneau() {
        Afficheur afficheur = new Afficheur(this);
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
    public void creerCoupeAxiale(float axe, boolean composante) {
        Point pointOrigine = new Point(0, 0); 
        Point pointDestination = new Point((int)axe, 0); 
        ElementCoupe elementCoupe = new ElementCoupe( // elle doit etre dans le cnc pas dans controleur
            pointOrigine, pointDestination, 5.0f, 0.3f, axe, composante, 0.0f, 0.0f, "CoupeAxiale", null
        );

        cnc.creerCoupe(elementCoupe);
    }

   
    public void modifierCoupe() {
        // TODO : Implémentation pour modifier une coupe
    }

 
    public void supprimerCoupe(Coupe coupe) {
        this.cnc.supprimerCoupe(coupe);
    }






}
    
    

