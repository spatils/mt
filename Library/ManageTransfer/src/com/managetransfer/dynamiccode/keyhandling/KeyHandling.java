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
}if (className.equals("Information")){ 
 Information InformationObject = new Information(); 
 InformationObject.setObjectId(hmString.get("r_object_id")); 
 return InformationObject;
} return null;
}public void extractKey(Object object,String className){ 
if (className.equals("Claims")){ 
 Claims ClaimsObject =  (Claims) object; 
 ohmString.put("r_object_id",ClaimsObject.getObjectId()); 
}if (className.equals("Policy")){ 
 Policy PolicyObject =  (Policy) object; 
 ohmString.put("r_object_id",PolicyObject.getObjectId()); 
}if (className.equals("Information")){ 
 Information InformationObject =  (Information) object; 
 ohmString.put("r_object_id",InformationObject.getObjectId()); 
}if (className.equals("Information")){ 
 Information InformationObject =  (Information) object; 
 ohmString.put("r_object_id",InformationObject.getObjectId()); 
}
}
}