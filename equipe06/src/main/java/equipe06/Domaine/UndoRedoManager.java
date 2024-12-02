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
import java.util.Vector;

public class UndoRedoManager {
    private final Stack<Snapshot> undos = new Stack<>();
    private final Stack<Snapshot> redos = new Stack<>();

    // Classe interne pour capturer un instantané
    public static class Snapshot {
        Vector<Coupe> coupes;
        Panneau panneau;

        Snapshot(Vector<Coupe> coupes, Panneau panneau) {
            this.coupes = deepCopyCoupes(coupes); // Copie profonde des coupes
            this.panneau = panneau.clone();       // Clone du panneau
          
        }

        // Méthode pour faire une copie profonde des coupes
        private Vector<Coupe> deepCopyCoupes(Vector<Coupe> originalCoupes) {
            Vector<Coupe> copie = new Vector<>();
            for (Coupe coupe : originalCoupes) {
                copie.add(coupe.clone()); // Assurez-vous que la classe Coupe implémente Cloneable correctement
            }
            return copie;
        }
    }

    // Sauvegarde l'état actuel du panneau et des coupes
    public void saveState(Vector<Coupe> currentCoupes, Panneau panneau) {
        undos.push(new Snapshot(currentCoupes, panneau));
        redos.clear();
    }

    // Restaure l'état précédent
    public Snapshot undo() {
        if (!undos.isEmpty()) {
            Snapshot currentState = undos.pop();
            redos.push(currentState);
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
