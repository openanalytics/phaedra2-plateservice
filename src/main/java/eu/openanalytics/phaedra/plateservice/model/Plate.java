package eu.openanalytics.phaedra.plateservice.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table("hca_plate")
@JsonInclude(Include.NON_NULL)
public class Plate implements IValueObject {

	@Id
	@Column("plate_id")
	private Long id;
	
	private Long experimentId;
	
	private String barcode;
	private String description;
	
	@Column("plate_info")
	private String info;
	
	@Column("plate_rows")
	private Integer rows;
	@Column("plate_columns")
	private Integer columns;
	
	@Column("sequence_in_run")
	private Integer sequence;
	
	@Column("link_status")
	private Integer linkStatus;
	@Column("link_user")
	private String linkUser;
	@Column("link_dt")
	private Date linkDate;
	
	@Column("calc_status")
	private Integer calculationStatus;
	@Column("calc_error")
	private String calculationError;
	@Column("calc_dt")
	private Date calculationDate;

	@Column("validate_status")
	private Integer validationStatus;
	@Column("validate_user")
	private String validationUser;
	@Column("validate_dt")
	private Date validationDate;

	@Column("approve_status")
	private Integer approvalStatus;
	@Column("approve_user")
	private String approvalUser;
	@Column("approve_dt")
	private Date approvalDate;

	@Column("upload_status")
	private Integer uploadStatus;
	@Column("upload_user")
	private String uploadUser;
	@Column("upload_dt")
	private Date uploadDate;

	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	public Long getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(Long experimentId) {
		this.experimentId = experimentId;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getColumns() {
		return columns;
	}
	public void setColumns(Integer columns) {
		this.columns = columns;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Integer getLinkStatus() {
		return linkStatus;
	}
	public void setLinkStatus(Integer linkStatus) {
		this.linkStatus = linkStatus;
	}
	public String getLinkUser() {
		return linkUser;
	}
	public void setLinkUser(String linkUser) {
		this.linkUser = linkUser;
	}
	public Date getLinkDate() {
		return linkDate;
	}
	public void setLinkDate(Date linkDate) {
		this.linkDate = linkDate;
	}
	public Integer getCalculationStatus() {
		return calculationStatus;
	}
	public void setCalculationStatus(Integer calculationStatus) {
		this.calculationStatus = calculationStatus;
	}
	public String getCalculationError() {
		return calculationError;
	}
	public void setCalculationError(String calculationError) {
		this.calculationError = calculationError;
	}
	public Date getCalculationDate() {
		return calculationDate;
	}
	public void setCalculationDate(Date calculationDate) {
		this.calculationDate = calculationDate;
	}
	public Integer getValidationStatus() {
		return validationStatus;
	}
	public void setValidationStatus(Integer validationStatus) {
		this.validationStatus = validationStatus;
	}
	public String getValidationUser() {
		return validationUser;
	}
	public void setValidationUser(String validationUser) {
		this.validationUser = validationUser;
	}
	public Date getValidationDate() {
		return validationDate;
	}
	public void setValidationDate(Date validationDate) {
		this.validationDate = validationDate;
	}
	public Integer getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getApprovalUser() {
		return approvalUser;
	}
	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}
	public Date getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	public Integer getUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(Integer uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	public String getUploadUser() {
		return uploadUser;
	}
	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
}
