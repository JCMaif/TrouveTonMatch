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

## Vendredi 07/12/24
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
  - 


