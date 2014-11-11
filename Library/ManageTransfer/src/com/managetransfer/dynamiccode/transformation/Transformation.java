package com.managetransfer.dynamiccode.transformation;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import com.managetransfer.businessobject.*;
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
 }public void executeTransformation(){
}

}