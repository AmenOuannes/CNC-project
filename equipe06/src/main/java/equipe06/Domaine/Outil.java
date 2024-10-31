package equipe06.Domaine;
//@author amen
import java.util.UUID;
public class Outil {
    private String Nom;
    private UUID id;
    private float largeur_coupe;
    public Outil(String nom, UUID id, float largeur_coupe) {
        this.Nom = nom;
        this.id = id;
        this.largeur_coupe = largeur_coupe;
    }

    public float getLargeur_coupe() {
        return largeur_coupe;
    }
    public void setLargeur_coupe(float largeur_coupe) {
        this.largeur_coupe = largeur_coupe;
    }
    public String getNom() {
        return Nom;
    }
    public void setNom(String nom) {
        this.Nom = nom;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
}
