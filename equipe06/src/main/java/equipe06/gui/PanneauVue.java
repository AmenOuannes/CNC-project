package equipe06.gui;

import equipe06.gui.MainWindow;
import equipe06.Domaine.Repere;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import equipe06.drawing.Afficheur;
import equipe06.Domaine.Controleur;
import equipe06.Domaine.PanneauDTO;

public class PanneauVue extends JPanel {
    private MainWindow mainWindow;
    public int largeurPixelsTable;  
    public int hauteurPixelsTable;
    private int largeurPixelsPanneau; 
    private int hauteurPixelsPanneau;
    private Repere repere;
    private Controleur controleur;
    private int lastClickX = -1;
    private int lastClickY = -1;
    public boolean deleteTriggered = false;
    public boolean modifyTriggered;
    //private static final double SCALE_FACTOR = 0.09;
    private float BordureX;
    private float BordureY;

    private double zoomFactor = 1.0;
    private boolean peutCreerCoupe = false;
    private boolean peutCreerCoupeRect = false ;
    private boolean peutCreerCoupeBordure = false ;
    private boolean peutCreerCoupeL = false ;
    private boolean peutCreerCoupeH = false;
    private boolean peutCreerCoupeV = false;


    // Variables pour gérer le décalage de la vue lors du zoom
    private double offsetX = 0.0;
    private double offsetY = 0.0;
    
    // Variable pour la coupe rect (clic)
    private int rectX1 = -1;
    private int rectY1 = -1;
    private int rectX2 = -1;
    private int rectY2 = -1;
    public PanneauVue(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.repere = new Repere();
        this.controleur = Controleur.getInstance();
        PanneauDTO panneauDTO = controleur.getPanneau();

        // Conversion des dimensions de la table CNC en appliquant un facteur d'échelle
        this.largeurPixelsTable = (int) (repere.convertirEnPixelsDepuisPouces(120));
        this.hauteurPixelsTable = (int) (repere.convertirEnPixelsDepuisPouces(60));

        // Conversion des dimensions du panneau avec facteur d'échelle
        this.largeurPixelsPanneau = (int) (repere.convertirEnPixelsDepuisMm(panneauDTO.getLargeur()) );
        this.hauteurPixelsPanneau = (int) (repere.convertirEnPixelsDepuisMm(panneauDTO.getLongueur()));

        // Définir la taille préférée du panneau basé sur la table CNC
        this.setPreferredSize(new Dimension(largeurPixelsTable , hauteurPixelsTable ));

        // Ajouter un MouseListener pour la création de coupe
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                drawingPanelMouseClicked(evt);
            }
        });
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                captureRectanglePoints(evt);
            }
        });
         
        
        // Ajouter un écouteur pour la roulette de la souris
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                double oldZoomFactor = zoomFactor;

                if (notches < 0) {
                    // Molette vers le haut -> zoom avant
                    zoomFactor += 0.1;
                    if (zoomFactor > 3.0) {
                        zoomFactor = 3.0;
                    }
                } else {
                    // Molette vers le bas -> zoom arrière
                    zoomFactor -= 0.1;
                    if (zoomFactor < 1.0) {
                        zoomFactor = 1.0; // Réinitialiser à l'état initial
                        resetView();
                    }
                }

                // Si le zoomFactor est revenu à 1, réinitialiser complètement la vue
                if (zoomFactor == 1.0) {
                    resetView();
                } else {
                    // Sinon, ajuster le décalage proportionnellement au facteur de zoom
                    double scaleChange = zoomFactor / oldZoomFactor;
                    int mouseX = e.getX();
                    int mouseY = e.getY();

                    offsetX = mouseX - (mouseX - offsetX) * scaleChange;
                    offsetY = mouseY - (mouseY - offsetY) * scaleChange;
                }

                repaint(); // Redessiner après le changement de zoom
            }
        });
    }

    private void resetView() {
        // Réinitialiser les variables de zoom et de décalage
        zoomFactor = 1.0;
        offsetX = 0.0;
        offsetY = 0.0;
        repaint(); // Redessiner la vue pour l'état initial
    }

    private void drawingPanelMouseClicked(java.awt.event.MouseEvent evt) {
        if (peutCreerCoupeH || peutCreerCoupeV) {
            lastClickX = evt.getX();
            lastClickY = evt.getY();
            repaint();
            //peutCreerCoupeH = false;
            //peutCreerCoupeV = false;

        } else {
            /*// Zoom centré sur le clic
            double oldZoomFactor = zoomFactor;
            zoomFactor += 0.2;
            if (zoomFactor > 3.0) {
                zoomFactor = 3.0; // Limiter le zoom maximal
            }

            // Calculer la différence pour recadrer le point autour du clic
            double scaleChange = zoomFactor / oldZoomFactor;
            int clickX = evt.getX();
            int clickY = evt.getY();

            offsetX = clickX - (clickX - offsetX) * scaleChange;
            offsetY = clickY - (clickY - offsetY) * scaleChange;

            repaint();*/
        }
    }
