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
 public String getFilePath(String val0 ){
		String newFilePath = val0.substring(0,val0.indexOf("&") );
		newFilePath =newFilePath.replace( "path=","") ;
		if(val0.contains("filestore_01")){
			// location object storage_01 mount point /ecm02 Shared Drive W:\
			newFilePath ="W:\\content_storage_01\\"+newFilePath  ;
		}else if(val0.contains("share_filestore_02")){
			// location object storage_02 mount point /ecm03 Shared Drive V:\
			newFilePath ="V:\\"+newFilePath  ;
		}else if(val0.contains("share_filestore_03")){
			// location object storage_03 mount point /ecm04 Shared Drive U:\
			newFilePath ="U:\\"+newFilePath  ;
		}else if(val0.contains("share_filestore_04")){
			// location object storage_04 mount point /ecm05 Shared Drive T:\
			newFilePath ="T:\\"+newFilePath  ;
		}else if(val0.contains("share_filestore_05")){
			// location object storage_05 mount point /ecm06 Shared Drive S:\
			newFilePath ="S:\\"+newFilePath  ;
		}
		return newFilePath;
	}
 public void executeTransformation(){
 
  if(transformationName.equals("ClaimsMsgMapping")){ 
 for(int i=0 ; i < getSourceObject().size(); i++ ) { 
 if( getSourceObject().get(i).getClass().toString().equals("class com.managetransfer.businessobject.ClaimsMsg")) { 

 ClaimsD7 ClaimsD7Object = new ClaimsD7();
 ClaimsMsg ClaimsMsgObject = ( ClaimsMsg) getSourceObject().get(i) ; 
ClaimsD7Object.setAclName(ClaimsMsgObject.getAclName());
ClaimsD7Object.setArbitration_caption_id(ClaimsMsgObject.getArbitration_caption_id());
ClaimsD7Object.setArbitration_caption_name(ClaimsMsgObject.getArbitration_caption_name());
ClaimsD7Object.setBatch_control_number(ClaimsMsgObject.getBatch_control_number());
ClaimsD7Object.setClaim_number(ClaimsMsgObject.getClaim_number());
ClaimsD7Object.setClaim_status(ClaimsMsgObject.getClaim_status());
ClaimsD7Object.setClaim_type(ClaimsMsgObject.getClaim_type());
ClaimsD7Object.setClaims_region_cd(ClaimsMsgObject.getClaims_region_cd());
ClaimsD7Object.setCompensability_indicator(ClaimsMsgObject.getCompensability_indicator());
ClaimsD7Object.setDate_closed(ClaimsMsgObject.getDate_closed());
ClaimsD7Object.setDocument_control_number(ClaimsMsgObject.getDocument_control_number());
ClaimsD7Object.setDocument_source(ClaimsMsgObject.getDocument_source());
ClaimsD7Object.setDocument_status(ClaimsMsgObject.getDocument_status());
ClaimsD7Object.setDocument_type(ClaimsMsgObject.getDocument_type());
ClaimsD7Object.setDp_date(ClaimsMsgObject.getDp_date());
ClaimsD7Object.setDp_type(ClaimsMsgObject.getDp_type());
ClaimsD7Object.setDs_date(ClaimsMsgObject.getDs_date());
ClaimsD7Object.setDs_interviewer(ClaimsMsgObject.getDs_interviewer());
ClaimsD7Object.setDs_party(ClaimsMsgObject.getDs_party());
ClaimsD7Object.setDs_role(ClaimsMsgObject.getDs_role());
ClaimsD7Object.setExt_ref_number(ClaimsMsgObject.getExt_ref_number());
ClaimsD7Object.setExternal_system_id(ClaimsMsgObject.getExternal_system_id());
ClaimsD7Object.setFax_number(ClaimsMsgObject.getFax_number());
ClaimsD7Object.setFile_format_cd(ClaimsMsgObject.getFile_format_cd());
ClaimsD7Object.setFolderPath(getFilePath(ClaimsMsgObject.getFolderPath()));
ClaimsD7Object.setForm_benefits_state(ClaimsMsgObject.getForm_benefits_state());
ClaimsD7Object.setForm_claimant_name(ClaimsMsgObject.getForm_claimant_name());
ClaimsD7Object.setForm_claimant_number(ClaimsMsgObject.getForm_claimant_number());
ClaimsD7Object.setForm_claimant_type_cd(ClaimsMsgObject.getForm_claimant_type_cd());
ClaimsD7Object.setForm_number(ClaimsMsgObject.getForm_number());
ClaimsD7Object.setHas_updates(ClaimsMsgObject.isHas_updates());
ClaimsD7Object.setInfant_case(ClaimsMsgObject.isInfant_case());
ClaimsD7Object.setInsured_name(ClaimsMsgObject.getInsured_name());
ClaimsD7Object.setIrs_name(ClaimsMsgObject.getIrs_name());
ClaimsD7Object.setIrs_number(ClaimsMsgObject.getIrs_number());
ClaimsD7Object.setIs_duplicate(ClaimsMsgObject.isIs_duplicate());
ClaimsD7Object.setLoss_date(ClaimsMsgObject.getLoss_date());
ClaimsD7Object.setLoss_state_code(ClaimsMsgObject.getLoss_state_code());
ClaimsD7Object.setMail_identifier(ClaimsMsgObject.getMail_identifier());
ClaimsD7Object.setMedbill_claimant_num(ClaimsMsgObject.getMedbill_claimant_num());
ClaimsD7Object.setMtRepositoryPath(ClaimsMsgObject.getMtRepositoryPath());
ClaimsD7Object.setObjectId(ClaimsMsgObject.getObjectId());
ClaimsD7Object.setObjectName(ClaimsMsgObject.getObjectName());
ClaimsD7Object.setOccurrence_code(ClaimsMsgObject.getOccurrence_code());
ClaimsD7Object.setOrigination_department(ClaimsMsgObject.getOrgination_department());
ClaimsD7Object.setOwnerName(ClaimsMsgObject.getOwnerName());
ClaimsD7Object.setParty_id(ClaimsMsgObject.getParty_id());
ClaimsD7Object.setPolicy_number(ClaimsMsgObject.getPolicy_number());
ClaimsD7Object.setPolicy_state_code(ClaimsMsgObject.getPolicy_state_code());
ClaimsD7Object.setPolicy_term_eff_date(ClaimsMsgObject.getPolicy_term_eff_date());
ClaimsD7Object.setPolicy_term_exp_date(ClaimsMsgObject.getPolicy_term_exp_date());
ClaimsD7Object.setProject_matter(ClaimsMsgObject.getProject_matter());
ClaimsD7Object.setProvider_name(ClaimsMsgObject.getProvider_name());
ClaimsD7Object.setProvider_number(ClaimsMsgObject.getProvider_number());
ClaimsD7Object.setReceipt_date(ClaimsMsgObject.getReceipt_date());
ClaimsD7Object.setRestricted_claim_ind(ClaimsMsgObject.getRestricted_claim_ind());
ClaimsD7Object.setRestricted_indicator(ClaimsMsgObject.getRestricted_indicator());
ClaimsD7Object.setScan_date(ClaimsMsgObject.getScan_date());
ClaimsD7Object.setSigi_null_date(ClaimsMsgObject.getSigi_null_date());
ClaimsD7Object.setSiu_indicator(ClaimsMsgObject.getSiu_indicator());
ClaimsD7Object.setSub_document_type(ClaimsMsgObject.getSub_document_type());
ClaimsD7Object.setTaxonomy_indicator(ClaimsMsgObject.getTaxonomy_indicator());

 getTargetobject().add(ClaimsD7Object);
 }
 if( getSourceObject().get(i).getClass().toString().equals("class com.managetransfer.businessobject.ClaimsMsgR")) { 

 ClaimsRD7 ClaimsRD7Object = new ClaimsRD7();
 ClaimsMsgR ClaimsMsgRObject = ( ClaimsMsgR) getSourceObject().get(i) ; 
ClaimsRD7Object.setAdjuster_assigned(ClaimsMsgRObject.getAdjuster_assigned());
ClaimsRD7Object.setAdjuster_id(ClaimsMsgRObject.getAdjuster_id());
ClaimsRD7Object.setAudit_review_type(ClaimsMsgRObject.getAudit_review_type());
ClaimsRD7Object.setCl_arbitration_caption_id(ClaimsMsgRObject.getCl_arbitration_caption_id());
ClaimsRD7Object.setCl_arbitration_caption_name(ClaimsMsgRObject.getCl_arbitration_caption_name());
ClaimsRD7Object.setClaimant_info(ClaimsMsgRObject.getClaimant_info());
ClaimsRD7Object.setClaimant_name(ClaimsMsgRObject.getClaimant_name());
ClaimsRD7Object.setClaimant_number(ClaimsMsgRObject.getClaimant_number());
ClaimsRD7Object.setComments(ClaimsMsgRObject.getComments());
ClaimsRD7Object.setDate_of_birth_r(ClaimsMsgRObject.getDate_of_birth_r());
ClaimsRD7Object.setFirst_service_date(ClaimsMsgRObject.getFirst_service_date());
ClaimsRD7Object.setJcn_r(ClaimsMsgRObject.getJcn_r());
ClaimsRD7Object.setLast_service_date(ClaimsMsgRObject.getLast_service_date());
ClaimsRD7Object.setLob_r(ClaimsMsgRObject.getLob_r());
ClaimsRD7Object.setMethod_name(ClaimsMsgRObject.getMethod_name());
ClaimsRD7Object.setMtIndexId(ClaimsMsgRObject.getMtIndexId());
ClaimsRD7Object.setObjectId(ClaimsMsgRObject.getObjectId());
ClaimsRD7Object.setReceiving_site_location(ClaimsMsgRObject.getReceiving_site_location());
ClaimsRD7Object.setSsn_r(ClaimsMsgRObject.getSsn_r());
ClaimsRD7Object.setStructured_settlement(ClaimsMsgRObject.isStructured_settlement());
ClaimsRD7Object.setSupervisor_id(ClaimsMsgRObject.getSupervisor_id());

 getTargetobject().add(ClaimsRD7Object);
 }
 } 
 } 
  if(transformationName.equals("TaxMapping")){ 
 for(int i=0 ; i < getSourceObject().size(); i++ ) { 
 if( getSourceObject().get(i).getClass().toString().equals("class com.managetransfer.businessobject.TaxDocument")) { 

 TaxDocumentD7 TaxDocumentD7Object = new TaxDocumentD7();
 TaxDocument TaxDocumentObject = ( TaxDocument) getSourceObject().get(i) ; 
TaxDocumentD7Object.setAclName(TaxDocumentObject.getAclName());
TaxDocumentD7Object.setBatch_control_number(TaxDocumentObject.getBatch_control_number());
TaxDocumentD7Object.setDocument_control_number(TaxDocumentObject.getDocument_control_number());
TaxDocumentD7Object.setDocument_source(TaxDocumentObject.getDocument_source());
TaxDocumentD7Object.setDocument_status(TaxDocumentObject.getDocument_status());
TaxDocumentD7Object.setDocument_type(TaxDocumentObject.getDocument_type());
TaxDocumentD7Object.setFolderPath(TaxDocumentObject.getFolderPath());
TaxDocumentD7Object.setForm_number(TaxDocumentObject.getForm_number());
TaxDocumentD7Object.setIrs_name(TaxDocumentObject.getIrs_name());
TaxDocumentD7Object.setIrs_number(TaxDocumentObject.getIrs_number());
TaxDocumentD7Object.setMtRepositoryPath(TaxDocumentObject.getMtRepositoryPath());
TaxDocumentD7Object.setObjectId(TaxDocumentObject.getObjectId());
TaxDocumentD7Object.setObjectName(TaxDocumentObject.getObjectName());
TaxDocumentD7Object.setOwnerName(TaxDocumentObject.getOwnerName());
TaxDocumentD7Object.setReceipt_date(TaxDocumentObject.getReceipt_date());
TaxDocumentD7Object.setReference_number(TaxDocumentObject.getReference_number());
TaxDocumentD7Object.setReference_type(TaxDocumentObject.getReference_type());
TaxDocumentD7Object.setSub_document_type(TaxDocumentObject.getSub_document_type());

 getTargetobject().add(TaxDocumentD7Object);
 }
 } 
 } 
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
ClaimsD7Object.setFolderPath(getFilePath(ClaimsObject.getFolderPath()));
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
ClaimsD7Object.setOrigination_department(ClaimsObject.getOrgination_department());
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