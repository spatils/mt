package com.managetransfer.batches;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.managetransfer.client.BatchDetails;
import com.managetransfer.client.ObjectDetails;
import com.managetransfer.hibernate.HibernateConnection;

 

public class BatchHandler {
     
    final Logger logger = Logger.getLogger(BatchHandler.class.getName()) ;
	
	private HibernateConnection hc = new HibernateConnection();
	private String sequenceName = new String("Seq1");
    private int sequenceNumber =0;
    private int processId = 0 ;
    private int threadCount = 0;
    private boolean batchExist = false;
    private BatchDetails batchDetails = new BatchDetails();
	public static void main(String[] args) {
		 
	}
	public void initOperation() throws Exception{
		if( hc  == null) {
			 hc.initOperation();
		}
		Date today = new Date();
		 
			//Create batch record for process id 0
		batchExist = isBatchAlreadyExist(getSequenceName(),getSequenceNumber(),getProcessId());
		if(!batchExist){
			batchDetails.setBatchName(getBatchName(getSequenceName(),getSequenceNumber(),getProcessId()));
			batchDetails.setBatchStatus("RUNNING");
			batchDetails.setStartTime(today);
			batchDetails.setSuccessCount(0);
			batchDetails.setFailureCount(0);	
			batchDetails.setModifyTime(today);
			batchDetails.setSequenceName(getSequenceName());
			batchDetails.setSequenceNumber(getSequenceNumber());
			
		}
		hc.saveOperation(batchDetails);
		
	}
	public String getBatchName (String sequenceName, int sequenceNumber,int processId){
		  	 Date today = new Date();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss"); 
			 return sequenceName +sequenceNumber +processId +sdf.format(today);
		  
	}
	public boolean isBatchAlreadyExist( String sequenceName,int seqeunceNumber, int processId ){
		boolean isPresent = false;
		List list = hc.getObject("from BatchDetails where batchName like '"+sequenceName +sequenceNumber + processId +"%' and batch_status='RUNNING'" );
		for (int i=0;i<list.size();i++){
			//If it is current batch then get the object
			if(processId==getProcessId()){
				batchDetails = (BatchDetails) list.get(i);
			}
			isPresent = true;
		}
		
		return isPresent;
	}
	public void addSuccessCount(int i){
		batchDetails.setSuccessCount(batchDetails.getSuccessCount()+i);
	}
	public void addFailureCount(int i){
		batchDetails.setFailureCount(batchDetails.getFailureCount()+i);
	}
	public int getSuccessCount(){
		return batchDetails.getSuccessCount();
	}
	public int getFailureCount(){
		return batchDetails.getFailureCount(); 
	}
	public void setSuccessCount(int count){
		batchDetails.setSuccessCount(count);
	}
	public void getFailureCount(int count){
		batchDetails.setFailureCount(count); 
	}
	public void saveBatch(){
		Date today = new Date();
		batchDetails.setModifyTime(today);
		hc.saveOrUpdateOperation(batchDetails);
	}
	public void exitBatch(String errorCode){
		//After commit everything is flushed from memory. Hence save and update operation is being used.
		Date today = new Date();
		if(errorCode == null || errorCode.equals("")){
			batchDetails.setModifyTime(today);
			batchDetails.setBatchStatus("SUCCESS");
			hc.saveOrUpdateOperation(batchDetails);
		}else{
			batchDetails.setModifyTime(today);
			batchDetails.setBatchStatus("FAIL");
			batchDetails.setErrorDescription(errorCode);
			hc.saveOrUpdateOperation(batchDetails);
		}
	}
	
	public HibernateConnection getHc() {
		return hc;
	}
	public void setHc(HibernateConnection hc) {
		this.hc = hc;
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
	public BatchDetails getBatchDetails() {
		return batchDetails;
	}
	public void setBatchDetails(BatchDetails batchDetails) {
		this.batchDetails = batchDetails;
	}
	public int getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	public boolean isBatchExist() {
		return batchExist;
	}
	public void setBatchExist(boolean batchExist) {
		this.batchExist = batchExist;
	}
	public String getBatchName(){
		return batchDetails.getBatchName();
	}
	public ObjectDetails getObjectDetails(String primaryObjectName, String secondarObjectName){
		String methodName = "getObjectList";
		logger.info("Inside Method" + methodName);
		try{
		String HQL = " from ObjectDetails where objectName ='"+primaryObjectName+"' and repeatingObject ='"+secondarObjectName+"'";
		logger.info("HQL" + HQL);
		return (ObjectDetails) hc.getObject(HQL).get(0);
		}catch (Exception e){
			logger.severe("Error" + e);
			throw e;
		}
		 
	}
}
