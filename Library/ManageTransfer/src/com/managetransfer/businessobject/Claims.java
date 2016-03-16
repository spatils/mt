package com.managetransfer.businessobject;
// Generated Mar 15, 2016 1:49:44 PM by Hibernate Tools 4.0.0


import java.util.Date;

/**
 *          This class contains the connection detail. 
 *       
 */
public class Claims  implements java.io.Serializable {


     private String objectId;
     private String objectName;
     private String folderPath;
     private String ownerName;
     private String aclName;
     private String batch_control_number;
     private String document_control_number;
     private String medbill_claimant_num;
     private String orgination_department;
     private String ext_ref_number;
     private String document_status;
     private String provider_number;
     private String provider_name;
     private Date receipt_date;
     private String document_type;
     private String external_system_id;
     private String mail_identifier;
     private Date policy_term_exp_date;
     private String loss_state_code;
     private String claim_status;
     private Date policy_term_eff_date;
     private String siu_indicator;
     private String file_format_cd;
     private Date loss_date;
     private String policy_number;
     private String restricted_claim_ind;
     private String claims_region_cd;
     private String policy_state_code;
     private Date date_closed;
     private String restricted_indicator;
     private String claim_number;
     private String irs_name;
     private int irs_number;
     private String form_claimant_name;
     private String form_claimant_type_cd;
     private int form_claimant_number;
     private String dp_type;
     private String ds_interviewer;
     private String ds_party;
     private Date scan_date;
     private String document_source;
     private boolean infant_case;
     private String form_benefits_state;
     private String fax_number;
     private String ds_role;
     private Date ds_date;
     private Date dp_date;
     private Date sigi_null_date;
     private String party_id;
     private String arbitration_caption_id;
     private String arbitration_caption_name;
     private String insured_name;
     private String adjuster_name;
     private String form_number;
     private String bill_id;
     private boolean has_updates;
     private String occurrence_code;
     private String sub_document_type;
     private String compensability_indicator;
     private String claim_type;
     private String project_matter;
     private int taxonomy_indicator;
     private boolean is_duplicate;
     private boolean is_junk;
     private String mtSequenceName;
     private int mtSequenceNumber;
     private Date mtCreateDate;
     private Date mtModifyDate;
     private String mtErrorDesc;
     private String mtStatus;
     private int mtProcessId;
     private String mtRepositoryPath;

