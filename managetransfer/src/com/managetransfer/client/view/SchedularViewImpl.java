package com.managetransfer.client.view;

import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.JobDetails;
 
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import java.util.Date;

import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.datepicker.client.DateBox;
 

public class SchedularViewImpl extends Composite implements SchedularView<JobDetails>{
	@UiTemplate("SchedularView.ui.xml")
	interface JobsViewUiBinder extends UiBinder<Widget, SchedularViewImpl> {
	} 
	private List<JobDetails> rowData;
	private static JobsViewUiBinder uiBinder =	GWT.create(JobsViewUiBinder.class);
	@UiField(provided=true) CellTable<JobDetails> jobTable = new CellTable<JobDetails>();
	@UiField SimplePager simplePager;
	@UiField TextBox  searchString = new TextBox();
	@UiField Button searchButton;
	@UiField Button searchRefreshButton;
	@UiField Button searchChangeSateButton;
	@UiField Button searchDeleteButton;
	@UiField Button searchRunButton;
	@UiField Button searchInterruptButton;
	@UiField TabLayoutPanel tabLayoutPanelMain;
	 
	 
			 
	public SchedularViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		 
	}
	private Presenter<JobDetails> presenter;
	private JobDetails selectedJobDetails = new JobDetails();
	private List<String> jobTypes;
	@Override
	public void setPresenter(Presenter<JobDetails> presenter) {
		// TODO Auto-generated method stub
		jobTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		final SingleSelectionModel<JobDetails> selectionModel = new SingleSelectionModel<JobDetails>();
		jobTable.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
		    	selectedJobDetails = selectionModel.getSelectedObject();
		    	
		        if (selectedJobDetails != null) {
		        	searchChangeSateButton.setEnabled(true);
		    		searchDeleteButton.setEnabled(true);
		    		searchRunButton.setEnabled(true);
		    		searchInterruptButton.setEnabled(true);
		        }else{
		        	searchChangeSateButton.setEnabled(false);
		    		searchDeleteButton.setEnabled(false);
		    		searchRunButton.setEnabled(false);
		    		searchInterruptButton.setEnabled(false);
		        }
		      }
		    });
		this.presenter = presenter;
		 
		 
	}
	@Override
	public void setRowData(List<JobDetails> rowData) {
		// TODO Auto-generated method stub
		 
		if(jobTable.getColumnCount() == 0 ){
			jobTable.addColumn(colJobName,"Job Name");
			jobTable.addColumn(colCronSchedule,"Cron Schedule");
			jobTable.addColumn(colServerName,"Server Name");
			jobTable.addColumn(lastExecutionDate,"Last Execution Date");
			jobTable.addColumn(nextExecutionDate,"Next Execution Date");
			jobTable.addColumn(jobState,"Job State");
			 
		}
		ListDataProvider<JobDetails> dataProvider = new ListDataProvider<JobDetails>();
		 
		
		dataProvider.addDataDisplay(jobTable); 
		List<JobDetails> listSearchResult = dataProvider.getList();
		
		 
		listSearchResult.clear(); 
		for(JobDetails sr : rowData){
			listSearchResult.add(sr);
		}
		simplePager.setDisplay(jobTable); 
		simplePager.setPageSize(10);
		 
	}
	 
	TextColumn<JobDetails> colJobName = new TextColumn<JobDetails>(){
		public String getValue(JobDetails hr){
			return hr.getJobName();
		}
	};
	TextColumn<JobDetails> colCronSchedule = new TextColumn<JobDetails>(){
		public String getValue(JobDetails hr){
			return hr.getCronSchedule();
		}
	};
	TextColumn<JobDetails> colServerName = new TextColumn<JobDetails>(){
		public String getValue(JobDetails hr){
			return hr.getServerName() ;
		}
	};
	
	TextColumn<JobDetails> lastExecutionDate = new TextColumn<JobDetails>(){
		public String getValue(JobDetails hr){
			return hr.getLastExecutionDate();
		}
	};
	TextColumn<JobDetails> nextExecutionDate = new TextColumn<JobDetails>(){
		public String getValue(JobDetails hr){
			return hr.getNextExectuionDate();
		}
	};
	TextColumn<JobDetails> jobState = new TextColumn<JobDetails>(){
		public String getValue(JobDetails hr){
			return hr.getJobState();
		}
	};
	  
	//Implementation Related to actions 
	 
	@UiHandler("searchButton")
	void onSearchButtonClick(ClickEvent event) {
		if (presenter != null) {
			presenter.onSearchButtonClicked();
		}
	}
	
	
	@UiHandler("searchRefreshButton")
		void onSearchRefreshButton(@SuppressWarnings("unused") ClickEvent event){
		presenter.onRefreshButtonClicked();
		 	 
	}
	@UiHandler("searchChangeSateButton")
		void onEditButton(@SuppressWarnings("unused") ClickEvent event){
		presenter.onChangeStateButtonClicked();
		 
	}
	@UiHandler("searchDeleteButton")
		void onDeleteButton(@SuppressWarnings("unused") ClickEvent event){
		if (presenter != null) {
			presenter.onDeleteButtonClicked();
		}
	}
	@UiHandler("searchRunButton")
		void onDetailsButton(@SuppressWarnings("unused") ClickEvent event){
		presenter.onRunButtonClicked();
		 
	}
	@Override
	public HasValue<String> getSearchString() {
		// TODO Auto-generated method stub
		return searchString;
	}
	/** Attribute related implementations***/
	@Override
	public HasValue<String> getJobs() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<String> getViewJobName() {
		// TODO Auto-generated method stub
		return  null;
	}
	@Override
	public HasValue<String> getViewCronSchedule() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<String> getViewParameters() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<Date> getViewLastExecutionDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<Date> getViewNextExectuionDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<String> getEditJobName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<String> getNewCronSchedule() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<String> getNewParameters() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<Date> getNewLastExecutionDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<Date> getNewNextExectuionDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<String> getNewJobName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<String> getEditCronSchedule() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<String> getEditParameters() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<Date> getEditLastExecutionDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<Date> getEditNextExectuionDate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasValue<String> getDeleteJobString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setDropDown() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public JobDetails   getSelectedJobName() {
		return selectedJobDetails ;
	}
 
	@UiHandler("searchInterruptButton")
	void onSearchInterruptButtonClick(ClickEvent event) {
		if (presenter != null) {
			presenter.onSearchInterruptButtonClicked();
		}

	}
}
