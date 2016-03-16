package com.managetransfer.businessobject;
// Generated Mar 15, 2016 1:49:44 PM by Hibernate Tools 4.0.0


import java.util.Date;

/**
 * 			This class stores task associated with the workflow
 * 		
 */
public class ProcessTaskList  implements java.io.Serializable {


     private String newWorkflowId;
     private String oldWorkflowId;
     private int sequenceNumber;
     private String targetActivityName;
     private String taskName;
     private String mtSequenceName;
     private int mtSequenceNumber;
     private Date mtCreateDate;
     private Date mtModifyDate;
     private String mtErrorDesc;
     private String mtStatus;
     private int mtProcessId;

    public ProcessTaskList() {
    }

	
    public ProcessTaskList(String newWorkflowId, String oldWorkflowId, int sequenceNumber) {
        this.newWorkflowId = newWorkflowId;
        this.oldWorkflowId = oldWorkflowId;
        this.sequenceNumber = sequenceNumber;
    }
    public ProcessTaskList(String newWorkflowId, String oldWorkflowId, int sequenceNumber, String targetActivityName, String taskName, String mtSequenceName, int mtSequenceNumber, Date mtCreateDate, Date mtModifyDate, String mtErrorDesc, String mtStatus, int mtProcessId) {
       this.newWorkflowId = newWorkflowId;
       this.oldWorkflowId = oldWorkflowId;
       this.sequenceNumber = sequenceNumber;
       this.targetActivityName = targetActivityName;
       this.taskName = taskName;
       this.mtSequenceName = mtSequenceName;
       this.mtSequenceNumber = mtSequenceNumber;
       this.mtCreateDate = mtCreateDate;
       this.mtModifyDate = mtModifyDate;
       this.mtErrorDesc = mtErrorDesc;
       this.mtStatus = mtStatus;
       this.mtProcessId = mtProcessId;
    }
   
    public String getNewWorkflowId() {
        return this.newWorkflowId;
    }
    
    public void setNewWorkflowId(String newWorkflowId) {
        this.newWorkflowId = newWorkflowId;
    }
    public String getOldWorkflowId() {
        return this.oldWorkflowId;
    }
    
    public void setOldWorkflowId(String oldWorkflowId) {
        this.oldWorkflowId = oldWorkflowId;
    }
    public int getSequenceNumber() {
        return this.sequenceNumber;
    }
    
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    public String getTargetActivityName() {
        return this.targetActivityName;
    }
    
    public void setTargetActivityName(String targetActivityName) {
        this.targetActivityName = targetActivityName;
    }
    public String getTaskName() {
        return this.taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getMtSequenceName() {
        return this.mtSequenceName;
    }
    
    public void setMtSequenceName(String mtSequenceName) {
        this.mtSequenceName = mtSequenceName;
    }
    public int getMtSequenceNumber() {
        return this.mtSequenceNumber;
    }
    
    public void setMtSequenceNumber(int mtSequenceNumber) {
        this.mtSequenceNumber = mtSequenceNumber;
    }
    public Date getMtCreateDate() {
        return this.mtCreateDate;
    }
    
    public void setMtCreateDate(Date mtCreateDate) {
        this.mtCreateDate = mtCreateDate;
    }
    public Date getMtModifyDate() {
        return this.mtModifyDate;
    }
    
    public void setMtModifyDate(Date mtModifyDate) {
        this.mtModifyDate = mtModifyDate;
    }
    public String getMtErrorDesc() {
        return this.mtErrorDesc;
    }
    
    public void setMtErrorDesc(String mtErrorDesc) {
        this.mtErrorDesc = mtErrorDesc;
    }
    public String getMtStatus() {
        return this.mtStatus;
    }
    
    public void setMtStatus(String mtStatus) {
        this.mtStatus = mtStatus;
    }
    public int getMtProcessId() {
        return this.mtProcessId;
    }
    
    public void setMtProcessId(int mtProcessId) {
        this.mtProcessId = mtProcessId;
    }




}