    public Claims() {
    }

	
    public Claims(String objectId) {
        this.objectId = objectId;
    }
    public Claims(String objectId, String objectName, String folderPath, String ownerName, String aclName, String batch_control_number, String document_control_number, String medbill_claimant_num, String orgination_department, String ext_ref_number, String document_status, String provider_number, String provider_name, Date receipt_date, String document_type, String external_system_id, String mail_identifier, Date policy_term_exp_date, String loss_state_code, String claim_status, Date policy_term_eff_date, String siu_indicator, String file_format_cd, Date loss_date, String policy_number, String restricted_claim_ind, String claims_region_cd, String policy_state_code, Date date_closed, String restricted_indicator, String claim_number, String irs_name, int irs_number, String form_claimant_name, String form_claimant_type_cd, int form_claimant_number, String dp_type, String ds_interviewer, String ds_party, Date scan_date, String document_source, boolean infant_case, String form_benefits_state, String fax_number, String ds_role, Date ds_date, Date dp_date, Date sigi_null_date, String party_id, String arbitration_caption_id, String arbitration_caption_name, String insured_name, String adjuster_name, String form_number, String bill_id, boolean has_updates, String occurrence_code, String sub_document_type, String compensability_indicator, String claim_type, String project_matter, int taxonomy_indicator, boolean is_duplicate, boolean is_junk, String mtSequenceName, int mtSequenceNumber, Date mtCreateDate, Date mtModifyDate, String mtErrorDesc, String mtStatus, int mtProcessId, String mtRepositoryPath) {
       this.objectId = objectId;
       this.objectName = objectName;
       this.folderPath = folderPath;
       this.ownerName = ownerName;
       this.aclName = aclName;
       this.batch_control_number = batch_control_number;
       this.document_control_number = document_control_number;
       this.medbill_claimant_num = medbill_claimant_num;
       this.orgination_department = orgination_department;
       this.ext_ref_number = ext_ref_number;
       this.document_status = document_status;
       this.provider_number = provider_number;
       this.provider_name = provider_name;
       this.receipt_date = receipt_date;
       this.document_type = document_type;
       this.external_system_id = external_system_id;
       this.mail_identifier = mail_identifier;
       this.policy_term_exp_date = policy_term_exp_date;
       this.loss_state_code = loss_state_code;
       this.claim_status = claim_status;
       this.policy_term_eff_date = policy_term_eff_date;
       this.siu_indicator = siu_indicator;
       this.file_format_cd = file_format_cd;
       this.loss_date = loss_date;
       this.policy_number = policy_number;
       this.restricted_claim_ind = restricted_claim_ind;
       this.claims_region_cd = claims_region_cd;
       this.policy_state_code = policy_state_code;
       this.date_closed = date_closed;
       this.restricted_indicator = restricted_indicator;
       this.claim_number = claim_number;
       this.irs_name = irs_name;
       this.irs_number = irs_number;
       this.form_claimant_name = form_claimant_name;
       this.form_claimant_type_cd = form_claimant_type_cd;
       this.form_claimant_number = form_claimant_number;
       this.dp_type = dp_type;
       this.ds_interviewer = ds_interviewer;
       this.ds_party = ds_party;
       this.scan_date = scan_date;
       this.document_source = document_source;
       this.infant_case = infant_case;
       this.form_benefits_state = form_benefits_state;
       this.fax_number = fax_number;
       this.ds_role = ds_role;
       this.ds_date = ds_date;
       this.dp_date = dp_date;
       this.sigi_null_date = sigi_null_date;
       this.party_id = party_id;
       this.arbitration_caption_id = arbitration_caption_id;
       this.arbitration_caption_name = arbitration_caption_name;
       this.insured_name = insured_name;
       this.adjuster_name = adjuster_name;
       this.form_number = form_number;
       this.bill_id = bill_id;
       this.has_updates = has_updates;
       this.occurrence_code = occurrence_code;
       this.sub_document_type = sub_document_type;
       this.compensability_indicator = compensability_indicator;
       this.claim_type = claim_type;
       this.project_matter = project_matter;
       this.taxonomy_indicator = taxonomy_indicator;
       this.is_duplicate = is_duplicate;
       this.is_junk = is_junk;
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
    public String getMedbill_claimant_num() {
        return this.medbill_claimant_num;
    }
    
    public void setMedbill_claimant_num(String medbill_claimant_num) {
        this.medbill_claimant_num = medbill_claimant_num;
    }
    public String getOrgination_department() {
        return this.orgination_department;
    }
    
    public void setOrgination_department(String orgination_department) {
        this.orgination_department = orgination_department;
    }
    public String getExt_ref_number() {
        return this.ext_ref_number;
    }
    
    public void setExt_ref_number(String ext_ref_number) {
        this.ext_ref_number = ext_ref_number;
    }
    public String getDocument_status() {
        return this.document_status;
    }
    
    public void setDocument_status(String document_status) {
        this.document_status = document_status;
    }
    public String getProvider_number() {
        return this.provider_number;
    }
    
    public void setProvider_number(String provider_number) {
        this.provider_number = provider_number;
    }
    public String getProvider_name() {
        return this.provider_name;
    }
    
    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }
    public Date getReceipt_date() {
        return this.receipt_date;
    }
    
