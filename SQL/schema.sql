CREATE TABLE Client (
                        id UUID PRIMARY KEY,
                        nom VARCHAR(255) NOT NULL,
                        adresse VARCHAR(255) NOT NULL,
                        telephone VARCHAR(20) NOT NULL,
                        professionnel BOOLEAN NOT NULL
);

CREATE TYPE EtatProjet AS ENUM ('EN_COURS', 'TERMINE', 'ANNULE');

CREATE TABLE Projet (
                        id UUID PRIMARY KEY,
                        nom_projet VARCHAR(255) NOT NULL,
                        marge_beneficiaire DECIMAL(10, 2) NOT NULL,
                        cout_total DECIMAL(10, 2) NOT NULL,
                        etat_projet etatprojet NOT NULL,
                        client_id UUID REFERENCES Client(id) ON DELETE SET NULL,
                        devis_id UUID REFERENCES Devis(id) ON DELETE SET NULL
);

CREATE TABLE Devis (
                       id UUID PRIMARY KEY,
                       montant_estime DECIMAL(10, 2) NOT NULL,
                       date_emission DATE NOT NULL,
                       date_validite DATE NOT NULL,
                       accepte BOOLEAN NOT NULL,
                       projet_id UUID REFERENCES Projet(id) ON DELETE SET NULL,
                       client_id UUID REFERENCES Client(id) ON DELETE SET NULL
);

ALTER TABLE Projet
    ADD COLUMN devis_id UUID;

ALTER TABLE Projet
    ADD CONSTRAINT fk_devis
        FOREIGN KEY (devis_id) REFERENCES Devis(id) ON DELETE SET NULL;

CREATE TYPE typecomposant AS ENUM ('MATERIAU', 'MAIN_DOEUVRE');

CREATE TABLE Composant (
                           id UUID PRIMARY KEY,
                           nom VARCHAR(255) NOT NULL,
                           cout_unitaire DECIMAL(10, 2) NOT NULL,
                           quantite DECIMAL(10, 2) NOT NULL,
                           type_composant typecomposant NOT NULL,
                           taux_tva DECIMAL(5, 2) NOT NULL
);


CREATE TABLE Materiau (
                          cout_transport DECIMAL(10, 2) NOT NULL,
                          coefficient_qualite DECIMAL(5, 2) NOT NULL
) INHERITS (Composant);


CREATE TABLE MainDoeuvre (
                             heures_travail DECIMAL(10, 2) NOT NULL,
                             productivite_ouvrier DECIMAL(5, 2) NOT NULL
) INHERITS (Composant);