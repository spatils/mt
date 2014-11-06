package com.managetransfer.client.view;

import java.util.List;

 
 
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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.ExpressionsDetails;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.managetransfer.client.view.ExpressionsView;
public class ExpressionsViewImpl extends Composite implements ExpressionsView<ExpressionsDetails>{
	@UiTemplate("ExpressionsView.ui.xml")
	interface ExpressionsViewUiBinder extends UiBinder<Widget, ExpressionsViewImpl> {
	} 
	private List<ExpressionsDetails> rowData;
	private static ExpressionsViewUiBinder uiBinder =	GWT.create(ExpressionsViewUiBinder.class);
	@UiField(provided=true) CellTable<ExpressionsDetails> expressionTable = new CellTable<ExpressionsDetails>();
	@UiField SimplePager simplePager;
	@UiField TextBox  viewExpressionName = new TextBox();
	@UiField TextBox  viewExpressionType = new TextBox();
	@UiField TextBox  editExpressionName = new TextBox();
	@UiField TextBox  newExpressionName = new TextBox();
	@UiField TextArea  newExpression = new TextArea();
	@UiField TextArea  editExpression = new TextArea();
	@UiField TextBox  searchString = new TextBox();
	@UiField DisclosurePanel workAreaPanel = new DisclosurePanel();
	@UiField Button searchButton;
	@UiField Button deleteButton;
	@UiField Button saveButton;
	@UiField Button searchAddButton;
	@UiField Button searchEditButton;
	@UiField Button searchDeleteButton;
	@UiField Button searchTestButton;
	@UiField Button searchDetailsButton;
	@UiField TabLayoutPanel tabLayoutPanelMain;
	@UiField TabLayoutPanel tabLayoutPanelDetails;
	@UiField ListBox editExpressionTypeList = new ListBox() ;
	@UiField ListBox newExpressionTypeList = new ListBox() ;;
			 
	public ExpressionsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		 
	}
	private Presenter<ExpressionsDetails> presenter;
	private ExpressionsDetails selectedExpressionDetails = new ExpressionsDetails();
	private List<String> ExpressionTypes;
	@Override
	public void setPresenter(Presenter<ExpressionsDetails> presenter) {
		// TODO Auto-generated method stub
		expressionTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		final SingleSelectionModel<ExpressionsDetails> selectionModel = new SingleSelectionModel<ExpressionsDetails>();
		expressionTable.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
		    	selectedExpressionDetails = selectionModel.getSelectedObject();
		        if (selectedExpressionDetails != null) {
		        	viewExpressionName.setText(selectedExpressionDetails.getExpressionsName() );
		    		viewExpressionType.setText(selectedExpressionDetails.getExpressionsType());
		    		editExpressionName.setText(selectedExpressionDetails.getExpressionsName());
		    		editExpression.setText(selectedExpressionDetails.getExpression());
		    		newExpression.setText(selectedExpressionDetails.getExpression());
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
	
     
	
	public void setDropDown() {
		 
		if(editExpressionTypeList!=null & editExpressionTypeList.getItemCount() !=0 ){
			editExpressionTypeList.clear();
		}
		if(newExpressionTypeList!=null &  newExpressionTypeList.getItemCount() !=0){
			newExpressionTypeList.clear();
		}
		if( presenter.getExpressionTypeList() !=null){
			for(int i=0; i < presenter.getExpressionTypeList().size(); i++){
				
				editExpressionTypeList.addItem(presenter.getExpressionTypeList().get(i));
				newExpressionTypeList.addItem(presenter.getExpressionTypeList().get(i));
			}
		}
		 
	}



	 
	@Override
	public void setRowData(List<ExpressionsDetails> rowData) {
		// TODO Auto-generated method stub
		 
		if(expressionTable.getColumnCount() == 0 ){
			expressionTable.addColumn(expressionName,"Expression Name");
			expressionTable.addColumn(expressionType,"Expression Type");
			expressionTable.addColumn(expression,"Expression");
			 
		}
		ListDataProvider<ExpressionsDetails> dataProvider = new ListDataProvider<ExpressionsDetails>();
		 
		
		dataProvider.addDataDisplay(expressionTable); 
		List<ExpressionsDetails> listSearchResult = dataProvider.getList();
		
		 
		listSearchResult.clear(); 
		for(ExpressionsDetails sr : rowData){
			listSearchResult.add(sr);
		}
		simplePager.setDisplay(expressionTable); 
		simplePager.setPageSize(10);
		 
	}
	 
	TextColumn<ExpressionsDetails> expressionName = new TextColumn<ExpressionsDetails>(){
		public String getValue(ExpressionsDetails hr){
			return hr.getExpressionsName();
		}
	};
	TextColumn<ExpressionsDetails> expressionType = new TextColumn<ExpressionsDetails>(){
		public String getValue(ExpressionsDetails hr){
			return hr.getExpressionsType();
		}
	};
	 
	TextColumn<ExpressionsDetails> expression = new TextColumn<ExpressionsDetails>(){
		public String getValue(ExpressionsDetails hr){
			return hr.getExpression();
		}
	}; 
	 
	 
	@UiHandler("addButton")
	void onAddButtonClicked(@SuppressWarnings("unused") ClickEvent event) {
		if (presenter != null) {
			presenter.onAddButtonClicked();
			tabLayoutPanelMain.selectTab(0);
		}
	}
	@UiHandler("searchButton")
	void onSearchButtonClick(ClickEvent event) {
		if (presenter != null) {
			presenter.onSearchButtonClicked();
		}
	}
	@UiHandler("deleteButton")
	void onDeleteButtonClick(ClickEvent event) {
		if (presenter != null) {
			presenter.onDeleteButtonClicked();
			tabLayoutPanelMain.selectTab(0);
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
		tabLayoutPanelDetails.selectTab(2);
			 
	}
	@UiHandler("searchEditButton")
		void onEditButton(@SuppressWarnings("unused") ClickEvent event){
		if ( editExpressionTypeList != null || editExpressionTypeList.getItemCount()!=0){
			editExpressionTypeList.setSelectedIndex(presenter.getExpressionTypeList().indexOf(selectedExpressionDetails.getExpressionsType()));
		}
		tabLayoutPanelMain.selectTab(1);
		tabLayoutPanelDetails.selectTab(1);
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
	}
	 
	

	@Override
	public HasValue<String> getSearchString() {
		return searchString;
	}


	@Override
	public HasValue<String> getDeleteExpressionString() {
		return viewExpressionName;
	}


	@Override
	public HasValue<String> getEditExpressionName() {
		return editExpressionName;
	}


	@Override
	public  String getEditExpressionType() {
		return presenter.getExpressionTypeList().get(editExpressionTypeList.getSelectedIndex());
	}



	@Override
	public HasValue<String> getExpressions() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public HasValue<String> getExpressionName() {
		return newExpressionName;
	}



	@Override
	public String getExpressionType() {
		return presenter.getExpressionTypeList().get(newExpressionTypeList.getSelectedIndex()) ;
	}



	@Override
	public String getNewExpressionType() {
		// TODO Auto-generated method stub
		  return presenter.getExpressionTypeList().get(newExpressionTypeList.getSelectedIndex()) ;
		  
	}



	@Override
	public HasValue<String> getNewExpressionName() {
		// TODO Auto-generated method stub
		return newExpressionName;
	}



	@Override
	public HasValue<String> getEditExpression() {
		// TODO Auto-generated method stub
		return editExpression;
	}



	@Override
	public HasValue<String> getNewExpression() {
		// TODO Auto-generated method stub
		return newExpression;
	}



	 
		
	 
 


	 
	 
	 
}
