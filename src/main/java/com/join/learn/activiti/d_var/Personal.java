package com.join.learn.activiti.d_var;

import java.io.Serializable;

public class Personal implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer personalId;
	private String personalName;
	
	public Integer getPersonalId() {
		return personalId;
	}
	public void setPersonalId(Integer personalId) {
		this.personalId = personalId;
	}
	public String getPersonalName() {
		return personalName;
	}
	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}
	@Override
	public String toString() {
		return "Personal [personalId=" + personalId + ", personalName=" + personalName + "]";
	}
	
}
