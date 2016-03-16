package com.managetransfer.documetum;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.documentum.bpm.IDfWorkitemEx;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfACL;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfDocument;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfFormat;
import com.documentum.fc.client.IDfProcess;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfQueueItem;
import com.documentum.fc.client.IDfWorkflow;
import com.documentum.fc.common.DfId;
import com.documentum.operations.IDfExportNode;
import com.documentum.operations.IDfExportOperation;
import com.documentum.operations.IDfFile;
import com.documentum.operations.IDfImportNode;
import com.documentum.operations.IDfImportOperation;
import com.managetransfer.batches.BatchHandler;
import com.managetransfer.businessobject.DocumentumACL;
import com.managetransfer.businessobject.DocumentumACLDetails;
import com.managetransfer.businessobject.ProcessData;
import com.managetransfer.businessobject.ProcessDataPackage;
import com.managetransfer.client.ConnectionDetails;
import com.managetransfer.client.ObjectDetails;
import com.managetransfer.client.PhasesDetailsH;
import com.managetransfer.client.PhasesDetailsIntH;
import com.managetransfer.client.PhasesDetailsStringH;
import com.managetransfer.client.ProcessDependancy;
import com.managetransfer.client.SequenceDetailsH;
import com.managetransfer.client.SequenceDetailsMapH;
import com.managetransfer.hibernate.HibernateConnection;
import com.managetransfer.record.Record;
import com.managetransfer.record.RecordHandler;

/* Input Parameters 
 *  	Sequence Name
 *  	Sequence Number
 *  	Process Id
 *  	DQL To select ACLs Attributes		--ExportQuery
 *  	Export Location					--ExportLocation
 *  	Repository Connection Details	--Connection
 *     Commit Count
 *     Batch Count
 *     DQL To Extract Repeating Attributes --RepeatingAttributeDQL 
 */
public class ExportProcessD6 {
	/**
	 * This job takes care of extraction of process information from D6 repository
	 * and storing it in the DocumemntumACL and
	 */

	private DocumentumConnection cd = new DocumentumConnection();
	Boolean interruptFlag = false;

	private String DQLDrivingCursor = new String(
			"select r_object_id ,object_name,owner_name,acl_class,globally_managed from dm_acl where object_name like 'sigi%'");
	private RecordHandler rh = new RecordHandler();
	private String recordType = new String("ProcessData");
	private String packageName = new String(
			"com.managetransfer.businessobject.");
	private IDfQuery idfQuery = new DfQuery();
	private IDfCollection idfCollection = null;
	private int commitCount = 1000;
	private int batchCount = 10000;
	private int nextThreadCount = 1;
	private String sequenceName = new String("");
	private int sequenceNumber = 0;
	private int processId = 0;
	private BatchHandler bh = new BatchHandler();
	private boolean isLastSequence = false;
	final Logger logger = Logger.getLogger(ExportProcessD6.class.getName());
	private HibernateConnection hc = new HibernateConnection();
	private ArrayList<String> repeatingAttributeList = new ArrayList<String>();
	HashMap<String,RecordHandler> listOfRecordHandlers = new HashMap<String, RecordHandler>() ;
	
	public static void main(String[] args) throws Exception {
		ExportProcessD6 ed = new ExportProcessD6();
		ed.initOperation();
		ed.executeOperation();
		ed.existOperation();
	}

	private void existOperation() {

	}

