package org.simplon.TrouveTonMatch.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.simplon.TrouveTonMatch.model.Utilisateur;
import org.simplon.TrouveTonMatch.model.UserRole;

@Getter
@Setter
@Builder
public class UserDto {
    String username;
    UserRole role;
    Long id;

    public static UserDto fromEntity(Utilisateur user) {
        return new UserDto(user.getUsername(), user.getRole(), user.getId()); }
}
