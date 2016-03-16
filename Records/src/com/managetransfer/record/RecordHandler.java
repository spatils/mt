package com.managetransfer.record;

 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import java.util.logging.Logger;
  




import com.managetransfer.client.ObjectDetails;
import com.managetransfer.dynamiccode.keyhandling.KeyHandling;
import com.managetransfer.hibernate.GetRecordDetails;
import com.managetransfer.hibernate.HibernateConnection;

public class RecordHandler {

	final Logger logger = Logger.getLogger(RecordHandler.class.getName()) ;
	private HibernateConnection hc = new HibernateConnection();
	private GetRecordDetails grd = new GetRecordDetails();
	private ArrayList<String> columnNameListPK = new ArrayList<String> ();
	private ArrayList<String> columnNameList  = new ArrayList<String> ();
	private ArrayList<String> allColumnNameList  = new ArrayList<String> ();
	private ArrayList<String> allColumnNameListDataBase  = new ArrayList<String> ();
	private String sequenceName = new String("");
	private int sequenceNumber = 0;
	private int processId =0 ;
	private int threadCount = 0;
	private String typeOfRecord = new String("");
	public static void main(String[] args) {
		 

	}
	public void splitRecordsOperation(){
		/* This methods splits the given record into multiple threads
		 * Input typeOfRecord
		 * 		 processId
		 * 		 threadCount
		 * 		 sequenceNumber
		 * 		 sequenceName
		*/
		//Get count of the records
		String selectCountHQL  = new String("select count(*) from $objectName$ where sequenceName='$sequenceName$' and sequenceNumber=$sequenceNumber$");
		String updateAllHQL  = new String("Update $objectName$  set processId =0 where sequenceName='$sequenceName$' and sequenceNumber=$sequenceNumber$");
		
		String dSelectCountHQL = selectCountHQL.replace("$objectName$", typeOfRecord.substring(typeOfRecord.lastIndexOf(".")+1,typeOfRecord.length()));
		dSelectCountHQL = dSelectCountHQL.replace("$sequenceName$", getSequenceName());
		dSelectCountHQL = dSelectCountHQL.replace("$sequenceNumber$", ""+getSequenceNumber());
		
		long totalCount = grd.getRecordCount(dSelectCountHQL);
		//Update all records with 0 count
		String dUpdateAllHQL = updateAllHQL.replace("$objectName$", typeOfRecord.substring(typeOfRecord.lastIndexOf(".")+1,typeOfRecord.length()));
		dUpdateAllHQL = dSelectCountHQL.replace("$sequenceName$", getSequenceName());
		dUpdateAllHQL = dSelectCountHQL.replace("$sequenceNumber$", ""+getSequenceNumber());
		grd.executeUpdate(dUpdateAllHQL);
		
		//Update all records in loop starting with process id 1 
		long rowCount = totalCount/ getThreadCount();
		
	}
	public void initOperation() throws Exception{
		/* input typeOfRecord
		*/
		String methodName="initOperation";
		logger.info("Inside Method"+methodName);
		//Get hibernate session
		hc.initOperation();
		grd.initOperation(hc);
		if(grd.getColumnNamesPK(getTypeOfRecord()) !=null && !grd.getColumnNamesPK(getTypeOfRecord()).equals("")){
			columnNameListPK.add(grd.getColumnNamesPK(getTypeOfRecord()));
		}else{
			String[] ckColumnList = grd.getColumnNamesCK(getTypeOfRecord());
			for (String str :ckColumnList){
				columnNameListPK.add(str);
			}
		}
		String[] columns = grd.getColumnNames(getTypeOfRecord());
		for(String str : columns ){
			columnNameList.add(str);
		}
	    grd.getPersisterObject(getTypeOfRecord());
	    
	    allColumnNameList.addAll(columnNameList);
	    allColumnNameList.addAll(columnNameListPK);
	    //Populating database list
	    for (int k=0 ; k < allColumnNameList.size();k++ ){
	    	allColumnNameListDataBase.add(grd.getDatabaseColumnName(getTypeOfRecord(), allColumnNameList.get(k)));
	    }
		logger.info("Exiting Method"+methodName);
	}
	public ArrayList<String> getListOfAllColumns(String recordTypeMethod) throws Exception{
		String methodName="getListOfAllColumns";
		logger.info("Inside Method"+methodName);
		ArrayList<String> columnNames = new ArrayList<String>();
		/***
		 * This method returns lists of all columns of an object type.Claims
		 * It expects initOperation method called before this method
		 * recordTypeMethod -- example com.managetransfer.businessobject
		 */
		columnNames= grd.getCombinedPKAndCKAndProperties(recordTypeMethod);
		logger.info("Exiting Method"+methodName);
		return columnNames;
	}
	public ArrayList<String> getListOfColumns(String recordTypeMethod) throws Exception{
		String methodName="getListOfColumns";
		logger.info("Inside Method"+methodName);
		ArrayList<String> columnNames = new ArrayList<String>();
		/***
		 * This method returns lists of all columns of an object type. except primary keyClaims
		 * It expects initOperation method called before this method
		 * recordTypeMethod -- example com.managetransfer.businessobject
		 */
		String[] columns= grd.getColumnNames(recordTypeMethod);
		for(String str : columns ){
			columnNames.add(str);
		}
		logger.info("Exiting Method"+methodName);
		return columnNames;
	}
	public String getColumnType(String recordTypeMethod, String columnNameMethod) throws Exception{
		String methodName="getColumnType";
		logger.info("Inside Method"+methodName);
		/***
		 * This method returns lists of all columns of an object type
		 * It expects initOperation method called before this method
		 * recordTypeMethod -- example com.managetransfer.businessobject.Claims
		 * columnName --example ClaimNumber
		 */
		logger.info("Exiting Method"+methodName);
		return grd.getColumnType(recordTypeMethod, columnNameMethod);
	}
	
