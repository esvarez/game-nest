package dev.ericksuarez.roomies.units.service.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Credentials {
    private String type;

    private Boolean temporary;

    private String value;
}
