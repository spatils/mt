package com.managetransfer.businessobject;
// Generated Mar 15, 2016 1:49:44 PM by Hibernate Tools 4.0.0



/**
 * 			This class stores task associated with the workflow
 * 		
 */
public class ProcessTaskTemplate  implements java.io.Serializable {


     private String sourceProcessName;
     private String activityName;
     private String activityState;
     private int sequenceNumber;
     private String targetActivityName;
     private String taskName;

    public ProcessTaskTemplate() {
    }

	
    public ProcessTaskTemplate(String sourceProcessName, String activityName, String activityState, int sequenceNumber) {
        this.sourceProcessName = sourceProcessName;
        this.activityName = activityName;
        this.activityState = activityState;
        this.sequenceNumber = sequenceNumber;
    }
    public ProcessTaskTemplate(String sourceProcessName, String activityName, String activityState, int sequenceNumber, String targetActivityName, String taskName) {
       this.sourceProcessName = sourceProcessName;
       this.activityName = activityName;
       this.activityState = activityState;
       this.sequenceNumber = sequenceNumber;
       this.targetActivityName = targetActivityName;
       this.taskName = taskName;
    }
   
    public String getSourceProcessName() {
        return this.sourceProcessName;
    }
    
    public void setSourceProcessName(String sourceProcessName) {
        this.sourceProcessName = sourceProcessName;
    }
    public String getActivityName() {
        return this.activityName;
    }
    
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    public String getActivityState() {
        return this.activityState;
    }
    
    public void setActivityState(String activityState) {
        this.activityState = activityState;
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




}


