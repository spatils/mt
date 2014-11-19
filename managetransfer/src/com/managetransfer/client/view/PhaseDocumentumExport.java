package com.managetransfer.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.ListBox;

public class PhaseDocumentumExport extends Composite implements HasText {
	@UiField CheckBox createFolder;
	@UiField TextBox  exportQuery = new TextBox();
	@UiField TextBox  repeatAttributeDQL = new TextBox();
	@UiField TextBox  exportLocation = new TextBox();

	@UiField ListBox connectionList;
	@UiField ListBox sourceObjectList ;
	ArrayList<String> connectionListString = new ArrayList<String>();
	ArrayList<String> sourceObjListString = new ArrayList<String>();
	private static PhaseDocumentumExportUiBinder uiBinder = GWT
			.create(PhaseDocumentumExportUiBinder.class);

	interface PhaseDocumentumExportUiBinder extends
			UiBinder<Widget, PhaseDocumentumExport> {
	}

	public PhaseDocumentumExport() {
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
		phaseDetailsString.put("SourceObject", sourceObjListString.get(sourceObjectList.getSelectedIndex()));
		 
		phaseDetailsString.put("ExportQuery", exportQuery.getValue());
		phaseDetailsString.put("ExportLocation" , exportLocation.getValue());
		phaseDetailsString.put("RepeatAttributeDQL" , repeatAttributeDQL.getValue());
		return phaseDetailsString;
	}
	public HashMap<String,Integer> getPhaseDetailsInteger() {
		HashMap<String,Integer> phaseDetailsInteger = new HashMap<String,Integer>();
		if(createFolder.getValue()){
			phaseDetailsInteger.put("CreateFolder", 1);
		}
		else{
			phaseDetailsInteger.put("CreateFolder", 0);
		}
		return phaseDetailsInteger;
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
				for(int i=0;i < sourceObjListString.size(); i++){
					if(sourceObjListString.get(i).equals(phaseDetailsString.get("SourceObject"))){
						sourceObjectList.setSelectedIndex(i);
						break;
					}
				}
			}else{
				 
			}
			 
			if (phaseDetailsString.containsKey("ExportQuery")){
				exportQuery.setValue(phaseDetailsString.get("ExportQuery"));
			}else{
				exportQuery.setValue("");
			}
			if (phaseDetailsString.containsKey("ExportLocation")){
				exportLocation.setValue(phaseDetailsString.get("ExportLocation"));
			}else{
				exportLocation.setValue("");
			}
			if (phaseDetailsString.containsKey("RepeatAttributeDQL")){
				repeatAttributeDQL.setValue(phaseDetailsString.get("RepeatAttributeDQL"));
			}else{
				exportLocation.setValue("");
			}
			
		 }else{
			  
			 
			  
			 exportQuery.setValue("");
			 exportLocation.setValue("");
		 }
	}
	public HashMap<String,Date> getPhaseDetailsDate() {
		HashMap<String,Date> phaseDetailsDate = new HashMap<String,Date>();
		return phaseDetailsDate;
	}
	public void setPhaseDetailsInteger( HashMap<String,Integer> phaseDetailsInt) {
		if ( phaseDetailsInt.containsKey("CreateFolder")){
			if ( phaseDetailsInt.get("CreateFolder") == 0 ){
				createFolder.setValue(false);
			}else{
				createFolder.setValue(true);
			}
				 
		}else{
			createFolder.setValue(false);
		}
	}
	public void setReadOnly(boolean isReadOnly) {
 		 
		exportQuery.setReadOnly(isReadOnly);
		exportLocation.setReadOnly(isReadOnly);
		repeatAttributeDQL.setReadOnly(isReadOnly);
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
	public void  setSourceObjectList (ArrayList<String>  sourceObjectNames ){
		sourceObjListString = sourceObjectNames;
		if(sourceObjectList!=null &  sourceObjectList.getItemCount() !=0){
			sourceObjectList.clear();
		}
		if( sourceObjListString !=null){
			for(int i=0; i < sourceObjListString.size(); i++){
				sourceObjectList.addItem(sourceObjListString.get(i));
			}
		}
		 
	}
		 
	 

}
