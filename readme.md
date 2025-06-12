# Initiative Deux-Sèvres

## Accès
Cette application est hébergée sur mon serveur personnel, accessible à l'adresse :
(https://ttm.jc1932.synology.me)

## Jeux d'utilisateurs
Il est possible de tester cette application selon plusieurs rôles utilisateurs :

* Plateforme : a des droits étendus sur les autres utilisateurs, peut poster des documents, etc...

<details>
```sh
username : plateforme1
password : plateforme
```
</details>

* Parrain
<details>
```sh
username : parrain1
password : parrain

username : parrain2
password : parrain
```
</details>

* Porteur

<details>
```sh
username : porteur1
password : porteur

username : porteur2
password : porteur

username : porteur2
password : porteur
```
</details>

Vous pouvez tout essayer, la base de donnée sera réinitialisée à chaque démarrage de l'application.

Note : Pour la création d'un compte utilisateur, son password est créé par défaut (en attendant l'activation de la fonctionnalité d'envoi de mail automatique avec jeton ou code d'accès). 
A la première connexion, l'utilisateur doit modifier son password.

<details>
password par défaut : `password321`
</details>

## Code source

Le code source est présent sur github : (https://github.com/JCMaif/TrouveTonMatch)

### Installation

* Cloner le projet (`git clone https://github.com/JCMaif/TrouveTonMatch`)
* Créer une base de données postgres en local (ayant pour nom par défaut `match`, sinon modifier le fichier application.yml)
* aller dans le dossier du projet (`cd TrouveTonMatch`)
* Le back est un projet maven => `mvn clean install`
* Installer les dépendances du front => `npm install`
* lancer le back : `mvn spring-boot:run` - par défaut démarre sur le port 8080, modifiable dans le fichier application.yml 
* lancer le front : `npm run dev`
* accéder à (http://localhost:5173) - port modifiable en éditant le fichier `vite.config.js`

## Problèmes

L'application n'est pas finalisée, mais en cas de problème sur des fonctionnalités il est possible d'ouvrir une issue.
(https://github.com/JCMaif/TrouveTonMatch/issues)

## Déploiement

Le projet nécessite des variables d'environnement à adapter à votre environnement de déploiement. Les variables sont :
* DEPLOY_SSH_KEY : clé ssh pour accéder au serveur
* NAS : adresse ip du serveur
* NAS_USER : utilisateur pour accéder au serveur
* DOCKERHUB_USER : utilisateur dockerhub
* DOCKERHUB_TOKEN : token dockerhub
* SPRING_SECURITY_JWT_SECRET : clé de chiffrement jwt

Ces variables sont intégrées en tant que secrets github pour le repository. Elles sont utilisées dans le workflow de CI/CD (github actions).
Le workflow est aussi disponible sur github : (https://github.com/JCMaif/TrouveTonMatch/blob/main/.github/workflows/deploy.yml)

### Docker

Le fichier docker-compose-build.yml est utilisé pour construire les images docker. 
Le fichier docker-compose-nas.yml est utilisé pour copier les images sur le serveur.

Il prend en charge un fichier .env qui contient les variables suivantes :
* SPRING_MAIL_HOST
* SPRING_MAIL_USER
* SPRING_MAIL_PASSWORD
* SPRING_PROFILES_ACTIVE
* SPRING_DATASOURCE_URL
* SPRING_DATASOURCE_USERNAME
* SPRING_DATASOURCE_PASSWORD
* SPRING_JPA_HIBERNATE_DDL_AUTO
* POSTGRES_DB
* POSTGRES_USER
* POSTGRES_PASSWORD

Ce fichier .env ne figure pas dans le repository, il est à créer.

### Volumes

Le volume `uploads` est utilisé pour stocker les fichiers uploadés par les utilisateurs.
Le volume `pgdata` est utilisé pour stocker les données de la base de données.

## API

L'API utilise openapi (swagger) pour la documentation et l'essai des endpoints.
Elle est accessible à l'adresse suivante :
(https://ttm.jc1932.synology.me/api/public/documentation/swagger-ui/index.html)

Les endpoints sont protégés par jeton JWT. Il est nécessaire de se connecter en utilisant l'endpoint suivant : auth-controller : POST /auth/login, en utilisant les informations de connexion fournies dans le paragraphe [Jeux d'utilisateurs](#jeux-dutilisateurs).
Ensuite, il faut copier le jeton fourni en réponse (sans les ") et l'ajouter dans l'en-tête des requêtes en utilisant le bouton 'Authorize' tout en haut à droite de la page.