package com.managetransfer.businessobject;
// Generated Jan 6, 2015 9:21:39 AM by Hibernate Tools 4.0.0


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

    public ProcessData() {
    }

	
    public ProcessData(String taskId) {
        this.taskId = taskId;
    }
    public ProcessData(String taskId, Map processDataPackageMap, String processName, String workflowName, Date workflowStartDate, String workflowState, String workflowSupervisor, String activityName, Date activityReceiptDate, String activityState, Date activityCreateDate, String performerName, String queueName) {
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




}


