package com.managetransfer.client;
// Generated Nov 7, 2014 7:09:07 AM by Hibernate Tools 4.0.0


import java.util.Date;

/**
 *          This class contains the connection detail. 
 *       
 */
public class BatchDetails  implements java.io.Serializable {


     private String batchName;
     private int successCount;
     private int failureCount;
     private Date modifyTime;
     private Date startTime;
     private String batchStatus;
     private String errorDescription;
     private String sequenceName;
     private int sequenceNumber;

    public BatchDetails() {
    }

	
    public BatchDetails(String batchName) {
        this.batchName = batchName;
    }
    public BatchDetails(String batchName, int successCount, int failureCount, Date modifyTime, Date startTime, String batchStatus, String errorDescription, String sequenceName, int sequenceNumber) {
       this.batchName = batchName;
       this.successCount = successCount;
       this.failureCount = failureCount;
       this.modifyTime = modifyTime;
       this.startTime = startTime;
       this.batchStatus = batchStatus;
       this.errorDescription = errorDescription;
       this.sequenceName = sequenceName;
       this.sequenceNumber = sequenceNumber;
    }
   
    public String getBatchName() {
        return this.batchName;
    }
    
    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }
    public int getSuccessCount() {
        return this.successCount;
    }
    
    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }
    public int getFailureCount() {
        return this.failureCount;
    }
    
    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }
    public Date getModifyTime() {
        return this.modifyTime;
    }
    
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public String getBatchStatus() {
        return this.batchStatus;
    }
    
    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }
    public String getErrorDescription() {
        return this.errorDescription;
    }
    
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
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




}


