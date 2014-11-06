package com.managetransfer.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.PhasesDetails;
public interface PhasesView<T>   {

	public interface Presenter<T> {
		 void onSearchButtonClicked();
		 void onSearchEditButtonClicked();
		 void onSearchDetailsButtonClicked();
		 void onPhaseTypeSelected();
		 void onAddButtonClicked();
		 void onSaveButtonClicked();
		 void onDeleteButtonClicked();
		 void onTestButtonClicked();
		 void onItemSelected(T selectedItem);
		 void onItemClicked(PhasesDetails phaseDetails);
		 ArrayList<String> getPhaseTypeList();
		 ArrayList<String> getConnectionList();
		 ArrayList<String> getSourceObjectList();
		 ArrayList<String> getTargetObjectList();
		 ArrayList<String> getTransformationList();
	 }
	/** Generic function**/
	HasValue<String> getSearchString();
	void setRowData(List<T> rowData);
	String getDeletePhaseString();
	Widget asWidget();
	void setPresenter(Presenter<T> presenter);
	void setDropDown();
	void setDropDownConnection();
	void setDropDownSourceObject();
	void setDropDownTargetObject();
	void setDropDownTransformation();
	HasValue<String> getPhases(); //Gets all phases
	void displayPhaseInformation(boolean readOnly ,PhasesDetails phaseDetails );
	String getSelectedPhase();
	String getSelectedPhaseType();
	String getNewPhaseName();
	String getNewPhaseType();
	HashMap<String,String> getPhaseParametersString();
	HashMap<String,Integer> getPhaseParametersInteger();
	HashMap<String,Date> getPhaseParametersDate();
	
	
	/** Attribute specific functions **/
}
