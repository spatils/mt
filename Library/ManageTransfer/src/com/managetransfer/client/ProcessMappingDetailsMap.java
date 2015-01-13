package com.managetransfer.client;
// Generated Jan 13, 2015 12:37:55 AM by Hibernate Tools 4.0.0



/**
 *          This class contains the Process mapping details for SDT and Activities
 *       
 */
public class ProcessMappingDetailsMap  implements java.io.Serializable {


     private String sourceProcessName;
     private int sequenceNumber;
     private String sourceObject;
     private String targetObject;
     private String mappingType;

    public ProcessMappingDetailsMap() {
    }

	
    public ProcessMappingDetailsMap(String sourceProcessName, int sequenceNumber, String sourceObject, String targetObject) {
        this.sourceProcessName = sourceProcessName;
        this.sequenceNumber = sequenceNumber;
        this.sourceObject = sourceObject;
        this.targetObject = targetObject;
    }
    public ProcessMappingDetailsMap(String sourceProcessName, int sequenceNumber, String sourceObject, String targetObject, String mappingType) {
       this.sourceProcessName = sourceProcessName;
       this.sequenceNumber = sequenceNumber;
       this.sourceObject = sourceObject;
       this.targetObject = targetObject;
       this.mappingType = mappingType;
    }
   
    public String getSourceProcessName() {
        return this.sourceProcessName;
    }
    
    public void setSourceProcessName(String sourceProcessName) {
        this.sourceProcessName = sourceProcessName;
    }
    public int getSequenceNumber() {
        return this.sequenceNumber;
    }
    
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    public String getSourceObject() {
        return this.sourceObject;
    }
    
    public void setSourceObject(String sourceObject) {
        this.sourceObject = sourceObject;
    }
    public String getTargetObject() {
        return this.targetObject;
    }
    
    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }
    public String getMappingType() {
        return this.mappingType;
    }
    
    public void setMappingType(String mappingType) {
        this.mappingType = mappingType;
    }




}