	public void executeOperation() throws Exception {
		String methodName = "executeOperation";
		logger.info("Inside Method" + methodName);
		ProcessData object = new ProcessData();
		idfQuery.setDQL(getDQLDrivingCursor());
		logger.info("DQL---------" + idfQuery.getDQL());
		idfCollection = idfQuery.execute(cd.getDocumemtumSession(),
				DfQuery.DF_EXEC_QUERY);
		rh.startBatchTransaction();
		int processCount = 0;
		Record record = new Record();
		record.setTypeOfRecord(getRecordType());
		int nextProcessId = 0;
		int totalProcessCount = 0;
		Date createDate = new Date();
		String taskId = new String("");
		String workflowId = new String("");
		while (idfCollection.next()) {
			try {
				if (totalProcessCount >= batchCount || getInterruptFlag()) {
					logger.info("Exiting bactch "+totalProcessCount+batchCount+getInterruptFlag());
					break;
				}
				logger.info("Start Processing New Record---------"+totalProcessCount);
				if (processCount >=commitCount ) {
					rh.commitBatchTransaction();
					processCount = 0;
					rh.startBatchTransaction();
					cd.clearCache();
				}

				totalProcessCount = totalProcessCount + 1;
				processCount = processCount + 1;
				record = new Record();
				record.setErrorDetails("");
				rh= getRecordHandler(packageName+"ProcessData");
				taskId = idfCollection.getString("task_id");
				//Get activity instance 
				object= new ProcessData();
				IDfWorkitemEx  idfX = (IDfWorkitemEx ) cd.getDocumemtumSession().getObject(new DfId(taskId));
				if (idfX==null) throw new Exception("Activitiy id not found "+taskId);
				IDfQueueItem idfQueueItem = (IDfQueueItem) cd.getDocumemtumSession().getObject(idfX.getQueueItemId());
				if (idfQueueItem==null) throw new Exception("idfQueueItem id not found "+taskId);
				IDfWorkflow idfWorkflow  =(IDfWorkflow)cd.getDocumemtumSession().getObject(idfX.getWorkflowId());
				if (idfWorkflow==null) throw new Exception("idfWorkflow id not found "+taskId);
				IDfProcess idfProcess = (IDfProcess)cd.getDocumemtumSession().getObject(new DfId(idfWorkflow.getString("process_id")));
				if (idfProcess==null) throw new Exception("idfProcess id not found "+taskId);
				//Extract Process data
				//Extract Process Instance data
				//Extract Activity Data
				object.setTaskId(taskId);
				object.setTaskName(idfQueueItem.getTaskName());
				object.setActivityReceiptDate(idfQueueItem.getDateSent().getDate());
				object.setActivityName(idfX.getActivity().getObjectName());
				object.setActivityState(""+idfX.getRuntimeState());
				object.setActivityCreateDate(idfX.getCreationDate().getDate());
				object.setPerformerName(idfX.getPerformerName());
				object.setProcessName(idfProcess.getObjectName());
				object.setWorkflowName(idfWorkflow.getObjectName());
				object.setWorkflowStartDate(idfWorkflow.getStartDate().getDate());
				object.setWorkflowSupervisor(idfWorkflow.getString("supervisor_name"));
				object.setWorkflowState(""+idfWorkflow.getRuntimeState());
				object.setInstructions(idfWorkflow.getString("instructions"));
				logger.info("Extraced Process Level inforamtion");
				
				List pdList = getProcessDependany(object.getProcessName());
				//Get list of dependent variables and SDTs
				for(int pd=0; pd < pdList.size();pd++ ){
					ProcessDependancy  processDependancy  =(ProcessDependancy) pdList.get(pd);
					HashMap<String,String> listOfStringAtrributes = new HashMap<String, String>() ;
					HashMap<String,Integer> listOfIntAttributes  = new HashMap<String, Integer>() ;
					HashMap<String,Date> listOfDateAttributes  = new HashMap<String, Date>() ;
					HashMap<String,Long> listOfLongAtrributes  = new HashMap<String, Long>() ;
					HashMap<String,Boolean> listOfBooleanAttributes  = new HashMap<String, Boolean>() ;
					logger.info("processDependancy.getDependancyType()"+processDependancy.getDependancyType());
					logger.info("processDependancy.getDependancyName()"+processDependancy.getDependancyName());
					RecordHandler rhDep =   getRecordHandler(packageName+processDependancy.getDependancyName());
					if(processDependancy.getDependancyType().equals("SDT") ){
						for (int j =0 ; j < rhDep.getColumnNameList().size();j++){
							logger.info("Setting SDT");
							if(rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("string")){
								logger.info("Getting value for"+rhDep.getColumnNameList().get(j));
								String val = ( String ) idfX.getStructuredDataTypeAttrValue(processDependancy.getDependancyName(),rhDep.getColumnNameList().get(j));
								listOfStringAtrributes.put(rhDep.getColumnName(rhDep.getColumnNameList().get(j)), val);
							 }else if (rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("integer")||rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("int")){
								 int val =0;
								 if(idfX.getStructuredDataTypeAttrValue(processDependancy.getDependancyName(),rhDep.getColumnNameList().get(j)) !=null)
								 val = ( int ) idfX.getStructuredDataTypeAttrValue(processDependancy.getDependancyName(),rhDep.getColumnNameList().get(j));
								listOfIntAttributes.put(rhDep.getColumnName(rhDep.getColumnNameList().get(j)),val);
							 }else if (rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("date")|| rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("time")|| rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("timestamp")){
								Date val = ( Date ) idfX.getStructuredDataTypeAttrValue(processDependancy.getDependancyName(),rhDep.getColumnNameList().get(j));
								listOfDateAttributes.put(rhDep.getColumnName(rhDep.getColumnNameList().get(j)), val);
							 }else if (rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("long")){
								Long val = ( Long ) idfX.getStructuredDataTypeAttrValue(processDependancy.getDependancyName(),rhDep.getColumnNameList().get(j)); 
								listOfLongAtrributes.put(rhDep.getColumnName(rhDep.getColumnNameList().get(j)), val);
							 }else if (rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("boolean")){
								 Boolean val = false ;
								 if( idfX.getStructuredDataTypeAttrValue(processDependancy.getDependancyName(),rhDep.getColumnNameList().get(j))!=null)
								 val = ( Boolean ) idfX.getStructuredDataTypeAttrValue(processDependancy.getDependancyName(),rhDep.getColumnNameList().get(j));
								 listOfBooleanAttributes.put(rhDep.getColumnName(rhDep.getColumnNameList().get(j)), val);
								 logger.info("boolean value set"+listOfBooleanAttributes.get(rhDep.getColumnName(rhDep.getColumnNameList().get(j))));
							 }
						}
					}else if(processDependancy.getDependancyType().equals("VARIABLE")){
						logger.info("Setting VARIABLE");
						for (int j =0 ; j < rhDep.getColumnNameList().size();j++){
							if(rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("string")){
								String val = "";
								try{
									val = ( String ) idfX.getPrimitiveVariableValue(rhDep.getColumnNameList().get(j));
								}catch(Exception e){
									logger.severe("this exception ignored.since process variable may not exist for old process"+e);
								}
								listOfStringAtrributes.put(rhDep.getColumnName(rhDep.getColumnNameList().get(j)), val);
							 }else if (rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("integer")||rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("int")){
								 int val =0;
								 try{
									 if(idfX.getPrimitiveVariableValue(rhDep.getColumnNameList().get(j))!=null)
									 val = ( int ) idfX.getPrimitiveVariableValue(rhDep.getColumnNameList().get(j));
								 }catch(Exception e){
									logger.severe("this exception ignored.since process variable may not exist for old process"+e);
								 }
								listOfIntAttributes.put(rhDep.getColumnName(rhDep.getColumnNameList().get(j)),val);
							 }else if (rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("date")|| rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("time")|| rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("timestamp")){
								Date val = null;
								try{
									val=( Date ) idfX.getPrimitiveVariableValue(rhDep.getColumnNameList().get(j));
								}catch(Exception e){
									logger.severe("this exception ignored.since process variable may not exist for old process"+e);
								 }
								listOfDateAttributes.put(rhDep.getColumnName(rhDep.getColumnNameList().get(j)), val);
							 }else if (rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("long")){
								Long val  =null; 
								try{
								 val = ( Long ) idfX.getPrimitiveVariableValue(rhDep.getColumnNameList().get(j)); 
								}catch(Exception e){
									logger.severe("this exception ignored.since process variable may not exist for old process"+e);
								}
								listOfLongAtrributes.put(rhDep.getColumnName(rhDep.getColumnNameList().get(j)), val);
							 }else if (rhDep.getColumnType(rhDep.getColumnNameList().get(j)).equals("boolean")){
								 Boolean val = false;
								 try{
								 if( idfX.getPrimitiveVariableValue(rhDep.getColumnNameList().get(j))!=null)
									 val = ( Boolean ) idfX.getPrimitiveVariableValue(rhDep.getColumnNameList().get(j));
								 }catch(Exception e){
										logger.severe("this exception ignored.since process variable may not exist for old process"+e);
								 }
								 listOfBooleanAttributes.put(rhDep.getColumnName(rhDep.getColumnNameList().get(j)), val);
								 logger.info("boolean value set"+listOfBooleanAttributes.get(rhDep.getColumnName(rhDep.getColumnNameList().get(j))));
							 }
						}
					}
					logger.info("Creating new recod");
					listOfStringAtrributes.put("task_id",taskId);
					record = new Record();
					record.setListOfBooleanAttributes(listOfBooleanAttributes);
					record.setListOfDateAttributes(listOfDateAttributes);
					record.setListOfIntAttributes(listOfIntAttributes);
					record.setListOfLongAtrributes(listOfLongAtrributes);
					record.setListOfStringAtrributes(listOfStringAtrributes);
					logger.info("Extracted all values");
					rhDep.createNewRecord(packageName+processDependancy.getDependancyName(), record); 
					listOfStringAtrributes = null;
					listOfIntAttributes  = null;
					listOfDateAttributes  = null;
					listOfLongAtrributes  = null;
					rhDep= null;
				}
				//Extract Package information
				IDfCollection attachmentCollection = idfX.getPackages("");
				int pdpCount = 0;
				Map<Integer, ProcessDataPackage> pdpMap = new HashMap<Integer, ProcessDataPackage>();
				while(attachmentCollection.next()){
					logger.info("Attachmentnumber"+pdpCount);
					ProcessDataPackage pdp = new ProcessDataPackage();
					pdp.setPackageName(attachmentCollection.getString("r_package_name"));
					pdp.setPackageType(attachmentCollection.getString("r_package_type"));
					pdp.setPackageId(attachmentCollection.getString("r_component_id"));
					pdp.setSequenceNumber(pdpCount);
					pdp.setTaskId(taskId);
					pdpMap.put(pdpCount, pdp);
					pdpCount=pdpCount+1;
					pdp=null;
				}
				attachmentCollection.close();
				object.setProcessDataPackageMap(pdpMap);
				//cd.commitRecordLevelDocumentumTransaction();
				//Object Clean up
				record =null;
				pdpMap= null;
				idfX=null;
				idfQueueItem=null;
				idfWorkflow=null;
				idfProcess=null;
				pdList=null;
				attachmentCollection= null;
				// Save Record
				object.setMtSequenceName(sequenceName);
				if (!isLastSequence) {
					object.setMtSequenceNumber(sequenceNumber + 1);
					if (nextProcessId < (nextThreadCount - 1)) {
						nextProcessId = nextProcessId + 1;

					} else {
						nextProcessId = 0;
					}
					object.setMtProcessId(nextProcessId);
				} else {
					object.setMtStatus("SUCCESS");
					object.setMtProcessId(0);
					object.setMtSequenceNumber(sequenceNumber);

				}
				
				logger.info("Got Creation Date" + createDate);
				Date today = new Date();
				object.setMtModifyDate(today);
				object.setMtCreateDate(createDate);
				rh.saveObject(object);
				bh.addSuccessCount(1);
				bh.saveBatch();
				
			} catch (Exception e) {
				logger.severe("Error while processing " + e);
				try {
					//cd.abortRecordLevelDocumentumTransaction();
					rh.getPropertyValuesAll(object);
					rh.getRecord().setSequenceNumber(sequenceNumber);
					rh.getRecord().setErrorDetails(e.getMessage());
					rh.getRecord().setProcessId(getProcessId());
					rh.getRecord().setSequenceName(getSequenceName());
					rh.getRecord().setCreateDate(createDate);
					rh.getRecord().setStatusOfRecord("FAIL");
					rh.getRecord().setModifyDate(new Date());
					rh.saveRecord(object);
					bh.saveBatch();
					bh.addFailureCount(1);

				} catch (Exception ex) {
					logger.severe("Error while saving error " + ex);
				}
			}
		}
		idfCollection.close();
		bh.exitBatch("");
		rh.finalCommit();
		rh.closeConnection();
		logger.info("Existing Method" + methodName);
	}

