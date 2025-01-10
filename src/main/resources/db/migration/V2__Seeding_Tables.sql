-- Encodage des mots de passe avec bcrypt
-- Les mots de passe encodés ici correspondent à "admin" et "user" respectivement, puis "porteur" et "parrain".

INSERT INTO utilisateur (username, password, email, role, plateforme_id, adresse_id)
VALUES
    ('admin', '$2a$10$5emptuO4dTsTCkllTOgBleDscsbHazzcdsEBZAOCXDFf1jWYKF.4e', 'admin@example.com', 'ADMIN', NULL, NULL),
    ('user', '$2a$10$2OFtNEME3lhpp0OSKY4Xz.9h7g3Zz2Ls5Wk4BPHcmsw68.oLWQXSG', 'user@example.com', 'USER', NULL, NULL);

INSERT INTO plateforme (nom, telephone, email, adresse_id)
VALUES
    ('Initiative Deux-Sèvres', '06 79 87 56 09', 'accompagnement@initiativedeuxsevres.fr', NULL);

INSERT INTO adresse (rue, cpostal, ville)
VALUES
    ('123 Rue des Entrepreneurs', '79000', 'Niort');

-- Insertion du porteur dans la table utilisateur
INSERT INTO utilisateur (username, password, email, role, adresse_id)
VALUES
    ('porteur1', '$2a$10$a.cYNFmTCbQaz.WbSQfc6.2/OlZNaZgFuRDzMUGsbD3nqzc6O5Lfu', 'porteur@example.com', 'PORTEUR', 1);

-- Insertion dans la table porteur
INSERT INTO porteur (id, disponibilite, projet_id)
VALUES
    ((SELECT id FROM utilisateur WHERE username = 'porteur1'), 'Temps plein', NULL);

INSERT INTO project (starting_date, title, description)
VALUES
    ('2025-01-01', 'Projet Innovant', 'Un projet innovant pour changer le monde.');

-- Mise à jour du porteur pour référencer le projet
UPDATE porteur SET projet_id = (SELECT id FROM project WHERE title = 'Projet Innovant') WHERE id = (SELECT id FROM utilisateur WHERE username = 'porteur1');


INSERT INTO adresse (rue, cpostal, ville)
VALUES
    ('45 Boulevard de l’Innovation', '75000', 'Paris');

-- Insertion du parrain dans la table utilisateur
INSERT INTO utilisateur (username, password, email, role, adresse_id)
VALUES
    ('parrain1', '$2a$10$v/8OtTdd7TccTQd71CmG9e3U0XxOTa4rLNwd0ng447fnF37cP/3Xe', 'parrain@example.com', 'PARRAIN', 2);

-- Insertion dans la table parrain
INSERT INTO parrain (id, parcours, expertise, deplacements, disponibilites)
VALUES
    ((SELECT id FROM utilisateur WHERE username = 'parrain1'), 'Ingénieur en informatique', 'Cloud et DevOps', 'Régional', 'Temps partiel');
