package dev.ericksuarez.roomies.units.service.model.dto;

import dev.ericksuarez.roomies.units.service.model.responses.Credentials;
import dev.ericksuarez.roomies.units.service.model.responses.UserRegister;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RegisterUserDto {
    private UserRegister userRegister;

    private Credentials credentials;
}
