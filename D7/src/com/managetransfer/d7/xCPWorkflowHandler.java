package com.managetransfer.d7;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfActivity;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfProcess;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfQueueItem;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.client.IDfWorkflow;
import com.documentum.fc.client.IDfWorkflowBuilder;
import com.documentum.fc.client.IDfWorkitem;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.DfList;
import com.documentum.fc.common.DfLogger;
import com.documentum.fc.common.DfTime;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfList;
import com.managetransfer.businessobject.ProcessData;
import com.managetransfer.businessobject.ProcessTaskList;
import com.managetransfer.client.ProcessMappingDetails;
import com.managetransfer.client.ProcessMappingDetailsMap;
import com.managetransfer.hibernate.HibernateConnection;
import com.managetransfer.record.RecordHandler;

public class xCPWorkflowHandler {
	DocumentumConnection dc = new DocumentumConnection();
	String wf_name = new String("AdjusterNotifyWorkflow");
	ProcessData processData = new ProcessData();
	ProcessTaskList processTaskList = new ProcessTaskList();
	HashMap<String,RecordHandler> listOfRecordHandlers = new HashMap<String, RecordHandler>() ;
	public ProcessTaskList getProcessTaskList() {
		return processTaskList;
	}
	public void setProcessTaskList(ProcessTaskList processTaskList) {
		this.processTaskList = processTaskList;
	}

