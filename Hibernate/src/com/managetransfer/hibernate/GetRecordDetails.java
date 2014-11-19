package com.managetransfer.hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;

import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

//import com.managetransfer.record.Information;
//import com.managetransfer.record.KeyHandling;
 




public class GetRecordDetails {
	private SessionFactory sessionFactory = null;
	private HibernateConnection hc = new HibernateConnection();
	private AbstractEntityPersister persisterObject = null;
	private ClassMetadata classMetadata = null;
	private String queryString = new String("from Claims where 1=1 ");
	private HashMap<String,String> listOfStringAtrributes = new HashMap<String, String>() ;
	private HashMap<String,Integer> listOfIntAttributes  = new HashMap<String, Integer>() ;
	private HashMap<String,Date> listOfDateAttributes  = new HashMap<String, Date>() ;
	private HashMap<String,Long> listOfLongAtrributes  = new HashMap<String, Long>() ; 
	final Logger logger = Logger.getLogger(GetRecordDetails.class.getName()) ;
	public static void main(String[] args) throws Exception {
		/**
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		GetRecordDetails grd = new GetRecordDetails();
		Calendar startTime = Calendar.getInstance();
		HibernateConnection hc = new HibernateConnection();
		
		hc.initOperation();
		hc.startBatchLevelTransaction();
		Information info1 = new Information();
		info1.setObjectId("wew");
		hc.saveOperation(info1);
		
		grd.initOperation(hc);
		grd.getPersisterObject("com.managetransfer.record.Information");
		String columnName[] = grd.getColumnNames("com.managetransfer.record.Information");
		
		for(int i=0; i < columnName.length; i++){
			System.out.println("columns "+columnName[i]);
		}
		//Calendar endTime = Calendar.getInstance();
		KeyHandling kh = new KeyHandling();
		grd.getListOfStringAtrributes().put("r_object_id","14");
		
		Object info = kh.instantiateObject("Information",grd.getListOfStringAtrributes(),null,null,null);
		hc.saveOperation(info);
		 
		grd.getListOfIntAttributes().put("mt_sequence_number",12);
		grd.getListOfIntAttributes().put("mt_process_id",12);
		grd.getListOfStringAtrributes().put("policy_number","12");
		grd.getListOfStringAtrributes().put("object_name","12");
		System.out.println("After Setting policy number " );
		grd.setAttributes(info , "com.managetransfer.record.Information" );
		System.out.println("Informatiuon object attributes values are set" );
		hc.saveOperation(info);
		System.out.println("Information object saved" );
		
		 
		//grd.saveRecord();
		//grd.queryRecord();
		 
		  
		hc.commitBatchLevelTransaction();
		***/
		 
		
	}
	private void queryRecord() {
		List list = hc.getObject(queryString);
		for(int i=0 ; i<list.size();i++){
			Object obj = list.get(i);
			System.out.println(classMetadata.getPropertyValue(obj, "folderPath"));
			  
			
		}
		
	}
	private void saveRecord() {
		hc.startBatchLevelTransaction();
		//hc.saveOperation(claim);
		hc.commitBatchLevelTransaction();
	}
	public long getRecordCount(String queryString){
	  return	( (Long) hc.getHibernateSession().createQuery(queryString).iterate().next() ).intValue();
	}
	public void executeUpdate(String queryString){
		hc.getHibernateSession().createQuery( queryString ).executeUpdate();
	}
	public void getProperties(Object object ,String className) throws Exception{
		String methodName="getProperties";
		logger.info("Inside Method"+methodName);
		/*
		 * This method extracts list of properties and stored it locally according to type of property
		 */
		if(listOfStringAtrributes!=null && listOfStringAtrributes.size()>1){
			listOfStringAtrributes.clear();
		}
		if(listOfLongAtrributes!=null && listOfLongAtrributes.size()>1){
			listOfLongAtrributes.clear();
		}
		if(listOfDateAttributes!=null && listOfDateAttributes.size()>1){
			listOfDateAttributes.clear();
		}if(listOfIntAttributes!=null && listOfIntAttributes.size()>1){
			listOfIntAttributes.clear();
		}
		String[] columnNames = getColumnNames(className) ;
		for(String str: columnNames){
			logger.info("Processing str : "+str+" Type "+getColumnType(className,str));
			logger.info("persisterObject : "+persisterObject);
			logger.info("Val1   : "+persisterObject.getPropertyValue(object, str));
			if(getColumnType(className,str).equals("string")){
				 listOfStringAtrributes.put(getDatabaseColumnName(className,str),(String)persisterObject.getPropertyValue(object, str)) ;
			 }else if (getColumnType(className,str).equals("integer")){
				 listOfIntAttributes.put(getDatabaseColumnName(className,str), (Integer)persisterObject.getPropertyValue(object, str)) ;
			 }else if (getColumnType(className,str).equals("date")){
				 listOfDateAttributes.put(getDatabaseColumnName(className,str), (Date)persisterObject.getPropertyValue(object, str)) ;
			 }else if (getColumnType(className,str).equals("long")){
				 listOfLongAtrributes.put(getDatabaseColumnName(className,str),(Long)persisterObject.getPropertyValue(object, str)) ;
			 }
		}
		logger.info("Exit Method"+methodName);
				
	}
	public Object getSpecificAttributeValue(Object perObject ,String className, String attributeName) throws Exception{
		String methodName="getSpecificAttributeValue";
		logger.info("Inside Method"+methodName);
		logger.info("perObject"+perObject+" Class Name "+className+" Atttribute Name "+attributeName);
		logger.info("persisterObject"+persisterObject+" Class Name "+className+" Atttribute database name "+getDatabaseColumnName(className,attributeName));
		logger.info("Value 2 "+ persisterObject.getPropertyValue(perObject,  attributeName ));
		Object object = persisterObject.getPropertyValue(perObject,  attributeName );
		logger.info("Exit Method"+methodName);
		return object;
	}
	public void initOperation( HibernateConnection hc){
		setHc(hc) ;
		setSessionFactory(hc.getSessionFactory());
	}
	public String[] getColumnNames(String className) throws Exception{
		/* this method returns all properties except PK and CK  */
		 if (getSessionFactory() == null){
			 throw new Exception("Please initialize session factory");
		 }
		 ClassMetadata connectionClassMetadata =  getSessionFactory().getClassMetadata(className);
		 return connectionClassMetadata.getPropertyNames();
	}
	
