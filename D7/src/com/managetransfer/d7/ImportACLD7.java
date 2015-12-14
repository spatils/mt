package com.managetransfer.d7;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;



import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfACL;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfDocument;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfFormat;
import com.documentum.fc.client.IDfPermit;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.IDfList;
import com.documentum.operations.IDfExportNode;
import com.documentum.operations.IDfExportOperation;
import com.documentum.operations.IDfFile;
import com.documentum.operations.IDfImportNode;
import com.documentum.operations.IDfImportOperation;
import com.managetransfer.batches.BatchHandler;
import com.managetransfer.businessobject.DocumentumACL;
import com.managetransfer.businessobject.DocumentumACLDetails;
import com.managetransfer.client.ConnectionDetails;
import com.managetransfer.client.MappingDetailsMapH;
import com.managetransfer.client.ObjectDetails;
import com.managetransfer.client.PhasesDetailsH;
import com.managetransfer.client.PhasesDetailsIntH;
import com.managetransfer.client.PhasesDetailsStringH;
import com.managetransfer.client.SequenceDetailsH;
import com.managetransfer.client.SequenceDetailsMapH;
import com.managetransfer.hibernate.HibernateConnection;
import com.managetransfer.record.Record;
import com.managetransfer.record.RecordHandler;

/* Input Parameters 
 *  	Sequence Name
 *  	Sequence Number
 *  	Process Id
 *  	Repository Connection Details	--Connection
 *      Record Type						--DocumentumACL
 *      Commit Count
 *     Batch Count
 *     DQL To Extract Repeating Attributes --RepeatingAttributeDQL 
 */
public class ImportACLD7 {

	private DocumentumConnection cd = new DocumentumConnection();

	Boolean interruptFlag = false;

	private String destinationFolderPath = new String(
			"D:\\Documentum\\exportdirectory");
	private String DQLToExtractAttributes = new String(
			"select claim_number, claim_number,object_name ,owner_name,acl_name,claimant_name,claim_type,effective_date,adjuster_name,claim_type,document_state,department_type from claims where r_object_id='$r_object_id$'");
	private String DQLToExtractRepeatingAttributes = new String("");
	private String SQLDrivingCursor = new String(
			"from $objectName$ where mtSequenceName='$sequenceName$' and mtSequenceNumber=$sequenceNumber$ and mtProcessId = $processId$ and ( mtStatus is null or mtStatus !='SUCCESS'  ) ");
	private RecordHandler rh = new RecordHandler();
	private String recordType = new String("Claims");
	private String recordTypeR = new String("");
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
	final Logger logger = Logger.getLogger(ImportACLD7.class.getName());
	private HibernateConnection hc = new HibernateConnection();
	private boolean exportFolderPath = false;
	private ArrayList<String> repeatingAttributeList = new ArrayList<String>();

	public static void main(String[] args) throws Exception {
		ImportACLD7 ed = new ImportACLD7();
		ed.initOperation();
		ed.executeOperation();
		ed.existOperation();
	}

	private void existOperation() {
		// Get next Phase and process details
		// Divide batch Equally among the processes
		// perform above tasks
	}

