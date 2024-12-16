package equipe06.gui;

import equipe06.Domaine.CoupeDTO;
import equipe06.gui.MainWindow;
import equipe06.Domaine.Repere;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import equipe06.drawing.Afficheur;
import equipe06.Domaine.Controleur;
import equipe06.Domaine.PanneauDTO;
import java.util.Vector;

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
    private int rectX3 = -1;
    private int rectY3 = -1;
    public boolean deleteTriggered = false;
    public boolean modifyTriggered;
    private float BordureX;
    private float BordureY;
    private boolean grilleMagnetique = false;
    private double zoomFactor = 1.0;
    private boolean peutCreerCoupe = false;
    private boolean peutCreerCoupeRect = false;
    private boolean peutCreerCoupeBordure = false;
    private boolean peutCreerCoupeL = false;
    private boolean peutCreerCoupeH = false;
    private boolean peutCreerCoupeV = false;
    private boolean peutCreerZoneInterdite = false;
    private boolean deplacementGraphique = false;
    private float AxeRelatif;
    private boolean modifyTriggeredA = false;
    private boolean modifyTriggeredR = false;
    private boolean afficherGrille = false;
    private float longueur_modify;
    private float largeur_modify;
    private boolean EditRef = false;
    private boolean ModifOutil = false;
    private Vector<Point> pointsEnregistres = new Vector<>();
    private float intervalleGrilleX;

    // Variables pour gérer le décalage de la vue lors du zoom
    private double offsetX = 0.0;
    private double offsetY = 0.0;
    public Point myPoint;
    private int currentMouseX = -1;
    private int currentMouseY = -1;
    // Variable pour la coupe rect (clic)
    private int rectX1 = -1;
    private int rectY1 = -1;
    private int rectX2 = -1;
    private int rectY2 = -1;
      // Variables pour le curseur virtuel
    private int cursorX = -1;
    private int cursorY = -1;

    // **Ajout du JLabel pour afficher les coordonnées**
    private JLabel coordLabel;

    public PanneauVue(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.repere = Repere.getInstance();
        this.controleur = Controleur.getInstance();
        PanneauDTO panneauDTO = controleur.getPanneau();

        // Conversion des dimensions de la table CNC en appliquant un facteur d'échelle
        this.largeurPixelsTable = (int) (repere.convertirEnPixelsDepuisPouces(120));
        this.hauteurPixelsTable = (int) (repere.convertirEnPixelsDepuisPouces(60));

        // Conversion des dimensions du panneau avec facteur d'échelle
        this.largeurPixelsPanneau = (int) (repere.convertirEnPixelsDepuisMm(panneauDTO.getLargeur()));
        this.hauteurPixelsPanneau = (int) (repere.convertirEnPixelsDepuisMm(panneauDTO.getLongueur()));

        
        this.setPreferredSize(new Dimension(largeurPixelsTable, hauteurPixelsTable));

       
        this.setLayout(null);

       
        coordLabel = new JLabel();
        coordLabel.setOpaque(true);
        coordLabel.setBackground(Color.WHITE); // Fond blanc
        coordLabel.setForeground(Color.BLACK); // Texte en noir pour contraste
        coordLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        coordLabel.setVisible(false); // Caché par défaut
        coordLabel.setFont(new Font("Arial", Font.PLAIN, 10)); // Police plus petite
        this.add(coordLabel);

      
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(peutCreerCoupeL || peutCreerCoupeRect || peutCreerCoupeV ||
                   peutCreerCoupeH || modifyTriggeredA || modifyTriggeredR || 
                   deleteTriggered || EditRef || ModifOutil || peutCreerZoneInterdite ) {
                    captureRectanglePoints(evt);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                coordLabel.setVisible(false); 
            }
        });

        this.addMouseListener(new MouseAdapter() {
            private int startX, startY; // Variables to hold the start position

            @Override
            public void mousePressed(MouseEvent evt) {
                // Save the starting position when mouse press occurs
                startX = evt.getX();
                startY = evt.getY();
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                // Capture the released point
                int endX = evt.getX();
                int endY = evt.getY();
                if(grilleMagnetique){
                    int n = (int) ( endX / intervalleGrilleX);
                    if(endX % intervalleGrilleX >= (intervalleGrilleX/2)){
                        n = (n+1)*Repere.getInstance().convertirEnPixelsDepuisMm(intervalleGrilleX);
                    }
                    else{
                        n = (n)*Repere.getInstance().convertirEnPixelsDepuisMm(intervalleGrilleX);
                    }
                    int y = (int) ( endY / intervalleGrilleX);
                    if(endY % intervalleGrilleX >= (intervalleGrilleX/2)){
                        y = (y+1)*Repere.getInstance().convertirEnPixelsDepuisMm(intervalleGrilleX);
                    }
                    else{
                        y = (y)*Repere.getInstance().convertirEnPixelsDepuisMm(intervalleGrilleX);
                    }
                    endX = n;
                    endY = y;

                }

                // Call your controller method with start and end points
                if(deplacementGraphique) {
                    controleur.modifDeplacement(startX, startY, endX, endY);
                    deplacementGraphique = false;
                }

                System.out.println("Dragged from (" + startX + ", " + startY +
                        ") to (" + endX + ", " + endY + ")");
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent evt) {
              
                currentMouseY = ajusterCoordonneePourVueY(evt.getY(), offsetY, zoomFactor);
                currentMouseX = ajusterCoordonneePourVue(evt.getX(), offsetX, zoomFactor);

               
                mainWindow.updateCoord(currentMouseX, currentMouseY);

                // Convertir les coordonnées en mm
                float xMm = repere.convertirEnMmDepuisPixels(currentMouseX);
                float yMm = repere.convertirEnMmDepuisPixels(currentMouseY);

              
                coordLabel.setText(String.format("(%.2f mm, %.2f mm)", xMm, yMm));

              
                coordLabel.setSize(coordLabel.getPreferredSize());

               
                int labelX = evt.getX() + 10; // 10 pixels à droite du curseur
                int labelY = evt.getY() - coordLabel.getHeight() - 10; // 10 pixels au-dessus du curseur

                
                if (labelY < 0) {
                    labelY = evt.getY() + 10; 
                }

              
                if (labelX + coordLabel.getWidth() > PanneauVue.this.getWidth()) {
                    labelX = evt.getX() - coordLabel.getWidth() - 10; 
                }

                // Appliquer la nouvelle position au label
                coordLabel.setLocation(labelX, labelY);
                coordLabel.setVisible(true);
                repaint();
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
    public void setCoord(int x, int y) {
        this.cursorX = x;
        this.cursorY = y;
        repaint();
    }

    private int ajusterCoordonneePourVue(int coordonnee, double offset, double zoomFactor) {
        return (int) ((coordonnee - offset) / zoomFactor);
    }

    private int ajusterCoordonneePourVueY(int coordonnee, double offset, double zoomFactor) {
        // Inverser les coordonnées Y
        return (int) ((hauteurPixelsTable - coordonnee - offset) / zoomFactor);
    }

    public void setAfficherGrille(boolean afficher) {
        this.afficherGrille = afficher;
        repaint(); // Redessiner le panneau après la mise à jour
    }

    private void captureRectanglePoints(java.awt.event.MouseEvent evt) {
        if (rectX1 == -1 && rectY1 == -1 ) {
            rectX1 = ajusterCoordonneePourVue(evt.getX(), offsetX, zoomFactor);
            rectY1 = ajusterCoordonneePourVue(evt.getY(), offsetY, zoomFactor);

            if (deleteTriggered) {
                Point p = new Point(rectX1, rectY1);
                controleur.supprimerCoupeSurClic(p);
                System.out.print("Point pour supprimer\n");
                deleteTriggered = false;
                repaint();
                rectX1 = -1;
                rectY1 = -1;
            }
            else if(modifyTriggeredR){
                Point Ref = new Point(rectX1, rectY1);
                System.out.print("Point pour modifier carre\n");
                controleur.modifierCoupeCarre(longueur_modify, largeur_modify, Ref);
                repaint();
                modifyTriggeredR = false;
                rectX1 = -1;
                rectY1 = -1;
            }
            else if(modifyTriggeredA){
                Point Ref = new Point(rectX1, rectY1);
                System.out.print("Point pour modifier un axe\n");
                controleur.modifierCoupeAxiale(AxeRelatif, Ref);
                repaint();
                modifyTriggeredA = false;
                rectX1 = -1;
                rectY1 = -1;
            }
            else if(ModifOutil){
                Point Ref = new Point(rectX1, rectY1);
                System.out.print("Point pour modifier outil\n");
                controleur.modifierCoupeOutil(Ref);
                repaint();
                ModifOutil = false;
                rectX1 = -1;
                rectY1 = -1;
            }
            System.out.print("Point 1\n");

        } else if (rectX2 == -1 && rectY2 == -1) {
            rectX2 = ajusterCoordonneePourVue(evt.getX(), offsetX, zoomFactor);
            rectY2 = ajusterCoordonneePourVue(evt.getY(), offsetY, zoomFactor);
            System.out.print("Point 2\n");

            Point Ref = new Point(rectX1, rectY1);
            Point Axe = new Point(rectX2, rectY2);
            if (peutCreerCoupeV) {
                controleur.CreerCoupeAxiale(Axe, true, Ref);
                repaint();
                System.out.print("Coupe créé avec succès!\n");
                peutCreerCoupeV = false;
                rectX1 = -1;
                rectY1 = -1;
                rectX2 = -1;
                rectY2 = -1;
            }
            else if (peutCreerCoupeH) {
                controleur.CreerCoupeAxiale(Axe, false, Ref);
                repaint();
                peutCreerCoupeH = false;
                System.out.print("Coupe créé avec succès!\n");
                rectX1 = -1;
                rectY1 = -1;
                rectX2 = -1;
                rectY2 = -1;
            }
            else if (peutCreerCoupeL) {
                controleur.CreerCoupeL(Ref, Axe);
                repaint();
                peutCreerCoupeL = false;
                rectX1 = -1;
                rectY1 = -1;
                rectX2 = -1;
                rectY2 = -1;
            }
            else if (peutCreerZoneInterdite) {
                controleur.CreerZoneInterdite(Ref, Axe);
                repaint();
                peutCreerZoneInterdite = false;
                rectX1 = -1;
                rectY1 = -1;
                rectX2 = -1;
                rectY2 = -1;
            }
            if(EditRef){
                controleur.EditerRef(Ref, Axe);
                repaint();
                EditRef = false;
                rectX1 = -1;
                rectY1 = -1;
                rectX2 = -1;
                rectY2 = -1;
            }

        }
        else if (peutCreerCoupeRect) {
            rectX3 = ajusterCoordonneePourVue(evt.getX(), offsetX, zoomFactor);
            rectY3 = ajusterCoordonneePourVue(evt.getY(), offsetY, zoomFactor);
            System.out.print("Point 3\n");

            Point Ref = new Point(rectX1, rectY1);
            Point Origin = new Point(rectX2, rectY2);
            Point Dest = new Point(rectX3, rectY3);

            controleur.CreerCoupeRect(Origin, Dest, Ref);
            System.out.println("Coupe créé avec succès \n");
            repaint();
            peutCreerCoupeRect = false;
            rectX1 = -1;
            rectY1 = -1;
            rectX2 = -1;
            rectY2 = -1;
            rectX3 = -1;
            rectY3 = -1;

        }
        else {
            System.out.print("Rien\n");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Appliquer le facteur de zoom et le décalage
        g2d.translate(offsetX, offsetY);
        g2d.scale(zoomFactor, zoomFactor);
        // Dessiner la table CNC en blanc avec une bordure noire
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, largeurPixelsTable, hauteurPixelsTable);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, largeurPixelsTable - 1, hauteurPixelsTable - 1);
        // Utiliser l'Afficheur pour dessiner les autres éléments (panneau, coupes, etc.)
        Afficheur afficheur = new Afficheur(mainWindow.controleur);
        afficheur.DessinerPanneau(g, hauteurPixelsTable);
        // Dessiner les axes X et Y
        //dessinerAxes(g2d);
        if (afficherGrille) {
            afficheur.dessinerGrille(g, hauteurPixelsTable, largeurPixelsTable, intervalleGrilleX);
        }

        Vector<CoupeDTO> Coupes = controleur.getCoupes();
        for(CoupeDTO coupe: Coupes) {
            if(coupe.getTypeCoupeDTO().equals("V") && coupe.isComposanteDTO()) {
                afficheur.dessinerCoupeAxiale(g, coupe, hauteurPixelsTable, largeurPixelsTable, true);
            }
            else if(coupe.getTypeCoupeDTO().equals("H") && !coupe.isComposanteDTO())  {
                afficheur.dessinerCoupeAxiale(g, coupe, hauteurPixelsTable, largeurPixelsTable, false);
            }
            else if(coupe.getTypeCoupeDTO().equals("Bordure")) {
                afficheur.dessinerBordure(g, coupe.getBordureXDTO(), coupe.getBordureYDTO(), hauteurPixelsTable, coupe);
            }
            else if(coupe.getTypeCoupeDTO().equals("Rect")){
                afficheur.dessinerRectangleAVdeuxpoints(g, coupe.getPointOrigineDTO(), coupe.getPointDestinoDTO(), coupe);
            }
            else if (coupe.getTypeCoupeDTO().equals("L"))  {
                Point pointOrigine = coupe.getPointOrigineDTO();
                Point pointDestino = coupe.getPointDestinoDTO();
                afficheur.dessinerL(g, pointOrigine, pointDestino, coupe);
            }
            else if(coupe.getTypeCoupeDTO().equals("ZoneInterdite")){
                afficheur.dessinerZoneInterdite(g, coupe.getPointOrigineDTO(), coupe.getPointDestinoDTO());
            }
        }
      // Dessiner le curseur virtuel si les coordonnées sont valides
        if (cursorX != -1 && cursorY != -1) {
            g2d.setColor(Color.RED);
            int cursorSize = 10; // Taille du curseur
            g2d.fillOval(cursorX - cursorSize / 2, cursorY - cursorSize / 2, cursorSize, cursorSize);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(cursorX - cursorSize / 2, cursorY - cursorSize / 2, cursorSize, cursorSize);
        }
    }

    

    public void setIntervalleGrille(float intervalleX) {
        this.intervalleGrilleX = intervalleX;
    }

    /* A SUPP
    public void dessinerGrille(Graphics g) {
        g.setColor(Color.BLACK);
        Repere repere = Repere.getInstance();
        int intervallePixelsX = repere.convertirEnPixelsDepuisMm(intervalleGrilleX);

        // Dessiner les lignes verticales (X)
        for (int i = 0; i <= largeurPixelsTable / intervallePixelsX; i++) {
            int x = i * intervallePixelsX;
            g.drawLine(x, 0, x, hauteurPixelsTable);
        }

        // Dessiner les lignes horizontales (Y)
        for (int i = 0; i <= hauteurPixelsTable / intervallePixelsX; i++) {
            int y = hauteurPixelsTable - i * intervallePixelsX;
            g.drawLine(0, y, largeurPixelsTable, y);
        }
    }
    */

    public void activerCreationCoupeL() {
        this.peutCreerCoupeL = true;
    }

    public void activerCreationCoupeH() {
        this.peutCreerCoupeH = true;
    }

    public void activerCreationCoupeV() {
        this.peutCreerCoupeV = true;
    }

    public void activerCreationCoupeRect() {
        this.peutCreerCoupeRect = true;
    }
    
    public void activerCreationZoneInterdite() {
        this.peutCreerZoneInterdite = true;
    }

    public boolean isAttenteClicPourCoupe() {
        return peutCreerCoupe;
    }

    public void activerCreationCoupeBordure() {
        this.peutCreerCoupeBordure = true;
    }

    /*   
    public void DimensionsBordure(float BordureXValue, float BordureYValue) {
        this.BordureX = BordureXValue;
        this.BordureY = BordureYValue;
        this.peutCreerCoupeBordure = true;
        repaint();
    }
    */
    
    public void activerSuppressionCoupe() {
        this.deleteTriggered = true;
    }

    public void activerModifierCoupeAxiale(float axe) {
        modifyTriggeredA = true;
        AxeRelatif = axe;
    }

    public void activerModifierR(float longueur, float largeur) {
        modifyTriggeredR = true;
        longueur_modify = longueur;
        largeur_modify = largeur;
    }
    
    // Zeyda ki walla fama grille + mechekl fl conversion decallage sghir fl ekher maa l grille
    /*
    private void dessinerAxes(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        FontMetrics metrics = g.getFontMetrics();

        Repere repere = Repere.getInstance();
        // Axe X (horizontal, sur la bordure inférieure)
        int yPosition = hauteurPixelsTable;
        g.drawLine(0, yPosition, largeurPixelsTable, yPosition);

        for (int i = 0; i <= 3000; i += 100) {
            int xPos = repere.convertirEnPixelsDepuisMm(i);
            g.drawLine(xPos, yPosition - 5, xPos, yPosition + 5);
            String texte = String.valueOf(i);
            int texteLargeur = metrics.stringWidth(texte);
            g.drawString(texte, xPos - texteLargeur / 2, yPosition + metrics.getHeight() + 5);
        }

        // Axe Y (vertical, sur la bordure gauche)
        int xPosition = 0;
        g.drawLine(xPosition, 0, xPosition, hauteurPixelsTable);

        for (int i = 0; i <= 1500; i += 100) {
            int yPos = hauteurPixelsTable - repere.convertirEnPixelsDepuisMm(i);
            g.drawLine(xPosition - 5, yPos, xPosition + 5, yPos);
            String texte = String.valueOf(i);
            int texteHauteur = metrics.getHeight();
            g.drawString(texte, xPosition - metrics.stringWidth(texte) - 10, yPos + texteHauteur / 4);
        }
    }
    */

    public void activerEditRef() {
        EditRef = true;
    }
    
    public void activerModifOutil(){
        ModifOutil = true;
    }

    public void setDeplacementGraphique() { deplacementGraphique = true;
    }
    public void changeMagnetique(){
        if(grilleMagnetique) grilleMagnetique = false;
        else grilleMagnetique = true;
    }

 /*   
    private void enregistrerPointAvantCoupe(Point point) {
        if (peutCreerCoupeL || peutCreerCoupeRect || peutCreerCoupeV || peutCreerCoupeH) {
            pointsEnregistres.add(point);
            // Log immédiat pour chaque ajout
            System.out.println("Point enregistré avec succès : (" + point.x + ", " + point.y + ")");
            System.out.println("Total des points enregistrés : " + pointsEnregistres.size());
        }
    }
    */
}
