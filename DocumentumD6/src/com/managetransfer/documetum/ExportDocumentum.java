package com.managetransfer.documetum;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.documentum.fc.client.DfQuery;
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
import com.managetransfer.client.ConnectionDetails;
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
 *  	DQL To Extract Attributes		--ExportQuery
 *  	Export Location					--ExportLocation
 *  	Repository Connection Details	--Connection
 *      Record Type						--SourceObject
 *      Commit Count
  *     Batch Count
 */ 
public class ExportDocumentum {
	
	private DocumentumConnection cd = new DocumentumConnection();
	
	private String destinationFolderPath = new String ("D:\\Documentum\\exportdirectory");
	private String DQLToExtractAttributes = new String("select claim_number, claim_number,object_name ,owner_name,acl_name,claimant_name,claim_type,effective_date,adjuster_name,claim_type,document_state,department_type from claims where r_object_id='$r_object_id$'") ;
	private String SQLDrivingCursor  = new String("from $objectName$ where mtSequenceName='$sequenceName$' and mtSequenceNumber=$sequenceNumber$ and mtProcessId = $processId$ and ( mtStatus is null or mtStatus !='SUCCESS'  ) " );
	private RecordHandler rh =   new RecordHandler();
    private String recordType = new String("Claims");
    private String packageName = new String("com.managetransfer.businessobject.");
    private IDfQuery idfQuery = new DfQuery();
    private  IDfCollection idfCollection = null;
    private int commitCount = 1000;
    private int batchCount = 10000;
    private int nextThreadCount = 1 ;
    private String sequenceName = new String("");
    private int sequenceNumber =0;
    private int processId = 0 ;
    private BatchHandler bh = new BatchHandler();
    private boolean isLastSequence = false;
    final Logger logger = Logger.getLogger(ExportDocumentum.class.getName()) ;
    private HibernateConnection hc = new HibernateConnection();
    private boolean exportFolderPath = false;
	public static void main(String[] args)  throws Exception{
		ExportDocumentum ed = new ExportDocumentum();
		ed.initOperation();
		ed.executeOperation();
		ed.existOperation();
	}
	private void existOperation() {
		 //Get next Phase and process details
		 //Divide batch Equally among the processes
		 //perform above tasks 
	}
	public void executeOperation()     {
		String methodName="executeOperation";
		logger.info("Inside Method"+methodName);
		List objectList = rh.getObject(getSQLDrivingCursor());
		rh.startBatchTransaction();
		int processCount = 0;
		Record record = new Record();
		Object object = new Object() ;
		HashMap<String,String> listOfStringAtrributes = new HashMap<String, String>() ;
		HashMap<String,Integer> listOfIntAttributes  = new HashMap<String, Integer>() ;
		HashMap<String,Date> listOfDateAttributes  = new HashMap<String, Date>() ;
		HashMap<String,Long> listOfLongAtrributes  = new HashMap<String, Long>() ;
		record.setTypeOfRecord(getRecordType());
		int nextProcessId = 0;
		int totalProcessCount = 0;
		int errorCount = 0;
		Date   createDate = new Date();
		for(int t=0; t<  objectList.size() ; t++){
			try{
				logger.info("Start Processing New Record---------"+t);
				totalProcessCount = totalProcessCount +1;
				processCount = processCount +1 ;
				record = new Record();
				record.setErrorDetails("");;
				listOfStringAtrributes.clear();
				listOfIntAttributes.clear();
				listOfDateAttributes.clear();
				listOfLongAtrributes.clear();
				//Populate object id in DQL
				object = objectList.get(t);
				String objectId = (String)rh.getSpecificAttributeValuePK(objectList.get(t), "r_object_id");
				logger.info("Extracted object id");
				// Get creation date
				createDate = (Date) rh.getSpecificAttributeValue(objectList.get(t),"mtCreateDate");
				
				idfQuery.setDQL(rh.getModifiedExportDocumentumQuery(DQLToExtractAttributes,object));
				logger.info("DQL---------"+idfQuery.getDQL());
				idfCollection = idfQuery.execute(cd.getDocumemtumSession(),DfQuery.DF_EXEC_QUERY);
				while(idfCollection.next()){
					for (int j =0 ; j < rh.getColumnNameList().size();j++){
						//Extract value from DQL 
						
						if (idfCollection.hasAttr(rh.getColumnName(rh.getColumnNameList().get(j)))){
							
							if(rh.getColumnType(rh.getColumnNameList().get(j)).equals("string")){
								listOfStringAtrributes.put(rh.getColumnName(rh.getColumnNameList().get(j)), idfCollection.getString(rh.getColumnName(rh.getColumnNameList().get(j))) );
							 }else if (rh.getColumnType(rh.getColumnNameList().get(j)).equals("integer")){
								listOfIntAttributes.put(rh.getColumnName(rh.getColumnNameList().get(j)), idfCollection.getInt(rh.getColumnName(rh.getColumnNameList().get(j))) );
							 }else if (rh.getColumnType(rh.getColumnNameList().get(j)).equals("date")){
								listOfDateAttributes.put(rh.getColumnName(rh.getColumnNameList().get(j)), idfCollection.getTime(rh.getColumnName(rh.getColumnNameList().get(j))).getDate() );
							 }else if (rh.getColumnType(rh.getColumnNameList().get(j)).equals("long")){
								listOfLongAtrributes.put(rh.getColumnName(rh.getColumnNameList().get(j)), idfCollection.getLong(rh.getColumnName(rh.getColumnNameList().get(j)))  );
							 }
						}
						
					}
					logger.info("Extracted data from DQL");
					//perform export operation
					String folderPath = cd.exportDocumentSysObject(objectId,destinationFolderPath);
					logger.info("Exported File");
					//Save folder Path
					listOfStringAtrributes.put("folder_path", folderPath);
					logger.info("Saved Folder Path");
					//Get repository folder path
					if(exportFolderPath){
						listOfStringAtrributes.put("mt_repo_path",cd.getFolderPath(objectId));
					}
					//Save Record 
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
						record.setProcessId(0);
						record.setSequenceNumber(sequenceNumber);
						
					}
					
					logger.info("Got Creation Date"+createDate);
					Date today = new Date();
					record.setModifyDate(today);
					record.setCreateDate(createDate);
					record.setListOfDateAttributes(listOfDateAttributes);
					record.setListOfIntAttributes(listOfIntAttributes);
					record.setListOfLongAtrributes(listOfLongAtrributes);
					record.setListOfStringAtrributes(listOfStringAtrributes);
					rh.setRecord(record);
					rh.saveRecord(object);
					bh.addSuccessCount(1);
					bh.saveBatch();
					if(commitCount==processCount){
						rh.commitBatchTransaction();
						processCount = 0;
						rh.startBatchTransaction();
						
					}
					if(totalProcessCount==batchCount){
						break;
					}
					
					
				}
				idfCollection.close();
			}
			catch (Exception e){
				logger.severe("Error while processing "+e);
				try{
					record.setSequenceNumber(sequenceNumber);
					record.setErrorDetails(e.getMessage());
					record.setProcessId(getProcessId());
					record.setSequenceName(getSequenceName());
					record.setCreateDate(createDate);
					record.setStatusOfRecord("FAIL");
					record.setModifyDate(new Date());
					rh.setRecord(record);
				 	rh.saveRecord( objectList.get(t));
					bh.saveBatch();
					bh.addFailureCount(1);
					
					
				}catch(Exception ex){
					logger.severe("Error while saving error "+ex);
				}
			}
		}
		bh.exitBatch("");
		rh.finalCommit();
		rh.closeConnection();
		logger.info("Existing Method"+methodName);
	}
	public void initOperation() throws Exception{
		String methodName="initOperation";
		logger.info("Inside Method"+methodName);
		try{
			
			//Get record types
			//Set destination directory
			//Set DQL to extract attributes ..set object name in the query
			//Set SQL to execute ..set object name in the query 
			try{
				//Create a batch recode	 
				hc.initOperation();
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
				//Get Repository Connection Information
				PhasesDetailsStringH connectName = (PhasesDetailsStringH) phasesDetails.getPhaseDetailsString().get("Connection");
				ConnectionDetails connectionDetails = getConnectionDetails(connectName.getParameterValue());
				if (connectionDetails == null )   throw new Exception("Connection not found");
				
				logger.info("Got connection object");
				//Get Documentum Session 
				cd.setRepoName(connectionDetails.getDataSource());
				cd.setUserPsw(connectionDetails.getUserPsw());
				cd.setUsrName(connectionDetails.getUserName());
				cd.connectRepository();
				if ( cd.getDocumemtumSession() == null )  throw new Exception("Could not connect to documentum  ");
				logger.info("Initialized repository session ");
				
				
				rh.setHc(hc);
				//Get Source object name 
				PhasesDetailsStringH phaseDetails  =( PhasesDetailsStringH )phasesDetails.getPhaseDetailsString().get("SourceObject");
				setRecordType( packageName+phaseDetails.getParameterValue());
				rh.setTypeOfRecord(getRecordType());
				rh.initOperation();
				
				//Destination folder
				PhasesDetailsStringH destFolder  =( PhasesDetailsStringH )phasesDetails.getPhaseDetailsString().get("ExportLocation");
				destinationFolderPath = createFolder( destFolder.getParameterValue(),bh.getBatchName());
				//Create a folder if not exist
				PhasesDetailsStringH dqlExtract  =( PhasesDetailsStringH )phasesDetails.getPhaseDetailsString().get("ExportQuery");
				DQLToExtractAttributes = dqlExtract.getParameterValue();
				logger.info("Got DQL");
				if(!isLastSequence){
					nextThreadCount =   nextSdm.getThreadCount();
				}
				
				//Get export folder location option
				PhasesDetailsIntH createFolder  =( PhasesDetailsIntH )phasesDetails.getPhaseDetailsInt().get("CreateFolder");
				if ( createFolder.getParameterValue() ==1 ){
					exportFolderPath = true;
				}
				
				hc.commitBatchLevelTransaction();
			}catch(Exception e){
				logger.severe("Error in init Operation"+e);
				bh.exitBatch(e.toString());
				hc.commitBatchLevelTransaction();
				throw e ;
			}
		}catch (Exception e ){
			logger.severe("Error in init Operation"+e);
			throw  e;
		}
		 
		 logger.info("Existing Method"+methodName);
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
		String dSelectCountHQL = SQLDrivingCursor.replace("$objectName$", getRecordType() );
		dSelectCountHQL = dSelectCountHQL.replace("$sequenceName$", getSequenceName());
		dSelectCountHQL = dSelectCountHQL.replace("$sequenceNumber$", ""+getSequenceNumber());
		dSelectCountHQL = dSelectCountHQL.replace("$processId$", ""+getProcessId());
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
	public ConnectionDetails getConnectionDetails(String connectionName){
		List connectionList = hc.getObject("from ConnectionDetails where connectionName='"+connectionName+"'"); 
		ConnectionDetails connectionDetails = new ConnectionDetails()  ;
		for(int i=0;i<connectionList.size() ;i++){
			connectionDetails = (ConnectionDetails) connectionList.get(i) ;
		}
		return connectionDetails;
	}
	public String createFolder(String basePath , String folderName){
		String methodName="createFolder";
		logger.info("Inside Method"+methodName);
		logger.info("basePath"+basePath);
		logger.info("folderName"+folderName);
		File filePath = new File( basePath+File.separator+folderName);
		filePath.mkdir();
		
		logger.info("Exist Method"+methodName);
		return basePath+File.separator+folderName;
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
		 
	 
}
