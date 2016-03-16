package com.managetransfer.d7;

 
import java.util.Date;
import java.util.concurrent.TimeUnit;
 
import java.util.logging.Logger;

import com.documentum.com.DfClientX;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfDocument;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfFormat;
import com.documentum.fc.client.IDfLocalTransaction;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfRelation;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.DfTime;
import com.documentum.fc.common.IDfLoginInfo;
import com.documentum.operations.IDfCheckinNode;
import com.documentum.operations.IDfCheckinOperation;
import com.documentum.operations.IDfExportNode;
import com.documentum.operations.IDfExportOperation;
import com.documentum.operations.IDfFormatRecognizer;

public class DocumentumConnection {
	private DfClientX clientx = null;
	private IDfClient client = null;
	private String  usrName = new String("");
	private String  userPsw	 = new String("");
	private String  repoName = new String ("");
	private IDfSessionManager sessionMgr = null;
	IDfLocalTransaction recordLevelTransaction = null;
	IDfLocalTransaction recordLevelTransactionSupervisor = null;
	IDfLocalTransaction batchLevelTransaction = null;
	String  DQLGetFolderPath  = new String(
			"SELECT   	  f.r_folder_path  	FROM  	  dm_document d, dm_folder f  WHERE   d.i_folder_id = f.r_object_id and   d.i_position = -1 and    f.i_position = -1 and  d.r_object_id ='$r_object_id$' ENABLE   (ROW_BASED) ");  
	
