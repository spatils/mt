package com.managetransfer.businessobject;
// Generated 2 May, 2020 2:29:40 PM by Hibernate Tools 4.0.0


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 			This class workflow related information.
 * 		
 */
public class ProcessData  implements java.io.Serializable {


     private String taskId;
     private Map processDataPackageMap = new HashMap(0);
     private String processName;
     private String workflowName;
     private Date workflowStartDate;
     private String workflowState;
     private String workflowSupervisor;
     private String activityName;
     private Date activityReceiptDate;
     private String activityState;
     private Date activityCreateDate;
     private String performerName;
     private String queueName;
     private String newWorkflowId;
     private String taskName;
     private String instructions;
     private String mtSequenceName;
     private int mtSequenceNumber;
     private Date mtCreateDate;
     private Date mtModifyDate;
     private String mtErrorDesc;
     private String mtStatus;
     private int mtProcessId;

    public ProcessData() {
    }

	
    public ProcessData(String taskId) {
        this.taskId = taskId;
    }
    public ProcessData(String taskId, Map processDataPackageMap, String processName, String workflowName, Date workflowStartDate, String workflowState, String workflowSupervisor, String activityName, Date activityReceiptDate, String activityState, Date activityCreateDate, String performerName, String queueName, String newWorkflowId, String taskName, String instructions, String mtSequenceName, int mtSequenceNumber, Date mtCreateDate, Date mtModifyDate, String mtErrorDesc, String mtStatus, int mtProcessId) {
       this.taskId = taskId;
       this.processDataPackageMap = processDataPackageMap;
       this.processName = processName;
       this.workflowName = workflowName;
       this.workflowStartDate = workflowStartDate;
       this.workflowState = workflowState;
       this.workflowSupervisor = workflowSupervisor;
       this.activityName = activityName;
       this.activityReceiptDate = activityReceiptDate;
       this.activityState = activityState;
       this.activityCreateDate = activityCreateDate;
       this.performerName = performerName;
       this.queueName = queueName;
       this.newWorkflowId = newWorkflowId;
       this.taskName = taskName;
       this.instructions = instructions;
       this.mtSequenceName = mtSequenceName;
       this.mtSequenceNumber = mtSequenceNumber;
       this.mtCreateDate = mtCreateDate;
       this.mtModifyDate = mtModifyDate;
       this.mtErrorDesc = mtErrorDesc;
       this.mtStatus = mtStatus;
       this.mtProcessId = mtProcessId;
    }
   
    public String getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public Map getProcessDataPackageMap() {
        return this.processDataPackageMap;
    }
    
    public void setProcessDataPackageMap(Map processDataPackageMap) {
        this.processDataPackageMap = processDataPackageMap;
    }
    public String getProcessName() {
        return this.processName;
    }
    
    public void setProcessName(String processName) {
        this.processName = processName;
    }
    public String getWorkflowName() {
        return this.workflowName;
    }
    
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }
    public Date getWorkflowStartDate() {
        return this.workflowStartDate;
    }
    
    public void setWorkflowStartDate(Date workflowStartDate) {
        this.workflowStartDate = workflowStartDate;
    }
    public String getWorkflowState() {
        return this.workflowState;
    }
    
    public void setWorkflowState(String workflowState) {
        this.workflowState = workflowState;
    }
    public String getWorkflowSupervisor() {
        return this.workflowSupervisor;
    }
    
    public void setWorkflowSupervisor(String workflowSupervisor) {
        this.workflowSupervisor = workflowSupervisor;
    }
    public String getActivityName() {
        return this.activityName;
    }
    
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    public Date getActivityReceiptDate() {
        return this.activityReceiptDate;
    }
    
    public void setActivityReceiptDate(Date activityReceiptDate) {
        this.activityReceiptDate = activityReceiptDate;
    }
    public String getActivityState() {
        return this.activityState;
    }
    
    public void setActivityState(String activityState) {
        this.activityState = activityState;
    }
    public Date getActivityCreateDate() {
        return this.activityCreateDate;
    }
    
    public void setActivityCreateDate(Date activityCreateDate) {
        this.activityCreateDate = activityCreateDate;
    }
    public String getPerformerName() {
        return this.performerName;
    }
    
    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }
    public String getQueueName() {
        return this.queueName;
    }
    
    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
    public String getNewWorkflowId() {
        return this.newWorkflowId;
    }
    
    public void setNewWorkflowId(String newWorkflowId) {
        this.newWorkflowId = newWorkflowId;
    }
    public String getTaskName() {
        return this.taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getInstructions() {
        return this.instructions;
    }
    
    public void setInstructions(String instructions) {
        this.instructions = instructions;
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


