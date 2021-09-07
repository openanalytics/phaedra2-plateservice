package eu.openanalytics.phaedra.plateservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Table("hca_plate_measurement")
public class PlateMeasurement {
    @Id
    private Long id;
    @Column("plate_id")
    @NotNull
    private Long plateId;
    @Column("measurement_id")
    @NotNull
    private Long measurementId;
    @Column("linked_by")
    private String linkedBy;
    @Column("linked_on")
    private Date linkedOn;
}
