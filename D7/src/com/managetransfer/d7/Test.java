package com.managetransfer.d7;

import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfSysObject;
import com.managetransfer.businessobject.ProcessData;

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
		xCPWorkflowHandler xcpWM = new xCPWorkflowHandler();
		dc.setRepoName("SIGI_CLM_DEV");
		dc.setUsrName("ecm_dmadmin");
		dc.setUserPsw("gVRcn85Lu9#M");
		ProcessData pd = new ProcessData();
		pd.setPerformerName("ecm_dmadmin");
		pd.setProcessName("AdjusterNotifyWorkflow");
		pd.setWorkflowSupervisor("ecm_dmadmin");
		dc.connectRepository();
		xcpWM.setDc(dc);
		xcpWM.setProcessData(pd);
		xcpWM.setDocumentObjectId("090233408003f3b2");
		xcpWM.InitiateWorkflow();
		Test test = new Test();
		//test.createFolderObject(dc);
		 
		
	}
	public void formatterTesting(){
		String sampleData = " This is %1$s %2$s %3$s %4$B";
		System.out.println(String.format(sampleData, "one","two","three",true));
	}
	public void createFolderObject(DocumentumConnection dc) throws Exception{
		IDfFolder folderObject = (IDfFolder) dc.getDocumemtumSession().newObject("sigi_folder_claim");
		folderObject.setObjectName("test");
		folderObject.link("/Claims");
		//System.out.println(folderObject.getAttrDataType("performer_name"));
		folderObject.setACLName("sigi_claim");
		folderObject.setACLDomain("ecm_dmadmin");
		folderObject.save();
		
		
	}
}    
