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
 public void setSourceObjects(Object[] objects){  
 for(int i=0;i< objects.length;i++){  
 sourceObject.add( objects[i]);  
 }  
 } 
 public String getAdjuster(String val0 ){
		return  val0+"T";
	} 
 public String getFolder(String val0){
		return val0+"F";
	}
 public void executeTransformation(){
 
  if(transformationName.equals("newmap")){ 
 for(int i=0 ; i < getSourceObject().size(); i++ ) { 
 if( getSourceObject().get(i).equals("com.managetransfer.businessobject.Claims")) { 

 ClaimsD7 ClaimsD7Object = new ClaimsD7();
 Claims ClaimsObject = ( Claims) getSourceObject().get(i) ; 
ClaimsD7Object.setFolderPath(getFolder(ClaimsObject.getFolderPath()));
ClaimsD7Object.setObjectId(ClaimsObject.getObjectId());

 getTargetobject().add(ClaimsD7Object);
 }
 if( getSourceObject().get(i).equals("com.managetransfer.businessobject.ClaimsR")) { 

 ClaimsRD7 ClaimsRD7Object = new ClaimsRD7();
 ClaimsR ClaimsRObject = ( ClaimsR) getSourceObject().get(i) ; 
ClaimsRD7Object.setAdjusterId(getAdjuster(ClaimsRObject.getAdjusterId()));
ClaimsRD7Object.setMtIndexId(ClaimsRObject.getMtIndexId());
ClaimsRD7Object.setObjectId(ClaimsRObject.getObjectId());

 getTargetobject().add(ClaimsRD7Object);
 }
 } 
 }
}
}