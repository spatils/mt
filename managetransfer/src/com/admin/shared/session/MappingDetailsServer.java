package com.admin.shared.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.managetransfer.client.MappingDetails;
import com.managetransfer.client.MappingDetailsH;
import com.managetransfer.client.MappingDetailsMapH;
import com.managetransfer.client.SequenceDetails;
import com.managetransfer.client.SequenceDetailsH;
import com.managetransfer.client.SequenceDetailsMap;
import com.managetransfer.client.SequenceDetailsMapH;
import com.managetransfer.hibernate.GetRecordDetails;
import com.managetransfer.hibernate.HibernateConnection;


public class MappingDetailsServer {
	List<MappingDetails> mappingResult = null;
	ArrayList<MappingDetails> arrayMapping = new ArrayList<MappingDetails>();
	ArrayList<String> mappingType = new ArrayList<String>();
	ArrayList<String> objectLevelMappingNames = new ArrayList<String>();
	HibernateConnection hc = new HibernateConnection();
	private String packageName = new String ("com.managetransfer.businessobject.");
	final Logger logger = Logger.getLogger(MappingDetailsServer.class.getName()) ;
	public HibernateConnection getHc() {
		return hc;
	}
	public void setHc(HibernateConnection hc) {
		mappingResult = arrayMapping;
		String methodName= new String("MappingDetailsServer.setHc");
		logger.info("Inside"+methodName);
		try {
			this.hc = hc;
			List list = hc.getObject("from MappingDetailsH ");
			for(int i=0;i<list.size();i++){
				mappingResult.add(convertDB2FrontEnd((MappingDetailsH)list.get(i)));
			}
			if (mappingResult.size()==0) {
				ArrayList<String> sourceObjectList = new ArrayList<String>();
				sourceObjectList.add("Claims");
				sourceObjectList.add("Policy");
				mappingResult.add(new MappingDetails("Claim Mapping","Object",sourceObjectList,"Information","Equal"));
				mappingResult.add(new MappingDetails("Policy Mapping","Object",sourceObjectList,"Information","Equal"));
			}
			mappingType.clear();
			mappingType.add("Object");
			mappingType.add("Attribute");
		} catch(Exception e){
			logger.severe("Error"+e);
		}
	    logger.info("Existing"+methodName);
	}
  	public MappingDetailsServer(){
		
		 
	}
	public ArrayList<MappingDetails> getMappingResult(String input){
		ArrayList<MappingDetails> arrayMappingObject = new ArrayList<MappingDetails>();
		for(MappingDetails sr : mappingResult){
			 	arrayMappingObject.add(sr);
		}
		return arrayMappingObject;
			
	}
	public ArrayList<MappingDetails> getMappingResultObject(String input){
		ArrayList<MappingDetails> arrayMappingObject = new ArrayList<MappingDetails>();
		for(MappingDetails sr : mappingResult){
			if(sr.getMappingType().equals("Object")){
				arrayMappingObject.add(sr);
			}
		}
		return arrayMappingObject;
			
	}
	public boolean addMapping(MappingDetails input) {
		String methodName= new String("MappingDetailsServer.setHc");
		logger.info("Inside"+methodName);
		try{
			MappingDetailsH mdh = convertFrontEnd2DB(input);
			hc.startBatchLevelTransaction();
			hc.saveOperation(mdh);
			hc.commitBatchLevelTransaction();
			mappingResult.add(new MappingDetails(input.getMappingName(),input.getMappingType(),input.getSourceObjectList(),input.getTargetObject(),input.getNameOfExpression()));
		}catch(Exception e){
			logger.severe("Error"+e);
			hc.abortBatchLevelTransaction();
		}
		logger.info("Exiting"+methodName);
		return true;
	}
	public ArrayList<MappingDetails> searchMappingResult(String input) {
		// TODO Auto-generated method stub
		List<MappingDetails> searchMappingResult = null;
		ArrayList<MappingDetails> searchArrayConnection = new ArrayList<MappingDetails>();
		searchMappingResult =searchArrayConnection;
		for(MappingDetails sr : mappingResult){
			if (sr.getMappingName().contains(input)){
				searchMappingResult.add(sr);
			}
		}
		
		return (ArrayList<MappingDetails>) searchMappingResult;
	}
	public Boolean deleteMapping(String input) {
		// TODO Auto-generated method stub
		int i=0;
		for( MappingDetails sr : mappingResult){
			if (sr.getMappingName().equals(input)){
				try{
					MappingDetailsH mdhsr = convertFrontEnd2DB(sr);
					cascadeDelete(mdhsr);
				 	break; 
				}
				catch(Exception e){
					System.out.println("Error"+e);
					logger.severe("Error"+e);
					hc.abortBatchLevelTransaction();
				}
			}
			i = i+1;
		}
		mappingResult.remove(i);
		return true;
	}
	public boolean editMapping(MappingDetails mappingDetail) {
		// TODO Auto-generated method stub
		int i=0;
		String methodName= new String("MappingDetailsServer.editMapping");
		logger.info("Inside"+methodName);
		for( MappingDetails sr : mappingResult){
			if (sr.getMappingName().equals(mappingDetail.getMappingName())){
				try{
					MappingDetailsH mdhsr = convertFrontEnd2DB(sr);
					cascadeDelete(mdhsr);
					hc.clearObjectCache();
					hc.startBatchLevelTransaction();
					hc.saveOperation(convertFrontEnd2DB(mappingDetail));
					hc.commitBatchLevelTransaction();
					break;
				}
				catch(Exception e){
					System.out.println("Error"+e);
					hc.abortBatchLevelTransaction();
				}
			}
			i = i+1;
		}
		mappingResult.remove(i);
		mappingResult.add(i,mappingDetail);
		return true;
	}
	public ArrayList<String> getMappingTypeList() {
		System.out.println("Size"+mappingType.size());
		System.out.println("getConnectionType");
		
		return mappingType;
	}
	 
