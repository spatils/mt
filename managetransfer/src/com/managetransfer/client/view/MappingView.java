package com.managetransfer.client.view;

import java.util.ArrayList;
import java.util.List;

 
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
 
import com.managetransfer.client.MappingDetails;

public interface MappingView<T> {

	public interface Presenter<T> {
		 void onSearchButtonClicked();
		 void onSaveButtonClicked();
		 void onSaveButtonClickedAttribute();
		 void onDeleteButtonClicked();
		 void onDeleteAttributeButtonClicked();
		 void onAddButtonClicked();
		 void onTestButtonClicked();
		 void onItemSelected(T selectedItem);
		 void onItemClicked(MappingDetails connectionDetails);
		 ArrayList<String> getConnectionTypeList();
		 ArrayList<String> getTargetObjectList();
		 ArrayList<String> getSourceObjectList();
		 ArrayList<String> getTargetAttributeList();
		 ArrayList<String> getSourceAttributeList();
		 ArrayList<String> getExpressionList();
		 void  fetchSourceAttributeList(String[] src);
		 void  fetchTargetAttributeList(String src);
		 List<MappingDetails> getConnectionDetails();
		void fetchMappingDetailsAttribute();
		 
		 
	 }

	void setPresenter(Presenter<T> presenter);
	void setDropDown();
	void setSourceObjectlist();
	void setTargetObjectlist();
	void setSourceAttributelist();
	void setTargetAttributelist();
	void  setExpressionList();
	HasValue<String> getMapping();
	HasValue<String> getMappingName();
	String getMappingType();
	HasValue<String> getUserName();
	HasValue<String> getDataSource();
	HasValue<String> getSearchString();
	HasValue<String> getDeleteConnectionString();
	void setRowData(List<T> rowData);
	void setRowDataAttribute(List<T> rowData);
	HasValue<String> getEditDataSource();
	HasValue<String> getEditMappingName();
	String getEditMappingType();
	HasValue<String> getEditUserName();
	String getNewMappingNameObject();
	ArrayList<String> getSourceMappingOjbect();
	String getNewTargetMappingObject();
	String getSelectedMappingNameObject();
	String getSelectedMappingNameAttribute();
	String getAttributeExpressionName();
	ArrayList<String> getAttributeSource();
	String getSelectedAttributeTarget();
	 
	
	Widget asWidget();
}
