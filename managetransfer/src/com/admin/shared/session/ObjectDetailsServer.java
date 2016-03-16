package com.admin.shared.session;

import java.util.ArrayList;
import java.util.List;
import com.managetransfer.client.ObjectDetails;
import com.managetransfer.hibernate.HibernateConnection;


public class ObjectDetailsServer {
	List<ObjectDetails> objectDetailsResult = null;
	ArrayList<ObjectDetails> arrayObjectDetails = new ArrayList<ObjectDetails>();
	ArrayList<String> sourceObjectList = new ArrayList<String>();
	ArrayList<String> targetObjectList = new ArrayList<String>();
	HibernateConnection hc = new HibernateConnection();
    	 
	public ObjectDetailsServer(){
		
	}
	public ArrayList<ObjectDetails> getObjectResult(String input){
		return arrayObjectDetails;
			
	}
	public void setHc(HibernateConnection hc) {
		this.hc = hc;
		List list = hc.getObject("from ObjectDetails ");
		objectDetailsResult = arrayObjectDetails;
		for (int i=0 ; i < list.size();i++){
			ObjectDetails objectDetails =(ObjectDetails)list.get(i);
			objectDetailsResult.add(objectDetails);
			if(objectDetails.getObjectType().equals("Source")){
				sourceObjectList.add(objectDetails.getObjectName());
			}else if(objectDetails.getObjectType().equals("Target")){
				targetObjectList.add(objectDetails.getObjectName());
			}
		} 
	}
	public ArrayList<String> getSourceObjectList() {
		return sourceObjectList;
	}
	
	public ArrayList<String> getTargetObjectList() {
		return targetObjectList;
	}

}
