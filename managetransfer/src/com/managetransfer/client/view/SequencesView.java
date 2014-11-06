package com.managetransfer.client.view;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.SequenceDetails;

public interface  SequencesView<T> {

	public interface Presenter<T> {
		 void onSearchButtonClicked();
		 void onSaveButtonClicked();
		 void onDeleteButtonClicked();
		 void onAddButtonClicked();
		 void onTestButtonClicked();
		 void onItemSelected(T selectedItem);
		 void onItemClicked(SequenceDetails seqeunceDetails);
		 void paintDetails(SequenceDetails seqeunceDetails);
		 ArrayList<String> getMachineTypeList();
		 ArrayList<String> getPhaseTypeList();
		 List<SequenceDetails> getSequenceDetails();
		 SequenceDetails getSelectedItem(String sequenceName);
		 
	}

	void setPresenter(Presenter<T> presenter);
	Widget asWidget();
	HasValue<String> getSequences();
	HasValue<String> getNewSequenceName();
	String getSelectedSequenceName();
	SequenceDetails getSelectedSequence();
	HasValue<String> getNewNumberOfPhases();
	HasValue<String> getRegionName();
	HasValue<String> getSearchString();
	HasValue<String> getDeleteSequenceString();
	void setRowData(List<T> rowData);
	HasValue<String> getEditDataSource();
	HasValue<String> getEditSequenceName();
	HasValue<String> getEditNumberOfPhases();
	HasValue<String> getEditUserName();
	void onPaintSequence(SequenceDetails seqeunceDetails);
	void setDropDownMachine();
	void setDropDownPhase();
	HasValue<String> getCommitCount();
	HasValue<String> getBatchSize();
	Boolean getDailySplit();
	
}
