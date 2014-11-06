package com.managetransfer.client;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
public class PhasesDetails implements Serializable {
 	private static final long serialVersionUID = 6938550317765485308L;
	private   String  phaseName ;
	private   String  phaseType ;
	private   String  regionName;
	private   HashMap<String,String> phaseParametersString  = new HashMap<String,String>();
	private   HashMap<String,Date> phaseParametersDate  = new HashMap<String,Date>();
	private   HashMap<String,Integer> phaseParametersInteger  = new HashMap<String,Integer>();
    public PhasesDetails(  String phaseName ,   String phaseType, String regionName ){
		this.setPhaseName(phaseName);
		this.setPhaseType(phaseType);
		this.setRegionName(regionName);
	}
    public PhasesDetails(  String phaseName ,   String phaseType, String regionName ,HashMap<String,String> phaseParametersString ,HashMap<String,Date> phaseParametersDate,HashMap<String,Integer> phaseParametersInt){
		this.setPhaseName(phaseName);
		this.setPhaseType(phaseType);
		this.setRegionName(regionName);
		this.setPhaseParametersString(phaseParametersString);
		this.setPhaseParametersDate(phaseParametersDate);
		this.setPhaseParametersInteger(phaseParametersInt);
	}
	public PhasesDetails(){
		
	}
	 
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getPhaseName() {
		return phaseName;
	}
	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}
	
	public String getPhaseType() {
		return phaseType;
	}
	public void setPhaseType(String phaseType) {
		this.phaseType = phaseType;
	}
	public HashMap<String,String> getPhaseParametersString() {
		return phaseParametersString;
	}
	public void setPhaseParametersString(HashMap<String,String> phaseParametersString) {
		this.phaseParametersString = phaseParametersString;
	}
	public HashMap<String,Date> getPhaseParametersDate() {
		return phaseParametersDate;
	}
	public void setPhaseParametersDate(HashMap<String,Date> phaseParametersDate) {
		this.phaseParametersDate = phaseParametersDate;
	}
	public HashMap<String,Integer> getPhaseParametersInteger() {
		return phaseParametersInteger;
	}
	public void setPhaseParametersInteger(HashMap<String,Integer> phaseParametersInteger) {
		this.phaseParametersInteger = phaseParametersInteger;
	}
	
	
}
