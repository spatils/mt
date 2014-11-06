package com.managetransfer.client.presenter;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.ManageTransferServiceAsync;
import com.managetransfer.client.SequenceDetails;
import com.managetransfer.client.SequenceDetailsMap;
import com.managetransfer.client.view.HomePageViewImpl;
import com.managetransfer.client.view.ThroughputView;
import com.managetransfer.client.BatchDetails;

public class ThroughputPresenter implements Presenter,
		ThroughputView.Presenter<SequenceDetails> {

	private final ManageTransferServiceAsync rpcService;
	private final EventBus eventBus;
	private final ThroughputView<SequenceDetails> display;

	public ThroughputPresenter(ManageTransferServiceAsync rpcService,
			EventBus eventBus, ThroughputView<SequenceDetails> display) {
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
		fetchSequencesList();
	}

	private void fetchSequencesList() {
		rpcService.getSequencesList(new AsyncCallback<List<SequenceDetails>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching batch details");
			}

			@Override
			public void onSuccess(List<SequenceDetails> result) {
				display.setRowData(result);
			}
		});
	}

	@Override
	public void getSequenceDetailsMapList(String seqName) {
		rpcService.getSequenceDetailsMapList(seqName,
				new AsyncCallback<List<SequenceDetailsMap>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error fetching batch details");
					}
					@Override
					public void onSuccess(List<SequenceDetailsMap> result) {
						display.setRowDataWithSequenceDetails(result);
					}
				});
	}

	@Override
	public void getBatchDetails(String sequenceName, int sequenceNumber) {
		rpcService.getBatchDetailsBySequence(sequenceName,sequenceNumber,
				new AsyncCallback<List<BatchDetails>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error fetching batch details");
					}
					@Override
					public void onSuccess(List<BatchDetails> result) {
						display.showSequenceDetailsAsBarChart(result);
					}
				});
	}

}
