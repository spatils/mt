package com.managetransfer.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
 

public class MappingDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6938550317765485308L;
	/**
	 * 
	 */
	private   String mappingName ;
	private   String mappingType ;
	private   ArrayList<String> sourceObjectList = new ArrayList<String>();;
	private   String targetObject ;
	private   String nameOfExpression;
	
	
	public MappingDetails(  String mappingName ,   String mappingType, ArrayList<String>sourceObjectList ,String targetObject,String nameOfExpression){
		this.setMappingName(mappingName);
		this.setMappingType(mappingType);
		this.setSourceObjectList(sourceObjectList);
		this.setTargetObject(targetObject);
		this.setNameOfExpression(nameOfExpression);
	}
	public MappingDetails(){
		
	}
	public String getNameOfExpression() {
		return nameOfExpression;
	}
	public void setNameOfExpression(String nameOfExpression) {
		this.nameOfExpression = nameOfExpression;
	}
	public  ArrayList<String> getSourceObjectList() {
		return sourceObjectList;
	}
	public void setSourceObjectList(ArrayList<String> sourceObjectList) {
		this.sourceObjectList = sourceObjectList;
	}
	public String getMappingName() {
		return mappingName;
	}
	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}
	public String getMappingType() {
		return mappingType;
	}
	public void setMappingType(String mappingType) {
		this.mappingType = mappingType;
	}
	public String getTargetObject() {
		return targetObject;
	}
	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}
	 
}
