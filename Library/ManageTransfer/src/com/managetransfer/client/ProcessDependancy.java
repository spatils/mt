package com.managetransfer.client;
// Generated 2 May, 2020 2:29:40 PM by Hibernate Tools 4.0.0



/**
 *          This class defines dependancy related to Process like name of the SDT variables and Process variable 
 *       
 */
public class ProcessDependancy  implements java.io.Serializable {


     private String processName;
     private int sequenceNumber;
     private String dependancyName;
     private String dependancyType;

    public ProcessDependancy() {
    }

	
    public ProcessDependancy(String processName, int sequenceNumber, String dependancyName) {
        this.processName = processName;
        this.sequenceNumber = sequenceNumber;
        this.dependancyName = dependancyName;
    }
    public ProcessDependancy(String processName, int sequenceNumber, String dependancyName, String dependancyType) {
       this.processName = processName;
       this.sequenceNumber = sequenceNumber;
       this.dependancyName = dependancyName;
       this.dependancyType = dependancyType;
    }
   
    public String getProcessName() {
        return this.processName;
    }
    
    public void setProcessName(String processName) {
        this.processName = processName;
    }
    public int getSequenceNumber() {
        return this.sequenceNumber;
    }
    
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    public String getDependancyName() {
        return this.dependancyName;
    }
    
    public void setDependancyName(String dependancyName) {
        this.dependancyName = dependancyName;
    }
    public String getDependancyType() {
        return this.dependancyType;
    }
    
    public void setDependancyType(String dependancyType) {
        this.dependancyType = dependancyType;
    }




}


