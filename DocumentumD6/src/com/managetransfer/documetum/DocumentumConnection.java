package com.managetransfer.documetum;

import java.util.ArrayList;
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
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfLoginInfo;
import com.documentum.operations.IDfExportNode;
import com.documentum.operations.IDfExportOperation;
import com.documentum.operations.IDfFormatRecognizer;
import com.managetransfer.record.Record;

public class DocumentumConnection {
	private DfClientX clientx = null;
	private IDfClient client = null;
	private String  usrName = new String("dmadmin");
	private String  userPsw	 = new String("TEGINS@123");
	private String  repoName = new String ("documentum");
	private IDfSessionManager sessionMgr = null;
	IDfLocalTransaction recordLevelTransaction = null;
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
	public static void main(String[] args) throws Exception{
		 
		 DocumentumConnection cd = new DocumentumConnection();
		 cd.connectRepository();
		 System.out.println(cd.getDocumemtumSession());
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
	public void disconnectSession(){
		getSessionMgr().release(getDocumemtumSession());
		 
	}
	public void recordTransactionBegin() throws Exception{
		  recordLevelTransaction = getDocumemtumSession().beginTransEx();
	}
	public void batchTransactionBegin() throws Exception{
		  batchLevelTransaction = getDocumemtumSession().beginTransEx();
	}
	public void recordTransactionRollBack() throws Exception{
		getDocumemtumSession().abortTransEx(recordLevelTransaction);
	}
	public void batchTransactionCommit() throws Exception{
		getDocumemtumSession().commitTransEx(recordLevelTransaction);
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
		}catch(Exception e){
			logger.severe("error inside commit transaction"+e);
			throw e;
		}
	}
	public IDfSysObject createNewObject(String repositoryObjectName,
			String fileLocation, String repositoryPath) throws Exception {
		/** this method creates a import file in the repository
		 *  if repository path does not exist then it creates those folders in the repository
		**/
		IDfSysObject sysObject  = null;
		try{
			logger.info("repositoryObjectName"+repositoryObjectName);
			sysObject =(IDfSysObject)  getDocumemtumSession().newObject(repositoryObjectName);
			sysObject.link(repositoryPath);
			logger.info("before get Path");
			IDfFormatRecognizer oFormatRec = clientx.getFormatRecognizer(getDocumemtumSession(),fileLocation,null);
			String fileFormatName = oFormatRec.getDefaultSuggestedFileFormat();
			logger.info("after get Path");
			sysObject.setPath(fileLocation,fileFormatName,0,null);
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
			idfRelation.save();
		}catch (Exception e){
			logger.severe("Error in createRelationShip"+e);
			throw e;
		}
	}
}
