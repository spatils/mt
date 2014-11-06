package com.managetransfer.client.view;


import java.util.List;

 

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
 
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
 
 
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
 
 
import com.managetransfer.client.Resource;
import com.managetransfer.client.SequenceDetails;
import com.managetransfer.client.SequenceDetailsMap;
 
import com.google.gwt.user.cellview.client.CellTable; 
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.event.dom.client.ChangeEvent;
 

public class SequencesViewImpl extends Composite     implements SequencesView<SequenceDetails>{

	@UiTemplate("SequencesView.ui.xml")
	interface SequencesViewUiBinder extends UiBinder<Widget, SequencesViewImpl> {
	} 
	
	private static SequencesViewUiBinder uiBinder =	GWT.create(SequencesViewUiBinder.class);
	public SequencesViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	private Presenter<SequenceDetails> presenter;
	private List<SequenceDetails> rowData;
	@UiField(provided=true) CellTable<SequenceDetails> sequenceTable = new CellTable<SequenceDetails>();
	@UiField SimplePager simplePager;
	@UiField TextBox  searchString = new TextBox();
	@UiField Button searchButton;
	@UiField Button deleteButton;
	@UiField Button saveButton;
	@UiField Button searchAddButton;
	@UiField Button searchDeleteButton;
	@UiField Button searchTestButton;
	@UiField Button searchDetailsButton;
	@UiField Button addNewSequence;
	@UiField TextBox  newSequenceName = new TextBox();
	@UiField TextBox  newNumberOfPhases = new TextBox();
	 