	public  MappingDetails convertDB2FrontEnd(MappingDetailsH mappingDetailsH){
		String methodName= new String("MappingDetailsServer.convertDB2FrontEnd");
		logger.info("Inside"+methodName);
		MappingDetails mappingDetailsM = new MappingDetails();
		try{
			mappingDetailsM.setMappingName(mappingDetailsH.getMappingName());
			mappingDetailsM.setMappingType(mappingDetailsH.getMappingType());
			mappingDetailsM.setNameOfExpression(mappingDetailsH.getExpressionName());
			 
			ArrayList<String> sourceObjectListT = new ArrayList<String>() ;
			Map<Integer,MappingDetailsMapH> sdmh = mappingDetailsH.getMappingDetailsMap();
			if(sdmh!=null){
				Iterator iterator = sdmh.entrySet().iterator();
				int i = 0;
				String targetObjectPrivus = new String("");
				while(iterator.hasNext()){
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					MappingDetailsMapH sdmp =(MappingDetailsMapH)mapEntry.getValue();
					logger.info("Adding sourceObjectList number"+i);
					sourceObjectListT.add(sdmp.getSourceObject());
					if(mappingDetailsH.getMappingType().equals("Object")){
						if(targetObjectPrivus.equals("")){
							mappingDetailsM.setTargetObject(sdmp.getTargetObject());
						}else{
							if(!sdmp.getTargetObject().equals(targetObjectPrivus)){
								mappingDetailsM.setTargetObject(mappingDetailsM.getTargetObject()+","+sdmp.getTargetObject());
							}
						}
						targetObjectPrivus = sdmp.getTargetObject();
						logger.info("Target Object Name"+mappingDetailsM.getTargetObject());
					}else{
						mappingDetailsM.setTargetObject(sdmp.getTargetObject());
					}
					
					i = i + 1;
				}
			}
			mappingDetailsM.setSourceObjectList(sourceObjectListT);
		}catch(Exception e){
			logger.severe("error"+e);
			
		}
		
		logger.info("exiting"+methodName);
	    return mappingDetailsM;
	}
	public  MappingDetailsH convertFrontEnd2DB(MappingDetails mappingDetails){
		 String methodName= new String("MappingDetailsServer.convertFrontEnd2DB");
		 logger.info("Inside"+methodName);
		 MappingDetailsH mdh = new MappingDetailsH();
		 mdh.setExpressionName(mappingDetails.getNameOfExpression());
		 mdh.setMappingName(mappingDetails.getMappingName());
		 mdh.setMappingType(mappingDetails.getMappingType());
		 ArrayList<String> sourceObjectList = mappingDetails.getSourceObjectList();
		 /****
		  * if mapping type is object then multiple targets can exist.
		  * in this case source objects number should equal to target object list 
		  */
		 String[] targetObjectList = mappingDetails.getTargetObject().split(",");
		 logger.info("mappingDetails.getTargetObject()"+mappingDetails.getTargetObject());
		 Map<Integer,MappingDetailsMapH> sdmh = new HashMap(0);
		 if(sourceObjectList!=null){
			 for(int i=0;i<sourceObjectList.size();i++){
				 logger.info("Adding object number"+i);
				 MappingDetailsMapH mdmh = new MappingDetailsMapH();
				 mdmh.setMappingName(mappingDetails.getMappingName());
				 mdmh.setSequenceNumber(i);
				 if(targetObjectList.length>i){
					 mdmh.setTargetObject(targetObjectList[i]);
				 }else{
					 mdmh.setTargetObject(targetObjectList[0]);
				 }
				 mdmh.setSourceObject(sourceObjectList.get(i));
				 mdmh.setMappingType(mappingDetails.getMappingType());
				 sdmh.put(i,mdmh);
				 logger.info("value for mappingName"+mdmh.getMappingName());
			 }
		 }
		 mdh.setMappingDetailsMap(sdmh);
		 logger.info("exiting"+methodName);
		 return mdh;
	}
	public void cascadeDelete(MappingDetailsH mappingDetailsH){
		 String methodName= new String("MappingDetailsServer.cascadeDelete");
		 logger.info("Inside"+methodName);
		 try{
			 hc.clearObjectCache();
			 Map<Integer,MappingDetailsMapH> sdmhM = mappingDetailsH.getMappingDetailsMap();
				if(sdmhM!=null){
					Iterator iterator = sdmhM.entrySet().iterator();
					int i = 0;
					while(iterator.hasNext()){
						Map.Entry mapEntry = (Map.Entry) iterator.next();
						MappingDetailsMapH sdmp =(MappingDetailsMapH)mapEntry.getValue();
						logger.info("delete map number "+i);
						logger.info("get map name  "+sdmp.getMappingName());
						hc.startBatchLevelTransaction();
						hc.deleteOperation(sdmp);
						hc.commitBatchLevelTransaction(); 
						logger.info("deleted map seq"+sdmp.getSequenceNumber());
						logger.info("deleted map src obj"+sdmp.getSourceObject());
						i = i+1;
					}
				}
				logger.info("start deleting parent");
				hc.startBatchLevelTransaction();
				hc.deleteOperation(mappingDetailsH);
				hc.commitBatchLevelTransaction();
		 }catch (Exception e){
			 logger.severe("Error"+e);
			 hc.abortBatchLevelTransaction();
		 }
		 
		 logger.info("Exiting"+methodName);
	}
	public ArrayList<String> getAttributeList(String  srcObjectName) {
		String methodName= new String("MappingDetailsServer.getAttributeList");
		logger.info("Inside"+methodName);
		ArrayList<String> attributeList = new   ArrayList<String>();
		try{
			GetRecordDetails grd = new GetRecordDetails();
			grd.initOperation(hc);
			ArrayList<String> pList = grd.getCombinedPKAndCKAndProperties(packageName+srcObjectName);
			for(int i=0; i< pList.size();i++){
				attributeList.add(srcObjectName+"."+pList.get(i));
			}
			logger.info("Exiting"+methodName);
		}catch(Exception e){
			logger.severe("Exception"+e);
		}
		return attributeList;
		
	}
	public ArrayList<String> getObjectLevelMappingNames() {
		String methodName= new String("MappingDetailsServer.getObjectLevelMappingNames");
		logger.info("Inside"+methodName);
		objectLevelMappingNames.clear();
		for(int i=0; i <mappingResult.size(); i++){
			if(mappingResult.get(i).getMappingType().equals("Object")){
				objectLevelMappingNames.add(mappingResult.get(i).getMappingName());
			}
		}
		return objectLevelMappingNames;
	}

}
