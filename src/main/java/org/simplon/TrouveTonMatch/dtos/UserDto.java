package org.simplon.TrouveTonMatch.dtos;

import lombok.*;
import org.simplon.TrouveTonMatch.model.UserApi;
import org.simplon.TrouveTonMatch.model.UserRole;

@Getter
@Setter
@Builder
public class UserDto {
    String username;
    UserRole role;
    Long id;

    public static UserDto fromEntity(UserApi user) {
        return new UserDto(user.getUsername(), user.getRole(), user.getId()); }
}
