INSERT INTO adresse (rue, cpostal, ville)
VALUES
    ('123 Rue des Entrepreneurs', '79000', 'Niort'),
    ('45 Boulevard de l’Innovation', '79000', 'Niort'),
    ('4, boulevard Louis Tardy Pépinière d''entreprises du Niortais', '79000', 'Niort');

INSERT INTO utilisateur (username, password, email, role, plateforme_id, adresse_id)
VALUES
    ('admin', '$2a$10$5emptuO4dTsTCkllTOgBleDscsbHazzcdsEBZAOCXDFf1jWYKF.4e', 'admin@example.com', 'ADMIN', NULL, NULL),
    ('porteur1', '$2a$10$a.cYNFmTCbQaz.WbSQfc6.2/OlZNaZgFuRDzMUGsbD3nqzc6O5Lfu', 'porteur@example.com', 'PORTEUR', NULL, 1),
    ('parrain1', '$2a$10$v/8OtTdd7TccTQd71CmG9e3U0XxOTa4rLNwd0ng447fnF37cP/3Xe', 'parrain@example.com', 'PARRAIN', NULL, 2),
    ('plateforme1', '$2a$10$Pv2LaKuZljzFWk2OcgwbV.mbpdmUgUJlhG5ts6Calx0w//q90H70a', 'accompagnement@initiativedeuxsevres.fr', 'PLATEFORME', NULL, 3),
    ('user', '$2a$10$2OFtNEME3lhpp0OSKY4Xz.9h7g3Zz2Ls5Wk4BPHcmsw68.oLWQXSG', 'user@example.com', 'USER', NULL, NULL);

INSERT INTO plateforme (id, nom, telephone)
VALUES
    ((SELECT id FROM utilisateur WHERE username = 'plateforme1'),'Initiative Deux-Sèvres', '06 79 87 56 09');

INSERT INTO porteur (id, disponibilite, projet_id)
VALUES
    ((SELECT id FROM utilisateur WHERE username = 'porteur1'), 'Temps plein', NULL);

INSERT INTO project (starting_date, title, description)
VALUES
    ('2025-01-01', 'Projet Innovant', 'Un projet innovant pour changer le monde.');

UPDATE porteur SET projet_id = (SELECT id FROM project WHERE title = 'Projet Innovant') WHERE id = (SELECT id FROM utilisateur WHERE username = 'porteur1');

INSERT INTO parrain (id, parcours, expertise, deplacements, disponibilites)
VALUES
    ((SELECT id FROM utilisateur WHERE username = 'parrain1'), 'Ingénieur en informatique', 'Cloud et DevOps', 'Régional', 'Temps partiel');

UPDATE utilisateur SET plateforme_id = (SELECT id FROM plateforme WHERE nom = 'Initiative Deux-Sèvres') WHERE id = (SELECT id FROM utilisateur WHERE username = 'porteur1');
UPDATE utilisateur SET plateforme_id = (SELECT id FROM plateforme WHERE nom = 'Initiative Deux-Sèvres') WHERE id = (SELECT id FROM utilisateur WHERE username = 'parrain1');
UPDATE utilisateur SET plateforme_id = (SELECT id FROM plateforme WHERE nom = 'Initiative Deux-Sèvres') WHERE id = (SELECT id FROM utilisateur WHERE username = 'plateforme1');

