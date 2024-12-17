package equipe06.Domaine;

import equipe06.Domaine.Panneau;
import equipe06.Domaine.Repere;
import equipe06.Domaine.Coupe;
import equipe06.Domaine.*;
import equipe06.Domaine.Utils.ElementCoupe;
import equipe06.Domaine.Utils.ZoneInterdite;
import equipe06.Domaine.Utils.ZoneInterditeDTO;
import org.w3c.dom.css.Rect;
import java.awt.Point;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.Vector;
import java.util.Iterator;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class CNC {
    private Panneau panneau;

    private Vector<Coupe> coupes;
    private Vector<ZoneInterdite> zones;
    private Vector<Outil> outils;
    private Outil outil_courant;
    private final UndoRedoManager undoRedoManager = new UndoRedoManager();
    private float epaisseurActuelle = 12.7f; // Un exemple de valeur par défaut
     private float coteGrille = 200;
    private float marge= 0.5f;
    private int largeurPixelsTable;
    private int hauteurPixelsTable;
      


    public CNC() {
        panneau = new Panneau(1200,1000,10);
        coupes = new Vector <Coupe>();
        zones = new Vector<ZoneInterdite>();
        outils = new Vector<Outil>(12);
        outils.add(new Outil("defaut", 12.7f));
        // Conversion des dimensions de la table CNC en appliquant un facteur d'échelle
        largeurPixelsTable = (int) (Repere.getInstance().convertirEnPixelsDepuisPouces(120));
        hauteurPixelsTable = (int) (Repere.getInstance().convertirEnPixelsDepuisPouces(60));
        
        outil_courant = outils.firstElement();
       undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);


    }


    public float getMarge() {
        return marge;
    }
    
    public void setMarge(float marge) {
        if (marge < 0) {
            throw new IllegalArgumentException("La marge doit être un nombre positif.");
        }
        this.marge = marge;
    }
    
    public void creerPanneau(float longueurX, float largeurY, float profondeurZ) {
        // Création de l'objet Panneau avec les attributs donnés
        this.panneau = new Panneau(longueurX, largeurY, profondeurZ);
        undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);


    }
     public void reset() {
        coupes.clear();  // Vider toutes les coupes
       
    }
    
    public PanneauDTO getPanneau() {
        if (panneau == null) {
            throw new NullPointerException("Le panneau n'a pas encore été créé.");
        }
        return new PanneauDTO(panneau);
    }



    public Vector<CoupeDTO> getCoupes() {
        Vector<CoupeDTO> cDTO = new Vector<CoupeDTO>();
        for (Coupe coupe : coupes) cDTO.add(new CoupeDTO(coupe));
        return cDTO;
    }
    public Vector<ZoneInterditeDTO> getZones() {
        Vector<ZoneInterditeDTO> zDTO = new Vector<ZoneInterditeDTO>();
        for (ZoneInterdite z : zones) zDTO.add(new ZoneInterditeDTO(z));
        return zDTO;
    }
    //-------------------------------------------------OUTILS--------------------------------------------
    // TODO fix redundancy
    public void ajouterOutil(String nom, float largeurCoupe){
        Outil outil = new Outil(nom, largeurCoupe);
        if (outils.size() < 12  && !outils.contains(outil)){
            
            outils.add(outil);
            System.out.println("Outil ajouté avec succès : " + outil); //remove @zied
           undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);

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
    // Mochkla houni lezemch nhot feha save state sinon ywali
    //undo redo b deux clicks khater kol ma naaytou l coupe naaytou set outil
    //ywali aana 2 etats ala kol ocupe
    public void setOutil_courant(OutilDTO outil_courantDTO) {

        // Recherche l'outil correspondant dans la liste des outils
        /*for (Outil outil : outils) {
            if (outil.getNom().equals(outil_courantDTO.getNomDTO())) {
                this.outil_courant = outil;
                System.out.println("Outil courant mis à jour : " + outil.getNom());
                return;
            }
        }*/
         System.out.println("Avant modification : " + this.outil_courant);
       

        this.outil_courant.setLargeur_coupe(outil_courantDTO.getLargeur_coupeDTO()) ;
        System.out.println("Après modification : " + this.outil_courant);
      



}

    //TODO : rendre cette boucle en try catch
    // TODO check if outil est outilCourant
    public void supprimerOutilParIndex(int index) {
    if (index >= 0 && index < outils.size()) {
        // Sauvegarder l'état actuel avant la suppression
    undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);

        
        Outil outil = outils.remove(index);

        // Vérifiez si l'outil supprimé est l'outil courant
        if (outil_courant != null && outil_courant.equals(outil)) {
            outil_courant = !outils.isEmpty() ? outils.get(0) : null;
        }

        System.out.println("Outil supprimé avec succès.");
        Controleur.getInstance().mettreAJourVue(); // Mise à jour de la vue
       undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);

    } else {
        System.out.println("Index invalide. Impossible de supprimer l'outil.");
    }
}


    //amen
    public void ModifierOutil(UUID uuid, String NewName, float NewLargeur){
         

        for(Outil outil : outils){
            if(outil.getId().equals(uuid)){
                if(outil.getNom()!= NewName)
                    outil.setNom(NewName);
                if(outil.getLargeur_coupe() != NewLargeur)
                    outil.setLargeur_coupe(NewLargeur);
            }
        }
    undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);

      

    }
    



    //-------------------------------------------------COUPES--------------------------------------------------------
    //Katia
    public void CreerCoupeL(Point referencePoint, Point pointDestination){
        //TODO coupe en L, attribut extraits du controleur
        assert referencePoint != null;
        assert pointDestination != null;
        ElementCoupe e  = new ElementCoupe( referencePoint, pointDestination,panneau.getProfondeur()+marge,marge,0,false,0.0f,0.0f,"L",outil_courant.getLargeur_coupe());
        Vector<UUID> CoupesDeReferences = surCoupes(referencePoint);
        CoupeL coupe = new CoupeL(e, CoupesDeReferences);
        if (CoupeValide(coupe, panneau)) { // Vérifie si la coupe est valide avant l'ajout
            AjouterCoupe(coupe);
            System.out.println("Coupe en L créée avec succès.");
        } else {
            System.out.println("La coupe en L est invalide et n'a pas été ajoutée.");
        }
    }
    //Amen
    public void CreerCoupeRect(Point Origine, Point Destination, Point reference){
        //TODO coupe rect, attribut extraits du controleur float
        assert (Origine != null);
        assert (Destination != null);
        float BordureX = (Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(Origine.x-Destination.x)));
        float BordureY = (Repere.getInstance().convertirEnMmDepuisPixels(Math.abs(Origine.y-Destination.y)));
        ElementCoupe e = new ElementCoupe(
                Origine, Destination, panneau.getProfondeur()+marge,marge,
                0,false,BordureX, BordureY,"Rect",outil_courant.getLargeur_coupe());
        Vector<UUID> CoupesDeReferences = surCoupes(reference);
        CoupeRec coupe = new CoupeRec(e, CoupesDeReferences ,reference);
        if (CoupeValide(coupe, panneau)) { // Vérifie si la coupe est valide avant l'ajout
            AjouterCoupe(coupe);
            System.out.println("Coupe rectangulaire créée avec succès.");
        } else {
            System.out.println("La coupe rectangulaire est invalide et n'a pas été ajoutée.");
        }

    }


    public void CreerCoupeBordure(float x, float y){

        float bordureX = x;
        float bordureY = y;
        
        int bordureXPx = Repere.getInstance().convertirEnPixelsDepuisMm(bordureX);
        int bordureYPx = Repere.getInstance().convertirEnPixelsDepuisMm(bordureY);
        
        int longueurOriginalePx = Repere.getInstance().convertirEnPixelsDepuisMm(panneau.getLongueur());
        int largeurOriginalePx = Repere.getInstance().convertirEnPixelsDepuisMm(panneau.getLargeur());
        
        int xOrigine = (longueurOriginalePx - bordureXPx) / 2;
        int yOrigine = (int) Repere.getInstance().convertirEnPixelsDepuisPouces(60) - largeurOriginalePx + (largeurOriginalePx - bordureYPx) / 2;
        
        // Calcul du point de destination basé sur l'origine et les dimensions
        int xDestination = xOrigine + bordureXPx; // Point à l'extrémité droite
        int yDestination = yOrigine + bordureYPx; // Point à l'extrémité basse
        
        Point pointOrigine = new Point(xOrigine, yOrigine);
        Point pointDestination = new Point(xDestination, yDestination);
        
        ElementCoupe e = new ElementCoupe(
                pointOrigine, pointDestination, panneau.getProfondeur()+marge,marge, 0, false, bordureX, bordureY, "Bordure", outil_courant.getLargeur_coupe() );
        
        CoupeRec coupe = new CoupeRec(e);
        coupes.add(coupe);
      undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);


        System.out.println("Coupe bordure enregistrée.");
        
    }
    //TODO --------------------------------------------------------------------------------
    public void CreerZoneInterdite(Point Origine, Point Destination){
        
        assert (Origine != null);
        assert (Destination != null);
        ZoneInterdite z = new ZoneInterdite(Origine, Destination);
        zones.add(z);

    }
    
    public void CreerCoupeAxe(Point Destination, float x, float y, boolean composante, Point reference) {
        
        assert reference != null : "Le point de référence ne doit pas être null.";
        assert Destination != null : "Le point de référence ne doit pas être null.";
        assert x >= 0 : "L'axe x doit être positif.";
        assert y >= 0 : "L'axe y doit être positif.";
   
        ElementCoupe e = null;
        
        
        if (composante == true)
        {
            
         e = new ElementCoupe(
                reference, Destination, panneau.getProfondeur()+marge,marge,
                 x, composante, 0.0f, 0.0f, "V", outil_courant.getLargeur_coupe());
        }
        else{
            
             e = new ElementCoupe(
            reference, Destination, panneau.getProfondeur()+marge,marge,
                     y, composante, 0.0f, 0.0f, "H", outil_courant.getLargeur_coupe());
        }
        // Affichage des coordonnées du point de destination
        System.out.println("Point de destination créé : " + Destination.toString());
        
        Vector<UUID> CoupesDeReferences = surCoupes(reference);
        CoupeAxe ma_coupe = new CoupeAxe(e, CoupesDeReferences ,reference);
        if (CoupeValide(ma_coupe, panneau))
            {
                AjouterCoupe(ma_coupe);
            }
        else {
            System.out.println("invalide");
        }
        
    }
    //non pour le moment
    // TODO: Rendre modifier apte a modifier toute coupe possible
    // cette fonction fait appel au divers coupes
    public void ModifierCoupe(float axe) {
            
            CoupeAxe coupe = (CoupeAxe) coupes.get(0);
            coupe.setAxe(axe);
          undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);



    }
    
    public void ModifierCoupeRectL() {
        //verifier si ma coupe est modifiée lors de la modification d'un axe
       undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);

    }
    public boolean IsThere(Vector<UUID> uuids, String s){

        for(Coupe coupe : coupes){
            if(uuids.contains(coupe.getUUID()))
                if(Objects.equals(s, coupe.getTypeCoupe()))
                    return true;
        }
        return false;
    }
    public boolean CoupeValide(Coupe coupe, Panneau panneau) {

        assert coupe != null : "La coupe ne peut pas etre invalide.";
        assert panneau != null : "Le panneau ne peut pas être invalide.";

        for (ZoneInterdite z : zones) {
            if(z.surZoneInterdite(coupe)) return false;
        }
        if((Objects.equals(coupe.getTypeCoupe(), "H") || Objects.equals(coupe.getTypeCoupe(), "V")) ){
            CoupeAxe c = (CoupeAxe) coupe;
            Vector<UUID> uuids = surCoupes(c.getReference());
            Vector<UUID> uuids_coin = surCoin(c.getReference());
            System.out.print(uuids);
            System.out.print(uuids_coin);
            System.out.print(c.getElement().getPointDestination());
            float x = Repere.getInstance().convertirEnMmDepuisPixels(c.getElement().getPointDestination().x);
            float y = Repere.getInstance().convertirEnMmDepuisPixels(c.getElement().getPointDestination().y);
            float axe = Repere.getInstance().convertirEnMmDepuisPixels(c.getElement().getAxe());
            if ((panneau.inPanneau(c.getElement().getAxe(), y) && c.getTypeCoupe() == "V") || (panneau.inPanneau(x, c.getElement().getAxe()) && c.getTypeCoupe() == "H"))
            {
                System.out.println("hethi mrgla");
                if(uuids.size() >= 2 || panneau.surPanneau(c.getReference()) || 
                    (uuids.size()== 1 && (IsThere(uuids_coin, "Bordure") || IsThere(uuids_coin, "L") || IsThere(uuids_coin, "Rect"))) ){
                System.out.println("parfait");
                return true;
            }
            }
            
            //if(!c.getMyRef().isEmpty())
                
                //return true;
            //else if (panneau.surPanneau(c.getReference())) {
                //System.out.println(" click sur Panneau");
                //return true;
            //}
        }else if (Objects.equals(coupe.getTypeCoupe(), "Rect") || Objects.equals(coupe.getTypeCoupe(), "Bordure")) {
            CoupeRec c = (CoupeRec) coupe;
            float origineX = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointOrigine().x);
            float origineY = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointOrigine().y);
            float destinationX = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointDestination().x);
            float destinationY = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointDestination().y);

            // Vérifier que origine et destination sont dans le panneau, et que la référence est sur le panneau
            Vector<UUID> uuids = surCoupes(c.getReference());
            Vector<UUID> uuids_coin = surCoin(c.getReference());

            if (panneau.inPanneau(origineX, origineY) && 
                    panneau.inPanneau(destinationX, destinationY))
            {
                System.out.print(uuids);
                System.out.print(panneau.surPanneau(c.getReference()));
                if(uuids.size() >= 2 || panneau.surCoins(c.getReference()) || 
                    (uuids.size()== 1 && (IsThere(uuids_coin, "Bordure") || IsThere(uuids_coin, "L") || IsThere(uuids_coin, "Rect"))) ||
                        (panneau.surPanneau(c.getReference()) && uuids.size() >= 1)){
                System.out.println("parfait");
                return true;
            }
            }
          


        } else if (Objects.equals(coupe.getTypeCoupe(), "L")) {
            CoupeL c = (CoupeL) coupe;
            // Extraire les coordonnées des points
            float destinationX = Repere.getInstance().convertirEnMmDepuisPixels(c.getPointDestination().x);
            float destinationY = Repere.getInstance().convertirEnMmDepuisPixels(c.getPointDestination().y);
            Vector<UUID> uuids = surCoupes(c.getPointOrigine());
            Vector<UUID> uuids_coin = surCoin(c.getPointOrigine());

            if (panneau.inPanneau(destinationX, destinationY))
            {
                if(uuids.size() >= 2 || panneau.surCoins(c.getPointOrigine()) || 
                    (uuids.size()== 1 && (IsThere(uuids_coin, "Bordure") || IsThere(uuids_coin, "L") || IsThere(uuids_coin, "Rect"))) ||
                        (panneau.surPanneau(c.getPointOrigine()) && uuids.size() >= 1)){
                System.out.println("parfait");
                return true;
            }
            }

        }
        return false;
    } 

    public void AjouterCoupe(Coupe coupe) {
       
        assert coupe != null : "La coupe ne doit pas être null.";
        // vector of uuids
        Vector<UUID> uuids = new Vector<UUID>();
        for(Coupe c: coupes){
            uuids.add(coupe.getUUID());
        }
        do{
            coupe.setUUID(UUID.randomUUID());
        }while(uuids.contains(coupe.getUUID()));

        coupes.add(coupe);
    undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);

        getCoupes();
        System.out.print("coupe enregistrée\n");


    }
 

    public Vector<UUID> surCoupes(Point reference) {
        Vector<UUID> uuids = new Vector<>();
        //if (panneau.inPanneau(Repere.getInstance().convertirEnMmDepuisPixels(reference.x), Repere.getInstance().convertirEnMmDepuisPixels(reference.y))) {
            float y = Repere.getInstance().convertirEnMmDepuisPixels(reference.y);
            float x = Repere.getInstance().convertirEnMmDepuisPixels(reference.x);

            for (Coupe c : coupes) {
                switch (c.getTypeCoupe()) {
                    case "V":
                        CoupeAxe axiale = (CoupeAxe) c;
                        if ((axiale.getAxe() - 100 <= x) &&
                                (x <= axiale.getAxe() + 100)) {
                            uuids.add(c.getUUID());
                            System.out.println("click sur coupe verticale");
                        }
                        break;
                    case "H":
                        axiale = (CoupeAxe) c;
                        if ((axiale.getAxe() - 100 <= y) &&
                                (y <= axiale.getAxe() + 100)) {
                            uuids.add(c.getUUID());
                            System.out.println("click sur coupe Horizontale");
                        }
                        break;
                    case "Rect", "Bordure":
                        CoupeRec coupeRect = (CoupeRec) c;
                        Point origineRect = coupeRect.getPointOrigine();
                        Point destinationRect = coupeRect.getPointDestination();
                        // Le point doit correspondre à un des 4 coins du rectangle
                        boolean ligne1 = ((x >= Repere.getInstance().convertirEnMmDepuisPixels(origineRect.x) - 100) &&(x <= Repere.getInstance().convertirEnMmDepuisPixels(destinationRect.x) + 100))
                                && (((y >= Repere.getInstance().convertirEnMmDepuisPixels(origineRect.y) - 100) && (y <= Repere.getInstance().convertirEnMmDepuisPixels(origineRect.y) + 100)) || 
                                ((y >= Repere.getInstance().convertirEnMmDepuisPixels(destinationRect.y) - 100) && (y <= Repere.getInstance().convertirEnMmDepuisPixels(destinationRect.y) + 100)));

                        boolean ligne2 = ((y >= Repere.getInstance().convertirEnMmDepuisPixels(origineRect.y) - 100) &&(y <= Repere.getInstance().convertirEnMmDepuisPixels(destinationRect.y) + 100))
                                && (((x >= Repere.getInstance().convertirEnMmDepuisPixels(origineRect.x) - 100) && (x <= Repere.getInstance().convertirEnMmDepuisPixels(origineRect.x) + 100)) || 
                                ((x >= Repere.getInstance().convertirEnMmDepuisPixels(destinationRect.x) - 100) && (x <= Repere.getInstance().convertirEnMmDepuisPixels(destinationRect.x) + 100)));

                        
                        if (ligne1 || ligne2) {
                            System.out.println("click sur coupe rectangulaire");
                            uuids.add(c.getUUID());
                        }
                        break;
                    case "L":
                        CoupeL coupeL = (CoupeL) c;
                        Point origineL = coupeL.getPointOrigine();
                        Point destinationL = coupeL.getPointDestination();
                        // Le point doit correspondre à un des 4 coins du rectangle
                        boolean lignee1 = ((x >= Repere.getInstance().convertirEnMmDepuisPixels(origineL.x) - 100) &&(x <= Repere.getInstance().convertirEnMmDepuisPixels(destinationL.x) + 100))
                                && (((y >= Repere.getInstance().convertirEnMmDepuisPixels(origineL.y) - 100) && (y <= Repere.getInstance().convertirEnMmDepuisPixels(origineL.y) + 100)) || 
                                ((y >= Repere.getInstance().convertirEnMmDepuisPixels(destinationL.y) - 100) && (y <= Repere.getInstance().convertirEnMmDepuisPixels(destinationL.y) + 100)));

                        boolean lignee2 = ((y >= Repere.getInstance().convertirEnMmDepuisPixels(origineL.y) - 100) &&(y <= Repere.getInstance().convertirEnMmDepuisPixels(destinationL.y) + 100))
                                && (((x >= Repere.getInstance().convertirEnMmDepuisPixels(origineL.x) - 100) && (x <= Repere.getInstance().convertirEnMmDepuisPixels(origineL.x) + 100)) || 
                                ((x >= Repere.getInstance().convertirEnMmDepuisPixels(destinationL.x) - 100) && (x <= Repere.getInstance().convertirEnMmDepuisPixels(destinationL.x) + 100)));

                        
                        if (lignee1 || lignee2) {
                            System.out.println("click sur coupe rectangulaire");
                            uuids.add(c.getUUID());
                        }
                        break;

                    default:
                        System.out.println("Type de coupe non pris en charge : " + c.getTypeCoupe());
                        break;

                }
            }

        //}
        return uuids;
    }
    
    public Vector<UUID> surCoin(Point reference) {
        Vector<UUID> uuids = new Vector<>();
        if (panneau.inPanneau(Repere.getInstance().convertirEnMmDepuisPixels(reference.x), Repere.getInstance().convertirEnMmDepuisPixels(reference.y))) {
            float y = Repere.getInstance().convertirEnMmDepuisPixels(reference.y);
            float x = Repere.getInstance().convertirEnMmDepuisPixels(reference.x);

            for (Coupe c : coupes) {
                switch (c.getTypeCoupe()) {
                    case "Rect", "Bordure":
                        CoupeRec coupeRect = (CoupeRec) c;
                        Point origineRect = coupeRect.getPointOrigine();
                        Point destinationRect = coupeRect.getPointDestination();
                        // Le point doit correspondre à un des 4 coins du rectangle
                        boolean Coin1 = Math.abs(x - Repere.getInstance().convertirEnMmDepuisPixels(origineRect.x)) <= 100
                                && Math.abs(y - Repere.getInstance().convertirEnMmDepuisPixels(origineRect.y)) <= 100;

                        boolean Coin2 = Math.abs(x - Repere.getInstance().convertirEnMmDepuisPixels(origineRect.x)) <= 100
                                && Math.abs(y - Repere.getInstance().convertirEnMmDepuisPixels(destinationRect.y)) <= 100;

                        boolean Coin3 = Math.abs(x - Repere.getInstance().convertirEnMmDepuisPixels(destinationRect.x)) <= 100
                                && Math.abs(y - Repere.getInstance().convertirEnMmDepuisPixels(origineRect.y)) <= 100;

                        boolean Coin4 = Math.abs(x - Repere.getInstance().convertirEnMmDepuisPixels(destinationRect.x)) <= 100
                                && Math.abs(y - Repere.getInstance().convertirEnMmDepuisPixels(destinationRect.y)) <= 100;

                        if (Coin1 || Coin2 || Coin3 || Coin4) {
                            System.out.println("click sur coupe rectangulaire");
                            uuids.add(c.getUUID());
                        }
                        break;
                    case "L":
                        CoupeL coupeL = (CoupeL) c;
                        Point origineL = coupeL.getPointOrigine();
                        Point destinationL = coupeL.getPointDestination();
                        // Le point doit correspondre à un des 4 coins du rectangle
                        boolean CoinL1 = Math.abs(x - Repere.getInstance().convertirEnMmDepuisPixels(origineL.x)) <= 100
                            && Math.abs(y - Repere.getInstance().convertirEnMmDepuisPixels(origineL.y)) <= 100;

                         boolean CoinL2 = Math.abs(x - Repere.getInstance().convertirEnMmDepuisPixels(origineL.x)) <= 100
                            && Math.abs(y - Repere.getInstance().convertirEnMmDepuisPixels(destinationL.y)) <= 100;

                        boolean CoinL3 = Math.abs(x - Repere.getInstance().convertirEnMmDepuisPixels(destinationL.x)) <= 100
                            && Math.abs(y - Repere.getInstance().convertirEnMmDepuisPixels(origineL.y)) <= 100;

                        boolean CoinL4 = Math.abs(x - Repere.getInstance().convertirEnMmDepuisPixels(destinationL.x)) <= 100
                            && Math.abs(y - Repere.getInstance().convertirEnMmDepuisPixels(destinationL.y)) <= 100;

                        if (CoinL1 || CoinL2 || CoinL3 || CoinL4) {
                        System.out.println("click sur coupe L");
                        uuids.add(c.getUUID());
                        }
                        break;

                    default:
                        System.out.println("Type de coupe non pris en charge : " + c.getTypeCoupe());
                        break;

                }
            }

        }
        return uuids;
    }


    
   public void supprimerCoupe(Point point) {
    try {
        // Récupérer les UUIDs associés au point
        Vector<UUID> uuids = surCoupes(point);
        System.out.println("UUIDs retournés par surCoupes : " + uuids);
         

        // Parcourir la liste des coupes
        for (int i = 0; i < coupes.size(); i++) {
            UUID currentUUID = coupes.get(i).getUUID(); // UUID actuel de la coupe
            System.out.println("UUID de la coupe actuelle : " + currentUUID);

            // Vérifier si le UUID de la coupe actuelle est dans les UUIDs retournés
            if (uuids.contains(currentUUID)) {
                for(Coupe coupe :coupes) {
                    switch (coupe.getTypeCoupe()) {
                        case "V", "H":
                            CoupeAxe cut =(CoupeAxe) coupe;
                                Vector<UUID> df = surCoupes(cut.getReference());
                                if(df.contains(currentUUID)) {
                                    coupe.setValide(true);
                                }

                            break;
                        case "Rect":
                            CoupeRec cutR =(CoupeRec) coupe;
                            Vector<UUID> dr = surCoupes(cutR.getReference());
                            if(dr.contains(currentUUID)) {
                                coupe.setValide(true);
                            }
                            break;
                        case "L":
                            CoupeL cutL =(CoupeL) coupe;
                            Vector<UUID> dl = surCoupes(cutL.getPointOrigine());
                            if(dl.contains(currentUUID)) {
                                coupe.setValide(true);
                            }
                            break;

                        default:
                            System.out.println("Type de coupe non pris en charge : ");
                            break;

                    }
                }

                System.out.println("UUID trouvé, suppression en cours...");
                coupes.remove(i);
                System.out.println("Coupe supprimée avec succès.");
                 return; 
            }
        }
         for (Iterator<Coupe> iterator = coupes.iterator(); iterator.hasNext();) {
            Coupe coupe = iterator.next();
            if (uuids.contains(coupe.getUUID())) {
              
                iterator.remove();
                System.out.println("Coupe supprimée : " + coupe.getUUID());
                return;
            }
         undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);

        }

        // Si aucune coupe n'a été supprimée
        System.out.println("Aucune coupe correspondante trouvée pour le point donné.");
    } catch (IndexOutOfBoundsException e) {
        System.out.println("Erreur : problème avec l'accès à la liste des coupes.");
    } catch (Exception e) {
        System.out.println("Erreur inattendue : " + e.getMessage());
    }
   }
    public void modifierCoupeCarre(float longueur, float largeur, Point ref) {
        if(surCoupes(ref).isEmpty()) return;
        UUID uuid = this.surCoupes(ref).firstElement();
        Coupe cut = null ;

        int TranslationX=0, TranslationY=0;
        for(Coupe coupe : coupes) {
          if(uuid.equals(coupe.getUUID()))
              cut = coupe;

        }
        if(cut==null) return;
        if(cut.getTypeCoupe()=="Rect")
        {   CoupeRec ma_coupe = (CoupeRec) cut;
            Point OldDestination = ma_coupe.getPointDestination();
            int x, y;
            if(ma_coupe.getPointOrigine().getX()>ma_coupe.getPointDestination().getX())
                x = ma_coupe.getPointOrigine().x - Repere.getInstance().convertirEnPixelsDepuisMm(longueur);
            else
                x = ma_coupe.getPointOrigine().x + Repere.getInstance().convertirEnPixelsDepuisMm(longueur);
            if(ma_coupe.getPointOrigine().getY()>ma_coupe.getPointDestination().getY())
                y = ma_coupe.getPointOrigine().y - Repere.getInstance().convertirEnPixelsDepuisMm(largeur) ;
            else
                y = ma_coupe.getPointOrigine().y + Repere.getInstance().convertirEnPixelsDepuisMm(largeur) ;
            ma_coupe.setBordureX(longueur);
            ma_coupe.setBordureY(largeur);
            ma_coupe.setPointDestination(new Point(x, y));
            if (CoupeValide(ma_coupe, panneau))
            {
                ma_coupe.setValide(false);
            }
            else{
                ma_coupe.setValide(true);
            }
            TranslationX = x-OldDestination.x;
            TranslationY = y-OldDestination.y;
            System.out.println("TranslationX : " + TranslationX);
            System.out.println("TranslationY : " + TranslationY);
            modifierEnCascade(ma_coupe.getUUID(), TranslationX,TranslationY);
          undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);

        }
        else if (cut.getTypeCoupe()=="L") {
            CoupeL ma_coupe = (CoupeL) cut;
            Point OldDestination = ma_coupe.getPointDestination();
            int x, y;
            if (ma_coupe.getPointOrigine().getX() > ma_coupe.getPointDestination().getX())
                x = ma_coupe.getPointOrigine().x - Repere.getInstance().convertirEnPixelsDepuisMm(longueur);
            else
                x = ma_coupe.getPointOrigine().x + Repere.getInstance().convertirEnPixelsDepuisMm(longueur);
            if (ma_coupe.getPointOrigine().getY() > ma_coupe.getPointDestination().getY())
                y = ma_coupe.getPointOrigine().y - Repere.getInstance().convertirEnPixelsDepuisMm(largeur);
            else
                y = ma_coupe.getPointOrigine().y + Repere.getInstance().convertirEnPixelsDepuisMm(largeur);
            ma_coupe.setPointDestination(new Point(x, y));
            if (CoupeValide(ma_coupe, panneau))
            {
                ma_coupe.setValide(false);
            }
            else{
                ma_coupe.setValide(true);
            }
            TranslationX = x-OldDestination.x;
            TranslationY = y- OldDestination.y;
            modifierEnCascade(ma_coupe.getUUID(), TranslationX,TranslationY);
        }





    }
    //TODO remove!!!!
    /*public void modifierCoupeAxiale(Point p, float largeur){
        if(surCoupes(p).isEmpty()) return;
        UUID uuid = this.surCoupes(p).firstElement();
        Coupe ma_coupe = null ;
        int translationX=0, translationY=0;
        for(Coupe coupe : coupes) {
          if(uuid.equals(coupe.getUUID()))
        ma_coupe = (CoupeAxe) coupe;}
        ma_coupe.setOutil(largeur);
        if (CoupeValide(ma_coupe, panneau))
            {
                ma_coupe.setValide(false);
            }
            else{
                ma_coupe.setValide(true);
            }
    }*/
    public void modifierCoupeAxiale(float a, Point p) {
        if(surCoupes(p).isEmpty()) return;
        UUID uuid = this.surCoupes(p).firstElement();
        CoupeAxe ma_coupe = null ;
        int translationX=0, translationY=0;
        for(Coupe coupe : coupes) {
          if(uuid.equals(coupe.getUUID()))
                ma_coupe=(CoupeAxe) coupe;
        }
        if(ma_coupe==null) return;
        if(ma_coupe.getTypeCoupe()=="H"){
            float ymax = Repere.getInstance().convertirEnMmDepuisPixels(Repere.getInstance().convertirEnPixelsDepuisPouces(60));
            int yInitial = Repere.getInstance().convertirEnPixelsDepuisMm(ma_coupe.getAxe());
            ma_coupe.setAxeRelatif(Math.abs(ma_coupe.getReference().y-ymax)<=1250, panneau, a);
            translationY = Repere.getInstance().convertirEnPixelsDepuisMm(ma_coupe.getAxe())-yInitial;


        }
        else if(ma_coupe.getTypeCoupe()=="V"){
            int xInitial = Repere.getInstance().convertirEnPixelsDepuisMm(ma_coupe.getAxe());
            ma_coupe.setAxeRelatif(ma_coupe.getReference().x!=0, panneau, a);
            translationX = Repere.getInstance().convertirEnPixelsDepuisMm(ma_coupe.getAxe())-xInitial;

        }
        if (CoupeValide(ma_coupe, panneau))
            {
                ma_coupe.setValide(false);
            }
            else{
                ma_coupe.setValide(true);
            }
        System.out.println("went here");
        modifierEnCascade(ma_coupe.getUUID(), translationX, translationY);
    undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);

        System.out.println("arrived");
    }

    public void modifDeplacement(int startX, int startY, int endX, int endY) {
        Point start = new Point(startX, startY);
        Point end = new Point(endX, endY);
        Vector<UUID> surCoupe = this.surCoupes(start);
        if(surCoupe.isEmpty()) return;
        UUID uuid = surCoupe.get(0);
        Coupe coupeDéplacée = null;
        for(Coupe coupe : coupes) {
            if(uuid.equals(coupe.getUUID()))
                coupeDéplacée= coupe;
        }
        switch(coupeDéplacée.getTypeCoupe()){
            case "L":
                CoupeL L = (CoupeL) coupeDéplacée;
                L.setPointDestination(end);
                modifierEnCascade(uuid,endX-startX,endY-startY);
                L.setValide(!CoupeValide(L, panneau));

                break;
            case "Rect":
                CoupeRec R = (CoupeRec) coupeDéplacée;

                R.setPointOrigine(end);
                int translationX = endX-startX;
                int translationY = endY-startY;
                Point newDest = new Point(R.getPointDestination().x+translationX, R.getPointDestination().y+translationY);
                R.setPointDestination(newDest);
                modifierEnCascade(uuid,translationX,translationY);
                R.setValide(!CoupeValide(R, panneau));


                break;
            case "H":
                CoupeAxe H = (CoupeAxe) coupeDéplacée;
                H.setAxe(Repere.getInstance().convertirEnMmDepuisPixels(endY));
                if(CoupeValide(H, panneau) && inPanneau(Repere.getInstance().convertirEnMmDepuisPixels(endX),Repere.getInstance().convertirEnMmDepuisPixels(endY) ))
                {
                    H.setValide(false);
                }
                else{
                    H.setValide(true);
                }
                //H.setValide(!CoupeValide(H, panneau));
                //H.setValide(!inPanneau(Repere.getInstance().convertirEnMmDepuisPixels(endX),Repere.getInstance().convertirEnMmDepuisPixels(endY) ));
                modifierEnCascade(uuid,endX-startX,endY-startY);

                break;
            case "V":
                CoupeAxe V = (CoupeAxe) coupeDéplacée;
                V.setAxe(Repere.getInstance().convertirEnMmDepuisPixels(endX));
                //V.setValide(!inPanneau(Repere.getInstance().convertirEnMmDepuisPixels(endX),Repere.getInstance().convertirEnMmDepuisPixels(endY) ));
                //V.setValide(!CoupeValide(V, panneau));
                if(CoupeValide(V, panneau) && inPanneau(Repere.getInstance().convertirEnMmDepuisPixels(endX),Repere.getInstance().convertirEnMmDepuisPixels(endY) ))
                {
                    V.setValide(false);
                }
                else{
                    V.setValide(true);
                }
                modifierEnCascade(uuid,endX-startX,endY-startY);

                break;


        }
        undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);

    }

    public void modifierEnCascade(UUID uuid, int X, int Y) {
        for (Coupe coupe : coupes) {
            switch (coupe.getTypeCoupe()) {
                case "V":
                    CoupeAxe cut = (CoupeAxe) coupe;
                    if (cut.getMyRef().contains(uuid)) {
                        cut.setAxe(cut.getAxe() + X);
                        modifierEnCascade(cut.getUUID(), X, Y);
                        
                        if (CoupeValide(cut, panneau))
                        {
                            cut.setValide(false);
                        }
                        else{
                            cut.setValide(true);
                        }
                    }
                    break;
                case "H":
                    CoupeAxe cutH = (CoupeAxe) coupe;
                    if (cutH.getMyRef().contains(uuid)) {
                        cutH.setAxe(cutH.getAxe() + Y);
                        modifierEnCascade(cutH.getUUID(), X, Y);
                        if (CoupeValide(cutH, panneau))
                        {
                            cutH.setValide(false);
                        }
                        else{
                            cutH.setValide(true);
                        }
                    }
                    
                    break;
                case "Rect":
                    CoupeRec cutRec = (CoupeRec) coupe;
                    if (cutRec.getMyRef().contains(uuid)) {
                        cutRec.setPointDestination(new Point(cutRec.getPointDestination().x + X,
                                cutRec.getPointDestination().y + Y));
                        cutRec.setPointOrigine(new Point(cutRec.getPointOrigine().x + X,
                                cutRec.getPointOrigine().y + Y));
                        cutRec.setPointReference(new Point(cutRec.getReference().x + X,
                                cutRec.getReference().y + Y));
                        modifierEnCascade(cutRec.getUUID(), X, Y);
                        if (CoupeValide(cutRec, panneau))
                        {
                            cutRec.setValide(false);
                        }
                        else{
                            cutRec.setValide(true);
                        }
                    }
                    

                    break;
                case "L":
                    CoupeL cutL = (CoupeL) coupe;
                    if (cutL.getMyRef().contains(uuid)) {
                        cutL.setPointDestination(new Point(cutL.getPointDestination().x + X,
                                cutL.getPointDestination().y + Y));
                        cutL.setPointOrigine(new Point(cutL.getPointOrigine().x + X,
                                cutL.getPointOrigine().y + Y));

                        modifierEnCascade(cutL.getUUID(), X, Y);
                    }
                    if (CoupeValide(cutL, panneau))
                        {
                            cutL.setValide(false);
                        }
                        else{
                            cutL.setValide(true);
                        }
                    

                    break;
            }
        }
    }

 public boolean inPanneau(float x, float y){
        return panneau.inPanneau(x,y);
 }


    public void EditerRef(Point surCoupe, Point ref) {
        int X,Y;
        if(surCoupes(surCoupe).isEmpty()) return;
        UUID uuid = surCoupes(surCoupe).firstElement();
        Coupe ma_coupe = null ;
        for(Coupe coupe : coupes) {
          if(uuid.equals(coupe.getUUID()))
                ma_coupe= coupe;
        }
        if(ma_coupe==null) return;
        switch (ma_coupe.getTypeCoupe()) {
            case "V", "H":

                CoupeAxe cut = (CoupeAxe) ma_coupe;
                X = ref.x-cut.getReference().x;
                Y = ref.y-cut.getReference().y;
                   cut.ChangeReference(ref);
                if(ma_coupe.getTypeCoupe()=="H") {
                    cut.setAxe(cut.getAxe()+Y);
                    modifierEnCascade(cut.getUUID(), X, Y);
                }
                else {
                    cut.setAxe(cut.getAxe()+X);
                    modifierEnCascade(cut.getUUID(), X, Y);
                }
                if (CoupeValide(cut, panneau))
                        {
                            cut.setValide(false);
                        }
                        else{
                            cut.setValide(true);
                        }
                    
                break;

            case "Rect":
                CoupeRec cutRec = (CoupeRec) ma_coupe;
                X = ref.x-cutRec.getReference().x;
                Y = ref.y-cutRec.getReference().y;
                cutRec.setPointReference(ref);
                cutRec.setPointDestination(new Point(cutRec.getPointDestination().x + X,
                        cutRec.getPointDestination().y + Y));
                cutRec.setPointOrigine(new Point(cutRec.getPointOrigine().x + X,
                        cutRec.getPointOrigine().y + Y));
                modifierEnCascade(cutRec.getUUID(), X, Y);
                if (CoupeValide(cutRec, panneau))
                        {
                            cutRec.setValide(false);
                        }
                        else{
                            cutRec.setValide(true);
                        }
                    

                break;
            case "L":
                CoupeL cutL = (CoupeL) ma_coupe;
                X = ref.x-cutL.getPointOrigine().x;
                Y = ref.y-cutL.getPointOrigine().y;
                cutL.setPointOrigine(ref);
                cutL.setPointDestination(new Point(cutL.getPointDestination().x + X,
                        cutL.getPointDestination().y + Y));
                modifierEnCascade(cutL.getUUID(), X, Y);
                if (CoupeValide(cutL, panneau))
                        {
                            cutL.setValide(false);
                        }
                        else{
                            cutL.setValide(true);
                        }
                    

                break;
        }
    }
    public float getCoteGrille() {
        return coteGrille;
    }
    public void setCoteGrille(float cote) {
    this.coteGrille = cote;
    // Sauvegarder l'état après modification de la taille de la grille
   undoRedoManager.saveState(coupes, panneau, outils, outil_courant, coteGrille, epaisseurActuelle);

}
public void undo() {
    UndoRedoManager.State previousState = undoRedoManager.undo();
    if (previousState != null) {
        this.coupes = previousState.coupes;
        this.panneau = previousState.panneau;
        this.outils = previousState.outils;
        this.outil_courant = previousState.outilCourant;
        this.coteGrille = previousState.coteGrille;
        this.epaisseurActuelle = previousState.epaisseurActuelle;
        Controleur.getInstance().mettreAJourVue();
        System.out.println("Undo effectué.");
        getCoupes();
    } else {
        System.out.println("Aucune action à annuler (Undo).");
    }
}

