-- Insertion des adresses
INSERT INTO adresse (rue, cpostal, ville)
VALUES
    ('123 Rue des Entrepreneurs', '79000', 'Niort'),
    ('45 Boulevard de l’Innovation', '79000', 'Niort'),
    ('4, boulevard Louis Tardy Pépinière d''entreprises du Niortais', '79000', 'Niort');

-- Insertion des plateformes
INSERT INTO plateforme ( nom, telephone, email, adresse_id)
VALUES
    ( 'Initiative Deux-Sèvres', '06 79 87 56 09', 'accompagnement@initiativedeuxsevres.fr',3),
    ('Initiative Vienne', '06 02 02 02 02', 'accompagnement@initiativevienne.fr', null),
    ( 'Initiative Charentes', '06 16 16 16 16', 'accompagnement@initiativecharentes.fr', null);

-- Insertion des utilisateurs
INSERT INTO utilisateur (username, firstname, lastname, profile_picture, password, email, role, adresse_id, enabled, plateforme_id)
VALUES
    ('admin', 'John', 'Doe', '1_profile.jpg',
     '$2a$10$5emptuO4dTsTCkllTOgBleDscsbHazzcdsEBZAOCXDFf1jWYKF.4e', 'admin@example.com', 'ADMIN', NULL, true, null),

    ('porteur1', 'Jean', 'Bonbeurre', '2_profile.jpg',
     '$2a$10$a.cYNFmTCbQaz.WbSQfc6.2/OlZNaZgFuRDzMUGsbD3nqzc6O5Lfu', 'porteur1@example.com', 'PORTEUR', 1, true,
     (SELECT id FROM plateforme WHERE nom = 'Initiative Deux-Sèvres')),

    ('porteur2', 'Claire', 'Chaussette', '3_profile.jpg',
     '$2a$10$a.cYNFmTCbQaz.WbSQfc6.2/OlZNaZgFuRDzMUGsbD3nqzc6O5Lfu', 'porteur2@example.com', 'PORTEUR', NULL, true,
     (SELECT id FROM plateforme WHERE nom = 'Initiative Deux-Sèvres')),

    ('porteur3', 'Guy', 'Tariste', '4_profile.jpg',
     '$2a$10$a.cYNFmTCbQaz.WbSQfc6.2/OlZNaZgFuRDzMUGsbD3nqzc6O5Lfu', 'porteur3@example.com', 'PORTEUR', null, true,
     (SELECT id FROM plateforme WHERE nom = 'Initiative Deux-Sèvres')),

    ('porteur4', 'Alain', 'Térieur', '5_profile.jpg',
     '$2a$10$a.cYNFmTCbQaz.WbSQfc6.2/OlZNaZgFuRDzMUGsbD3nqzc6O5Lfu', 'porteur4@example.com', 'PORTEUR', null, true,
     (SELECT id FROM plateforme WHERE nom = 'Initiative Vienne')),

    ('porteur5', 'Emma', 'Grenade', '6_profile.jpg',
     '$2a$10$a.cYNFmTCbQaz.WbSQfc6.2/OlZNaZgFuRDzMUGsbD3nqzc6O5Lfu', 'porteur5@example.com', 'PORTEUR', null, true,
     (SELECT id FROM plateforme WHERE nom = 'Initiative Charentes')),

    ('porteur6', 'Tom', 'Atoo', '7_profile.jpg',
     '$2a$10$a.cYNFmTCbQaz.WbSQfc6.2/OlZNaZgFuRDzMUGsbD3nqzc6O5Lfu', 'porteur6@example.com', 'PORTEUR', null, true,
     (SELECT id FROM plateforme WHERE nom = 'Initiative Charentes')),

    ('parrain1', 'Sarah', 'Tatouille', '8_profile.jpg',
     '$2a$10$v/8OtTdd7TccTQd71CmG9e3U0XxOTa4rLNwd0ng447fnF37cP/3Xe', 'parrain1@example.com', 'PARRAIN', 2, true,
     (SELECT id FROM plateforme WHERE nom = 'Initiative Deux-Sèvres')),

    ('parrain2', 'Jacques', 'Pot', '9_profile.jpg',
     '$2a$10$v/8OtTdd7TccTQd71CmG9e3U0XxOTa4rLNwd0ng447fnF37cP/3Xe', 'parrain2@example.com', 'PARRAIN', NULL, false,
     (SELECT id FROM plateforme WHERE nom = 'Initiative Deux-Sèvres')),

    ('plateforme1', 'Paul', 'Ochon', '10_profile.jpg',
     '$2a$10$Pv2LaKuZljzFWk2OcgwbV.mbpdmUgUJlhG5ts6Calx0w//q90H70a', 'staff@initiativegironde.fr', 'STAFF', 3, true,
     (SELECT id FROM plateforme WHERE nom = 'Initiative Deux-Sèvres')),

    ('plateforme2', 'Lorraine', 'Ipsum', '11_profile.jpg',
     '$2a$10$Pv2LaKuZljzFWk2OcgwbV.mbpdmUgUJlhG5ts6Calx0w//q90H70a', 'staff@initiativevienne.fr', 'STAFF', NULL, true,
     (SELECT id FROM plateforme WHERE nom = 'Initiative Vienne')),

    ('plateforme3', 'Elvira', 'Moto', '12_profile.jpg',
     '$2a$10$Pv2LaKuZljzFWk2OcgwbV.mbpdmUgUJlhG5ts6Calx0w//q90H70a', 'staff@initiativecharentes.fr', 'STAFF', NULL, true,
     (SELECT id FROM plateforme WHERE nom = 'Initiative Charentes'));

