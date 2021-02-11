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
	private long id;
	
	private long plateId;
	
	@Column("row_nr")
	private int row;
	@Column("col_nr")
	private int column;

	private String description;
	
	@Column("is_valid")
	private int status;
	
	@Column("welltype_code")
	private String wellType;
	
	private String compoundType;
	private String compoundName;
	@Column("concentration")
	private double compoundConcentration;
	
	@Override
	public long getId() {
		return id;
	}
	@Override
	public void setId(long id) {
		this.id = id;
	}
	public long getPlateId() {
		return plateId;
	}
	public void setPlateId(long plateId) {
		this.plateId = plateId;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
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
	public double getCompoundConcentration() {
		return compoundConcentration;
	}
	public void setCompoundConcentration(double compoundConcentration) {
		this.compoundConcentration = compoundConcentration;
	}
}
