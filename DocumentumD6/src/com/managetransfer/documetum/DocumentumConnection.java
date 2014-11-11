package com.managetransfer.documetum;

import java.util.logging.Logger;

import com.documentum.com.DfClientX;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfDocument;
import com.documentum.fc.client.IDfFormat;
import com.documentum.fc.client.IDfLocalTransaction;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.IDfLoginInfo;
import com.documentum.operations.IDfExportNode;
import com.documentum.operations.IDfExportOperation;

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
			"select r_folder_path from  dm_folder where r_object_id in ( select i_folder_id from dm_document where r_object_id ='$r_object_id$'  ) and any r_folder_path is not nullstring ");
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
		IDfDocument doc = 		(IDfDocument)  getDocumemtumSession().getObject(new DfId(docId));
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
				folderPath = idfCollection.getRepeatingString("r_folder_path", 0)  ;
		}
		idfCollection.close();
		logger.info("Folder Path"+folderPath);
		return folderPath;
	}
}
