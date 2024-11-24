package equipe06.Domaine;

import equipe06.Domaine.Panneau;
import equipe06.Domaine.Repere;
import equipe06.Domaine.Coupe;
import equipe06.Domaine.*;
import equipe06.Domaine.Utils.ElementCoupe;

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


    public CNC() {
        panneau = new Panneau(1200,1000,0);
        //repere = new Repere(); // Repère pour gérer les conversions
        coupes = new Vector <Coupe>();
        outils = new Vector<Outil>(12);
        outils.add(new Outil("defaut", 10));
        outil_courant = outils.firstElement();
        
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
    if (outil_courantDTO != null) {
        // Recherche l'outil correspondant dans la liste des outils
        for (Outil outil : outils) {
            if (outil.getNom().equals(outil_courantDTO.getNomDTO())) {
                this.outil_courant = outil;
                System.out.println("Outil courant mis à jour : " + outil.getNom());
                return;
            }
        }
        System.out.println("Outil courant non trouvé dans la liste.");
    } else {
        this.outil_courant = null; // Réinitialise l'outil courant si aucun outil DTO n'est fourni
        System.out.println("Outil courant réinitialisé.");
    }
}

    //TODO : rendre cette boucle en try catch
    // TODO check if outil est outilCourant
    public void supprimerOutilParIndex(int index) {
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
    /* fonction existe deja marra jeya dzid ell code ell nekes fi west ell fonction
   public void supprimerOutilParIndex(int index) {
    if (index >= 0 && index < outils.size()) {
        Outil outilSupprime = outils.get(index); // Récupère l'outil à supprimer
        outils.remove(index); // Supprime l'outil de la liste

        // Vérifie si l'outil courant est celui qui a été supprimé
        if (outil_courant != null && outil_courant.getNom().equals(outilSupprime.getNom())) {
            if (!outils.isEmpty()) {
                outil_courant = outils.get(0); // Définit un nouvel outil courant
                System.out.println("Outil courant mis à jour après suppression : " + outil_courant.getNom());
            } else {
                outil_courant = null; // Réinitialise si aucun outil n'est disponible
                System.out.println("Aucun outil disponible. Outil courant réinitialisé.");
            }
        }

        System.out.println("Outil supprimé avec succès : " + outilSupprime.getNom());
    } else {
        System.out.println("Index invalide. Impossible de supprimer l'outil.");
    }
}
*/


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
    public void ModifierCoupesOutilCourant(){
        for(Coupe coupe: coupes){
            coupe.setOutil(outil_courant);
        }
    }


    //-------------------------------------------------COUPES--------------------------------------------------------
    //Katia
    public void CreerCoupeL(Point pointOrigine , Point pointDestination, Point reference){
        //TODO coupe en L, attribut extraits du controleur
        assert pointOrigine != null;
        assert pointDestination != null;
        ElementCoupe e  = new ElementCoupe( pointOrigine, pointDestination,5.0f,0.3f,0,false,0.0f,0.0f,"L",null);
        Vector<UUID> CoupesDeReferences = surCoupes(reference);
        CoupeL coupe = new CoupeL(e, CoupesDeReferences ,reference);
        if (CoupeValide(coupe, panneau)) { // Vérifie si la coupe est valide avant l'ajout
            AjouterCoupe(coupe);
            System.out.println("Coupe en L créée avec succès.");
        } else {
            System.out.println("La coupe en L est invalide et n'a pas été ajoutée.");
        }
    }
    //Amen
    public void CreerCoupeRect(Point Origine, Point Destination, Point reference){
        //TODO coupe rect, attribut extraits du controleur
        assert (Origine != null);
        assert (Destination != null);
        ElementCoupe e = new ElementCoupe(
                Origine, Destination, 5.0f,
                0.3f,0,false,0.0f, 0.0f,"Rect", null);
        Vector<UUID> CoupesDeReferences = surCoupes(reference);
        CoupeRec coupe = new CoupeRec(e, CoupesDeReferences ,reference);
        if (CoupeValide(coupe, panneau)) { // Vérifie si la coupe est valide avant l'ajout
            AjouterCoupe(coupe);
            System.out.println("Coupe rectangulaire créée avec succès.");
        } else {
            System.out.println("La coupe rectangulaire est invalide et n'a pas été ajoutée.");
        }

    }

    //zied
    public void CreerCoupeBordure(float x, float y){

        float bordureX = x;
        float bordureY = y;
        ElementCoupe e = new ElementCoupe(
                null, null, 5.0f, 0.3f, 0, false, bordureX, bordureY, "Bordure", null );
        CoupeRec coupe = new CoupeRec(e);
        //TODO coupe valide
        // on a pas besoin de verifier la coupe Bordure
        coupes.add(coupe);
        
    }

    //hedi+amen
    // TODO :changer ça en fnct creer coupeAXE, correction sur l'ajout du point origine et destination dans le element coupe
    public void CreerCoupeAxe(float x,  float y, boolean composante, Point reference) {
        ElementCoupe e = null;
        //CoupeAxe ma_coupe = null;
        Point pointDestination = new Point();

        if (composante == true)
        {

         e = new ElementCoupe(
                reference, pointDestination, 5.0f, 0.3f,
                 x, composante, 0.0f, 0.0f, "V", null);
        }
        else{

             e = new ElementCoupe(
            reference, pointDestination, 5.0f, 0.3f,
                     y, composante, 0.0f, 0.0f, "H", null);
        }
        Vector<UUID> CoupesDeReferences = surCoupes(reference);
        CoupeAxe ma_coupe = new CoupeAxe(e, CoupesDeReferences ,reference);
        if (CoupeValide(ma_coupe, panneau)) //remove katia
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
    //hedi
    // TODO: fnct invalide pour le reste du travail
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

            // Convertir les points origine et destination en coordonnées nécessaires
            float origineX = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointOrigine().x);
            float origineY = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointOrigine().y);
            float destinationX = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointDestination().x);
            float destinationY = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointDestination().y);
            float referenceX = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getReference().x);
            float referenceY = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getReference().y);
            Point reference = new Point((int) Repere.getInstance().convertirEnMmDepuisPixels(c.getReference().x),
                                     (int) Repere.getInstance().convertirEnMmDepuisPixels(c.getReference().y));

            // Vérifier que origine et destination sont dans le panneau, et que la référence est sur le panneau
            if (panneau.inPanneau(origineX, origineY) && 
                    panneau.inPanneau(destinationX, destinationY )/*&& panneau.surPanneau(reference)*/ && 
                    panneau.inPanneau(referenceX, referenceY ) ) {
                System.out.println("Coupe rectangulaire valide.");
                return true;
                }   
        } else if (Objects.equals(coupe.getTypeCoupe(), "L")) {
            CoupeL c = (CoupeL) coupe;
            // Extraire les coordonnées des points
            float origineX = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointOrigine().x);
            float origineY = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointOrigine().y);
            float destinationX = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointDestination().x);
            float destinationY = (float) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointDestination().y);
            Point origine = /*c.getPointOrigine();*/   new Point((int) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointOrigine().x),
                                     (int) Repere.getInstance().convertirEnMmDepuisPixels(c.getPointOrigine().y));
            // Calculer les points adjacents pour une coupe en L
            float adj1X = destinationX;
            float adj1Y = origineY; // Coin horizontal adjacent
            float adj2X = origineX;
            float adj2Y = destinationY; // Coin vertical adjacent
            // Vérifier que origine est sur le panneau, et les autres points sont dans le panneau
            if (/*panneau.surPanneau(origine) && */ panneau.inPanneau(origineX, origineY) &&
                panneau.inPanneau(destinationX, destinationY) &&
                panneau.inPanneau(adj1X, adj1Y) &&
                panneau.inPanneau(adj2X, adj2Y)) {
                System.out.println("Coupe en L valide.");
                return true;
            }
        }
        return false;
    } 
    //amen
    // TODO: changer en ajoutant les uuid
    public void AjouterCoupe(Coupe coupe) {
        // vector of uuids
        Vector<UUID> uuids = new Vector<UUID>();
        for(Coupe c: coupes){
            uuids.add(coupe.getUUID());
        }
        do{
            coupe.setUUID(UUID.randomUUID());
        }while(uuids.contains(coupe.getUUID()));

        coupes.add(coupe);
        System.out.print("coupe enregistrée\n");
        //System.out.print(coupes.size());

    }
    public void supprimerCoupe(UUID uuid) {
        try{
            for (int i=0; i<coupes.size(); i++){
                if (coupes.get(i).getUUID().equals(uuid)){
                    coupes.remove(i);
                    break;
                }
            }
        } catch (IndexOutOfBoundsException e){
            System.out.println("Erreur : y'a pas de coupe a supprimer ");
        }
    }

    public Vector<UUID> surCoupes(Point reference){
        if (panneau.inPanneau((float) Repere.getInstance().convertirEnMmDepuisPixels(reference.x), (float) Repere.getInstance().convertirEnMmDepuisPixels(reference.y)))
        {
        float y = Repere.getInstance().convertirEnMmDepuisPixels(reference.y);
        float x = Repere.getInstance().convertirEnMmDepuisPixels(reference.x);
        Vector<UUID> uuids = new Vector<>();
       for(Coupe c: coupes){
           switch (c.getTypeCoupe()){
               case "V":
                   CoupeAxe axiale = (CoupeAxe) c;
                   if((axiale.getAxe() - outil_courant.getLargeur_coupe() / 2 <= x) &&
                           (x <= axiale.getAxe() + outil_courant.getLargeur_coupe() / 2)){
                       uuids.add(c.getUUID());
                   }
                   break;
               case "H":
                   axiale = (CoupeAxe) c;
                   if((axiale.getAxe() - outil_courant.getLargeur_coupe() / 2 <= y) &&
                           (y <= axiale.getAxe() + outil_courant.getLargeur_coupe() / 2)){
                       uuids.add(c.getUUID());
                   }
                   break;
                case "Rect":
                CoupeRec coupeRect = (CoupeRec) c;
                Point origineRect = coupeRect.getPointOrigine();
                Point destinationRect = coupeRect.getPointDestination();
                // Le point doit correspondre à un des 4 coins du rectangle
                if ((((origineRect.x - outil_courant.getLargeur_coupe() / 2 <= x && origineRect.x + outil_courant.getLargeur_coupe() / 2 >= x) || 
                        (destinationRect.x - outil_courant.getLargeur_coupe() / 2 <= x && destinationRect.x + outil_courant.getLargeur_coupe() / 2 >= x)) &&
                        (y >= Math.min(destinationRect.y, origineRect.y) && y <= Math.max(destinationRect.y, origineRect.y))) || (((origineRect.y - outil_courant.getLargeur_coupe() / 2 <= y && origineRect.y + outil_courant.getLargeur_coupe() / 2 >= y) || 
                        (destinationRect.y - outil_courant.getLargeur_coupe() / 2 <= y && destinationRect.y + outil_courant.getLargeur_coupe() / 2 >= y)) &&
                        (x >= Math.min(destinationRect.x, origineRect.x) && y <= Math.max(destinationRect.x, origineRect.x))))
                {
                    uuids.add(c.getUUID());
                }
                break;
                case "L":
                CoupeL coupeL = (CoupeL) c;
                Point destinationL = coupeL.getPointDestination();
                Point OrigineL = coupeL.getPointOrigine();
                Point adjacent1 = new Point(destinationL.x, OrigineL.y); // Coin horizontal adjacent
                Point adjacent2 = new Point(OrigineL.x, destinationL.y); // Coin vertical adjacent
                // Vérifie si le point de référence est le point destination ou un des coins adjacents
                if ((((OrigineL.x - outil_courant.getLargeur_coupe() / 2 <= x && OrigineL.x + outil_courant.getLargeur_coupe() / 2 >= x) || 
                        (destinationL.x - outil_courant.getLargeur_coupe() / 2 <= x && destinationL.x + outil_courant.getLargeur_coupe() / 2 >= x)) &&
                        (y >= Math.min(destinationL.y, OrigineL.y) && y <= Math.max(destinationL.y, OrigineL.y))) || (((OrigineL.y - outil_courant.getLargeur_coupe() / 2 <= y && OrigineL.y + outil_courant.getLargeur_coupe() / 2 >= y) || 
                        (destinationL.y - outil_courant.getLargeur_coupe() / 2 <= y && destinationL.y + outil_courant.getLargeur_coupe() / 2 >= y)) &&
                        (x >= Math.min(destinationL.x, OrigineL.x) && y <= Math.max(destinationL.x, OrigineL.x)))) {
                    uuids.add(c.getUUID());
                }
                break;
                default:
                    System.out.println("Type de coupe non pris en charge : " + c.getTypeCoupe());
                    break;

           }
       }
       return uuids;
        }
        else {
            return null;
        }
    }
    
    

    public void supprimerCoupesParPoint(Point point) {
        // Convertir les coordonnées du point de référence en millimètres
        float y = Repere.getInstance().convertirEnMmDepuisPixels(point.y);
        float x = Repere.getInstance().convertirEnMmDepuisPixels(point.x);
        boolean coupeSupprimee = false;
        // Parcourir les coupes pour identifier celles à supprimer
        Iterator<Coupe> iterator = coupes.iterator();
        while (iterator.hasNext()) {
            Coupe coupe = iterator.next();
            switch (coupe.getTypeCoupe()) {
                /*
                case "Rect": // Coupe rectangulaire
                case "L":    // Coupe en L
                    Point origine = coupe.getPointOrigine();
                    Point destination = coupe.getPointDestination();

                    // Vérifier si le point correspond à l'origine ou à la destination
                    if ((origine != null && Math.abs(origine.x - x) <= 1 && Math.abs(origine.y - y) <= 1) ||
                        (destination != null && Math.abs(destination.x - x) <= 1 && Math.abs(destination.y - y) <= 1)) {
                        iterator.remove();
                        coupeSupprimee = true;
                    }
                    break;
*/
                case "H": // Coupe axiale horizontale
                case "V": // Coupe axiale verticale
                    CoupeAxe coupeAxe = (CoupeAxe) coupe;

                    // Vérifier si le point correspond directement à l'axe
                    if ((coupe.getTypeCoupe().equals("H") && Math.abs(coupeAxe.getAxe() - x) <= 10) ||
                        (coupe.getTypeCoupe().equals("V") && Math.abs(coupeAxe.getAxe() - y) <= 10)) {
                        iterator.remove();
                        coupeSupprimee = true;
                    }
                    break;
            }
       }

    // Afficher le résultat de la suppression
    if (coupeSupprimee) {
        System.out.println("Coupe(s) supprimée(s) avec succès.");
    } else {
        System.out.println("Aucune coupe trouvée à supprimer.");
    }
    
    }
     
}
