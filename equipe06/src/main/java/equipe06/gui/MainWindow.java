/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package equipe06.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

import equipe06.Domaine.Repere;
import equipe06.gui.PanneauVue;
import equipe06.Domaine.Controleur;
import equipe06.Domaine.CoupeDTO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import equipe06.Domaine.OutilDTO;
import java.util.Stack;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends javax.swing.JFrame {

    public Controleur controleur;
    private PanneauVue panneauVue;
    private boolean messageAffiche = false;
    private CoupeDTO coupeSelectionnee; // Coupe actuellement sélectionnée

    private static double SCALE_FACTOR = 0.25;
    private DefaultListModel<String> outilsListModel;

    /**
     * Creates new form MainWindow
     */
   public MainWindow() {
        initComponents();
  

        // Initialiser `PanneauVue` et l'ajouter à l'endroit de `PanneauVisualisation`
        panneauVue = new PanneauVue(this);
     
  
        // Obtenir l'instance de Controleur et établir la communication
        controleur = Controleur.getInstance();
        
        controleur.setMainWindow(this); // lien etablie ici pour la mise a jour distance x
        // Configurer `PanneauVisualisation`
        mettreAJourComboBoxOutil(); // Pour le comboBox
        controleur.mettreAJourTableauOutils(); // Pour le JTable
        PanneauVisualisation.setLayout(new BorderLayout());
         
        //System.out.println("Width: " + PanneauVisualisation.getWidth());
        //System.out.println("Height: " + PanneauVisualisation.getHeight());
        
        // Ajouter `panneauVue` dans `PanneauVisualisation`
        PanneauVisualisation.add(panneauVue, BorderLayout.CENTER);
        // Ajout d'un `MouseListener` à `MainWindow` pour détecter les clics en dehors du panneau
        // si L'utilisateur clic sur le bouton cree coupe apres ca il ne clic pas sur le panneau un settext s'affiche
        // principe : détecter les clics en dehors du panneau quand la bool panneauvue.peutCreerCoupe = true
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (panneauVue.isAttenteClicPourCoupe() && !evt.getSource().equals(panneauVue)) {//probleme condition
                    message.setText("Veuillez cliquer sur le panneau pour créer la coupe.");
                }
                else {
        
            // Si la condition n'est pas remplie, effacer le message précédent
            message.setText("");
        }}
        });
        
        
        // Ajuster la fenêtre à la taille totale des composants
        pack();
    }

    public JTextField getDistanceX() {
        return DistanceX;
    }
    
    // permet de pour mettre à jour le champ distancex avec la valeur de x 
    public void afficherValeurDistanceX(float x) {
        if(!controleur.getCoupes().isEmpty())
        {
        DistanceX.setText(String.format("%.2f", x)); // Afficher la valeur avec deux décimales

        }
}
    // cette methode interage directement avec le jtabel elle vide le tableau et remettre tt les objets outils dto 
    public void afficherOutilsDansTable(Vector<OutilDTO> outils) {
        DefaultTableModel tableModel = (DefaultTableModel) tableauOutils.getModel();
        tableModel.setRowCount(0); // Vider le tableau avant de le remplir
        for (OutilDTO outil : outils) {
            tableModel.addRow(new Object[]{outil.getNomDTO(), outil.getLargeur_coupeDTO()});
        }
    } 
   
    //////////////////////////////////////////////////
 
public void updateDimensions(float x, float y) {
    Repere repere = Repere.getInstance();
    float xMm = repere.convertirEnMmDepuisPixels(x);
    float yMm = repere.convertirEnMmDepuisPixels(y);

    // Affichage dans les champs de texte
    DimX.setText(String.format("%.2f", xMm));
    DimY.setText(String.format("%.2f", yMm));
}
// ZZedtha khater naarch ken el fou9 yestaamlou feha louled lel points de reference 

public void updateCoord(float x, float y) {
    Repere repere = Repere.getInstance();
    float xMm = repere.convertirEnMmDepuisPixels(x);
    float yMm = repere.convertirEnMmDepuisPixels(y);

    // Affichage dans les champs de texte
    //CoordX.setText(String.format("%.2f", xMm));
    //CoordY.setText(String.format("%.2f", yMm));
}

public void updateDimX(float x) {
    // Mettre à jour le champ DimX
    DimX.setText(String.valueOf(x));
}

public void updateDimY(float y) {
    // Mettre à jour le champ DimX
    DimY.setText(String.valueOf(y));
}

//////////////////////////////////////////////////
public void mettreAJourComboBoxOutil() {
    Outil_Coupe.removeAllItems(); // Supprime tous les éléments existants

    // Ajouter les outils mis à jour dans le comboBox
    Vector<OutilDTO> outils = controleur.getOutils(); // Récupérer les outils à jour
    for (OutilDTO outil : outils) {
        Outil_Coupe.addItem(outil.getNomDTO()); // Ajouter chaque outil
    }
    Outil_Coupe.repaint(); // Redessiner le comboBox
}

