package com.managetransfer.businessobject;
// Generated Nov 20, 2014 6:30:15 AM by Hibernate Tools 4.0.0


import java.util.Date;

/**
 *          This class contains claims document at D7 repository 
 *       
 */
public class ClaimsD7  implements java.io.Serializable {


     private String objectId;
     private String claimNumber;
     private String objectName;
     private String folderPath;
     private String ownerName;
     private String aclName;
     private String claimantName;
     private String claimType;
     private Date effectiveDate;
     private String adjusterName;
     private String documentState;
     private String departmentType;
     private String mtSequenceName;
     private int mtSequenceNumber;
     private Date mtCreateDate;
     private Date mtModifyDate;
     private String mtErrorDesc;
     private String mtStatus;
     private int mtProcessId;
     private String mtRepositoryPath;

    public ClaimsD7() {
    }

	
    public ClaimsD7(String objectId) {
        this.objectId = objectId;
    }
    public ClaimsD7(String objectId, String claimNumber, String objectName, String folderPath, String ownerName, String aclName, String claimantName, String claimType, Date effectiveDate, String adjusterName, String documentState, String departmentType, String mtSequenceName, int mtSequenceNumber, Date mtCreateDate, Date mtModifyDate, String mtErrorDesc, String mtStatus, int mtProcessId, String mtRepositoryPath) {
       this.objectId = objectId;
       this.claimNumber = claimNumber;
       this.objectName = objectName;
       this.folderPath = folderPath;
       this.ownerName = ownerName;
       this.aclName = aclName;
       this.claimantName = claimantName;
       this.claimType = claimType;
       this.effectiveDate = effectiveDate;
       this.adjusterName = adjusterName;
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
    public String getClaimNumber() {
        return this.claimNumber;
    }
    
    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
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
    public String getClaimantName() {
        return this.claimantName;
    }
    
    public void setClaimantName(String claimantName) {
        this.claimantName = claimantName;
    }
    public String getClaimType() {
        return this.claimType;
    }
    
    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }
    public Date getEffectiveDate() {
        return this.effectiveDate;
    }
    
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    public String getAdjusterName() {
        return this.adjusterName;
    }
    
    public void setAdjusterName(String adjusterName) {
        this.adjusterName = adjusterName;
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
    public String getMtRepositoryPath() {
        return this.mtRepositoryPath;
    }
    
    public void setMtRepositoryPath(String mtRepositoryPath) {
        this.mtRepositoryPath = mtRepositoryPath;
    }




}


