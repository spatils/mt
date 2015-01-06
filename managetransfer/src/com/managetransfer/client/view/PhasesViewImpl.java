package com.managetransfer.client.view;

import java.util.HashMap;
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
import com.google.gwt.user.client.ui.VerticalPanel;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.PhasesDetails;
 
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
import com.google.gwt.event.dom.client.ChangeEvent;
 

public class PhasesViewImpl extends Composite implements PhasesView<PhasesDetails>{
	@UiTemplate("PhasesView.ui.xml")
	interface PhasesViewUiBinder extends UiBinder<Widget, PhasesViewImpl> {
	} 
	private List<PhasesDetails> rowData;
	private static PhasesViewUiBinder uiBinder =	GWT.create(PhasesViewUiBinder.class);
	@UiField(provided=true) CellTable<PhasesDetails> phaseTable = new CellTable<PhasesDetails>();
	@UiField SimplePager simplePager;
	@UiField TextBox  newPhaseName = new TextBox();
	@UiField ListBox  newPhaseType = new ListBox();
 
	@UiField TextBox  searchString = new TextBox();
	@UiField DisclosurePanel workAreaPanel = new DisclosurePanel();
	@UiField Button searchButton;
	@UiField Button saveButton;
	@UiField Button searchAddButton;
	@UiField Button searchEditButton;
	@UiField Button searchDeleteButton;
	@UiField Button searchTestButton;
	@UiField Button searchDetailsButton;
	@UiField TabLayoutPanel tabLayoutPanelMain;
	@UiField TabLayoutPanel tabLayoutPanelDetails;
	@UiField VerticalPanel addPhaseVerticalPanel = new VerticalPanel();
	@UiField Button addPhase;
	PhaseDocumentumContentExport phaseDocumentumContentExport = new PhaseDocumentumContentExport();
	PhaseDocumentumMetadataExport phaseDocumentumMetadataExport = new PhaseDocumentumMetadataExport();
	PhaseDocumentumExport phaseDocumentumExport = new PhaseDocumentumExport();
	PhaseInitDocumentumSeq phaseInitDocumentumSeq = new PhaseInitDocumentumSeq();
	PhaseSharePointImport phaseSharePointImport = new PhaseSharePointImport();
	PhaseTransformation phaseTransformation = new PhaseTransformation();
	PhaseDocumentumImport phaseDocumentumImportD7 = new PhaseDocumentumImport();
	PhaseExportACL  phaseExportACL = new PhaseExportACL();
	PhaseImportACLD7 phaseImportACLD7 = new PhaseImportACLD7();
	PhaseExportProcessD6  phaseExportProcessD6 = new PhaseExportProcessD6();
	PhaseImportProcessD7 phaseImportProcessD7 = new PhaseImportProcessD7();
	
	private String selectedPhase = new String("");
	private String selectedPhaseType = new String("");
			 