	@UiField TextBox  cronFreuency = new TextBox();
	@UiField TabLayoutPanel tabLayoutPanelMain;
	@UiField VerticalPanel drawingCanvas;
	@UiField VerticalPanel verticalPanelSequence;
	@UiField ListBox phaseTypeList;
	@UiField ListBox machineNameList;
	@UiField TextBox threadsField = new TextBox();
	@UiField TextBox batchSize = new TextBox();
	@UiField TextBox commitCount = new TextBox();
	@UiField CheckBox dailySplit = new CheckBox();
	private String selectedSequenceName ;
	private SequenceDetails selectedSequence = new SequenceDetails();
	@Override
	public void setPresenter(Presenter<SequenceDetails> presenter) {
		// TODO Auto-generated method stub
		sequenceTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		final SingleSelectionModel<SequenceDetails> selectionModel = new SingleSelectionModel<SequenceDetails>();
		sequenceTable.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
		      SequenceDetails selected = selectionModel.getSelectedObject();
		        if (selected != null) {
		        	searchDeleteButton.setEnabled(true);
		    		searchTestButton.setEnabled(true);
		    		searchDetailsButton.setEnabled(true);
		    		selectedSequenceName = selected.getSequenceName();
		    	  }else{
		        	searchDeleteButton.setEnabled(false);
		    		searchTestButton.setEnabled(false);
		    		searchDetailsButton.setEnabled(false);
		        }
		      }
		    });
		//verticalPanelSequence.
		
		this.presenter = presenter;
	}


	@Override
	public HasValue<String> getSequences() {
		// TODO Auto-generated method stub
		return newSequenceName;
	}


	@Override
	public  HasValue<String> getNewSequenceName() {
		// TODO Auto-generated method stub
		return newSequenceName;
	}


	@Override
	public  HasValue<String> getNewNumberOfPhases() {
		// TODO Auto-generated method stub
		return newNumberOfPhases;
	}


	@Override
	public HasValue<String> getRegionName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HasValue<String> getSearchString() {
		// TODO Auto-generated method stub
		
		return searchString;
	}


	@Override
	public HasValue<String> getDeleteSequenceString() {
		// TODO Auto-generated method stub
		return null;
	}
    @Override
	public HasValue<String> getEditDataSource() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HasValue<String> getEditSequenceName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HasValue<String> getEditNumberOfPhases() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HasValue<String> getEditUserName() {
		// TODO Auto-generated method stub
		return null;
	}
	 
	TextColumn<SequenceDetails> sequenceName = new TextColumn<SequenceDetails>(){
		public String getValue(SequenceDetails hr){
			return hr.getSequenceName();
		}
	};
	TextColumn<SequenceDetails> numberOfPhases = new TextColumn<SequenceDetails>(){
		public String getValue(SequenceDetails hr){
			return ""+hr.getNumberOfPhases();
		}
	};
	TextColumn<SequenceDetails> region = new TextColumn<SequenceDetails>(){
		public String getValue(SequenceDetails hr){
			return hr.getRegionName();
		}
	};
	
	  
	@UiHandler("searchDeleteButton")
	void onSearchDeleteButtonClick(ClickEvent event) {
		if (presenter != null) {
			presenter.onDeleteButtonClicked();
		}
		
	}
	@UiHandler("searchAddButton")
	void onSearchAddButtonClick(ClickEvent event) {
		tabLayoutPanelMain.selectTab(1);
	}
	public void setRowData(List<SequenceDetails> rowData) {
		// TODO Auto-generated method stub
		if(sequenceTable.getColumnCount() == 0 ){
			sequenceTable.addColumn(sequenceName,"Sequence Name");
			sequenceTable.addColumn(numberOfPhases,"Number Of Phases");
			sequenceTable.addColumn(region,"Name Of Region");
			 
		}
		ListDataProvider<SequenceDetails> dataProvider = new ListDataProvider<SequenceDetails>();
		dataProvider.addDataDisplay(sequenceTable); 
		List<SequenceDetails> listSearchResult = dataProvider.getList();
		listSearchResult.clear(); 
		for(SequenceDetails sr : rowData){
			listSearchResult.add(sr);
		}
		simplePager.setDisplay(sequenceTable); 
		simplePager.setPageSize(10);
		 
	}


	public String getSelectedSequenceName() {
		return selectedSequenceName;
	}


	public void setSelectedSequenceName(String selectedSequenceName) {
		this.selectedSequenceName = selectedSequenceName;
	}
	
	 
	@UiHandler("searchButton")
	void onSearchButtonClick(ClickEvent event) {
		if (presenter != null) {
			presenter.onSearchButtonClicked();
		}
	}
	@UiHandler("addNewSequence")
	void onAddNewSequenceClick(ClickEvent event) {
		if (presenter != null) {
			presenter.onAddButtonClicked();
			tabLayoutPanelMain.selectTab(2);
		}
	}
	 


	public SequenceDetails getSelectedSequence() {
		return selectedSequence;
	}


	public void setSelectedSequence(SequenceDetails selectedSequence) {
		this.selectedSequence = selectedSequence;
	}
	@UiHandler("searchDetailsButton")
	void onSearchDetailsButtonClick(ClickEvent event) {
		setSelectedSequence(presenter.getSelectedItem(selectedSequenceName));
		onPaintSequence(getSelectedSequence());
		tabLayoutPanelMain.selectTab(2);
	}


	@Override
	public void onPaintSequence(SequenceDetails sequenceDetails) {
		drawingCanvas.clear();
		Window.alert("Inside onPaintSequence ");
		setSelectedSequence(sequenceDetails);
		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(2);
		hp.setHorizontalAlignment(	HasHorizontalAlignment.ALIGN_CENTER);
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		drawingCanvas.add(hp);
		for(int i=0 ; i < sequenceDetails.getNumberOfPhases();i++){
			if(i!=0){
				Image arrowImg = new Image();
				arrowImg.setUrl(Resource.INSTANCE.arrow().getSafeUri());
				arrowImg.setPixelSize(47, 35);
				hp.add(arrowImg);
			}
			Label phaseLabel = new Label();
			phaseLabel.setText(sequenceDetails.getSequenceDetails().get(i).getPhaseName());
			phaseLabel.setStyleName("headerBackGrndGrey3");
			phaseLabel.setTitle(""+i);
			//Enabling Drag Option Start
			phaseLabel.getElement().setDraggable(Element.DRAGGABLE_TRUE);
			phaseLabel.addDragStartHandler(new DragStartHandler(){
				public void onDragStart(DragStartEvent event){
					Label labelObj = (Label) event.getSource();
					event.setData("text", labelObj.getTitle());
					event.getDataTransfer().setDragImage(labelObj.getElement(), 10, 10);
				}
				
			});
			phaseLabel.addDragOverHandler(new DragOverHandler(){
				public void onDragOver(DragOverEvent event){
					
				}
			});
			phaseLabel.addDropHandler(new DropHandler(){
				public void onDrop(DropEvent event){
					event.preventDefault();
					Label labelObj = (Label) event.getSource();
					int sourceId = Integer.parseInt( event.getData("text"));
					int targetId =  Integer.parseInt(labelObj.getTitle());
					SequenceDetailsMap sdm  = selectedSequence.getSequenceDetails().get(sourceId);
					selectedSequence.getSequenceDetails().remove(sourceId);
					selectedSequence.getSequenceDetails().add(targetId, sdm);
					onPaintSequence(getSelectedSequence());
				}
			}) ;
			//Enabling Drag Option End
			hp.setHeight("70px");
			hp.add(phaseLabel);
			phaseLabel.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					Label labelObj = (Label) event.getSource();
					int selectSequence = Integer.parseInt(labelObj.getTitle());
					SequenceDetailsMap sdm = getSelectedSequence().getSequenceDetails().get(selectSequence);
					cronFreuency.setText(sdm.getCronFrequency());
					cronFreuency.setTitle(""+selectSequence);
					threadsField.setText(""+sdm.getThreadCount());
					commitCount.setText(""+sdm.getCommitCount());
					batchSize.setText(""+sdm.getBatchSize());
					if(sdm.getDailySplit()==1){
						dailySplit.setValue(true);
					}else{
						dailySplit.setValue(false);
					}
					for(int k=0;k < presenter.getPhaseTypeList().size() ; k++ ){
						if(presenter.getPhaseTypeList().get(k).equals(sdm.getPhaseName())) {
							phaseTypeList.setSelectedIndex(k);
						}
					}
					for(int k=0;k < presenter.getMachineTypeList().size() ; k++ ){
						if(presenter.getMachineTypeList().get(k).equals(sdm.getMachineName())) {
							machineNameList.setSelectedIndex(k);
						}
					}
				}
			});
			 
		}
		
	}
	@UiHandler("deleteButton")
	void onDeleteButtonClick(ClickEvent event) {
		getSelectedSequence().getSequenceDetails().remove(Integer.parseInt(cronFreuency.getTitle()));
		getSelectedSequence().setNumberOfPhases(getSelectedSequence().getSequenceDetails().size());
		presenter.onSaveButtonClicked();
		onPaintSequence(getSelectedSequence());
	}
	
	@UiHandler("addButton")
	void onAddButtonClick(ClickEvent event) {
		SequenceDetailsMap sdm =   new SequenceDetailsMap(getSelectedSequence().getSequenceName(),getSelectedSequence().getSequenceDetails().size(),"* * 1 * * ?",presenter.getMachineTypeList().get(0),1,presenter.getPhaseTypeList().get(0),1000,10000,0);
	    getSelectedSequence().getSequenceDetails().add(sdm);
		getSelectedSequence().setNumberOfPhases(getSelectedSequence().getSequenceDetails().size());
		presenter.onSaveButtonClicked();
		onPaintSequence(getSelectedSequence());
	}
	@UiHandler("saveButton")
	void onSaveButtonClick(ClickEvent event) {
		int selectedPhase = Integer.parseInt(cronFreuency.getTitle());
		getSelectedSequence().getSequenceDetails().get(selectedPhase).setCronFrequency(cronFreuency.getText());
		getSelectedSequence().getSequenceDetails().get(selectedPhase).setThreadCount(Integer.parseInt(threadsField.getText()));
		getSelectedSequence().getSequenceDetails().get(selectedPhase).setCommitCount(Integer.parseInt(commitCount.getText()));
		getSelectedSequence().getSequenceDetails().get(selectedPhase).setBatchSize(Integer.parseInt(batchSize.getText()));
		if(dailySplit.getValue()){
			getSelectedSequence().getSequenceDetails().get(selectedPhase).setDailySplit(1);
 		}else{
 			getSelectedSequence().getSequenceDetails().get(selectedPhase).setDailySplit(0);
 		}
		presenter.onSaveButtonClicked();
	}


	@Override
	public void setDropDownMachine() {
		if(machineNameList!=null &  machineNameList.getItemCount() !=0){
			machineNameList.clear();
		}
		if( presenter.getMachineTypeList() !=null){
			for(int i=0; i < presenter.getMachineTypeList().size(); i++){
				machineNameList.addItem(presenter.getMachineTypeList().get(i));
			}
		}
	}
	@Override
	public void setDropDownPhase() {
		if(phaseTypeList!=null &  phaseTypeList.getItemCount() !=0){
			phaseTypeList.clear();
		}
		if( presenter.getPhaseTypeList() !=null){
			for(int i=0; i < presenter.getPhaseTypeList().size(); i++){
				phaseTypeList.addItem(presenter.getPhaseTypeList().get(i));
			}
		}
		
	}
	@UiHandler("phaseTypeList")
	void onPhaseTypeListChange(ChangeEvent event) {
		int selectedPhase = Integer.parseInt(cronFreuency.getTitle());
		getSelectedSequence().getSequenceDetails().get(selectedPhase).setPhaseName(presenter.getPhaseTypeList().get(phaseTypeList.getSelectedIndex()));
		onPaintSequence(getSelectedSequence());
		
	}
	@UiHandler("machineNameList")
	void onMachineNameListChange(ChangeEvent event) {
		int selectedPhase = Integer.parseInt(cronFreuency.getTitle());
		getSelectedSequence().getSequenceDetails().get(selectedPhase).setMachineName(presenter.getMachineTypeList().get(machineNameList.getSelectedIndex()));
		onPaintSequence(getSelectedSequence());
	}


	@Override
	public HasValue<String> getCommitCount() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HasValue<String> getBatchSize() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Boolean getDailySplit() {
		// TODO Auto-generated method stub
		return dailySplit.getValue();
	}
}
