package com.managetransfer.client.presenter;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.LogsDetails;
import com.managetransfer.client.ManageTransferServiceAsync;
import com.managetransfer.client.event.LoginEvent;
import com.managetransfer.client.view.HomePageViewImpl;
import com.managetransfer.client.view.LogsView;

public class LogsPresenter implements Presenter,
		LogsView.Presenter<LogsDetails> {

	private final ManageTransferServiceAsync rpcService;
	private final EventBus eventBus;
	private final LogsView<LogsDetails> display;

	private List<String> objectTypeList;
	private List<String> attributesList;

	public LogsPresenter(ManageTransferServiceAsync rpcService,
			EventBus eventBus, LogsView<LogsDetails> display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		display.setPresenter(this);

		loadObjectTypeList();
	}

	@Override
	public void go(HasWidgets container) {
		Iterator<Widget> iteratorOfWidgets = container.iterator();
		HomePageViewImpl hpvi = (HomePageViewImpl) iteratorOfWidgets.next();
		hpvi.addContentArea(display.asWidget());
		fetchLogsDetails();
	}

	private void fetchLogsDetails() {
		rpcService.getLogsDetails(new AsyncCallback<List<LogsDetails>>() {

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getMessage().startsWith("500 Internal Server Error")){
					eventBus.fireEvent( new LoginEvent());	
				}else{
					Window.alert("Error From Server"+caught.getMessage());
				}
			}

			@Override
			public void onSuccess(List<LogsDetails> result) {
				display.setRowData(result, false);
				display.showLogsDetailsAsColumnChart(result);
			}
		});
	}

	private void loadObjectTypeList() {
		rpcService.getObjectTypes(new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getMessage().startsWith("500 Internal Server Error")){
					eventBus.fireEvent( new LoginEvent());	
				}else{
					Window.alert("Error From Server"+caught.getMessage());
				}

			}

			@Override
			public void onSuccess(List<String> result) {
				objectTypeList = result;
				display.loadObjectTypeDropDownBox();
			}
		});
	}

	public List<String> getObjectTypeList() {
		return objectTypeList;
	}

	@Override
	public void onObjectTypeComboBoxChange(String objectTye) {
		rpcService.getAttributes(objectTye, new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getMessage().startsWith("500 Internal Server Error")){
					eventBus.fireEvent( new LoginEvent());	
				}else{
					Window.alert("Error From Server"+caught.getMessage());
				}

			}

			@Override
			public void onSuccess(List<String> result) {
				attributesList = result;
				display.loadAttributesDropDownBox();
			}
		});

	}

	@Override
	public List<String> getAttributesList() {
		return attributesList;
	}

	@Override
	public void onSearchButtonClicked(String objectType, String attribute,
			String value) {
		rpcService.getLogsDetails(objectType, attribute, value,
				new AsyncCallback<List<LogsDetails>>() {

					@Override
					public void onFailure(Throwable caught) {
						if(caught.getMessage().startsWith("500 Internal Server Error")){
							eventBus.fireEvent( new LoginEvent());	
						}else{
							Window.alert("Error From Server"+caught.getMessage());
						}

					}

					@Override
					public void onSuccess(List<LogsDetails> result) {
						display.setRowData(result, true);
					}
				});

	}

}
