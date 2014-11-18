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
import com.managetransfer.client.JobDetails;
import com.managetransfer.client.ManageTransferServiceAsync;
import com.managetransfer.client.view.SchedularView;
import com.managetransfer.client.view.HomePageViewImpl;
 

public class SchedularPresenter implements Presenter, SchedularView.Presenter<JobDetails> {

	private List<JobDetails> jobDetails;
	private final ManageTransferServiceAsync rpcService;
	private final EventBus eventBus;
	private final SchedularView<JobDetails> display;
	private final SelectionModel<JobDetails> selectionModel;
	private ArrayList<String> jobTypeList = new ArrayList<String>();
	public SchedularPresenter(ManageTransferServiceAsync rpcService, EventBus eventBus, SchedularView<JobDetails> display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		this.selectionModel = new SelectionModel<JobDetails>();
		display.setPresenter(this);
		fetchDropDownList();
		 
	}
	private void fetchDropDownList() {
		 
		fetchJobType();
	}
	private void fetchJobType(){
		 
		rpcService.getJobTypeList( new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				setJobTypeList(result);
				display.setDropDown();
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error search job details");
			}
		});
	}
	@Override
	public void go(final HasWidgets container) {
	     Iterator<Widget> iteratorOfWidgets =container.iterator();
		 HomePageViewImpl hpvi = (HomePageViewImpl) iteratorOfWidgets.next();
		 hpvi.addContentArea(display.asWidget());
		 fetchJobDetails();
	}
	@Override
	public void onSearchButtonClicked() {
		// TODO Auto-generated method stub
		rpcService.searchJob(display.getSearchString().getValue(),new AsyncCallback<ArrayList<JobDetails>>() {
			@Override
			public void onSuccess(ArrayList<JobDetails> result) {
				jobDetails = result;
				display.setRowData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error search job details");
			}
		});
		
	}
	private void fetchJobDetails() {
		rpcService.getJobResult("dev",new AsyncCallback<ArrayList<JobDetails>>() {
			@Override
			public void onSuccess(ArrayList<JobDetails> result) {
				jobDetails = result;
				display.setRowData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching job details");
			}
		});
	}
	
	@Override
	public void onSaveButtonClicked() {
		// TODO Auto-generated method stub
		JobDetails newjobDetail = new JobDetails();
		newjobDetail.setJobName(display.getEditJobName().getValue());
		newjobDetail.setCronSchedule(display.getEditCronSchedule().getValue());
		//newjobDetail.setParameters(display.getEditParameters().getValue());
		//newjobDetail.setLastExecutionDate(display.getEditLastExecutionDate().getValue());
		//newjobDetail.setNextExectuionDate(display.getEditNextExectuionDate().getValue());
		rpcService.editJob(newjobDetail,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchJobDetails();
				Window.alert("updated");
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error updating job details");
			}
		});
		
	}
	@Override
	public void onDeleteButtonClicked() {
		 rpcService.deleteJob(display.getSelectedJobName() ,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchJobDetails();
				Window.alert("Deleted");
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error deleting job details");
			}
		});
		
	}
	@Override
	public void onAddButtonClicked() {
		JobDetails newjobDetail = new JobDetails();
		newjobDetail.setJobName(display.getNewJobName().getValue());
		newjobDetail.setCronSchedule(display.getNewCronSchedule().getValue());
		//newjobDetail.setParameters(display.getNewParameters().getValue());
		//newjobDetail.setLastExecutionDate(display.getNewLastExecutionDate().getValue());
		//newjobDetail.setNextExectuionDate(display.getNewNextExectuionDate().getValue());
		rpcService.addJob(newjobDetail,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchJobDetails();
				Window.alert("added");
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error adding job details");
			}
		});
		
	}
	@Override
	public void onTestButtonClicked() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemSelected(JobDetails selectedItem) {
		// TODO Auto-generated method stub
		
		
	}
	@Override
	public void onItemClicked(JobDetails jobDetails) {
		// TODO Auto-generated method stub
		
	}
	public ArrayList<String> getJobTypeList() {
		return this.jobTypeList;
	}
	public void setJobTypeList(ArrayList<String> jobType) {
		this.jobTypeList = jobType;
	}
	@Override
	public void onRefreshButtonClicked() {
		fetchJobDetails();
	}
	
	@Override
	public void onChangeStateButtonClicked() {
		rpcService.changeStateJob(display.getSelectedJobName() ,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchJobDetails();
				Window.alert("Changed");
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error updating job details");
			}
		});
		
	}
	@Override
	public void onRunButtonClicked() {
		rpcService.runJob(display.getSelectedJobName() ,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchJobDetails();
				Window.alert("Request Submitted");
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error executing job ");
			}
		});
		
	}
	 
	public void onSearchInterruptButtonClicked() {
		rpcService.interruptJob(display.getSelectedJobName() ,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchJobDetails();
				Window.alert("Request Submitted");
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error executing job ");
			}
		});
		
	}
	 
}
