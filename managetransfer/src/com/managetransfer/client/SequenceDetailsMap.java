package com.managetransfer.client;

import java.io.Serializable;
 

public class SequenceDetailsMap implements Serializable {
	private static final long serialVersionUID = 429130034538673029L;
	public String getSequenceName() {
		return sequenceName;
	}
	public SequenceDetailsMap(String sequenceName, int sequenceNumber,
			String cronFrequency, String machineName, int threadCount,
			String phaseName , int commitCount , int batchSize , int dailySplit) {
		super();
		this.sequenceName = sequenceName;
		this.sequenceNumber = sequenceNumber;
		this.cronFrequency = cronFrequency;
		this.machineName = machineName;
		this.threadCount = threadCount;
		this.phaseName = phaseName;
		this.commitCount = commitCount;
		this.batchSize = batchSize;
		this.dailySplit  = dailySplit;
	}
	public SequenceDetailsMap(){
		
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
	public String getCronFrequency() {
		return cronFrequency;
	}
	public void setCronFrequency(String cronFrequency) {
		this.cronFrequency = cronFrequency;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public int getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	public String getPhaseName() {
		return phaseName;
	}
	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	public int getCommitCount() {
		return commitCount;
	}
	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}
	public int getDailySplit() {
		return dailySplit;
	}
	public void setDailySplit(int dailySplit) {
		this.dailySplit = dailySplit;
	}
	private String sequenceName;
	  private int sequenceNumber;
	  private String cronFrequency;
	  private String machineName;
	  private int threadCount;
	  private String phaseName;
	  private int batchSize;
	  private int commitCount;
	  private int dailySplit ;

}
	 
