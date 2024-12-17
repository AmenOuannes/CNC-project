/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;
import equipe06.Domaine.CNC;
import equipe06.Domaine.Utils.ZoneInterditeDTO;
import equipe06.drawing.Afficheur;
import equipe06.gui.MainWindow;
import java.awt.Point;
import java.util.UUID;
import java.util.Vector;
import java.util.Stack;

import equipe06.Domaine.Utils.ElementCoupe;

/**
 *
 * @author ziedd
 */
public class Controleur {
    
    private static Controleur instance; // Instance unique de Controleur
    private CNC cnc;
    private float epaisseurActuelle = 12.7f; 
    private MainWindow mainWindow;
    public boolean suprim = false;
    private Stack<Coupe> undoStack = new Stack<>();
    private Stack<Coupe> redoStack = new Stack<>();  
    
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
    


    //getters
    public CNC getCNC() {
        return cnc;
    }

    public Vector<CoupeDTO> getCoupes() {return cnc.getCoupes();}
    public PanneauDTO getPanneau() {return cnc.getPanneau();}
    public Vector<ZoneInterditeDTO> getZones() {return cnc.getZones();}
//------------------------------------------------------COUPES----------------------------------------------------------
     public void CreerCoupeAxiale(Point p, boolean composante, Point reference) {

        cnc.setOutil_courant(new OutilDTO(new Outil("current",getEpaisseurActuelle())));
        cnc.CreerCoupeAxe(p,Repere.getInstance().convertirEnMmDepuisPixels((int) p.getX()),
                    Repere.getInstance().convertirEnMmDepuisPixels((int) p.getY())
                , composante, reference);
    }

     
    public void SetCoupeBordure(float BordureXValue, float BordureYValue) {
        cnc.setOutil_courant(new OutilDTO(new Outil("current",getEpaisseurActuelle())));
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
    public void supprimerCoupeSurClic(Point point) {
        System.out.println(" cont 1 ");
    cnc.supprimerCoupe(point);
    System.out.println(" supprimer cont 2 ");
    }
    public void CreerCoupeRect(Point origin, Point dest, Point reference) {
        cnc.setOutil_courant(new OutilDTO(new Outil("current",getEpaisseurActuelle())));
        cnc.CreerCoupeRect(origin, dest, reference);
       
    }

    public void CreerCoupeL(Point reference, Point destination) {
        cnc.setOutil_courant(new OutilDTO(new Outil("current",getEpaisseurActuelle())));
        cnc.CreerCoupeL(reference, destination);
    }
    
    public void CreerZoneInterdite(Point origin, Point dest) {
        cnc.CreerZoneInterdite(origin, dest);  
    }

//---------------------------------------------PANNEAU & OUTILS---------------------------------------------------------
    public void SetPanneau(float longueurX, float largeurY, float profondeurZ) {
        if (longueurX <= 0 || largeurY <= 0 || profondeurZ < 0) {
            throw new IllegalArgumentException("Les dimensions doivent être positives.");
        }
        cnc.creerPanneau(longueurX, largeurY, profondeurZ);
    }

    
    public void SetOutil(String nomOutil, float epaisseur) {
        cnc.ajouterOutil(nomOutil, epaisseur);    
    }

    
    // on appelle cette methode lors d'ajouter ou modifier un outil pour que la table des outils fait une mise a jour
 public void mettreAJourTableauOutils() {
    Vector<OutilDTO> outils = this.getOutils(); 
    System.out.println("Nombre d'outils après mise à jour : " + outils.size());
    /*
    for (OutilDTO outil : outils) {
        System.out.println("Outil : " + outil.getNomDTO() + ", Largeur : " + outil.getLargeur_coupeDTO());
    }*/
    mainWindow.afficherOutilsDansTable(outils);
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
    return 12.7f; // 0,5 pouces par défaut
}
    public void setEpaisseurActuelle(float epaisseurPixels) {
    this.epaisseurActuelle = epaisseurPixels;

}

public float getEpaisseurActuelle() {
    return epaisseurActuelle;
}

public void modifierOutil(String nomActuel, String nouveauNom, float nouvelleEpaisseur) {
    // Recherche l'outil par son nom pour obtenir l'UUID
    for (OutilDTO outilDTO : cnc.getOutils()) {
        if (outilDTO.getNomDTO().equals(nomActuel)) {
            // Appel de la méthode ModifierOutil de CNC
            cnc.ModifierOutil(outilDTO.getId(), nouveauNom, nouvelleEpaisseur);

            System.out.println("Outil modifié dans CNC : Nom = " + nouveauNom + ", Largeur = " + nouvelleEpaisseur);

            // Mise à jour des composants graphiques
            mettreAJourTableauOutils();
            mainWindow.mettreAJourComboBoxOutil();
            return; // Quitte la méthode après modification
        }
    }
    System.out.println("Outil non trouvé : " + nomActuel);
}
 public void reset() {
     cnc.reset();
     
 }


// Méthode pour retourner tous les outils
public Vector<OutilDTO> getOutils() {
    return cnc.getOutils(); // Remplacez `cnc` par l'objet qui contient les outils si nécessaire
}


public void modifierCoupeAxiale(float a, Point Ref) {
        cnc.modifierCoupeAxiale(a,Ref);
    }
    public void modifierCoupeOutil(Point Ref) {
       float largeur = epaisseurActuelle;
        cnc.modifierCoupeAxiale(largeur, Ref);
    }

    public void modifierCoupeCarre(float longueur, float largeur, Point Ref) {
        cnc.modifierCoupeCarre(longueur, largeur, Ref);
}
public boolean inPanneau(float x, float y) {
        return cnc.inPanneau(x, y);
    }

    public void EditerRef(Point surCoupe, Point ref) {
        cnc.EditerRef(surCoupe, ref);
    }
    
    public void mettreAJourVue() {
    if (mainWindow != null) {
        mainWindow.mettreAJourComboBoxOutil();
        mainWindow.mettreAJourTableauOutils();
        mainWindow.mettreAJourPanneau();
         float coteGrille = cnc.getCoteGrille();
        mainWindow.mettreAJourGrille(coteGrille);
     
    }
}
      public void setCoteGrille(float cote) {
        cnc.setCoteGrille(cote);
        // Vous pouvez mettre à jour la vue si nécessaire
        mettreAJourVue();
    }

     public void undo() {
        cnc.undo();
    }

    public void redo() {
        cnc.redo();
    }

    public boolean isUndoAvailable() {
        return cnc.isUndoAvailable();
    }

    public boolean isRedoAvailable() {
        return cnc.isRedoAvailable();
    }
    
    public void exporterGCode(String cheminFichier) {
        cnc.exporterGCode(cheminFichier);
    }

    public void saveCNC(String cheminFichier) {
        cnc.saveCNC(cheminFichier);
    }
    
    
    public void setMarge(float nouvelleMarge) {
        if (cnc != null) {
            cnc.setMarge(nouvelleMarge);
        } else {
            System.out.println("CNC non initialisé.");
        }
    }

    public void modifDeplacement(int startX, int startY, int endX, int endY) {
        cnc.modifDeplacement(startX, startY, endX, endY);
    }

    public void import_cnc(String absolutePath) {
        cnc.loadCNC(absolutePath);
    }
}


    
