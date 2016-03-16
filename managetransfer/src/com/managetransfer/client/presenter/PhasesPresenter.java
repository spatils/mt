package com.managetransfer.client.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.common.SelectionModel;
import com.managetransfer.client.event.ExpressionsEvent;
import com.managetransfer.client.event.LoginEvent;
import com.managetransfer.client.PhasesDetails;
import com.managetransfer.client.ManageTransferServiceAsync;
import com.managetransfer.client.view.PhasesView;
import com.managetransfer.client.view.HomePageViewImpl;
 

public class PhasesPresenter implements Presenter, PhasesView.Presenter<PhasesDetails> {

	private List<PhasesDetails> phaseDetails;
	private final ManageTransferServiceAsync rpcService;
	private final EventBus eventBus;
	private final PhasesView<PhasesDetails> display;
	private final SelectionModel<PhasesDetails> selectionModel;
	private ArrayList<String> phaseTypeList = new ArrayList<String>();
	private ArrayList<String> connectionList = new ArrayList<String>();
	private ArrayList<String> sourceObjectList = new ArrayList<String>();
	private ArrayList<String> targetObjectList = new ArrayList<String>();
	private ArrayList<String> transformationList = new ArrayList<String>();
	
	public PhasesPresenter(ManageTransferServiceAsync rpcService, EventBus eventBus, PhasesView<PhasesDetails> display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		this.selectionModel = new SelectionModel<PhasesDetails>();
		display.setPresenter(this);
		fetchDropDownList();
		 
	}
	private void fetchDropDownList() {
		fetchPhasesType();
	}
	private void fetchConnectionList() {
		rpcService.getConnectionList( new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				setConnectionList(result);
				display.setDropDownConnection();
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
		private void fetchPhasesType(){
		 
		rpcService.getPhaseTypeList( new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				setPhaseTypeList(result);
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
	     fetchPhaseDetails();
		 
	}
	@Override
	public void onSearchButtonClicked() {
		// TODO Auto-generated method stub
		rpcService.searchPhase(display.getSearchString().getValue(),new AsyncCallback<ArrayList<PhasesDetails>>() {
			@Override
			public void onSuccess(ArrayList<PhasesDetails> result) {
				phaseDetails = result;
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
	private void fetchPhaseDetails() {
		rpcService.getPhaseResult("dev",new AsyncCallback<ArrayList<PhasesDetails>>() {
			@Override
			public void onSuccess(ArrayList<PhasesDetails> result) {
				phaseDetails = result;
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
		PhasesDetails phaseDetails = new PhasesDetails();
		phaseDetails.setPhaseName(display.getSelectedPhase());
		phaseDetails.setPhaseType(display.getSelectedPhaseType());
		phaseDetails.setPhaseParametersString(display.getPhaseParametersString());
		phaseDetails.setPhaseParametersInteger(display.getPhaseParametersInteger());
		phaseDetails.setPhaseParametersDate(display.getPhaseParametersDate());
		rpcService.editPhase(phaseDetails,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchPhaseDetails();
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
		 rpcService.deletePhase(display.getDeletePhaseString() ,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchPhaseDetails();
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
	public void onTestButtonClicked() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemSelected(PhasesDetails selectedItem) {
		// TODO Auto-generated method stub
		
		
	}
	@Override
	public void onItemClicked(PhasesDetails jobDetails) {
		// TODO Auto-generated method stub
		
	}
	public ArrayList<String> getPhaseTypeList() {
		return this.phaseTypeList;
	}
	public void setPhaseTypeList(ArrayList<String> phaseType) {
		this.phaseTypeList = phaseType;
	}
	@Override
	public void onSearchEditButtonClicked() {
		rpcService.searchPhase(display.getSelectedPhase(),new AsyncCallback<ArrayList<PhasesDetails>>() {
			@Override
			public void onSuccess(ArrayList<PhasesDetails> result) {
				display.displayPhaseInformation(false, result.get(0));
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
		fetchConnectionList();
		fetchSourceObjectList();
		fetchTargetObjectList();
		fetchTransformationList();
	}
	@Override
	public void onSearchDetailsButtonClicked() {
		rpcService.searchPhase(display.getSelectedPhase(),new AsyncCallback<ArrayList<PhasesDetails>>() {
			@Override
			public void onSuccess(ArrayList<PhasesDetails> result) {
				display.displayPhaseInformation(true, result.get(0));
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
		fetchConnectionList();
		fetchSourceObjectList();
		fetchTargetObjectList();
		fetchTransformationList();
	}
	@Override
	public void onPhaseTypeSelected() {
		
		
	}
	@Override
	public void onAddButtonClicked() {
		final PhasesDetails newPhaseDetails = new PhasesDetails();
		newPhaseDetails.setPhaseName(display.getNewPhaseName());
		newPhaseDetails.setPhaseType(display.getNewPhaseType());
		Window.alert("Adding new Phase");
		rpcService.addPhase(newPhaseDetails,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				Window.alert("New Phase Added");
				display.displayPhaseInformation(false, newPhaseDetails );
				fetchConnectionList();
				fetchSourceObjectList();
				fetchTargetObjectList();
				fetchTransformationList();
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
	private void fetchSourceObjectList() {
		rpcService.getSourceObjectList( new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				setSourceObjectList(result);
				display.setDropDownSourceObject();
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
	private void fetchTargetObjectList() {
		rpcService.getTargetObjectList( new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				setTargetObjectList(result);
				display.setDropDownTargetObject();
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
	private void fetchTransformationList() {
		rpcService.getObjectLevelMappingNames( new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				setTransformationList(result);
				display.setDropDownTransformation();
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
	public ArrayList<String> getConnectionList() {
		return connectionList;
	}
	public void setConnectionList(ArrayList<String> connectionList) {
		this.connectionList = connectionList;
	}
	public ArrayList<String> getSourceObjectList() {
		return sourceObjectList;
	}
	public void setSourceObjectList(ArrayList<String> sourceObjectList) {
		this.sourceObjectList = sourceObjectList;
	}
	public ArrayList<String> getTargetObjectList() {
		return targetObjectList;
	}
	public void setTargetObjectList(ArrayList<String> targetObjectList) {
		this.targetObjectList = targetObjectList;
	}
	public ArrayList<String> getTransformationList() {
		return transformationList;
	}
	public void setTransformationList(ArrayList<String> transformationList) {
		this.transformationList = transformationList;
	}
	
	
	 
		 
}
