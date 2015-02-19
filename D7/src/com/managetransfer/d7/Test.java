package com.managetransfer.d7;

import com.documentum.fc.client.IDfFolder;

import com.documentum.fc.client.IDfSysObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EClassifier;

public class Test {
	public static void main(String[] arg) throws Exception{
		Test test = new Test();
		test.testing();
		//test.formatterTesting(); 
	}
	public void testing() throws Exception{
		DocumentumConnection dc = new  DocumentumConnection();
		XCPWorkflowMethods xcpWM = new XCPWorkflowMethods();
		
		dc.connectRepository();
		//xcpWM.setDc(dc);
		//xcpWM.InitiateWorkflow();
		Test test = new Test();
		test.createFolderObject(dc);
		
	}
	public void formatterTesting(){
		String sampleData = " This is %1$s %2$s %3$s %4$B";
		System.out.println(String.format(sampleData, "one","two","three",true));
	}
	public void createFolderObject(DocumentumConnection dc) throws Exception{
		IDfFolder folderObject = (IDfFolder) dc.getDocumemtumSession().newObject("sigi_folder_claim");
		folderObject.setObjectName("test");
		folderObject.link("/Claims");
		folderObject.setACLName("sigi_claim");
		folderObject.setACLDomain("ecm_dmadmin");
		folderObject.save();
		
		
	}
}    
