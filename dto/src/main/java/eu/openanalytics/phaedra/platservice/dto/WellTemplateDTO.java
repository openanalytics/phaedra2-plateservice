package eu.openanalytics.phaedra.platservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import eu.openanalytics.phaedra.platservice.enumartion.WellType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.constraints.NotNull;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class WellTemplateDTO {
    private Long id;
    private String description;
    private Integer row;
    private Integer column;
    private Long plateTemplateId;
    private WellType wellType;
    private boolean skipped;
}
