package equipe06.Domaine;
import equipe06.Domaine.Utils.ElementCoupe;
import equipe06.Domaine.Coupe;
import java.awt.Point;
import java.awt.*;
import java.util.UUID;
import java.util.Vector;


public class CoupeL extends Coupe{
  private Point pointReference ;
  private Point pointDestination ;
  private Vector<UUID> myRefs;
  private ElementCoupe element;

    // Implémentation de la méthode abstraite coupe
    //@Override
    public CoupeL(ElementCoupe e, Vector<UUID> myRefs){
        super(e);
        assert e != null:"L'element de coupe est invalide.";
        // on peux pas desiner la coupe en L si les 2 points sont identiques car on aura pas d'intersection

        this.pointReference  = e.getPointOrigine();
        this.pointDestination = e.getPointDestination();

        this.myRefs = myRefs;
        this.element = e;

    }
  public Point getPointOrigine() {
        return pointReference;
  }
  public void setPointOrigine(Point pointOrigine) {
        this.pointReference = pointOrigine;
  }
  public Point getPointDestination() {
        return pointDestination;
  }
  public void setPointDestination(Point pointDestination) {
        this.pointDestination = pointDestination;
  }

    public void ChangeReference(Point reference, UUID myRef) {
        this.pointReference = reference;
    }
    public Vector<UUID> getMyRef() {
        return myRefs;
    }
  
  
public ElementCoupe getElement() {
        return element;
    }
  
  
}

