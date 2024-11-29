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
    private final Stack<Vector<Coupe>> undos = new Stack<>();
    private final Stack<Vector<Coupe>> redos = new Stack<>();

    // Sauvegarde l'état actuel avant toute modification
    public void saveState(Vector<Coupe> currentState) {
        undos.push(new Vector<>(currentState)); // Sauvegarde une copie de l'état
        redos.clear(); // Vide la pile Redo après une nouvelle action
    }

    // Annule la dernière action en restaurant l'état précédent
    public Vector<Coupe> undo() {
        if (!undos.isEmpty()) {
            Vector<Coupe> currentState = undos.pop();
            redos.push(new Vector<>(currentState)); // Sauvegarder pour Redo
            return !undos.isEmpty() ? undos.peek() : new Vector<>();
        }
        System.out.println("Aucune action à annuler (Undo).");
        return null;
    }

    // Rétablit l'action annulée
    public Vector<Coupe> redo() {
        if (!redos.isEmpty()) {
            Vector<Coupe> redoState = redos.pop();
            undos.push(new Vector<>(redoState)); // Restaurer dans Undo
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