public void mettreAJourTableauOutils() {
    DefaultTableModel model = (DefaultTableModel) tableauOutils.getModel();
    model.setRowCount(0); // Effacer les lignes actuelles

    Vector<OutilDTO> outils = controleur.getOutils(); // Récupérer les outils
    for (OutilDTO outil : outils) {
        model.addRow(new Object[]{outil.getNomDTO(), outil.getLargeur_coupeDTO()});
    }
}
public void mettreAJourPanneau(){
    panneauVue.repaint();
}
public void mettreAJourGrille(float coteGrille) {
    panneauVue.setIntervalleGrille(coteGrille);
    panneauVue.repaint();
}


private void genererGCode() {
    try {
        // Créer une instance de JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer le G-code");
        
        // Définir le nom de fichier par défaut
        fileChooser.setSelectedFile(new File("programme.gcode"));
        
        // Ouvrir la boîte de dialogue d'enregistrement
        int userSelection = fileChooser.showSaveDialog(this);
        
        // Vérifier si l'utilisateur a approuvé la sélection
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String fichierGCode = fileToSave.getAbsolutePath();
            
            // Vérifier si le fichier a l'extension .gcode, sinon l'ajouter
            if (!fichierGCode.toLowerCase().endsWith(".gcode")) {
                fichierGCode += ".gcode";
            }
    
            // Appeler l'exportation via le contrôleur
            controleur.exporterGCode(fichierGCode);
    
            // Afficher un message de confirmation avec le chemin du fichier
            JOptionPane.showMessageDialog(this, "G-code généré avec succès :\n" + fichierGCode);
        } else {
            // L'utilisateur a annulé l'opération
            JOptionPane.showMessageDialog(this, "Enregistrement du G-code annulé.");
        }
    } catch (Exception ex) {
        // Afficher un message d'erreur en cas d'exception
        JOptionPane.showMessageDialog(this, "Erreur lors de la génération du G-code : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator3 = new javax.swing.JSeparator();
        jMenuItem2 = new javax.swing.JMenuItem();
        PanneauVisualisation = new javax.swing.JPanel();
        message = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        PANlongueurX = new javax.swing.JTextField();
        PANlargeurY = new javax.swing.JTextField();
        PANprofondeurZ = new javax.swing.JTextField();
        comboBoxUnite = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        DessinerPanneau = new javax.swing.JButton();
        AfficherGrille = new javax.swing.JButton();
        SuppGrille = new javax.swing.JButton();
        GrilleX = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        Creer_Outil = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        Nom_Outil = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        Epaisseur_Outil = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        Supprimer_Outil = new javax.swing.JButton();
        ModifOutil = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableauOutils = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        ValidModifOutil = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        ModMarge = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        marge = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        PanneauContrôle = new javax.swing.JPanel();
        DefCoupe = new javax.swing.JButton();
        ModCoupe = new javax.swing.JButton();
        SuppCoupe = new javax.swing.JButton();
        DistanceX = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        Outil_Coupe = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        Type_Coupe = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        BordureX = new javax.swing.JTextField();
        BordureY = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        UniteBordure = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        DimX = new javax.swing.JTextField();
        DimY = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        DistanceY = new javax.swing.JTextField();
        ModifRef = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        CoordX = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        CoordY = new javax.swing.JTextField();
        AjouterZI = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        Nouveau_Menu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        Undo = new javax.swing.JMenuItem();
        Redo = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        Gen_Gcode = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        Import_cnc = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout PanneauVisualisationLayout = new javax.swing.GroupLayout(PanneauVisualisation);
        PanneauVisualisation.setLayout(PanneauVisualisationLayout);
        PanneauVisualisationLayout.setHorizontalGroup(
            PanneauVisualisationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 348, Short.MAX_VALUE)
        );
        PanneauVisualisationLayout.setVerticalGroup(
            PanneauVisualisationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 308, Short.MAX_VALUE)
        );

        jLabel3.setText("1.Commençons par la création de votre panneau");

        PANlongueurX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PANlongueurXActionPerformed(evt);
            }
        });

        PANlargeurY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PANlargeurYActionPerformed(evt);
            }
        });

        comboBoxUnite.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "mm", "cm", "metre", "pouce" }));
        comboBoxUnite.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxUniteItemStateChanged(evt);
            }
        });
        comboBoxUnite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxUniteActionPerformed(evt);
            }
        });

        jLabel4.setText("1.Choisissez l'unité");

        jLabel5.setText("2.Entrez la longueur :");

        jLabel6.setText("3.Entrez la largeur :");

        jLabel7.setText("4.Entrez la profondeur :");

        DessinerPanneau.setText("Dessiner Panneau");
        DessinerPanneau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DessinerPanneauActionPerformed(evt);
            }
        });

        AfficherGrille.setText("Activer Grille");
        AfficherGrille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AfficherGrilleActionPerformed(evt);
            }
        });

        SuppGrille.setText("Désactiver Grille");
        SuppGrille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuppGrilleActionPerformed(evt);
            }
        });

        jLabel27.setText("5.Gestion Grille");

        jLabel28.setText("Taille (200mm par defaut):");

        jButton4.setText("Magnétique");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PANlongueurX)
                            .addComponent(PANlargeurY)
                            .addComponent(PANprofondeurZ)
                            .addComponent(comboBoxUnite, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(DessinerPanneau, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(AfficherGrille, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel28)
                                    .addComponent(GrilleX, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(SuppGrille, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel27))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxUnite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(1, 1, 1)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PANlongueurX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GrilleX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(AfficherGrille))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PANlargeurY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SuppGrille))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PANprofondeurZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(DessinerPanneau)
                .addContainerGap(220, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("1.Panneau", jPanel1);

        jLabel9.setText("2. Personalisez votre Outil");

        Creer_Outil.setText("Créer Outil");
        Creer_Outil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Creer_OutilActionPerformed(evt);
            }
        });

        jLabel10.setText("1.Entrez le nom de l'outil");

        Nom_Outil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Nom_OutilActionPerformed(evt);
            }
        });

        jLabel12.setText("2.Entrez la largeur (mm)");

        Epaisseur_Outil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Epaisseur_OutilActionPerformed(evt);
            }
        });

        jLabel13.setText("2.Gestion des Outil");

        Supprimer_Outil.setText("Supprimer Outil");
        Supprimer_Outil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Supprimer_OutilActionPerformed(evt);
            }
        });

        ModifOutil.setText("Modifier Outil");
        ModifOutil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifOutilActionPerformed(evt);
            }
        });

        tableauOutils.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nom", "Epaisseur"
            }
        ));
        jScrollPane2.setViewportView(tableauOutils);

        jLabel16.setText("Sélectionnez un outil pour le modifier ou le supprimer.");

        ValidModifOutil.setText("Valider Modifcation");
        ValidModifOutil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ValidModifOutilActionPerformed(evt);
            }
        });

        jLabel1.setText("Un outil de 12.7 mm est par défaut à votre disposition.");

        jLabel29.setText("Marge profondeur coupe par def : 0.5(mm)");

        ModMarge.setText("Modifier la Marge");
        ModMarge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModMargeActionPerformed(evt);
            }
        });

        jButton1.setText("Afficher la Marge Actuelle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jSeparator6)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5)
                            .addComponent(Creer_Outil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Nom_Outil))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Epaisseur_Outil)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ValidModifOutil, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                    .addComponent(ModifOutil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Supprimer_Outil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(24, 24, 24))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(marge))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29)
                                    .addComponent(ModMarge, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(6, 6, 6))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Nom_Outil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Epaisseur_Outil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Creer_Outil)
                .addGap(18, 18, 18)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(Supprimer_Outil)
                        .addGap(18, 18, 18)
                        .addComponent(ModifOutil)
                        .addGap(18, 18, 18)
                        .addComponent(ValidModifOutil))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(marge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(ModMarge))
        );

        jTabbedPane2.addTab("2.Outil", jPanel2);

        DefCoupe.setText("Créer une Coupe");
        DefCoupe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DefCoupeActionPerformed(evt);
            }
        });

        ModCoupe.setText("Modifier une Coupe");
        ModCoupe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModCoupeActionPerformed(evt);
            }
        });

        SuppCoupe.setText("Supprimer une Coupe");
        SuppCoupe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuppCoupeActionPerformed(evt);
            }
        });

        DistanceX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DistanceXActionPerformed(evt);
            }
        });

        Outil_Coupe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Defaut" }));
        Outil_Coupe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Outil_CoupeActionPerformed(evt);
            }
        });

        jLabel11.setText("1.Choisissez l'outil");

        jLabel14.setText("2.Modifier une coupe");

        jLabel15.setText("5.Supprimer coupe");

        jLabel17.setText("2. Type Coupe");

        Type_Coupe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vertical", "Horizontal", "Rect", "L", "Bordure" }));
        Type_Coupe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Type_CoupeActionPerformed(evt);
            }
        });

        jLabel18.setText("Bordure");

        BordureX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BordureXActionPerformed(evt);
            }
        });

        jLabel19.setText("X :");

        jLabel20.setText("Y :");

        UniteBordure.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "mm", "cm", "metre", "pouce" }));

        jLabel21.setText("3.Panneau après découpe");

        DimX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DimXActionPerformed(evt);
            }
        });

        jLabel22.setText("x:");

        jLabel23.setText("y:");

        ModifRef.setText("Modif ref");
        ModifRef.setActionCommand("ModifRef");
        ModifRef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifRefActionPerformed(evt);
            }
        });

        jButton2.setLabel("Modifier Outil Coupe");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel24.setText("4.Pann. Resultant");

        CoordX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CoordXActionPerformed(evt);
            }
        });

        jLabel25.setText("x:");

        jLabel26.setText("y:");

        CoordY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CoordYActionPerformed(evt);
            }
        });

        AjouterZI.setText("Ajouter Zone Interdite");
        AjouterZI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjouterZIActionPerformed(evt);
            }
        });

        jButton3.setText("Déplacer par souris");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanneauContrôleLayout = new javax.swing.GroupLayout(PanneauContrôle);
        PanneauContrôle.setLayout(PanneauContrôleLayout);
        PanneauContrôleLayout.setHorizontalGroup(
            PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanneauContrôleLayout.createSequentialGroup()
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Outil_Coupe, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanneauContrôleLayout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Type_Coupe, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator7)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanneauContrôleLayout.createSequentialGroup()
                        .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanneauContrôleLayout.createSequentialGroup()
                                    .addComponent(jLabel20)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(BordureY, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                .addGroup(PanneauContrôleLayout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(BordureX, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanneauContrôleLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(UniteBordure, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                        .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(DefCoupe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AjouterZI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanneauContrôleLayout.createSequentialGroup()
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ModCoupe, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(DistanceY, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DistanceX, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addGap(18, 18, 18)
                        .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DimY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DimX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(ModifRef, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
            .addGroup(PanneauContrôleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel14)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addComponent(jSeparator4))
                        .addContainerGap())))
            .addGroup(PanneauContrôleLayout.createSequentialGroup()
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SuppCoupe))
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CoordX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel26)
                        .addGap(26, 26, 26)
                        .addComponent(CoordY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanneauContrôleLayout.setVerticalGroup(
            PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanneauContrôleLayout.createSequentialGroup()
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(Outil_Coupe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(Type_Coupe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BordureX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(4, 4, 4)
                        .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(BordureY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addComponent(DefCoupe)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(UniteBordure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AjouterZI)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DimX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(DistanceX, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DistanceY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DimY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ModifRef)
                    .addComponent(ModCoupe))
                .addGap(18, 18, 18)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(CoordX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(CoordY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(SuppCoupe))
                .addGap(14, 14, 14))
        );

        jLabel8.setText("3.Personalisez vos coupes");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanneauContrôle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanneauContrôle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("3.Coupe", jPanel3);

        jMenu1.setText("Fichier");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setText("Quitter");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        Nouveau_Menu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Nouveau_Menu.setText("Nouveau");
        Nouveau_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Nouveau_MenuActionPerformed(evt);
            }
        });
        jMenu1.add(Nouveau_Menu);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        Undo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Undo.setText("Undo");
        Undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UndoActionPerformed(evt);
            }
        });
        jMenu2.add(Undo);

        Redo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Redo.setText("Redo");
        Redo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RedoActionPerformed(evt);
            }
        });
        jMenu2.add(Redo);

        jMenuBar1.add(jMenu2);

        jMenu5.setText("Export");

        Gen_Gcode.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Gen_Gcode.setText("GCODE");
        Gen_Gcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Gen_GcodeActionPerformed(evt);
            }
        });
        jMenu5.add(Gen_Gcode);

        jMenuItem3.setText("CNC+PAN");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem3);

        jMenuBar1.add(jMenu5);

        jMenu3.setText("Import");

        Import_cnc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        Import_cnc.setText("CNC");
        Import_cnc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Import_cncActionPerformed(evt);
            }
        });
        jMenu3.add(Import_cnc);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PanneauVisualisation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(167, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(PanneauVisualisation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void UndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UndoActionPerformed
if (controleur.isUndoAvailable()) {
        controleur.undo();
        System.out.println("Action annulée (Undo).");
    } else {
        System.out.println("Aucune action à annuler.");
    }
    }//GEN-LAST:event_UndoActionPerformed

    private void RedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RedoActionPerformed
if (controleur.isRedoAvailable()) {
        controleur.redo();
        System.out.println("Action rétablie (Redo).");
    } else {
        System.out.println("Aucune action à rétablir.");
    }
    }//GEN-LAST:event_RedoActionPerformed

    private void Nouveau_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Nouveau_MenuActionPerformed
        // TODO add your handling code here:
         this.dispose(); // Ferme la fenêtre actuelle
    
    // Créer une nouvelle instance de la fenêtre principale (MainWindow)
    java.awt.EventQueue.invokeLater(() -> {
        MainWindow newWindow = new MainWindow();
        newWindow.setVisible(true);
        controleur.reset();
    });
    }//GEN-LAST:event_Nouveau_MenuActionPerformed

    private void Gen_GcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Gen_GcodeActionPerformed
           genererGCode();
    }//GEN-LAST:event_Gen_GcodeActionPerformed

    private void AjouterZIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjouterZIActionPerformed
        // TODO add your handling code here:
        System.out.println("Création d'une Zone Interdite");
        panneauVue.activerCreationZoneInterdite();
        panneauVue.repaint();
    }//GEN-LAST:event_AjouterZIActionPerformed

    private void CoordYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CoordYActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CoordYActionPerformed

    private void CoordXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CoordXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CoordXActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        panneauVue.activerModifOutil();
        panneauVue.repaint();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void ModifRefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifRefActionPerformed
        // TODO add your handling code here:
        panneauVue.activerEditRef();
        panneauVue.repaint();
    }//GEN-LAST:event_ModifRefActionPerformed

    private void DimXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DimXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DimXActionPerformed

    private void BordureXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BordureXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BordureXActionPerformed

    private void Type_CoupeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Type_CoupeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Type_CoupeActionPerformed

    private void Outil_CoupeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Outil_CoupeActionPerformed
