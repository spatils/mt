package com.managetransfer.record;

 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.managetransfer.batches.BatchHandler;
 
import com.managetransfer.client.MappingDetailsH;
import com.managetransfer.client.MappingDetailsMapH;
import com.managetransfer.client.PhasesDetailsH;
import com.managetransfer.client.PhasesDetailsStringH;
import com.managetransfer.client.SequenceDetailsH;
import com.managetransfer.client.SequenceDetailsMapH;
import com.managetransfer.hibernate.HibernateConnection;
 
 

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
    String SQLDrivingCursor =  new String("from $objectName$ where mtSequenceName ='$sequenceName$' and mtSequenceNumber=$sequenceNumber$ and mtProcessId = $processId$");
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
    			sourceRecord = new Record();
    			targetRecord = new Record();
    			logger.info("Processing Record Number : "+i);
    			sourceObject = objectList.get(i);
    			rh.initOperation();
    			rh.setRecord(sourceRecord);
				rh.getPropertyValuesAll(sourceObject);
    			Transformation tf = new Transformation();
	    		tf.setTransformationName(transformationName);
	    		Object[] objects = new Object[2];
	    		objects[0] = objectList.get(i);
	    		tf.setSourceObjects(objects);
	    		try{
	    			tf.executeTransformation();
	    		}catch(Exception e){
	    			logger.severe("Error in transformation"+e);
	    			throw e;
	    		}
	    		logger.info("Transformation Successful");
	    		targetObject = tf.getTargetobject();
	    		rhTarget.initOperation();
	    		rhTarget.setRecord(targetRecord);
	    		rhTarget.getPropertyValuesAll(targetObject);
	    		//Set of Attributes for the Target
	    		rhTarget.getRecord().setSequenceName(sequenceName);
	    		if(!isLastSequence){
	    			rhTarget.getRecord().setSequenceNumber(sequenceNumber+1);
						if(nextProcessId < ( nextThreadCount -1 )   ){
							nextProcessId = nextProcessId +1 ;
							
						}else{
							nextProcessId = 0 ;
						}
						rhTarget.getRecord().setProcessId(nextProcessId);
				}else{
					rhTarget.getRecord().setSequenceNumber(sequenceNumber);
					rhTarget.getRecord().setStatusOfRecord("SUCCESS");
					rhTarget.getRecord().setProcessId(0);
				}
				Date today = new Date();
				rhTarget.getRecord().setCreateDate(today);
				rhTarget.getRecord().setModifyDate(today);
				logger.info("Target Record String attr size:"+rhTarget.getRecord().getListOfStringAtrributes().size());
				rhTarget.saveRecord(targetObject);
				//Set of Attributes for the source 
				//Get create date for source
				Date createDateSource  = (Date) rh.getSpecificAttributeValue(objectList.get(i), "mtCreateDate")  ;
				rh.getRecord().setSequenceName(sequenceName);
				rh.getRecord().setSequenceNumber(sequenceNumber);
				rh.getRecord().setStatusOfRecord("SUCCESS");
				rh.getRecord().setCreateDate(createDateSource);
				rh.getRecord().setModifyDate(today);
				rh.getRecord().setProcessId(getProcessId());
				logger.info("Source Record String attr size:"+rh.getRecord().getListOfStringAtrributes().size());
				rh.saveRecord(sourceObject);
				batchCount = batchCount +1 ;
				bh.addSuccessCount(1);
				processCount = processCount + 1 ;
				if(commitCount>=processCount){
					batchCount = 0;
					bh.saveBatch();
					hc.commitBatchLevelTransaction();
					hc.startBatchLevelTransaction();
				}
				if(totalProcessCount>=batchCount || getInterruptFlag()){
					break ;
				}
    		}
			catch(Exception e){
				e.printStackTrace(System.out);
				logger.severe("Error While process  "+e);
				try {
					rh.getRecord().setErrorDetails(e.getMessage());
					rh.getRecord().setSequenceNumber(sequenceNumber);
					rh.saveRecord(sourceObject);
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
				MappingDetailsMapH mdm  = mapDtails.get(0);
				setRecordType(packageName+mdm.getSourceObject() ) ;
				setRecordTypeTarget(packageName+mdm.getTargetObject());
				logger.info("Found source and target object");
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
}
