package com.managetransfer.client;
// Generated Nov 10, 2014 9:52:48 AM by Hibernate Tools 4.0.0


import java.util.HashMap;
import java.util.Map;

/**
 *          This class contains the connection detail. 
 *       
 */
public class MappingDetailsH  implements java.io.Serializable {


     private String mappingName;
     private String mappingType;
     private String expressionName;
     private Map mappingDetailsMap = new HashMap(0);

    public MappingDetailsH() {
    }

	
    public MappingDetailsH(String mappingName, String mappingType) {
        this.mappingName = mappingName;
        this.mappingType = mappingType;
    }
    public MappingDetailsH(String mappingName, String mappingType, String expressionName, Map mappingDetailsMap) {
       this.mappingName = mappingName;
       this.mappingType = mappingType;
       this.expressionName = expressionName;
       this.mappingDetailsMap = mappingDetailsMap;
    }
   
    public String getMappingName() {
        return this.mappingName;
    }
    
    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }
    public String getMappingType() {
        return this.mappingType;
    }
    
    public void setMappingType(String mappingType) {
        this.mappingType = mappingType;
    }
    public String getExpressionName() {
        return this.expressionName;
    }
    
    public void setExpressionName(String expressionName) {
        this.expressionName = expressionName;
    }
    public Map getMappingDetailsMap() {
        return this.mappingDetailsMap;
    }
    
    public void setMappingDetailsMap(Map mappingDetailsMap) {
        this.mappingDetailsMap = mappingDetailsMap;
    }




}


