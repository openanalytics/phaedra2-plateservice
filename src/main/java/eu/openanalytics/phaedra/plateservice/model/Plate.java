package eu.openanalytics.phaedra.plateservice.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Plate implements IValueObject {

	private long id;
	
	private String barcode;
	private String description;
	private String linkInfo;
	
	private int rows;
	private int columns;
	
	private int sequence;
	
	private int linkStatus;
	private String linkUser;
	private Date linkDate;
	
	private int calculationStatus;
	private String calculationError;
	private Date calculationDate;

	private int validationStatus;
	private String validationUser;
	private Date validationDate;

	private int approvalStatus;
	private String approvalUser;
	private Date approvalDate;

	private int uploadStatus;
	private String uploadUser;
	private Date uploadDate;
	
	private List<Well> wells = new ArrayList<>();

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
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
	
	public String getLinkInfo() {
		return linkInfo;
	}

	public void setLinkInfo(String linkInfo) {
		this.linkInfo = linkInfo;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public int getSequence() {
		return sequence;
	}
	
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	public int getLinkStatus() {
		return linkStatus;
	}
	
	public void setLinkStatus(int linkStatus) {
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
	
	public int getCalculationStatus() {
		return calculationStatus;
	}

	public void setCalculationStatus(int calculationStatus) {
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

	public int getValidationStatus() {
		return validationStatus;
	}

	public void setValidationStatus(int validationStatus) {
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

	public int getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(int approvalStatus) {
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

	public int getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(int uploadStatus) {
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

	public List<Well> getWells() {
		return wells;
	}

	public void setWells(List<Well> wells) {
		this.wells = wells;
	}
	
}
