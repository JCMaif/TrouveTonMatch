package org.simplon.TrouveTonMatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.simplon.TrouveTonMatch.model.Plateforme;
import org.simplon.TrouveTonMatch.model.UserRole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    String username;
    String password;
    UserRole role;
    String email;
    Plateforme plateforme;
    private String firstname;
    private String lastname;

    public SignupDto(String username, String password, UserRole role, String email, Long aLong, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getPlateformeId() {
        return plateforme != null ? plateforme.getPlateformeId() : null;
    }
}
