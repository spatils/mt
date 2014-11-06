package com.managetransfer.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.ExpressionsDetails;
import com.managetransfer.client.view.ExpressionsView.Presenter;

public interface ExpressionsView<T>   {

	public interface Presenter<T> {
		 void onSearchButtonClicked();
		 void onSaveButtonClicked();
		 void onDeleteButtonClicked();
		 void onAddButtonClicked();
		 void onTestButtonClicked();
		 void onItemSelected(T selectedItem);
		 void onItemClicked(ExpressionsDetails expressionDetails);
		 ArrayList<String> getExpressionTypeList();
	 }

	void setPresenter(Presenter<T> presenter);
	void setDropDown();
	HasValue<String> getEditExpression();
	HasValue<String> getNewExpression();
	HasValue<String> getExpressions();
	HasValue<String> getExpressionName();
	HasValue<String> getSearchString();
	HasValue<String> getDeleteExpressionString();
	void setRowData(List<T> rowData);
	HasValue<String> getEditExpressionName();
	String getExpressionType();
	String getEditExpressionType();
	
	String getNewExpressionType();
	HasValue<String> getNewExpressionName();
	 
 	Widget asWidget();
	 
	 

}
