package com.managetransfer.businessobject;
// Generated Feb 19, 2015 4:10:49 AM by Hibernate Tools 4.0.0



/**
 *          This class contains documentum ACL details
 *       
 */
public class DocumentumACLDetails  implements java.io.Serializable {


     private String objectId;
     private int SequenceNumber;
     private String accessorName;
     private String basicPermission;
     private String extndPermission;

    public DocumentumACLDetails() {
    }

	
    public DocumentumACLDetails(String objectId, int SequenceNumber) {
        this.objectId = objectId;
        this.SequenceNumber = SequenceNumber;
    }
    public DocumentumACLDetails(String objectId, int SequenceNumber, String accessorName, String basicPermission, String extndPermission) {
       this.objectId = objectId;
       this.SequenceNumber = SequenceNumber;
       this.accessorName = accessorName;
       this.basicPermission = basicPermission;
       this.extndPermission = extndPermission;
    }
   
    public String getObjectId() {
        return this.objectId;
    }
    
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public int getSequenceNumber() {
        return this.SequenceNumber;
    }
    
    public void setSequenceNumber(int SequenceNumber) {
        this.SequenceNumber = SequenceNumber;
    }
    public String getAccessorName() {
        return this.accessorName;
    }
    
    public void setAccessorName(String accessorName) {
        this.accessorName = accessorName;
    }
    public String getBasicPermission() {
        return this.basicPermission;
    }
    
    public void setBasicPermission(String basicPermission) {
        this.basicPermission = basicPermission;
    }
    public String getExtndPermission() {
        return this.extndPermission;
    }
    
    public void setExtndPermission(String extndPermission) {
        this.extndPermission = extndPermission;
    }




}


