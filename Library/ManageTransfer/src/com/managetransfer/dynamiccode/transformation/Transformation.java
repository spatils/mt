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
 public String getRepoPath(String val0){
  return val0+"done";
}
 public void executeTransformation(){
 
  if(transformationName.equals("TLCTransformation")){ 
 for(int i=0 ; i < getSourceObject().size(); i++ ) { 
 if( getSourceObject().get(i).getClass().toString().equals("class com.managetransfer.businessobject.TermLetterCustomer")) { 

 TermLetterCustomerSP TermLetterCustomerSPObject = new TermLetterCustomerSP();
 TermLetterCustomer TermLetterCustomerObject = ( TermLetterCustomer) getSourceObject().get(i) ; 
TermLetterCustomerSPObject.setDocCategory(TermLetterCustomerObject.getDocCategory());
TermLetterCustomerSPObject.setDocDate(TermLetterCustomerObject.getDocDate());
TermLetterCustomerSPObject.setIsActive(TermLetterCustomerObject.isIsActive());
TermLetterCustomerSPObject.setObjectName(TermLetterCustomerObject.getObjectName());
TermLetterCustomerSPObject.setRefNum(TermLetterCustomerObject.getRefNum());

 getTargetobject().add(TermLetterCustomerSPObject);
 }
 } 
 }
}
}