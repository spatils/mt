package com.managetransfer.documetum;

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
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.common.DfId;
import com.documentum.operations.IDfExportNode;
import com.documentum.operations.IDfExportOperation;
import com.documentum.operations.IDfFile;
import com.documentum.operations.IDfImportNode;
import com.documentum.operations.IDfImportOperation;
import com.managetransfer.batches.BatchHandler;
import com.managetransfer.businessobject.DocumentumACL;
import com.managetransfer.businessobject.DocumentumACLDetails;
import com.managetransfer.client.ConnectionDetails;
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
 *  	DQL To select ACLs Attributes		--ExportQuery
 *  	Export Location					--ExportLocation
 *  	Repository Connection Details	--Connection
 *      Record Type						--SourceObject
 *      Commit Count
 *     Batch Count
 *     DQL To Extract Repeating Attributes --RepeatingAttributeDQL 
 */
public class ExportACL {
	/**
	 * This job takes care of extraction of ACL inforamtion from D6 repository
	 * and storing it in the DocumemntumACL and
	 */

	private DocumentumConnection cd = new DocumentumConnection();
	Boolean interruptFlag = false;

	private String destinationFolderPath = new String(
			"D:\\Documentum\\exportdirectory");
	private String DQLToExtractAttributes = new String(
			"select claim_number, claim_number,object_name ,owner_name,object_name,claimant_name,claim_type,effective_date,adjuster_name,claim_type,document_state,department_type from claims where r_object_id='$r_object_id$'");
	private String DQLToExtractRepeatingAttributes = new String("");
	private String DQLDrivingCursor = new String(
			"select r_object_id ,object_name,owner_name,acl_class,globally_managed from dm_acl where object_name like 'sigi%'");
	private RecordHandler rh = new RecordHandler();
	private RecordHandler rhDetails = new RecordHandler();
	private String recordType = new String("DocumentumACL");
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
	final Logger logger = Logger.getLogger(ExportACL.class.getName());
	private HibernateConnection hc = new HibernateConnection();
	private ArrayList<String> repeatingAttributeList = new ArrayList<String>();

	public static void main(String[] args) throws Exception {
		ExportACL ed = new ExportACL();
		ed.initOperation();
		ed.executeOperation();
		ed.existOperation();
	}

	private void existOperation() {

	}

	public void executeOperation() throws Exception {
		String methodName = "executeOperation";
		logger.info("Inside Method" + methodName);
		DocumentumACL object = new DocumentumACL();
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
		while (idfCollection.next()) {
			try {
				if (totalProcessCount >= batchCount || getInterruptFlag()) {
					break;
				}
				logger.info("Start Processing New Record---------");
				totalProcessCount = totalProcessCount + 1;
				processCount = processCount + 1;
				record = new Record();
				record.setErrorDetails("");

				// Setting ACL Level Properties
				object = new DocumentumACL();
				 
				object.setObjectId(idfCollection.getString("r_object_id"));
				object.setIsInternal(idfCollection.getBoolean("globally_managed"));
				object.setAclName(idfCollection.getString("object_name"));
				object.setOwner(idfCollection.getString("owner_name"));
				object.setAclClass(""+idfCollection.getInt("acl_class"));
				object.setIsInternal( idfCollection.getBoolean("globally_managed"));
				logger.info("Set ACL Level Properties object id");
				
				// Get creation date
				IDfACL idfacl = cd.getDocumemtumSession().getACL(
						idfCollection.getString("owner_name"),idfCollection.getString("object_name") );
				if (idfacl == null)
					throw new Exception("ACL Not found");
				Map<Integer, DocumentumACLDetails> mapDocumentumDetails = new HashMap(
						0);
				for (int p = 0; p < idfacl.getAccessorCount(); p++) {
					DocumentumACLDetails aclDetails = new DocumentumACLDetails();
					aclDetails.setObjectId(object.getObjectId());
					aclDetails.setSequenceNumber(p);
					aclDetails.setAccessorName(idfacl.getAccessorName(p));
					aclDetails.setBasicPermission(""
							+ idfacl.getAccessorPermit(p));
					aclDetails.setExtndPermission(idfacl.getXPermitNames(idfacl
							.getAccessorName(p)));
					mapDocumentumDetails.put(p, aclDetails);
					rhDetails.saveObject(aclDetails);
				}
			 
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
				if (processCount >= commitCount) {
					rh.commitBatchTransaction();
					processCount = 0;
					rh.startBatchTransaction();

				}
				

			} catch (Exception e) {
				logger.severe("Error while processing " + e);
				try {
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
				// This is by default DocumentumACL
				setRecordType(packageName + "DocumentumACL");
				rh.setTypeOfRecord(getRecordType());
				rh.initOperation();
				
				
				rhDetails.setHc(hc);
				rhDetails.setTypeOfRecord(packageName +"DocumentumACLDetails");
				rhDetails.initOperation();

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

	public String getDQLDrivingCursor() {
		return DQLDrivingCursor;
	}

	public void setDQLDrivingCursor(String dQLDrivingCursor) {
		DQLDrivingCursor = dQLDrivingCursor;
	}

}
