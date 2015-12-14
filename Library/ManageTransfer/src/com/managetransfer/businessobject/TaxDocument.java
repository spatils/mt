package com.managetransfer.businessobject;
// Generated Jun 29, 2015 8:28:44 AM by Hibernate Tools 4.0.0


import java.util.Date;

/**
 *          This class contains the connection detail. 
 *       
 */
public class TaxDocument  implements java.io.Serializable {


     private String objectId;
     private String objectName;
     private String folderPath;
     private String ownerName;
     private String aclName;
     private String sub_document_type;
     private String reference_number;
     private String form_number;
     private String irs_name;
     private String document_status;
     private String document_source;
     private String reference_type;
     private Date receipt_date;
     private String irs_number;
     private String document_type;
     private String batch_control_number;
     private String document_control_number;
     private String mtSequenceName;
     private int mtSequenceNumber;
     private Date mtCreateDate;
     private Date mtModifyDate;
     private String mtErrorDesc;
     private String mtStatus;
     private int mtProcessId;
     private String mtRepositoryPath;

    public TaxDocument() {
    }

	
    public TaxDocument(String objectId) {
        this.objectId = objectId;
    }
    public TaxDocument(String objectId, String objectName, String folderPath, String ownerName, String aclName, String sub_document_type, String reference_number, String form_number, String irs_name, String document_status, String document_source, String reference_type, Date receipt_date, String irs_number, String document_type, String batch_control_number, String document_control_number, String mtSequenceName, int mtSequenceNumber, Date mtCreateDate, Date mtModifyDate, String mtErrorDesc, String mtStatus, int mtProcessId, String mtRepositoryPath) {
       this.objectId = objectId;
       this.objectName = objectName;
       this.folderPath = folderPath;
       this.ownerName = ownerName;
       this.aclName = aclName;
       this.sub_document_type = sub_document_type;
       this.reference_number = reference_number;
       this.form_number = form_number;
       this.irs_name = irs_name;
       this.document_status = document_status;
       this.document_source = document_source;
       this.reference_type = reference_type;
       this.receipt_date = receipt_date;
       this.irs_number = irs_number;
       this.document_type = document_type;
       this.batch_control_number = batch_control_number;
       this.document_control_number = document_control_number;
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
    public String getSub_document_type() {
        return this.sub_document_type;
    }
    
    public void setSub_document_type(String sub_document_type) {
        this.sub_document_type = sub_document_type;
    }
    public String getReference_number() {
        return this.reference_number;
    }
    
    public void setReference_number(String reference_number) {
        this.reference_number = reference_number;
    }
    public String getForm_number() {
        return this.form_number;
    }
    
    public void setForm_number(String form_number) {
        this.form_number = form_number;
    }
    public String getIrs_name() {
        return this.irs_name;
    }
    
    public void setIrs_name(String irs_name) {
        this.irs_name = irs_name;
    }
    public String getDocument_status() {
        return this.document_status;
    }
    
    public void setDocument_status(String document_status) {
        this.document_status = document_status;
    }
    public String getDocument_source() {
        return this.document_source;
    }
    
    public void setDocument_source(String document_source) {
        this.document_source = document_source;
    }
    public String getReference_type() {
        return this.reference_type;
    }
    
    public void setReference_type(String reference_type) {
        this.reference_type = reference_type;
    }
    public Date getReceipt_date() {
        return this.receipt_date;
    }
    
    public void setReceipt_date(Date receipt_date) {
        this.receipt_date = receipt_date;
    }
    public String getIrs_number() {
        return this.irs_number;
    }
    
    public void setIrs_number(String irs_number) {
        this.irs_number = irs_number;
    }
    public String getDocument_type() {
        return this.document_type;
    }
    
    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }
    public String getBatch_control_number() {
        return this.batch_control_number;
    }
    
    public void setBatch_control_number(String batch_control_number) {
        this.batch_control_number = batch_control_number;
    }
    public String getDocument_control_number() {
        return this.document_control_number;
    }
    
    public void setDocument_control_number(String document_control_number) {
        this.document_control_number = document_control_number;
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


