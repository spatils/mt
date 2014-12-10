package com.managetransfer.record;

 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.managetransfer.batches.BatchHandler;
 
import com.managetransfer.client.MappingDetailsH;
import com.managetransfer.client.MappingDetailsMapH;
import com.managetransfer.client.ObjectDetails;
import com.managetransfer.client.PhasesDetailsH;
import com.managetransfer.client.PhasesDetailsStringH;
import com.managetransfer.client.SequenceDetailsH;
import com.managetransfer.client.SequenceDetailsMapH;
import com.managetransfer.hibernate.HibernateConnection;
import com.managetransfer.dynamiccode.transformation.Transformation;
 

/* Input Parameters 
 *  	Sequence Name
 *  	Sequence Number
 *  	Process Id
 *  	Source Record Type
 *      Commit Count
 *      Batch Count
 *      Transformation Name
 */

public class TransformationHandler {
    String transformationName =  new String("Mappping123");
    String SQLDrivingCursor =  new String("from $objectName$ where mtSequenceName ='$sequenceName$' and mtSequenceNumber=$sequenceNumber$ and mtProcessId = $processId$  and ( mtStatus is null or mtStatus !='SUCCESS'  ) ");
    private String packageName = new String("com.managetransfer.businessobject.");
	private String recordType = new String("Claims");
	private String recordTypeTarget = new String("Information");
	private RecordHandler rh =   new RecordHandler();
	private RecordHandler rhTarget =   new RecordHandler();
	private ArrayList<String> allColumns = new ArrayList<String>();
	private String sequenceName = new String("");
    private int sequenceNumber =0;
    private int processId = 0 ;
    private int batchCount = 10000;
    private int commitCount = 1000;
    private int nextThreadCount = 1 ;
    private BatchHandler bh = new BatchHandler();
    private boolean isLastSequence = false;
    private HibernateConnection hc = new HibernateConnection();
    final Logger logger = Logger.getLogger(TransformationHandler.class.getName()) ;
    Boolean interruptFlag =false;
    ArrayList<String> dependantSourceObjectTypeList = new ArrayList<String>();// This is the list of dependant object passed to the trasnformation 
    public static void main(String[] args) throws Exception{
    	TransformationHandler th = new TransformationHandler();
    	th.initOperation();
    	th.executeOperation();
    
	}
    public void executeOperation() {
    	String methodName="executeOperation";
		logger.info("Inside Method"+methodName);
		int processCount = 0;
		int totalProcessCount =0;
		hc.startBatchLevelTransaction();
    	List objectList = hc.getObject(getSQLDrivingCursor());
    	Object sourceObject = new Object();
		Object targetObject = new Object();
		Record targetRecord = new Record();
		Record sourceRecord = new Record();
		 
		int nextProcessId = 0;
		 for(int i=0; i < objectList.size();i++ ){
    		try{
    			if(totalProcessCount>=batchCount || getInterruptFlag()){
					break ;
				}
    			sourceRecord = new Record();
    			targetRecord = new Record();
    			logger.info("Processing Record Number : "+i);
    			sourceObject = objectList.get(i);
    			
    			rh.initOperation();
    			rh.setRecord(sourceRecord);
				rh.getPropertyValuesAll(sourceObject);
				//Get all dependant objects
    			ArrayList<Object> sourceDependantObjects = new ArrayList<Object>();
    			sourceDependantObjects = getAllDependantObject(sourceRecord);
    			logger.info("Got all dependnent object::"+sourceDependantObjects.size());
    			Transformation tf = new Transformation();
	    		tf.setTransformationName(transformationName);
	    		//Adding primary object to the list
	    		sourceDependantObjects.add(objectList.get(i));
	    		tf.setSourceObjects(sourceDependantObjects);
	    		try{
	    			tf.executeTransformation();
	    		}catch(Exception e){
	    			logger.severe("Error in transformation"+e);
	    			throw e;
	    		}
	    		logger.info("Transformation Successful");
	    		ArrayList<Object> targetObjectList = tf.getTargetobject();
	    		if(targetObjectList.size()==0) throw new Exception("List of target objects is null");
	    		logger.info("Size of objects received"+targetObjectList.size());
	    		for(int p=0;p<targetObjectList.size();p++){
	    			if(targetObjectList.get(p).getClass().toString().equals("class "+getRecordTypeTarget())){
	    				//Got primary target object
	    				logger.info("Primary object assingment"+targetObjectList.get(p).getClass());
	    				targetObject= targetObjectList.get(p);
	    			}else{
	    				//Saving dependant object
	    				logger.info("Saving object type"+targetObjectList.get(p).getClass());
	    				rh.saveObject(targetObjectList.get(p));
	    				
	    			}
	    			 
	    		}
	    		rhTarget.initOperation();
	    		rhTarget.setRecord(targetRecord);
	    		rhTarget.getPropertyValuesAll(targetObject);
	    		//Set of Attributes for the Target
	    		targetRecord.setSequenceName(sequenceName);
	    		if(!isLastSequence){
	    			targetRecord.setSequenceNumber(sequenceNumber+1);
						if(nextProcessId < ( nextThreadCount -1 )   ){
							nextProcessId = nextProcessId +1 ;
							
						}else{
							nextProcessId = 0 ; 
						}
						targetRecord.setProcessId(nextProcessId);
				}else{
					targetRecord.setSequenceNumber(sequenceNumber);
					targetRecord.setStatusOfRecord("SUCCESS");
					targetRecord.setProcessId(0);
				}
				Date today = new Date();
				targetRecord.setCreateDate(today);
				targetRecord.setModifyDate(today);
				logger.info("Target Record String attr size:"+targetRecord.getListOfStringAtrributes().size());
				rhTarget.setRecord(targetRecord);
				rhTarget.saveRecord(targetObject);
				//Set of Attributes for the source 
				//Get create date for source
				Date createDateSource  = (Date) rh.getSpecificAttributeValue(objectList.get(i), "mtCreateDate")  ;
				sourceRecord.setSequenceName(sequenceName);
				sourceRecord.setSequenceNumber(sequenceNumber);
				sourceRecord.setStatusOfRecord("SUCCESS");
				sourceRecord.setCreateDate(createDateSource);
				sourceRecord.setModifyDate(today);
				sourceRecord.setProcessId(getProcessId());
				logger.info("Source Record String attr size:"+sourceRecord.getListOfStringAtrributes().size());
				rh.setRecord(sourceRecord);
				rh.saveRecord(sourceObject);
				bh.addSuccessCount(1);
				processCount = processCount + 1 ;
				if(commitCount>=processCount){
					processCount = 0;
					bh.saveBatch();
					hc.commitBatchLevelTransaction();
					hc.startBatchLevelTransaction();
				}
				
    		}
			catch(Exception e){
				e.printStackTrace(System.out);
				logger.severe("Error While processing a record "+e);
				try {
					rh.getPropertyValuesAll(sourceObject);
					rh.getRecord().setErrorDetails(e.toString());
					logger.info(" Error details set"+sourceRecord.getErrorDetails());
					rh.getRecord().setSequenceNumber(sequenceNumber);
					rh.getRecord().setStatusOfRecord("FAIL");
					rh.getRecord().setModifyDate(new Date());
					rh.saveRecord(sourceObject);
					logger.info(" Error details set"+rh.getRecord().getErrorDetails());
					bh.addFailureCount(1);
					
				} catch ( Exception ex){
					logger.severe("Error While saving object process  "+ex);
				}
			}
    		
    	}
    	bh.exitBatch("");
    	hc.commitBatchLevelTransaction();
		hc.closeConnection();
		logger.info("Existing Method"+methodName);
    }
    private ArrayList<Object> getAllDependantObject(Record record) throws Exception{
    	
		ArrayList<Object> dependantObject = new ArrayList<Object>();
		try{
			/** 
			 *  Get list of objects (other than primary object) required for current transformation from mappingsdetailsmap
			 *  For each source object type get relation attributes from object details table
			 *  Fetch object from rh.getObjectList
			 */
			for (int p=0;p< dependantSourceObjectTypeList.size(); p++){
				logger.info("dependant object name"+dependantSourceObjectTypeList.get(p));
				if(! dependantSourceObjectTypeList.get(p).equals( getRecordType().substring(getRecordType().lastIndexOf(".")+1,getRecordType().length()))){
					ObjectDetails objectDetails = bh.getObjectDetails(getRecordType().substring(getRecordType().lastIndexOf(".")+1,getRecordType().length()),dependantSourceObjectTypeList.get(p));
					ArrayList<String> dependantColumn = new ArrayList<String>();
					String[] columnList = objectDetails.getRelationshipColumns().split(",");
					for(int q=0;q< columnList.length;q++){
						logger.info("columnName"+columnList[q]);
						dependantColumn.add(columnList[q]);
					}
					dependantObject.addAll(rh.getObjectList(packageName+dependantSourceObjectTypeList.get(p),dependantColumn,record ));
				} 
				
			}
		}catch(Exception e){
			logger.severe("Error"+e);
			throw e;
		}
    
		return dependantObject;
	}
	public void initOperation() throws Exception{
    	String methodName="initOperation";
		logger.info("Inside Method"+methodName);
		try{
			hc.initOperation();
			try{
				//Create a batch recode	 
				hc.startBatchLevelTransaction();
				bh.setHc(hc);
				bh.setProcessId(getProcessId());
				bh.setSequenceName(sequenceName);
				bh.setSequenceNumber(sequenceNumber);
				bh.initOperation();
				bh.saveBatch();
				//Get Sequence information
				SequenceDetailsH sequenceDetails = null;
				try{
					sequenceDetails = getSequenceDetails(getSequenceName());
					if (sequenceDetails == null )   throw new Exception("Sequence not found");
					if(getSequenceNumber()==sequenceDetails.getNumberOfPhases()-1){
						isLastSequence = true;
					}
				}catch (Exception e){
					throw new Exception ("Error in Sequence information"+e.getMessage());
				}
				//Get Phase Information 
				PhasesDetailsH phasesDetails = null;
				SequenceDetailsMapH  sdm = null;
				SequenceDetailsMapH  nextSdm = null;
				//Get Phase Information 
				try{
					logger.info(""+sequenceDetails.getSequenceDetailsMap().size());
					Map<Integer,SequenceDetailsMapH> sdmh = sequenceDetails.getSequenceDetailsMap();
					Iterator iterator = sdmh.entrySet().iterator();
					int i = 0;
					while(iterator.hasNext()){
						logger.info("inside Iterator"+i);
						Map.Entry mapEntry = (Map.Entry) iterator.next();
						if(i==getSequenceNumber()){
							sdm =(SequenceDetailsMapH)mapEntry.getValue();
						}
						if(i==getSequenceNumber()+1){
							nextSdm = (SequenceDetailsMapH)mapEntry.getValue();
						}
						i = i + 1;
					}
					
					if (sdm == null )   throw new Exception("Sequence Details Map not found");
					
					phasesDetails = getPhaseDetails(sdm.getPhaseName()) ;
					if (phasesDetails == null )   throw new Exception("Phases Details Map not found");
					//Set Commit Count
					commitCount = sdm. getCommitCount();
					//set Batch Count 
					setBatchCount(sdm.getBatchSize());
				}catch (Exception e){
					throw new Exception ("Error in Get Phase Information"+e.getMessage());
				}
				
				
				//Setting next thread count
				if(!isLastSequence){
					nextThreadCount =   nextSdm.getThreadCount();
				}
				
				//Set Record Type 
				PhasesDetailsStringH transformationPh = (PhasesDetailsStringH) phasesDetails.getPhaseDetailsString().get("Transformation");
				MappingDetailsH mappingDetails = getMappingDetails ( transformationPh.getParameterValue());
				if ( mappingDetails == null ) throw new Exception("Mapping details not found");
				Map<Integer,MappingDetailsMapH> mapDtails = mappingDetails.getMappingDetailsMap();
				if ( mapDtails == null ) throw new Exception("Mapping of object type not found");
				transformationName =transformationPh.getParameterValue();
				for(int p=0;p < mapDtails.size();p++){
					MappingDetailsMapH mdm  = mapDtails.get(p);
				    if(mdm.getSequenceNumber()==0){
						setRecordType(packageName+mdm.getSourceObject() ) ;
						setRecordTypeTarget(packageName+mdm.getTargetObject());
						logger.info("Found source and target object");
				    }else{
				    	dependantSourceObjectTypeList.add(mdm.getSourceObject());
				    }
				}
				rh.setHc(hc);
				rh.setTypeOfRecord(getRecordType());
				rh.initOperation();
				rhTarget.setHc(hc);
				rhTarget.setTypeOfRecord(getRecordTypeTarget());
				rhTarget.initOperation();
				hc.commitBatchLevelTransaction();
				
			} catch (Exception e){
				logger.severe("Error Inside initOperation"+e);
				bh.exitBatch(e.toString());
				hc.commitBatchLevelTransaction();
				throw e;
			}
		}catch(Exception e){
			logger.severe("Error Inside initOperation"+e);
			throw e ;
		}
		 
		logger.info("Exiting Method"+methodName);
    }
    public void setSQL(){
    	
    }
    private void existOperation() {
		  
	}
	public String getSequenceName() {
		return sequenceName;
	}
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public int getProcessId() {
		return processId;
	}
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	public SequenceDetailsH getSequenceDetails(String sequenceName){
		List sequenceList = hc.getObject("from SequenceDetailsH where sequenceName='"+sequenceName+"'"); 
		SequenceDetailsH sequenceDetails = null;
		for(int i=0;i<sequenceList.size() ;i++){
			 sequenceDetails = (SequenceDetailsH) sequenceList.get(i) ;
		}
		return sequenceDetails;
	}
	public PhasesDetailsH getPhaseDetails(String  phaseName ){
		List phaseList = hc.getObject("from PhasesDetailsH where phaseName='"+phaseName+"'"); 
		PhasesDetailsH phasesDetails = new PhasesDetailsH()  ;
		for(int i=0;i<phaseList.size() ;i++){
			phasesDetails = (PhasesDetailsH) phaseList.get(i) ;
		}
		return phasesDetails;
	}
	public MappingDetailsH getMappingDetails(String  mappingName ){
		List mappingList = hc.getObject("from MappingDetailsH where mappingType='Object' and mappingName='"+mappingName+"'"); 
		MappingDetailsH mappingDetails = new MappingDetailsH()  ;
		for(int i=0;i<mappingList.size() ;i++){
			mappingDetails = (MappingDetailsH) mappingList.get(i) ;
		}
		return mappingDetails;
	}
	public boolean isLastSequence() {
		return isLastSequence;
	}
	public void setLastSequence(boolean isLastSequence) {
		this.isLastSequence = isLastSequence;
	}
	public int getBatchCount() {
		return batchCount;
	}
	public void setBatchCount(int batchCount) {
		this.batchCount = batchCount;
	}
	public void splitBatch(){
		
	}
	public String getSQLDrivingCursor() {
		String dSelectCountHQL = SQLDrivingCursor.replace("$objectName$", getRecordType());
		dSelectCountHQL = dSelectCountHQL.replace("$sequenceName$", getSequenceName());
		dSelectCountHQL = dSelectCountHQL.replace("$sequenceNumber$", ""+getSequenceNumber());
		dSelectCountHQL = dSelectCountHQL.replace("$processId$", ""+getProcessId());
		logger.info("Driving cursor : "+dSelectCountHQL);
		return dSelectCountHQL;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getRecordTypeTarget() {
		return recordTypeTarget;
	}
	public void setRecordTypeTarget(String recordTypeTarget) {
		this.recordTypeTarget = recordTypeTarget;
	}public Boolean getInterruptFlag() {
		return this.interruptFlag;
	}
	public void setInterruptFlag(Boolean interruptFlag) {
		logger.info("Setting interrup flag to"+interruptFlag);
		this.interruptFlag = interruptFlag;
	}
	public ObjectDetails getObjectDetails(String objectName){
		List objectDetailsList = hc.getObject("from ObjectDetails where objectName='"+objectName+"'"); 
		ObjectDetails objectDetails = new ObjectDetails()  ;
		for(int i=0;i<objectDetailsList.size() ;i++){
			objectDetails = (ObjectDetails) objectDetailsList.get(i) ;
		}
		return objectDetails;
	}
}
