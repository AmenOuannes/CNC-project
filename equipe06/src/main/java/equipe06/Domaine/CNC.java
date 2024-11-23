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
    /*
    public void supprimerOutilParIndex(int index) {
        if (index >= 0 && index < outils.size()) {
            Outil outil = outils.get(index);
            outils.remove(index);
            if (outil_courant.getNom() == outil.getNom())  //set another
                outil_courant = outils.get(0);

            System.out.println("Outil supprimé avec succès."); //control local remove @ zied

        } else {
            System.out.println("Index invalide. Impossible de supprimer l'outil."); //control local remove @ zied
        }
    }*/
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
    public void CreerCoupeL(Point pointOrigine , Point pointDestination){
        //TODO coupe en L, attribut extraits du controleur
        assert pointOrigine != null;
        assert pointDestination != null;
        ElementCoupe e  = new ElementCoupe( pointOrigine, pointDestination,5.0f,0.3f,0,false,0.0f,0.0f,"L",null);
        CoupeL coupe = new CoupeL(e);
         //if(panneau.inPanneau((float) pointOrigine.getX() , (float) pointOrigine.getY())&& panneau.inPanneau((float) pointDestination.getX(), (float) pointDestination.getY())){
             coupes.add(coupe);
         //}
    }
    //Amen
    public void CreerCoupeRect(Point Origine, Point Destination){
        //TODO coupe rect, attribut extraits du controleur
        assert (Origine != null);
        assert (Destination != null);
        ElementCoupe e = new ElementCoupe(
                Origine, Destination, 5.0f,
                0.3f,0,false,0.0f, 0.0f,"Rect", null);
        CoupeRec coupe = new CoupeRec(e);
        //if(panneau.inPanneau(Origine) && panneau.inPanneau(Destination)){
        coupes.add(coupe);
        //}

    }

    //zied
    public void CreerCoupeBordure(float x, float y){

        float bordureX = x;
        float bordureY = y;
        ElementCoupe e = new ElementCoupe(
                null, null, 5.0f, 0.3f, 0, false, bordureX, bordureY, "Bordure", null );
        CoupeRec coupe = new CoupeRec(e);
        //TODO coupe valide
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

           }
       }
       return uuids;
    }



}