	public String  getColumnType(String className,String columnName) throws Exception{
		/*This method returns property type of primary and non-primary columns for it return string,int,data  */
		 if (getSessionFactory() == null){
			 throw new Exception("Please initialize session factory");
		 }
		 ClassMetadata connectionClassMetadata =  getSessionFactory().getClassMetadata(className);
		 AbstractEntityPersister persister = (AbstractEntityPersister) connectionClassMetadata;
		 return persister.getPropertyType(columnName).getName();
		  
	}
	public String getDatabaseColumnName(String className,String columnName) throws Exception{
		/*This method returns database column name for any primary key, composite key or normal column for example r_object_type,claim_number etc  */
		 if (getSessionFactory() == null){
			 throw new Exception("Please initialize session factory");
		 }
		 ClassMetadata connectionClassMetadata =  getSessionFactory().getClassMetadata(className);
		 AbstractEntityPersister persister = (AbstractEntityPersister) connectionClassMetadata;
		 return persister.getPropertyColumnNames(columnName)[0];
		 
	}
	
	public String  getColumnNamesPK(String className) throws Exception{
		/* this method returns the primary key names   */
		 if (getSessionFactory() == null){
			 throw new Exception("Please initialize session factory");
		 }
		 System.out.println(className);
		 ClassMetadata connectionClassMetadata =  getSessionFactory().getClassMetadata(className);
		 return connectionClassMetadata.getIdentifierPropertyName();
 	}
	public String[]  getColumnNamesCK(String className) throws Exception{
		/* this method returns the Composite key name   */
		 if (getSessionFactory() == null){
			 throw new Exception("Please initialize session factory");
		 }
		 ClassMetadata connectionClassMetadata =  getSessionFactory().getClassMetadata(className);
		 String component =  connectionClassMetadata.getIdentifierType().getName() ;
		 component = component.substring(component.indexOf("["),component.length()).replace("[", "").replace("]", "");
		 return component.split(",");
		 
	}
	public ArrayList<String> getCombinedPKAndCKAndProperties(String classNname) throws Exception{
		String methodName="getCombinedPKAndCKAndProperties";
		logger.info("Inside Method"+methodName);
		ArrayList<String> combinedPropertyValues = new ArrayList<String>();
		try{
			if(getColumnNamesPK(classNname)  !=null && !getColumnNamesPK(classNname).equals("")){
				combinedPropertyValues.add(getColumnNamesPK(classNname));
			}else{
				String[] ckColumnList =   getColumnNamesCK(classNname);
				for (String str :ckColumnList){
					combinedPropertyValues.add(str);
				}
			}
			String[] columnList = getColumnNames(classNname);
				for (String str :columnList){
					combinedPropertyValues.add(str);
				}
		}catch(Exception e){
			logger.severe("Exception"+e);
			throw e;
		}
		logger.info("Existing"+methodName);
		return combinedPropertyValues;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public HibernateConnection getHc() {
		return hc;
	}
	public void setHc(HibernateConnection hc) {
		this.hc = hc;
	}
	public void getPersisterObject(String className){
		 classMetadata =  getSessionFactory().getClassMetadata(className);
		 persisterObject = (AbstractEntityPersister) classMetadata;
	}
	public void setAttributes(HashMap<String,String> hm){
		
	}
	 
	public void setPersisterObject(AbstractEntityPersister persisterObject) {
		this.persisterObject = persisterObject;
	}
	public HashMap<String, String> getListOfStringAtrributes() {
		return listOfStringAtrributes;
	}
	public void setListOfStringAtrributes(
			HashMap<String, String> listOfStringAtrributes) {
		this.listOfStringAtrributes = listOfStringAtrributes;
	}
	public HashMap<String, Integer> getListOfIntAttributes() {
		return listOfIntAttributes;
	}
	public void setListOfIntAttributes(HashMap<String, Integer> listOfIntAttributes) {
		this.listOfIntAttributes = listOfIntAttributes;
	}
	public HashMap<String, Date> getListOfDateAttributes() {
		return listOfDateAttributes;
	}
	public void setListOfDateAttributes(HashMap<String, Date> listOfDateAttributes) {
		this.listOfDateAttributes = listOfDateAttributes;
	}
	public HashMap<String, Long> getListOfLongAtrributes() {
		return listOfLongAtrributes;
	}
	public void setListOfLongAtrributes(HashMap<String, Long> listOfLongAtrributes) {
		this.listOfLongAtrributes = listOfLongAtrributes;
	}
	public Object setAttributes(Object object, String className) throws Exception{
		String[] columnNames = getColumnNames(className);
		String methodName="setAttributes";
		logger.info("Inside Method"+methodName);
		for(int i = 0 ; i< columnNames.length ; i++){
			logger.info("Setting "+columnNames[i]+" Type : "+getColumnType(className,columnNames[i]));
			if(getColumnType(className,columnNames[i]).equals("string")){
				logger.info("Setting "+columnNames[i]+" :value "+getListOfStringAtrributes().get(getDatabaseColumnName(className,columnNames[i])));
				persisterObject.setPropertyValue(object, columnNames[i],getListOfStringAtrributes().get(getDatabaseColumnName(className,columnNames[i])) );
			}else if (getColumnType(className,columnNames[i]).equals("long")){
				persisterObject.setPropertyValue(object, columnNames[i],getListOfLongAtrributes().get(getDatabaseColumnName(className,columnNames[i])) );
			}else if(getColumnType(className,columnNames[i]).equals("date")){
				persisterObject.setPropertyValue(object, columnNames[i],getListOfDateAttributes().get(getDatabaseColumnName(className,columnNames[i])) );
			}else if(getColumnType(className,columnNames[i]).equals("integer")){
				logger.info("Setting "+columnNames[i]+" :value "+getListOfIntAttributes().get(getDatabaseColumnName(className,columnNames[i])));
				persisterObject.setPropertyValue(object, columnNames[i],getListOfIntAttributes().get(getDatabaseColumnName(className,columnNames[i])) );
			}
		}
		logger.info("Exit Method"+methodName);
		return object;
	}
		public Object setAttributes(Object objectMethod, String classNameMethod, HashMap<String,String> listOfStringAtrributesMethod,HashMap<String,Integer> listOfIntAttributesMethod,HashMap<String,Date> listOfDateAttributesMethod,HashMap<String,Long> listOfLongAtrributesMethod) throws Exception{
		String[] columnNames = getColumnNames(classNameMethod);
		String methodName="setAttributes";
		AbstractEntityPersister persisterObjectMethod = null;
		ClassMetadata classMetadataMethod = null;
		classMetadataMethod =  getSessionFactory().getClassMetadata(classNameMethod);
		persisterObjectMethod = (AbstractEntityPersister) classMetadataMethod;
		logger.info("Inside Method"+methodName);
		for(int i = 0 ; i< columnNames.length ; i++){
			logger.info("Setting "+columnNames[i]+" Type : "+getColumnType(classNameMethod,columnNames[i]));
			if(getColumnType(classNameMethod,columnNames[i]).equals("string")){
				logger.info("Setting "+columnNames[i]+" :value "+getListOfStringAtrributes().get(getDatabaseColumnName(classNameMethod,columnNames[i])));
				persisterObjectMethod.setPropertyValue(objectMethod, columnNames[i],listOfStringAtrributesMethod.get(getDatabaseColumnName(classNameMethod,columnNames[i])) );
			}else if (getColumnType(classNameMethod,columnNames[i]).equals("long")){
				persisterObjectMethod.setPropertyValue(objectMethod, columnNames[i],listOfLongAtrributesMethod.get(getDatabaseColumnName(classNameMethod,columnNames[i])) );
			}else if(getColumnType(classNameMethod,columnNames[i]).equals("date")){
				persisterObjectMethod.setPropertyValue(objectMethod, columnNames[i],listOfDateAttributesMethod.get(getDatabaseColumnName(classNameMethod,columnNames[i])) );
			}else if(getColumnType(classNameMethod,columnNames[i]).equals("integer")){
				logger.info("Setting "+columnNames[i]+" :value "+getListOfIntAttributes().get(getDatabaseColumnName(classNameMethod,columnNames[i])));
				persisterObjectMethod.setPropertyValue(objectMethod, columnNames[i],listOfIntAttributesMethod.get(getDatabaseColumnName(classNameMethod,columnNames[i])) );
			}
		}
		logger.info("Exit Method"+methodName);
		return objectMethod;
	}
 	
}