	public void initOperation() throws Exception {
		String methodName = "initOperation";
		logger.info("Inside Method" + methodName);
		try {

			// Get record types
			// Set destination directory
			// Set DQL to extract attributes ..set object name in the query
			// Set SQL to execute ..set object name in the query
			try {
				// Create a batch recode
				hc.initOperation();
				hc.startBatchLevelTransaction();
				bh.setHc(hc);
				bh.setProcessId(getProcessId());
				bh.setSequenceName(sequenceName);
				bh.setSequenceNumber(sequenceNumber);
				bh.initOperation();
				bh.saveBatch();
				rh.setHc(hc);
				rh.setTypeOfRecord(packageName+"ProcessData");
				rh.initOperation();
				logger.info("Initialized batch connection ");
				// Get Sequence information
				SequenceDetailsH sequenceDetails = null;
				try {
					sequenceDetails = getSequenceDetails(getSequenceName());
					if (sequenceDetails == null)
						throw new Exception("Sequence not found");
					if (getSequenceNumber() == sequenceDetails
							.getNumberOfPhases() - 1) {
						isLastSequence = true;
					}
				} catch (Exception e) {
					throw new Exception("Error in Sequence information"
							+ e.getMessage());
				}
				// Get Phase Information
				PhasesDetailsH phasesDetails = null;
				SequenceDetailsMapH sdm = null;
				SequenceDetailsMapH nextSdm = null;
				// Get Phase Information
				try {
					logger.info(""
							+ sequenceDetails.getSequenceDetailsMap().size());
					Map<Integer, SequenceDetailsMapH> sdmh = sequenceDetails
							.getSequenceDetailsMap();
					Iterator iterator = sdmh.entrySet().iterator();
					int i = 0;
					while (iterator.hasNext()) {
						logger.info("inside Iterator" + i);
						Map.Entry mapEntry = (Map.Entry) iterator.next();
						if (i == getSequenceNumber()) {
							sdm = (SequenceDetailsMapH) mapEntry.getValue();
						}
						if (i == getSequenceNumber() + 1) {
							nextSdm = (SequenceDetailsMapH) mapEntry.getValue();
						}
						i = i + 1;
					}

					if (sdm == null)
						throw new Exception("Sequence Details Map not found");

					phasesDetails = getPhaseDetails(sdm.getPhaseName());
					if (phasesDetails == null)
						throw new Exception("Phases Details Map not found");
					// Set Commit Count
					commitCount = sdm.getCommitCount();
					// set Batch Count
					setBatchCount(sdm.getBatchSize());
					logger.info("batch size"+batchCount);
				} catch (Exception e) {
					throw new Exception("Error in Get Phase Information"
							+ e.getMessage());
				}
				// Get Repository Connection Information
				PhasesDetailsStringH connectName = (PhasesDetailsStringH) phasesDetails
						.getPhaseDetailsString().get("Connection");
				ConnectionDetails connectionDetails = getConnectionDetails(connectName
						.getParameterValue());
				if (connectionDetails == null)
					throw new Exception("Connection not found");

				logger.info("Got connection object");
				// Get Documentum Session
				cd.setRepoName(connectionDetails.getDataSource());
				cd.setUserPsw(connectionDetails.getUserPsw());
				cd.setUsrName(connectionDetails.getUserName());
				cd.connectRepository();
				if (cd.getDocumemtumSession() == null)
					throw new Exception("Could not connect to documentum  ");
				logger.info("Initialized repository session ");
				// DQL to get folders
				PhasesDetailsStringH dqlExtract = (PhasesDetailsStringH) phasesDetails
						.getPhaseDetailsString().get("ExportQuery");
				setDQLDrivingCursor(dqlExtract.getParameterValue());
				logger.info("Got DQL");
				if (!isLastSequence) {
					nextThreadCount = nextSdm.getThreadCount();
				}

				hc.commitBatchLevelTransaction();
			} catch (Exception e) {
				logger.severe("Error in init Operation" + e);
				bh.exitBatch(e.toString());
				hc.commitBatchLevelTransaction();
				throw e;
			}
		} catch (Exception e) {
			logger.severe("Error in init Operation" + e);
			throw e;
		}

		logger.info("Existing Method" + methodName);
	}

