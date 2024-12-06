| Entité         | Route                                      | Méthode HTTP | Description                                                  | Statut        |
|----------------|--------------------------------------------|--------------|--------------------------------------------------------------|---------------|
| Utilisateur    | /api/users/create                          | POST         | Créer un utilisateur (parrain ou porteur)                     | [ ] Fait      |
|                | /api/users/{id}                            | GET          | Récupérer les détails d’un utilisateur                        | [ ] Fait      |
|                | /api/users/{id}                            | PUT          | Modifier un utilisateur                                       | [ ] Fait      |
|                | /api/users/{id}                            | DELETE       | Supprimer un utilisateur                                      | [ ] Fait      |
|                | /api/users/activate/{activationCode}       | POST         | Activer un compte utilisateur avec un code d'activation       | [ ] Fait      |
|                | /api/login                                 | POST         | Authentification d’un utilisateur                             | [ ] Fait      |
|                | /api/me                                    | GET          | Récupérer les informations de l'utilisateur connecté          | [ ] Fait      |
| Parrain        | /api/parrains                              | GET          | Récupérer la liste des parrains disponibles                   | [ ] Fait      |
|                | /api/parrains/{id}                         | PUT          | Mettre à jour les informations d'un parrain                   | [ ] Fait      |
|                | /api/parrains/matches                      | GET          | Récupérer les matchs associés au parrain connecté             | [ ] Fait      |
|                | /api/parrains/match/{projectId}            | POST         | Sélectionner un projet pour un match                          | [ ] Fait      |
| Porteur        | /api/porteurs                              | GET          | Récupérer la liste des porteurs disponibles                   | [ ] Fait      |
|                | /api/porteurs/{id}                         | PUT          | Mettre à jour les informations d’un porteur                   | [ ] Fait      |
|                | /api/porteurs/{id}/projects                | POST         | Créer un projet pour un porteur spécifique                    | [ ] Fait      |
|                | /api/porteurs/matches                      | GET          | Récupérer les matchs associés au porteur connecté             | [ ] Fait      |
| Projet         | /api/projects                              | GET          | Récupérer la liste de tous les projets disponibles            | [ ] Fait      |
|                | /api/projects/{id}                         | GET          | Récupérer les détails d’un projet spécifique                  | [ ] Fait      |
|                | /api/projects                              | POST         | Créer un nouveau projet                                       | [ ] Fait      |
|                | /api/projects/{id}                         | PUT          | Modifier un projet existant                                   | [ ] Fait      |
|                | /api/projects/{id}                         | DELETE       | Supprimer un projet                                           | [ ] Fait      |
|                | /api/projects/matches                      | GET          | Récupérer la liste des projets ayant un match                 | [ ] Fait      |
| Match          | /api/matches                               | GET          | Récupérer la liste des matchs entre parrains et porteurs      | [ ] Fait      |
|                | /api/matches                               | POST         | Créer un match entre un parrain et un projet                  | [ ] Fait      |
|                | /api/matches/{id}                          | DELETE       | Supprimer un match existant                                   | [ ] Fait      |
|                | /api/matches/parrain                       | GET          | Récupérer les matchs liés au parrain connecté                 | [ ] Fait      |
|                | /api/matches/porteur                       | GET          | Récupérer les matchs liés au porteur connecté                 | [ ] Fait      |
| Rôle           | /api/roles/assign                          | POST         | Assigner un rôle (Admin, Parrain, Porteur) à un utilisateur   | [ ] Fait      |
|                | /api/roles                                 | GET          | Récupérer la liste des rôles disponibles                      | [ ] Fait      |
|                | /api/roles/{roleId}/assign/{userId}        | POST         | Assigner un rôle spécifique à un utilisateur                  | [ ] Fait      |
| Notification   | /api/notifications/email                   | POST         | Envoyer un email d'activation ou de notification à un utilisateur | [ ] Fait      |
|                | /api/notifications                         | GET          | Récupérer les notifications pour l'utilisateur connecté       | [ ] Fait      |
|                | /api/notifications/mark-as-read/{id}       | POST         | Marquer une notification comme lue                            | [ ] Fait      |
| Statistique    | /api/statistics                            | GET          | Récupérer les statistiques globales                           | [ ] Fait      |
|                | /api/statistics/matches                    | GET          | Récupérer des statistiques spécifiques aux matchs             | [ ] Fait      |
|                | /api/statistics/users/{id}                 | GET          | Récupérer les statistiques spécifiques à un utilisateur       | [ ] Fait      |
| Adresse        | /api/addresses                             | POST         | Ajouter une nouvelle adresse                                  | [ ] Fait      |
|                | /api/addresses/{id}                        | PUT          | Modifier une adresse existante                                | [ ] Fait      |
|                | /api/addresses                             | GET          | Récupérer la liste des adresses                               | [ ] Fait      |
|                | /api/addresses/{id}                        | DELETE       | Supprimer une adresse                                         | [ ] Fait      |