-- Insertion des projets
INSERT INTO projet (starting_date, title, description, porteur_id)
VALUES
    ('2025-01-01', 'Projet Innovant', 'Un projet innovant pour changer le monde.', 2),
    ('2025-01-04', 'La pierre philosophale', 'Un projet qui vous couvrira d''or.',4),
    ('2025-01-06', 'Projet bancale', 'Un projet porté par des gens pas sûr d''eux.', 5),
    ('2025-01-11', 'Mission Mars', 'Allons-y avant Elon.', 6),
    ('2025-01-19', 'J''ai un', 'Inspiré par Jean-Michel AMoitié.',3);

-- Insertion des porteurs
INSERT INTO porteur (id, disponibilite, projet_id)
VALUES
    ((SELECT id FROM utilisateur WHERE username = 'porteur1'), 'Temps plein', (SELECT id FROM projet WHERE title = 'Projet Innovant')),
    ((SELECT id FROM utilisateur WHERE username = 'porteur2'), 'Temps plein', (SELECT id FROM projet WHERE title = 'J''ai un')),
    ((SELECT id FROM utilisateur WHERE username = 'porteur3'), 'Temps plein', (SELECT id FROM projet WHERE title = 'La pierre philosophale')),
    ((SELECT id FROM utilisateur WHERE username = 'porteur4'), 'Temps plein', (SELECT id FROM projet WHERE title = 'Projet bancale')),
    ((SELECT id FROM utilisateur WHERE username = 'porteur5'), 'Temps plein', (SELECT id FROM projet WHERE title = 'Mission Mars')),
    ((SELECT id FROM utilisateur WHERE username = 'porteur6'), null, null);

-- Insertion des parrains
INSERT INTO parrain (id, parcours, expertise, deplacement, disponibilite, max_projects, is_active)
VALUES
    ((SELECT id FROM utilisateur WHERE username = 'parrain1'), 'Ingénieur en informatique', 'Cloud et DevOps', 'Régional', 'Temps partiel',3, true),
    ((SELECT id FROM utilisateur WHERE username = 'parrain2'), 'Buiseness Angel', 'Recherche de financement', 'Régional', 'Temps partiel', 4, true);
