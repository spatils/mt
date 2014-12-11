package com.managetransfer.client.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.common.SelectionModel;
import com.managetransfer.client.event.LoginEvent;
import com.managetransfer.client.event.LoginSuccessEvent;
import com.managetransfer.client.ConnectionDetails;
import com.managetransfer.client.ManageTransferServiceAsync;
import com.managetransfer.client.UserDetails;
import com.managetransfer.client.view.ConnectionsView;
import com.managetransfer.client.view.HomePageViewImpl;
import com.managetransfer.client.view.LoginView;
 

public class ConnectionsPresenter implements Presenter, ConnectionsView.Presenter<ConnectionDetails> {

	private List<ConnectionDetails> connectionDetails;
	private final ManageTransferServiceAsync rpcService;
	private final EventBus eventBus;
	private final ConnectionsView<ConnectionDetails> display;
	private final SelectionModel<ConnectionDetails> selectionModel;
	private ArrayList<String> connectionTypeList = new ArrayList<String>();
	public ConnectionsPresenter(ManageTransferServiceAsync rpcService, EventBus eventBus, ConnectionsView<ConnectionDetails> display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		this.selectionModel = new SelectionModel<ConnectionDetails>();
		display.setPresenter(this);
		fetchDropDownList();
		 
	}
	private void fetchDropDownList() {
		 
		fetchConnectionType();
	}
	private void fetchConnectionType(){
		 
		rpcService.getConnectionTypeList( new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				setConnectionTypeList(result);
				display.setDropDown();
			}

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getMessage().startsWith("500 Internal Server Error")){
					eventBus.fireEvent( new LoginEvent());	
				}else{
					Window.alert("Error From Server"+caught.getMessage());
				}
				
			}
		});
	}
	@Override
	public void go(final HasWidgets container) {
	     Iterator<Widget> iteratorOfWidgets =container.iterator();
		 HomePageViewImpl hpvi = (HomePageViewImpl) iteratorOfWidgets.next();
		 hpvi.addContentArea(display.asWidget());
		 fetchConnectionDetails();
	}
	@Override
	public void onSearchButtonClicked() {
		// TODO Auto-generated method stub
		rpcService.searchConnectionResult(display.getSearchString().getValue(),new AsyncCallback<ArrayList<ConnectionDetails>>() {
			@Override
			public void onSuccess(ArrayList<ConnectionDetails> result) {
				connectionDetails = result;
				display.setRowData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getMessage().startsWith("500 Internal Server Error")){
					eventBus.fireEvent( new LoginEvent());	
				}else{
					Window.alert("Error From Server"+caught.getMessage());
				}
			}
		});
		
	}
	private void fetchConnectionDetails() {
		rpcService.getConnectionResult("dev",new AsyncCallback<ArrayList<ConnectionDetails>>() {
			@Override
			public void onSuccess(ArrayList<ConnectionDetails> result) {
				connectionDetails = result;
				display.setRowData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getMessage().startsWith("500 Internal Server Error")){
					eventBus.fireEvent( new LoginEvent());	
				}else{
					Window.alert("Error From Server"+caught.getMessage());
				}
			}
		});
	}
	
	@Override
	public void onSaveButtonClicked() {
		// TODO Auto-generated method stub
		ConnectionDetails newconnectionDetail = new ConnectionDetails();
		newconnectionDetail.setConnectionName(display.getEditConnectionName().getValue());
		newconnectionDetail.setConnectionType(display.getEditConnectionType());
		newconnectionDetail.setDataSource(display.getEditDataSource().getValue());
		newconnectionDetail.setUserName(display.getEditUserName().getValue());
		newconnectionDetail.setDomain(display.getEditDomain().getValue());
		newconnectionDetail.setPort(display.getEditPort().getValue());
		newconnectionDetail.setUrl(display.getEditURL().getValue());
		newconnectionDetail.setUserPsw(display.getEditPsw().getValue());
		 
		rpcService.editConnection(newconnectionDetail,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchConnectionDetails();
				Window.alert("updated");
			}

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getMessage().startsWith("500 Internal Server Error")){
					eventBus.fireEvent( new LoginEvent());	
				}else{
					Window.alert("Error From Server"+caught.getMessage());
				}
			}
		});
		
	}
	@Override
	public void onDeleteButtonClicked() {
		 rpcService.deleteConnection(display.getDeleteConnectionString().getValue() ,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchConnectionDetails();
				Window.alert("Deleted");
			}

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getMessage().startsWith("500 Internal Server Error")){
					eventBus.fireEvent( new LoginEvent());	
				}else{
					Window.alert("Error From Server"+caught.getMessage());
				}
			}
		});
		
	}
	@Override
	public void onAddButtonClicked() {
		ConnectionDetails newconnectionDetail = new ConnectionDetails();
		newconnectionDetail.setConnectionName(display.getConnectionName().getValue());
		newconnectionDetail.setConnectionType(display.getConnectionType() );
		newconnectionDetail.setDataSource(display.getDataSource().getValue());
		newconnectionDetail.setUserName(display.getUserName().getValue());
		newconnectionDetail.setDomain(display.getNewDomain().getValue());
		newconnectionDetail.setPort(display.getNewPort().getValue());
		newconnectionDetail.setUrl(display.getNewURL().getValue());
		newconnectionDetail.setUserPsw(display.getNewPsw().getValue());
		rpcService.addConnection(newconnectionDetail,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchConnectionDetails();
				Window.alert("added");
			}

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getMessage().startsWith("500 Internal Server Error")){
					eventBus.fireEvent( new LoginEvent());	
				}else{
					Window.alert("Error From Server"+caught.getMessage());
				}
			}
		});
		
	}
	@Override
	public void onTestButtonClicked() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemSelected(ConnectionDetails selectedItem) {
		// TODO Auto-generated method stub
		
		
	}
	@Override
	public void onItemClicked(ConnectionDetails connectionDetails) {
		// TODO Auto-generated method stub
		
	}
	public ArrayList<String> getConnectionTypeList() {
		return this.connectionTypeList;
	}
	public void setConnectionTypeList(ArrayList<String> connectionType) {
		this.connectionTypeList = connectionType;
	}
	 
		 
}
