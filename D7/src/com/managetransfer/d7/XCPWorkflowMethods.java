package com.managetransfer.d7;

import com.documentum.fc.client.IDfActivity;
import com.documentum.fc.client.IDfProcess;

import com.documentum.fc.client.IDfWorkflow;
import com.documentum.fc.client.IDfWorkflowBuilder;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.DfList;
import com.documentum.fc.common.DfLogger;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfList;

public class XCPWorkflowMethods {
	DocumentumConnection dc = new DocumentumConnection();
	String wf_name = new String("AdjusterNotifyWorkflow");
	String documentObjectId   = new String ("0902334080028ba9");
	String boObjectId = new String("0802334080029616");
	public void InitiateWorkflow ( ) throws Exception{
		try{
			IDfProcess wfTemplate = (IDfProcess) dc.getDocumemtumSession().getObjectByQualification("dm_process WHERE object_name = '" + wf_name + "'");
			if (wfTemplate != null)
			{
				
				IDfWorkflowBuilder wfBldrObj = dc.getDocumemtumSession().newWorkflowBuilder(wfTemplate.getObjectId());
				IDfWorkflow workflow = wfBldrObj.getWorkflow();
				wfBldrObj.initWorkflow();
				wfBldrObj.runWorkflow();
	
				IDfList startActivityNames = wfBldrObj.getStartActivityNames();
				IDfList startActivityIds = wfBldrObj.getStartActivityIds();
				String startActivityName = startActivityNames.getString(0);
				IDfId startActivityId = startActivityIds.getId(0);
				IDfActivity activity = (IDfActivity) dc.getDocumemtumSession().getObject(startActivityId);
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
							String typeName = new String("");
							System.out.println(inPackageName);
							IDfList dfList = new DfList();
							if(inPackageType.equals("sigi_document_claim")){
								typeName="sigi_document_claim";
								dfList.appendId(new DfId(documentObjectId));
							}else if(inPackageType.equals("sigi_workflow_data")){
								typeName="sigi_workflow_data"; 
								dfList.appendId(new DfId(boObjectId));
							}
							
							_First_pkgId = workflow.addPackage(startActivityName, inPortName, inPackageName, typeName, null, false, dfList);
							 
	
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
	public DocumentumConnection getDc() {
		return dc;
	}

	public void setDc(DocumentumConnection dc) {
		this.dc = dc;
	}
	
}

