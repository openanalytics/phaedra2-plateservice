package eu.openanalytics.phaedra.plateservice.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Experiment implements IValueObject {

	private long id;
	
	private String name;
	private String description;
	private String comments;
	
	private String createdBy;
	private Date createdOn;
	
	private boolean closed;
	
	private String multiploMethod;
	private String multiploParameter;
	
	@Override
	public long getId() {
		return id;
	}
	@Override
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public boolean isClosed() {
		return closed;
	}
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	public String getMultiploMethod() {
		return multiploMethod;
	}
	public void setMultiploMethod(String multiploMethod) {
		this.multiploMethod = multiploMethod;
	}
	public String getMultiploParameter() {
		return multiploParameter;
	}
	public void setMultiploParameter(String multiploParameter) {
		this.multiploParameter = multiploParameter;
	}
}
