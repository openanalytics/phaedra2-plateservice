package eu.openanalytics.phaedra.plateservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Data
public class ExperimentProperty {
    @Id
    private Long id;

    @NotNull
    private String name;
    private String value;
}
