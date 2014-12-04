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
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfDocument;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfFormat;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.DfTime;
import com.documentum.fc.common.IDfTime;
import com.documentum.operations.IDfExportNode;
import com.documentum.operations.IDfExportOperation;
import com.documentum.operations.IDfFile;
import com.documentum.operations.IDfImportNode;
import com.documentum.operations.IDfImportOperation;
import com.managetransfer.batches.BatchHandler;
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
 *  	Destination folder				--Destination Path ( if mtRepositoryPath exist then it is given preference)
 *  	Repository Connection Details	--Connection
 *      Record Type						--Destination Object
 *      Commit Count
 *     Batch Count
 *     DQL To Extract Repeating Attributes --RepeatingAttributeDQL 
 */ 
public class ImportDocumentumD7 {

	private DocumentumConnection cd = new DocumentumConnection();

	Boolean interruptFlag =false;

	private String destinationFolderPath = new String ("D:\\Documentum\\exportdirectory");
	private String DQLToExtractAttributes = new String("select claim_number, claim_number,object_name ,owner_name,acl_name,claimant_name,claim_type,effective_date,adjuster_name,claim_type,document_state,department_type from claims where r_object_id='$r_object_id$'") ;
	private String DQLToExtractRepeatingAttributes = new String("") ;
	private String SQLDrivingCursor  = new String("from $objectName$ where mtSequenceName='$sequenceName$' and mtSequenceNumber=$sequenceNumber$ and mtProcessId = $processId$ and ( mtStatus is null or mtStatus !='SUCCESS'  ) " );
	private RecordHandler rh =   new RecordHandler();
	private RecordHandler rhRepating =   new RecordHandler();
	private String recordType = new String("Claims");
	private String recordTypeR = new String("");
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
	final Logger logger = Logger.getLogger(ImportDocumentumD7.class.getName()) ;
	private HibernateConnection hc = new HibernateConnection();
	 