	public PhasesViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		 
	}
	private Presenter<PhasesDetails> presenter;
	private PhasesDetails selectedPhaseDetails = new PhasesDetails();
	 
	@Override
	public void setPresenter(Presenter<PhasesDetails> presenter) {
		// TODO Auto-generated method stub
		phaseTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		final SingleSelectionModel<PhasesDetails> selectionModel = new SingleSelectionModel<PhasesDetails>();
		phaseTable.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
		    	selectedPhaseDetails = selectionModel.getSelectedObject();
		        if (selectedPhaseDetails != null) {
		        	selectedPhase 	= selectedPhaseDetails.getPhaseName();
		        	selectedPhaseType = selectedPhaseDetails.getPhaseType();
		        	searchEditButton.setEnabled(true);
		    		searchDeleteButton.setEnabled(true);
		    		searchTestButton.setEnabled(true);
		    		searchDetailsButton.setEnabled(true);
		        }else{
		        	searchEditButton.setEnabled(false);
		    		searchDeleteButton.setEnabled(false);
		    		searchTestButton.setEnabled(false);
		    		searchDetailsButton.setEnabled(false);
		        }
		      }
		    });
		this.presenter = presenter;
		 
		 
	}
	@Override
	public void setRowData(List<PhasesDetails> rowData) {
		// TODO Auto-generated method stub
		 
		if(phaseTable.getColumnCount() == 0 ){
			phaseTable.addColumn(colPhaseName,"Phase Name");
			phaseTable.addColumn(colCronSchedule,"Phase Type");
		}
		ListDataProvider<PhasesDetails> dataProvider = new ListDataProvider<PhasesDetails>();
		dataProvider.addDataDisplay(phaseTable); 
		List<PhasesDetails> listSearchResult = dataProvider.getList();
		listSearchResult.clear(); 
		for(PhasesDetails sr : rowData){
			listSearchResult.add(sr);
		}
		simplePager.setDisplay(phaseTable); 
		simplePager.setPageSize(10);
		 
	}
	 
	TextColumn<PhasesDetails> colPhaseName = new TextColumn<PhasesDetails>(){
		public String getValue(PhasesDetails hr){
			return hr.getPhaseName();
		}
	};
	TextColumn<PhasesDetails> colCronSchedule = new TextColumn<PhasesDetails>(){
		public String getValue(PhasesDetails hr){
			return hr.getPhaseType();
		}
	};
	 
	//Implementation Related to actions 
	 
	@UiHandler("searchButton")
	void onSearchButtonClick(ClickEvent event) {
		if (presenter != null) {
			presenter.onSearchButtonClicked();
		}
	}
	@UiHandler("saveButton")
	void onSaveButtonClick(ClickEvent event) {
		if (presenter != null) {
			presenter.onSaveButtonClicked();
			tabLayoutPanelMain.selectTab(0);
		}
	}
	
	@UiHandler("searchAddButton")
		void onSearchAddButton(@SuppressWarnings("unused") ClickEvent event){
		tabLayoutPanelMain.selectTab(1);
		tabLayoutPanelDetails.selectTab(0);	 
	}
	@UiHandler("searchEditButton")
		void onEditButton(@SuppressWarnings("unused") ClickEvent event){
		tabLayoutPanelMain.selectTab(1);
		tabLayoutPanelDetails.selectTab(1);
		presenter.onSearchEditButtonClicked();
	    
		 
	}
	@UiHandler("searchDeleteButton")
		void onDeleteButton(@SuppressWarnings("unused") ClickEvent event){
		if (presenter != null) {
			presenter.onDeleteButtonClicked();
		}
	}
	@UiHandler("searchDetailsButton")
		void onDetailsButton(@SuppressWarnings("unused") ClickEvent event){
		tabLayoutPanelMain.selectTab(1);
		tabLayoutPanelDetails.selectTab(0);
		presenter.onSearchDetailsButtonClicked();
		 
	}
	 
	

	@Override
	public HasValue<String> getSearchString() {
		// TODO Auto-generated method stub
		return searchString;
	}
	/** Attribute related implementations***/

 
	@Override
	public String getDeletePhaseString() {
		// TODO Auto-generated method stub
		return this.selectedPhase;
	}
	@Override
	public void setDropDown() {
		 if(newPhaseType!=null && newPhaseType.getItemCount()>0){
			 newPhaseType.clear();
		 }
		 for (int i=0; i< this.presenter.getPhaseTypeList().size();i++){
			 newPhaseType.addItem( this.presenter.getPhaseTypeList().get(i));
		 }
		 
	}
	@Override
	public HasValue<String> getPhases() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getSelectedPhase() {
		return selectedPhase;
	}
	public void setSelectedPhase(String selectedPhase) {
		this.selectedPhase = selectedPhase;
	}

	@UiHandler("newPhaseType")
	void onNewPhaseTypeChange(ChangeEvent event) {
		 
		 
	}
	public void displayPhaseInformation(boolean readOnly , PhasesDetails phaseDetails){
		tabLayoutPanelDetails.selectTab(1);
		addPhaseVerticalPanel.clear();
		this.setSelectedPhaseType(phaseDetails.getPhaseType());
		this.setSelectedPhase(phaseDetails.getPhaseName());
		if(this.getSelectedPhaseType().equals("ExportContentFromDocumentum")){
			addPhaseVerticalPanel.add(phaseDocumentumContentExport);
			phaseDocumentumContentExport.setReadOnly(readOnly);
			phaseDocumentumContentExport.setPhaseDetailsString(phaseDetails.getPhaseParametersString());
			 
		}
		if(this.getSelectedPhaseType().equals("ExportMetadataFromDocumentum")){
			addPhaseVerticalPanel.add(phaseDocumentumMetadataExport);
			phaseDocumentumMetadataExport.setReadOnly(readOnly);
			phaseDocumentumMetadataExport.setPhaseDetailsString(phaseDetails.getPhaseParametersString());
		}
		if(this.getSelectedPhaseType().equals("ExportFromDocumentum")){
			addPhaseVerticalPanel.add(phaseDocumentumExport);
			phaseDocumentumExport.setReadOnly(readOnly);
			phaseDocumentumExport.setPhaseDetailsString(phaseDetails.getPhaseParametersString());
			phaseDocumentumExport.setPhaseDetailsInteger(phaseDetails.getPhaseParametersInteger());
		}
		if(this.getSelectedPhaseType().equals("InitDocumentumBatch")){
			addPhaseVerticalPanel.add(phaseInitDocumentumSeq);
			phaseInitDocumentumSeq.setReadOnly(readOnly);
			phaseInitDocumentumSeq.setPhaseDetailsString(phaseDetails.getPhaseParametersString());
		}if(this.getSelectedPhaseType().equals("ImportToSharePoint")){
			addPhaseVerticalPanel.add(phaseSharePointImport);
			phaseSharePointImport.setPhaseDetailsString(phaseDetails.getPhaseParametersString());
			phaseSharePointImport.setPhaseDetailsInteger(phaseDetails.getPhaseParametersInteger());
		}if(this.getSelectedPhaseType().equals("Transformation")){
			addPhaseVerticalPanel.add(phaseTransformation);
			phaseTransformation.setPhaseDetailsString(phaseDetails.getPhaseParametersString());
		}
		if(this.getSelectedPhaseType().equals("ImportToDocumentumD7")){
			addPhaseVerticalPanel.add(phaseDocumentumImportD7);
			phaseDocumentumImportD7.setPhaseDetailsString(phaseDetails.getPhaseParametersString());
			phaseDocumentumImportD7.setPhaseDetailsInteger(phaseDetails.getPhaseParametersInteger());
		}
		if(this.getSelectedPhaseType().equals("ExportACL")){
			addPhaseVerticalPanel.add(phaseExportACL);
			phaseExportACL.setPhaseDetailsString(phaseDetails.getPhaseParametersString());
		}
		if(this.getSelectedPhaseType().equals("ImportACLD7")){
			addPhaseVerticalPanel.add(phaseImportACLD7);
			phaseImportACLD7.setPhaseDetailsString(phaseDetails.getPhaseParametersString());
		}
		if(this.getSelectedPhaseType().equals("ExportProcessD6")){
			addPhaseVerticalPanel.add(phaseExportProcessD6);
			phaseExportProcessD6.setPhaseDetailsString(phaseDetails.getPhaseParametersString());
		}
		if(this.getSelectedPhaseType().equals("ImportProcessD7")){
			addPhaseVerticalPanel.add(phaseImportProcessD7);
			phaseImportProcessD7.setPhaseDetailsString(phaseDetails.getPhaseParametersString());
		}
	}
	@Override
	public String getNewPhaseName() {
		 return newPhaseName.getValue();
	}
	@Override
	public String getNewPhaseType() {
		// TODO Auto-generated method stub
		return presenter.getPhaseTypeList().get(newPhaseType.getSelectedIndex()) ;
	}
	 
	@UiHandler("cancelButton")
	void onButtonClick(ClickEvent event) {
		tabLayoutPanelMain.selectTab(0);
	}
	@Override
	public HashMap<String, String> getPhaseParametersString() {
		if(this.getSelectedPhaseType().equals("ExportContentFromDocumentum")){
			return phaseDocumentumContentExport.getPhaseDetailsString();
		}
		else if(this.getSelectedPhaseType().equals("ExportMetadataFromDocumentum")){
			return phaseDocumentumMetadataExport.getPhaseDetailsString();
		}
		else if(this.getSelectedPhaseType().equals("ExportFromDocumentum")){
			return phaseDocumentumExport.getPhaseDetailsString();
		}
		else if(this.getSelectedPhaseType().equals("InitDocumentumBatch")){
			return phaseInitDocumentumSeq.getPhaseDetailsString();
		}else if(this.getSelectedPhaseType().equals("ImportToSharePoint")){
			
			 return phaseSharePointImport.getPhaseDetailsString();
		}else if(this.getSelectedPhaseType().equals("Transformation")){
			return phaseTransformation.getPhaseDetailsString();
		} else if(this.getSelectedPhaseType().equals("ImportToDocumentumD7")){
			return phaseDocumentumImportD7.getPhaseDetailsString();
	    }else if(this.getSelectedPhaseType().equals("ExportACL")){
	    	return phaseExportACL.getPhaseDetailsString();
	    }else if(this.getSelectedPhaseType().equals("ImportACLD7")){
	    	return phaseImportACLD7.getPhaseDetailsString();
	    }else if(this.getSelectedPhaseType().equals("ExportProcessD6")){
	    	return phaseExportProcessD6.getPhaseDetailsString();
	    }else if(this.getSelectedPhaseType().equals("ImportProcessD7")){
	    	return phaseImportProcessD7.getPhaseDetailsString();
	    }

		return null;
		
		
		
	}
	@Override
	public HashMap<String, Integer> getPhaseParametersInteger() {
		if(this.getSelectedPhaseType().equals("DocumentumContentExport")){
			return phaseDocumentumContentExport.getPhaseDetailsInteger();
		}
		else if(this.getSelectedPhaseType().equals("DocumentumMetadataExport")){
			 
			return phaseDocumentumMetadataExport.getPhaseDetailsInteger();
		}
		else if(this.getSelectedPhaseType().equals("ExportFromDocumentum")){
			 
			return phaseDocumentumExport.getPhaseDetailsInteger();
		}
		else if(this.getSelectedPhaseType().equals("InitDocumentumBatch")){
			 
			return phaseInitDocumentumSeq.getPhaseDetailsInteger();
		}else if(this.getSelectedPhaseType().equals("ImportToSharePoint")){
			
			 return phaseSharePointImport.getPhaseDetailsInteger();
		}else if(this.getSelectedPhaseType().equals("Transformation")){
			return phaseTransformation.getPhaseDetailsInteger();
		}else if(this.getSelectedPhaseType().equals("ImportToDocumentumD7")){
			return phaseDocumentumImportD7.getPhaseDetailsInteger();
		}
		return null;
	}
	@Override
	public HashMap<String, Date> getPhaseParametersDate() {
		if(this.getSelectedPhaseType().equals("DocumentumContentExport")){
			return phaseDocumentumContentExport.getPhaseDetailsDate();
		}
		else if(this.getSelectedPhaseType().equals("DocumentumMetadataExport")){
			 
			return phaseDocumentumMetadataExport.getPhaseDetailsDate();
		}
		else if(this.getSelectedPhaseType().equals("ExportFromDocumentum")){
			 
			return phaseDocumentumExport.getPhaseDetailsDate();
		}else if(this.getSelectedPhaseType().equals("InitDocumentumBatch")){
			 
			return phaseInitDocumentumSeq.getPhaseDetailsDate();
		}else if(this.getSelectedPhaseType().equals("ImportToSharePoint")){
			
			 return phaseSharePointImport.getPhaseDetailsDate();
		}else if(this.getSelectedPhaseType().equals("Transformation")){
			return phaseTransformation.getPhaseDetailsDate();
		}
		
		return null;
		  
	}
	public String getSelectedPhaseType() {
		return selectedPhaseType;
	}
	public void setSelectedPhaseType(String selectedPhaseType) {
		this.selectedPhaseType = selectedPhaseType;
	}
	
	@UiHandler("addPhase")
	void onAddPhaseClick(ClickEvent event) {
		presenter.onAddButtonClicked();
	}
	@Override
	public void setDropDownConnection() {
		if(this.getSelectedPhaseType().equals("DocumentumContentExport")){
			 phaseDocumentumContentExport.setConnectionList(presenter.getConnectionList());
		}
		else if(this.getSelectedPhaseType().equals("DocumentumMetadataExport")){
			 phaseDocumentumMetadataExport.setConnectionList(presenter.getConnectionList());
		}
		else if(this.getSelectedPhaseType().equals("ExportFromDocumentum")){
			 phaseDocumentumExport.setConnectionList(presenter.getConnectionList());
		}else if(this.getSelectedPhaseType().equals("InitDocumentumBatch")){
			phaseInitDocumentumSeq.setConnectionList(presenter.getConnectionList());
		}else if(this.getSelectedPhaseType().equals("ImportToSharePoint")){
			  phaseSharePointImport.setConnectionList(presenter.getConnectionList());
		} else if(this.getSelectedPhaseType().equals("ImportToDocumentumD7")){
			  phaseDocumentumImportD7.setConnectionList(presenter.getConnectionList());
		} else if(this.getSelectedPhaseType().equals("ExportACL")){
			  phaseExportACL.setConnectionList(presenter.getConnectionList());
		} else if(this.getSelectedPhaseType().equals("ImportACLD7")){
			  phaseImportACLD7.setConnectionList(presenter.getConnectionList());
		} else if(this.getSelectedPhaseType().equals("ExportProcessD6")){
			  phaseExportProcessD6.setConnectionList(presenter.getConnectionList());
		} else if(this.getSelectedPhaseType().equals("ImportProcessD7")){
			  phaseImportProcessD7.setConnectionList(presenter.getConnectionList());
		} 
		
	}
	@Override
	public void setDropDownSourceObject() {
		if(this.getSelectedPhaseType().equals("DocumentumContentExport")){
			 //phaseDocumentumContentExport.setSourceObjectList(presenter.getSourceObjectList());
		}
		else if(this.getSelectedPhaseType().equals("DocumentumMetadataExport")){
			 //phaseDocumentumMetadataExport.setConnectionList(presenter.getConnectionList());
		}
		else if(this.getSelectedPhaseType().equals("ExportFromDocumentum")){
			phaseDocumentumExport.setSourceObjectList(presenter.getSourceObjectList());
		}else if(this.getSelectedPhaseType().equals("InitDocumentumBatch")){
			phaseInitDocumentumSeq.setSourceObjectList(presenter.getSourceObjectList());
		}
		
	}
	public void setDropDownTargetObject() {
		 if(this.getSelectedPhaseType().equals("ImportToSharePoint")){
			  phaseSharePointImport.setTargetObjectList(presenter.getTargetObjectList());
		}
		 if(this.getSelectedPhaseType().equals("ImportToDocumentumD7")){
			  phaseDocumentumImportD7.setTargetObjectList(presenter.getTargetObjectList());
		}
	}
	@Override
	public void setDropDownTransformation() {
		 if(this.getSelectedPhaseType().equals("Transformation")){
			  phaseTransformation.setTransformationList (presenter.getTransformationList());
		}
	}
	 
}
