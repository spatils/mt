package com.managetransfer.client;
// Generated Mar 15, 2016 1:49:44 PM by Hibernate Tools 4.0.0



/**
 *          This class contains the connection detail. 
 *       
 */
public class SequenceDetailsMapH  implements java.io.Serializable {


     private String sequenceName;
     private int sequenceNumber;
     private String cronFrequency;
     private String machineName;
     private int threadCount;
     private String phaseName;
     private int commitCount;
     private int batchSize;
     private int dailySplit;

    public SequenceDetailsMapH() {
    }

	
    public SequenceDetailsMapH(String sequenceName, int sequenceNumber) {
        this.sequenceName = sequenceName;
        this.sequenceNumber = sequenceNumber;
    }
    public SequenceDetailsMapH(String sequenceName, int sequenceNumber, String cronFrequency, String machineName, int threadCount, String phaseName, int commitCount, int batchSize, int dailySplit) {
       this.sequenceName = sequenceName;
       this.sequenceNumber = sequenceNumber;
       this.cronFrequency = cronFrequency;
       this.machineName = machineName;
       this.threadCount = threadCount;
       this.phaseName = phaseName;
       this.commitCount = commitCount;
       this.batchSize = batchSize;
       this.dailySplit = dailySplit;
    }
   
    public String getSequenceName() {
        return this.sequenceName;
    }
    
    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }
    public int getSequenceNumber() {
        return this.sequenceNumber;
    }
    
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    public String getCronFrequency() {
        return this.cronFrequency;
    }
    
    public void setCronFrequency(String cronFrequency) {
        this.cronFrequency = cronFrequency;
    }
    public String getMachineName() {
        return this.machineName;
    }
    
    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
    public int getThreadCount() {
        return this.threadCount;
    }
    
    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }
    public String getPhaseName() {
        return this.phaseName;
    }
    
    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }
    public int getCommitCount() {
        return this.commitCount;
    }
    
    public void setCommitCount(int commitCount) {
        this.commitCount = commitCount;
    }
    public int getBatchSize() {
        return this.batchSize;
    }
    
    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
    public int getDailySplit() {
        return this.dailySplit;
    }
    
    public void setDailySplit(int dailySplit) {
        this.dailySplit = dailySplit;
    }




}


