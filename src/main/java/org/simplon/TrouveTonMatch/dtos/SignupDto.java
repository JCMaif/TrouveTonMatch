package org.simplon.TrouveTonMatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.simplon.TrouveTonMatch.model.UserRole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    String username;
    String password;
    UserRole role;
}
