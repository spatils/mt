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
 
import com.managetransfer.client.MappingDetails;
import com.managetransfer.client.ManageTransferServiceAsync;
import com.managetransfer.client.PhasesDetails;
 
import com.managetransfer.client.view.HomePageViewImpl;
import com.managetransfer.client.view.LoginView;
import com.managetransfer.client.view.MappingView;
import com.managetransfer.client.view.PhasesView;
 

public class MappingPresenter implements Presenter, MappingView.Presenter<MappingDetails> {

	private List<MappingDetails> connectionDetails;
	private List<MappingDetails> connectionDetailsAttribute;
	
	
	private final ManageTransferServiceAsync rpcService;
	private final EventBus eventBus;
	private final MappingView<MappingDetails> display;
	private final SelectionModel<MappingDetails> selectionModel;
	private ArrayList<String> connectionTypeList = new ArrayList<String>();
	private ArrayList<String> sourceObjectList = new ArrayList<String>();
	private ArrayList<String> targetObjectList = new ArrayList<String>();
	private ArrayList<String> sourceAttributeList = new ArrayList<String>();
	private ArrayList<String> targetAttributeList = new ArrayList<String>();
	private ArrayList<String> expressionList = new ArrayList<String>();
	 
	public MappingPresenter(ManageTransferServiceAsync rpcService, EventBus eventBus, MappingView<MappingDetails> display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		this.selectionModel = new SelectionModel<MappingDetails>();
		display.setPresenter(this);
		fetchDropDownList();
		 
	}
	private void fetchDropDownList() {
		fetchConnectionType();
		fetchSourceObject();
		fetchTargetObject();
		fetchExpressionList();
	}
	private void fetchExpressionList() {
		rpcService.getExpressionList( new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				setExpressionList(result);
				display.setExpressionList();
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
	public ArrayList<String> getExpressionList() {
		return expressionList;
	}
	public void setExpressionList(ArrayList<String> expressionList) {
		this.expressionList = expressionList;
	}
	public List<MappingDetails> getConnectionDetails() {
		return connectionDetails;
	}
	public void setConnectionDetails(List<MappingDetails> connectionDetails) {
		this.connectionDetails = connectionDetails;
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
	private void fetchSourceObject(){
		 
		rpcService.getSourceObjectList( new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				 
				setSourceObjectList(result);
				display.setSourceObjectlist();
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
	private void fetchTargetObject(){
		 
		rpcService.getTargetObjectList( new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				setTargetObjectList(result);
				display.setTargetObjectlist();
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
		 fetchMappingDetails();
	}
	@Override
	public void onSearchButtonClicked() {
		rpcService.searchMappingResult(display.getSearchString().getValue(),new AsyncCallback<ArrayList<MappingDetails>>() {
			@Override
			public void onSuccess(ArrayList<MappingDetails> result) {
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
	private void fetchMappingDetails() {
		rpcService.getMappingResultObject("dev",new AsyncCallback<ArrayList<MappingDetails>>() {
			@Override
			public void onSuccess(ArrayList<MappingDetails> result) {
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
	public void fetchMappingDetailsAttribute() {
		rpcService.getMappingResultObject("dev",new AsyncCallback<ArrayList<MappingDetails>>() {
			@Override
			public void onSuccess(ArrayList<MappingDetails> result) {
				connectionDetailsAttribute = result;
				display.setRowDataAttribute(result);
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
		MappingDetails newMappingDetail = new MappingDetails();
		boolean isNewMapping = true;
		for ( MappingDetails sr: connectionDetails){
			if( sr.getMappingName().equals(display.getNewMappingNameObject())){
				isNewMapping = false;
				break;
			}
		}
		newMappingDetail.setMappingName(display.getNewMappingNameObject());
		newMappingDetail.setMappingType("Object");
		newMappingDetail.setSourceObjectList(display.getSourceMappingOjbect());
		newMappingDetail.setTargetObject(display.getNewTargetMappingObject());
		 
		if(isNewMapping){
			rpcService.addMapping(newMappingDetail,new AsyncCallback<Boolean>() {
				@Override
				public void onSuccess( Boolean result) {
					fetchMappingDetails();
					Window.alert("Added New Mapping Attribute");
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
			
		}else{
		rpcService.editMapping(newMappingDetail,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchMappingDetails();
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
		
	}
	@Override
	public void onSaveButtonClickedAttribute() {
		MappingDetails newMappingDetail = new MappingDetails();
		boolean isNewMapping = true;
		for ( MappingDetails sr: connectionDetails){
			if( sr.getMappingName().equals(display.getSelectedAttributeTarget() ) && sr.getMappingType().equals(display.getSelectedMappingNameObject())){
				isNewMapping = false;
				break;
			}
		}
		newMappingDetail.setMappingName(display.getSelectedAttributeTarget());
		//Mapping type is same target object
		newMappingDetail.setMappingType(display.getSelectedMappingNameObject());
		newMappingDetail.setSourceObjectList(display.getAttributeSource() );
		newMappingDetail.setTargetObject(display.getSelectedAttributeTarget());
		newMappingDetail.setNameOfExpression(display.getAttributeExpressionName());
		Window.alert("Target Objet::"+newMappingDetail.getTargetObject()+"::"+newMappingDetail.getSourceObjectList().size());
		if(isNewMapping){
			Window.alert(newMappingDetail.getMappingName()+"::"+newMappingDetail.getMappingType()+"::"+newMappingDetail.getTargetObject()+"::"+newMappingDetail.getNameOfExpression());
			rpcService.addMapping(newMappingDetail,new AsyncCallback<Boolean>() {
				@Override
				public void onSuccess( Boolean result) {
					fetchMappingDetailsAttribute();
					Window.alert("Added Mapping attr");
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
			
		}else{
		rpcService.editMapping(newMappingDetail,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchMappingDetailsAttribute();
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
		
	}
	@Override
	public void onDeleteButtonClicked() {
		 rpcService.deleteMapping(display.getSelectedMappingNameObject() ,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchMappingDetails();
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
	public void onDeleteAttributeButtonClicked() {
		 rpcService.deleteMapping(display.getSelectedMappingNameAttribute() ,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchMappingDetailsAttribute();
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
		MappingDetails newconnectionDetail = new MappingDetails();
		rpcService.addMapping(newconnectionDetail,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchMappingDetails();
				Window.alert("Added New Mapping");
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
	public void onItemSelected(MappingDetails selectedItem) {
		// TODO Auto-generated method stub
		
		
	}
	
	public ArrayList<String> getConnectionTypeList() {
		return this.connectionTypeList;
	}
	public void setConnectionTypeList(ArrayList<String> connectionType) {
		this.connectionTypeList = connectionType;
	}
	@Override
	public void onItemClicked(MappingDetails connectionDetails) {
		// TODO Auto-generated method stub
		
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
	 
	public void fetchSourceAttributeList(String objectName[]){
		rpcService.getSourceAttributeList(objectName,new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				 setSourceAttributeList(result);
				 display.setSourceAttributelist();
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
	public void fetchTargetAttributeList(String objectName){
		rpcService.getSourceAttributeList(objectName.split(","),new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				 setTargetAttributeList(result);
				 display.setTargetAttributelist();
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
	public ArrayList<String> getSourceAttributeList() {
		return sourceAttributeList;
	}
	public void setSourceAttributeList(ArrayList<String> sourceAttributeList) {
		this.sourceAttributeList = sourceAttributeList;
	}
	public ArrayList<String> getTargetAttributeList() {
		return targetAttributeList;
	}
	public void setTargetAttributeList(ArrayList<String> targetAttributeList) {
		this.targetAttributeList = targetAttributeList;
	}
	 
	 
}
