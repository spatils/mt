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
import com.google.gwt.user.client.ui.Widget;

public class PhaseTransformation extends Composite implements HasText {
	@UiField ListBox transformationList ;
	ArrayList<String> transformationListString = new ArrayList<String>();
	private static PhaseTransformationUiBinder uiBinder = GWT
			.create(PhaseTransformationUiBinder.class);

	interface PhaseTransformationUiBinder extends
			UiBinder<Widget, PhaseTransformation> {
	}

	public PhaseTransformation() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	 

	public PhaseTransformation(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		 
	}
	public HashMap<String,String> getPhaseDetailsString() {
		HashMap<String,String> phaseDetailsString = new HashMap<String,String>();
		phaseDetailsString.put("Transformation", transformationListString.get(transformationList.getSelectedIndex()));
		return phaseDetailsString;
	}
	public void setPhaseDetailsString( HashMap<String,String> phaseDetailsString) {
		 if(phaseDetailsString != null){
			if (phaseDetailsString.containsKey("Transformation")){
				for(int i=0;i < transformationListString.size(); i++){
					if(transformationListString.get(i).equals(phaseDetailsString.get("Transformation"))){
						transformationList.setSelectedIndex(i);
						break;
					}
				}
			}else{
				 
			}
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
	public void  setTransformationList (ArrayList<String>  sourceObjectNames ){
		transformationListString = sourceObjectNames;
		if(transformationList!=null &  transformationList.getItemCount() !=0){
			transformationList.clear();
		}
		if( transformationListString !=null){
			for(int i=0; i < transformationListString.size(); i++){
				transformationList.addItem(transformationListString.get(i));
			}
		}
		 
	}
	public void setText(String text) {
		 
	}

	public String getText() {
		 
		return null;
	}

}
