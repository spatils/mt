package com.managetransfer.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.ListBox;

public class PhaseDocumentumMetadataExport extends Composite implements HasText {
	@UiField TextBox  sourceObject = new TextBox();
 
	@UiField TextBox  hostName = new TextBox();
	@UiField TextBox  exportQuery = new TextBox();
	
	
	@UiField ListBox connectionList;
	ArrayList<String> connectionListString = new ArrayList<String>();
	private static PhaseDocumentumMetadataExportUiBinder uiBinder = GWT
			.create(PhaseDocumentumMetadataExportUiBinder.class);

	interface PhaseDocumentumMetadataExportUiBinder extends
			UiBinder<Widget, PhaseDocumentumMetadataExport> {
	}

	public PhaseDocumentumMetadataExport() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public String getText() {
		return null;
	}
	public void setText(String text) {
	
	}
	public HashMap<String,String> getPhaseDetailsString() {
		HashMap<String,String> phaseDetailsString = new HashMap<String,String>();
		phaseDetailsString.put("Connection", connectionListString.get(connectionList.getSelectedIndex()));
		phaseDetailsString.put("SourceObject", sourceObject.getValue());
		phaseDetailsString.put("HostName", hostName.getValue());
		phaseDetailsString.put("ExportQuery", exportQuery.getValue());
		 
		return phaseDetailsString;
	}
	public void setPhaseDetailsString( HashMap<String,String> phaseDetailsString) {
		 if(phaseDetailsString != null){
			if (phaseDetailsString.containsKey("Connection")){
				for(int i=0;i < connectionListString.size(); i++){
					if(connectionListString.get(i).equals(phaseDetailsString.get("Connection"))){
						connectionList.setSelectedIndex(i);
						break;
					}
				}
				
			}else{
				 
			}
			if (phaseDetailsString.containsKey("SourceObject")){
				sourceObject.setValue(phaseDetailsString.get("SourceObject"));
			}else{
				sourceObject.setValue("");
			}
			if (phaseDetailsString.containsKey("HostName")){
				 hostName.setValue(phaseDetailsString.get("HostName"));
			}else{
				hostName.setValue("");
			}
			if (phaseDetailsString.containsKey("ExportQuery")){
				exportQuery.setValue(phaseDetailsString.get("ExportQuery"));
			}else{
				exportQuery.setValue("");
			}
		 }else{
			  
			 sourceObject.setValue("");
			 hostName.setValue("");
			 exportQuery.setValue("");
		 }
	}
	public HashMap<String,Date> getPhaseDetailsDate() {
		HashMap<String,Date> phaseDetailsDate = new HashMap<String,Date>();
		return phaseDetailsDate;
	}
	public HashMap<String,Integer> getPhaseDetailsInteger() {
		HashMap<String,Integer> phaseDetailsInteger = new HashMap<String,Integer>();
		return phaseDetailsInteger;
	}
	public void setReadOnly(boolean isReadOnly) {
		sourceObject.setReadOnly(isReadOnly);
		 
		hostName.setReadOnly(isReadOnly);
		exportQuery.setReadOnly(isReadOnly);
	}
	public void setConnectionList(ArrayList<String> connectionNames){
		connectionListString = connectionNames;
		if(connectionList!=null &  connectionList.getItemCount() !=0){
			connectionList.clear();
		}
		if( connectionListString !=null){
			for(int i=0; i < connectionListString.size(); i++){
				connectionList.addItem(connectionListString.get(i));
			}
		}
		 
	}

}
