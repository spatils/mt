package com.managetransfer.client;
// Generated Jan 13, 2015 12:37:55 AM by Hibernate Tools 4.0.0


import java.util.HashMap;
import java.util.Map;

/**
 *          This class contains the connection detail. 
 *       
 */
public class ProcessMappingDetails  implements java.io.Serializable {


     private String sourceProcessName;
     private String mappingType;
     private Map processMappingDetailsMap = new HashMap(0);

    public ProcessMappingDetails() {
    }

	
    public ProcessMappingDetails(String sourceProcessName, String mappingType) {
        this.sourceProcessName = sourceProcessName;
        this.mappingType = mappingType;
    }
    public ProcessMappingDetails(String sourceProcessName, String mappingType, Map processMappingDetailsMap) {
       this.sourceProcessName = sourceProcessName;
       this.mappingType = mappingType;
       this.processMappingDetailsMap = processMappingDetailsMap;
    }
   
    public String getSourceProcessName() {
        return this.sourceProcessName;
    }
    
    public void setSourceProcessName(String sourceProcessName) {
        this.sourceProcessName = sourceProcessName;
    }
    public String getMappingType() {
        return this.mappingType;
    }
    
    public void setMappingType(String mappingType) {
        this.mappingType = mappingType;
    }
    public Map getProcessMappingDetailsMap() {
        return this.processMappingDetailsMap;
    }
    
    public void setProcessMappingDetailsMap(Map processMappingDetailsMap) {
        this.processMappingDetailsMap = processMappingDetailsMap;
    }




}


