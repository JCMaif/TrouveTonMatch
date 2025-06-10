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
```
</details>

* Porteur
<details>
```sh
username : porteur1
password : porteur
```
</details>

Vous pouvez tout essayer, la base de donnée sera réinitialisée à chaque démarrage de l'application.

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

