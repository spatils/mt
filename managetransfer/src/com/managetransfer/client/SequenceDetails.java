package com.managetransfer.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SequenceDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6938550317765485308L;
	/**
	 * 
	 */
	private   String sequenceName ;
	private   Integer numberOfPhases ;
	private   String regionName;
	private   List<SequenceDetailsMap> sequenceDetails = new ArrayList<SequenceDetailsMap>();
	 
	
	public SequenceDetails(  String sequenceName ,   Integer numberOfPhases, String regionName,List<SequenceDetailsMap> sequenceDetails ){
		this.setSequenceName(sequenceName);
		this.setNumberOfPhases(numberOfPhases);
		this.setRegionName(regionName);
		this.setSequenceDetails(sequenceDetails);
	}
	public SequenceDetails(){
		
	}
	 
	public String getSequenceName() {
		return sequenceName;
	}
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	public int getNumberOfPhases() {
		return numberOfPhases;
	}
	public void setNumberOfPhases(int numberOfPhases) {
		this.numberOfPhases = numberOfPhases;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public List<SequenceDetailsMap> getSequenceDetails() {
		return sequenceDetails;
	}
	public void setSequenceDetails(List<SequenceDetailsMap> sequenceDetails) {
		this.sequenceDetails = sequenceDetails;
	}
	 
}
