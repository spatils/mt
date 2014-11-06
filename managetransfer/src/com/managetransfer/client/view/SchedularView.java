package com.managetransfer.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.JobDetails;
 

public interface SchedularView<T>  {

	public interface Presenter<T> {
		 void onSearchButtonClicked();
		 void onRefreshButtonClicked();
		 void onChangeStateButtonClicked();
		 void onRunButtonClicked();
		 void onSaveButtonClicked();
		 void onDeleteButtonClicked();
		 void onAddButtonClicked();
		 void onTestButtonClicked();
		 void onItemSelected(T selectedItem);
		 void onItemClicked(JobDetails jobDetails);
		 ArrayList<String> getJobTypeList();
	 }
	
	
	/** Attribute specific functions **/
	
	
	HasValue<String> getViewJobName();
	HasValue<String> getViewCronSchedule();
	HasValue<String> getViewParameters();
	HasValue<Date>   getViewLastExecutionDate();
	HasValue<Date>   getViewNextExectuionDate();
	
	HasValue<String> getEditJobName();
	HasValue<String> getNewCronSchedule();
	HasValue<String> getNewParameters();
	HasValue<Date>   getNewLastExecutionDate();
	HasValue<Date>   getNewNextExectuionDate();
	
	HasValue<String> getNewJobName();
	HasValue<String> getEditCronSchedule();
	HasValue<String> getEditParameters();
	HasValue<Date>   getEditLastExecutionDate();
	HasValue<Date>   getEditNextExectuionDate();
	
	
	JobDetails getSelectedJobName();
	
	/** Generic function**/
	HasValue<String> getSearchString();
	void setRowData(List<T> rowData);
	HasValue<String> getDeleteJobString();
	Widget asWidget();
	void setPresenter(Presenter<T> presenter);
	void setDropDown();
	HasValue<String> getJobs(); //Gets all jobs


}