public void redo() {
    UndoRedoManager.State nextState = undoRedoManager.redo();
    if (nextState != null) {
        this.coupes = nextState.coupes;
        this.panneau = nextState.panneau;
        this.outils = nextState.outils;
        this.outil_courant = nextState.outilCourant;
        this.coteGrille = nextState.coteGrille;
        this.epaisseurActuelle = nextState.epaisseurActuelle;
        Controleur.getInstance().mettreAJourVue();
        System.out.println("Redo effectué.");
        getCoupes();
    } else {
        System.out.println("Aucune action à rétablir (Redo).");
    }
}


    public boolean isUndoAvailable() {
        return undoRedoManager.canUndo();
    }

    public boolean isRedoAvailable() {
        return undoRedoManager.canRedo();
    }
    
    
public void exporterGCode(String cheminFichier) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
        // Initialisation du G-code
        writer.write("G21 ; Utiliser des unités en millimètres\n");
        writer.write("G90 ; Mode de positionnement absolu\n");
        writer.write("M03 S1500 ; Démarrer la broche à 1500 RPM\n");
        // Parcourir les coupes et écrire les instructions
        for (Coupe coupe : coupes) {
            ElementCoupe element = (ElementCoupe) coupe.getElement();
            switch (coupe.getTypeCoupe()) {
                case "V": // Coupe axiale verticale
                    double xV = element.getPointDestination().getX(); // Point de départ en X
                    double yV = panneau.getLargeur(); // Largeur complète du panneau
                    double zV = 0;

                    // Écrire les instructions pour la coupe verticale
                    writer.write(String.format(
                        "G00 X%.2f Y%.2f ; Déplacement rapide au début de la coupe\n",
                        xV, zV
                    ));
                    writer.write(String.format(
                        "G01 Z-%.2f F500 ; Descendre à la profondeur de coupe\n",
                        element.getProfondeur()
                    ));
                    writer.write(String.format(
                        "G01 X%.2f Y%.2f F500 ; Tracé de la coupe verticale\n",
                        xV, yV
                    ));
                    writer.write("G00 Z5 ; Remonter l'outil après la coupe\n");
                    break;

                case "H": // Coupe axiale horizontale
                    double yH = element.getPointDestination().getY(); // Point de départ en Y
                    double xH = panneau.getLongueur(); // Longueur complète du panneau
                    double zH = 0;

                    // Écrire les instructions pour la coupe horizontale
                    writer.write(String.format(
                        "G00 X%.2f Y%.2f ; Déplacement rapide au début de la coupe\n",
                        zH, yH
                    ));
                    writer.write(String.format(
                        "G01 Z-%.2f F500 ; Descendre à la profondeur de coupe\n",
                        element.getProfondeur()
                    ));
                    writer.write(String.format(
                        "G01 X%.2f Y%.2f F500 ; Tracé de la coupe horizontale\n",
                        xH, yH
                    ));
                    writer.write("G00 Z5 ; Remonter l'outil après la coupe\n");
                    break;
              
                case "Rect":// Coupe rectangulaire
                case "Bordure": // Même logique que Rect

                    // Récupérer les coordonnées des coins du rectangle
                    double x00 = element.getPointOrigine().getX();
                    double y00 = element.getPointOrigine().getY();
                    double x11 = element.getPointDestination().getX();
                    double y11 = element.getPointDestination().getY();

                    writer.write(String.format(
                        "G00 X%.2f Y%.2f ; Déplacement rapide au point d'origine\n",
                        x00, y00
                    ));
                    writer.write(String.format(
                        "G01 Z-%.2f F500 ; Descendre à la profondeur de coupe\n",
                        element.getProfondeur()
                    ));

                    // Suivre le contour du rectangle
                    writer.write(String.format("G01 X%.2f Y%.2f F500 ; Aller au coin supérieur droit\n", x11, y00));
                    writer.write(String.format("G01 X%.2f Y%.2f ; Aller au coin inférieur droit\n", x11, y11));
                    writer.write(String.format("G01 X%.2f Y%.2f ; Aller au coin inférieur gauche\n", x00, y11));
                    writer.write(String.format("G01 X%.2f Y%.2f ; Revenir au point d'origine\n", x00, y00));

                    writer.write("G00 Z5 ; Remonter l'outil après la coupe\n");
                    break;
                case "L":// Coupe en L
                    
                    // Récupérer les coordonnées
                    double lx0 = element.getPointOrigine().getX();
                    double ly0 = element.getPointOrigine().getY();
                    double lx1 = element.getPointDestination().getX();
                    double ly1 = element.getPointDestination().getY();

                    writer.write(String.format(
                        "G00 X%.2f Y%.2f ; Déplacement rapide au point d'origine\n",
                        lx0, ly1    
                    ));
                    writer.write(String.format(
                        "G01 Z-%.2f F500 ; Descendre à la profondeur de coupe\n",
                        element.getProfondeur()
                    ));

                    

                    // Tracer le second segment horizontal ou vertical depuis le croisement
                    writer.write(String.format(
                        "G01 X%.2f Y%.2f ; Aller à la fin de la coupe L\n",
                        lx1, ly1
                    ));
                    
                    // Tracer le premier segment horizontal ou vertical jusqu'au point de croisement
                    writer.write(String.format(
                        "G01 X%.2f Y%.2f F500 ; Aller au point de croisement\n",
                        lx1, ly0
                    ));

                    writer.write("G00 Z5 ; Remonter l'outil après la coupe\n");
                    break;
                case "ZoneInterdite": // Zone interdite, exclue de l'exportation
                    System.out.println("Zone interdite détectée, non incluse dans le G-code.");
                    break;

                default:
                    System.out.println("Type de coupe non pris en charge : " + coupe.getTypeCoupe());
                    break;
            }
        }

        // Commandes de fin
        writer.write("M05 ; Arrêt de la broche\n");
        writer.write("G00 X0 Y0 ; Retour à l'origine\n");
        writer.write("M30 ; Fin du programme\n");
        System.out.println("G-code exporté avec succès dans : " + cheminFichier);
    } catch (IOException e) {
        System.err.println("Erreur lors de l'exportation du G-code : " + e.getMessage());
    }
}

    public void saveCNC(String cheminFichier) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))){
            writer.write("état de machine CNC sauvegardé\n");
            writer.write("Configurations système:\n");
            writer.write("Outils:\n");
            writer.write("Outil courant:\n" + outil_courant.getNom());
            for(Outil outil: outils){
                writer.write(outil.getNom()+":"+ outil.getLargeur_coupe()+"\n" );
            }
            writer.write("Marge de coupes:\n" + marge );
            writer.write("coupes \n");
            for(Coupe coupe: coupes){
                switch(coupe.getTypeCoupe()){
                    case "H":
                        CoupeAxe c = (CoupeAxe) coupe;
                        writer.write("Coupe Horizontale, axe = "  + c.getAxe() + "\n");
                        break;
                    case "V":
                        CoupeAxe c1 = (CoupeAxe) coupe;
                        writer.write("Coupe Verticale, axe = "  + c1.getAxe() + "\n");
                        break;
                    case "Rect":
                        CoupeRec c2 = (CoupeRec) coupe;
                        writer.write("Coupe Rectangulaire:\n");
                        writer.write("reference : ("+ c2.getReference().getX() + "," + c2.getReference().getY() + ")\n");
                        writer.write("origine : ("+ c2.getPointOrigine().getX() + ", " + c2.getPointOrigine().getY() + ")\n");
                        writer.write("destination : ("+ c2.getPointDestination().getX() + ", " + c2.getPointDestination().getY() + ")\n");
                        break;
                    case "L":
                        CoupeL c3 = (CoupeL) coupe;
                        writer.write("Coupe en L:\n");
                        writer.write("origine : ("+ c3.getPointOrigine().getX() + ", " + c3.getPointOrigine().getY() + ")\n");
                        writer.write("destination : ("+ c3.getPointDestination().getX() + ", " + c3.getPointDestination().getY() + ")\n");
                        break;
                    case "Bordure":
                        CoupeRec c4 = (CoupeRec) coupe;
                        writer.write("Coupe Bordure:\n");
                        writer.write("Bordure X: " + c4.getBordureX() + "\n");
                        writer.write("Bordure Y: "+c4.getBordureY() + "\n");

                }
            }

        }
        catch (IOException e) {
            System.err.println("Erreur de sauvegarde de l'application : " + e.getMessage());
        }

    }



}


