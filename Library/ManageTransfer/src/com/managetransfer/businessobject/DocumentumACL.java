package com.managetransfer.businessobject;
// Generated Dec 15, 2014 3:44:01 AM by Hibernate Tools 4.0.0


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *          This class contains Documentum ACL properties
 *       
 */
public class DocumentumACL  implements java.io.Serializable {


     private String objectId;
     private String aclName;
     private String aclClass;
     private String owner;
     private boolean isInternal;
     private String mtSequenceName;
     private int mtSequenceNumber;
     private Date mtCreateDate;
     private Date mtModifyDate;
     private String mtErrorDesc;
     private String mtStatus;
     private int mtProcessId;
     private Map ACLDetailsMap = new HashMap(0);

    public DocumentumACL() {
    }

	
    public DocumentumACL(String objectId) {
        this.objectId = objectId;
    }
    public DocumentumACL(String objectId, String aclName, String aclClass, String owner, boolean isInternal, String mtSequenceName, int mtSequenceNumber, Date mtCreateDate, Date mtModifyDate, String mtErrorDesc, String mtStatus, int mtProcessId, Map ACLDetailsMap) {
       this.objectId = objectId;
       this.aclName = aclName;
       this.aclClass = aclClass;
       this.owner = owner;
       this.isInternal = isInternal;
       this.mtSequenceName = mtSequenceName;
       this.mtSequenceNumber = mtSequenceNumber;
       this.mtCreateDate = mtCreateDate;
       this.mtModifyDate = mtModifyDate;
       this.mtErrorDesc = mtErrorDesc;
       this.mtStatus = mtStatus;
       this.mtProcessId = mtProcessId;
       this.ACLDetailsMap = ACLDetailsMap;
    }
   
    public String getObjectId() {
        return this.objectId;
    }
    
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getAclName() {
        return this.aclName;
    }
    
    public void setAclName(String aclName) {
        this.aclName = aclName;
    }
    public String getAclClass() {
        return this.aclClass;
    }
    
    public void setAclClass(String aclClass) {
        this.aclClass = aclClass;
    }
    public String getOwner() {
        return this.owner;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public boolean isIsInternal() {
        return this.isInternal;
    }
    
    public void setIsInternal(boolean isInternal) {
        this.isInternal = isInternal;
    }
    public String getMtSequenceName() {
        return this.mtSequenceName;
    }
    
    public void setMtSequenceName(String mtSequenceName) {
        this.mtSequenceName = mtSequenceName;
    }
    public int getMtSequenceNumber() {
        return this.mtSequenceNumber;
    }
    
    public void setMtSequenceNumber(int mtSequenceNumber) {
        this.mtSequenceNumber = mtSequenceNumber;
    }
    public Date getMtCreateDate() {
        return this.mtCreateDate;
    }
    
    public void setMtCreateDate(Date mtCreateDate) {
        this.mtCreateDate = mtCreateDate;
    }
    public Date getMtModifyDate() {
        return this.mtModifyDate;
    }
    
    public void setMtModifyDate(Date mtModifyDate) {
        this.mtModifyDate = mtModifyDate;
    }
    public String getMtErrorDesc() {
        return this.mtErrorDesc;
    }
    
    public void setMtErrorDesc(String mtErrorDesc) {
        this.mtErrorDesc = mtErrorDesc;
    }
    public String getMtStatus() {
        return this.mtStatus;
    }
    
    public void setMtStatus(String mtStatus) {
        this.mtStatus = mtStatus;
    }
    public int getMtProcessId() {
        return this.mtProcessId;
    }
    
    public void setMtProcessId(int mtProcessId) {
        this.mtProcessId = mtProcessId;
    }
    public Map getACLDetailsMap() {
        return this.ACLDetailsMap;
    }
    
    public void setACLDetailsMap(Map ACLDetailsMap) {
        this.ACLDetailsMap = ACLDetailsMap;
    }




}


