/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package equipe06.Domaine;

import java.util.Stack;
import java.util.Vector;

/**
 *
 * @author faresmajdoub
 */


public class UndoRedoManager {
    private final Stack<Snapshot> undos = new Stack<>();
    private final Stack<Snapshot> redos = new Stack<>();

    // Classe interne pour capturer un instantané
    public static class Snapshot {
        Vector<Coupe> coupes;
        Panneau panneau;
        Vector<Outil> outils;
        Outil outilCourant;

        Snapshot(Vector<Coupe> coupes, Panneau panneau, Vector<Outil> outils, Outil outilCourant) {
            this.coupes = deepCopyCoupes(coupes); // Copie profonde des coupes
            this.panneau = panneau.clone();       // Clone du panneau
            this.outils = deepCopyOutils(outils); // Copie profonde des outils
            this.outilCourant = outilCourant != null ? cloneOutil(outilCourant) : null; // Clone de l'outil courant
        }

        // Méthode pour faire une copie profonde des coupes
        private Vector<Coupe> deepCopyCoupes(Vector<Coupe> originalCoupes) {
            Vector<Coupe> copie = new Vector<>();
            for (Coupe coupe : originalCoupes) {
                copie.add(coupe.clone()); // Assurez-vous que la classe Coupe implémente Cloneable correctement
            }
            return copie;
        }
        

        // Méthode pour faire une copie profonde des outils
        private Vector<Outil> deepCopyOutils(Vector<Outil> originalOutils) {
            Vector<Outil> copie = new Vector<>();
            for (Outil outil : originalOutils) {
                copie.add(cloneOutil(outil)); // Clone chaque outil
            }
            return copie;
        }
        

        // Méthode pour cloner un outil
        private Outil cloneOutil(Outil original) {
            return new Outil(original.getNom(), original.getLargeur_coupe());
        }
    }
    

    // Sauvegarde l'état actuel du panneau, des coupes, des outils et de l'outil courant
    public void saveState(Vector<Coupe> currentCoupes, Panneau panneau, Vector<Outil> currentOutils, Outil outilCourant) {
           System.out.println("État sauvegardé - outils : " + currentOutils);
        undos.push(new Snapshot(currentCoupes, panneau, currentOutils, outilCourant));
        redos.clear();
    }

    // Restaure l'état précédent
    public Snapshot undo() {
        if (!undos.isEmpty()) {
            Snapshot currentState = undos.pop();
            redos.push(currentState);
               System.out.println("État restauré après Undo - outils : " + currentState.outils);
            return !undos.isEmpty() ? undos.peek() : null;
        }
        System.out.println("Aucune action à annuler (Undo).");
        return null;
    }

    // Rétablit l'état suivant
    public Snapshot redo() {
        if (!redos.isEmpty()) {
            Snapshot redoState = redos.pop();
            undos.push(redoState);
            return redoState;
        }
        System.out.println("Aucune action à rétablir (Redo).");
        return null;
    }

    public boolean canUndo() {
        return !undos.isEmpty();
    }

    public boolean canRedo() {
        return !redos.isEmpty();
    }
}