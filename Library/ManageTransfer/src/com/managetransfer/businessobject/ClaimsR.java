package com.managetransfer.businessobject;
// Generated Jan 13, 2015 12:37:55 AM by Hibernate Tools 4.0.0


import java.util.Date;

/**
 *          This class Repeating attributes of claims objects. 
 *       
 */
public class ClaimsR  implements java.io.Serializable {


     private String objectId;
     private int mtIndexId;
     private String comments;
     private Date first_service_date;
     private Date last_service_date;
     private String adjuster_assigned;
     private String audit_review_type;
     private String adjuster_id;
     private String supervisor_id;
     private String claimant_name;
     private int claimant_number;
     private String claimant_info;
     private String method_name;
     private String receiving_site_location;
     private boolean structured_settlement;
     private String cl_arbitration_caption_id;
     private String cl_arbitration_caption_name;
     private String lob_r;
     private String ssn_r;
     private String jcn_r;
     private Date date_of_birth_r;
     private String ssn;
     private Date date_of_birth;
     private String jcn;

    public ClaimsR() {
    }

	
    public ClaimsR(String objectId, int mtIndexId) {
        this.objectId = objectId;
        this.mtIndexId = mtIndexId;
    }
    public ClaimsR(String objectId, int mtIndexId, String comments, Date first_service_date, Date last_service_date, String adjuster_assigned, String audit_review_type, String adjuster_id, String supervisor_id, String claimant_name, int claimant_number, String claimant_info, String method_name, String receiving_site_location, boolean structured_settlement, String cl_arbitration_caption_id, String cl_arbitration_caption_name, String lob_r, String ssn_r, String jcn_r, Date date_of_birth_r, String ssn, Date date_of_birth, String jcn) {
       this.objectId = objectId;
       this.mtIndexId = mtIndexId;
       this.comments = comments;
       this.first_service_date = first_service_date;
       this.last_service_date = last_service_date;
       this.adjuster_assigned = adjuster_assigned;
       this.audit_review_type = audit_review_type;
       this.adjuster_id = adjuster_id;
       this.supervisor_id = supervisor_id;
       this.claimant_name = claimant_name;
       this.claimant_number = claimant_number;
       this.claimant_info = claimant_info;
       this.method_name = method_name;
       this.receiving_site_location = receiving_site_location;
       this.structured_settlement = structured_settlement;
       this.cl_arbitration_caption_id = cl_arbitration_caption_id;
       this.cl_arbitration_caption_name = cl_arbitration_caption_name;
       this.lob_r = lob_r;
       this.ssn_r = ssn_r;
       this.jcn_r = jcn_r;
       this.date_of_birth_r = date_of_birth_r;
       this.ssn = ssn;
       this.date_of_birth = date_of_birth;
       this.jcn = jcn;
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
    public Date getFirst_service_date() {
        return this.first_service_date;
    }
    
    public void setFirst_service_date(Date first_service_date) {
        this.first_service_date = first_service_date;
    }
    public Date getLast_service_date() {
        return this.last_service_date;
    }
    
    public void setLast_service_date(Date last_service_date) {
        this.last_service_date = last_service_date;
    }
    public String getAdjuster_assigned() {
        return this.adjuster_assigned;
    }
    
    public void setAdjuster_assigned(String adjuster_assigned) {
        this.adjuster_assigned = adjuster_assigned;
    }
    public String getAudit_review_type() {
        return this.audit_review_type;
    }
    
    public void setAudit_review_type(String audit_review_type) {
        this.audit_review_type = audit_review_type;
    }
    public String getAdjuster_id() {
        return this.adjuster_id;
    }
    
    public void setAdjuster_id(String adjuster_id) {
        this.adjuster_id = adjuster_id;
    }
    public String getSupervisor_id() {
        return this.supervisor_id;
    }
    
    public void setSupervisor_id(String supervisor_id) {
        this.supervisor_id = supervisor_id;
    }
    public String getClaimant_name() {
        return this.claimant_name;
    }
    
    public void setClaimant_name(String claimant_name) {
        this.claimant_name = claimant_name;
    }
    public int getClaimant_number() {
        return this.claimant_number;
    }
    
    public void setClaimant_number(int claimant_number) {
        this.claimant_number = claimant_number;
    }
    public String getClaimant_info() {
        return this.claimant_info;
    }
    
    public void setClaimant_info(String claimant_info) {
        this.claimant_info = claimant_info;
    }
    public String getMethod_name() {
        return this.method_name;
    }
    
    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }
    public String getReceiving_site_location() {
        return this.receiving_site_location;
    }
    
    public void setReceiving_site_location(String receiving_site_location) {
        this.receiving_site_location = receiving_site_location;
    }
    public boolean isStructured_settlement() {
        return this.structured_settlement;
    }
    
    public void setStructured_settlement(boolean structured_settlement) {
        this.structured_settlement = structured_settlement;
    }
    public String getCl_arbitration_caption_id() {
        return this.cl_arbitration_caption_id;
    }
    
    public void setCl_arbitration_caption_id(String cl_arbitration_caption_id) {
        this.cl_arbitration_caption_id = cl_arbitration_caption_id;
    }
    public String getCl_arbitration_caption_name() {
        return this.cl_arbitration_caption_name;
    }
    
    public void setCl_arbitration_caption_name(String cl_arbitration_caption_name) {
        this.cl_arbitration_caption_name = cl_arbitration_caption_name;
    }
    public String getLob_r() {
        return this.lob_r;
    }
    
    public void setLob_r(String lob_r) {
        this.lob_r = lob_r;
    }
    public String getSsn_r() {
        return this.ssn_r;
    }
    
    public void setSsn_r(String ssn_r) {
        this.ssn_r = ssn_r;
    }
    public String getJcn_r() {
        return this.jcn_r;
    }
    
    public void setJcn_r(String jcn_r) {
        this.jcn_r = jcn_r;
    }
    public Date getDate_of_birth_r() {
        return this.date_of_birth_r;
    }
    
    public void setDate_of_birth_r(Date date_of_birth_r) {
        this.date_of_birth_r = date_of_birth_r;
    }
    public String getSsn() {
        return this.ssn;
    }
    
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    public Date getDate_of_birth() {
        return this.date_of_birth;
    }
    
    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
    public String getJcn() {
        return this.jcn;
    }
    
    public void setJcn(String jcn) {
        this.jcn = jcn;
    }




}