    public void setReceipt_date(Date receipt_date) {
        this.receipt_date = receipt_date;
    }
    public String getDocument_type() {
        return this.document_type;
    }
    
    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }
    public String getExternal_system_id() {
        return this.external_system_id;
    }
    
    public void setExternal_system_id(String external_system_id) {
        this.external_system_id = external_system_id;
    }
    public String getMail_identifier() {
        return this.mail_identifier;
    }
    
    public void setMail_identifier(String mail_identifier) {
        this.mail_identifier = mail_identifier;
    }
    public Date getPolicy_term_exp_date() {
        return this.policy_term_exp_date;
    }
    
    public void setPolicy_term_exp_date(Date policy_term_exp_date) {
        this.policy_term_exp_date = policy_term_exp_date;
    }
    public String getLoss_state_code() {
        return this.loss_state_code;
    }
    
    public void setLoss_state_code(String loss_state_code) {
        this.loss_state_code = loss_state_code;
    }
    public String getClaim_status() {
        return this.claim_status;
    }
    
    public void setClaim_status(String claim_status) {
        this.claim_status = claim_status;
    }
    public Date getPolicy_term_eff_date() {
        return this.policy_term_eff_date;
    }
    
    public void setPolicy_term_eff_date(Date policy_term_eff_date) {
        this.policy_term_eff_date = policy_term_eff_date;
    }
    public String getSiu_indicator() {
        return this.siu_indicator;
    }
    
    public void setSiu_indicator(String siu_indicator) {
        this.siu_indicator = siu_indicator;
    }
    public String getFile_format_cd() {
        return this.file_format_cd;
    }
    
    public void setFile_format_cd(String file_format_cd) {
        this.file_format_cd = file_format_cd;
    }
    public Date getLoss_date() {
        return this.loss_date;
    }
    
    public void setLoss_date(Date loss_date) {
        this.loss_date = loss_date;
    }
    public String getPolicy_number() {
        return this.policy_number;
    }
    
    public void setPolicy_number(String policy_number) {
        this.policy_number = policy_number;
    }
    public String getRestricted_claim_ind() {
        return this.restricted_claim_ind;
    }
    
    public void setRestricted_claim_ind(String restricted_claim_ind) {
        this.restricted_claim_ind = restricted_claim_ind;
    }
    public String getClaims_region_cd() {
        return this.claims_region_cd;
    }
    
    public void setClaims_region_cd(String claims_region_cd) {
        this.claims_region_cd = claims_region_cd;
    }
    public String getPolicy_state_code() {
        return this.policy_state_code;
    }
    
    public void setPolicy_state_code(String policy_state_code) {
        this.policy_state_code = policy_state_code;
    }
    public Date getDate_closed() {
        return this.date_closed;
    }
    
    public void setDate_closed(Date date_closed) {
        this.date_closed = date_closed;
    }
    public String getRestricted_indicator() {
        return this.restricted_indicator;
    }
    
    public void setRestricted_indicator(String restricted_indicator) {
        this.restricted_indicator = restricted_indicator;
    }
    public String getClaim_number() {
        return this.claim_number;
    }
    
    public void setClaim_number(String claim_number) {
        this.claim_number = claim_number;
    }
    public String getIrs_name() {
        return this.irs_name;
    }
    
    public void setIrs_name(String irs_name) {
        this.irs_name = irs_name;
    }
    public int getIrs_number() {
        return this.irs_number;
    }
    
    public void setIrs_number(int irs_number) {
        this.irs_number = irs_number;
    }
    public String getForm_claimant_name() {
        return this.form_claimant_name;
    }
    
    public void setForm_claimant_name(String form_claimant_name) {
        this.form_claimant_name = form_claimant_name;
    }
    public String getForm_claimant_type_cd() {
        return this.form_claimant_type_cd;
    }
    
    public void setForm_claimant_type_cd(String form_claimant_type_cd) {
        this.form_claimant_type_cd = form_claimant_type_cd;
    }
    public int getForm_claimant_number() {
        return this.form_claimant_number;
    }
    
    public void setForm_claimant_number(int form_claimant_number) {
        this.form_claimant_number = form_claimant_number;
    }
    public String getDp_type() {
        return this.dp_type;
    }
    
    public void setDp_type(String dp_type) {
        this.dp_type = dp_type;
    }
    public String getDs_interviewer() {
        return this.ds_interviewer;
    }
    
    public void setDs_interviewer(String ds_interviewer) {
        this.ds_interviewer = ds_interviewer;
    }
    public String getDs_party() {
        return this.ds_party;
    }
    
    public void setDs_party(String ds_party) {
        this.ds_party = ds_party;
    }
    public Date getScan_date() {
        return this.scan_date;
    }
    
    public void setScan_date(Date scan_date) {
        this.scan_date = scan_date;
    }
    public String getDocument_source() {
        return this.document_source;
    }
    
    public void setDocument_source(String document_source) {
        this.document_source = document_source;
    }
    public boolean isInfant_case() {
        return this.infant_case;
    }
    
    public void setInfant_case(boolean infant_case) {
        this.infant_case = infant_case;
    }
    public String getForm_benefits_state() {
        return this.form_benefits_state;
    }
    
    public void setForm_benefits_state(String form_benefits_state) {
        this.form_benefits_state = form_benefits_state;
    }
    public String getFax_number() {
        return this.fax_number;
    }
    
    public void setFax_number(String fax_number) {
        this.fax_number = fax_number;
    }
    public String getDs_role() {
        return this.ds_role;
    }
    
    public void setDs_role(String ds_role) {
        this.ds_role = ds_role;
    }
    public Date getDs_date() {
        return this.ds_date;
    }
    
    public void setDs_date(Date ds_date) {
        this.ds_date = ds_date;
    }
    public Date getDp_date() {
        return this.dp_date;
    }
    
    public void setDp_date(Date dp_date) {
        this.dp_date = dp_date;
    }
    public Date getSigi_null_date() {
        return this.sigi_null_date;
    }
    
    public void setSigi_null_date(Date sigi_null_date) {
        this.sigi_null_date = sigi_null_date;
    }
    public String getParty_id() {
        return this.party_id;
    }
    
    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }
    public String getArbitration_caption_id() {
        return this.arbitration_caption_id;
    }
    
    public void setArbitration_caption_id(String arbitration_caption_id) {
        this.arbitration_caption_id = arbitration_caption_id;
    }
    public String getArbitration_caption_name() {
        return this.arbitration_caption_name;
    }
    
    public void setArbitration_caption_name(String arbitration_caption_name) {
        this.arbitration_caption_name = arbitration_caption_name;
    }
    public String getInsured_name() {
        return this.insured_name;
    }
    
    public void setInsured_name(String insured_name) {
        this.insured_name = insured_name;
    }
    public String getAdjuster_name() {
        return this.adjuster_name;
    }
    
    public void setAdjuster_name(String adjuster_name) {
        this.adjuster_name = adjuster_name;
    }
    public String getForm_number() {
        return this.form_number;
    }
    
    public void setForm_number(String form_number) {
        this.form_number = form_number;
    }
    public String getBill_id() {
        return this.bill_id;
    }
    
    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }
    public boolean isHas_updates() {
        return this.has_updates;
    }
    
    public void setHas_updates(boolean has_updates) {
        this.has_updates = has_updates;
    }
    public String getOccurrence_code() {
        return this.occurrence_code;
    }
    
    public void setOccurrence_code(String occurrence_code) {
        this.occurrence_code = occurrence_code;
    }
    public String getSub_document_type() {
        return this.sub_document_type;
    }
    
    public void setSub_document_type(String sub_document_type) {
        this.sub_document_type = sub_document_type;
    }
    public String getCompensability_indicator() {
        return this.compensability_indicator;
    }
    
    public void setCompensability_indicator(String compensability_indicator) {
        this.compensability_indicator = compensability_indicator;
    }
    public String getClaim_type() {
        return this.claim_type;
    }
    
    public void setClaim_type(String claim_type) {
        this.claim_type = claim_type;
    }
    public String getProject_matter() {
        return this.project_matter;
    }
    
    public void setProject_matter(String project_matter) {
        this.project_matter = project_matter;
    }
    public int getTaxonomy_indicator() {
        return this.taxonomy_indicator;
    }
    
    public void setTaxonomy_indicator(int taxonomy_indicator) {
        this.taxonomy_indicator = taxonomy_indicator;
    }
    public boolean isIs_duplicate() {
        return this.is_duplicate;
    }
    
    public void setIs_duplicate(boolean is_duplicate) {
        this.is_duplicate = is_duplicate;
    }
    public boolean isIs_junk() {
        return this.is_junk;
    }
    
    public void setIs_junk(boolean is_junk) {
        this.is_junk = is_junk;
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


