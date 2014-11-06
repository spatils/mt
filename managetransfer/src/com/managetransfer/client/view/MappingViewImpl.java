package com.managetransfer.client.view;

import java.util.ArrayList;
import java.util.List;

 
 
import com.google.gwt.core.client.GWT;
 
import com.google.gwt.event.dom.client.ClickEvent;
 
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
 
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
 
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
 
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
 
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.MappingDetails;
 
import com.managetransfer.client.Resource;
 
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.cellview.client.PageSizePager;
 

public class MappingViewImpl extends Composite implements MappingView<MappingDetails>{
	@UiTemplate("MappingView.ui.xml")
	interface ConnectionsViewUiBinder extends UiBinder<Widget, MappingViewImpl> {
	} 
	private List<MappingDetails> rowData;
	private static ConnectionsViewUiBinder uiBinder =	GWT.create(ConnectionsViewUiBinder.class);
	@UiField(provided=true) CellTable<MappingDetails> connectionTable = new CellTable<MappingDetails>();
	@UiField SimplePager simplePager;
	 
	@UiField TextBox  searchString = new TextBox();
	@UiField Button searchButton;
 
	@UiField Button saveButton;
	@UiField Button searchAddButton;
	@UiField Button searchEditButton;
	@UiField Button searchDeleteButton;
	@UiField TabLayoutPanel tabLayoutPanelMain;
	@UiField ListBox srcObjectListAll  = new ListBox() ;
	@UiField ListBox srcObjectListSelected  = new ListBox() ;
	@UiField Image moveLeft;
	@UiField Image moveRight;
	@UiField ListBox trgObjectListSelected = new ListBox() ;
	@UiField ListBox trgObjectListAllList = new ListBox() ;
	@UiField Image moveRightTrgObject;
	@UiField Image moveLeftTrgObject;
	@UiField TextBox newMappingName;
	@UiField Button button;
    @UiField Button saveAttributeButton;
    @UiField Button deleteAttributeButton;
	@UiField ListBox srcAttributeListAll;
	@UiField ListBox srcAttributeListSelected;
	@UiField ListBox trgAttributeListAll;
	@UiField ListBox trgAttributeListSelected;
	@UiField(provided=true) CellTable<MappingDetails>  cellTableAttibuteList = new CellTable<MappingDetails>();
	@UiField Image moveRightSrcAttribute;
	@UiField Image moveLeftSrcAttribute;
	@UiField Image moveRightTrgAttribute;
	@UiField Image moveLeftTrgAttribute;
	@UiField SimplePager simplePagerAttribute;
	@UiField ListBox expNameList;
	 
	 
	 
	 
	 