	public void executeOperation()   throws Exception  {
		String methodName="executeOperation";
		logger.info("Inside Method"+methodName);
		cd.beginBatchLevelDocumentumTransaction();
		rh.startBatchTransaction();
		logger.info(" is sessionvalid "+hc.isSessionValid());
		int processCount = 0;
		Record record = new Record();
		DocumentumACL object = new DocumentumACL() ;
		List objectList = rh.getObject(getSQLDrivingCursor());
		record.setTypeOfRecord(getRecordType());
		int nextProcessId = 0;
		int totalProcessCount = 0;
		Date   createDate = new Date();
		for(int t=0; t<  objectList.size() ; t++){
			try{
				logger.info("Start Processing New Record---------"+t);
				logger.info(" is sessionvalid "+hc.isSessionValid());
				if(totalProcessCount >= batchCount || getInterruptFlag()  ){
					break;
				}
				cd.beginRecordLevelDocumentumTransaction();
				totalProcessCount = totalProcessCount +1;
				processCount = processCount +1 ;
				record = new Record();
				record.setErrorDetails("");;
				object = (DocumentumACL)objectList.get(t);
				logger.info("Extracted object ");
				logger.info(" is sessionvalid "+hc.isSessionValid());
				logger.info(" Object Id  "+object.getObjectId());
				Map<Integer,DocumentumACLDetails> sdmh = new  HashMap();
				logger.info(" Getting acl details for subsequent ACLs  "+object.getObjectId());
				sdmh = getACLMapDetails (object.getObjectId());
				logger.info(" Extracted acl details"+sdmh.size());
				//Create a New ACL object
				IDfACL idfACL = cd.getDocumemtumSession().getACL(object.getOwner(),object.getAclName() );
				if( null == idfACL ) {
					idfACL = (IDfACL) cd.getDocumemtumSession().newObject("dm_acl");
				}else{
					//If ACL already exist in the repository then remove existing permission
					IDfList permissionList = idfACL.getPermissions();
					for (int p = 0; p < permissionList.getCount(); p++) {
						idfACL.revokePermit((IDfPermit)permissionList.get(p));
					}
				}
				idfACL.setDomain(object.getOwner());
				idfACL.setObjectName(object.getAclName());
				idfACL.setACLClass(Integer.parseInt(object.getAclClass()));
				idfACL.setBoolean("globally_managed",object.isIsInternal() );
				logger.info("Created ACL object ");
				//Setting permissions on the ACL object
				logger.info("Before Setting Permission on the ACL ");
				logger.info(" is sessionvalid "+hc.isSessionValid()); 
				
				for(int q=0;q< sdmh.size();q++){
					DocumentumACLDetails dad = sdmh.get(q);
					idfACL.grant(dad.getAccessorName(), Integer.parseInt(dad.getBasicPermission()), dad.getExtndPermission());
				}
				idfACL.save();
				cd.commitRecordLevelDocumentumTransaction();
				//Save Record
				rh.setRecord(new Record());
				rh.getPropertyValuesAll(object);
				rh.getRecord().setSequenceName(sequenceName);
				if(!isLastSequence){
					rh.getRecord().setSequenceNumber(sequenceNumber+1);
					if(nextProcessId < ( nextThreadCount -1 )   ){
						nextProcessId = nextProcessId +1 ;

					}else{
						nextProcessId = 0 ;
					}
					rh.getRecord().setProcessId(nextProcessId);
				}else{
					rh.getRecord().setStatusOfRecord("SUCCESS");
					rh.getRecord().setProcessId(0);
					rh.getRecord().setSequenceNumber(sequenceNumber);

				}
				
				
				Date today = new Date();
				rh.getRecord().setModifyDate(today);
				logger.info(" setting modify date "+rh.getRecord().getModifyDate());
				rh.getRecord().setCreateDate(createDate);
				rh.getRecord().setErrorDetails("");
				rh.saveRecord(object);
				bh.addSuccessCount(1);
				bh.saveBatch();
				if(processCount>=commitCount){
					rh.commitBatchTransaction();
					cd.commitBatchLevelDocumentumTransaction();
					processCount = 0;
					rh.startBatchTransaction();
					cd.beginBatchLevelDocumentumTransaction();

				}
				
			}
			catch (Exception e){
				logger.severe("Error while processing "+e);
				try{
					cd.abortRecordLevelDocumentumTransaction();
					rh.setRecord(new Record());
					rh.getPropertyValuesAll(object);
					rh.getRecord().getPropertyValues(object);
					rh.getRecord().setSequenceNumber(sequenceNumber);
					rh.getRecord().setErrorDetails(e.toString());
					rh.getRecord().setProcessId(getProcessId());
					rh.getRecord().setSequenceName(getSequenceName());
					rh.getRecord().setCreateDate(createDate);
					rh.getRecord().setStatusOfRecord("FAIL");
					rh.getRecord().setModifyDate(new Date());
					rh.saveRecord(objectList.get(t));
					bh.saveBatch();
					bh.addFailureCount(1);
				}catch(Exception ex){
					logger.severe("Error while saving error "+ex);
				}
			}
		}
		cd.commitBatchLevelDocumentumTransaction();
		bh.exitBatch("");
		rh.finalCommit();
		rh.closeConnection();
		logger.info("Existing Method"+methodName);
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
					logger.info("Commit Count"+commitCount);
					// set Batch Count
					setBatchCount(sdm.getBatchSize());
					logger.info("Batch Count"+batchCount);
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

				rh.setHc(hc);
				// Get Source object name

				setRecordType(packageName + "DocumentumACL");
				rh.setTypeOfRecord(getRecordType());
				rh.initOperation();

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

	public String getDestinationFolderPath() {
		return destinationFolderPath;
	}

	public void setDestinationFolderPath(String destinationFolderPath) {
		this.destinationFolderPath = destinationFolderPath;
	}

	public String getDQLToExtractAttributes() {
		return DQLToExtractAttributes;
	}

	public void setDQLToExtractAttributes(String dQLToExtractAttributes) {
		DQLToExtractAttributes = dQLToExtractAttributes;
	}

	public String getSQLDrivingCursor() {
		String dSelectCountHQL = SQLDrivingCursor.replace("$objectName$",
				getRecordType());
		dSelectCountHQL = dSelectCountHQL.replace("$sequenceName$",
				getSequenceName());
		dSelectCountHQL = dSelectCountHQL.replace("$sequenceNumber$", ""
				+ getSequenceNumber());
		dSelectCountHQL = dSelectCountHQL.replace("$processId$", ""
				+ getProcessId());
		return dSelectCountHQL;
	}

	public void setSQLDrivingCursor(String sQLDrivingCursor) {
		SQLDrivingCursor = sQLDrivingCursor;
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
	  
	public Map<Integer,DocumentumACLDetails> getACLMapDetails(String objectId) {
		logger.info("Inside getACLMapDetails");
		Map<Integer,DocumentumACLDetails> amd = new HashMap();
		List aclDetailsList = hc.getObjectNonCursorQuery("from DocumentumACLDetails where objectId='"
						+ objectId + "'");
		logger.info("Executed Query");
		for (int i = 0; i < aclDetailsList.size(); i++) {
			DocumentumACLDetails dacld=(DocumentumACLDetails) aclDetailsList.get(i);
			logger.info("Gettting each object"+dacld.getObjectId());
			amd.put(i,dacld  ) ;
		}
		return amd;
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

	public String getRecordTypeR() {
		return recordTypeR;
	}

	public void setRecordTypeR(String recordTypeR) {
		this.recordTypeR = recordTypeR;
	}

	public String getDQLToExtractRepeatingAttributes() {
		return DQLToExtractRepeatingAttributes;
	}

	public void setDQLToExtractRepeatingAttributes(
			String dQLToExtractRepeatingAttributes) {
		DQLToExtractRepeatingAttributes = dQLToExtractRepeatingAttributes;
	}

	public ArrayList<String> getRepeatingAttributeList() {
		return repeatingAttributeList;
	}

	public void setRepeatingAttributeList(
			ArrayList<String> repeatingAttributeList) {
		this.repeatingAttributeList = repeatingAttributeList;
	}

}