String outilSelectionne = (String) Outil_Coupe.getSelectedItem();
if (outilSelectionne != null) {
    // On récupère l'épaisseur en millimètres
    float epaisseurMm = controleur.getEpaisseurOutil(outilSelectionne);

    // On NE convertit PAS en pixels ici. On garde l'épaisseur en mm.
    controleur.setEpaisseurActuelle(epaisseurMm);
}

    }//GEN-LAST:event_Outil_CoupeActionPerformed

    private void DistanceXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DistanceXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DistanceXActionPerformed

    private void SuppCoupeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuppCoupeActionPerformed
        panneauVue.activerSuppressionCoupe();
        /*
        if (!controleur.getCoupes().isEmpty()) {
            panneauVue.deleteTriggered = true;
            System.out.printf("mainwindow \n");
            controleur.supprimerCoupe();
            panneauVue.repaint();
            message.setText("Dernière coupe supprimée avec succès.");
            panneauVue.deleteTriggered = false;
        } else {
            message.setText("Aucune coupe à supprimer.");
        }*/
    }//GEN-LAST:event_SuppCoupeActionPerformed

    private void ModCoupeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModCoupeActionPerformed
        //try {
            String selection = (String) Type_Coupe.getSelectedItem();

            switch (selection) {
                case "Vertical", "Horizontal":
                try{
                    Float AxeRelatif = Float.parseFloat(DistanceX.getText());
                    String unite = (String) UniteBordure.getSelectedItem();
                    switch (unite) {
                        case "cm":
                        AxeRelatif *= 10; // Convertir en mm
                        break;
                        case "metre":
                        AxeRelatif *= 1000; // Convertir en mm

                        break;
                        case "pouce":
                        AxeRelatif *= 25.4f; // Convertir en mm

                    }
                    panneauVue.activerModifierCoupeAxiale(AxeRelatif);
                    panneauVue.repaint();
                } catch (NumberFormatException e) {
                    message.setText("dimensions invalides.");
                }

                break;
                case "Rect", "L":
                try{
                    Float longueur = Float.parseFloat(DistanceX.getText());
                    Float largeur = Float.parseFloat(DistanceY.getText());
                    String unite = (String) UniteBordure.getSelectedItem();
                    switch (unite) {
                        case "cm":
                        longueur *= 10; // Convertir en mm
                        largeur *= 10;
                        break;
                        case "metre":
                        longueur *= 1000; // Convertir en mm
                        largeur *= 1000;
                        break;
                        case "pouce":
                        longueur *= 25.4f; // Convertir en mm
                        largeur *= 25.4f;
                    }
                    panneauVue.activerModifierR(longueur, largeur);
                    panneauVue.repaint();
                }
                catch (NumberFormatException e) {
                    message.setText("dimensions invalides.");
                }
                break;

            }
    }//GEN-LAST:event_ModCoupeActionPerformed

    //TODO: liaison bouton, controleur fares
    private void DefCoupeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DefCoupeActionPerformed
        // TODO add your handling code here:
        String selection = (String) Type_Coupe.getSelectedItem();
        switch (selection) {
            case "Vertical":
            System.out.println("Création d'une coupe Verticale");
            panneauVue.activerCreationCoupeV();  // Active la possibilité de créer une coupe
            panneauVue.repaint();
            //
            break;
            case "Horizontal":
            System.out.println("Création d'une coupe Horizontale");
            panneauVue.activerCreationCoupeH();  // Active la possibilité de créer une coupe
            panneauVue.repaint();
            break;
            case "Rect":
            System.out.println("Création d'une coupe Rectangulaire");
            panneauVue.activerCreationCoupeRect();
            panneauVue.repaint();
            break;
            case "L":
            System.out.println("Création d'une coupe en L");
            panneauVue.activerCreationCoupeL();
            panneauVue.repaint();
            break;
            case "Bordure":
            System.out.println("Création d'une coupe Bordure");
            try {
                // Récupération des valeurs des champs de texte
                float BordureXValue = Float.parseFloat(BordureX.getText());
                float BordureYValue = Float.parseFloat(BordureY.getText());

                String unite = (String) UniteBordure.getSelectedItem();
                switch (unite) {
                    case "cm":
                    BordureXValue *= 10; // Convertir en mm
                    BordureYValue *= 10;
                    break;
                    case "metre":
                    BordureXValue *= 1000; // Convertir en mm
                    BordureYValue *= 1000;
                    break;
                    case "pouce":
                    BordureXValue *= 25.4; // Convertir en mm
                    BordureYValue *= 25.4;
                }
                controleur.SetCoupeBordure(BordureXValue, BordureYValue);
                System.out.print("Coupe créé!\n");
                //panneauVue.DimensionsBordure(BordureXValue, BordureYValue);
                panneauVue.repaint();
            } catch (NumberFormatException ex) {
                message.setText("format non valide.");
            }
            break;
            default:
            System.out.println("Type de coupe inconnu");
            break;
        }
    }//GEN-LAST:event_DefCoupeActionPerformed

    private void ValidModifOutilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ValidModifOutilActionPerformed
        int selectedRow = tableauOutils.getSelectedRow(); // Récupérer la ligne sélectionnée

        if (selectedRow != -1) { // Vérifie qu'une ligne est sélectionnée
            String nouveauNom = Nom_Outil.getText();
            String nouvelleEpaisseurStr = Epaisseur_Outil.getText();

            if (nouveauNom.isEmpty() || nouvelleEpaisseurStr.isEmpty()) {
                message.setText("Veuillez remplir les champs pour modifier l'outil.");
                return;
            }

            try {
                float nouvelleEpaisseur = Float.parseFloat(nouvelleEpaisseurStr);

                // Récupérer le nom actuel de l'outil depuis la table
                String nomActuel = (String) tableauOutils.getValueAt(selectedRow, 0);

                // Mettre à jour l'outil via le contrôleur
                controleur.modifierOutil(nomActuel, nouveauNom, Repere.getInstance().convertirEnPixelsDepuisMm(nouvelleEpaisseur));

                // **Mettre à jour la table et la combobox**
                mettreAJourComboBoxOutil();
                controleur.mettreAJourTableauOutils();

                // Nettoyer les champs
                Nom_Outil.setText("");
                Epaisseur_Outil.setText("");
                message.setText("Modification validée.");
            } catch (NumberFormatException e) {
                message.setText("Format d'épaisseur invalide. Veuillez entrer un nombre.");
            }
        } else {
            message.setText("Veuillez sélectionner un outil à modifier.");
        }
    }//GEN-LAST:event_ValidModifOutilActionPerformed

    private void ModifOutilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifOutilActionPerformed
        int selectedRow = tableauOutils.getSelectedRow(); // Récupérer l'index de l'outil sélectionné

        if (selectedRow != -1) { // Vérifie qu'une ligne est sélectionnée
            String nomActuel = (String) tableauOutils.getValueAt(selectedRow, 0); // Nom actuel de l'outil
            String nouveauNom = Nom_Outil.getText(); // Nouveau nom saisi
            String nouvelleEpaisseurStr = Epaisseur_Outil.getText(); // Nouvelle largeur saisie

            if (nouveauNom.isEmpty() || nouvelleEpaisseurStr.isEmpty()) {
                message.setText("Veuillez remplir les champs pour modifier l'outil.");
                return;
            }

            try {
                float nouvelleEpaisseur = Float.parseFloat(nouvelleEpaisseurStr);

                // Appel du Controleur pour modifier l'outil
                controleur.modifierOutil(nomActuel, nouveauNom, nouvelleEpaisseur);

                // Nettoyer les champs de texte
                Nom_Outil.setText("");
                Epaisseur_Outil.setText("");
                message.setText("Modification validée.");
            } catch (NumberFormatException e) {
                message.setText("Format d'épaisseur invalide. Veuillez entrer un nombre.");
            }
        } else {
            message.setText("Veuillez sélectionner un outil à modifier.");
        }
    }//GEN-LAST:event_ModifOutilActionPerformed

    private void Supprimer_OutilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Supprimer_OutilActionPerformed
        int index = tableauOutils.getSelectedRow();
        if (index != -1) {
            String outilASupprimer = (String) tableauOutils.getValueAt(index, 0); // Récupérer le nom de l'outil depuis le `JTable`

            // Supprimer l'outil du contrôleur
            controleur.supprimerOutil(index);
            controleur.mettreAJourTableauOutils(); // Mettre à jour le tableau pour refléter la suppression

            // Supprimer du ComboBox des outils
            for (int i = 0; i < Outil_Coupe.getItemCount(); i++) {
                if (Outil_Coupe.getItemAt(i).equals(outilASupprimer)) {
                    Outil_Coupe.removeItemAt(i);
                    break;
                }
            }

            message.setText("Outil supprimé avec succès.");
        } else {
            message.setText("Veuillez sélectionner un outil à supprimer.");
        }
    }//GEN-LAST:event_Supprimer_OutilActionPerformed

    private void Epaisseur_OutilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Epaisseur_OutilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Epaisseur_OutilActionPerformed

    private void Nom_OutilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Nom_OutilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nom_OutilActionPerformed

    private void Creer_OutilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Creer_OutilActionPerformed
        try {
            String nomOutil = Nom_Outil.getText();
            String epaisseurStr = Epaisseur_Outil.getText();

            if (nomOutil.isEmpty() || epaisseurStr.isEmpty()) {
                message.setText("Veuillez entrer un nom et une épaisseur pour l'outil.");
                return;
            }

            float epaisseur = Float.parseFloat(epaisseurStr);
            //ICI c juste pour relier la création d'outil avec la tab du panneau
            Outil_Coupe.addItem(nomOutil);

            // Ajouter le nouvel outil via le contrôleur
            controleur.SetOutil(nomOutil, Float.parseFloat(epaisseurStr));
            controleur.mettreAJourTableauOutils(); // Mettre à jour le `JTable`

            // Nettoyer les champs de texte
            Nom_Outil.setText("");
            Epaisseur_Outil.setText("");
            message.setText("Outil ajouté avec succès.");
        } catch (NumberFormatException ex) {
            message.setText("Format d'épaisseur non valide. Veuillez entrer un nombre.");
        }
    }//GEN-LAST:event_Creer_OutilActionPerformed

    private void SuppGrilleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuppGrilleActionPerformed
        panneauVue.setAfficherGrille(false);
    }//GEN-LAST:event_SuppGrilleActionPerformed

    private void AfficherGrilleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AfficherGrilleActionPerformed
        try {
            float cote = Float.parseFloat(GrilleX.getText());
            //}
        String unite = (String) comboBoxUnite.getSelectedItem();
        switch (unite) {
            case "cm":
            cote *= 10; // Convertir en mm
            break;
            case "metre":
            cote *= 1000; // Convertir en mm
            break;
            case "pouce":
            cote *= 25.4; // Convertir en mm
        }
        panneauVue.setIntervalleGrille(cote);
        panneauVue.setAfficherGrille(true);
        Controleur.getInstance().setCoteGrille(cote);
        panneauVue.repaint();
        } catch (NumberFormatException ex) {
            message.setText("grille avec 200mm.");
            panneauVue.setIntervalleGrille(200);
            Controleur.getInstance().setCoteGrille(200);
            panneauVue.setAfficherGrille(true);
            panneauVue.repaint();
        }
    }//GEN-LAST:event_AfficherGrilleActionPerformed

    private void DessinerPanneauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DessinerPanneauActionPerformed
        // TODO add your handling code here:
        try {
            // Récupération des valeurs des champs de texte
            float longueurX = Float.parseFloat(PANlongueurX.getText());
            float largeurY = Float.parseFloat(PANlargeurY.getText());
            float profondeurZ = Float.parseFloat(PANprofondeurZ.getText());

            String unite = (String) comboBoxUnite.getSelectedItem();
            switch (unite) {
                case "cm":
                longueurX *= 10; // Convertir en mm
                largeurY *= 10;
                profondeurZ *= 10;
                break;
                case "metre":
                longueurX *= 1000; // Convertir en mm
                largeurY *= 1000;
                profondeurZ *= 1000;
                break;
                case "pouce":
                longueurX *= 25.4; // Convertir en mm
                largeurY *= 25.4;
                profondeurZ *= 25.4;

            }
            controleur.SetPanneau(longueurX, largeurY, profondeurZ);
            panneauVue.repaint();
        } catch (NumberFormatException ex) {
            message.setText("format non valide.");
        }
    }//GEN-LAST:event_DessinerPanneauActionPerformed

    private void comboBoxUniteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxUniteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxUniteActionPerformed

    private void comboBoxUniteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxUniteItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxUniteItemStateChanged

    private void PANlargeurYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PANlargeurYActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PANlargeurYActionPerformed

    private void PANlongueurXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PANlongueurXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PANlongueurXActionPerformed

    private void ModMargeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModMargeActionPerformed
        // TODO add your handling code here:
        try {
            // Récupérer la nouvelle valeur de marge depuis le JTextField
            float nouvelleMarge = Float.parseFloat(marge.getText());

            // Appeler la méthode du contrôleur pour mettre à jour la marge
            controleur.setMarge(nouvelleMarge);

            // Afficher un message de confirmation
            System.out.println("La marge a été mise à jour avec succès : " + nouvelleMarge);
        } catch (NumberFormatException ex) {
            // Gérer les cas où l'entrée n'est pas valide
            System.out.println("Erreur : Veuillez entrer une valeur numérique valide pour la marge.");
        }
    }//GEN-LAST:event_ModMargeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            // Récupérer la marge actuelle depuis le contrôleur
            float margeActuelle = controleur.getCNC().getMarge();
            
            // Écrire la valeur de la marge actuelle dans le JTextField
            marge.setText(String.valueOf(margeActuelle));

            // Afficher un message dans la console
            System.out.println("La marge actuelle a été affichée : " + margeActuelle);
        } catch (Exception ex) {
            // Gérer les cas où la marge ne peut pas être récupérée
            System.out.println("Erreur : Impossible de récupérer la marge actuelle.");
            }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
         try {
        // Créer une instance de JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer le fichier CNC");

        // Définir le nom de fichier par défaut
        fileChooser.setSelectedFile(new File("CNC_etat.txt"));

        // Ajouter un filtre pour les fichiers texte
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers texte (*.txt)", "txt");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false); // Optionnel : n'affiche que le filtre spécifié

        // Ouvrir la boîte de dialogue d'enregistrement
        int userSelection = fileChooser.showSaveDialog(this);

        // Vérifier si l'utilisateur a approuvé la sélection
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String fichierCNC = fileToSave.getAbsolutePath();

            // Vérifier si le fichier a l'extension .txt, sinon l'ajouter
            if (!fichierCNC.toLowerCase().endsWith(".txt")) {
                fichierCNC += ".txt";
            }

            File finalFile = new File(fichierCNC);

            // Vérifier si le fichier existe déjà et demander confirmation pour l'écraser
            if (finalFile.exists()) {
                int response = JOptionPane.showConfirmDialog(this, 
                    "Le fichier existe déjà. Voulez-vous le remplacer ?", 
                    "Confirmer l'écrasement", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
                
                if (response != JOptionPane.YES_OPTION) {
                    // L'utilisateur a choisi de ne pas écraser le fichier
                    JOptionPane.showMessageDialog(this, "Enregistrement du fichier CNC annulé.");
                    return;
                }
            }

            // Appeler la méthode d'exportation via le contrôleur
            controleur.saveCNC(fichierCNC);

            // Afficher un message de confirmation avec le chemin du fichier
            JOptionPane.showMessageDialog(this, "Fichier CNC enregistré avec succès :\n" + fichierCNC);
        } else {
            // L'utilisateur a annulé l'opération
            JOptionPane.showMessageDialog(this, "Enregistrement du fichier CNC annulé.");
        }
    } catch (Exception ex) {
        // Afficher un message d'erreur en cas d'exception
        JOptionPane.showMessageDialog(this, 
            "Erreur lors de l'enregistrement du fichier CNC : " + ex.getMessage(), 
            "Erreur", 
            JOptionPane.ERROR_MESSAGE);}
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        panneauVue.setDeplacementGraphique();
        repaint();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        panneauVue.changeMagnetique();
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void Import_cncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Import_cncActionPerformed
   
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Choisir un fichier");

   
    int userSelection = fileChooser.showOpenDialog(this);

    
    if (userSelection == JFileChooser.APPROVE_OPTION) {
       
        File selectedFile = fileChooser.getSelectedFile();
        
        
        System.out.println("Chemin du fichier sélectionné : " + selectedFile.getAbsolutePath());
    }
    }//GEN-LAST:event_Import_cncActionPerformed

    /*
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AfficherGrille;
    private javax.swing.JButton AjouterZI;
    private javax.swing.JTextField BordureX;
    private javax.swing.JTextField BordureY;
    private javax.swing.JTextField CoordX;
    private javax.swing.JTextField CoordY;
    private javax.swing.JButton Creer_Outil;
    private javax.swing.JButton DefCoupe;
    private javax.swing.JButton DessinerPanneau;
    private javax.swing.JTextField DimX;
    private javax.swing.JTextField DimY;
    private javax.swing.JTextField DistanceX;
    private javax.swing.JTextField DistanceY;
    private javax.swing.JTextField Epaisseur_Outil;
    private javax.swing.JMenuItem Gen_Gcode;
    private javax.swing.JTextField GrilleX;
    private javax.swing.JMenuItem Import_cnc;
    private javax.swing.JButton ModCoupe;
    private javax.swing.JButton ModMarge;
    private javax.swing.JButton ModifOutil;
    private javax.swing.JButton ModifRef;
    private javax.swing.JTextField Nom_Outil;
    private javax.swing.JMenuItem Nouveau_Menu;
    private javax.swing.JComboBox<String> Outil_Coupe;
    private javax.swing.JTextField PANlargeurY;
    private javax.swing.JTextField PANlongueurX;
    private javax.swing.JTextField PANprofondeurZ;
    private javax.swing.JPanel PanneauContrôle;
    private javax.swing.JPanel PanneauVisualisation;
    private javax.swing.JMenuItem Redo;
    private javax.swing.JButton SuppCoupe;
    private javax.swing.JButton SuppGrille;
    private javax.swing.JButton Supprimer_Outil;
    private javax.swing.JComboBox<String> Type_Coupe;
    private javax.swing.JMenuItem Undo;
    private javax.swing.JComboBox<String> UniteBordure;
    private javax.swing.JButton ValidModifOutil;
    private javax.swing.JComboBox<String> comboBoxUnite;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField marge;
    private javax.swing.JLabel message;
    private javax.swing.JTable tableauOutils;
    // End of variables declaration//GEN-END:variables
}
