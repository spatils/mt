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

import com.managetransfer.client.ManageTransferServiceAsync;

import com.managetransfer.client.SequenceDetails;
import com.managetransfer.client.SequenceDetailsMap;
import com.managetransfer.client.view.HomePageViewImpl;
import com.managetransfer.client.view.SequencesView;
 

public class SequencesPresenter implements Presenter, SequencesView.Presenter<SequenceDetails> {

	private List<SequenceDetails> sequenceDetails;
	private final ManageTransferServiceAsync rpcService;
	private final EventBus eventBus;
	private final SequencesView<SequenceDetails> display;
	private final SelectionModel<SequenceDetails> selectionModel;
	private ArrayList<String> phaseTypeList = new ArrayList<String>() ;
	private ArrayList<String> machineTypeList = new ArrayList<String>() ;

	public SequencesPresenter(ManageTransferServiceAsync rpcService, EventBus eventBus,SequencesView<SequenceDetails> display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		this.selectionModel = new SelectionModel<SequenceDetails>();
		display.setPresenter(this);
		fetchDropDownList();
	}
	private void fetchDropDownList() {
		fetchPhaseType();
		fetchMatchineType();
	}
	private void fetchMatchineType() {
		rpcService.getMachineList( new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				setMachineTypeList(result);
				display.setDropDownMachine();
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching machine details");
			}
		}); 
		
	}
	private void fetchPhaseType() {
		rpcService.getPhaseList( new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onSuccess(ArrayList<String> result) {
				setPhaseTypeList(result);
				display.setDropDownPhase();
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching phase details");
			}
		});
		
	}
	@Override
	public void go(final HasWidgets container) {
		Iterator<Widget> iteratorOfWidgets =container.iterator();
		HomePageViewImpl hpvi = (HomePageViewImpl) iteratorOfWidgets.next();
		hpvi.addContentArea(display.asWidget());
		fetchSequenceDetails();
		 
	}
	@Override
	public void onSearchButtonClicked() {
		// TODO Auto-generated method stub
		rpcService.searchSequenceResult(display.getSearchString().getValue(),new AsyncCallback<ArrayList<SequenceDetails>>() {
			@Override
			public void onSuccess(ArrayList<SequenceDetails> result) {
				setSequenceDetails(result);
				display.setRowData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error search sequence details");
			}
		});
		
	}
	@Override
	public void onSaveButtonClicked() {
		// TODO Auto-generated method stub
		Window.alert(" onSaveButtonClicked :: size"+display.getSelectedSequence().getSequenceDetails().size());
		for (int i=0 ; i <display.getSelectedSequence().getSequenceDetails().size();i++ ){
			Window.alert(" onSaveButtonClicked :: size"+display.getSelectedSequence().getSequenceDetails().get(i).getSequenceNumber());
		}
		rpcService.editSequence(display.getSelectedSequence() ,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchSequenceDetails();
				Window.alert("Saved Phases");
				
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error Saving Sequence details");
			}
		});
	}
	@Override
	public void onDeleteButtonClicked() {
		// TODO Auto-generated method stub
		 rpcService.deleteSequence(display.getSelectedSequenceName() ,new AsyncCallback<Boolean>() {
				@Override
				public void onSuccess( Boolean result) {
					fetchSequenceDetails();
					Window.alert("Deleted");
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error deleting sequence details");
				}
			});
		
	}
	@Override
	public void onAddButtonClicked() {
		 Window.alert("Inside onAddButtonClicked");
		 List<SequenceDetailsMap>  sdm  = new ArrayList<SequenceDetailsMap>();
		 sdm = getDefaultPhases(Integer.parseInt(display.getNewNumberOfPhases().getValue()));
	     SequenceDetails newSequeneDetail = new SequenceDetails();
		 newSequeneDetail.setRegionName("dev");
		 newSequeneDetail.setSequenceName(display.getNewSequenceName().getValue());
		 newSequeneDetail.setSequenceDetails(sdm);
		 newSequeneDetail.setNumberOfPhases(Integer.parseInt(display.getNewNumberOfPhases().getValue()));
		 paintDetails(newSequeneDetail);
		 rpcService.addSequence(newSequeneDetail,new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess( Boolean result) {
				fetchSequenceDetails();
				Window.alert("added");
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error adding sequence details");
			}
		});
		
	}
	@Override
	public void onTestButtonClicked() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemSelected(SequenceDetails selectedItem) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemClicked(SequenceDetails seqeunceDetails) {
		// TODO Auto-generated method stub
		
	}
	 
	private void fetchSequenceDetails() {
		rpcService.getSequenceResult("dev",new AsyncCallback<ArrayList<SequenceDetails>>() {
			@Override
			public void onSuccess(ArrayList<SequenceDetails> result) {
				setSequenceDetails(result);
				display.setRowData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching sequence details");
			}
		});
	}
	@Override
	public void paintDetails(SequenceDetails seqeunceDetails) {
		Window.alert("Inside paintDetails");
		display.onPaintSequence(seqeunceDetails);
		
	}
	public List<SequenceDetailsMap> getDefaultPhases( int numberOfPhases){
		  List<SequenceDetailsMap> sequenceDetails = new ArrayList<SequenceDetailsMap>();
		  for (int i=0; i < numberOfPhases ; i++){
			  sequenceDetails.add(new SequenceDetailsMap(display.getNewSequenceName().getValue(),i,"* * 1 * * ?",machineTypeList.get(0),1,phaseTypeList.get(0),1000,10000,0));
		  }
		  return sequenceDetails;
	}
	public List<SequenceDetails> getSequenceDetails() {
		return sequenceDetails;
	}
	public void setSequenceDetails(List<SequenceDetails> sequenceDetails) {
		this.sequenceDetails = sequenceDetails;
	}
	@Override
	public SequenceDetails getSelectedItem(String sequenceName) {
		for(SequenceDetails sr : sequenceDetails){
			if (sr.getSequenceName().equals(sequenceName)){
				return sr;
			}
		}
		return null;
	}
	public ArrayList<String> getPhaseTypeList() {
		return phaseTypeList;
	}
	public void setPhaseTypeList(ArrayList<String> phaseTypeList) {
		this.phaseTypeList = phaseTypeList;
	}
	public ArrayList<String> getMachineTypeList() {
		return machineTypeList;
	}
	public void setMachineTypeList(ArrayList<String> machineTypeList) {
		this.machineTypeList = machineTypeList;
	}
	
		 
}
