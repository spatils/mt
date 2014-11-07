package com.managetransfer.record;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
public class Transformation {
ArrayList<Object> sourceObject = new ArrayList<>();   
private Object targetObject = null;  ; 
private String transformationName = new String (""); 
  
 public Object getTargetobject() { 
 	return targetObject; 
 } 
 public void setTargetObject(Object targetObject) { 
 this.targetObject = targetObject;  
 } 
 public String getTransformationName() { 
 return transformationName; 
 } 
 public void setTransformationName(String transformationName) { 
 this.transformationName = transformationName; 
 }   
 public void setSourceObjects(Object[] objects){  
 for(int i=0;i< objects.length;i++){  
 sourceObject.add( objects[i]);  
 }  
 } 
 public String exp1(String var1) {
 
      return var1+"added value" ;
 
 } 
 public String folderPath (String val1 ){
		String folderPath = val1 ;
		if(folderPath.startsWith("/") ) {
			folderPath = folderPath.substring(1,folderPath.length());
		}
		if(folderPath.endsWith("/") ) {
			folderPath = folderPath.substring(0,folderPath.length()-1);
		}
		 return folderPath;
		} 
 public String objectName (String val1){
		String objectName = new String("");
		String fileSeparator = new String("/");
		if (val1.contains("\\")) { 
			fileSeparator = ("\\");
		}
		objectName = val1.substring(val1.lastIndexOf(fileSeparator)+1);
		return objectName;
	}public void executeTransformation(){
if(transformationName.equals("Claim2Information")){
Information InformationObject = new Information(); 
Claims ClaimsObject = (Claims) sourceObject.get( 0); 
InformationObject.setDepartmentType(exp1(ClaimsObject.getClaimType()));
InformationObject.setDocumentState(ClaimsObject.getDocumentState());
InformationObject.setFolderPath(ClaimsObject.getFolderPath());
InformationObject.setInsuredName(ClaimsObject.getClaimantName());
InformationObject.setMtRepositoryPath(folderPath(ClaimsObject.getMtRepositoryPath()));
InformationObject.setObjectId(ClaimsObject.getObjectId());
InformationObject.setObjectName(objectName(ClaimsObject.getFolderPath()));
InformationObject.setOwnerName(ClaimsObject.getOwnerName());
InformationObject.setPolicyNumber(ClaimsObject.getClaimNumber());
 setTargetObject(InformationObject); 
 
 }}

}