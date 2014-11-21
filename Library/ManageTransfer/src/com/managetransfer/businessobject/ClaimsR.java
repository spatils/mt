package com.managetransfer.businessobject;
// Generated Nov 20, 2014 6:30:15 AM by Hibernate Tools 4.0.0



/**
 *          This class Repeating attributes of claims objects. 
 *       
 */
public class ClaimsR  implements java.io.Serializable {


     private String objectId;
     private int mtIndexId;
     private String adjusterId;
     private String adjusterName;

    public ClaimsR() {
    }

	
    public ClaimsR(String objectId, int mtIndexId) {
        this.objectId = objectId;
        this.mtIndexId = mtIndexId;
    }
    public ClaimsR(String objectId, int mtIndexId, String adjusterId, String adjusterName) {
       this.objectId = objectId;
       this.mtIndexId = mtIndexId;
       this.adjusterId = adjusterId;
       this.adjusterName = adjusterName;
    }
   
    public String getObjectId() {
        return this.objectId;
    }
    
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public int getMtIndexId() {
        return this.mtIndexId;
    }
    
    public void setMtIndexId(int mtIndexId) {
        this.mtIndexId = mtIndexId;
    }
    public String getAdjusterId() {
        return this.adjusterId;
    }
    
    public void setAdjusterId(String adjusterId) {
        this.adjusterId = adjusterId;
    }
    public String getAdjusterName() {
        return this.adjusterName;
    }
    
    public void setAdjusterName(String adjusterName) {
        this.adjusterName = adjusterName;
    }




}