	/******************get Next Record ****************/
	public void getNextRecord(Record record) {
		 
	}
	/******************does next records exist ****************/
	public boolean doesNextRecordExist() {
		return false;
	}
	 
	public void startBatchTransaction(){
		 hc.startBatchLevelTransaction();
	}
	public void commitBatchTransaction(){
		hc.commitBatchLevelTransaction();
	}
	public void abortBatchTransaction(){
		hc.abortBatchLevelTransaction();
	}
	public void startDataTransaction(){
		 hc.startBatchLevelTransaction();
	}
	public void abortDataTransaction(){
		hc.abortRecordLevelTransaction();
	}
	public void finalCommit(){
		hc.finalCommitOperation();
	}
	 
	public ArrayList<String> getColumnNameListPK() {
		return columnNameListPK;
	}
	public void setColumnNameListPK(ArrayList<String> columnNameListPK) {
		this.columnNameListPK = columnNameListPK;
	}
	public String getTypeOfRecord() {
		return typeOfRecord;
	}
	public void setTypeOfRecord(String typeOfRecord) {
		this.typeOfRecord = typeOfRecord;
	}
	public Record getRecord() {
		return record;
	}
	public void setRecord(Record record) {
		this.record = record;
	}
	public HibernateConnection getHc() {
		return hc;
	}
	public void setHc(HibernateConnection hc) {
		this.hc = hc;
	}
	public GetRecordDetails getGrd() {
		return grd;
	}
	public void setGrd(GetRecordDetails grd) {
		this.grd = grd;
	}
	private Record record = new Record();
	public String getColumnType(String columnName) throws Exception{
		/*This method returns property type of primary and non-primary columns for example if columns name String is */
		return grd.getColumnType(getTypeOfRecord(), columnName);
	}
	public String getColumnName(String columnName) throws Exception{
		return grd.getDatabaseColumnName(getTypeOfRecord(), columnName);
	}
	public String getDatabaseColumnName(String recordTypeMethod,String columnNameMethod) throws Exception{
		/***
		 * i/p example com.managetransfer.businessobject.Claims , ClaimNumber
		 * o/p Example claim_number
		 */
		return grd.getDatabaseColumnName(recordTypeMethod, columnNameMethod);
	}
	public void saveRecordPK() throws Exception{
		//Used inside Init sequence Documentum
		String methodName="saveRecordPK";
		logger.info("Inside Method"+methodName);
		logger.info("record.getSequenceNumber()"+record.getSequenceNumber());
		KeyHandling kh = new KeyHandling();
		Object object = kh.instantiateObject(typeOfRecord.substring(typeOfRecord.lastIndexOf(".")+1,typeOfRecord.length()), record.getListOfStringAtrributes(), record.getListOfIntAttributes() , record.getListOfDateAttributes(), record.getListOfLongAtrributes());
		hc.saveOperation(object);
		logger.info("object"+object.getClass());
		if ( record.getSequenceName() != null ){
			record.getListOfStringAtrributes().put("mt_sequence_name", record.getSequenceName());
		 }
		if ( record.getSequenceNumber() != 999 ){
			record.getListOfIntAttributes().put("mt_sequence_number", record.getSequenceNumber());
		}
		if ( record.getProcessId() != 999 ){
			record.getListOfIntAttributes().put("mt_process_id", record.getProcessId());
		}
		if ( record.getSequenceName() != null ){
			record.getListOfStringAtrributes().put("mt_status", record.getStatusOfRecord());
		}
		if ( record.getCreateDate() != null ){
			record.getListOfDateAttributes().put("mt_create_date",  record.getCreateDate());
		}
		if ( record.getModifyDate()  != null ){
			record.getListOfDateAttributes().put("mt_modify_date",  record.getModifyDate());
		}
		if ( record.getErrorDetails()  != null ){
			record.getListOfStringAtrributes().put("mt_error_description", record.getErrorDetails());
		}
		grd.setListOfDateAttributes(record.getListOfDateAttributes( )) ;
		grd.setListOfIntAttributes(record.getListOfIntAttributes( )) ;
		grd.setListOfLongAtrributes(record.getListOfLongAtrributes( )) ;
		grd.setListOfStringAtrributes(record.getListOfStringAtrributes( )) ;
		grd.setListOfBooleanAttributes(record.getListOfBooleanAttributes( )) ;
		Object objectWithAllProperties = grd.setAttributes(object , getTypeOfRecord() );
		hc.updateOperation(objectWithAllProperties);
		logger.info("Exiting Method"+methodName);
	}
	public void createNewRecord(String recordTypeMethod,Record recordMethod) throws Exception{
		String methodName="createNewRecord";
		logger.info("Inside Method"+methodName);
	    /**
	     * This method expects following input 
	     * 1 recodeType --Value for example com.managetransfer.businessobject.Claims
	     * 2 Record -- Attribute values are populated in the ListArrays
	     * 
	     * This method expects that hc.initOperation method is called prior to calling this method
	     * This method will create new object in the repository
	     * 	
	     */
		KeyHandling kh = new KeyHandling();
		Object object = kh.instantiateObject(recordTypeMethod.substring(recordTypeMethod.lastIndexOf(".")+1,recordTypeMethod.length()), recordMethod.getListOfStringAtrributes(), recordMethod.getListOfIntAttributes() , recordMethod.getListOfDateAttributes(), recordMethod.getListOfLongAtrributes());
		hc.saveOperation(object);
		Object objectWithAllProperties = grd.setAttributes(object , recordTypeMethod,recordMethod.getListOfStringAtrributes(),recordMethod.getListOfIntAttributes(),recordMethod.getListOfDateAttributes(),recordMethod.getListOfLongAtrributes(),recordMethod.getListOfBooleanAttributes());
		hc.updateOperation(objectWithAllProperties);
		logger.info("Exiting Method"+methodName);
	
	
	}
	public void closeConnection(){
		hc.closeConnection();
	}
	public List getObject(String sqlDrivingCursor) {
		 
		return hc.getObject(sqlDrivingCursor) ;
	}
	public List getObjectNonCursorQuery(String sql) {
		 
		return hc.getObjectNonCursorQuery(sql) ;
	}
	public void getPropertyValues(Object object) throws Exception{
		String methodName="getPropertyValues";
		logger.info("Inside Method"+methodName);
		grd.getProperties(object,getTypeOfRecord());
		record.setListOfDateAttributes(grd.getListOfDateAttributes());
		record.setListOfIntAttributes(grd.getListOfIntAttributes());
		record.setListOfBooleanAttributes(grd.getListOfBooleanAttributes());
		record.setListOfLongAtrributes(grd.getListOfLongAtrributes());
		record.setListOfStringAtrributes(grd.getListOfStringAtrributes());
		logger.info("Existing Method"+methodName);
	}
	public void getPropertyValuesAll(Object object) throws Exception{
		String methodName="getPropertyValuesAll";
		logger.info("Inside Method"+methodName);
		//This section extracts non-primary key values
		grd.getProperties(object,getTypeOfRecord());
		record.setListOfDateAttributes(grd.getListOfDateAttributes());
		record.setListOfIntAttributes(grd.getListOfIntAttributes());
		record.setListOfBooleanAttributes(grd.getListOfBooleanAttributes());
		record.setListOfLongAtrributes(grd.getListOfLongAtrributes());
		record.setListOfStringAtrributes(grd.getListOfStringAtrributes());
		//This section extracts primary key values
		KeyHandling kh = new KeyHandling();
		kh.extractKey(object, getTypeOfRecord().substring(typeOfRecord.lastIndexOf(".")+1,typeOfRecord.length()));
		if(kh.ohmDate!=null & kh.ohmDate.size()>0){
			record.getListOfDateAttributes().putAll(kh.ohmDate);
		}
		if(kh.ohmString!=null & kh.ohmString.size()>0){
			record.getListOfStringAtrributes().putAll(kh.ohmString);
		}
		if(kh.ohmInteger!=null & kh.ohmInteger.size()>0){
			record.getListOfIntAttributes().putAll(kh.ohmInteger);
		}
		if(kh.ohmLong!=null & kh.ohmLong.size()>0){
			record.getListOfLongAtrributes().putAll(kh.ohmLong);
		}
		
		logger.info("Existing Method"+methodName);
	}
	public ArrayList<String> getColumnNameList() {
		return columnNameList;
	}
	public void setColumnNameList(ArrayList<String> columnNameList) {
		this.columnNameList = columnNameList;
	}
	public void saveObject(Object objectMethod)throws Exception{
		/**
		 * This method is called from Transformation Phase to store repeating value attriutes
		 */
		hc.saveOperation(objectMethod);
	}
	public void saveOrUpdateObject(Object objectMethod)throws Exception{
		/**
		 * This method is called from Transformation Phase to store repeating value attriutes
		 */
		hc.saveOrUpdateOperation(objectMethod);
	}
	public void updateObject(Object objectMethod)throws Exception{
		/**
		 * This method is called from Transformation Phase to store repeating value attriutes
		 */
		hc.updateOperation(objectMethod);
	}
	public void saveRecord(Object object) throws Exception {
		//Used inside Export Documentum
	
		String methodName="saveRecord";
		logger.info("Inside Method"+methodName);
		logger.info("record.getSequenceNumber()"+record.getSequenceNumber());
		if ( record.getSequenceName() != null ){
			record.getListOfStringAtrributes().put("mt_sequence_name", record.getSequenceName());
		 }
		if ( record.getSequenceNumber() != 999 ){
			record.getListOfIntAttributes().put("mt_sequence_number", record.getSequenceNumber());
		}
		if ( record.getProcessId() != 999 ){
			record.getListOfIntAttributes().put("mt_process_id", record.getProcessId());
		}
		if ( record.getSequenceName() != null ){
			record.getListOfStringAtrributes().put("mt_status", record.getStatusOfRecord());
		}
		if ( record.getCreateDate() != null ){
			record.getListOfDateAttributes().put("mt_create_date",  record.getCreateDate());
		}
		if ( record.getModifyDate()  != null ){
			record.getListOfDateAttributes().put("mt_modify_date",  record.getModifyDate());
		}
		if ( record.getErrorDetails()  != null ){
			record.getListOfStringAtrributes().put("mt_error_description", record.getErrorDetails());
		}
		grd.setListOfDateAttributes(record.getListOfDateAttributes( )) ;
		grd.setListOfIntAttributes(record.getListOfIntAttributes( )) ;
		grd.setListOfBooleanAttributes(record.getListOfBooleanAttributes( )) ;
		grd.setListOfLongAtrributes(record.getListOfLongAtrributes( )) ;
		grd.setListOfStringAtrributes(record.getListOfStringAtrributes( )) ;
		Object objectWithAllProperties = grd.setAttributes(object , getTypeOfRecord() );
		hc.updateOperation(objectWithAllProperties);
		logger.info("Exiting Method"+methodName);
	}
	public String getModifiedExportDocumentumQuery(String dql,Object object ) throws Exception{
		String methodName="getModifiedExportDocumentumQuery";
		// Passing boolean and date value is not supported
		
		logger.info("Inside Method"+methodName);
		KeyHandling kh = new KeyHandling();
		kh.extractKey(object, getTypeOfRecord().substring(typeOfRecord.lastIndexOf(".")+1,typeOfRecord.length()));
		for(int i=0; i < getColumnNameListPK().size();i++){
			 logger.info("attributename String"+grd.getDatabaseColumnName(getTypeOfRecord(), getColumnNameListPK().get(i)));
			 logger.info("$"+grd.getDatabaseColumnName(getTypeOfRecord(), getColumnNameListPK().get(i))+"$");
			 if(getColumnType( getColumnNameListPK().get(i)).equals("string")){
				 dql=dql.replace("$"+grd.getDatabaseColumnName(getTypeOfRecord(), getColumnNameListPK().get(i))+"$",""+kh.ohmString.get(grd.getDatabaseColumnName(getTypeOfRecord(), getColumnNameListPK().get(i))));
			 }else if (getColumnType(getColumnNameListPK().get(i)).equals("integer")||getColumnType(getColumnNameListPK().get(i)).equals("int")){
				 dql=dql.replace("$"+grd.getDatabaseColumnName(getTypeOfRecord(), getColumnNameListPK().get(i))+"$",""+kh.ohmInteger.get(grd.getDatabaseColumnName(getTypeOfRecord(), getColumnNameListPK().get(i))));
					
			 }else if (getColumnType(getColumnNameListPK().get(i)).equals("date")){
				 dql=dql.replace("$"+grd.getDatabaseColumnName(getTypeOfRecord(), getColumnNameListPK().get(i))+"$",""+kh.ohmDate.get(grd.getDatabaseColumnName(getTypeOfRecord(), getColumnNameListPK().get(i))));
					
			 }else if (getColumnType(getColumnNameListPK().get(i)).equals("long")){
				 dql=dql.replace("$"+grd.getDatabaseColumnName(getTypeOfRecord(), getColumnNameListPK().get(i))+"$",""+kh.ohmLong.get(grd.getDatabaseColumnName(getTypeOfRecord(), getColumnNameListPK().get(i))));
					
			 }
		}
		logger.info("DQL::"+dql);
		logger.info("exiting Method"+methodName);
		return  dql;
	}
	public Object getSpecificAttributeValuePK(Object object,String attributeName){
		KeyHandling kh = new KeyHandling();
		kh.extractKey(object, getTypeOfRecord().substring(typeOfRecord.lastIndexOf(".")+1,typeOfRecord.length()));
		if(kh.ohmString.get(attributeName)!=null){
			return kh.ohmString.get(attributeName);
		}else if(kh.ohmLong.get(attributeName)!=null){
			return kh.ohmLong.get(attributeName);
		}else if(kh.ohmDate.get(attributeName)!=null){
			return kh.ohmDate.get(attributeName);
		}else if(kh.ohmInteger.get(attributeName)!=null){
			return kh.ohmInteger.get(attributeName);
		}
		return null;
	}
	public Object getSpecificAttributeValue(Object object,String attributeName) throws Exception{
		String methodName="getSpecificAttributeValue";
		logger.info("Inside Method"+methodName);
		return grd.getSpecificAttributeValue(object, getTypeOfRecord(), attributeName);
	}
	public ArrayList<String> getAllColumnNameList() {
		return allColumnNameList;
	}
	public void setAllColumnNameList(ArrayList<String> allColumnNameList) {
		this.allColumnNameList = allColumnNameList;
	}
	public ArrayList<String> getAllColumnNameListDataBase() {
		 
		return allColumnNameListDataBase;
	}
	public void setAllColumnNameListDataBase(
			ArrayList<String> allColumnNameListDataBase) {
		this.allColumnNameListDataBase = allColumnNameListDataBase;
	}
	public void setError(String error){
		this.record.setErrorDetails(error);
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
	public int getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public ArrayList<Object> getObjectList(String objectName,
			ArrayList<String> attributeList, Record record) throws Exception {

		 
		String methodName = "getObjectList";
		logger.info("Inside Method" + methodName);
		try {
			/***
			 * This method expects following input 1. Object Name like
			 * com.managetransfer.Claims 2. List of attributes available to
			 * query objectId 3. List of attribute values
			 * 
			 * currently only interger and string values are supported The
			 * method constructs a HQL and returns all objects matching the
			 * criteria hql = " from Claims where objectId='xyz'
			 */
			logger.info("ObjectName" + objectName);
			String HQL = "from " + objectName;
			for (int i = 0; i < attributeList.size(); i++) {
				logger.info(" attribute name " +  attributeList.get(i) );
				if (i == 0)
					HQL = HQL + " where " + attributeList.get(i) + " = ";
				else
					HQL = HQL + " and " + attributeList.get(i) + " = ";
				if (getColumnType(objectName, attributeList.get(i)).equals(
						"string")) {
					HQL = HQL
							+ "'"
							+ record.getListOfStringAtrributes().get(
									getDatabaseColumnName(objectName,
											attributeList.get(i))) + "'";
				} else if (getColumnType(objectName, attributeList.get(i))
						.equals("integer")||getColumnType(objectName, attributeList.get(i))
						.equals("int")) {
					HQL = HQL
							+ ""
							+ record.getListOfStringAtrributes().get(
									getDatabaseColumnName(objectName,
											attributeList.get(i)));
				}

			}
			logger.info("HQL " + HQL);
			List objectListFromHQL = hc.getObject(HQL) ; 
			ArrayList<Object> objectList =new ArrayList<Object>();
			for(int i=0;i< objectListFromHQL.size(); i++){
				objectList.add( objectListFromHQL.get(i));
			}
			return objectList;
		} catch (Exception e) {
			logger.severe("Error insdiegetObjectList " + e);
			throw e;
		}
		 
	}
	public void clearAndFlush(){
		hc.clearAndFlush();
	}
	public ObjectDetails getObjectDetails(String primaryObjectName, String secondarObjectName) throws Exception{
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
