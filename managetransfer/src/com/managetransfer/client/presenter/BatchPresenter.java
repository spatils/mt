package com.managetransfer.client.presenter;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
 
import com.managetransfer.client.BatchDetails;
import com.managetransfer.client.ManageTransferServiceAsync;
import com.managetransfer.client.SequenceDetailsMap;
import com.managetransfer.client.event.LoginEvent;
import com.managetransfer.client.view.BatchesView;
import com.managetransfer.client.view.HomePageViewImpl;
 

public class BatchPresenter implements Presenter,
		BatchesView.Presenter<BatchDetails> {

	private final ManageTransferServiceAsync rpcService;
	private final EventBus eventBus;
	private final BatchesView<BatchDetails> display;

	public BatchPresenter(ManageTransferServiceAsync rpcService,
			EventBus eventBus, BatchesView<BatchDetails> display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		display.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		Iterator<Widget> iteratorOfWidgets = container.iterator();
		HomePageViewImpl hpvi = (HomePageViewImpl) iteratorOfWidgets.next();
		hpvi.addContentArea(display.asWidget());
		fetchBatchDetails();
	}

	private void fetchBatchDetails() {
		rpcService.getBatchDetails(new AsyncCallback<List<BatchDetails>>() {

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getMessage().startsWith("500 Internal Server Error")){
					eventBus.fireEvent( new LoginEvent());	
				}else{
					Window.alert("Error From Server"+caught.getMessage());
				}
			}

			@Override
			public void onSuccess(List<BatchDetails> result) {
				display.setRowData(result);
			}
		});
	}

	@Override
	public void displayBatchDetails(String sequenceName, int seqNumber) {
		// TODO Auto-generated method stub
		rpcService.getSequenceResultMap(sequenceName, seqNumber, new AsyncCallback<SequenceDetailsMap>() {

			@Override
			public void onFailure(Throwable caught) {
				if(caught.getMessage().startsWith("500 Internal Server Error")){
					eventBus.fireEvent( new LoginEvent());	
				}else{
					Window.alert("Error From Server"+caught.getMessage());
				}
			}

			@Override
			public void onSuccess(SequenceDetailsMap result) {
				 display.displayBatchDetails(result);
			}
		});
	}

}
