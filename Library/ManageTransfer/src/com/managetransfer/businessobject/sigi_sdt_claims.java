package com.managetransfer.businessobject;
// Generated Jan 13, 2015 12:37:55 AM by Hibernate Tools 4.0.0


import java.util.Date;

/**
 *          This class contains the claims SDT information     
 *       
 */
public class sigi_sdt_claims  implements java.io.Serializable {


     private String taskId;
     private String compensability_indicator;
     private String claimtype_cd;
     private String file_format_cd;
     private String inHoldComments;
     private String ECN;
     private boolean pausedComments;
     private boolean delegateCheck;
     private String form_number;
     private String form_benefits_state;
     private String bill_id;
     private String workflow_comments;
     private String priority;
     private String IName;
     private String comments;
     private String arbitration_caption_name;
     private String policy_state_code;
     private String workitem_state;
     private String option1;
     private String option;
     private String r_modifier;
     private Date r_modify_date;
     private String document_status;
     private String insured_name;
     private String object_name;
     private String adjuster_id;
     private boolean mitchell_flag;
     private String claimant_name;
     private String sub_document_type;
     private String document_type;
     private String claim_number;

    public sigi_sdt_claims() {
    }

	
    public sigi_sdt_claims(String taskId) {
        this.taskId = taskId;
    }
    public sigi_sdt_claims(String taskId, String compensability_indicator, String claimtype_cd, String file_format_cd, String inHoldComments, String ECN, boolean pausedComments, boolean delegateCheck, String form_number, String form_benefits_state, String bill_id, String workflow_comments, String priority, String IName, String comments, String arbitration_caption_name, String policy_state_code, String workitem_state, String option1, String option, String r_modifier, Date r_modify_date, String document_status, String insured_name, String object_name, String adjuster_id, boolean mitchell_flag, String claimant_name, String sub_document_type, String document_type, String claim_number) {
       this.taskId = taskId;
       this.compensability_indicator = compensability_indicator;
       this.claimtype_cd = claimtype_cd;
       this.file_format_cd = file_format_cd;
       this.inHoldComments = inHoldComments;
       this.ECN = ECN;
       this.pausedComments = pausedComments;
       this.delegateCheck = delegateCheck;
       this.form_number = form_number;
       this.form_benefits_state = form_benefits_state;
       this.bill_id = bill_id;
       this.workflow_comments = workflow_comments;
       this.priority = priority;
       this.IName = IName;
       this.comments = comments;
       this.arbitration_caption_name = arbitration_caption_name;
       this.policy_state_code = policy_state_code;
       this.workitem_state = workitem_state;
       this.option1 = option1;
       this.option = option;
       this.r_modifier = r_modifier;
       this.r_modify_date = r_modify_date;
       this.document_status = document_status;
       this.insured_name = insured_name;
       this.object_name = object_name;
       this.adjuster_id = adjuster_id;
       this.mitchell_flag = mitchell_flag;
       this.claimant_name = claimant_name;
       this.sub_document_type = sub_document_type;
       this.document_type = document_type;
       this.claim_number = claim_number;
    }
   
    public String getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getCompensability_indicator() {
        return this.compensability_indicator;
    }
    
    public void setCompensability_indicator(String compensability_indicator) {
        this.compensability_indicator = compensability_indicator;
    }
    public String getClaimtype_cd() {
        return this.claimtype_cd;
    }
    
    public void setClaimtype_cd(String claimtype_cd) {
        this.claimtype_cd = claimtype_cd;
    }
    public String getFile_format_cd() {
        return this.file_format_cd;
    }
    
    public void setFile_format_cd(String file_format_cd) {
        this.file_format_cd = file_format_cd;
    }
    public String getInHoldComments() {
        return this.inHoldComments;
    }
    
    public void setInHoldComments(String inHoldComments) {
        this.inHoldComments = inHoldComments;
    }
    public String getECN() {
        return this.ECN;
    }
    
    public void setECN(String ECN) {
        this.ECN = ECN;
    }
    public boolean isPausedComments() {
        return this.pausedComments;
    }
    
    public void setPausedComments(boolean pausedComments) {
        this.pausedComments = pausedComments;
    }
    public boolean isDelegateCheck() {
        return this.delegateCheck;
    }
    
    public void setDelegateCheck(boolean delegateCheck) {
        this.delegateCheck = delegateCheck;
    }
    public String getForm_number() {
        return this.form_number;
    }
    
    public void setForm_number(String form_number) {
        this.form_number = form_number;
    }
    public String getForm_benefits_state() {
        return this.form_benefits_state;
    }
    
    public void setForm_benefits_state(String form_benefits_state) {
        this.form_benefits_state = form_benefits_state;
    }
    public String getBill_id() {
        return this.bill_id;
    }
    
    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }
    public String getWorkflow_comments() {
        return this.workflow_comments;
    }
    
    public void setWorkflow_comments(String workflow_comments) {
        this.workflow_comments = workflow_comments;
    }
    public String getPriority() {
        return this.priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getIName() {
        return this.IName;
    }
    
    public void setIName(String IName) {
        this.IName = IName;
    }
    public String getComments() {
        return this.comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getArbitration_caption_name() {
        return this.arbitration_caption_name;
    }
    
    public void setArbitration_caption_name(String arbitration_caption_name) {
        this.arbitration_caption_name = arbitration_caption_name;
    }
    public String getPolicy_state_code() {
        return this.policy_state_code;
    }
    
    public void setPolicy_state_code(String policy_state_code) {
        this.policy_state_code = policy_state_code;
    }
    public String getWorkitem_state() {
        return this.workitem_state;
    }
    
    public void setWorkitem_state(String workitem_state) {
        this.workitem_state = workitem_state;
    }
    public String getOption1() {
        return this.option1;
    }
    
    public void setOption1(String option1) {
        this.option1 = option1;
    }
    public String getOption() {
        return this.option;
    }
    
    public void setOption(String option) {
        this.option = option;
    }
    public String getR_modifier() {
        return this.r_modifier;
    }
    
    public void setR_modifier(String r_modifier) {
        this.r_modifier = r_modifier;
    }
    public Date getR_modify_date() {
        return this.r_modify_date;
    }
    
    public void setR_modify_date(Date r_modify_date) {
        this.r_modify_date = r_modify_date;
    }
    public String getDocument_status() {
        return this.document_status;
    }
    
    public void setDocument_status(String document_status) {
        this.document_status = document_status;
    }
    public String getInsured_name() {
        return this.insured_name;
    }
    
    public void setInsured_name(String insured_name) {
        this.insured_name = insured_name;
    }
    public String getObject_name() {
        return this.object_name;
    }
    
    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }
    public String getAdjuster_id() {
        return this.adjuster_id;
    }
    
    public void setAdjuster_id(String adjuster_id) {
        this.adjuster_id = adjuster_id;
    }
    public boolean isMitchell_flag() {
        return this.mitchell_flag;
    }
    
    public void setMitchell_flag(boolean mitchell_flag) {
        this.mitchell_flag = mitchell_flag;
    }
    public String getClaimant_name() {
        return this.claimant_name;
    }
    
    public void setClaimant_name(String claimant_name) {
        this.claimant_name = claimant_name;
    }
    public String getSub_document_type() {
        return this.sub_document_type;
    }
    
    public void setSub_document_type(String sub_document_type) {
        this.sub_document_type = sub_document_type;
    }
    public String getDocument_type() {
        return this.document_type;
    }
    
    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }
    public String getClaim_number() {
        return this.claim_number;
    }
    
    public void setClaim_number(String claim_number) {
        this.claim_number = claim_number;
    }




}


