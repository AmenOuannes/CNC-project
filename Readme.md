# Guide d'utilisation de l'application Panneau et Coupe CNC

## Introduction
Cette application permet de visualiser un panneau CNC, de créer des coupes, de modifier des outils et de gérer divers types de coupes. Elle se compose de trois onglets principaux, chacun ayant une fonction spécifique. Le panneau CNC est initialement configuré en **centimètres**, avec une graduation incrémentant de **200 cm** par case par défaut.

## Étapes d'utilisation

### 1. **Lancer l'application**
Lorsque vous ouvrez l'application, vous accédez à trois onglets principaux :

### Onglet 1 : **Dessin du panneau**
- Cet onglet est dédié au dessin du panneau.
- Un panneau par défaut de **120 cm sur 100 cm** est automatiquement affiché à l'ouverture.
- Vous pouvez spécifier vos propres dimensions en modifiant les champs correspondants et choisir l'unité (par exemple, centimètres, pouces, millimètres).
- Les axes de la table de la CNC sont configurés selon l'unité sélectionnée, avec des graduations de **200 cm** par défaut pour chaque case, modifiables selon vos préférences.
- **Activation et personnalisation de la grille :**
  - Vous pouvez activer une grille dans la zone de travail en cliquant sur le bouton **"Activer la grille"**.
  - La longueur de côté de la grille est de **200 cm** par défaut, mais peut être modifiée dans les paramètres.
- Une fois les dimensions renseignées, cliquez sur le bouton **"Dessiner le panneau"** pour afficher le panneau dans la zone de travail.

### Onglet 2 : **Création et modification des outils**
- Cet onglet permet de créer et de gérer des outils utilisés pour les coupes. 
- Un outil par défaut, nommé **"défaut"**, est disponible avec une épaisseur fixe de **½ pouce**. Cet outil apparaît dans la table dès que vous créez votre premier outil personnalisé.
- **Remarque :** L'outil par défaut ne peut pas être modifié.

#### Création d'un outil personnalisé
1. Renseignez un nom et une épaisseur dans les champs correspondants.
2. Cliquez sur le bouton **"Créer un outil"**.

#### Modification d'un outil existant
1. Sélectionnez un outil dans la table.
2. Cliquez sur le bouton **"Modifier un outil"**.
3. Modifiez le nom et/ou l'épaisseur de l'outil dans les champs appropriés.
4. Cliquez sur le bouton **"Valider modification"**.

### Onglet 3 : **Création et gestion des coupes**
- Cet onglet vous permet de gérer les coupes en sélectionnant un outil dans le menu déroulant.
- **Fonctionnalités supplémentaires :**
  - **Undo/Redo :** Vous pouvez annuler ou rétablir toutes les actions effectuées (création, modification, suppression de coupes) en utilisant les boutons **"Annuler"** et **"Rétablir"** situés en haut de l'onglet.
  - **Modification par glisser-déposer :** Les coupes peuvent être modifiées directement sur le panneau en les sélectionnant et en les déplaçant avec la souris.

#### Changer l'outil d'une coupe
1. Sélectionnez un nouvel outil dans le menu déroulant.
2. Cliquez sur le bouton **"Modifier outil coupe"**.
3. Cliquez sur le panneau pour dessiner une nouvelle coupe avec le nouvel outil.

#### Types de coupes disponibles
1. **Horizontale**
2. **Verticale**
3. **En L**
4. **Rectangulaire**
5. **De bordure**

#### Création d'une coupe
1. Cliquez sur le panneau pour définir un point de référence.
2. Cliquez à nouveau pour tracer la coupe.
   - Pour les coupes de bordure, entrez les dimensions sur les axes X et Y, puis cliquez sur **"Créer une coupe"**.

#### Modification d'une coupe
- Cliquez sur le bouton **"Modifier une coupe"**.
- Sélectionnez une coupe directement sur le panneau.
- Mettez les nouvelles coordonnées en fonction du type de la coupe ou déplacez-la par glisser-déposer avec la souris.

#### Suppression d'une coupe
- Cliquez sur le bouton **"Supprimer une coupe"**.
- Sélectionnez une coupe directement sur le panneau.

#### Coupes invalides
- Si une coupe est invalide, elle sera affichée en rouge pour signaler l'erreur.

#### Importation et exportation de projets
- **Importer un projet :**
  1. Cliquez sur **"Fichier"** dans le menu situé en haut de la fenêtre.
  2. Sélectionnez **"Importer un projet"**.
  3. Choisissez le fichier contenant les coupes nécessaires.
- **Exporter un projet :**
  1. Cliquez sur **"Fichier"** dans le menu situé en haut de la fenêtre.
  2. Sélectionnez **"Exporter un projet"**.
  3. Choisissez l'emplacement et le format du fichier à exporter.

### 4. **Quitter l'application**
Pour quitter l'application :
1. Cliquez sur **"Fichier"** dans le menu situé en haut de la fenêtre.
2. Sélectionnez l'option **"Quitter"**.

## Remarques
- Assurez-vous de bien sélectionner ou cliquer dans la zone marron clair pour effectuer des actions sur le panneau.
- Chaque modification ou suppression de coupe est immédiatement visible après validation ou application.
- **Undo/Redo :** Utilisez les fonctionnalités d'annulation et de rétablissement pour gérer vos actions efficacement.
- **Grille personnalisée :** Ajustez la grille selon vos besoins pour une meilleure précision dans vos dessins.
- **Importation/Exportation :** Sauvegardez vos projets et partagez-les facilement grâce aux fonctionnalités d'importation et d'exportation.
- **Modification intuitive :** Profitez de la fonctionnalité de glisser-déposer pour une modification rapide et intuitive des coupes.

Nous espérons que cette application répondra à vos besoins pour visualiser et manipuler des panneaux CNC efficacement !
