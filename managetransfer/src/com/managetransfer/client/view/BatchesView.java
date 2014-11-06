package com.managetransfer.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.BatchDetails;
import com.managetransfer.client.SequenceDetailsMap;
 
 

public interface BatchesView<T> {
	public interface Presenter<T> {
		void displayBatchDetails(String sequenceName , int seqNumber);
	}

	void setPresenter(Presenter<T> presenter);

	Widget asWidget();
	void displayBatchDetails(SequenceDetailsMap seqDetailsMap);
	void setRowData(List<BatchDetails> batchDetails);
}
