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




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator3 = new javax.swing.JSeparator();
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
        Actualiser = new javax.swing.JButton();
        SelectCoupe = new javax.swing.JButton();
        DistanceY = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

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

        jLabel3.setText("Commençons par la création de votre panneau");

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

        jLabel5.setText("2.Entrez la longueur : X");

        jLabel6.setText("3.Entrez la largeur : Y");

        jLabel7.setText("4.Entrez la profondeur : Z");

        DessinerPanneau.setText("Dessiner Panneau");
        DessinerPanneau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DessinerPanneauActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator2)
                .addContainerGap())
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
                .addContainerGap(178, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(11, 11, 11)
                .addComponent(comboBoxUnite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PANlongueurX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(11, 11, 11)
                .addComponent(PANlargeurY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(13, 13, 13)
                .addComponent(PANprofondeurZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(DessinerPanneau)
                .addContainerGap(151, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("1.Panneau", jPanel1);

        jLabel9.setText("1. Créer un Outil");

        Creer_Outil.setText("Créer Outil");
        Creer_Outil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Creer_OutilActionPerformed(evt);
            }
        });

        jLabel10.setText("Entrez le nom de l'outil");

        Nom_Outil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Nom_OutilActionPerformed(evt);
            }
        });

        jLabel12.setText("Entrez la largeur (mm)");

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
                "Nom", "Largeur Lame"
            }
        ));
        jScrollPane2.setViewportView(tableauOutils);

        jLabel16.setText("Sélectionnez un outil pour le configurer ou supprimer");

        ValidModifOutil.setText("Valider Modifcation");
        ValidModifOutil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ValidModifOutilActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Creer_Outil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                            .addComponent(Nom_Outil))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(Epaisseur_Outil))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(Supprimer_Outil, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ValidModifOutil)
                                    .addComponent(ModifOutil, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jSeparator6)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel9)
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(Supprimer_Outil)
                        .addGap(18, 18, 18)
                        .addComponent(ModifOutil)
                        .addGap(18, 18, 18)
                        .addComponent(ValidModifOutil))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(65, Short.MAX_VALUE))
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

        jLabel15.setText("3.Supprimer coupe");

        jLabel17.setText("2. Type Coupe");

        Type_Coupe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vertical", "Horizontal", "Rect", "L", "Bordure" }));
        Type_Coupe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Type_CoupeActionPerformed(evt);
            }
        });

        jLabel18.setText("Si Bordure");

        BordureX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BordureXActionPerformed(evt);
            }
        });

        jLabel19.setText("X :");

        jLabel20.setText("Y :");

        UniteBordure.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "mm", "cm", "metre", "pouce" }));

        jLabel21.setText("Point courant :");

        jLabel22.setText("x:");

        jLabel23.setText("y:");

        Actualiser.setText("Actualiser Panneau");
        Actualiser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualiserActionPerformed(evt);
            }
        });

        SelectCoupe.setText("Selection Coupe");
        SelectCoupe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectCoupeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanneauContrôleLayout = new javax.swing.GroupLayout(PanneauContrôle);
        PanneauContrôle.setLayout(PanneauContrôleLayout);
        PanneauContrôleLayout.setHorizontalGroup(
            PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanneauContrôleLayout.createSequentialGroup()
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanneauContrôleLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Outil_Coupe, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jSeparator1)
                            .addGroup(PanneauContrôleLayout.createSequentialGroup()
                                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanneauContrôleLayout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(BordureY, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                    .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(PanneauContrôleLayout.createSequentialGroup()
                                            .addComponent(jLabel19)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(BordureX, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(ModCoupe, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanneauContrôleLayout.createSequentialGroup()
                        .addGap(0, 230, Short.MAX_VALUE)
                        .addComponent(Type_Coupe, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jSeparator4)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanneauContrôleLayout.createSequentialGroup()
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(DistanceY, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DistanceX, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanneauContrôleLayout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addGap(18, 18, 18)
                            .addComponent(DimY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(PanneauContrôleLayout.createSequentialGroup()
                            .addComponent(jLabel22)
                            .addGap(18, 18, 18)
                            .addComponent(DimX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel21))
                .addGap(41, 41, 41))
            .addGroup(PanneauContrôleLayout.createSequentialGroup()
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(UniteBordure, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(DefCoupe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Actualiser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SelectCoupe, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanneauContrôleLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SuppCoupe)
                        .addGap(21, 21, 21))))
        );
        PanneauContrôleLayout.setVerticalGroup(
            PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanneauContrôleLayout.createSequentialGroup()
                .addGap(16, 16, 16)
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
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18)
                        .addGap(9, 9, 9)
                        .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(BordureX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanneauContrôleLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(DefCoupe)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(BordureY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SelectCoupe, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UniteBordure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Actualiser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DistanceX, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(DimX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(DimY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DistanceY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ModCoupe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(PanneauContrôleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(SuppCoupe))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jLabel8.setText("1.Créer une coupe");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanneauContrôle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
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

        jMenuItem1.setText("Quitter");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(PanneauVisualisation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PanneauVisualisation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void comboBoxUniteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxUniteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxUniteActionPerformed

    private void comboBoxUniteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxUniteItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxUniteItemStateChanged

    private void PANlongueurXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PANlongueurXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PANlongueurXActionPerformed

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
        controleur.SetOutil(nomOutil, epaisseur);
        controleur.mettreAJourTableauOutils(); // Mettre à jour le `JTable`

        // Nettoyer les champs de texte
        Nom_Outil.setText("");
        Epaisseur_Outil.setText("");
        message.setText("Outil ajouté avec succès.");
    } catch (NumberFormatException ex) {
        message.setText("Format d'épaisseur non valide. Veuillez entrer un nombre.");
    }
    }//GEN-LAST:event_Creer_OutilActionPerformed

    private void Nom_OutilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Nom_OutilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Nom_OutilActionPerformed

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

    private void PANlargeurYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PANlargeurYActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PANlargeurYActionPerformed

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

    private void Epaisseur_OutilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Epaisseur_OutilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Epaisseur_OutilActionPerformed

    private void Outil_CoupeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Outil_CoupeActionPerformed
          String outilSelectionne = (String) Outil_Coupe.getSelectedItem();
    if (outilSelectionne != null) {
        // Récupérer l'épaisseur en millimètres de l'outil sélectionné
        float epaisseurMm = controleur.getEpaisseurOutil(outilSelectionne);

        // Convertir l'épaisseur en pixels
        Repere repere = Repere.getInstance();
        float epaisseurPixels = repere.convertirEnPixelsDepuisMm(epaisseurMm);

        // Passer l'épaisseur au contrôleur pour qu'elle soit utilisée par l'Afficheur
        controleur.setEpaisseurActuelle(epaisseurPixels);
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
            Float Axe = Float.parseFloat(DistanceX.getText());
            controleur.modifierCoupe(Axe);

            panneauVue.modifyTriggered=true;
            panneauVue.repaint();
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
            break;
            case "L":
            System.out.println("Création d'une coupe en L");
            panneauVue.activerCreationCoupeL();
            //panneauVue.repaint();
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

    private void BordureXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BordureXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BordureXActionPerformed

    private void Type_CoupeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Type_CoupeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Type_CoupeActionPerformed

    private void ActualiserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualiserActionPerformed
         String outilSelectionne = (String) Outil_Coupe.getSelectedItem(); // Outil sélectionné
    if (outilSelectionne != null) {
        // Récupérer l'épaisseur de l'outil sélectionné
        float epaisseurMm = controleur.getEpaisseurOutil(outilSelectionne);
       if ("Defaut".equals(outilSelectionne)) {
           float epaisseurDefaut = Repere.getInstance().convertirEnPixelsDepuisPouces(0.5f); // Convertir 0,5 pouces en pixels
           controleur.setEpaisseurActuelle(epaisseurDefaut);
       }

        // Convertir l'épaisseur en pixels
        Repere repere = Repere.getInstance();
        float epaisseurPixels = repere.convertirEnPixelsDepuisMm(epaisseurMm);

        // Forcer le repaint du panneau de visualisation
        panneauVue.repaint();

        // Message de confirmation
        message.setText("Les coupes ont été redessinées avec le nouvel outil.");
    } else {
        message.setText("Veuillez sélectionner un outil avant de redessiner.");
    }
    }//GEN-LAST:event_ActualiserActionPerformed

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
            controleur.modifierOutil(nomActuel, nouveauNom, nouvelleEpaisseur);

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

    private void SelectCoupeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectCoupeActionPerformed
        
    }//GEN-LAST:event_SelectCoupeActionPerformed

    /**
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
    private javax.swing.JButton Actualiser;
    private javax.swing.JTextField BordureX;
    private javax.swing.JTextField BordureY;
    private javax.swing.JButton Creer_Outil;
    private javax.swing.JButton DefCoupe;
    private javax.swing.JButton DessinerPanneau;
    private javax.swing.JTextField DimX;
    private javax.swing.JTextField DimY;
    private javax.swing.JTextField DistanceX;
    private javax.swing.JTextField DistanceY;
    private javax.swing.JTextField Epaisseur_Outil;
    private javax.swing.JButton ModCoupe;
    private javax.swing.JButton ModifOutil;
    private javax.swing.JTextField Nom_Outil;
    private javax.swing.JComboBox<String> Outil_Coupe;
    private javax.swing.JTextField PANlargeurY;
    private javax.swing.JTextField PANlongueurX;
    private javax.swing.JTextField PANprofondeurZ;
    private javax.swing.JPanel PanneauContrôle;
    private javax.swing.JPanel PanneauVisualisation;
    private javax.swing.JButton SelectCoupe;
    private javax.swing.JButton SuppCoupe;
    private javax.swing.JButton Supprimer_Outil;
    private javax.swing.JComboBox<String> Type_Coupe;
    private javax.swing.JComboBox<String> UniteBordure;
    private javax.swing.JButton ValidModifOutil;
    private javax.swing.JComboBox<String> comboBoxUnite;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel message;
    private javax.swing.JTable tableauOutils;
    // End of variables declaration//GEN-END:variables
}
