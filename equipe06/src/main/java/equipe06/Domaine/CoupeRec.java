package equipe06.Domaine;

import equipe06.Domaine.Utils.ElementCoupe;

import java.awt.*;
import java.util.UUID;
import java.util.Vector;


public class CoupeRec extends Coupe{
    private Point PointOrigine;
    private Point PointDestination;
    private float BordureX;
    private float BordureY;
    private Point reference;
    private Vector<UUID> myRef;

    //create new subclass Bordure
    public CoupeRec(ElementCoupe e, Vector<UUID> myRef, Point reference) {
        super(e);
        assert e != null;
        
        // Transfert direct des valeurs de ElementCoupe
        this.BordureX = e.getBordureX();
        this.BordureY = e.getBordureY();
        PointOrigine = e.getPointOrigine();
        PointDestination = e.getPointDestination();
        this.reference = reference;
        this.myRef = myRef;

        // Validation avec des affichages
        //System.out.println("Dans le constructeur de CoupeRec - BordureX après transfert : " + this.BordureX);
        //System.out.println("Dans le constructeur de CoupeRec - BordureY après transfert : " + this.BordureY);
        //System.out.println("Dans le constructeur de CoupeRec - BordureX après transfert : " + this.PointOrigine);
        //System.out.println("Dans le constructeur de CoupeRec - BordureY après transfert : " + this.PointDestination);
        /*
        // Ajoutez des affichages ici pour valider les valeurs de ElementCoupe
        if(e.getPointDestination() != null && e.getPointOrigine()!= null) {
            PointOrigine = e.getPointOrigine();

            PointDestination = e.getPointDestination();
            assert PointDestination != null;
        }
        if(getBordureX()!=0 && getBordureY()!=0){
            assert getBordureX() >0;
            assert getBordureY() >0;
            BordureX = getBordureX();
            BordureY = getBordureY();
        }

*/
    }
    
    public CoupeRec(ElementCoupe e) {
        super(e);
        assert e != null;

        // Transfert direct des valeurs de ElementCoupe
        this.BordureX = e.getBordureX();
        this.BordureY = e.getBordureY();
        this.PointOrigine = e.getPointOrigine();
        this.PointDestination = e.getPointDestination();

        // Valeurs par défaut pour myRef et reference
        this.reference = null; // Pas de référence
        this.myRef = new Vector<>(); // Vecteur vide
    }
    
    
    public Point getPointOrigine() {
        return PointOrigine;
    }
    public Point getPointDestination() {
        return PointDestination;
    }
    public float getBordureX() {
        return BordureX;
    }
    public float getBordureY() {
        return BordureY;
    }
    public void setPointOrigine(Point PointOrigine) {
        assert PointOrigine != null;
        this.PointOrigine = PointOrigine;
    }
    public void setPointDestination(Point PointDestination) {
        assert PointDestination != null;
        this.PointDestination = PointDestination;
    }
    public void setBordureX(float BordureX) {
        assert BordureX >= 0;
        this.BordureX = BordureX;
    }
    public void setBordureY(float BordureY) {
        assert BordureY >= 0;
        this.BordureY = BordureY;
    }
    
    public Point getReference() {
        return reference;
    }
    public void ChangeReference(Point reference, UUID myRef) {
        this.reference = reference;
    }
    public Vector<UUID> getMyRef() {
        return myRef;
    }

    public void setPointReference(Point point) {
        this.reference=point;
    }

}
