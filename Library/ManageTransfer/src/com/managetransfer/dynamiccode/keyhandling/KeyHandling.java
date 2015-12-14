package com.managetransfer.dynamiccode.keyhandling;
import java.util.Date;
import java.util.HashMap;
import com.managetransfer.businessobject.*;
public class KeyHandling {
public HashMap<String,String> ohmString = new HashMap() ; 
public HashMap<String,Integer> ohmInteger = new HashMap() ; 
public HashMap<String,Date> ohmDate = new HashMap() ; 
public HashMap<String,Long> ohmLong = new HashMap() ; 
public Object instantiateObject( String className ,	HashMap<String, String> hmString, HashMap<String,Integer> hmInteger,HashMap<String,Date> hmDate,HashMap<String,Long> hmLong){if (className.equals("Claims")){ 
 Claims ClaimsObject = new Claims(); 
 ClaimsObject.setObjectId(hmString.get("r_object_id")); 
 return ClaimsObject;
}if (className.equals("Policy")){ 
 Policy PolicyObject = new Policy(); 
 PolicyObject.setObjectId(hmString.get("r_object_id")); 
 return PolicyObject;
}if (className.equals("ClaimsD7")){ 
 ClaimsD7 ClaimsD7Object = new ClaimsD7(); 
 ClaimsD7Object.setObjectId(hmString.get("r_object_id")); 
 return ClaimsD7Object;
}if (className.equals("ClaimsR")){ 
 ClaimsR ClaimsRObject = new ClaimsR(); 
 ClaimsRObject.setObjectId(hmString.get("r_object_id"));
ClaimsRObject.setMtIndexId(hmInteger.get("mt_index_id"));
 
 return ClaimsRObject;
}if (className.equals("ClaimsRD7")){ 
 ClaimsRD7 ClaimsRD7Object = new ClaimsRD7(); 
 ClaimsRD7Object.setObjectId(hmString.get("r_object_id"));
ClaimsRD7Object.setMtIndexId(hmInteger.get("mt_index_id"));
 
 return ClaimsRD7Object;
}if (className.equals("ClaimsMsg")){ 
 ClaimsMsg ClaimsMsgObject = new ClaimsMsg(); 
 ClaimsMsgObject.setObjectId(hmString.get("r_object_id")); 
 return ClaimsMsgObject;
}if (className.equals("ClaimsMsgR")){ 
 ClaimsMsgR ClaimsMsgRObject = new ClaimsMsgR(); 
 ClaimsMsgRObject.setObjectId(hmString.get("r_object_id"));
ClaimsMsgRObject.setMtIndexId(hmInteger.get("mt_index_id"));
 
 return ClaimsMsgRObject;
}if (className.equals("ProcessVariable3")){ 
 ProcessVariable3 ProcessVariable3Object = new ProcessVariable3(); 
 ProcessVariable3Object.setTaskId(hmString.get("task_id")); 
 return ProcessVariable3Object;
}if (className.equals("TaxDocument")){ 
 TaxDocument TaxDocumentObject = new TaxDocument(); 
 TaxDocumentObject.setObjectId(hmString.get("r_object_id")); 
 return TaxDocumentObject;
}if (className.equals("TaxDocumentR")){ 
 TaxDocumentR TaxDocumentRObject = new TaxDocumentR(); 
 TaxDocumentRObject.setObjectId(hmString.get("r_object_id"));
TaxDocumentRObject.setMtIndexId(hmInteger.get("mt_index_id"));
 
 return TaxDocumentRObject;
}if (className.equals("TaxDocumentRD7")){ 
 TaxDocumentRD7 TaxDocumentRD7Object = new TaxDocumentRD7(); 
 TaxDocumentRD7Object.setObjectId(hmString.get("r_object_id"));
TaxDocumentRD7Object.setMtIndexId(hmInteger.get("mt_index_id"));
 
 return TaxDocumentRD7Object;
}if (className.equals("TaxDocumentD7")){ 
 TaxDocumentD7 TaxDocumentD7Object = new TaxDocumentD7(); 
 TaxDocumentD7Object.setObjectId(hmString.get("r_object_id")); 
 return TaxDocumentD7Object;
}if (className.equals("sigi_sdt_claims")){ 
 sigi_sdt_claims sigi_sdt_claimsObject = new sigi_sdt_claims(); 
 sigi_sdt_claimsObject.setTaskId(hmString.get("task_id")); 
 return sigi_sdt_claimsObject;
}if (className.equals("ProcessVariable1")){ 
 ProcessVariable1 ProcessVariable1Object = new ProcessVariable1(); 
 ProcessVariable1Object.setTaskId(hmString.get("task_id")); 
 return ProcessVariable1Object;
}if (className.equals("ProcessVariable2")){ 
 ProcessVariable2 ProcessVariable2Object = new ProcessVariable2(); 
 ProcessVariable2Object.setTaskId(hmString.get("task_id")); 
 return ProcessVariable2Object;
}if (className.equals("sigi_sdt_selective")){ 
 sigi_sdt_selective sigi_sdt_selectiveObject = new sigi_sdt_selective(); 
 sigi_sdt_selectiveObject.setTaskId(hmString.get("task_id")); 
 return sigi_sdt_selectiveObject;
} return null;
}public void extractKey(Object object,String className){ 
if (className.equals("Claims")){ 
 Claims ClaimsObject =  (Claims) object; 
 ohmString.put("r_object_id",ClaimsObject.getObjectId()); 
}if (className.equals("Policy")){ 
 Policy PolicyObject =  (Policy) object; 
 ohmString.put("r_object_id",PolicyObject.getObjectId()); 
}if (className.equals("ClaimsD7")){ 
 ClaimsD7 ClaimsD7Object =  (ClaimsD7) object; 
 ohmString.put("r_object_id",ClaimsD7Object.getObjectId()); 
}if (className.equals("ClaimsR")){ 
 ClaimsR ClaimsRObject =  (ClaimsR) object; 
 ohmString.put("r_object_id",ClaimsRObject.getObjectId());
ohmInteger.put("mt_index_id",ClaimsRObject.getMtIndexId());
 
}if (className.equals("ClaimsRD7")){ 
 ClaimsRD7 ClaimsRD7Object =  (ClaimsRD7) object; 
 ohmString.put("r_object_id",ClaimsRD7Object.getObjectId());
ohmInteger.put("mt_index_id",ClaimsRD7Object.getMtIndexId());
 
}if (className.equals("ClaimsMsg")){ 
 ClaimsMsg ClaimsMsgObject =  (ClaimsMsg) object; 
 ohmString.put("r_object_id",ClaimsMsgObject.getObjectId()); 
}if (className.equals("ClaimsMsgR")){ 
 ClaimsMsgR ClaimsMsgRObject =  (ClaimsMsgR) object; 
 ohmString.put("r_object_id",ClaimsMsgRObject.getObjectId());
ohmInteger.put("mt_index_id",ClaimsMsgRObject.getMtIndexId());
 
}if (className.equals("ProcessVariable3")){ 
 ProcessVariable3 ProcessVariable3Object =  (ProcessVariable3) object; 
 ohmString.put("task_id",ProcessVariable3Object.getTaskId()); 
}if (className.equals("TaxDocument")){ 
 TaxDocument TaxDocumentObject =  (TaxDocument) object; 
 ohmString.put("r_object_id",TaxDocumentObject.getObjectId()); 
}if (className.equals("TaxDocumentR")){ 
 TaxDocumentR TaxDocumentRObject =  (TaxDocumentR) object; 
 ohmString.put("r_object_id",TaxDocumentRObject.getObjectId());
ohmInteger.put("mt_index_id",TaxDocumentRObject.getMtIndexId());
 
}if (className.equals("TaxDocumentRD7")){ 
 TaxDocumentRD7 TaxDocumentRD7Object =  (TaxDocumentRD7) object; 
 ohmString.put("r_object_id",TaxDocumentRD7Object.getObjectId());
ohmInteger.put("mt_index_id",TaxDocumentRD7Object.getMtIndexId());
 
}if (className.equals("TaxDocumentD7")){ 
 TaxDocumentD7 TaxDocumentD7Object =  (TaxDocumentD7) object; 
 ohmString.put("r_object_id",TaxDocumentD7Object.getObjectId()); 
}if (className.equals("sigi_sdt_claims")){ 
 sigi_sdt_claims sigi_sdt_claimsObject =  (sigi_sdt_claims) object; 
 ohmString.put("task_id",sigi_sdt_claimsObject.getTaskId()); 
}if (className.equals("ProcessVariable1")){ 
 ProcessVariable1 ProcessVariable1Object =  (ProcessVariable1) object; 
 ohmString.put("task_id",ProcessVariable1Object.getTaskId()); 
}if (className.equals("ProcessVariable2")){ 
 ProcessVariable2 ProcessVariable2Object =  (ProcessVariable2) object; 
 ohmString.put("task_id",ProcessVariable2Object.getTaskId()); 
}if (className.equals("sigi_sdt_selective")){ 
 sigi_sdt_selective sigi_sdt_selectiveObject =  (sigi_sdt_selective) object; 
 ohmString.put("task_id",sigi_sdt_selectiveObject.getTaskId()); 
}
}
}