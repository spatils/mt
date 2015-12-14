package com.managetransfer.businessobject;
// Generated Jun 29, 2015 8:28:44 AM by Hibernate Tools 4.0.0



/**
 *          This class Repeating attributes of tax objects. 
 *       
 */
public class TaxDocumentR  implements java.io.Serializable {


     private String objectId;
     private int mtIndexId;
     private String comments;

    public TaxDocumentR() {
    }

	
    public TaxDocumentR(String objectId, int mtIndexId) {
        this.objectId = objectId;
        this.mtIndexId = mtIndexId;
    }
    public TaxDocumentR(String objectId, int mtIndexId, String comments) {
       this.objectId = objectId;
       this.mtIndexId = mtIndexId;
       this.comments = comments;
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
    public String getComments() {
        return this.comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }




}


