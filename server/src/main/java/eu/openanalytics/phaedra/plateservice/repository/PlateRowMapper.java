package eu.openanalytics.phaedra.plateservice.repository;

import static eu.openanalytics.phaedra.plateservice.util.StatusUtils.getStatus;

import eu.openanalytics.phaedra.plateservice.enumeration.ApprovalStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.CalculationStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.LinkStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.UploadStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.ValidationStatus;
import eu.openanalytics.phaedra.plateservice.enumeration.WellStatus;
import eu.openanalytics.phaedra.plateservice.model.Plate;
import eu.openanalytics.phaedra.plateservice.model.Well;
import eu.openanalytics.phaedra.plateservice.record.ExperimentProjection;
import eu.openanalytics.phaedra.plateservice.record.PlateProjection;
import eu.openanalytics.phaedra.plateservice.record.ProjectProjection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

public class PlateRowMapper implements RowMapper<Plate> {
  @Override
  public Plate mapRow(ResultSet rs, int rowNum) throws SQLException {
    Plate plate = new Plate();
    plate.setId(rs.getLong("id"));
    plate.setBarcode(rs.getString("barcode"));
    plate.setDescription(rs.getString("description"));
    plate.setExperimentId(rs.getLong("experiment_id"));
    plate.setRows(rs.getInt("rows"));
    plate.setColumns(rs.getInt("columns"));
    plate.setSequence(rs.getInt("sequence"));
    plate.setLinkStatus(getStatus(rs.getString("link_status"), LinkStatus.class, LinkStatus.NOT_LINKED));
    plate.setLinkTemplateId(rs.getString("link_template_id"));
    plate.setLinkSource(rs.getString("link_source"));
    plate.setLinkedOn(rs.getDate("linked_on"));
    plate.setCalculationStatus(getStatus(rs.getString("calculation_status"), CalculationStatus.class, CalculationStatus.CALCULATION_NEEDED));
    plate.setCalculationError(rs.getString("calculation_error"));
    plate.setCalculatedBy(rs.getString("calculated_by"));
    plate.setCalculatedOn(rs.getDate("calculated_on"));
    plate.setValidationStatus(getStatus(rs.getString("validation_status"), ValidationStatus.class, ValidationStatus.VALIDATION_NOT_SET));
    plate.setValidatedBy(rs.getString("validated_by"));
    plate.setValidatedOn(rs.getDate("validated_on"));
    plate.setApprovalStatus(getStatus(rs.getString("approval_status"), ApprovalStatus.class, ApprovalStatus.APPROVAL_NOT_SET));
    plate.setApprovedBy(rs.getString("approved_by"));
    plate.setApprovedOn(rs.getDate("approved_on"));
    plate.setUploadStatus(getStatus(rs.getString("upload_status"), UploadStatus.class, UploadStatus.UPLOAD_NOT_SET));
    plate.setUploadedBy(rs.getString("uploaded_by"));
    plate.setUploadedOn(rs.getDate("uploaded_on"));
    plate.setCreatedOn(rs.getDate("created_on"));
    plate.setCreatedBy(rs.getString("created_by"));
    plate.setUpdatedOn(rs.getDate("updated_on"));
    plate.setUpdatedBy(rs.getString("updated_by"));
    plate.setInvalidatedReason(rs.getString("invalidated_reason"));
    plate.setDisapprovedReason(rs.getString("disapproved_reason"));

    ExperimentProjection experiment = new ExperimentProjection(
        rs.getLong("experiment_id"),
        rs.getString("experiment_name"),
        rs.getString("experiment_description"),
        rs.getLong("experiment_project_id")
    );
    plate.setExperiment(experiment);

    ProjectProjection project = new ProjectProjection(
        rs.getLong("project_id"),
        rs.getString("project_name"),
        rs.getString("project_description")
    );
    plate.setProject(project);

    return plate;
  }
}
