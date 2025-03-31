# Log

## Mardi 24/09/24
- Lecture des documents du brief, de ceux sur discord ou dans teams
- Début d'écriture du cahier des charges
- MVP, MCD, User Stories
- Initialisation du dépôt Github
- Création du Github Project et création du backlog et des User Stories

## Mercredi 25/09/24
- Initialisation de l'application : vite + react, Spring boot
- Lectures intéressantes : [lien1](https://vectorlinux.com/spring-security-login-page-with-react/), [lien2](https://mossaabfrifita.github.io/docs/Spring%20Framework/spring)
- Début de configuration Spring Security, écriture du cours (obsidian://open?vault=Pro&file=Procedures%2FSpring%20Security%203.3.1%20et%20React)
- Ecriture des routes : [routes](../routes.md)
- Envoi du CDC à shiipou pour voir si je ne suis pas hors sujet

## Jeudi 26/09/24
- Réécriture du cdc en .md

## Jeudi 17/10/24
- Création des entities

## Vendredi 18/10/24
- Création des entities
- Utilisateur en abstract, etendu par Porteur, Parrain et Plateforme
- CRUD sur Utilisateur OK fonctionnel
- Routes api/utilisateurs/... ok

## Mercredi 06/11/24
- Branche TDD-gestion-des-projets : développer en TDD le projetService (exercice de cours)
- Ok pour getAllProjets et getProjetById et exception si projet non trouvé

## Vendredi 08/11/24
- Config Spring Security //TODO terminer jwtFilter et jwtService

## Dimanche 01/12/24
- Recommencer à zéro : nouvelle application TrouveTonMatch

## Lundi 02/12/24
- Mise en place du back, on commence par SpringSecurity et les configs (swagger, etc)

## Mercredi 04/12/24
- Authentification ok, côté back
- Sur Bruno : création d'un utilisateur, lecture de tous les utilisateurs
- Création front réact sans css: 
- Route login et login page ok
- Logout front et back ok
- Layout sur front ok
- Mise en contexte de username et role, lecture dans le layout ok
- Css pour layout et Homepage

## Jeudi 05/12/24
- Maquette Figma commencée
- Grosse refacto sur le front

## Vendredi 06/12/24
- route utilisateurs en front

## Vendredi 07/02/25
- rencontre avec le client
  - faire simple au niveau des nav
  - parrains : photo, nom, prénom, zone géographique, bio, mes projets matchés, nombre max de projets qu'il se sent d'accompagner. Quand ce nombre est atteint, il disparait de la liste. Moyenne 4 projets maw par parrain mais c'est variable selon leur statut (retraité, etc)
  - n'a pas la possibilité de refuser un match, c'est un bénévole. On doit pouvoir revenir en arrière rapidement sur ce choix.
  - a possibilité de se mettre en retrait du site si trop de projets suivis
  - finalement, le premier critère est géographique. Le domaine d'activité ou l'expertise ne sont pas utiles
- zone de mise en service de documents par la plateforme, dispo pour tous 
- les porteurs doivent pouvoir identifier les parrains par leur photo ou leur nom, il serait intéressant de présenter les parrains d'abord géographiquement, puis par nombre de projets déjà suivis
- les demandes de match, accords et refus sont notifiés à la plateforme
- ajouter une fonctionnalité pour que le porteur puisse ajouter ses interactions avec le parrain : 
  - type d'échange : en personne, au téléphone, par messagerie
  - date
  - durée
  - sujets couverts

## Vendredi 07/03/25

### Fait
- login
- logout

#### connexion en tant que Parrain
  - Affichage liste des projets de ma plateforme
  - Mon espace :
    - Mon profile, modifiable
    - Ma photo de profile, modifiable 
#### connexion en tant que Porteur
- Affichage liste des projets de ma plateforme
- Mon espace :
  - Mon profile, modifiable
  - Ma photo de profile, modifiable
  - Créer un projet si je n'en ai pas
  - Afficher mon projet si j'en ai un
- Affichage liste des parrains de ma plateforme
  - Affichage des détails filtrés d'un parrain
#### connexion en tant que Plateforme
- Affichage liste des parrains de ma plateforme
  - Bouton pour renouveler le code d'activation (contrôle sur l'état du compte)
- Affichage du détail d'un parrain
  - Possibilité de le modifier ou supprimer
- Affichage liste des porteurs de ma plateforme
  - Bouton pour renouveler le code d'activation (contrôle sur l'état du compte)
- Affichage du détail d'un porteur
  - Possibilité de le modifier ou supprimer
- Affichage de la liste des projets de ma plateforme
  - Affichage d'un projet en détail
- Affichage de la liste des matchs (à faire)

### A faire
- Rendre le header et la nav responsive [x]
- Modifier les balises html pour remplacer les div par des balises sémantiques[]
- Ajouter les labels, arial, tab index []
- Porteur : peut modifier mon projet []
- Porteur : peut supprimer mon projet []
- Porteur : peut choisir un parrain []
- Parrain : peut saisir une distance de déplacement []
- Parrain : peut mettre son profile en retrait ou en avant de la liste des parrains []
- Plateforme : peut mettre le profile d'un parrain en retrait ou en avant []
- Parrain : peut choisir le nombre de projets max qu'il souhaite accompagner [x]
- Plateforme : peut mettre des documents en ligne pour téléchargement => centre de documentation []
- Tous : peut télécharger un document depuis le centre de documentation []
- Porteur : peut saisir un compte-rendu d'accompagnement : []
  - date, heure de l'échange
  - Moyen de l'échange : appel téléphonique ou visio, messagerie, visite
  - Sujets de l'échange
  - Résumé de l'échange
  - Actions à mener avec date de livraison
  - Prochain rendez-vous prévu
  - Laisser texte libre pour le résumé et les actions à mener, avec petite modale d'aide à la rédaction ?
- Parrain et plateforme : consulter les comptes-rendus associés au projet []
- Porteur : consulter/modifier les comptes-rendus []
- Notifications : 
  - Quand un porteur choisi un parrain :
    - notification au parrain []
    - feedback au porteur []
    - notification à la plateforme []
  - Quand un porteur saisit un compte-rendu :
    - notification au parrain et à la plateforme []
  - Quand un parrain se met en retrait/en avant :
    - notification à la plateforme []
  - Quand un porteur crée un projet []
    - notification à la plateforme []