	final Logger logger = Logger.getLogger(DocumentumConnection.class.getName()) ;
	public String getRepoName() {
		return repoName;
	}
	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}
	private IDfSession documemtumSession =  null;
	private IDfSession supervisorSession =  null;
	public static void main(String[] args) throws Exception{
		 
		 DocumentumConnection cd = new DocumentumConnection();
		 cd.connectRepository();
		 cd.disconnectSession();
	}
	public void connectRepository() throws Exception{
		String methodName="connectRepository";
		logger.info("Inside Method"+methodName);
		// create a client object using a factory method in DfClientX
		clientx = new DfClientX();
		client = clientx.getLocalClient();
		// call a factory method to create the session manager
		sessionMgr = client.newSessionManager();
		// create an IDfLoginInfo object and set its fields
		IDfLoginInfo loginInfo = clientx.getLoginInfo();
		loginInfo.setUser(usrName);
		loginInfo.setPassword(userPsw);
		// set single identity for all docbases
		sessionMgr.setIdentity(repoName , loginInfo);
		documemtumSession = sessionMgr.getSession(repoName);
		logger.info("Exiting Method"+methodName);
	}
	public void generateSupervisorSession(String userName) throws Exception{
		try{
			//timeout period 1 minute and ticket is valid on all servers
			 
			String ticket = documemtumSession.getLoginTicketEx(getUserLoginName(userName),"global", 1, false, null);
			IDfLoginInfo loginInfoLocalUser = clientx.getLoginInfo();
			loginInfoLocalUser.setUser(getUserLoginName(userName));
			loginInfoLocalUser.setPassword(ticket);
			IDfSessionManager sMgr = client.newSessionManager();
			sMgr.clearIdentities();
			sMgr.setIdentity(repoName, loginInfoLocalUser);
			supervisorSession = sMgr.getSession(repoName);
			/***
			 * Starting transaction for the supervisor session automatically. 
			 * This transaction is committed or rolled back with the main session
			 */
			recordLevelTransactionSupervisor = supervisorSession.beginTransEx();
		}catch(Exception e){
			logger.severe("erorr in generateSupervisorSession "+e);
			throw e;
		}
		 
	}
	public void disconnectSession(){
		getSessionMgr().release(getDocumemtumSession());
		 
	}
	
	public String getUsrName() {
		return usrName;
	}
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}
	public DfClientX getClientx() {
		return clientx;
	}
	public void setClientx(DfClientX clientx) {
		this.clientx = clientx;
	}
	public IDfClient getClient() {
		return client;
	}
	public void setClient(IDfClient client) {
		this.client = client;
	}
	public String getUserPsw() {
		return userPsw;
	}
	public void setUserPsw(String userPsw) {
		this.userPsw = userPsw;
	}
	 
	public IDfSession getDocumemtumSession() {
		return documemtumSession;
	}
	public void setDocumemtumSession(IDfSession documemtumSession) {
		this.documemtumSession = documemtumSession;
	}
	public IDfSessionManager getSessionMgr() {
		return sessionMgr;
	}
	public void setSessionMgr(IDfSessionManager sessionMgr) {
		this.sessionMgr = sessionMgr;
	}
	public void clearCache() throws Exception{
		try{
			getDocumemtumSession().flush("aclcache","");
			getDocumemtumSession().flush("groupcache","");
			getDocumemtumSession().flush("persistentcache","");
			getDocumemtumSession().flush("persistentobjcache","");
		}catch(Exception e){
			logger.severe("error inside clearCache"+e);
			throw e;
		}
	}
	public String exportDocument(String docId, String destinationFolderPath) throws Exception{
		// Use the factory method to create an IDfImportOperation instance.
		// Use the factory method to create an IDfExportOperation instance.
		IDfExportOperation eo =  getClientx().getExportOperation();
		// Create a document object that represents the document being exported.
		IDfDocument doc = 		(IDfDocument)  getDocumemtumSession().getObject(new DfId(docId));
		// Create an export node, adding the document to the export operation object.
		IDfExportNode node = (IDfExportNode) eo.add(doc);
		// Get the document's format.
		IDfFormat format = doc.getFormat();
		// If necessary, append a path separator to the targetLocalDirectory value.
		if (destinationFolderPath.lastIndexOf("/") != 		destinationFolderPath.length() - 1 && 		destinationFolderPath.lastIndexOf("\\") != 		destinationFolderPath.length() - 1) 		{
			destinationFolderPath += "/";
		}
		// Set the full file path on the local system.
		node.setFilePath(destinationFolderPath + docId + "." +format.getDOSExtension());
		// Execute and return results
		if (eo.execute())
		{
			return node.getFilePath();
		}
		else
		{
			return "";
		}
	}
	public String exportDocumentSysObject(String docId, String destinationFolderPath) throws Exception{
		//THis export operation written for better performance over exportDocument
		IDfSysObject doc = 		(IDfSysObject)  getDocumemtumSession().getObject(new DfId(docId));
		IDfFormat format = doc.getFormat();
		if (destinationFolderPath.lastIndexOf("/") != 		destinationFolderPath.length() - 1 && 		destinationFolderPath.lastIndexOf("\\") != 		destinationFolderPath.length() - 1) 		{
			destinationFolderPath += "/";
		}
		String filePath = new String(destinationFolderPath + docId + "." +format.getDOSExtension());
		doc.getFile(filePath);
		return filePath;
	}
	public String getFolderPath(String objectId) throws Exception{
		String methodName="getFolderPath";
		logger.info("Inside Method"+methodName);
		String folderPath = new String("") ;
		IDfQuery idfQuery = new DfQuery();
		IDfCollection idfCollection = null;
		idfQuery.setDQL(DQLGetFolderPath.replace("$r_object_id$",objectId));
		idfCollection = idfQuery.execute(getDocumemtumSession(),DfQuery.DF_EXEC_QUERY);
		logger.info("Executed Query");
		while(idfCollection.next()){
			if(null != idfCollection.getString("r_folder_path") && !idfCollection.getString("r_folder_path").trim().equals("")){
				folderPath =  idfCollection.getString("r_folder_path") ;
			}
				
		}
		idfCollection.close();
		logger.info("Folder Path"+folderPath);
		return folderPath;
	}
	public void beginBatchLevelDocumentumTransaction() throws Exception{
		getDocumemtumSession().flush("querycache","");
		getDocumemtumSession().flush("aclcache","");
		getDocumemtumSession().flush("groupcache","");
		getDocumemtumSession().flush("persistentcache","");
		getDocumemtumSession().flush("persistentobjcache","");
		try{
			if(getDocumemtumSession() != null &&  null == batchLevelTransaction){
				batchLevelTransaction = getDocumemtumSession().beginTransEx();
			}
		}catch(Exception e){
			logger.severe("error inside begin transaction"+e);
			throw e;
		}
	}
	public void commitBatchLevelDocumentumTransaction() throws Exception{
		try{
			if(getDocumemtumSession() != null && null != batchLevelTransaction){
				getDocumemtumSession().commitTransEx(batchLevelTransaction);
				batchLevelTransaction=null;
			}
		}catch(Exception e){
			logger.severe("error inside commit transaction"+e);
			throw e;
		}
	}
	public void beginRecordLevelDocumentumTransaction() throws Exception{
		try{
			if(getDocumemtumSession() != null && null == recordLevelTransaction ){
				 recordLevelTransaction = getDocumemtumSession().beginTransEx() ;
			}
		}catch(Exception e){
			logger.severe("error inside begin transaction"+e);
			throw e;
		}
	}
	public void commitRecordLevelDocumentumTransaction() throws Exception{
		try{
			if(getDocumemtumSession() != null && null!=recordLevelTransaction){
				getDocumemtumSession().commitTransEx(recordLevelTransaction);
				recordLevelTransaction = null;
			}
			if(supervisorSession!= null && recordLevelTransactionSupervisor != null ){
				getSupervisorSession().commitTransEx(recordLevelTransactionSupervisor);   
				getSupervisorSession().disconnect();
				setSupervisorSession(null);
				recordLevelTransactionSupervisor=null;
		   }
		}catch(Exception e){
			logger.severe("error inside commit transaction"+e);
			throw e;
		}
	}
	public void abortRecordLevelDocumentumTransaction() throws Exception{
		try{
			if(getDocumemtumSession() != null && null!=recordLevelTransaction){
				getDocumemtumSession().abortTransEx(recordLevelTransaction);
				recordLevelTransaction = null;
			}
			if(supervisorSession!= null && recordLevelTransactionSupervisor != null ){
				getSupervisorSession().abortTransEx(recordLevelTransactionSupervisor);   
				getSupervisorSession().disconnect();
				setSupervisorSession(null);
				recordLevelTransactionSupervisor=null;
		   }
		}catch(Exception e){
			logger.severe("error inside abortRecordLevelDocumentumTransaction "+e);
			throw e;
		}
	}
	public void recordTransactionBegin() throws Exception{
		  recordLevelTransaction = getDocumemtumSession().beginTransEx();
	}
	public void batchTransactionBegin() throws Exception{
		  batchLevelTransaction = getDocumemtumSession().beginTransEx();
	}
	public IDfSysObject createNewObject(String repositoryObjectName,
			String fileLocation, String repositoryPath,String newObjectId) throws Exception {
		/** this method creates a import file in the repository
		 *  if repository path does not exist then it creates those folders in the repository
		**/
		IDfSysObject sysObject  = null;
		try{
			logger.info("repositoryObjectName"+repositoryObjectName);
			if(newObjectId != null && !newObjectId.equals("")){
				// This is delta migration. object id is maintained since work flow may be triggered on document
				sysObject = (IDfSysObject)  getDocumemtumSession().getObject(new DfId(newObjectId));
				if( sysObject==null) throw new Exception("Object does not exist in the repository");
				sysObject.checkout();
				logger.info("checked out successfully" );
				IDfCheckinOperation cio = clientx.getCheckinOperation();
				cio.setCheckinVersion(IDfCheckinOperation.SAME_VERSION);
				IDfCheckinNode node = (IDfCheckinNode) cio.add(sysObject);
				node.setFilePath(fileLocation);
				cio.setSession(getDocumemtumSession());
				node.setKeepLocalFile(true);
				if (!cio.execute())
				{
					throw new Exception("Checkin failed.");
				}
				logger.info("checked in successfully" );
			}
			else{
				logger.info("before get Path");
				IDfFormatRecognizer oFormatRec = clientx.getFormatRecognizer(getDocumemtumSession(),fileLocation,null);
				String fileFormatName = oFormatRec.getDefaultSuggestedFileFormat();
				logger.info("after get Path");
				sysObject =(IDfSysObject)  getDocumemtumSession().newObject(repositoryObjectName);
				sysObject.setPath(fileLocation,fileFormatName,0,null);
				sysObject.link(repositoryPath);
			}
		}catch(Exception e){
			logger.severe("Errro in createNewObject"+e);
			throw e;
		}
	    return sysObject;
	}
	public void createFolderByPath(String repoPath) throws Exception{
		try{
			logger.info("repoPath"+repoPath);
			String folderName[] = repoPath.split("/");
			String repoLocation = new String("");
			for(int i=0;i<folderName.length;i++){
				if(!folderName[i].equals("")){
					repoLocation = repoLocation + "/"+folderName[i];
					logger.info(repoLocation+"folder name"+folderName[i]);
					IDfFolder idfFolder =  getDocumemtumSession().getFolderByPath(repoLocation) ;
					if(idfFolder==null){
						repoLocation = repoLocation + "/"+folderName[i];
						IDfFolder idfNewFolder = (IDfFolder)getDocumemtumSession().newObject("dm_folder");
						idfNewFolder.setObjectName(folderName[i]);
						idfNewFolder.link(repoLocation.substring(0,repoLocation.length()- folderName[i].length()-1));
						idfNewFolder.save();
					}
				}
			}
		} catch(Exception e){
			logger.severe("error inside create folder"+e);
			throw e;
		}
		
	}
	public String getAnnotationObjectId(String objectId) throws Exception{
		String annotationObject = new String("");
		try{
			logger.info("Inside getAnnotationObjectId");
			IDfQuery query= new DfQuery();
			query.setDQL("select child_id from dm_relation where parent_id='"+objectId+"' and relation_name='DM_ANNOTATE'");
			IDfCollection idfCollection =query.execute(getDocumemtumSession(), IDfQuery.EXECREAD_QUERY) ;
			while(idfCollection.next()){
				annotationObject = idfCollection.getString("child_id");
			}
			
		}catch(Exception e){
			logger.severe("Error in getAnnotationObjectId"+e);
			throw e;
		}
		return annotationObject;	
	}
	public void createRelationShip(String parentId, String childId , String relationName) throws Exception{
		try{
			logger.info("Inside createRelationShip");
			IDfRelation idfRelation = (IDfRelation ) getDocumemtumSession().newObject("dm_relation");
			idfRelation.setParentId(new DfId(parentId));
			idfRelation.setChildId(new DfId(childId));
			idfRelation.setRelationName(relationName);
			idfRelation.setBoolean("permanent_link", true);
			idfRelation.save();
		}catch (Exception e){
			logger.severe("Error in createRelationShip"+e);
			throw e;
		}
	}
	public void removeRelationShip(String parentId,  String relationName) throws Exception{
		try{
			logger.info("Inside removeRelationShip");
			String queryString = new String("delete dm_relation object where parent_id ='"+parentId+"' and relation_name='"+relationName+"'") ;
			IDfQuery idfQuery = new DfQuery();
			idfQuery.setDQL(queryString);
			IDfCollection idfCollection = idfQuery.execute(getDocumemtumSession(),DfQuery.DF_EXEC_QUERY);
			idfCollection.close();
		
		}catch (Exception e){
			logger.severe("Error in removeRelationShip"+e);
			throw e;
		}
	}
	public String getUserLoginName(String userName) throws Exception{
		try{
			String userLoginName =new String("");
			logger.info("Inside getUserLoginName");
			String queryString = new String("select user_login_name from dm_user   where user_name ='"+userName.replace("'", "''")+"' ") ;
			logger.info(queryString);
			IDfQuery idfQuery = new DfQuery();
			idfQuery.setDQL(queryString);
			IDfCollection idfCollection = idfQuery.execute(getDocumemtumSession(),DfQuery.DF_EXEC_QUERY);
			while(idfCollection.next()){
				userLoginName= idfCollection.getString("user_login_name");
			}
			idfCollection.close();
			logger.severe("User Id "+userLoginName);
			return userLoginName;
		}catch (Exception e){
			logger.severe("Error in removeRelationShip"+e);
			throw e;
		}
	}
	public IDfSession getSupervisorSession() {
		return supervisorSession;
	}
	public void setSupervisorSession(IDfSession suervisorSession) {
		this.supervisorSession = suervisorSession;
	}
	public void MTSEL_UpdateRegiterTable(String oldObjectId,String newObjectId) throws Exception{
		String methodName="getFolderPath";
		logger.info("Inside Method"+methodName);
		String queryString = new String("update dm_dbo.SIGI_DOCUMENT_WORKFLOW_STATUS set  DOCUMENT_OBJECT_ID  ='%1$s'  where DOCUMENT_OBJECT_ID  ='%2$s'  ") ;
		queryString = String.format(queryString,newObjectId,oldObjectId);
		IDfQuery idfQuery = new DfQuery();
		IDfCollection idfCollection = null;
		idfQuery.setDQL(queryString);
		try{
			idfCollection = idfQuery.execute(getDocumemtumSession(),DfQuery.DF_EXEC_QUERY);
			logger.info("Executed Query");
			while(idfCollection.next()){
			}
			idfCollection.close();
		}catch(Exception e ){
			logger.severe("Error while updating SIGI_DOCUMENT_WORKFLOW_STATUS status"+e);
			throw e;
		}
		queryString = String.format( "update dm_dbo.SIGI_REQUEST_ORIGINAL_STATUS set  DOCUMENT_ID  ='%1$s'  where DOCUMENT_ID  ='%2$s' ",newObjectId,oldObjectId);
		idfQuery.setDQL(queryString);
		try{
			idfCollection = idfQuery.execute(getDocumemtumSession(),DfQuery.DF_EXEC_QUERY);
			logger.info("Executed Query");
			while(idfCollection.next()){
			}
			idfCollection.close();
		}catch(Exception e ){
			logger.severe("Error while updating SIGI_REQUEST_ORIGINAL_STATUS status"+e);
			throw e;
		}
		 
	}
	public void MTSEL_LinktoIDS( IDfSysObject sysobject ,String newObjectId ,String folderPath)throws Exception{
		String methodName="MTSEL_LinktoIDS";
		try {
		logger.info("Inside Method"+methodName);
		String PUBLISH_TO_IDS ="";
		//applicable only for sigi_document_claim
			if(sysobject.getString("r_object_type").equals("sigi_document_claim") && ( newObjectId==null ||  newObjectId.equals(""))){ //If new object id is null means the IDS folder has already been created
				String externalSystemId = sysobject.getString("external_system_id") ;
				if ( externalSystemId== null )  externalSystemId  ="";
				if( (sysobject.getString("claim_status").equals("O") ||  sysobject.getString("claim_status").equals("P")  ) &&	!externalSystemId.equals( "MCS") ){
					String subDocType = sysobject.getString("sub_document_type");
					String docType = sysobject.getString("document_type");
					String formNumber= sysobject.getString("form_number");
					String queryString = new String("select PUBLISH_TO_IDS FROM dm_dbo.SIGI_CLAIMS_DOCUMENT_CONFIG WHERE    DOC_SUB_TYPE  ='%1$s' AND  FORM_NUMBER ='%2$s'  AND DOCUMENT_TYPE ='%3$s'   ") ;
					queryString = String.format(queryString ,subDocType.replace("'","''"),formNumber.replace("'","''"),docType.replace("'","''"));
					IDfQuery idfQuery = new DfQuery();
					IDfCollection idfCollection = null;
					idfQuery.setDQL(queryString);
					try{
						idfCollection = idfQuery.execute(getDocumemtumSession(),DfQuery.DF_EXEC_QUERY);
						logger.info("Executed Query"+idfQuery.getDQL());
						while(idfCollection.next()){
							PUBLISH_TO_IDS=idfCollection.getString("publish_to_ids");
						}
						idfCollection.close();
					}catch(Exception e ){
						logger.severe("Error while GETTING  PUBLISH_TO_IDS flag"+e);
						throw e;
					}
					if(PUBLISH_TO_IDS.equals("Y")){
						DfTime  dftime =(DfTime) sysobject.getTime("date_closed");
						Date currentDate = new Date();
						DfTime  createTime = new DfTime(currentDate);
						String datePath =createTime.asString("yyyy/MM/dd/HH/mm");
						logger.info("datePath"+datePath);
						String prospectFolderName=datePath.substring(0, 14)+(int )Math.ceil(Double.parseDouble(datePath.substring(14))/15) +"/"+sysobject.getString("r_object_id");
						logger.info("prospectFolderName"+prospectFolderName);
						boolean canPublish = true;
						/*TimeUnit timeunit = TimeUnit.DAYS;
						
						if(dftime!=DfTime.DF_NULLDATE&&timeunit.convert((System.currentTimeMillis()-dftime.getDate().getTime()), TimeUnit.MILLISECONDS)> 365){
							canPublish=false;
						}*/
						if(canPublish){
							String idsFolderPath= MTSEL_createIDSSubFolder(getDocumemtumSession(),"/Claims/IDS",folderPath);
							sysobject.link(idsFolderPath);
						}
					}
			}
				
			}
		}catch(Exception e ){
			logger.severe("Erorr inMTSEL_LinktoIDS"+e);
			throw e ;
		}
		logger.info("Exiting Method"+methodName);
	}
	private   String MTSEL_createIDSSubFolder(IDfSession currentSession, String sourceFolderPath, String prospectFolderName) throws Exception
	{
		try
		{
			String methodName="MTSEL_createIDSSubFolder";
			logger.info("Inside Method"+methodName);
			IDfFolder idsSubFolder =null;
			idsSubFolder = (IDfFolder) currentSession.getFolderByPath( sourceFolderPath+"/"+prospectFolderName);
			if(idsSubFolder ==null){
				idsSubFolder = (IDfFolder) currentSession.newObject("dm_folder");
				idsSubFolder.setObjectName(prospectFolderName);
				idsSubFolder.setACLName("sigi_ids_folder");
				idsSubFolder.setACLDomain("ecm_dmadmin");
				idsSubFolder.setOwnerName("ecm_dmadmin");
				idsSubFolder.link(sourceFolderPath);
				idsSubFolder.save();
				logger.info("Exiting Method"+methodName);
			}
		    return idsSubFolder.getFolderPath(0);
		}
		catch ( Exception e)
		{
			logger.severe(  "MTSEL_createIDSSubFolder " + e.getMessage() );
			throw e ;
		}
	}
	 
}
