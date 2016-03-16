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
import com.managetransfer.client.event.LoginEvent;
import com.managetransfer.client.ExpressionsDetails;
import com.managetransfer.client.ManageTransferServiceAsync;
import com.managetransfer.client.view.ExpressionsView;
import com.managetransfer.client.view.HomePageViewImpl;
 
 

public class ExpressionsPresenter implements Presenter, ExpressionsView.Presenter<ExpressionsDetails> {

	private List<ExpressionsDetails> expressionDetails;
	private final ManageTransferServiceAsync rpcService;
	private final EventBus eventBus;
	private final ExpressionsView<ExpressionsDetails> display;
	private final SelectionModel<ExpressionsDetails> selectionModel;
	private ArrayList<String> expressionTypeList = new ArrayList<String>();
	public ExpressionsPresenter(ManageTransferServiceAsync rpcService, EventBus eventBus, ExpressionsView<ExpressionsDetails> display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		this.selectionModel = new SelectionModel<ExpressionsDetails>();
		display.setPresenter(this);
		fetchDropDownList();
		 
	}
	private void fetchDropDownList() {
		 
		fetchExpressionType();
	}
	private void fetchExpressionType(){
		 
		rpcService.getExpressionTypeList( new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				setExpressionTypeList(result);
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
		rpcService.searchExpressions(display.getSearchString().getValue(),new AsyncCallback<ArrayList<ExpressionsDetails>>() {
			@Override
			public void onSuccess(ArrayList<ExpressionsDetails> result) {
				expressionDetails = result;
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
		rpcService.getExpressionsResult("dev",new AsyncCallback<ArrayList<ExpressionsDetails>>() {
			@Override
			public void onSuccess(ArrayList<ExpressionsDetails> result) {
				expressionDetails = result;
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
		ExpressionsDetails newconnectionDetail = new ExpressionsDetails();
		newconnectionDetail.setExpressionsName(display.getEditExpressionName().getValue());
		newconnectionDetail.setExpressionsType(display.getEditExpressionType());
		newconnectionDetail.setExpression(display.getEditExpression().getValue());
		rpcService.editExpression(newconnectionDetail,new AsyncCallback<Boolean>() {
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
		 rpcService.deleteExpression(display.getDeleteExpressionString().getValue() ,new AsyncCallback<Boolean>() {
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
		ExpressionsDetails newconnectionDetail = new ExpressionsDetails();
		newconnectionDetail.setExpressionsName(display.getNewExpressionName().getValue());
		newconnectionDetail.setExpressionsType(display.getNewExpressionType() );
		newconnectionDetail.setExpression(display.getNewExpression().getValue() );
		rpcService.addExpression(newconnectionDetail,new AsyncCallback<Boolean>() {
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
	public void onItemSelected(ExpressionsDetails selectedItem) {
		// TODO Auto-generated method stub
		
		
	}
	@Override
	public void onItemClicked(ExpressionsDetails connectionDetails) {
		// TODO Auto-generated method stub
		
	}
	public ArrayList<String> getExpressionTypeList() {
		return expressionTypeList;
	}
	public void setExpressionTypeList(ArrayList<String> expressionTypeList) {
		this.expressionTypeList = expressionTypeList;
	}
	 
	 
		 
}
