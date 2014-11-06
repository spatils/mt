package com.managetransfer.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.BatchDetails;
import com.managetransfer.client.SequenceDetails;
import com.managetransfer.client.SequenceDetailsMap;

public interface ThroughputView<T> {

	public interface Presenter<T> {
		void getSequenceDetailsMapList(String seqName);
		void getBatchDetails(String sequenceName , int sequenceNumber);
	}

	void setPresenter(Presenter<T> presenter);

	Widget asWidget();

	void showSequenceDetailsAsBarChart(List<BatchDetails> result);

	void setRowData(List<SequenceDetails> list);

	void setRowDataWithSequenceDetails(List<SequenceDetailsMap> list);
	
	
}
