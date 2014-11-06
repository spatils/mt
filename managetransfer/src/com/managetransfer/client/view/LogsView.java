package com.managetransfer.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.LogsDetails;

public interface LogsView<T> {

	public interface Presenter<T> {

		List<String> getObjectTypeList();

		List<String> getAttributesList();

		void onSearchButtonClicked(String objectType, String attribute,
				String value);

		void onObjectTypeComboBoxChange(String objectType);

	}

	void setPresenter(Presenter<T> presenter);

	Widget asWidget();

	void showLogsDetailsAsColumnChart(List<LogsDetails> logsDetails);

	void loadObjectTypeDropDownBox();

	void loadAttributesDropDownBox();

	void setRowData(List<LogsDetails> logsDetails,boolean isSearchGrid);

}
