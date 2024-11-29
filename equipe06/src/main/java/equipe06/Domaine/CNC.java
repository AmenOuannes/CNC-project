package equipe06.Domaine;

import equipe06.Domaine.Panneau;
import equipe06.Domaine.Repere;
import equipe06.Domaine.Coupe;
import equipe06.Domaine.*;
import equipe06.Domaine.Utils.ElementCoupe;
import org.w3c.dom.css.Rect;
import java.awt.Point;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.Vector;
import java.util.Iterator;
import java.io.IOException;


public class CNC {
    private Panneau panneau;
    private Repere repere;
    private Vector<Coupe> coupes;
    private Vector<Point> points_de_reference;
    private Vector<Outil> outils;
    private Outil outil_courant;
    private final UndoRedoManager undoRedoManager = new UndoRedoManager();


    public CNC() {
        panneau = new Panneau(1200,1000,0);
        //repere = new Repere(); // Repère pour gérer les conversions
        coupes = new Vector <Coupe>();
        outils = new Vector<Outil>(12);
        outils.add(new Outil("defaut", 12.7f));
        outil_courant = outils.firstElement();
        
    }



    public void creerPanneau(float longueurX, float largeurY, float profondeurZ) {
        // Création de l'objet Panneau avec les attributs donnés
        this.panneau = new Panneau(longueurX, largeurY, profondeurZ);
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

    public Repere getRepere() {
        return repere;
    }

    public Vector<CoupeDTO> getCoupes() {
        Vector<CoupeDTO> cDTO = new Vector<CoupeDTO>();
        for (Coupe coupe : coupes) cDTO.add(new CoupeDTO(coupe));
        return cDTO;
    }
    //-------------------------------------------------OUTILS--------------------------------------------
    // TODO fix redundancy
    public void ajouterOutil(String nom, float largeurCoupe){
        Outil outil = new Outil(nom, largeurCoupe);
        if (outils.size() < 12  && !outils.contains(outil)){
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
        assert index >= 0 && index < outils.size() : "Index invalide pour la suppression d'un outil.";
        if (index >= 0 && index < outils.size()) {
            Outil outil = outils.get(index);
            outils.remove(index);
            // Vérifie si l'outil courant est celui qui a été supprimé
            if (outil_courant != null && outil_courant.getNom().equals(outil.getNom())) {
                if (!outils.isEmpty()) {
                    outil_courant = outils.get(0); // Définit un nouvel outil courant
                    System.out.println("Outil courant mis à jour après suppression : " + outil_courant.getNom());
                } else {
                    outil_courant = null; // Réinitialise si aucun outil n'est disponible
                    System.out.println("Aucun outil disponible. Outil courant réinitialisé.");
                }
            }

            System.out.println("Outil supprimé avec succès."); //control local remove @ zied

        } else {
            System.out.println("Index invalide. Impossible de supprimer l'outil."); //control local remove @ zied
        }
    }

    //amen
    public void ModifierOutil(UUID uuid, String NewName, float NewLargeur){

        for(Outil outil : outils){
            if(outil.getId() == uuid){
                if(outil.getNom()!= NewName)
                    outil.setNom(NewName);
                if(outil.getLargeur_coupe() != NewLargeur)
                    outil.setLargeur_coupe(NewLargeur);
            }
        }
    }
    /*public void ModifierCoupesOutilCourant(){
        for(Coupe coupe: coupes){
            coupe.setOutil(outil_courant);
        }
    }*/


    //-------------------------------------------------COUPES--------------------------------------------------------
    //Katia
    public void CreerCoupeL(Point referencePoint, Point pointDestination){
        //TODO coupe en L, attribut extraits du controleur
        assert referencePoint != null;
        assert pointDestination != null;
        ElementCoupe e  = new ElementCoupe( referencePoint, pointDestination,5.0f,0.3f,0,false,0.0f,0.0f,"L",outil_courant.getLargeur_coupe());
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
                Origine, Destination, 5.0f,
                0.3f,0,false,BordureX, BordureY,"Rect",outil_courant.getLargeur_coupe());
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
        Point pointOrigine = new Point(xOrigine, yOrigine);
        Point pointDestination = new Point(bordureXPx, bordureYPx);
        ElementCoupe e = new ElementCoupe(
                pointOrigine, pointDestination, 5.0f, 0.3f, 0, false, bordureX, bordureY, "Bordure", outil_courant.getLargeur_coupe() );
        CoupeRec coupe = new CoupeRec(e);
        coupes.add(coupe);
        undoRedoManager.saveState(new Vector<>(coupes));

        System.out.println("Coupe bordure enregistrée.");
        
    }


    public void CreerCoupeAxe(float x,  float y, boolean composante, Point reference) {
        
        assert reference != null : "Le point de référence ne doit pas être null.";
        assert x >= 0 : "L'axe x doit être positif.";
        assert y >= 0 : "L'axe y doit être positif.";
   
        ElementCoupe e = null;

        Point pointDestination = new Point();

        if (composante == true)
        {

         e = new ElementCoupe(
                reference, pointDestination, 5.0f, 0.3f,
                 x, composante, 0.0f, 0.0f, "V", outil_courant.getLargeur_coupe());
        }
        else{

             e = new ElementCoupe(
            reference, pointDestination, 5.0f, 0.3f,
                     y, composante, 0.0f, 0.0f, "H", outil_courant.getLargeur_coupe());
        }
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


    }
    
    public void ModifierCoupeRectL() {
        //verifier si ma coupe est modifiée lors de la modification d'un axe
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


        if(Objects.equals(coupe.getTypeCoupe(), "H") || Objects.equals(coupe.getTypeCoupe(), "V")){
            CoupeAxe c = (CoupeAxe) coupe;
            if(!c.getMyRef().isEmpty())
                return true;
            else if (panneau.surPanneau(c.getReference())) {
                System.out.println(" click sur Panneau");
                return true;
            }
        }else if (Objects.equals(coupe.getTypeCoupe(), "Rect")) {
            CoupeRec c = (CoupeRec) coupe;
            float origineX = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointOrigine().x);
            float origineY = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointOrigine().y);
            float destinationX = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointDestination().x);
            float destinationY = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointDestination().y);

            // Vérifier que origine et destination sont dans le panneau, et que la référence est sur le panneau
            Vector<UUID> uuids = surCoupes(c.getReference());
            if (panneau.inPanneau(origineX, origineY) && 
                    panneau.inPanneau(destinationX, destinationY))
            {
                if(  IsThere(uuids, "Rect") || IsThere(uuids, "Bordure") || IsThere(uuids, "L")  ||    (  IsThere(uuids, "H") && IsThere(uuids, "V")  )   ){
                    System.out.println(" references valides");
                    return true;
                }
                if(panneau.surCoins(c.getReference())) return true;
            }
          


        } else if (Objects.equals(coupe.getTypeCoupe(), "L")) {
            CoupeL c = (CoupeL) coupe;
            // Extraire les coordonnées des points
            float destinationX = Repere.getInstance().convertirEnMmDepuisPixels(c.getPointDestination().x);
            float destinationY = Repere.getInstance().convertirEnMmDepuisPixels(c.getPointDestination().y);
            Vector<UUID> uuids = surCoupes(c.getPointOrigine());
            if (panneau.inPanneau(destinationX, destinationY))
            {
                if(  IsThere(uuids, "Rect")   || IsThere(uuids, "Bordure") || IsThere(uuids, "L")  ||    (  IsThere(uuids, "H") && IsThere(uuids, "V")  )   ){
                    System.out.println(" references valides");
                    return true;
                }
                if(panneau.surCoins(c.getPointOrigine())) return true;
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
        undoRedoManager.saveState(coupes); // Sauvegarder avant l'ajout
        System.out.print("coupe enregistrée\n");


    }
 

    public Vector<UUID> surCoupes(Point reference) {
        Vector<UUID> uuids = new Vector<>();
        if (panneau.inPanneau(Repere.getInstance().convertirEnMmDepuisPixels(reference.x), Repere.getInstance().convertirEnMmDepuisPixels(reference.y))) {
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
                                    coupe.invalide = true;
                                }

                            break;
                        case "Rect":
                            CoupeRec cutR =(CoupeRec) coupe;
                            Vector<UUID> dr = surCoupes(cutR.getReference());
                            if(dr.contains(currentUUID)) {
                                coupe.invalide = true;
                            }
                            break;
                        case "L":
                            CoupeL cutL =(CoupeL) coupe;
                            Vector<UUID> dl = surCoupes(cutL.getPointOrigine());
                            if(dl.contains(currentUUID)) {
                                coupe.invalide = true;
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
                undoRedoManager.saveState(coupes); // Sauvegarder avant suppression
                iterator.remove();
                System.out.println("Coupe supprimée : " + coupe.getUUID());
                return;
            }
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
            if(uuid==coupe.getUUID())
                cut= coupe;
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
            TranslationX = x-OldDestination.x;
            TranslationY = y-OldDestination.y;
            System.out.println("TranslationX : " + TranslationX);
            System.out.println("TranslationY : " + TranslationY);
            modifierEnCascade(ma_coupe.getUUID(), TranslationX,TranslationY);
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
            TranslationX = x-OldDestination.x;
            TranslationY = y- OldDestination.y;
            modifierEnCascade(ma_coupe.getUUID(), TranslationX,TranslationY);
        }





    }
    public void modifierCoupeAxiale(Point p, float largeur){
        if(surCoupes(p).isEmpty()) return;
        UUID uuid = this.surCoupes(p).firstElement();
        Coupe ma_coupe = null ;
        int translationX=0, translationY=0;
        for(Coupe coupe : coupes) {
            if(uuid==coupe.getUUID())
                ma_coupe= coupe;
        }
        ma_coupe.setOutil(largeur);
    }
    public void modifierCoupeAxiale(float a, Point p) {
        if(surCoupes(p).isEmpty()) return;
        UUID uuid = this.surCoupes(p).firstElement();
        CoupeAxe ma_coupe = null ;
        int translationX=0, translationY=0;
        for(Coupe coupe : coupes) {
            if(uuid==coupe.getUUID())
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
        System.out.println("went here");
        modifierEnCascade(ma_coupe.getUUID(), translationX, translationY);
        System.out.println("arrived");
    }


    public void modifierEnCascade(UUID uuid, int X, int Y) {
        for (Coupe coupe : coupes) {
            switch (coupe.getTypeCoupe()) {
                case "V":
                    CoupeAxe cut = (CoupeAxe) coupe;
                    if (cut.getMyRef().contains(uuid))
                        cut.setAxe(cut.getAxe() + X);
                    break;
                case "H":
                    CoupeAxe cutH = (CoupeAxe) coupe;
                    if (cutH.getMyRef().contains(uuid))
                        cutH.setAxe(cutH.getAxe() + Y);
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
                    }

                    break;
                case "L":
                    CoupeL cutL = (CoupeL) coupe;
                    if (cutL.getMyRef().contains(uuid)) {
                        cutL.setPointDestination(new Point(cutL.getPointDestination().x + X,
                                cutL.getPointDestination().y + Y));
                        cutL.setPointOrigine(new Point(cutL.getPointOrigine().x + X,
                                cutL.getPointOrigine().y + Y));

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
            if(uuid==coupe.getUUID())
                ma_coupe= coupe;
        }
        if(ma_coupe==null) return;
        switch (ma_coupe.getTypeCoupe()) {
            case "V", "H":

                CoupeAxe cut = (CoupeAxe) ma_coupe;
                X = ref.x-cut.getReference().x;
                Y = ref.y-cut.getReference().y;
                   cut.ChangeReference(ref);
                if(ma_coupe.getTypeCoupe()=="H") cut.setAxe(cut.getAxe()+Y);
                else cut.setAxe(cut.getAxe()+X);
                ma_coupe.invalide=false;
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
                ma_coupe.invalide=false;

                break;
            case "L":
                CoupeL cutL = (CoupeL) ma_coupe;
                X = ref.x-cutL.getPointOrigine().x;
                Y = ref.y-cutL.getPointOrigine().y;
                cutL.setPointOrigine(ref);
                cutL.setPointDestination(new Point(cutL.getPointDestination().x + X,
                        cutL.getPointDestination().y + Y));
                ma_coupe.invalide=false;

                break;
        }
    }
    public void redo() {
        Vector<Coupe> nextState = undoRedoManager.redo();
        if (nextState != null) {
            coupes = nextState; // Restaurer l'état suivant
            System.out.println("Redo effectué.");
        }
    }
    public void undo() {
        Vector<Coupe> previousState = undoRedoManager.undo();
        if (previousState != null) {
            coupes = previousState; // Restaurer l'état précédent
            System.out.println("Undo effectué.");
        }
    }


    public boolean isUndoAvailable() {
        return undoRedoManager.canUndo();
    }

    public boolean isRedoAvailable() {
        return undoRedoManager.canRedo();
    }
}




    

