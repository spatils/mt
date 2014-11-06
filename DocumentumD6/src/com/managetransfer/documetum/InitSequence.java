package com.managetransfer.documetum;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfQuery;
import com.managetransfer.batches.BatchHandler;
import com.managetransfer.client.ConnectionDetails;
import com.managetransfer.client.PhasesDetailsH;
import com.managetransfer.client.PhasesDetailsStringH;
import com.managetransfer.client.SequenceDetailsH;
 
import com.managetransfer.client.SequenceDetailsMapH;
import com.managetransfer.dao.Connection;
import com.managetransfer.hibernate.HibernateConnection;
import com.managetransfer.record.Record;
import com.managetransfer.record.RecordHandler;
 /* Input Parameters 
  *  	Sequence Name
  *  	Sequence Number
  *  	Process Id
  *  	DQL To Get object id  				--InitDQL
  *  	Record Type			  				--SourceObject
  *  	Repository Connection Details 		--Connection
  *     Commit Count
  *     Batch Count    
  *     */ 
 
public class InitSequence {
	private int commitCount = 1000;
	private int batchCount = 10000;
	private int nextThreadCount = 1 ;
	private DocumentumConnection cd = new DocumentumConnection();
    private String initDQL = new String("select r_object_id from claims");
    private IDfQuery idfQuery = new DfQuery();
    private IDfCollection idfCollection = null;
    private RecordHandler rh = new RecordHandler();
    private String recordType = new String("com.managetransfer.record.Claims");
    private String packageName = new String("com.managetransfer.record.");
    private String sequenceName = new String("Seq1");
    private int sequenceNumber =0;
    private int processId = 0 ;
    private HibernateConnection hc = new HibernateConnection();
    private BatchHandler bh = new BatchHandler();
    private boolean isLastSequence = false;
    final Logger logger = Logger.getLogger(InitSequence.class.getName()) ;
	public static void main(String[] args) throws Exception {
		InitSequence id = new InitSequence();
		id.initOperation();
		id.executeOperation(); 
		id.existOperation();
	}
	private void existOperation() {
		
	}
	public void initOperation() throws Exception{
		//If exception occurs in the block then batch should be considered as failure 
		String methodName="initOperation";
		logger.info("Inside Method"+methodName);
		try{
			hc.initOperation();
			logger.info("Initialized hibernate connection ");
			try{
				//Create a batch recode	 
				hc.startBatchLevelTransaction();
				bh.setHc(hc);
				bh.setProcessId(getProcessId());
				bh.setSequenceName(sequenceName);
				bh.setSequenceNumber(sequenceNumber);
				bh.initOperation();
				bh.saveBatch();
				logger.info("Initialized batch connection ");
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
				//Get Repository Connection Information
				PhasesDetailsStringH connectName = (PhasesDetailsStringH) phasesDetails.getPhaseDetailsString().get("Connection");
				ConnectionDetails connectionDetails = getConnectionDetails(connectName.getParameterValue());
				if (connectionDetails == null )   throw new Exception("Connection not found");
				logger.info("Got connection");
				//Get Documentum Session 
				cd.setRepoName(connectionDetails.getDataSource());
				cd.setUserPsw(connectionDetails.getUserPsw());
				cd.setUsrName(connectionDetails.getUserName());
				cd.connectRepository();
				if ( cd.getDocumemtumSession() == null )  throw new Exception("Could not connect to documentum  ");
				logger.info("Initialized repository session ");
				//Get DQL
				PhasesDetailsStringH dqlPh=  (PhasesDetailsStringH) phasesDetails.getPhaseDetailsString().get("InitDQL") ;
				setInitDQL(dqlPh.getParameterValue());
				logger.info("Set DQL ");
				//Set Record Type 
				PhasesDetailsStringH recordTypePh=  (PhasesDetailsStringH) phasesDetails.getPhaseDetailsString().get("SourceObject") ;
				setRecordType(packageName+ recordTypePh.getParameterValue()) ;
				logger.info("Set recrdd type ");
				//Setting next thread count
				if(!isLastSequence){
					 nextThreadCount =    nextSdm.getThreadCount();
				}
				logger.info("nextThreadCount"+nextThreadCount);
				logger.info("getSequenceNumber()"+getSequenceNumber());
			 
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
	 
	public void executeOperation() throws Exception{
		String methodName="executeOperation";
		logger.info("Inside Method"+methodName);
		idfQuery.setDQL(getInitDQL());
		idfCollection = idfQuery.execute(cd.getDocumemtumSession(),DfQuery.DF_EXEC_QUERY);
		
		rh.setTypeOfRecord(getRecordType());
		rh.setHc(getHc());
		rh.initOperation();
		rh.startBatchTransaction();
		HashMap<String,String> listOfStringAtrributes = new HashMap<String, String>() ;
		HashMap<String,Integer> listOfIntAttributes  = new HashMap<String, Integer>() ;
		HashMap<String,Date> listOfDateAttributes  = new HashMap<String, Date>() ;
		HashMap<String,Long> listOfLongAtrributes  = new HashMap<String, Long>() ;
		Record record = new Record();
		int nextProcessId = 0;
		int totalProcessCount =0;
		int processCount = 0;
		while(idfCollection.next()){
			//rh.startDataTransaction();
			processCount = processCount +1 ;
			totalProcessCount = totalProcessCount + 1;
			try{
				//Initialize record
				record.setTypeOfRecord(getRecordType());
				//Extract primary keys and set it in the Records object
				listOfStringAtrributes.clear();
				listOfIntAttributes.clear();
				listOfDateAttributes.clear();
				listOfLongAtrributes.clear();
				for(int i=0; i <rh.getColumnNameListPK().size();i++){
					 if(rh.getColumnType(rh.getColumnNameListPK().get(i)).equals("string")){
						 listOfStringAtrributes.put(rh.getColumnName(rh.getColumnNameListPK().get(i)), idfCollection.getString(rh.getColumnName(rh.getColumnNameListPK().get(i))) );
					 }else if (rh.getColumnType(rh.getColumnNameListPK().get(i)).equals("integer")){
						 listOfIntAttributes.put(rh.getColumnName(rh.getColumnNameListPK().get(i)), idfCollection.getInt(rh.getColumnName(rh.getColumnNameListPK().get(i))) );
					 }else if (rh.getColumnType(rh.getColumnNameListPK().get(i)).equals("date")){
						 listOfDateAttributes.put(rh.getColumnName(rh.getColumnNameListPK().get(i)), idfCollection.getTime(rh.getColumnName(rh.getColumnNameListPK().get(i))).getDate() );
					 }else if (rh.getColumnType(rh.getColumnNameListPK().get(i)).equals("long")){
						 listOfLongAtrributes.put(rh.getColumnName(rh.getColumnNameListPK().get(i)), idfCollection.getLong(rh.getColumnName(rh.getColumnNameListPK().get(i)))  );
					 }
				}
				record.setSequenceName(sequenceName);
				
				if(!isLastSequence){
					record.setSequenceNumber(sequenceNumber+1);
					if(nextProcessId < ( nextThreadCount -1 )   ){
						nextProcessId = nextProcessId +1 ;
						
					}else{
						nextProcessId = 0 ;
					}
					record.setProcessId(nextProcessId);
				}else{
					record.setStatusOfRecord("SUCCESS");
				}
				Date today = new Date();
				record.setCreateDate(today);
				record.setModifyDate(today);
				record.setListOfDateAttributes(listOfDateAttributes);
				record.setListOfIntAttributes(listOfIntAttributes);
				record.setListOfLongAtrributes(listOfLongAtrributes);
				record.setListOfStringAtrributes(listOfStringAtrributes);
				//save the record 
				//set next sequence number
				//set sequence details
				rh.setRecord(record);
				logger.info("nextProcessId ::"+record.getProcessId());
				logger.info("isLastSequence ::"+record.getSequenceNumber());
				rh.saveRecordPK();
				bh.addSuccessCount(1);
				bh.saveBatch();
				if(commitCount==processCount){
					rh.batchCommit();
					processCount = 0;
					rh.startBatchTransaction();
				}
				if(totalProcessCount==batchCount){
					break;
				}
			}
			catch(Exception e){
				//If batch fails to process any record then it will be logged in the error 
				bh.addFailureCount(1);
				bh.saveBatch();
			}
		}
		bh.exitBatch("");
		rh.finalCommit();
		rh.closeConnection();
		idfCollection.close();
		logger.info("Exiting Method"+methodName);
	}
	 
	public String getInitDQL() {
		return initDQL;
	}
	public void setInitDQL(String initDQL) {
		this.initDQL = initDQL;
	} 
	public DocumentumConnection getCd() {
		return cd;
	}
	public void setCd(DocumentumConnection cd) {
		this.cd = cd;
	}
	public IDfQuery getIdfQuery() {
		return idfQuery;
	}
	public void setIdfQuery(IDfQuery idfQuery) {
		this.idfQuery = idfQuery;
	}
	public IDfCollection getIdfCollection() {
		return idfCollection;
	}
	public void setIdfCollection(IDfCollection idfCollection) {
		this.idfCollection = idfCollection;
	}
	public int getCommitCount() {
		return commitCount;
	}
	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}
	 public RecordHandler getRecordsHandler() {
			return rh;
	}
		public void setRecordsHandler(RecordHandler recordsHandler) {
			this.rh = recordsHandler;
	}
		public String getRecordType() {
			return recordType;
		}
		public void setRecordType(String recordType) {
			this.recordType = recordType;
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
		public HibernateConnection getHc() {
			return hc;
		}
		public void setHc(HibernateConnection hc) {
			this.hc = hc;
		}
		public int getBatchCount() {
			return batchCount;
		}
		public void setBatchCount(int batchCount) {
			this.batchCount = batchCount;
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
			logger.info("Inside getPhaseDetails ");
			List phaseList = hc.getObject("from PhasesDetailsH where phaseName='"+phaseName+"'"); 
			PhasesDetailsH phasesDetails = new PhasesDetailsH()  ;
			for(int i=0;i<phaseList.size() ;i++){
				phasesDetails = (PhasesDetailsH) phaseList.get(i) ;
			}
			logger.info("Existing getPhaseDetails ");
			return phasesDetails;
		}
		public ConnectionDetails getConnectionDetails(String connectionName){
			List connectionList = hc.getObject("from ConnectionDetails where connectionName='"+connectionName+"'"); 
			ConnectionDetails connectionDetails = new ConnectionDetails()  ;
			for(int i=0;i<connectionList.size() ;i++){
				connectionDetails = (ConnectionDetails) connectionList.get(i) ;
			}
			return connectionDetails;
		}
		public boolean isLastSequence() {
			return isLastSequence;
		}
		public void setLastSequence(boolean isLastSequence) {
			this.isLastSequence = isLastSequence;
		}
		public int getNextThreadCount() {
			return nextThreadCount;
		}
		public void setNextThreadCount(int nextThreadCount) {
			this.nextThreadCount = nextThreadCount;
		}
		
}
