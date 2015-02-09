package com.managetransfer.businessobject;
// Generated Feb 9, 2015 8:45:14 AM by Hibernate Tools 4.0.0


import java.util.Date;

/**
 *          This class contains the connection detail. 
 *       
 */
public class Information  implements java.io.Serializable {


     private String objectId;
     private String policyNumber;
     private String objectName;
     private String folderPath;
     private String ownerName;
     private String aclName;
     private String insuredName;
     private String endorsementId;
     private Date expiryDate;
     private Date endorsementEffectiveDate;
     private String documentState;
     private String departmentType;
     private String mtSequenceName;
     private Integer mtSequenceNumber;
     private Date mtCreateDate;
     private Date mtModifyDate;
     private String mtErrorDesc;
     private String mtStatus;
     private Integer mtProcessId;
     private String mtRepositoryPath;

    public Information() {
    }

	
    public Information(String objectId) {
        this.objectId = objectId;
    }
    public Information(String objectId, String policyNumber, String objectName, String folderPath, String ownerName, String aclName, String insuredName, String endorsementId, Date expiryDate, Date endorsementEffectiveDate, String documentState, String departmentType, String mtSequenceName, Integer mtSequenceNumber, Date mtCreateDate, Date mtModifyDate, String mtErrorDesc, String mtStatus, Integer mtProcessId, String mtRepositoryPath) {
       this.objectId = objectId;
       this.policyNumber = policyNumber;
       this.objectName = objectName;
       this.folderPath = folderPath;
       this.ownerName = ownerName;
       this.aclName = aclName;
       this.insuredName = insuredName;
       this.endorsementId = endorsementId;
       this.expiryDate = expiryDate;
       this.endorsementEffectiveDate = endorsementEffectiveDate;
       this.documentState = documentState;
       this.departmentType = departmentType;
       this.mtSequenceName = mtSequenceName;
       this.mtSequenceNumber = mtSequenceNumber;
       this.mtCreateDate = mtCreateDate;
       this.mtModifyDate = mtModifyDate;
       this.mtErrorDesc = mtErrorDesc;
       this.mtStatus = mtStatus;
       this.mtProcessId = mtProcessId;
       this.mtRepositoryPath = mtRepositoryPath;
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
    public String getFolderPath() {
        return this.folderPath;
    }
    
    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
    public String getOwnerName() {
        return this.ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public String getAclName() {
        return this.aclName;
    }
    
    public void setAclName(String aclName) {
        this.aclName = aclName;
    }
    public String getInsuredName() {
        return this.insuredName;
    }
    
    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }
    public String getEndorsementId() {
        return this.endorsementId;
    }
    
    public void setEndorsementId(String endorsementId) {
        this.endorsementId = endorsementId;
    }
    public Date getExpiryDate() {
        return this.expiryDate;
    }
    
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    public Date getEndorsementEffectiveDate() {
        return this.endorsementEffectiveDate;
    }
    
    public void setEndorsementEffectiveDate(Date endorsementEffectiveDate) {
        this.endorsementEffectiveDate = endorsementEffectiveDate;
    }
    public String getDocumentState() {
        return this.documentState;
    }
    
    public void setDocumentState(String documentState) {
        this.documentState = documentState;
    }
    public String getDepartmentType() {
        return this.departmentType;
    }
    
    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType;
    }
    public String getMtSequenceName() {
        return this.mtSequenceName;
    }
    
    public void setMtSequenceName(String mtSequenceName) {
        this.mtSequenceName = mtSequenceName;
    }
    public Integer getMtSequenceNumber() {
        return this.mtSequenceNumber;
    }
    
    public void setMtSequenceNumber(Integer mtSequenceNumber) {
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
    public Integer getMtProcessId() {
        return this.mtProcessId;
    }
    
    public void setMtProcessId(Integer mtProcessId) {
        this.mtProcessId = mtProcessId;
    }
    public String getMtRepositoryPath() {
        return this.mtRepositoryPath;
    }
    
    public void setMtRepositoryPath(String mtRepositoryPath) {
        this.mtRepositoryPath = mtRepositoryPath;
    }




}


