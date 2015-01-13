package com.managetransfer.businessobject;
// Generated Jan 13, 2015 12:37:55 AM by Hibernate Tools 4.0.0



/**
 *          This class contains the   package   information
 *       
 */
public class ProcessDataPackage  implements java.io.Serializable {


     private String taskId;
     private int sequenceNumber;
     private String packageType;
     private String packageName;
     private String packageId;

    public ProcessDataPackage() {
    }

	
    public ProcessDataPackage(String taskId, int sequenceNumber) {
        this.taskId = taskId;
        this.sequenceNumber = sequenceNumber;
    }
    public ProcessDataPackage(String taskId, int sequenceNumber, String packageType, String packageName, String packageId) {
       this.taskId = taskId;
       this.sequenceNumber = sequenceNumber;
       this.packageType = packageType;
       this.packageName = packageName;
       this.packageId = packageId;
    }
   
    public String getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public int getSequenceNumber() {
        return this.sequenceNumber;
    }
    
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    public String getPackageType() {
        return this.packageType;
    }
    
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
    public String getPackageName() {
        return this.packageName;
    }
    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getPackageId() {
        return this.packageId;
    }
    
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }




}


