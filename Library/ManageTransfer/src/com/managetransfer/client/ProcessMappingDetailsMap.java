package com.managetransfer.client;
// Generated Jun 29, 2015 8:28:44 AM by Hibernate Tools 4.0.0



/**
 *          This class contains the proces mapping detail. 
 *       
 */
public class ProcessMappingDetailsMap  implements java.io.Serializable {


     private String sourceProcessName;
     private String targetProcessName;
     private String mappingType;
     private int sequenceNumber;
     private String sourceAttribute;
     private int sourceDependanySeq;
     private String targetAttribute;
     private String sourceDependanyObject;

    public ProcessMappingDetailsMap() {
    }

	
    public ProcessMappingDetailsMap(String sourceProcessName, String targetProcessName, String mappingType, int sequenceNumber) {
        this.sourceProcessName = sourceProcessName;
        this.targetProcessName = targetProcessName;
        this.mappingType = mappingType;
        this.sequenceNumber = sequenceNumber;
    }
    public ProcessMappingDetailsMap(String sourceProcessName, String targetProcessName, String mappingType, int sequenceNumber, String sourceAttribute, int sourceDependanySeq, String targetAttribute, String sourceDependanyObject) {
       this.sourceProcessName = sourceProcessName;
       this.targetProcessName = targetProcessName;
       this.mappingType = mappingType;
       this.sequenceNumber = sequenceNumber;
       this.sourceAttribute = sourceAttribute;
       this.sourceDependanySeq = sourceDependanySeq;
       this.targetAttribute = targetAttribute;
       this.sourceDependanyObject = sourceDependanyObject;
    }
   
    public String getSourceProcessName() {
        return this.sourceProcessName;
    }
    
    public void setSourceProcessName(String sourceProcessName) {
        this.sourceProcessName = sourceProcessName;
    }
    public String getTargetProcessName() {
        return this.targetProcessName;
    }
    
    public void setTargetProcessName(String targetProcessName) {
        this.targetProcessName = targetProcessName;
    }
    public String getMappingType() {
        return this.mappingType;
    }
    
    public void setMappingType(String mappingType) {
        this.mappingType = mappingType;
    }
    public int getSequenceNumber() {
        return this.sequenceNumber;
    }
    
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    public String getSourceAttribute() {
        return this.sourceAttribute;
    }
    
    public void setSourceAttribute(String sourceAttribute) {
        this.sourceAttribute = sourceAttribute;
    }
    public int getSourceDependanySeq() {
        return this.sourceDependanySeq;
    }
    
    public void setSourceDependanySeq(int sourceDependanySeq) {
        this.sourceDependanySeq = sourceDependanySeq;
    }
    public String getTargetAttribute() {
        return this.targetAttribute;
    }
    
    public void setTargetAttribute(String targetAttribute) {
        this.targetAttribute = targetAttribute;
    }
    public String getSourceDependanyObject() {
        return this.sourceDependanyObject;
    }
    
    public void setSourceDependanyObject(String sourceDependanyObject) {
        this.sourceDependanyObject = sourceDependanyObject;
    }




}


