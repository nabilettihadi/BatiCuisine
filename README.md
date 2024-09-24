### Bati-Cuisine - Application d'Estimation des Coûts de Rénovation de Cuisines

## Description
**Bati-Cuisine** est une application Java conçue pour les professionnels de la construction et de la rénovation de cuisines. Elle permet de gérer efficacement les projets de rénovation en calculant les coûts des matériaux, de la main-d'œuvre, et en générant des devis détaillés pour les clients. L'application prend en compte les taxes (TVA) et les marges bénéficiaires, fournissant une estimation claire et précise pour chaque projet.

## Fonctionnalités

### 1. Gestion des Projets
- Ajouter un client à un projet de rénovation.
- Gérer les composants (matériaux et main-d'œuvre) d'un projet.
- Générer des devis avant le début des travaux avec estimation des coûts.
- Caractéristiques d'un projet : nom, marge bénéficiaire, coût total, et état du projet (en cours, terminé, annulé).

### 2. Gestion des Composants
- **Matériaux** : gérer le nom, coût unitaire, quantité, TVA, transport et qualité des matériaux.
- **Main-d'œuvre** : gérer le coût horaire, les heures travaillées, et la productivité.

### 3. Gestion des Clients
- Enregistrer les informations client : nom, adresse, téléphone, et type de client (professionnel ou particulier).
- Appliquer des remises en fonction du type de client.

### 4. Création de Devis
- Générer des devis détaillés, incluant les coûts des matériaux, de la main-d'œuvre, et les taxes.
- Date d'émission et date de validité du devis.
- Suivi des devis : accepter ou refuser un devis par le client.

### 5. Calcul des Coûts
- Calculer les coûts des composants (matériaux, main-d'œuvre).
- Appliquer une marge bénéficiaire et gérer les taxes (TVA).
- Ajuster les coûts selon la qualité des matériaux et la productivité de la main-d'œuvre.

### 6. Affichage des Détails et Résultats
- Afficher un résumé complet du projet et des coûts associés.
- Générer un récapitulatif détaillé des matériaux, main-d'œuvre, et taxes appliquées.

## Exigences Techniques
- Java Streams, Collections, HashMap, Optional, Enum
- PostgreSQL pour la gestion de base de données
- Design Patterns : Singleton, Repository Pattern
- Application organisée en couches (Service, DAO, UI)
- Java Time API pour la gestion des dates
- Git et JIRA pour la gestion du projet
- Génération d'un fichier JAR

## User Stories

1. En tant que professionnel, je veux créer un projet de rénovation pour suivre tous les détails et les coûts associés.
2. Je veux associer un client à chaque projet pour la facturation et les devis.
3. Je veux ajouter des matériaux et des coûts unitaire et de transport pour estimer précisément les coûts du projet.
4. Je veux enregistrer les heures de travail et la productivité de la main-d'œuvre pour calculer le coût total.
5. Je veux générer un devis incluant les coûts des matériaux, main-d'œuvre, et équipements.
6. Je veux appliquer une TVA et une marge bénéficiaire pour obtenir le coût final du projet.

## Exemple d'utilisation

```plaintext
=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===
1. Créer un nouveau projet
2. Afficher les projets existants
3. Calculer le coût d'un projet
4. Quitter

Choisissez une option : 1
--- Recherche de client ---
Entrez le nom du client : Mme Dupont
Client trouvé !
Nom : Mme Dupont
Adresse : 12 Rue des Fleurs, Paris
Téléphone : 06 12345678

Entrez le nom du projet : Rénovation Cuisine Mme Dupont
--- Ajout des matériaux ---
Entrez le nom du matériau : Carrelage
Entrez la quantité (en m²) : 20
Entrez le coût unitaire (€/m²) : 30
Entrez le coût de transport (€) : 50
Entrez le coefficient de qualité : 1.1
--- Calcul du coût total ---
Entrez le pourcentage de TVA (%) : 20
Entrez la marge bénéficiaire (%) : 15
Calcul du coût en cours...
Coût total final : 3 381.00 €