	public MappingViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		 
	}
	private Presenter<MappingDetails> presenter;
	private MappingDetails selectedMappingDetails = new MappingDetails();
	private MappingDetails selectedMappingDetailsAttribute = new MappingDetails();
	@Override
	public void setPresenter(Presenter<MappingDetails> presenter) {
		// TODO Auto-generated method stub
		connectionTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		final SingleSelectionModel<MappingDetails> selectionModel = new SingleSelectionModel<MappingDetails>();
		connectionTable.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
		    	selectedMappingDetails = selectionModel.getSelectedObject();
		        if (selectedMappingDetails != null) {
		        	searchEditButton.setEnabled(true);
		    		searchDeleteButton.setEnabled(true);
		    		 
		        }else{
		        	searchEditButton.setEnabled(false);
		    		searchDeleteButton.setEnabled(false);
		    		 
		        }
		      }
		    });
		moveLeft.setUrl( Resource.INSTANCE.leftarrow().getSafeUri());
		moveRight.setUrl(Resource.INSTANCE.rightarrow().getSafeUri());
		moveRightTrgObject.setUrl(Resource.INSTANCE.rightarrow().getSafeUri());
		moveLeftTrgObject.setUrl( Resource.INSTANCE.leftarrow().getSafeUri());
		moveRightSrcAttribute.setUrl(Resource.INSTANCE.rightarrow().getSafeUri());
		moveLeftSrcAttribute.setUrl( Resource.INSTANCE.leftarrow().getSafeUri());
		moveRightTrgAttribute.setUrl(Resource.INSTANCE.rightarrow().getSafeUri());
		moveLeftTrgAttribute.setUrl( Resource.INSTANCE.leftarrow().getSafeUri());
		this.presenter = presenter;
	}
	
     
	public void selectedMappingDetailsAttribute(){
		 
		for(int i=0; i < presenter.getExpressionList().size();i++){
			if(presenter.getExpressionList().get(i).equals(selectedMappingDetailsAttribute.getNameOfExpression())){
				expNameList.setSelectedIndex(i);
			}
		}
		trgAttributeListSelected.clear();
		trgAttributeListSelected.addItem(selectedMappingDetailsAttribute.getTargetObject());
		srcAttributeListSelected.clear();
		for(int i=0;i<selectedMappingDetailsAttribute.getSourceObjectList().size();i++){
			srcAttributeListSelected.addItem(selectedMappingDetailsAttribute.getSourceObjectList().get(i));
		}
	}
	public void setSourceObjectlist() {
		 if(srcObjectListAll != null &&  srcObjectListAll.getItemCount() > 0){
			 srcObjectListAll.clear();
		 }
		for (  int i=0;i<presenter.getSourceObjectList().size();i++){
			srcObjectListAll.addItem(presenter.getSourceObjectList().get(i));
		}
		
	}
	public void setTargetObjectlist() {
		 if(trgObjectListAllList != null &&  trgObjectListAllList.getItemCount() > 0){
			 trgObjectListAllList.clear();
		 }
		for (  int i=0;i<presenter.getTargetObjectList().size();i++){
			trgObjectListAllList.addItem(presenter.getTargetObjectList().get(i));
		}
		
	}
	public void setSourceAttributelist() {
		 if(srcAttributeListAll != null &&  srcAttributeListAll.getItemCount() > 0){
			 srcAttributeListAll.clear();
		 }
		for (  int i=0;i<presenter.getSourceAttributeList().size();i++){
			srcAttributeListAll.addItem(presenter.getSourceAttributeList().get(i));
		}
		
	}
	public void setTargetAttributelist() {
		 if(trgAttributeListAll != null &&  trgAttributeListAll.getItemCount() > 0){
			 trgAttributeListAll.clear();
		 }
		for (  int i=0;i<presenter.getTargetAttributeList().size();i++){
			trgAttributeListAll.addItem(presenter.getTargetAttributeList().get(i));
		}
		
	}

	public void setDropDown() {
		 
	}
    @Override
	public void setRowData(List<MappingDetails> rowData) {
		// TODO Auto-generated method stub
		 
		if(connectionTable.getColumnCount() == 0 ){
			connectionTable.addColumn(mappingName,"Mapping Name");
			connectionTable.addColumn(mappingType,"Mapping Type");
			connectionTable.addColumn(expression,"Expression");
			connectionTable.addColumn(targetObject,"Target Object");
		}
		ListDataProvider<MappingDetails> dataProvider = new ListDataProvider<MappingDetails>();
		dataProvider.addDataDisplay(connectionTable); 
		List<MappingDetails> listSearchResult = dataProvider.getList();
		listSearchResult.clear(); 
		for(MappingDetails sr : rowData){
			if(sr.getMappingType().equals("Object")){
				listSearchResult.add(sr);
			}
		}
		simplePager.setDisplay(connectionTable); 
		simplePager.setPageSize(10);
		 
	}
    @Override
   	public void setRowDataAttribute(List<MappingDetails> rowData) {
   	 	if(cellTableAttibuteList.getColumnCount() == 0 ){
   			cellTableAttibuteList.addColumn(mappingName,"Mapping Name");
   			cellTableAttibuteList.addColumn(mappingType,"Mapping Type");
   			cellTableAttibuteList.addColumn(expression,"Expression Name");
   			cellTableAttibuteList.addColumn(targetObject,"Target Object");
   		}
   		ListDataProvider<MappingDetails> dataProvider = new ListDataProvider<MappingDetails>();
   		dataProvider.addDataDisplay(cellTableAttibuteList); 
   		List<MappingDetails> listSearchResult = dataProvider.getList();
   		listSearchResult.clear(); 
   		for(MappingDetails sr : rowData){
   			///Populate attribute table with list of mapping related to selected object mapping
   			if(sr.getMappingType().equals(selectedMappingDetails.getMappingName())){
   				listSearchResult.add(sr);
   			}
   		}
   		simplePagerAttribute.setDisplay(cellTableAttibuteList); 
   		simplePagerAttribute.setPageSize(10);
   	}
	 
	TextColumn<MappingDetails> mappingName = new TextColumn<MappingDetails>(){
		public String getValue(MappingDetails hr){
			return hr.getMappingName();
		}
	};
	TextColumn<MappingDetails> mappingType = new TextColumn<MappingDetails>(){
		public String getValue(MappingDetails hr){
			return hr.getMappingType();
		}
	};
	TextColumn<MappingDetails> expression = new TextColumn<MappingDetails>(){
		public String getValue(MappingDetails hr){
			return hr.getNameOfExpression();
		}
	};
	TextColumn<MappingDetails> targetObject = new TextColumn<MappingDetails>(){
		public String getValue(MappingDetails hr){
			return hr.getTargetObject();
		}
	}; 
	 
	 

	@Override
	public HasValue<String> getUserName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HasValue<String> getDataSource() {
		// TODO Auto-generated method stub
		return null;
	}
	 
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
		trgObjectListSelected.clear();
		srcObjectListSelected.clear();
		newMappingName.setValue("");
		tabLayoutPanelMain.selectTab(1);
			 
	}
	 
	@UiHandler("searchDeleteButton")
		void onDeleteButton(@SuppressWarnings("unused") ClickEvent event){
		if (presenter != null) {
			presenter.onDeleteButtonClicked();
		}
	}
	 
	

	@Override
	public HasValue<String> getSearchString() {
		// TODO Auto-generated method stub
		return searchString;
	}


	@Override
	public HasValue<String> getDeleteConnectionString() {
		// TODO Auto-generated method stub
		return null;
	}


	 

	@Override
	public HasValue<String> getEditUserName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public HasValue<String> getEditDataSource() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public HasValue<String> getMapping() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public HasValue<String> getMappingName() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getMappingType() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public HasValue<String> getEditMappingName() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getEditMappingType() {
		// TODO Auto-generated method stub
		return null;
	}
	@UiHandler("moveLeft")
	void onMoveLeftClick(ClickEvent event) {
		for(int k=0;k <srcObjectListSelected.getItemCount();k++){
			if(srcObjectListSelected.isItemSelected(k)){
				srcObjectListSelected.removeItem(k);
			}
		}
	}
	@UiHandler("moveRight")
	void onMoveRightClick(ClickEvent event) {
		for (  int i=0;i<presenter.getSourceObjectList().size();i++){
			if(srcObjectListAll.isItemSelected(i)){
				boolean alreadySelected= false;
				for(int k=0;k <srcObjectListSelected.getItemCount();k++){
					if (srcObjectListSelected.getItemText(k).equals(presenter.getSourceObjectList().get(i))){
						alreadySelected= true;
						break;
					}
					 
				}
				if(!alreadySelected){
					srcObjectListSelected.addItem(presenter.getSourceObjectList().get(i));
				}
			}
		}
	}
	
	 
	@UiHandler("moveRightTrgObject")
	void onMoveRightTrgObjectClick(ClickEvent event) {
		for (  int i=0;i<presenter.getTargetObjectList().size();i++){
			 
			if(trgObjectListAllList.isItemSelected(i)){
				 
				boolean alreadySelected= false;
				for(int k=0;k <trgObjectListSelected.getItemCount();k++){
					if (trgObjectListSelected.getItemText(k).equals(presenter.getTargetObjectList().get(i))){
						alreadySelected= true;
						break;
					}
					 
				}
				if(!alreadySelected){
					trgObjectListSelected.addItem(presenter.getTargetObjectList().get(i));
				}
			}
		}
	}
	@UiHandler("moveLeftTrgObject")
	void onMoveLeftTrgObjectClick(ClickEvent event) {
		for(int k=0;k <trgObjectListSelected.getItemCount();k++){
			if(trgObjectListSelected.isItemSelected(k)){
				trgObjectListSelected.removeItem(k);
			}
		}
	}

	@UiHandler("moveRightTrgAttribute")
	void onMoveRightTrgAttributeClick(ClickEvent event) {
		for (  int i=0;i<presenter.getTargetAttributeList().size();i++){
		 if(trgAttributeListAll.isItemSelected(i)){
				boolean alreadySelected= false;
				for(int k=0;k <trgAttributeListSelected.getItemCount();k++){
					if (trgAttributeListSelected.getItemText(k).equals(presenter.getTargetAttributeList().get(i))){
						alreadySelected= true;
						break;
					}
					 
				}
				if(!alreadySelected){
					trgAttributeListSelected.addItem(presenter.getTargetAttributeList().get(i));
				}
			}
		}
	}
	@UiHandler("moveLeftTrgAttribute")
	void onMoveLeftTrgAttributeClick(ClickEvent event) {
		for(int k=0;k <trgAttributeListSelected.getItemCount();k++){
			if(trgAttributeListSelected.isItemSelected(k)){
				trgAttributeListSelected.removeItem(k);
			}
		}
	}
	@UiHandler("moveRightSrcAttribute")
	void onMoveRightSrcAttributeClick(ClickEvent event) {
		for (  int i=0;i<presenter.getSourceAttributeList().size();i++){
		 if(srcAttributeListAll.isItemSelected(i)){
				boolean alreadySelected= false;
				for(int k=0;k <srcAttributeListSelected.getItemCount();k++){
					if (srcAttributeListSelected.getItemText(k).equals(presenter.getSourceAttributeList().get(i))){
						alreadySelected= true;
						break;
					}
					 
				}
				if(!alreadySelected){
					srcAttributeListSelected.addItem(presenter.getSourceAttributeList().get(i));
				}
			}
		}
	}
	
	@UiHandler("moveLeftSrcAttribute")
	void onMoveLeftSrcAttributeClick(ClickEvent event) {
		for(int k=0;k <srcAttributeListSelected.getItemCount();k++){
			if(srcAttributeListSelected.isItemSelected(k)){
				srcAttributeListSelected.removeItem(k);
			}
		}
	}
	@Override
	public String getNewMappingNameObject() {
		// TODO Auto-generated method stub
		return newMappingName.getValue();
	}



	@Override
	public ArrayList<String> getSourceMappingOjbect() {
		// TODO Auto-generated method stub
		ArrayList<String> sourceObjectList = new ArrayList<String>();
		for(int i=0; i<srcObjectListSelected.getItemCount();i++){
			sourceObjectList.add(srcObjectListSelected.getItemText(i));
		}
		return sourceObjectList;
	}



	@Override
	public String getNewTargetMappingObject() {
		// TODO Auto-generated method stub
		return trgObjectListSelected.getItemText(0);
	}
	@UiHandler("searchEditButton")
	void onSearchEditButtonClick(ClickEvent event) {
		trgObjectListSelected.clear();
		trgObjectListSelected.addItem(selectedMappingDetails.getTargetObject());
		srcObjectListSelected.clear();
		for(int i=0; i<selectedMappingDetails.getSourceObjectList().size();i++){
			srcObjectListSelected.addItem(selectedMappingDetails.getSourceObjectList().get(i));
		}
		newMappingName.setValue(selectedMappingDetails.getMappingName());
		tabLayoutPanelMain.selectTab(1);
	}



	@Override
	public String getSelectedMappingNameObject() {
		// TODO Auto-generated method stub
		return selectedMappingDetails.getMappingName();
	}
	@UiHandler("button")
	void onButtonClick(ClickEvent event) {
		final SingleSelectionModel<MappingDetails> selectionModelAttribute = new SingleSelectionModel<MappingDetails>();
		cellTableAttibuteList.setSelectionModel(selectionModelAttribute);
		selectionModelAttribute.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
		    	selectedMappingDetailsAttribute = selectionModelAttribute.getSelectedObject();
		    	selectedMappingDetailsAttribute();
		    	if (selectedMappingDetailsAttribute != null) {
		    		deleteAttributeButton.setEnabled(true);
		         }else{
		        	deleteAttributeButton.setEnabled(false);
		        }
		      }
		    });
		String[] srcString = new String[selectedMappingDetails.getSourceObjectList().size()];
		for(int i=0 ;i<selectedMappingDetails.getSourceObjectList().size();i++ ){
			srcString[i] = selectedMappingDetails.getSourceObjectList().get(i);
		}
		presenter.fetchSourceAttributeList(srcString);
		presenter.fetchTargetAttributeList(selectedMappingDetails.getTargetObject());
	    presenter.fetchMappingDetailsAttribute();
		tabLayoutPanelMain.selectTab(2);
	}

	@Override
	public String getAttributeExpressionName() {
		return  presenter.getExpressionList().get(expNameList.getSelectedIndex());
	}
	@Override
	public ArrayList<String> getAttributeSource() {
		ArrayList<String> selectedsrcAttributes = new ArrayList<String>();
		for(int i=0 ; i < srcAttributeListSelected.getItemCount();i++){
			selectedsrcAttributes.add(srcAttributeListSelected.getValue(i));
		}
		return selectedsrcAttributes;
	}
	@Override
 	public String getSelectedAttributeTarget() {
    	 String selectedtrgAttributes =  new String ();
		for(int i=0 ; i < trgAttributeListSelected.getItemCount();i++){
			selectedtrgAttributes =  trgAttributeListSelected.getValue(i);
		}
		return selectedtrgAttributes;
	}
	 
	@UiHandler("addAttributeButton")
	void onAddAttributeButtonClick(ClickEvent event) {
		trgAttributeListSelected.clear();
		srcAttributeListSelected.clear();
	}
	@UiHandler("saveAttributeButton")
	void onSaveAttributeButtonClick(ClickEvent event) {
		presenter.onSaveButtonClickedAttribute();
	}
	@UiHandler("deleteAttributeButton")
	void onDeleteAttributeButtonClick(ClickEvent event) {
		presenter.onDeleteAttributeButtonClicked();
	}


	@Override
	public String getSelectedMappingNameAttribute() {
		return selectedMappingDetailsAttribute.getMappingName();
	}


	@Override
	public void setExpressionList() {
		 if(expNameList != null &&  expNameList.getItemCount() > 0){
			 expNameList.clear();
		 }
		for (  int i=0;i<presenter.getExpressionList().size();i++){
			expNameList.addItem(presenter.getExpressionList().get(i));
		}
		
		
	}
}
