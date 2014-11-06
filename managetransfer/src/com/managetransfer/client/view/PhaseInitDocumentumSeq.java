package com.managetransfer.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class PhaseInitDocumentumSeq extends Composite implements HasText {
	@UiField ListBox connectionList;
	@UiField ListBox sourceObjectList ;
	@UiField TextArea exportQuery;
	ArrayList<String> connectionListString = new ArrayList<String>();
	ArrayList<String> sourceObjListString = new ArrayList<String>();
	 
	private static PhaseInitDocumentumSeqUiBinder uiBinder = GWT
			.create(PhaseInitDocumentumSeqUiBinder.class);

	interface PhaseInitDocumentumSeqUiBinder extends
			UiBinder<Widget, PhaseInitDocumentumSeq> {
	}

	public PhaseInitDocumentumSeq() {
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
	public HashMap<String,String> getPhaseDetailsString() {
		HashMap<String,String> phaseDetailsString = new HashMap<String,String>();
		
		phaseDetailsString.put("Connection", connectionListString.get(connectionList.getSelectedIndex()));
		phaseDetailsString.put("SourceObject", sourceObjListString.get(sourceObjectList.getSelectedIndex()));
		phaseDetailsString.put("InitDQL", exportQuery.getValue());
		 
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
				 
				for(int i=0;i < sourceObjListString.size(); i++){
					if(sourceObjListString.get(i).equals(phaseDetailsString.get("SourceObject"))){
						sourceObjectList.setSelectedIndex(i);
						break;
					}
				}
			}else{
				 
			}
			if (phaseDetailsString.containsKey("InitDQL")){
				exportQuery.setValue(phaseDetailsString.get("InitDQL"));
			}else{
				exportQuery.setValue("");
			}
			 
			
		 }else{
			  
			  
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
		exportQuery.setReadOnly(isReadOnly);
	}
	 
}