	HibernateConnection hc = null;
	Map<Integer,Object> processDependantObject = new  HashMap();
	public Map<Integer, Object> getProcessDependantObject() {
		return processDependantObject;
	}
	public void setProcessDependantObject(
			Map<Integer, Object> processDependantObject) {
		this.processDependantObject = processDependantObject;
	}
	public HibernateConnection getHc() {
		return hc;
	}
	public void setHc(HibernateConnection hc) {
		this.hc = hc;
	}
	public ProcessData getProcessData() {
		return processData;
	}
	public void setProcessData(ProcessData processData) {
		this.processData = processData;
	}
	public String getWf_name() {
		return wf_name;
	}
	public void setWf_name(String wf_name) {
		this.wf_name = wf_name;
	}
	public String getDocumentObjectId() {
		return documentObjectId;
	}
	public void setDocumentObjectId(String documentObjectId) {
		this.documentObjectId = documentObjectId;
	}
	public String getPerformerName() {
		return performerName;
	}
	public void setPerformerName(String performerName) {
		this.performerName = performerName;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public String getBoObjectId() {
		return boObjectId;
	}
	public void setBoObjectId(String boObjectId) {
		this.boObjectId = boObjectId;
	}
	
	IDfSysObject newObject = null;
	public IDfSysObject getNewObject() {
		return newObject;
	}
	public void setNewObject(IDfSysObject newObject) {
		this.newObject = newObject;
	}
	final Logger logger = Logger.getLogger(xCPWorkflowHandler.class.getName());
	private String packageName = new String(
			"com.managetransfer.businessobject.");
	String performerName = new String("");
	String documentObjectId   = new String ("");
	String packageType = new String ("");
	String boObjectId = new String("");
	String newWorkflowId = new String("");
	
	public void InitiateWorkflow ( ) throws Exception{
		wf_name = getProcessData().getProcessName();
		performerName = getProcessData().getPerformerName();
		//getting session for workflow supervisor 
		dc.generateSupervisorSession(getProcessData().getWorkflowSupervisor());
		try{
			IDfProcess wfTemplate = (IDfProcess) dc.getSupervisorSession().getObjectByQualification("dm_process WHERE object_name = '" + wf_name + "'");
			if (wfTemplate != null)
			{
				
				IDfWorkflowBuilder wfBldrObj = dc.getSupervisorSession().newWorkflowBuilder(wfTemplate.getObjectId());
				IDfWorkflow workflow = wfBldrObj.getWorkflow();
				workflow.setString("instructions", processData.getInstructions());
				
				wfBldrObj.initWorkflow();
				if(wf_name.equals("dmSendToList2") ||wf_name.equals("dmSendToList3")||wf_name.equals("dmSendToList2wNotify")||wf_name.equals("dmSendToList3wNotify")){//MTSELTHS LOOP SHOULD BE CONVERTED
					IDfList dfListPerformer = new DfList();
					dfListPerformer.append(performerName);
					workflow.setPerformers("Start_low", dfListPerformer);
				}
				wfBldrObj.runWorkflow();
				
				IDfList startActivityNames = wfBldrObj.getStartActivityNames();
				IDfList startActivityIds = wfBldrObj.getStartActivityIds();
				String startActivityName = startActivityNames.getString(0);//0
				IDfId startActivityId = startActivityIds.getId(0);//0
				IDfActivity activity = (IDfActivity) dc.getSupervisorSession().getObject(startActivityId);
				
				
				logger.info(  "activity.getExecType()"+activity.getExecType()+startActivityName );
				if( activity.getExecType() == 0 ){ //if this is manual activity assign performer now
					activity.setPerformerName(performerName);
					
				}
					
				newWorkflowId = workflow.getObjectId().getId();
				
				if (activity != null)
				{
					int nPorts = activity.getPortCount();
					IDfId _First_pkgId = null;
					for (int i = 0; i < nPorts; i++)
					{
						if (activity.getPortType(i).equals("INPUT"))
						{
							String inPortName = activity.getPortName(i);
							String inPackageName = activity.getPackageName(i);
							String inPackageType = activity.getPackageType(i);
							IDfList dfList = new DfList();
							
							if(inPackageType.equals(packageType)){
								dfList.appendId(new DfId(documentObjectId));
							}else {
								//Create new business Object
								newObject = (IDfSysObject)dc.getSupervisorSession().newObject(inPackageType);
								try{
									newObject.setString("performer_name",performerName);
								} catch(Exception e){
									//Ignore if performer name does not exist
								}
								dfList.appendId(newObject.getObjectId());
								populateAttributes(getProcessData().getProcessName(),newObject,processDependantObject);
								newObject.save();
							}
							
							_First_pkgId = workflow.addPackage(startActivityName, inPortName, inPackageName, inPackageType, null, false, dfList);
							
	
						}
					}
	
				}
				
			}
			
			else
			{
				DfLogger.error(this, "Workflow Template Is Null...", null, null);
				throw new DfException("Check if the Workflow template is available/accesible in repository");
			}
		}catch(Exception e){
			DfLogger.error(this,e.getMessage(), null, null);
			throw  e;
		}
	}
	public String getNewWorkflowId() {
		return newWorkflowId;
	}
	public void setNewWorkflowId(String newWorkflowId) {
		this.newWorkflowId = newWorkflowId;
	}
	public DocumentumConnection getDc() {
		return dc;
	}

	public void setDc(DocumentumConnection dc) {
		this.dc = dc;
	}
	public void populateAttributes(String sourceProcesName,IDfSysObject businessObject, Map<Integer,Object> processDependantObject) throws Exception{
		//Get Mapping data
		List processMappingDetailsList = hc
				.getObjectNonCursorQuery("from  ProcessMappingDetails where sourceProcessName='"
						+ sourceProcesName + "'");
		logger.info("Proces Name"+sourceProcesName);
		ProcessMappingDetails pmd =  (ProcessMappingDetails)processMappingDetailsList.get(0);
		logger.info("Got pmd"+pmd);
		Map<Integer,ProcessMappingDetailsMap> pmdm = pmd.getProcessMappingDetailsMap();
		logger.info("Got pmdm"+pmdm);
		for(int i=0 ; i < pmdm.size();i++){
			ProcessMappingDetailsMap processMappingDetailsMap = pmdm.get(i);
			String sourceAttributeName =  processMappingDetailsMap.getSourceAttribute();
			String sourceObjectName  =  processMappingDetailsMap.getSourceDependanyObject();
			int sourceObjectSequence = processMappingDetailsMap.getSourceDependanySeq();
			String targetAttributeName  = processMappingDetailsMap.getTargetAttribute();
			RecordHandler rhSource = getRecordHandler(packageName+sourceObjectName);
			logger.info("Setting BO  attribute"+targetAttributeName);
			Object obj =processDependantObject.get(sourceObjectSequence);
			logger.info("Object infor"+obj.getClass());
			if(!obj.getClass().toString().equals("class "+packageName+sourceObjectName))
					throw new Exception("Object Type and Object mismatch"); 
			try{
				if(rhSource.getColumnType(sourceAttributeName).equals("string")){
					String val ="";
					if(rhSource.getSpecificAttributeValue(processDependantObject.get(sourceObjectSequence),sourceAttributeName)!=null){
					   val =(String) rhSource.getSpecificAttributeValue(processDependantObject.get(sourceObjectSequence),sourceAttributeName);
					}
					businessObject.setString(targetAttributeName,val);
				}if(rhSource.getColumnType(sourceAttributeName).equals("integer")||rhSource.getColumnType(sourceAttributeName).equals("int")){
					int val  = 0;
					if(rhSource.getSpecificAttributeValue(processDependantObject.get(sourceObjectSequence),sourceAttributeName)!= null){
						val  = (Integer) rhSource.getSpecificAttributeValue(processDependantObject.get(sourceObjectSequence),sourceAttributeName);
					}
					businessObject.setInt(targetAttributeName,val);
				}if(rhSource.getColumnType(sourceAttributeName).equals("date") || rhSource.getColumnType(sourceAttributeName).equals("time")){
					if(rhSource.getSpecificAttributeValue(processDependantObject.get(sourceObjectSequence),sourceAttributeName)!=null){
						Date val  = (Date) rhSource.getSpecificAttributeValue(processDependantObject.get(sourceObjectSequence),sourceAttributeName);
						businessObject.setTime(targetAttributeName,new DfTime(val));
					}
				}if(rhSource.getColumnType(sourceAttributeName).equals("long")){
					if(rhSource.getSpecificAttributeValue(processDependantObject.get(sourceObjectSequence),sourceAttributeName)!=null){
						long val  = (long) rhSource.getSpecificAttributeValue(processDependantObject.get(sourceObjectSequence),sourceAttributeName);
						businessObject.setDouble(targetAttributeName,val);
					}
				}if(rhSource.getColumnType(sourceAttributeName).equals("boolean")){
					Boolean val = false;
					if(rhSource.getSpecificAttributeValue(processDependantObject.get(sourceObjectSequence),sourceAttributeName)!=null){
					  val  = (Boolean) rhSource.getSpecificAttributeValue(processDependantObject.get(sourceObjectSequence),sourceAttributeName);
					}
					businessObject.setBoolean(targetAttributeName,val);
				}
			}catch(Exception e){
				throw new Exception("Error while setting businesss attribute "+sourceAttributeName+e); 
			}
		}
		businessObject.save();
	}
	public void executeTask() throws Exception{
		 //Fetch BO object id
		 String boObjectId = getBOId(processTaskList.getNewWorkflowId());
		 if(!boObjectId.equals("")){
			 newObject = (IDfSysObject) dc.getDocumemtumSession().getObject(new DfId(boObjectId)) ;
			 populateAttributes(getProcessData().getProcessName(),newObject,processDependantObject);
		 }
		 if(getProcessTaskList().getTaskName().equals("UpdateAttributes")){
			  updateAttributes();
		 }else if(getProcessTaskList().getTaskName().equals("Acquire")){
			 acquire();
		 }else if(getProcessTaskList().getTaskName().equals("Complete")){
			 complete();
		 }
	}
	private void complete() throws Exception {
		logger.info("Inside complete");
		try{
			//Get work item
			String workItemId = getWorkItemId(processTaskList.getNewWorkflowId());
			//Get session of the user who acquires the task
			dc.generateSupervisorSession(getProcessData().getPerformerName());
			//Acquire the task
			IDfWorkitem idfWorkItem  = (IDfWorkitem) dc.getDocumemtumSession().getObject(new DfId(workItemId));
			if( !idfWorkItem.getActivity().getObjectName().equals(getProcessTaskList().getTargetActivityName()))
				throw new Exception("Activity Name Mismatch In complete");
			idfWorkItem.acquire();
			idfWorkItem.complete();;
			 
		}catch(Exception e){
			logger.severe("Error in  complete"+e);
			throw e;
		}
		logger.info("Exit complete");
	}
	private void acquire() throws Exception {
		logger.info("Inside acquire");
		try{
			//Get work item
			String workItemId = getWorkItemId(processTaskList.getNewWorkflowId());
			//Get session of the user who acquires the task
			dc.generateSupervisorSession(getProcessData().getPerformerName());
			logger.info("User NAme of the session"+dc.getSupervisorSession().getLoginUserName() );
			 //Acquire the task
			IDfWorkitem idfWorkItem  = (IDfWorkitem) dc.getSupervisorSession().getObject(new DfId(workItemId));
			if( !idfWorkItem.getActivity().getObjectName().equals(getProcessTaskList().getTargetActivityName()))
				throw new Exception("Activity Name Mismatch In acquire");
			idfWorkItem.acquire();
			//idfWorkItem.save();
		}catch(Exception e){
			logger.severe("Error in  acquire"+e);
			throw e;
		}
		logger.info("Exit acquire");
	}
	private void updateAttributes() throws Exception {
		logger.info("Inside updateAttributes");
		IDfWorkflow idfworfklow = null;
		//Update Task name and Send date of the task
		try {
			idfworfklow = (IDfWorkflow) dc.getDocumemtumSession().getObject( new DfId(processTaskList.getNewWorkflowId()));
			
			if ( idfworfklow == null) throw new Exception("New Workflow does not exist");
			String workItemId = getWorkItemId(processTaskList.getNewWorkflowId());
			logger.info("workjflow id "+workItemId);
			IDfWorkitem idfWorkItem  = (IDfWorkitem) dc.getDocumemtumSession().getObject(new DfId(workItemId));
			if( !idfWorkItem.getActivity().getObjectName().equals(getProcessTaskList().getTargetActivityName()))
				throw new Exception("Activity Name Mismatch In updateAttributes");
			String queueId =getWorkQueueId(processTaskList.getNewWorkflowId());
			IDfQueueItem idfQueue =  (IDfQueueItem) dc.getDocumemtumSession().getObject( new DfId(queueId));
			idfQueue.setString("task_name",getProcessData().getTaskName());
			idfQueue.setTime("date_sent", new DfTime(getProcessData().getActivityReceiptDate()));
			idfQueue.save();
		}
		catch(Exception e){
			logger.severe("Error in  updateAttributes"+e);
			throw e;
		}
		 
		logger.info("Exit updateAttributes");
	}
	private String getWorkItemId(String workFlowId) throws Exception{
		logger.info("Inside getWorkItemId");
		String workItemId  = "";
		try{
			IDfQuery idfQuery = new DfQuery();
			IDfCollection idfCollection = null;
			idfQuery.setDQL("select item_id as task_id from dmi_queue_item  where delete_flag =false  and router_id='"+workFlowId+"'");
			idfCollection = idfQuery.execute(dc.getDocumemtumSession(),DfQuery.DF_EXEC_QUERY);
			int i =0;
			logger.info("Executed Query");
			while(idfCollection.next()){
				if(i==0){
					workItemId = idfCollection.getString("task_id");
				}else{
					throw new Exception("More than one workitem for same workflow");
				}
				i=i+1;
					
			}
			idfCollection.close();
			if (workItemId.equals("")) throw new Exception("Could not find workitem");
		}catch(Exception e){
			logger.severe("Error while getting workitem id "+e);
			throw e;
		}
		logger.info("Exit getWorkItemId");
		return workItemId;
	}
	private String getWorkQueueId(String workFlowId) throws Exception{
		logger.info("Inside getWorkQueueId");
		String queueId  = "";
		try{
			IDfQuery idfQuery = new DfQuery();
			IDfCollection idfCollection = null;
			idfQuery.setDQL("select r_object_id as queue_id from dmi_queue_item  where delete_flag =false  and router_id='"+workFlowId+"'");
			idfCollection = idfQuery.execute(dc.getDocumemtumSession(),DfQuery.DF_EXEC_QUERY);
			int i =0;
			logger.info("Executed Query");
			while(idfCollection.next()){
				if(i==0){
					queueId = idfCollection.getString("queue_id");
				}else{
					throw new Exception("More than one queue_id for same workflow");
				}
				i=i+1;
					
			}
			idfCollection.close();
			if (queueId.equals("")) throw new Exception("Could not find queue id");
		}catch(Exception e){
			logger.severe("Error while getting workitem id "+e);
			throw e;
		}
		logger.info("Exit getWorkQueueId");
		return queueId;
	}
	private String getBOId(String workFlowId) throws Exception{ 
		logger.info("Inside getBOId");
		String componentId  = "";
		try{
			IDfQuery idfQuery = new DfQuery();
			IDfCollection idfCollection = null;
			idfQuery.setDQL("select  distinct r_component_id , r_package_type from dmi_package where r_workflow_id='"+workFlowId+"' and r_package_type not like 'sigi_document%'");
			idfCollection = idfQuery.execute(dc.getDocumemtumSession(),DfQuery.DF_EXEC_QUERY);
			int i =0;
			logger.info("Executed Query");
			while(idfCollection.next()){
				if(i==0){
					componentId = idfCollection.getString("r_component_id");
				}else{
					throw new Exception("More than one queue_id for same workflow");
				}
				i=i+1;
					
			}
			idfCollection.close();
			if (componentId.equals("")) throw new Exception("Could not find queue id");
		}catch(Exception e){
			logger.severe("Error while getting getBOId id "+e);
			throw e;
		}
		logger.info("Exit getBOId");
		return componentId;
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

