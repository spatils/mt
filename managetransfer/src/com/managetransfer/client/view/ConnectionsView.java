package com.managetransfer.client.view;

import java.util.ArrayList;
import java.util.List;

 
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
 
import com.managetransfer.client.ConnectionDetails;

public interface ConnectionsView<T>   {

	public interface Presenter<T> {
		 void onSearchButtonClicked();
		 void onSaveButtonClicked();
		 void onDeleteButtonClicked();
		 void onAddButtonClicked();
		 void onTestButtonClicked();
		 void onItemSelected(T selectedItem);
		 void onItemClicked(ConnectionDetails connectionDetails);
		 ArrayList<String> getConnectionTypeList();
	 }

	void setPresenter(Presenter<T> presenter);
	void setDropDown();
	HasValue<String> getConnections();
	HasValue<String> getConnectionName();
	String getConnectionType();
	HasValue<String> getUserName();
	HasValue<String> getDataSource();
	HasValue<String> getSearchString();
	HasValue<String> getDeleteConnectionString();
	void setRowData(List<T> rowData);
	HasValue<String> getEditDataSource();
	HasValue<String> getEditConnectionName();
	String getEditConnectionType();
	HasValue<String> getEditUserName();
	HasValue<String> getEditDomain();
	HasValue<String> getEditPort();
	HasValue<String> getEditURL();
	HasValue<String> getEditPsw();
	
	HasValue<String> getNewDomain();
	HasValue<String> getNewPort();
	HasValue<String> getNewURL();
	HasValue<String> getNewPsw();
	
	
	Widget asWidget();

}
