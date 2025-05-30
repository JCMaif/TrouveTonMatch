package org.simplon.TrouveTonMatch.mapper;

import org.mapstruct.*;
import org.simplon.TrouveTonMatch.dtos.UserDto;
import org.simplon.TrouveTonMatch.model.Adresse;
import org.simplon.TrouveTonMatch.model.Parrain;
import org.simplon.TrouveTonMatch.model.Porteur;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.simplon.TrouveTonMatch.repository.ProjetRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UtilisateurMapper {

    default UserDto toDto(Utilisateur utilisateur, @Context ProjetRepository projetRepository) {
        if (utilisateur == null) {
            return null;
        }

        Long plateformeId = utilisateur.getPlateforme() != null ? utilisateur.getPlateforme().getId() : null;
        String plateformeName = utilisateur.getPlateforme() != null ? utilisateur.getPlateforme().getNom() : null;
        Adresse adresse = utilisateur.getAdresse();
        String firstname = utilisateur.getFirstname();
        String lastname = utilisateur.getLastname();
        String profilePicture = utilisateur.getProfilePicture();

        String parcours = null, expertise = null, deplacement = null, disponibilite = null, projetTitle = null;
        Long projetId = null;
        Integer maxProjects = null;
        Boolean isActive = true;
        Integer nbProjetsAffectes = null;

        if (utilisateur instanceof Parrain parrain) {
            parcours = parrain.getParcours();
            expertise = parrain.getExpertise();
            deplacement = parrain.getDeplacement();
            disponibilite = parrain.getDisponibilite();
            maxProjects = parrain.getMaxProjects();
            isActive = parrain.getIsActive();
            nbProjetsAffectes = projetRepository.countByParrain(parrain);

            if(nbProjetsAffectes >= maxProjects){
                isActive = false;
            }

        } else if (utilisateur instanceof Porteur porteur) {
            disponibilite = porteur.getDisponibilite();
            projetTitle = (porteur.getProjet() != null) ? porteur.getProjet().getTitle() : null;
            projetId = (porteur.getProjet() != null) ? porteur.getProjet().getId() : null;
        }

        return new UserDto(
                utilisateur.getUsername(),
                utilisateur.getRole(),
                utilisateur.getId(),
                utilisateur.getFirstname(),
                utilisateur.getLastname(),
                utilisateur.getProfilePicture(),
                plateformeId,
                plateformeName,
                utilisateur.getEmail(),
                utilisateur.getEnabled(),
                adresse,
                parcours,
                expertise,
                deplacement,
                disponibilite,
                projetTitle,
                projetId,
                maxProjects,
                isActive,
                nbProjetsAffectes
        );
    }

    Utilisateur toEntity(UserDto utilisateurDto);
}
