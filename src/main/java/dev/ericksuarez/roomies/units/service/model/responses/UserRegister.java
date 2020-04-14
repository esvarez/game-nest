package dev.ericksuarez.roomies.units.service.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserRegister {
    private String email;

    private boolean enabled;

    private String username;

    private String firstName;

}
