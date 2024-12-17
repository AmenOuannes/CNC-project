package equipe06.Domaine;

import java.util.Stack;
import java.util.Vector;

/**
 * Classe UndoRedoManager gérant les opérations Undo/Redo.
 * Cette version gère :
 * - Les coupes (Vector<Coupe>)
 * - Le panneau (Panneau)
 * - Les outils (Vector<Outil>)
 * - L’outil courant (Outil)
 * - La taille de la grille (float coteGrille)
 * - L’épaisseur actuelle (float epaisseurActuelle)
 *
 * Ajout de prints pour debug :
 * A chaque saveState, undo, redo, on imprime l’état des piles undos et redos.
 */
public class UndoRedoManager {
    private final Stack<State> undos = new Stack<>();
    private final Stack<State> redos = new Stack<>();

    public static class State {
        Vector<Coupe> coupes;
        Panneau panneau;
        Vector<Outil> outils;
        Outil outilCourant;
        float coteGrille;
        float epaisseurActuelle;

        State(Vector<Coupe> coupes, Panneau panneau, Vector<Outil> outils, Outil outilCourant, float coteGrille, float epaisseurActuelle) {
            this.coupes = coupes;
            this.panneau = panneau;
            this.outils = outils;
            this.outilCourant = outilCourant;
            this.coteGrille = coteGrille;
            this.epaisseurActuelle = epaisseurActuelle;
        }
    }

    // Copie profonde des coupes
    private Vector<Coupe> deepCopyCoupes(Vector<Coupe> originalCoupes) {
        Vector<Coupe> copie = new Vector<>();
        for (Coupe coupe : originalCoupes) {
            copie.add(coupe.clone());
        }
        return copie;
    }

    // Copie profonde des outils
    private Vector<Outil> deepCopyOutils(Vector<Outil> originalOutils) {
        Vector<Outil> copie = new Vector<>();
        for (Outil outil : originalOutils) {
            copie.add(outil.clone());
        }
        return copie;
    }

    // Méthode pour cloner un panneau
    private Panneau deepCopyPanneau(Panneau panneau) {
        return panneau != null ? panneau.clone() : null; 
    }

    public void saveState(Vector<Coupe> currentCoupes, Panneau panneau, Vector<Outil> outils, Outil outilCourant, float coteGrille, float epaisseurActuelle) {
        Vector<Coupe> copiedCoupes = deepCopyCoupes(currentCoupes);
        Panneau copiedPanneau = deepCopyPanneau(panneau);
        Vector<Outil> copiedOutils = deepCopyOutils(outils);
        Outil copiedOutilCourant = (outilCourant != null) ? outilCourant.clone() : null;

        undos.push(new State(copiedCoupes, copiedPanneau, copiedOutils, copiedOutilCourant, coteGrille, epaisseurActuelle));
        redos.clear();
        System.out.println("État sauvegardé.");
        printStacks();
    }

    public State undo() {
        if (undos.size() <= 1) {
            System.out.println("Aucune action à annuler (Undo).");
            return null;
        }

        State currentState = undos.pop();
        redos.push(currentState);

        State previousState = undos.peek();
        System.out.println("Undo effectué.");
        printStacks();
        return previousState;
    }

    public State redo() {
        if (!canRedo()) {
            System.out.println("Aucune action à rétablir (Redo).");
            return null;
        }

        State redoState = redos.pop();
        undos.push(redoState);

        System.out.println("Redo effectué.");
        printStacks();
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
        if (canUndo() && undos.peek().outilCourant != null) {
            return undos.peek().outilCourant.clone();
        }
        return null;
    }

    public float getCurrentCoteGrille() {
        return canUndo() ? undos.peek().coteGrille : 0;
    }

    public float getCurrentEpaisseurActuelle() {
        return canUndo() ? undos.peek().epaisseurActuelle : 0;
    }

    // Méthode de debug pour imprimer l’état des piles
    private void printStacks() {
        System.out.println("--------------- ÉTAT DES PILES ---------------");
        System.out.println("UNDOS size: " + undos.size());
        for (int i = 0; i < undos.size(); i++) {
            State s = undos.get(i);
            System.out.println("  UNDOS[" + i + "]: coupes=" + s.coupes.size() + ", outils=" + s.outils.size());
            printCoupesInfo(s.coupes);
        }

        System.out.println("REDOS size: " + redos.size());
        for (int j = 0; j < redos.size(); j++) {
            State r = redos.get(j);
            System.out.println("  REDOS[" + j + "]: coupes=" + r.coupes.size() + ", outils=" + r.outils.size());
            printCoupesInfo(r.coupes);
        }
        System.out.println("---------------------------------------------");
    }

    private void printCoupesInfo(Vector<Coupe> coupes) {
        for (Coupe c : coupes) {
            System.out.println("    Coupe: UUID=" + c.getUUID() + ", type=" + c.getTypeCoupe());
        }
    }
}
