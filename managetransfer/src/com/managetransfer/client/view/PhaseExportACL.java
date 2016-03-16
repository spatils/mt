package com.managetransfer.client.view;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PhaseExportACL extends Composite implements HasText {
	
	@UiField ListBox connectionList;
	@UiField TextArea exportQuery;
	ArrayList<String> connectionListString = new ArrayList<String>();
	private static PhaseExportACLUiBinder uiBinder = GWT
			.create(PhaseExportACLUiBinder.class);

	interface PhaseExportACLUiBinder extends UiBinder<Widget, PhaseExportACL> {
	}

	public PhaseExportACL() {
		initWidget(uiBinder.createAndBindUi(this));
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
		phaseDetailsString.put("ExportQuery",exportQuery.getValue());
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
			 
			if(phaseDetailsString.containsKey("ExportQuery")){
				exportQuery.setValue(phaseDetailsString.get("ExportQuery"));
			}
			
		 }
		 
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
}
