package com.managetransfer.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.managetransfer.client.common.SelectionModel;
import com.managetransfer.client.event.BatchReportEvent;
import com.managetransfer.client.event.ConnectionsEvent;
import com.managetransfer.client.event.ExpressionsEvent;
import com.managetransfer.client.event.ImportDefinitionEvent;
import com.managetransfer.client.event.LogReportEvent;
import com.managetransfer.client.event.LoginEvent;
import com.managetransfer.client.event.LoginSuccessEvent;
import com.managetransfer.client.event.MapDataEvent;
import com.managetransfer.client.event.PhasesEvent;
import com.managetransfer.client.event.SchedularEvent;
import com.managetransfer.client.event.SequenceEvent;
import com.managetransfer.client.event.ThroughputReportEvent;
import com.managetransfer.client.ExpressionsDetails;
import com.managetransfer.client.ManageTransferServiceAsync;
import com.managetransfer.client.UserDetails;
import com.managetransfer.client.view.ExpressionsView;
import com.managetransfer.client.view.HomePageView;
import com.managetransfer.client.view.LoginView;
 

public class HomePagePresenter implements Presenter, HomePageView.Presenter<UserDetails> {
	private UserDetails userDetails;
	private final ManageTransferServiceAsync rpcService;
	private final EventBus eventBus;
	private final HomePageView<UserDetails> display;
	private final SelectionModel<UserDetails> selectionModel;

	public HomePagePresenter(ManageTransferServiceAsync rpcService, EventBus eventBus, HomePageView<UserDetails> display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.setUserDetails(new UserDetails());
		this.display = display;
		this.selectionModel = new SelectionModel<UserDetails>();
		display.setPresenter(this);
		 
	}
	@Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}
	@Override
	public void onConnectionsButtonClicked() {
		// TODO Auto-generated method stub
		eventBus.fireEvent(new ConnectionsEvent());
	}
	@Override
	public void onSequencesButtonClicked() {
		// TODO Auto-generated method stub
		eventBus.fireEvent(new SequenceEvent());
		
	}
	@Override
	public void onSchedularButtonClicked() {
		// TODO Auto-generated method stub
		eventBus.fireEvent(new SchedularEvent());
		
	}
	@Override
	public void onExpressionsButtonClicked() {
		// TODO Auto-generated method stub
		eventBus.fireEvent(new ExpressionsEvent());
		
	}
	public SelectionModel<UserDetails> getSelectionModel() {
		return selectionModel;
	}
	public UserDetails getUserDetails() {
		return this.userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	@Override
	public void onPhasesButtonClicked() {
		eventBus.fireEvent(new PhasesEvent());
	}
	@Override
	public void onImportDefinitionClicked() {
		// TODO Auto-generated method stub
		eventBus.fireEvent(new ImportDefinitionEvent());
	}
	@Override
	public void onMapDataClicked() {
		eventBus.fireEvent(new MapDataEvent());
		
	}
	@Override
	public void onLogReportClicked() {
		eventBus.fireEvent(new LogReportEvent());
		
	}
	@Override
	public void onBatchReportClicked() {
		eventBus.fireEvent(new BatchReportEvent());
		
	}
	@Override
	public void onThroughputReportClicked() {
		eventBus.fireEvent(new ThroughputReportEvent());
		
	}
	@Override
	public void onLogOutClicked() {
		
		Window.alert("Logging Out");
		rpcService.logOut( new AsyncCallback <String>() {
			@Override
			public void onSuccess( String result) {
				eventBus.fireEvent(new LoginEvent());
			}

			@Override
			public void onFailure(Throwable caught) {
				eventBus.fireEvent( new LoginEvent());
			}
		});
		
	}
	 
	 
	 
}
