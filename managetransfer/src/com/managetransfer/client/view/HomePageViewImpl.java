package com.managetransfer.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
 
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
 
 
 
import com.managetransfer.client.UserDetails;

public class HomePageViewImpl extends Composite implements HomePageView<UserDetails>{

	@UiTemplate("HomePageView.ui.xml")
	interface HomePageViewUiBinder extends UiBinder<Widget, HomePageViewImpl> {
	} 
	
	private static HomePageViewUiBinder uiBinder =	GWT.create(HomePageViewUiBinder.class);
	@UiField Label  loginMessage = new Label();
	public HomePageViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		 
	}
	private Presenter<UserDetails> presenter;
	
	 
	@Override
	public void setPresenter(Presenter<UserDetails> presenter) {
		// TODO Auto-generated method stub
		setWelcomeMessage(presenter.getUserDetails().getUserId());
		this.presenter = presenter;
	
		 
	}
	 
	@UiHandler("connections")
	void onConnectionsClicked(@SuppressWarnings("unused") ClickEvent event) {
		if (presenter != null) {
			presenter.onConnectionsButtonClicked();
		}
	}
	@UiHandler("expressions")
	void onExpressionsButtonClicked(@SuppressWarnings("unused") ClickEvent event) {
		if (presenter != null) {
			presenter.onExpressionsButtonClicked();
		}
	}
	@UiHandler("sequences")
	void onSequencesButtonClicked(@SuppressWarnings("unused") ClickEvent event) {
		if (presenter != null) {
			presenter.onSequencesButtonClicked();
		}
	}
	@UiHandler("schedular")
	void onSchedularButtonClicked(@SuppressWarnings("unused") ClickEvent event) {
		if (presenter != null) {
			presenter.onSchedularButtonClicked();
		}
	}
	@UiHandler("phases")
	void onPhasesButtonClicked(@SuppressWarnings("unused") ClickEvent event) {
		if (presenter != null) {
			presenter.onPhasesButtonClicked();
		}
	}
	@UiHandler("mapdata")
	void onMapDataButtonClicked(@SuppressWarnings("unused") ClickEvent event) {
		if (presenter != null) {
			presenter.onMapDataClicked();
		}
	}
	
	@UiHandler("batchreport")
	void onBatchReportButtonClicked(@SuppressWarnings("unused") ClickEvent event) {
		if (presenter != null) {
			presenter.onBatchReportClicked();
		}
	}
	@UiHandler("logreport")
	void onLogReportButtonClicked(@SuppressWarnings("unused") ClickEvent event) {
		if (presenter != null) {
			presenter.onLogReportClicked();
		}
	}
	@UiHandler("throughputreport")
	void onThroughputReportButtonClicked(@SuppressWarnings("unused") ClickEvent event) {
		if (presenter != null) {
			presenter.onThroughputReportClicked();
		}
	}
	@UiField VerticalPanel homeVerticalPaneCenter;
	public void  addContentArea(Widget childWidget){
		homeVerticalPaneCenter.clear();
		homeVerticalPaneCenter.add(childWidget);
		//homeDockLayoutPanel.add(childWidget);
	}

	@Override
	public void setWelcomeMessage(String userName) {
		// TODO Auto-generated method stub
		loginMessage.setText("Welcome "+userName+" !!!");
	}
	 
}
