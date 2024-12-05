package equipe06.Domaine;

import java.util.UUID;

public class Outil implements Cloneable {

    private String Nom;
    private UUID id;
    private float largeur_coupe;

    // Constructeur
    public Outil(String nom, float largeur_coupe) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'outil ne peut pas être invalide.");
        }
        if (largeur_coupe <= 0) {
            throw new IllegalArgumentException("La largeur de coupe doit être supérieure à zéro.");
        }
        this.Nom = nom;
        this.id = UUID.randomUUID(); // Génère un nouvel ID unique
        this.largeur_coupe = largeur_coupe;
    }

    // Méthode clone avec clonage profond
    @Override
    public Outil clone() {
        try {
            Outil copie = (Outil) super.clone(); // Copie superficielle de base
            copie.id = UUID.randomUUID();       // Générer un nouvel UUID pour éviter les collisions
            // Pas besoin de cloner "Nom" ou "largeur_coupe", car ce sont des types immuables ou primitifs.
            return copie;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Erreur lors du clonage profond de l'objet Outil", e);
        }
    }

    // Getters et setters
    public float getLargeur_coupe() {
        return largeur_coupe;
    }

    public void setLargeur_coupe(float largeur_coupe) {
        if (largeur_coupe <= 0) {
            throw new IllegalArgumentException("La largeur de coupe doit être supérieure à zéro.");
        }
        this.largeur_coupe = largeur_coupe;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'outil ne peut pas être invalide.");
        }
        this.Nom = nom;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'outil ne peut pas être null.");
        }
        this.id = id;
    }
}