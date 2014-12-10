package com.managetransfer.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PhaseDocumentumImport extends Composite implements HasText{
	@UiField ListBox connectionList;
	@UiField ListBox targetObjectList ;
	@UiField CheckBox createFolder;
	@UiField CheckBox expAnnotation;
	@UiField TextBox destinationPath;
	ArrayList<String> connectionListString = new ArrayList<String>();
	ArrayList<String> targetObjListString = new ArrayList<String>();
	private static PhaseDocumentumImportUiBinder uiBinder = GWT
			.create(PhaseDocumentumImportUiBinder.class);

	interface PhaseDocumentumImportUiBinder extends
			UiBinder<Widget, PhaseDocumentumImport> {
	}

	public PhaseDocumentumImport() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		
	}
	public HashMap<String,String> getPhaseDetailsString() {
		HashMap<String,String> phaseDetailsString = new HashMap<String,String>();
		phaseDetailsString.put("Connection", connectionListString.get(connectionList.getSelectedIndex()));
		phaseDetailsString.put("TargetObject", targetObjListString.get(targetObjectList.getSelectedIndex()));
		phaseDetailsString.put("DestinationPath",destinationPath.getValue());
		return phaseDetailsString;
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
		if ( phaseDetailsInt.containsKey("ExpAnnotation")){
			if ( phaseDetailsInt.get("ExpAnnotation") == 0 ){
				expAnnotation.setValue(false);
			}else{
				expAnnotation.setValue(true);
			}
				 
		}else{
			expAnnotation.setValue(false);
		}
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
			if (phaseDetailsString.containsKey("TargetObject")){
				for(int i=0;i < targetObjListString.size(); i++){
					if(targetObjListString.get(i).equals(phaseDetailsString.get("TargetObject"))){
						targetObjectList.setSelectedIndex(i);
						break;
					}
				}
			}else{
				 
			}
			if(phaseDetailsString.containsKey("DestinationPath")){
				destinationPath.setValue(phaseDetailsString.get("DestinationPath"));
			}
			
		 }
		 
	}
	public HashMap<String,Date> getPhaseDetailsDate() {
		HashMap<String,Date> phaseDetailsDate = new HashMap<String,Date>();
		return phaseDetailsDate;
	}
	public HashMap<String,Integer> getPhaseDetailsInteger() {
		HashMap<String,Integer> phaseDetailsInteger = new HashMap<String,Integer>();
		if(createFolder.getValue()){
			phaseDetailsInteger.put("CreateFolder", 1);
		}
		else{
			phaseDetailsInteger.put("CreateFolder", 0);
		}
		if(expAnnotation.getValue()){
			phaseDetailsInteger.put("ExpAnnotation", 1);
		}
		else{
			phaseDetailsInteger.put("ExpAnnotation", 0);
		}
		 
		return phaseDetailsInteger;
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
	public void  setTargetObjectList (ArrayList<String>  sourceObjectNames ){
		targetObjListString = sourceObjectNames;
		if(targetObjectList!=null &  targetObjectList.getItemCount() !=0){
			targetObjectList.clear();
		}
		if( targetObjListString !=null){
			for(int i=0; i < targetObjListString.size(); i++){
				targetObjectList.addItem(targetObjListString.get(i));
			}
		}
		 
	}

}
