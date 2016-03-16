package com.admin.shared.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.managetransfer.client.LogsDetails;
import com.managetransfer.client.PhasesDetailsStringH;
import com.managetransfer.hibernate.GetRecordDetails;
import com.managetransfer.hibernate.HibernateConnection;
import com.managetransfer.record.RecordHandler;

public class LogsDetailsServer {
	String packageName = new String("com.managetransfer.businessobject.");
	private HibernateConnection hc;
	String getObjectDetails = new String("from $object_name$  where $attribute_name$ = '$attribute_value$'");
	final Logger logger = Logger.getLogger(LogsDetailsServer.class.getName()) ;
	public List<LogsDetails> getLogsDetails() {
		List<LogsDetails> logsDetailsList = new ArrayList<LogsDetails>();
		LogsDetails logsDetails1 = new LogsDetails();
		logsDetails1.setErrorType("Connection Error");
		logsDetails1.setNumberOfErrors(10);

		LogsDetails logsDetails2 = new LogsDetails();
		logsDetails2.setErrorType("Content Not Found Error");
		logsDetails2.setNumberOfErrors(5);

		LogsDetails logsDetails3 = new LogsDetails();
		logsDetails3.setErrorType("Permission Error");
		logsDetails3.setNumberOfErrors(25);

		logsDetailsList.add(logsDetails1);
		logsDetailsList.add(logsDetails2);
		logsDetailsList.add(logsDetails3);

		return logsDetailsList;

		//return hc.getObject("from LogsDetails ");

	}

	public List<LogsDetails> getLogsDetails(String objectType,
			String attribute, String value) {
		String methodName="getLogsDetails";
		logger.info("Inside Method"+methodName);
		List<LogsDetails> logsDetailsList = new ArrayList<LogsDetails>();
		try{
		GetRecordDetails grd = new GetRecordDetails();
		grd.initOperation(hc);
		Object object = null;
		String query = getObjectDetails.replace("$object_name$", objectType).replace("$attribute_name$", attribute).replace("$attribute_value$",value);
		logger.info("query"+query);
		List objectList = hc.getObject(query);
		for(int l =0 ; l<objectList.size();l++){
			object = objectList.get(l);
		}
		System.out.println("The values are as below ..." + objectType + " - "
				+ attribute + " - " + value);
		
		String className = packageName+ objectType;
		LogsDetails logsDetails1 = new LogsDetails();
		if(object == null){
			logsDetails1.setMtSequenceName("Record Not Found");
		}else{
			try{
				RecordHandler rh =   new RecordHandler();
				rh.setHc(hc);
				rh.setTypeOfRecord(className);
				rh.initOperation();
				logsDetails1.setMtSequenceNumber((int)rh.getSpecificAttributeValue(object,   "mtSequenceNumber"));
				logsDetails1.setMtProcessId((int)rh.getSpecificAttributeValue(object,  "mtProcessId"));
				logsDetails1.setMtErrorDesc((String)rh.getSpecificAttributeValue(object,   "mtErrorDesc"));
				logsDetails1.setMtStatus((String)rh.getSpecificAttributeValue(object,  "mtStatus"));
				logsDetails1.setMtSequenceName((String)rh.getSpecificAttributeValue(object,   "mtSequenceName"));
				if(rh.getSpecificAttributeValue(object,    "mtModifyDate")!=null){
					logsDetails1.setMtModifyDate((Date)rh.getSpecificAttributeValue(object,"mtModifyDate"));
				}
				if(rh.getSpecificAttributeValue(object,  "mtCreateDate")!=null){
					logsDetails1.setMtCreateDate((Date)rh.getSpecificAttributeValue(object,"mtCreateDate"));
				}
			}catch (Exception e){

				logger.severe("Error setting populating log object"+e);
			}

		}

		logsDetails1.setNumberOfErrors(10);

		logsDetailsList.add(logsDetails1);
		}
		catch(Exception e){
			logger.severe("Error getLogsDetails"+e);
	}
		 
		return logsDetailsList;
	}

	public void setHc(HibernateConnection hc) {
		this.hc = hc;
	}

}
