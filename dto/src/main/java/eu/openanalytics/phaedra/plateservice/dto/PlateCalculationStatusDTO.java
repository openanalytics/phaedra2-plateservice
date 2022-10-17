package eu.openanalytics.phaedra.plateservice.dto;

import eu.openanalytics.phaedra.plateservice.enumartion.CalculationStatus;
import eu.openanalytics.phaedra.util.dto.validation.OnCreate;
import eu.openanalytics.phaedra.util.dto.validation.OnUpdate;
import lombok.*;
import lombok.experimental.NonFinal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Value
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // Jackson deserialize compatibility
@NonFinal
public class PlateCalculationStatusDTO {
    @NotNull(message = "PlateId is mandatory", groups = {OnCreate.class})
    @Null(message = "PlateId cannot be changed", groups = {OnUpdate.class})
    Long plateId;
    @NotNull(message = "CalculationStatus is mandatory", groups = {OnCreate.class})
    @Null(message = "CalculationStatus cannot be changed", groups = {OnUpdate.class})
    CalculationStatus calculationStatus;
    String details;
}
