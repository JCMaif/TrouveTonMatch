-- Encodage des mots de passe avec bcrypt
-- Les mots de passe encodés ici correspondent à "admin" et "user" respectivement, puis "porteur" et "parrain".

INSERT INTO utilisateur (username, password, email, role, plateforme_id, adresse_id)
VALUES
    ('admin', '$2a$10$XHkfpb4z9EO/v7QHx3Q2Le7fOmiIcDeNOOnScXOuYHlFafAv5vXxu', 'admin@example.com', 'admin', NULL, NULL),
    ('user', '$2a$10$cOF4i5lDlYCsSLZEvCPHCOS.oel5.kMb7mYldG3RIvhfFIPgR7o6e', 'user@example.com', 'user', NULL, NULL);

INSERT INTO plateforme (nom, telephone, email, adresse_id)
VALUES
    ('Initiative Deux-Sèvres', '06 79 87 56 09', 'accompagnement@initiativedeuxsevres.fr', NULL);

INSERT INTO adresse (rue, cpostal, ville)
VALUES
    ('123 Rue des Entrepreneurs', '79000', 'Niort');

-- Insertion du porteur dans la table utilisateur
INSERT INTO utilisateur (username, password, email, role, adresse_id)
VALUES
    ('porteur1', '$2a$10$3j1h5U9U8j8ROw9VOy5poeu.9oI3klN/9lBtp5TrPMvULeDZYVoAa', 'porteur@example.com', 'porteur', 1);

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
    ('parrain1', '$2a$10$E4nbf09x9Mbv/2LJZ8yPDeQflvBckjYrjWhzYXzYkm0dN8VI1kBWm', 'parrain@example.com', 'parrain', 2);

-- Insertion dans la table parrain
INSERT INTO parrain (id, parcours, expertise, deplacements, disponibilites)
VALUES
    ((SELECT id FROM utilisateur WHERE username = 'parrain1'), 'Ingénieur en informatique', 'Cloud et DevOps', 'Régional', 'Temps partiel');
