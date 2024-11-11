package equipe06.gui;

import equipe06.gui.MainWindow;
import equipe06.Domaine.Repere; // mainwimdow.controleur.repere ou bien ajout classe convertir(fait les fonctions du repere) dans gui
import javax.swing.*;
import java.awt.*;
import equipe06.drawing.Afficheur;
import equipe06.Domaine.Controleur;
import equipe06.Domaine.PanneauDTO;

/**
 * Classe PanneauVue modifiée pour dessiner la table CNC et le panneau avec un facteur d'échelle.
 */
public class PanneauVue extends JPanel {
    private MainWindow mainWindow;
    public int largeurPixelsTable; // Dimensions en pixels de la table CNC
    public int hauteurPixelsTable;
    private int largeurPixelsPanneau; // Dimensions en pixels du panneau au-dessus de la table CNC
    private int hauteurPixelsPanneau;

    private Repere repere;
    private Controleur controleur;
    private int lastClickX = -1;
    private int lastClickY = -1;
    public boolean deleteTriggered = false;
    public boolean modifyTriggered;
    private static final double SCALE_FACTOR = 0.09; // Facteur d'échelle de 10%

    private boolean peutCreerCoupe = false;  // bool pour savoir si si l'utilisateur veut créer une coupe ou non

    public PanneauVue(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.repere = new Repere(); // on doit passer par mainWindow puis controleur
        this.controleur = Controleur.getInstance();
        PanneauDTO panneauDTO = controleur.getPanneau();

        // Conversion des dimensions de la table CNC (3m x 1.5m) en appliquant un facteur d'échelle
        this.largeurPixelsTable = (int) (repere.convertirEnPixels(3000) * SCALE_FACTOR); // 3 mètres en mm
        this.hauteurPixelsTable = (int) (repere.convertirEnPixels(1500) * SCALE_FACTOR); // 1.5 mètres en mm

        // Conversion des dimensions du panneau au-dessus (0.9144m x 1.2192m) avec facteur d'échelle
        this.largeurPixelsPanneau = (int) (repere.convertirEnPixels(panneauDTO.getLargeur()) * SCALE_FACTOR);
        this.hauteurPixelsPanneau = (int) (repere.convertirEnPixels(panneauDTO.getLongueur()) * SCALE_FACTOR);

        // Définir la taille préférée du panneau basé sur la table CNC pour s'assurer qu'elle s'ajuste correctement
        this.setPreferredSize(new Dimension(largeurPixelsTable + 100, hauteurPixelsTable + 100));
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                drawingPanelMouseClicked(evt);
            }
        });
    }

    private void drawingPanelMouseClicked(java.awt.event.MouseEvent evt) {
        if (peutCreerCoupe) {
            lastClickX = evt.getX(); // Store the X coordinate of the click
            lastClickY = evt.getY();
            repaint(); // Trigger a repaint to update the drawing
            peutCreerCoupe = false; // Réinitialise la bool de la création de la coupe
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner la table CNC en gris clair avec une bordure noire
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(50, 50, largeurPixelsTable, hauteurPixelsTable); // Dessiner la table CNC
        g.setColor(Color.BLACK);
        g.drawRect(50, 50, largeurPixelsTable, hauteurPixelsTable); // Bordure de la table CNC

        // Dessiner les axes X et Y
        dessinerAxes(g);

        // Utiliser l'Afficheur pour dessiner les autres éléments (panneau, coupes, etc.)
        Afficheur afficheur = new Afficheur(mainWindow.controleur);
        afficheur.DessinerPanneau(g, SCALE_FACTOR, hauteurPixelsTable);

        if (modifyTriggered) {
            afficheur.dessinerCoupeModifie(g, 0.1f, hauteurPixelsTable);
        } else {
            afficheur.dessinerCoupe(g, lastClickX, lastClickY, 0.1f, hauteurPixelsTable);
        }

        lastClickX = -1;
        lastClickY = -1;
        modifyTriggered = false;
    }

    private void dessinerAxes(Graphics g) {
        g.setColor(Color.BLACK);

        // Dessiner l'axe X (horizontal)
        int xStart = 50; // Position initiale en pixels, aligné avec la table CNC
        int yPosition = 50 + hauteurPixelsTable; // Directement aligné avec le bas de la table CNC
        g.drawLine(xStart, yPosition, xStart + largeurPixelsTable, yPosition); // Axe X

        // Ajouter des graduations sur l'axe X tous les 200 mm
        for (int i = 0; i <= 3000; i += 200) {
            int xPos = xStart + (int) (i * 3.78 * SCALE_FACTOR);
            g.drawLine(xPos, yPosition - 5, xPos, yPosition + 5);
            g.drawString(String.valueOf(i), xPos - 10, yPosition + 20);
        }

        // Dessiner l'axe Y (vertical)
        int yStart = 50; // Position initiale en haut de la table CNC, sans décalage supplémentaire
        int xPosition = 50; // Aligné directement à gauche de la table CNC
        g.drawLine(xPosition, yStart, xPosition, yStart + hauteurPixelsTable); // Axe Y

        // Ajouter des graduations sur l'axe Y tous les 100 mm
        for (int i = 0; i <= 1500; i += 100) {
            // Positionner les graduations à partir du bas (hauteur totale moins la position)
            int yPos = yStart + hauteurPixelsTable - (int) (i * 3.78 * SCALE_FACTOR);
            g.drawLine(xPosition - 5, yPos, xPosition + 5, yPos);
            g.drawString(String.valueOf(i), xPosition - 45, yPos + 5);
        }
    }

    // Activer la création de la coupe
    public void activerCreationCoupe() {
        this.peutCreerCoupe = true;
    }

    public boolean isAttenteClicPourCoupe() {
        return peutCreerCoupe;
    }
}