	private ArrayList<String> repeatingAttributeList = new ArrayList<String>();
	private ArrayList<String> attributeListOfObject = new ArrayList<String>();
	private String repositoryObjectName = new String("sigi_document_claim");
	private Boolean createRepositoryFolder = false;
	public static void main(String[] args)  throws Exception{
		ImportDocumentumD7 ed = new ImportDocumentumD7();
		ed.initOperation();
		ed.executeOperation();
		ed.existOperation();
	}
	private void existOperation() {
		//Get next Phase and process details
		//Divide batch Equally among the processes
		//perform above tasks 
	}
	public void executeOperation()  throws Exception   {
		String methodName="executeOperation";
		logger.info("Inside Method"+methodName);
		List objectList = rh.getObject(getSQLDrivingCursor());
		cd.beginBatchLevelDocumentumTransaction();
		rh.startBatchTransaction();
		int processCount = 0;
		Record record = new Record();
		Object object = new Object() ;
		record.setTypeOfRecord(getRecordType());
		int nextProcessId = 0;
		int totalProcessCount = 0;
		Date   createDate = new Date();
		for(int t=0; t<  objectList.size() ; t++){
			try{
				if(totalProcessCount >= batchCount || getInterruptFlag()  ){
					break;
				}
				cd.beginRecordLevelDocumentumTransaction();
				logger.info("Start Processing New Record---------"+t);
				totalProcessCount = totalProcessCount +1;
				processCount = processCount +1 ;
				record = new Record();
				record.setErrorDetails("");;
				 
				object = objectList.get(t);
				// Get creation date
				createDate = (Date) rh.getSpecificAttributeValue(objectList.get(t),"mtCreateDate");
				//Get repository Path 
				String repositoryPath = (String)rh.getSpecificAttributeValue(object, "mtRepositoryPath")  ;
				if(null == repositoryPath) { 
					repositoryPath = getDestinationFolderPath();
				}
				//Create Repository folder
				cd.createFolderByPath(repositoryPath);
				//Get actual file location
				String fileLocation = (String)rh.getSpecificAttributeValue(object, "folderPath")  ;
				//Import file in the repository
				IDfSysObject idfSysObject = cd.createNewObject(getRepositoryObjectName(),fileLocation,repositoryPath);
				logger.info("New object created");
				//Setting single value attribute
				rh.getPropertyValuesAll(objectList.get(t));
				for (int j =0 ; j < getAttributeList().size();j++){
					logger.info("Processing"+getAttributeList().get(j));
						 if(rh.getColumnType(getAttributeList().get(j)).equals("string")){
							 if(rh.getRecord().getListOfStringAtrributes().containsKey(rh.getColumnName(getAttributeList().get(j)))){
								 idfSysObject.setString(rh.getColumnName(getAttributeList().get(j)),rh.getRecord().getListOfStringAtrributes().get(rh.getColumnName(getAttributeList().get(j))));
							 }
						 }else if (rh.getColumnType(getAttributeList().get(j)).equals("integer")){
							 if(rh.getRecord().getListOfIntAttributes().containsKey(rh.getColumnName(getAttributeList().get(j)))){
								 idfSysObject.setInt(rh.getColumnName(getAttributeList().get(j)),rh.getRecord().getListOfIntAttributes().get(rh.getColumnName(getAttributeList().get(j))));
							 }
						 }else if (rh.getColumnType(getAttributeList().get(j)).equals("date")){
							 if(rh.getRecord().getListOfDateAttributes().containsKey(rh.getColumnName(getAttributeList().get(j)))){
								idfSysObject.setTime(rh.getColumnName(getAttributeList().get(j)),new DfTime(rh.getRecord().getListOfDateAttributes().get(rh.getColumnName(getAttributeList().get(j)))));
							 }
						 }else if (rh.getColumnType(getAttributeList().get(j)).equals("long")){
							 
							 if(rh.getRecord().getListOfLongAtrributes().containsKey(rh.getColumnName(getAttributeList().get(j)))){
								 idfSysObject.setDouble(rh.getColumnName(getAttributeList().get(j)),rh.getRecord().getListOfLongAtrributes().get(rh.getColumnName(getAttributeList().get(j))));
							 }
						 }
					
				}
				logger.info("Attributes are set");
				//Setting repeating attributes
				//Get repeating attribute object then passing these values to the 
				logger.info("robjectID of recrd1 "+rh.getRecord().getListOfStringAtrributes().get("r_object_id"));
				ArrayList<Object> repeatingObjectList = getAllRepeatingObject(rh.getRecord());
				for(int s1=0;s1 <repeatingObjectList.size();s1++){
					rhRepating.getPropertyValuesAll(repeatingObjectList.get(s1));
						for (int j =0 ; j < getRepeatingAttributeList().size();j++){
							 if(rhRepating.getColumnType(getRepeatingAttributeList().get(j)).equals("string")){
								 if(rhRepating.getRecord().getListOfStringAtrributes().containsKey(rhRepating.getColumnName(getRepeatingAttributeList().get(j)))){
									 String dataValue =rhRepating.getRecord().getListOfStringAtrributes().get(rhRepating.getColumnName(getRepeatingAttributeList().get(j)));
									 if( null != dataValue && !dataValue.equals("")){
										 idfSysObject.appendString(rhRepating.getColumnName(getRepeatingAttributeList().get(j)),rhRepating.getRecord().getListOfStringAtrributes().get(rhRepating.getColumnName(getRepeatingAttributeList().get(j))));
									 }
								 }
							 }else if (rhRepating.getColumnType(getRepeatingAttributeList().get(j)).equals("integer")){
								 if(rhRepating.getRecord().getListOfIntAttributes().containsKey(rhRepating.getColumnName(getRepeatingAttributeList().get(j)))){
										 idfSysObject.appendInt(rhRepating.getColumnName(getRepeatingAttributeList().get(j)),rhRepating.getRecord().getListOfIntAttributes().get(rhRepating.getColumnName(getRepeatingAttributeList().get(j))));
								 }
							 }else if (rhRepating.getColumnType(getRepeatingAttributeList().get(j)).equals("date")){
								 if(rhRepating.getRecord().getListOfDateAttributes().containsKey(rhRepating.getColumnName(getRepeatingAttributeList().get(j)))){
									Date dateValue = rhRepating.getRecord().getListOfDateAttributes().get(rhRepating.getColumnName(getRepeatingAttributeList().get(j)));
									if(dateValue!=null){
										idfSysObject.appendTime(rhRepating.getColumnName(getRepeatingAttributeList().get(j)),new DfTime(rhRepating.getRecord().getListOfDateAttributes().get(rhRepating.getColumnName(getRepeatingAttributeList().get(j)))));
									}
								 }
							 }else if (rhRepating.getColumnType(getRepeatingAttributeList().get(j)).equals("long")){
								 
								 if(rhRepating.getRecord().getListOfLongAtrributes().containsKey(rhRepating.getColumnName(getRepeatingAttributeList().get(j)))){
									 idfSysObject.appendDouble(rhRepating.getColumnName(getRepeatingAttributeList().get(j)),rhRepating.getRecord().getListOfLongAtrributes().get(rhRepating.getColumnName(getRepeatingAttributeList().get(j))));
								 }
							 }
						
					}
				}
				logger.info("Repeatnig Attributes are set");
				idfSysObject.save();
				rh.getRecord().getListOfStringAtrributes().put("mt_new_object_id", idfSysObject.getObjectId().getId());
				cd.commitRecordLevelDocumentumTransaction();
				//Save Record 
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

				logger.info("Got Creation Date"+createDate);
				Date today = new Date();
				rh.getRecord().setModifyDate(today);
				rh.getRecord().setCreateDate(createDate);
				rh.saveRecord(object);
				bh.addSuccessCount(1);
				bh.saveBatch();
				if(commitCount>=processCount){
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
					rh.getPropertyValuesAll(object);
					rh.getRecord().setSequenceNumber(sequenceNumber);
					rh.getRecord().setErrorDetails(e.getMessage());
					rh.getRecord().setProcessId(getProcessId());
					rh.getRecord().setSequenceName(getSequenceName());
					rh.getRecord().setCreateDate(createDate);
					rh.getRecord().setStatusOfRecord("FAIL");
					rh.getRecord().setModifyDate(new Date());
					rh.saveRecord( objectList.get(t));
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
	public void initOperation() throws Exception{
		String methodName="initOperation";
		logger.info("Inside Method"+methodName);
		try{

			/****Get record types
				 Set destination directory
			 *   Repository object name
			 *   Repeating attribute object name
			 *   Connection information 
			 *   Sequence details
			 *   Batch related parameters
			 **/
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
				//Get Target object name 
				PhasesDetailsStringH phaseDetails  =( PhasesDetailsStringH )phasesDetails.getPhaseDetailsString().get("TargetObject");
				setRecordType( packageName+phaseDetails.getParameterValue());
				rh.setTypeOfRecord(getRecordType());
				rh.initOperation();
				//List of columns are set except primary key
				setAttributeList(rh.getColumnNameList());
				//Clear Framework Related columns from the list 
				clearFrameWorkRelatedColumn();
				//Destination folder
				PhasesDetailsStringH destFolder  =( PhasesDetailsStringH )phasesDetails.getPhaseDetailsString().get("DestinationPath");
				setDestinationFolderPath(destFolder.getParameterValue());

				if(!isLastSequence){
					nextThreadCount =   nextSdm.getThreadCount();
				}

				/**Get object details. These details get 
				 * 1  List of  dependent objects 
				 * 2  Repository name of target object
				 ***/
				ObjectDetails objectDetails = getObjectDetails(phaseDetails.getParameterValue());
				if(objectDetails==null){
					throw new Exception("Object Details not found");
				}else{
					if ( null != objectDetails.getRepeatingObject() && !objectDetails.getRepeatingObject().equals("")){
						setRecordTypeR( packageName+objectDetails.getRepeatingObject());
						
					}
					setRepositoryObjectName(objectDetails.getRepoObjectName());
					logger.info("repository object name "+getRepositoryObjectName());
				}
				rhRepating.setHc(hc);
				rhRepating.setTypeOfRecord(getRecordTypeR());
				rhRepating.initOperation();
				logger.info("Initialized rhRepating");
				//This will get list of all columns except primary key
				setRepeatingAttributeList(rhRepating.getListOfColumns(getRecordTypeR()));
				PhasesDetailsIntH createFolder  =( PhasesDetailsIntH )phasesDetails.getPhaseDetailsInt().get("CreateFolder");
				if ( createFolder.getParameterValue() ==1 ){
					setCreateRepositoryFolder(true);
				}
				logger.info("Initialized the batch");
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

	private void clearFrameWorkRelatedColumn() {
		// This method will remove list of columns on business object which does not exist in the target repository
		ArrayList<String> attributeListMethod = new ArrayList<String>();
		for(int i=0;i<getAttributeList().size();i++){
			if (getAttributeList().get(i).equals("folderPath")|| 
			getAttributeList().get(i).equals("mtSequenceName")||
			getAttributeList().get(i).equals("mtSequenceNumber" )||
			getAttributeList().get(i).equals("mtCreateDate" )||
			getAttributeList().get(i).equals("mtModifyDate")||
			getAttributeList().get(i).equals("mtErrorDesc"  )||
			getAttributeList().get(i).equals("mtStatus"  )||
			getAttributeList().get(i).equals("mtProcessId" )||
			getAttributeList().get(i).equals("mtRepositoryPath" )||
			getAttributeList().get(i).equals("mtNewObjectId" )){
				 
			}else{
				attributeListMethod.add(getAttributeList().get(i));
				logger.info("AddedttributeListMethod"+getAttributeList().get(i));
			}
		}
		setAttributeList(attributeListMethod);
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
	public ObjectDetails getObjectDetails(String objectName){
		List objectDetailsList = hc.getObject("from ObjectDetails where objectName='"+objectName+"'"); 
		ObjectDetails objectDetails = new ObjectDetails()  ;
		for(int i=0;i<objectDetailsList.size() ;i++){
			objectDetails = (ObjectDetails) objectDetailsList.get(i) ;
		}
		return objectDetails;
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

	public Boolean getInterruptFlag() {
		return this.interruptFlag;
	}
	public void setInterruptFlag(Boolean interruptFlag) {
		logger.info("Setting interrup flag to"+interruptFlag);
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
	public void setRepeatingAttributeList(ArrayList<String> repeatingAttributeList) {
		this.repeatingAttributeList = repeatingAttributeList;
	}
	public String getRepositoryObjectName() {
		return repositoryObjectName;
	}
	public void setRepositoryObjectName(String repositoryObjectName) {
		this.repositoryObjectName = repositoryObjectName;
	}
	public Boolean getCreateRepositoryFolder() {
		return createRepositoryFolder;
	}
	public void setCreateRepositoryFolder(Boolean createRepositoryFolder) {
		this.createRepositoryFolder = createRepositoryFolder;
	}
	public ArrayList<String> getAttributeList() {
		logger.info("Inside getAttributeList");
		return attributeListOfObject;
	}
	public void setAttributeList(ArrayList<String> attributeList) {
		logger.info("Inside setAttributeList");
		this.attributeListOfObject = attributeList;
	}
	private ArrayList<Object> getAllRepeatingObject(Record record) throws Exception{
		logger.info("Inside getAllRepeatingObject");
		ArrayList<Object> dependantObject = new ArrayList<Object>();
		try{
			ObjectDetails objectDetails = bh.getObjectDetails(getRecordType().substring(getRecordType().lastIndexOf(".")+1,getRecordType().length()),getRecordTypeR().substring(getRecordTypeR().lastIndexOf(".")+1,getRecordTypeR().length()));
			ArrayList<String> dependantColumn = new ArrayList<String>();
			String[] columnList = objectDetails.getRelationshipColumns().split(",");
			for(int q=0;q< columnList.length;q++){
				logger.info("columnName"+columnList[q]);
				dependantColumn.add(columnList[q]);
			}
			dependantObject.addAll(rh.getObjectList(getRecordTypeR(),dependantColumn,record ));
		
		}catch(Exception e){
			logger.severe("Error"+e);
			throw e;
		}
		logger.info("Exit getAllRepeatingObject");
		return dependantObject;
	}

}
