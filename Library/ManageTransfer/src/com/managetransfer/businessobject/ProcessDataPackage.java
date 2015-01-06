package com.managetransfer.businessobject;
// Generated Jan 6, 2015 9:21:39 AM by Hibernate Tools 4.0.0



/**
 *          This class contains the   package   information
 *       
 */
public class ProcessDataPackage  implements java.io.Serializable {


     private String taskId;
     private int sequenceNumber;
     private String packageType;
     private String packageName;

    public ProcessDataPackage() {
    }

	
    public ProcessDataPackage(String taskId, int sequenceNumber) {
        this.taskId = taskId;
        this.sequenceNumber = sequenceNumber;
    }
    public ProcessDataPackage(String taskId, int sequenceNumber, String packageType, String packageName) {
       this.taskId = taskId;
       this.sequenceNumber = sequenceNumber;
       this.packageType = packageType;
       this.packageName = packageName;
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




}


