package com.managetransfer.businessobject;
// Generated Dec 10, 2014 3:00:52 AM by Hibernate Tools 4.0.0



/**
 *          This class Repeating attributes of claims objects. 
 *       
 */
public class ClaimsRD7  implements java.io.Serializable {


     private String objectId;
     private int mtIndexId;
     private String adjusterId;
     private String adjusterName;

    public ClaimsRD7() {
    }

	
    public ClaimsRD7(String objectId, int mtIndexId) {
        this.objectId = objectId;
        this.mtIndexId = mtIndexId;
    }
    public ClaimsRD7(String objectId, int mtIndexId, String adjusterId, String adjusterName) {
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


