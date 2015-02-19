package com.managetransfer.sharepoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.axiom.om.OMNode;
import org.apache.axis2.client.Stub;




import com.managetransfer.batches.BatchHandler;
import com.managetransfer.client.ConnectionDetails;
import com.managetransfer.client.PhasesDetailsH;
import com.managetransfer.client.PhasesDetailsIntH;
import com.managetransfer.client.PhasesDetailsStringH;
import com.managetransfer.client.SequenceDetailsH;
import com.managetransfer.client.SequenceDetailsMapH;
import com.managetransfer.hibernate.HibernateConnection;
import com.managetransfer.record.RecordHandler;

public class SharePointImport {
	/*
	 * Input Parameters Sequence Name Sequence Number Process Id Commit Count
	 * Batch Count Connection --- Connection ( User Id , PSW, Domain, PORT,
	 * SharePointDestination, url ) Record Type --- TargetObject CreateFolder
	 * --- CreateFolder 1 0r 0
	 */
	private Boolean interruptFlag =false;
	public Boolean getInterruptFlag() {
		return interruptFlag;
	}

	public void setInterruptFlag(Boolean interruptFlag) {
		this.interruptFlag = interruptFlag;
	}

	private String SQLDrivingCursor = new String(
			"from $objectName$ where mtSequenceName='$sequenceName$' and mtSequenceNumber=$sequenceNumber$ and mtProcessId = $processId$");
    private String packageName = new String("com.managetransfer.businessobject.");
	private String recordType = new String("Information");
	private RecordHandler rh = new RecordHandler();
	private ArrayList<String> allColumns = new ArrayList<String>();
	private String sequenceName = new String("seq1");
	private int sequenceNumber = 3;
	private int processId = 0;
	private int batchCount = 10000;
	private int commitCount = 1000;
	private int nextThreadCount = 1;
	final Logger logger = Logger.getLogger(SharePointImport.class.getName());
	private BatchHandler bh = new BatchHandler();
	private boolean isLastSequence = false;
	private HibernateConnection hc = new HibernateConnection();
	private SharePointConnection sharePointConnection = new SharePointConnection();
	private boolean exportFolderPath = false;

	public static void main(String[] args) throws Exception {
		
		SharePointImport shi = new SharePointImport();
		shi.initOperation();
		System.out.println(shi.sharePointConnection.getPsw());
		System.out.println(shi.sharePointConnection.getMyDomain());
		System.out.println(shi.sharePointConnection.getMyHost());
		System.out.println(shi.sharePointConnection.getMyPort());
		//shi.sharePointConnection.setMyDomain("india");
		//shi.sharePointConnection.setMyPort(6622);
		shi.executeOperation();

	}

	public void initOperation() throws Exception {
		String methodName = "initOperation";
		logger.info("Inside Method" + methodName);
		try {
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
				logger.info("Got Seq" );
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
				
				logger.info("Got Phase" );
				//Setting next thread count
				if(!isLastSequence){
					nextThreadCount =   nextSdm.getThreadCount();
				}
				// Get Repository Connection Information
				PhasesDetailsStringH connectionPh  = (PhasesDetailsStringH)  phasesDetails.getPhaseDetailsString().get("Connection");
				ConnectionDetails connectionDetails = getConnectionDetails(connectionPh.getParameterValue()); 
				if(connectionDetails == null)  throw new Exception("SharePoint Connection Information Not Found");
				// Get SharePoint Session
				PhasesDetailsStringH destinationPathPh = (PhasesDetailsStringH) phasesDetails.getPhaseDetailsString().get("DestinationPath");
				sharePointConnection.setDocUriSharePointDest(destinationPathPh.getParameterValue() );
				sharePointConnection.setPsw(connectionDetails.getUserPsw());
				sharePointConnection.setUserName(connectionDetails.getUserName());
				sharePointConnection.setMyDomain(connectionDetails.getDomain());
				sharePointConnection.setWebserviceURLBase(connectionDetails.getUrl());
				sharePointConnection.setMyHost(connectionDetails.getDataSource());
				sharePointConnection.setMyPort(Integer.parseInt(connectionDetails.getPort()));
				 
				rh.setHc(hc);
				PhasesDetailsStringH targetObjectPh  = (PhasesDetailsStringH)  phasesDetails.getPhaseDetailsString().get("TargetObject");
				setRecordType(packageName +targetObjectPh.getParameterValue());
				rh.setTypeOfRecord(getRecordType());
				rh.initOperation();
				if (!isLastSequence) {
					nextThreadCount = nextSdm.getThreadCount();
				}
				getAllColumns().addAll(rh.getColumnNameList());
				getAllColumns().addAll(rh.getColumnNameListPK());
				//Get export folder location option
				PhasesDetailsIntH createFolder  =( PhasesDetailsIntH )phasesDetails.getPhaseDetailsInt().get("CreateFolder");
				if ( createFolder.getParameterValue() ==1 ){
					exportFolderPath = true;
				}
				hc.commitBatchLevelTransaction();
			} catch (Exception e) {
				logger.info("Error in init Operation" + e);
				bh.exitBatch(e.toString());
				hc.commitBatchLevelTransaction();
			}
		} catch (Exception e) {
			logger.info("Error in init Operation" + e);
		}

