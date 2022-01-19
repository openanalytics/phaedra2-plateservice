package eu.openanalytics.phaedra.platservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlateMeasurementDTO {
    @JsonIgnore
    private Long id;
    private Long measurementId;

    private Long plateId;
    private Boolean active;
    private String linkedBy;
    private Date linkedOn;

    private String name;
    private String barcode;
    private String description;
    private int rows;
    private int columns;
    private Date createdOn;
    private String createdBy;
    private String[] wellColumns;
    private String[] subWellColumns;
    private String[] imageChannels;
}
