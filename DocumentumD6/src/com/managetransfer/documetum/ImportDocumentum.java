package com.managetransfer.documetum;

import com.documentum.fc.client.IDfFolder;
import com.documentum.operations.IDfFile;
import com.documentum.operations.IDfImportNode;
import com.documentum.operations.IDfImportOperation;

public class ImportDocumentum {

	private DocumentumConnection cd = new DocumentumConnection();
	private String destinationFolderPath = new String ("/TestData");
	private String srcFilePath = new String ("D:\\hello.pdf");
	public static void main(String[] args) throws Exception {
		ImportDocumentum id = new ImportDocumentum();
		id.initOperation();
		for(int i=0;i<19; i++){
			id.importDocument();
		}
		
	}
	public void initOperation() throws Exception{
		//Get Session
		cd.setRepoName("documentum");
		cd.setUserPsw("TEGINS@123");
		cd.setUsrName("dmadmin");
		cd.connectRepository();
	}
	public String importDocument() throws Exception{

		// Use the factory method to create an IDfImportOperation instance.
		IDfImportOperation opi = cd.getClientx().getImportOperation();
		// You must explicitly set the session for an import operation.
		opi.setSession(cd.getDocumemtumSession());
		// Create an instance of the target folder.
		IDfFolder folder = cd.getDocumemtumSession().getFolderByPath(destinationFolderPath);
		// Create a file instance for the local file.
		IDfFile file = cd.getClientx().getFile(srcFilePath);
		if (!file.exists())
			return ("File does not exist.");
		// Set the destination folder.
		opi.setDestinationFolderId(folder.getObjectId());
		// Create the import node, adding the file to the import operation.
		IDfImportNode node = (IDfImportNode) opi.add(file);
		if (node == null)
			return ("Node is null.");
		// Execute the import operation and return the results.
		node.setDocbaseObjectType("claims");
		if (opi.execute())
		{
			String resultString =
					("Item" + opi.getNewObjects().toString() +
							" imported successfully.");
			return resultString;
		}
		else
		{
			return ("Error during import operation.");
		}
		// Handle any exceptions.
	} 

}