		logger.info("Existing Method" + methodName);

	}

	private void existOperation() {
		// Get next Phase and process details
		// Divide batch Equally among the processes

	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public RecordHandler getRh() {
		return rh;
	}

	public void setRh(RecordHandler rh) {
		this.rh = rh;
	}

	public int getCommitCount() {
		return commitCount;
	}

	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}

	public int getNextThreadCount() {
		return nextThreadCount;
	}

	public void setNextThreadCount(int nextThreadCount) {
		this.nextThreadCount = nextThreadCount;
	}

	public HibernateConnection getHc() {
		return hc;
	}

	public void setHc(HibernateConnection hc) {
		this.hc = hc;
	}

	public void executeOperation() throws Exception {
		String methodName="executeOperation";
		logger.info("Inside Method"+methodName);
		logger.info("SharePoint Init Connection : ");
		sharePointConnection.initOperation();
		logger.info("Set record type");
		sharePointConnection.setRecordType(getRecordType());
		List objectList = rh.getObject(getDrivingCursor());
		Object object = new Object();
		Date createDateSource  = new Date();
		int nextProcessId = 0;
		int processCount = 0;
		int totalProcessCount = 0;
		rh.startBatchTransaction();
		for (int i = 0; i < objectList.size(); i++) {
			totalProcessCount = totalProcessCount + 1 ;
			try {
				if(  interruptFlag || totalProcessCount >= batchCount){
					break; 
				}
				// Value of each record object is extracted
				logger.info("Value of each record object is extracted: ");
				object = objectList.get(i);
				rh.getPropertyValuesAll(object);
				createDateSource  = (Date) rh.getSpecificAttributeValue(object, "mtCreateDate")  ;
				//Create folder Path
				String repositoryPath = new String("");
				if(exportFolderPath){
					repositoryPath = (String)rh.getSpecificAttributeValue(object, "mtRepositoryPath")  ;
					sharePointConnection.createFolderStructure(repositoryPath);
					sharePointConnection.setRepositoryPath(repositoryPath+"/");
				}
				// All values are passed to SharePoint
				sharePointConnection.setDateAttributes(rh.getRecord().getListOfDateAttributes());
				sharePointConnection.setIntAttributes(rh.getRecord().getListOfIntAttributes());
				sharePointConnection.setLongAttributes(rh.getRecord().getListOfLongAtrributes());
				sharePointConnection.setStringAttributes(rh.getRecord().getListOfStringAtrributes());
				// Get actual column names
				sharePointConnection.setListOfAttributes(rh.getAllColumnNameListDataBase());
				// Document is uploaded
				String importStatus = sharePointConnection.ImportDocument();
				if(!importStatus.equals("Success")) throw new Exception("Document Not imported due to "+importStatus);
				// Document is updated
				String updateStatus = sharePointConnection
						.UpdateDocumentMetadata("");
				if(!updateStatus.equals("Success")) throw new Exception ("Document Metata Not Updated due to "+updateStatus);
				rh.getRecord().setSequenceName(sequenceName);
				if (!isLastSequence) {
					rh.getRecord().setSequenceNumber(sequenceNumber + 1);
					if (nextProcessId < (nextThreadCount - 1)) {
						nextProcessId = nextProcessId + 1;

					} else {
						nextProcessId = 0;
					}
					rh.getRecord().setProcessId(nextProcessId);
				} else {
					rh.getRecord().setSequenceNumber(sequenceNumber );
					rh.getRecord().setStatusOfRecord("SUCCESS");
					rh.getRecord().setProcessId(0);
				}
				Date today = new Date();
				rh.getRecord().setCreateDate(createDateSource);
				rh.getRecord().setModifyDate(today);
				rh.getRecord().setErrorDetails("");
				rh.saveRecord(object);
				processCount = processCount + 1;
				bh.addSuccessCount(1);
				bh.saveBatch();
				if (processCount >= commitCount ) {
					rh.commitBatchTransaction();
					processCount = 0;
					rh.startBatchTransaction();
				}
				
			} catch (Exception e) {
				try {
					rh.getPropertyValuesAll(object);
					rh.getRecord().setCreateDate(createDateSource);
					rh.getRecord().setErrorDetails(e.getMessage());
					rh.saveRecord(object);
					bh.addFailureCount(1);
					bh.saveBatch();
				} catch (Exception ex) {
					logger.severe("ERRROR While saving" + e);
				}

			}

		}
		bh.exitBatch("");
    	hc.commitBatchLevelTransaction();
		hc.closeConnection();
		logger.info("Existing Method"+methodName);
	}

	public ArrayList<String> getAllColumns() {
		return allColumns;
	}

	public void setAllColumns(ArrayList<String> allColumns) {
		this.allColumns = allColumns;
	}

	public String getDrivingCursor() {
		String dSelectCountHQL = SQLDrivingCursor.replace("$objectName$", getRecordType());
		dSelectCountHQL = dSelectCountHQL.replace("$sequenceName$", getSequenceName());
		dSelectCountHQL = dSelectCountHQL.replace("$sequenceNumber$", ""+getSequenceNumber());
		dSelectCountHQL = dSelectCountHQL.replace("$processId$", ""+getProcessId());
		logger.info("Driving cursor : "+dSelectCountHQL);
		return dSelectCountHQL;
	}

	public void setDrivingCursor(String drivingCursor) {
		this.SQLDrivingCursor = drivingCursor;
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
	 
}
