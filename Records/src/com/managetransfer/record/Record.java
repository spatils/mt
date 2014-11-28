package com.managetransfer.record;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;
 

public class Record {
	private String typeOfRecord ;
	private String statusOfRecord ;
	private int    currentSequenceNumber;
	private String currentSequenceName ;
	private String nextSequenceName;
	private String currentPhase;
	private String nextPhase;
	private String errorDetails;
	private HashMap<String,String> listOfStringAtrributes = new HashMap<String, String>() ;
	private HashMap<String,Integer> listOfIntAttributes  = new HashMap<String, Integer>() ;
	private HashMap<String,Date> listOfDateAttributes  = new HashMap<String, Date>() ;
	private HashMap<String,Long> listOfLongAtrributes  = new HashMap<String, Long>() ;
	private String sequenceName ;
	private int sequenceNumber =999;
	private int processId =999 ;
	private Date createDate;
	private Date modifyDate;
	
	final Logger logger = Logger.getLogger(Record.class.getName()) ;
	public String getTypeOfRecord() {
		return typeOfRecord;
	}

	public void setTypeOfRecord(String typeOfRecord) {
		this.typeOfRecord = typeOfRecord;
	}

	public String getStatusOfRecord() {
		return statusOfRecord;
	}

	public void setStatusOfRecord(String statusOfRecord) {
		this.statusOfRecord = statusOfRecord;
	}

	public int getCurrentSequenceNumber() {
		return currentSequenceNumber;
	}

	public void setCurrentSequenceNumber(int currentSequenceNumber) {
		this.currentSequenceNumber = currentSequenceNumber;
	}

	public String getCurrentSequenceName() {
		return currentSequenceName;
	}

	public void setCurrentSequenceName(String currentSequenceName) {
		this.currentSequenceName = currentSequenceName;
	}

	public String getNextSequenceName() {
		return nextSequenceName;
	}

	public void setNextSequenceName(String nextSequenceName) {
		this.nextSequenceName = nextSequenceName;
	}

	public String getCurrentPhase() {
		return currentPhase;
	}

	public void setCurrentPhase(String currentPhase) {
		this.currentPhase = currentPhase;
	}

	public String getNextPhase() {
		return nextPhase;
	}

	public void setNextPhase(String nextPhase) {
		this.nextPhase = nextPhase;
	}

	public String getErrorDetails() {
		return this.errorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}

	public HashMap<String, String> getListOfStringAtrributes() {
		return listOfStringAtrributes;
	}

	public void setListOfStringAtrributes(
			HashMap<String, String> listOfStringAtrributes) {
		this.listOfStringAtrributes = listOfStringAtrributes;
	}

	public HashMap<String, Integer> getListOfIntAttributes() {
		return listOfIntAttributes;
	}

	public void setListOfIntAttributes(HashMap<String, Integer> listOfIntAttributes) {
		this.listOfIntAttributes = listOfIntAttributes;
	}

	public HashMap<String, Date> getListOfDateAttributes() {
		return listOfDateAttributes;
	}

	public void setListOfDateAttributes(HashMap<String, Date> listOfDateAttributes) {
		this.listOfDateAttributes = listOfDateAttributes;
	}

	public HashMap<String, Long> getListOfLongAtrributes() {
		return listOfLongAtrributes;
	}

	public void setListOfLongAtrributes(HashMap<String, Long> listOfLongAtrributes) {
		this.listOfLongAtrributes = listOfLongAtrributes;
	}

	
	
	public static void main(String[] args) {
	 

	}

	public void getPropertyValues(Object object) {
		 
		
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

}
