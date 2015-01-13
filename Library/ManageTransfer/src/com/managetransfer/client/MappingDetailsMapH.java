package com.managetransfer.client;
// Generated Jan 13, 2015 12:37:55 AM by Hibernate Tools 4.0.0



/**
 *          This class contains the mapping detail. 
 *       
 */
public class MappingDetailsMapH  implements java.io.Serializable {


     private String mappingName;
     private int SequenceNumber;
     private String sourceObject;
     private String targetObject;
     private String mappingType;

    public MappingDetailsMapH() {
    }

	
    public MappingDetailsMapH(String mappingName, int SequenceNumber, String sourceObject, String targetObject) {
        this.mappingName = mappingName;
        this.SequenceNumber = SequenceNumber;
        this.sourceObject = sourceObject;
        this.targetObject = targetObject;
    }
    public MappingDetailsMapH(String mappingName, int SequenceNumber, String sourceObject, String targetObject, String mappingType) {
       this.mappingName = mappingName;
       this.SequenceNumber = SequenceNumber;
       this.sourceObject = sourceObject;
       this.targetObject = targetObject;
       this.mappingType = mappingType;
    }
   
    public String getMappingName() {
        return this.mappingName;
    }
    
    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }
    public int getSequenceNumber() {
        return this.SequenceNumber;
    }
    
    public void setSequenceNumber(int SequenceNumber) {
        this.SequenceNumber = SequenceNumber;
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


