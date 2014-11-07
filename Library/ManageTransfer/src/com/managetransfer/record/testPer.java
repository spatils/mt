package com.managetransfer.record;
// Generated Nov 7, 2014 7:09:07 AM by Hibernate Tools 4.0.0



/**
 *          This class contains the connection detail. 
 *       
 */
public class testPer  implements java.io.Serializable {


     private String objectId;
     private String policyNumber;
     private String objectName;

    public testPer() {
    }

	
    public testPer(String objectId) {
        this.objectId = objectId;
    }
    public testPer(String objectId, String policyNumber, String objectName) {
       this.objectId = objectId;
       this.policyNumber = policyNumber;
       this.objectName = objectName;
    }
   
    public String getObjectId() {
        return this.objectId;
    }
    
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getPolicyNumber() {
        return this.policyNumber;
    }
    
    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
    public String getObjectName() {
        return this.objectName;
    }
    
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }




}


