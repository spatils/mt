package com.managetransfer.d7;

import java.io.File;
import java.text.SimpleDateFormat;

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
 
import com.documentum.fc.client.IDfClient;
 
import com.documentum.operations.IDfFile;
import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfTime;
import com.documentum.fc.common.IDfLoginInfo;
import com.documentum.fc.common.IDfTime;
import com.documentum.operations.*;
import com.documentum.fc.common.DfLogger;
import  com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.IDfList;
public class ImportSampleDocument {

	IDfSession session = null;
	 
 	private static String psw = new String ("gVRcn85Lu9#M");
	private static String repo = new String ("SIGI_CLM_DEV");
	private static String user = new String("ecm_dmadmin");
	//*/
	
	public static void main(String[] args) throws Exception {
		ImportSampleDocument ss = new ImportSampleDocument ();
		ss.Connect();
		System.out.println("got Session");
		 
		for (int i= 0;i<1;i++){
			ss.importPolicyDocument(ss.session);
		}
		
		 
		//ss.importPolicyFolder(ss.session, "c:\\testdocs");
		ss.session.disconnect();
	}
	public void Connect() throws Exception{
		IDfClientX clientX = new DfClientX();
		IDfClient client = clientX.getLocalClient();
		IDfSessionManager sMgr = client.newSessionManager();
		IDfLoginInfo loginInfo = clientX.getLoginInfo();
		loginInfo.setUser(user);
		loginInfo.setPassword(psw);
		sMgr.setIdentity( repo, loginInfo );
		session = sMgr.getSession(repo);
	}

	public void importPolicyDocument(IDfSession session) throws Exception{
		IDfClientX clientX = new DfClientX();
    	// Construct a	new import operation
    	IDfImportOperation opImport = clientX.getImportOperation();
    	// Set operation object properties
    	opImport.setSession(session);
    	
    	// Add file to import and cast node to the specific type
    	//IDfFile file = clientX.getFile("c:\\renee_0003.pdf");
    	IDfFile file = clientX.getFile("V:\\Documents\\docu45955_Documentum-6.7SP2-Web-Services-for-Records-Manager-and-Retention-Policy-Services-Deployment-Guide.pdf");
    	
    	if (!file.exists()){
    		System.out.println("file doens not exist");
    		return ;
    	}
    	IDfImportNode node = (IDfImportNode)opImport.add(file);
    	if( node == null ) {  
    		DfLogger.error(this, "error generating node ", null, null); 
    		System.out.println("error  ");
    	}
    	System.out.println("Set Node ");
    	node.setDocbaseObjectType("sigi_document_claim");
    	IDfFolder folder = session.getFolderByPath("/Temp");
    	node.setDestinationFolderId(folder.getObjectId());
    	opImport.execute();
    	IDfList list = opImport.getNewObjects();
    	IDfSysObject sysObj=null;
    	int count = list.getCount();
    	if ( count > 0 ){
    		for( int i = 0; i < count;  i++ ) {
		    	sysObj = (IDfSysObject)list.get( i );
		    	System.out.println("ObjectId "+sysObj.getObjectId());
		    	//sysObj.setString("document_control_number", "P1");
		    	//sysObj.setString("policy_number", "P1");
		    	//sysObj.setString("source_system", "ACS");
		    	//sysObj.save();
		    }
    	}
    	 

		
	}
	public void importPolicyFolder(IDfSession session,String folderPath) throws Exception{
		IDfClientX clientX = new DfClientX();
    	// Construct a	new import operation
    	IDfImportOperation opImport = clientX.getImportOperation();
    	// Set operation object properties
    	opImport.setSession(session);
    	
    	File folderOS= new File(folderPath);
    	File[] files =folderOS.listFiles();
    	 
    	for ( int i=0 ; i<files.length;i++){
    		
    		if (files[i].getName().contains(".pdf")){
    			System.out.println("files[i]  "+files[i].getName());
		    	IDfImportNode node = (IDfImportNode)opImport.add(folderPath+"\\"+files[i].getName());
		    	if( node == null ) {  
		    		DfLogger.error(this, "error generating node ", null, null); 
		    		System.out.println("error  ");
		    	}
		    	System.out.println("Set Node ");
		    	node.setDocbaseObjectType("sigi_document_uw");
		    	IDfFolder folder = session.getFolderByPath("/UW");
		    	node.setDestinationFolderId(folder.getObjectId());
    		}
    	}
    	
    	opImport.execute();
    	IDfList list = opImport.getNewObjects();
    	IDfSysObject sysObj=null;
    	int count = list.getCount();
    	if ( count > 0 ){
    		for( int i = 0; i < count;  i++ ) {
		    	sysObj = (IDfSysObject)list.get( i );
		    	sysObj.setString("document_control_number", "12233");
		    	sysObj.setString("source_system", "ACS");
		    	sysObj.save();
		    	
		    	System.out.println("ObjectId "+sysObj.getObjectId());
		    }
    	}
    	 

		
	}


}
