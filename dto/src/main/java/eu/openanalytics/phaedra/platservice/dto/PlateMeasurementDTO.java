package eu.openanalytics.phaedra.platservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlateMeasurementDTO {
    private Long plateId;
    private Long measurementId;
    private String linkedBy;
    private Date linkedOn;
}