	public DocumentumConnection getCd() {
		return cd;
	}

	public void setCd(DocumentumConnection cd) {
		this.cd = cd;
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

	public SequenceDetailsH getSequenceDetails(String sequenceName) {
		List sequenceList = hc
				.getObject("from SequenceDetailsH where sequenceName='"
						+ sequenceName + "'");
		SequenceDetailsH sequenceDetails = null;
		for (int i = 0; i < sequenceList.size(); i++) {
			sequenceDetails = (SequenceDetailsH) sequenceList.get(i);
		}
		return sequenceDetails;
	}

	public PhasesDetailsH getPhaseDetails(String phaseName) {
		List phaseList = hc.getObject("from PhasesDetailsH where phaseName='"
				+ phaseName + "'");
		PhasesDetailsH phasesDetails = new PhasesDetailsH();
		for (int i = 0; i < phaseList.size(); i++) {
			phasesDetails = (PhasesDetailsH) phaseList.get(i);
		}
		return phasesDetails;
	}

	public ConnectionDetails getConnectionDetails(String connectionName) {
		List connectionList = hc
				.getObject("from ConnectionDetails where connectionName='"
						+ connectionName + "'");
		ConnectionDetails connectionDetails = new ConnectionDetails();
		for (int i = 0; i < connectionList.size(); i++) {
			connectionDetails = (ConnectionDetails) connectionList.get(i);
		}
		return connectionDetails;
	}
	public List getProcessDependany(String processName) {
		List processDependacnyList = hc
				.getObject("from ProcessDependancy where processName='"
						+ processName + "'");
		 
		return processDependacnyList;
	}

	public ObjectDetails getObjectDetails(String objectName) {
		List objectDetailsList = hc
				.getObject("from ObjectDetails where objectName='" + objectName
						+ "'");
		ObjectDetails objectDetails = new ObjectDetails();
		for (int i = 0; i < objectDetailsList.size(); i++) {
			objectDetails = (ObjectDetails) objectDetailsList.get(i);
		}
		return objectDetails;
	}

	public String createFolder(String basePath, String folderName) {
		String methodName = "createFolder";
		logger.info("Inside Method" + methodName);
		logger.info("basePath" + basePath);
		logger.info("folderName" + folderName);
		File filePath = new File(basePath + File.separator + folderName);
		filePath.mkdir();

		logger.info("Exist Method" + methodName);
		return basePath + File.separator + folderName;
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

	public void splitBatch() {

	}

	public Boolean getInterruptFlag() {
		return this.interruptFlag;
	}

	public void setInterruptFlag(Boolean interruptFlag) {
		logger.info("Setting interrup flag to" + interruptFlag);
		this.interruptFlag = interruptFlag;
	}

	 

	public ArrayList<String> getRepeatingAttributeList() {
		return repeatingAttributeList;
	}

	public void setRepeatingAttributeList(
			ArrayList<String> repeatingAttributeList) {
		this.repeatingAttributeList = repeatingAttributeList;
	}

	public String getDQLDrivingCursor() {
		return DQLDrivingCursor;
	}

	public void setDQLDrivingCursor(String dQLDrivingCursor) {
		DQLDrivingCursor = dQLDrivingCursor;
	}
	public RecordHandler getRecordHandler(String recordType)throws Exception{
		try{
			if(listOfRecordHandlers.containsKey(recordType)){
				return listOfRecordHandlers.get(recordType);
			}else {
				RecordHandler rhValue =  new RecordHandler();
				rhValue.setHc(hc);
				rhValue.setTypeOfRecord(recordType);
				rhValue.initOperation();
				listOfRecordHandlers.put(recordType, rhValue);
				return listOfRecordHandlers.get(recordType);
			}
		}catch(Exception e){
			logger.info("Error getRecordHandler" + e);
			throw e;
		}
		
	}
}
