 package com.managetransfer.dynamiccode.transformation; 
 import java.util.Date; import java.util.HashMap ; import java.util.ArrayList; import com.managetransfer.businessobject.*;
 public class Transformation { 
 ArrayList<Object> sourceObject = new ArrayList<>();   
ArrayList<Object> targetObject = new ArrayList<>();  
private String transformationName = new String (""); 
  
 public ArrayList<Object> getTargetobject() { 
 	return targetObject; 
 } 
 public ArrayList<Object> getSourceObject(){  
 return sourceObject;  
 }  
 public void setTargetObject(ArrayList<Object> targetObject) { 
 this.targetObject = targetObject;  
 } 
 public String getTransformationName() { 
 return transformationName; 
 } 
 public void setTransformationName(String transformationName) { 
 this.transformationName = transformationName; 
 }   
 public void setSourceObjects(ArrayList<Object> sourceObjectMethod){  
 sourceObject=  sourceObjectMethod ; 
 } 
 public String getAdjuster(String val0 ){
		return  val0+"T";
	} 
 public String getFolder(String val0){
		return val0+"F";
	}
 public void executeTransformation(){
 
  if(transformationName.equals("ClaimsMapping")){ 
 for(int i=0 ; i < getSourceObject().size(); i++ ) { 
 if( getSourceObject().get(i).getClass().toString().equals("class com.managetransfer.businessobject.Claims")) { 

 ClaimsD7 ClaimsD7Object = new ClaimsD7();
 Claims ClaimsObject = ( Claims) getSourceObject().get(i) ; 
ClaimsD7Object.setAclName(ClaimsObject.getAclName());
ClaimsD7Object.setAdjuster_name(ClaimsObject.getAdjuster_name());
ClaimsD7Object.setArbitration_caption_id(ClaimsObject.getArbitration_caption_id());
ClaimsD7Object.setArbitration_caption_name(ClaimsObject.getArbitration_caption_name());
ClaimsD7Object.setBatch_control_number(ClaimsObject.getBatch_control_number());
ClaimsD7Object.setBill_id(ClaimsObject.getBill_id());
ClaimsD7Object.setClaim_number(ClaimsObject.getClaim_number());
ClaimsD7Object.setClaim_status(ClaimsObject.getClaim_status());
ClaimsD7Object.setClaim_type(ClaimsObject.getClaim_type());
ClaimsD7Object.setClaims_region_cd(ClaimsObject.getClaims_region_cd());
ClaimsD7Object.setCompensability_indicator(ClaimsObject.getCompensability_indicator());
ClaimsD7Object.setDate_closed(ClaimsObject.getDate_closed());
ClaimsD7Object.setDocument_control_number(ClaimsObject.getDocument_control_number());
ClaimsD7Object.setDocument_source(ClaimsObject.getDocument_source());
ClaimsD7Object.setDocument_status(ClaimsObject.getDocument_status());
ClaimsD7Object.setDocument_type(ClaimsObject.getDocument_type());
ClaimsD7Object.setDp_date(ClaimsObject.getDp_date());
ClaimsD7Object.setDp_type(ClaimsObject.getDp_type());
ClaimsD7Object.setDs_date(ClaimsObject.getDs_date());
ClaimsD7Object.setDs_interviewer(ClaimsObject.getDs_interviewer());
ClaimsD7Object.setDs_party(ClaimsObject.getDs_party());
ClaimsD7Object.setDs_role(ClaimsObject.getDs_role());
ClaimsD7Object.setExt_ref_number(ClaimsObject.getExt_ref_number());
ClaimsD7Object.setExternal_system_id(ClaimsObject.getExternal_system_id());
ClaimsD7Object.setFax_number(ClaimsObject.getFax_number());
ClaimsD7Object.setFile_format_cd(ClaimsObject.getFile_format_cd());
ClaimsD7Object.setFolderPath(ClaimsObject.getFolderPath());
ClaimsD7Object.setForm_benefits_state(ClaimsObject.getForm_benefits_state());
ClaimsD7Object.setForm_claimant_name(ClaimsObject.getForm_claimant_name());
ClaimsD7Object.setForm_claimant_number(ClaimsObject.getForm_claimant_number());
ClaimsD7Object.setForm_claimant_type_cd(ClaimsObject.getForm_claimant_type_cd());
ClaimsD7Object.setForm_number(ClaimsObject.getForm_number());
ClaimsD7Object.setHas_updates(ClaimsObject.isHas_updates());
ClaimsD7Object.setInfant_case(ClaimsObject.isInfant_case());
ClaimsD7Object.setInsured_name(ClaimsObject.getInsured_name());
ClaimsD7Object.setIrs_name(ClaimsObject.getIrs_name());
ClaimsD7Object.setIrs_number(ClaimsObject.getIrs_number());
ClaimsD7Object.setIs_duplicate(ClaimsObject.isIs_duplicate());
ClaimsD7Object.setIs_junk(ClaimsObject.isIs_junk());
ClaimsD7Object.setLoss_date(ClaimsObject.getLoss_date());
ClaimsD7Object.setLoss_state_code(ClaimsObject.getLoss_state_code());
ClaimsD7Object.setMail_identifier(ClaimsObject.getMail_identifier());
ClaimsD7Object.setMedbill_claimant_num(ClaimsObject.getMedbill_claimant_num());
ClaimsD7Object.setMtRepositoryPath(ClaimsObject.getMtRepositoryPath());
ClaimsD7Object.setObjectId(ClaimsObject.getObjectId());
ClaimsD7Object.setObjectName(ClaimsObject.getObjectName());
ClaimsD7Object.setOccurrence_code(ClaimsObject.getOccurrence_code());
ClaimsD7Object.setOrgination_department(ClaimsObject.getOrgination_department());
ClaimsD7Object.setOwnerName(ClaimsObject.getOwnerName());
ClaimsD7Object.setParty_id(ClaimsObject.getParty_id());
ClaimsD7Object.setPolicy_number(ClaimsObject.getPolicy_number());
ClaimsD7Object.setPolicy_state_code(ClaimsObject.getPolicy_state_code());
ClaimsD7Object.setPolicy_term_eff_date(ClaimsObject.getPolicy_term_eff_date());
ClaimsD7Object.setPolicy_term_exp_date(ClaimsObject.getPolicy_term_exp_date());
ClaimsD7Object.setProject_matter(ClaimsObject.getProject_matter());
ClaimsD7Object.setProvider_name(ClaimsObject.getProvider_name());
ClaimsD7Object.setProvider_number(ClaimsObject.getProvider_number());
ClaimsD7Object.setReceipt_date(ClaimsObject.getReceipt_date());
ClaimsD7Object.setRestricted_claim_ind(ClaimsObject.getRestricted_claim_ind());
ClaimsD7Object.setRestricted_indicator(ClaimsObject.getRestricted_indicator());
ClaimsD7Object.setScan_date(ClaimsObject.getScan_date());
ClaimsD7Object.setSigi_null_date(ClaimsObject.getSigi_null_date());
ClaimsD7Object.setSiu_indicator(ClaimsObject.getSiu_indicator());
ClaimsD7Object.setSub_document_type(ClaimsObject.getSub_document_type());
ClaimsD7Object.setTaxonomy_indicator(ClaimsObject.getTaxonomy_indicator());

 getTargetobject().add(ClaimsD7Object);
 }
 if( getSourceObject().get(i).getClass().toString().equals("class com.managetransfer.businessobject.ClaimsR")) { 

 ClaimsRD7 ClaimsRD7Object = new ClaimsRD7();
 ClaimsR ClaimsRObject = ( ClaimsR) getSourceObject().get(i) ; 
ClaimsRD7Object.setAdjuster_assigned(ClaimsRObject.getAdjuster_assigned());
ClaimsRD7Object.setAdjuster_id(ClaimsRObject.getAdjuster_id());
ClaimsRD7Object.setAudit_review_type(ClaimsRObject.getAudit_review_type());
ClaimsRD7Object.setCl_arbitration_caption_id(ClaimsRObject.getCl_arbitration_caption_id());
ClaimsRD7Object.setCl_arbitration_caption_name(ClaimsRObject.getCl_arbitration_caption_name());
ClaimsRD7Object.setClaimant_info(ClaimsRObject.getClaimant_info());
ClaimsRD7Object.setClaimant_name(ClaimsRObject.getClaimant_name());
ClaimsRD7Object.setClaimant_number(ClaimsRObject.getClaimant_number());
ClaimsRD7Object.setComments(ClaimsRObject.getComments());
ClaimsRD7Object.setDate_of_birth(ClaimsRObject.getDate_of_birth());
ClaimsRD7Object.setDate_of_birth_r(ClaimsRObject.getDate_of_birth_r());
ClaimsRD7Object.setFirst_service_date(ClaimsRObject.getFirst_service_date());
ClaimsRD7Object.setJcn(ClaimsRObject.getJcn());
ClaimsRD7Object.setJcn_r(ClaimsRObject.getJcn_r());
ClaimsRD7Object.setLast_service_date(ClaimsRObject.getLast_service_date());
ClaimsRD7Object.setLob_r(ClaimsRObject.getLob_r());
ClaimsRD7Object.setMethod_name(ClaimsRObject.getMethod_name());
ClaimsRD7Object.setMtIndexId(ClaimsRObject.getMtIndexId());
ClaimsRD7Object.setObjectId(ClaimsRObject.getObjectId());
ClaimsRD7Object.setReceiving_site_location(ClaimsRObject.getReceiving_site_location());
ClaimsRD7Object.setSsn(ClaimsRObject.getSsn());
ClaimsRD7Object.setSsn_r(ClaimsRObject.getSsn_r());
ClaimsRD7Object.setStructured_settlement(ClaimsRObject.isStructured_settlement());
ClaimsRD7Object.setSupervisor_id(ClaimsRObject.getSupervisor_id());

 getTargetobject().add(ClaimsRD7Object);
 }
 } 
 }
}
}