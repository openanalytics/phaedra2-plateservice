package eu.openanalytics.phaedra.plateservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Table("hca_plate_well")
@JsonInclude(Include.NON_NULL)
public class Well implements IValueObject {
	
	@Id
	@Column("well_id")
	private Long id;
	
	private Long plateId;
	
	@Column("row_nr")
	private Integer row;
	@Column("col_nr")
	private Integer column;

	private String description;
	
	@Column("is_valid")
	private Integer status;
	
	@Column("welltype_code")
	private String wellType;
	
	private String compoundType;
	private String compoundName;
	@Column("concentration")
	private Double compoundConcentration;
	
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPlateId() {
		return plateId;
	}
	public void setPlateId(Long plateId) {
		this.plateId = plateId;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Integer getColumn() {
		return column;
	}
	public void setColumn(Integer column) {
		this.column = column;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getWellType() {
		return wellType;
	}
	public void setWellType(String wellType) {
		this.wellType = wellType;
	}
	public String getCompoundType() {
		return compoundType;
	}
	public void setCompoundType(String compoundType) {
		this.compoundType = compoundType;
	}
	public String getCompoundName() {
		return compoundName;
	}
	public void setCompoundName(String compoundName) {
		this.compoundName = compoundName;
	}
	public Double getCompoundConcentration() {
		return compoundConcentration;
	}
	public void setCompoundConcentration(Double compoundConcentration) {
		this.compoundConcentration = compoundConcentration;
	}
}
