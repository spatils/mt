package com.managetransfer.client.view;

import java.util.ArrayList;
import java.util.HashMap;

import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PhaseImportACLD7 extends Composite implements HasText {

	private static PhaseImportACLD7UiBinder uiBinder = GWT
			.create(PhaseImportACLD7UiBinder.class);
	@UiField ListBox connectionList;
	 
	ArrayList<String> connectionListString = new ArrayList<String>();
	interface PhaseImportACLD7UiBinder extends
			UiBinder<Widget, PhaseImportACLD7> {
	}

	public PhaseImportACLD7() {
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
