package com.managetransfer.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.view.LoginView.Presenter;
import com.managetransfer.client.UserDetails;

public interface     HomePageView<T>   {

	public interface Presenter<T> {

		void onConnectionsButtonClicked();

		void onSequencesButtonClicked();

		void onSchedularButtonClicked();

		void onExpressionsButtonClicked();
		
		void onPhasesButtonClicked();
		
		void onImportDefinitionClicked();
		
		void onMapDataClicked();
		
		void onLogReportClicked();
		
		void onBatchReportClicked();
		
		void onThroughputReportClicked();
		
		UserDetails getUserDetails();
		  
	 }
	void setPresenter(Presenter<T> presenter);
	void setWelcomeMessage(String userName);
	
	Widget asWidget();
}
