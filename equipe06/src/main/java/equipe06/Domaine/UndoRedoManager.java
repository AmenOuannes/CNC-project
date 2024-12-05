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
    private final Stack<State> undos = new Stack<>();
    private final Stack<State> redos = new Stack<>();

   // hethi classe tlem el logique lkol taa l copie profonde
    public static class State {
        Vector<Coupe> coupes;
        Panneau panneau;
        Vector<Outil> outils;
        Outil outilCourant;
          float coteGrille;

       State(Vector<Coupe> coupes, Panneau panneau, Vector<Outil> outils, Outil outilCourant, float coteGrille) {
        this.coupes = coupes;
        this.panneau = panneau;
        this.outils = outils;
        this.outilCourant = outilCourant;
        this.coteGrille = coteGrille;
    }
}

    
    private Vector<Coupe> deepCopyCoupes(Vector<Coupe> originalCoupes) {
        Vector<Coupe> copie = new Vector<>();
        for (Coupe coupe : originalCoupes) {
            copie.add(coupe.clone()); 
        }
        return copie;
    }

    private Vector<Outil> deepCopyOutils(Vector<Outil> originalOutils) {
        Vector<Outil> copie = new Vector<>();
        for (Outil outil : originalOutils) {
            copie.add(new Outil(outil.getNom(), outil.getLargeur_coupe())); // Suppose un constructeur de copie pour Outil
        }
        return copie;
    }

    // Méthode pour cloner un panneau
    private Panneau deepCopyPanneau(Panneau panneau) {
        return panneau != null ? panneau.clone() : null; // Suppose que Panneau implémente Cloneable
    }

   
    public void saveState(Vector<Coupe> currentCoupes, Panneau panneau, Vector<Outil> currentOutils, Outil outilCourant, float coteGrille) {
        // Créer une copie profonde de tous les éléments
        Vector<Coupe> copiedCoupes = deepCopyCoupes(currentCoupes);
        Panneau copiedPanneau = deepCopyPanneau(panneau);
        Vector<Outil> copiedOutils = deepCopyOutils(currentOutils);
        Outil copiedOutilCourant = outilCourant != null ? new Outil(outilCourant.getNom(), outilCourant.getLargeur_coupe()) : null;

       
         undos.push(new State(copiedCoupes, copiedPanneau, copiedOutils, copiedOutilCourant, coteGrille));
        redos.clear();
        System.out.println("État sauvegardé.");
    }

   
   public State undo() {
    if (undos.size() <= 1) {
        System.out.println("Aucune action à annuler (Undo).");
        return null;
    }

    // Déplacer l'état courant vers la pile Redo
    State currentState = undos.pop();
    redos.push(currentState);

    // Retourner le nouvel état courant
    State previousState = undos.peek();
    System.out.println("Undo effectué.");
    return previousState;
}

    public State redo() {
    if (!canRedo()) {
        System.out.println("Aucune action à rétablir (Redo).");
        return null;
    }

    // Déplacer l'état de Redo vers Undo
    State redoState = redos.pop();
    undos.push(redoState);

    System.out.println("Redo effectué.");
    return redoState;
}


      
    public boolean canUndo() {
        return !undos.isEmpty();
    }

    public boolean canRedo() {
        return !redos.isEmpty();
    }

    
    public Vector<Coupe> getCurrentCoupes() {
        return canUndo() ? deepCopyCoupes(undos.peek().coupes) : null;
    }

    public Panneau getCurrentPanneau() {
        return canUndo() ? deepCopyPanneau(undos.peek().panneau) : null;
    }

    public Vector<Outil> getCurrentOutils() {
        return canUndo() ? deepCopyOutils(undos.peek().outils) : null;
    }

    public Outil getCurrentOutilCourant() {
        return canUndo() ? new Outil(undos.peek().outilCourant.getNom(), undos.peek().outilCourant.getLargeur_coupe()) : null;
    } }