// TODO dessiner tout le vecteur, amen
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Appliquer le facteur de zoom et le décalage
        g2d.translate(offsetX, offsetY);
        g2d.scale(zoomFactor, zoomFactor);

        // Dessiner la table CNC en gris clair avec une bordure noire
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, largeurPixelsTable, hauteurPixelsTable);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, largeurPixelsTable-1, hauteurPixelsTable-1);

        // Dessiner les axes X et Y
        //dessinerAxes(g2d);

        // Utiliser l'Afficheur pour dessiner les autres éléments (panneau, coupes, etc.)
        Afficheur afficheur = new Afficheur(mainWindow.controleur);
        afficheur.DessinerPanneau(g, hauteurPixelsTable);
        //for coupe in coupes if coupe kedhe afficheur.kedhe
        if (modifyTriggered) {
            afficheur.dessinerCoupeModifie(g, hauteurPixelsTable);
        }
        if (peutCreerCoupeH){
            System.out.printf("peutCreerCoupeH MRGLA");

            afficheur.dessinerCoupe(g, lastClickX, lastClickY, hauteurPixelsTable, largeurPixelsTable, false);
        }
        if (peutCreerCoupeV){
            System.out.printf("peutCreerCoupeV MRGLA");
            afficheur.dessinerCoupe(g, lastClickX, lastClickY, hauteurPixelsTable, largeurPixelsTable, true);
        }
        if (peutCreerCoupeRect) {
            afficheur.dessinerRectangleAVdeuxpoints(g, rectX1, rectY1, rectX2, rectY2);
        }
        if (peutCreerCoupeL) {
          afficheur.dessinerL(g, rectX1, rectY1, rectX2, rectY2);
         }
        if (peutCreerCoupeBordure) {
            afficheur.dessinerBordure(g, BordureX, BordureY, hauteurPixelsTable);
        }

        // Réinitialiser après dessin
        lastClickX = -1;
        lastClickY = -1;
        rectX1 = -1;
        rectY1 = -1;
        rectY1 = -1;
        rectY2 = -1;
        modifyTriggered = false;
        peutCreerCoupeRect = false;
        peutCreerCoupeBordure = false;
        peutCreerCoupeL = false;
    }

    private void dessinerAxes(Graphics g) {
        g.setColor(Color.BLACK);

        // Dessiner l'axe X (horizontal)
        int xStart = 50;
        int yPosition = 50 + hauteurPixelsTable;
        g.drawLine(xStart, yPosition, xStart + largeurPixelsTable, yPosition);

        // Ajouter des graduations sur l'axe X tous les 200 mm
        for (int i = 0; i <= 3000; i += 200) {
            int xPos = xStart + (int) (i * 3.78  * zoomFactor);
            g.drawLine(xPos, yPosition - 5, xPos, yPosition + 5);
            g.drawString(String.valueOf(i), xPos - 10, yPosition + 20);
        }

        // Dessiner l'axe Y (vertical)
        int yStart = 50;
        int xPosition = 50;
        g.drawLine(xPosition, yStart - 130, xPosition, yStart + hauteurPixelsTable - 130);

        // Ajouter des graduations sur l'axe Y tous les 100 mm
        for (int i = 0; i <= 1500; i += 100) {
            int yPos = yStart + hauteurPixelsTable - (int) (i * 3.78  * zoomFactor);
            g.drawLine(xPosition - 5, yPos, xPosition + 5, yPos);
            g.drawString(String.valueOf(i), xPosition - 45, yPos + 5);
        }
    }
//<<<<<<< Updated upstream

    // Activer la création de la coupe
    //public void activerCreationCoupe() { 
        // Hedi houni zid les variables teeks true false switch case
        // starr 788 fi mainwindow
//=======
    public void activerCreationCoupeL() {
        this.peutCreerCoupeL = true;
    }
    public void activerCreationCoupeH() {
//>>>>>>> Stashed changes
        this.peutCreerCoupeH = true;
    }
    public void activerCreationCoupeV() {
//>>>>>>> Stashed changes
        this.peutCreerCoupeV = true;
    }
    public void activerCreationCoupeRect() {
        this.peutCreerCoupeRect = true;
    }
    public boolean isAttenteClicPourCoupe() {
        return peutCreerCoupe;
    }
    private void captureRectanglePoints(java.awt.event.MouseEvent evt) {
        if (rectX1 == -1 && rectY1 == -1 && (peutCreerCoupeRect || peutCreerCoupeL)) {
            rectX1 = evt.getX();
            rectY1 = evt.getY();
        } else {
            rectX2 = evt.getX();
            rectY2 = evt.getY();
            repaint();
        }
    }
    
    public void activerCreationCoupeBordure() {
        this.peutCreerCoupeBordure = true;
    }
    
    public void DimensionsBordure(float BordureXValue, float BordureYValue) {
        this.BordureX = BordureXValue;
        this.BordureY = BordureYValue;
        this.peutCreerCoupeBordure = true;
        repaint();
    }
    
}