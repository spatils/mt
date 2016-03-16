package com.managetransfer.dynamiccode.keyhandling;
import java.util.Date;
import java.util.HashMap;
import com.managetransfer.businessobject.*;
public class KeyHandling {
public HashMap<String,String> ohmString = new HashMap() ; 
public HashMap<String,Integer> ohmInteger = new HashMap() ; 
public HashMap<String,Date> ohmDate = new HashMap() ; 
public HashMap<String,Long> ohmLong = new HashMap() ; 
public Object instantiateObject( String className ,	HashMap<String, String> hmString, HashMap<String,Integer> hmInteger,HashMap<String,Date> hmDate,HashMap<String,Long> hmLong){if (className.equals("TermLetterCustomer")){ 
 TermLetterCustomer TermLetterCustomerObject = new TermLetterCustomer(); 
 TermLetterCustomerObject.setObjectId(hmString.get("r_object_id")); 
 return TermLetterCustomerObject;
}if (className.equals("TermLetterCustomerSP")){ 
 TermLetterCustomerSP TermLetterCustomerSPObject = new TermLetterCustomerSP(); 
 TermLetterCustomerSPObject.setObjectId(hmString.get("r_object_id")); 
 return TermLetterCustomerSPObject;
} return null;
}public void extractKey(Object object,String className){ 
if (className.equals("TermLetterCustomer")){ 
 TermLetterCustomer TermLetterCustomerObject =  (TermLetterCustomer) object; 
 ohmString.put("r_object_id",TermLetterCustomerObject.getObjectId()); 
}if (className.equals("TermLetterCustomerSP")){ 
 TermLetterCustomerSP TermLetterCustomerSPObject =  (TermLetterCustomerSP) object; 
 ohmString.put("r_object_id",TermLetterCustomerSPObject.getObjectId()); 
}
}
}