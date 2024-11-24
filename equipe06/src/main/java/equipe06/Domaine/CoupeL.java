package equipe06.Domaine;
import equipe06.Domaine.Utils.ElementCoupe;
import equipe06.Domaine.Coupe;
import java.awt.Point;
import java.awt.*;
import java.util.UUID;
import java.util.Vector;


public class CoupeL extends Coupe{
  private Point pointOrigine ;
  private Point pointDestination ;
  private Point reference;
  private Vector<UUID> myRef;
  
    // Implémentation de la méthode abstraite coupe
    //@Override
    public CoupeL(ElementCoupe e, Vector<UUID> myRef, Point reference){
        super(e);
        assert e != null:"L'element de coupe est invalide.";
        // on peux pas desiner la coupe en L si les 2 points sont identiques car on aura pas d'intersection
        assert pointDestination == pointOrigine:"Les points d'origine et de destination ne doivent pas être identiques.";
        this.pointOrigine  = e.getPointOrigine();
        this.pointDestination = e.getPointDestination();
        this.reference = reference;
        this.myRef = myRef;

    }
  public Point getPointOrigine() {
        return pointOrigine;
  }
  public void setPointOrigine(Point pointOrigine) {
        this.pointOrigine = pointOrigine;
  }
  public Point getPointDestination() {
        return pointDestination;
  }
  public void setPointDestination(Point pointDestination) {
        this.pointDestination = pointDestination;
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
  
  
  
  
  
}

