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
import com.google.gwt.user.client.ui.PasswordTextBox;
 
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.managetransfer.client.ConnectionDetails;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class ConnectionsViewImpl extends Composite implements ConnectionsView<ConnectionDetails>{
	@UiTemplate("ConnectionsView.ui.xml")
	interface ConnectionsViewUiBinder extends UiBinder<Widget, ConnectionsViewImpl> {
	} 
	private List<ConnectionDetails> rowData;
	private static ConnectionsViewUiBinder uiBinder =	GWT.create(ConnectionsViewUiBinder.class);
	@UiField(provided=true) CellTable<ConnectionDetails> connectionTable = new CellTable<ConnectionDetails>();
	@UiField SimplePager simplePager;
	@UiField TextBox  viewConnectionName = new TextBox();
	@UiField TextBox  viewConnectionType = new TextBox();
	@UiField TextBox  viewUserName = new TextBox();
	@UiField TextBox  viewRepository = new TextBox();
	@UiField TextBox  viewConnectionURL = new TextBox();
	@UiField TextBox  viewDomain = new TextBox();
	@UiField TextBox  viewPort = new TextBox();
	@UiField TextBox  editConnectionName = new TextBox();
	@UiField TextBox  editUserName = new TextBox();
	@UiField TextBox  editRepository = new TextBox();
	@UiField TextBox  editConnectionURL = new TextBox();
	@UiField TextBox  editDomain = new TextBox();
	@UiField TextBox  editPort = new TextBox();
	@UiField TextBox  newConnectionName = new TextBox();
	@UiField TextBox  newUserName = new TextBox();
	@UiField TextBox  newRepository = new TextBox();
	@UiField TextBox  newConnectionURL = new TextBox();
	@UiField TextBox  newDomain = new TextBox();
	@UiField TextBox  newPort = new TextBox();
	@UiField PasswordTextBox  newPsw = new PasswordTextBox();
	@UiField PasswordTextBox  editPsw = new PasswordTextBox();
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
	@UiField ListBox editConnectionTypeList = new ListBox() ;
	@UiField ListBox newConnectionTypeList = new ListBox() ;;
			 
	public ConnectionsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		 
	}
	private Presenter<ConnectionDetails> presenter;
	private ConnectionDetails selectedConnectionDetails = new ConnectionDetails();
	private List<String> connectionTypes;
	@Override
	public void setPresenter(Presenter<ConnectionDetails> presenter) {
		// TODO Auto-generated method stub
		connectionTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		final SingleSelectionModel<ConnectionDetails> selectionModel = new SingleSelectionModel<ConnectionDetails>();
		connectionTable.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
		    	selectedConnectionDetails = selectionModel.getSelectedObject();
		        if (selectedConnectionDetails != null) {
		        	viewConnectionName.setText(selectedConnectionDetails.getConnectionName());
		    		viewConnectionType.setText(selectedConnectionDetails.getConnectionType());
		    		viewUserName.setText(selectedConnectionDetails.getUserName());
		    		viewRepository.setText(selectedConnectionDetails.getDataSource());
		    		viewDomain.setText(selectedConnectionDetails.getDomain());
		    		viewPort.setText(selectedConnectionDetails.getPort());
		    		editConnectionName.setText(selectedConnectionDetails.getConnectionName());
		    		editUserName.setText(selectedConnectionDetails.getUserName());
		    		editRepository.setText(selectedConnectionDetails.getDataSource());
		    		editDomain.setText(selectedConnectionDetails.getDomain());
		    		editPort.setText(selectedConnectionDetails.getPort());
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
		 
		if(editConnectionTypeList!=null & editConnectionTypeList.getItemCount() !=0 ){
			editConnectionTypeList.clear();
		}
		if(newConnectionTypeList!=null &  newConnectionTypeList.getItemCount() !=0){
			newConnectionTypeList.clear();
		}
		if( presenter.getConnectionTypeList() !=null){
			for(int i=0; i < presenter.getConnectionTypeList().size(); i++){
				
				editConnectionTypeList.addItem(presenter.getConnectionTypeList().get(i));
				newConnectionTypeList.addItem(presenter.getConnectionTypeList().get(i));
				 
				 
			}
		}
		 
	}



	@Override
	public HasValue<String> getConnections() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setRowData(List<ConnectionDetails> rowData) {
		// TODO Auto-generated method stub
		 
		if(connectionTable.getColumnCount() == 0 ){
			connectionTable.addColumn(connectionName,"Connection Name");
			connectionTable.addColumn(connectionType,"Connection Type");
			connectionTable.addColumn(userName,"User Name");
			connectionTable.addColumn(repository,"Respository");
		}
		ListDataProvider<ConnectionDetails> dataProvider = new ListDataProvider<ConnectionDetails>();
		 
		
		dataProvider.addDataDisplay(connectionTable); 
		List<ConnectionDetails> listSearchResult = dataProvider.getList();
		
		 
		listSearchResult.clear(); 
		for(ConnectionDetails sr : rowData){
			listSearchResult.add(sr);
		}
		simplePager.setDisplay(connectionTable); 
		simplePager.setPageSize(10);
		 
	}
	 
	TextColumn<ConnectionDetails> connectionName = new TextColumn<ConnectionDetails>(){
		public String getValue(ConnectionDetails hr){
			return hr.getConnectionName();
		}
	};
	TextColumn<ConnectionDetails> connectionType = new TextColumn<ConnectionDetails>(){
		public String getValue(ConnectionDetails hr){
			return hr.getConnectionType();
		}
	};
	TextColumn<ConnectionDetails> userName = new TextColumn<ConnectionDetails>(){
		public String getValue(ConnectionDetails hr){
			return hr.getUserName();
		}
	};
	TextColumn<ConnectionDetails> repository = new TextColumn<ConnectionDetails>(){
		public String getValue(ConnectionDetails hr){
			return hr.getDataSource();
		}
	}; 
	 
	@Override
	public HasValue<String> getConnectionName() {
		// TODO Auto-generated method stub
		return newConnectionName;
	}


	@Override
	public String getConnectionType() {
		// TODO Auto-generated method stub
		return presenter.getConnectionTypeList().get(newConnectionTypeList.getSelectedIndex()) ;
	}


	@Override
	public HasValue<String> getUserName() {
		// TODO Auto-generated method stub
		return newUserName;
	}


	@Override
	public HasValue<String> getDataSource() {
		// TODO Auto-generated method stub
		return newRepository;
	}
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
		if ( editConnectionTypeList != null || editConnectionTypeList.getItemCount()!=0){
			editConnectionTypeList.setSelectedIndex(presenter.getConnectionTypeList().indexOf(selectedConnectionDetails.getConnectionType()));
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
		// TODO Auto-generated method stub
		return searchString;
	}


	@Override
	public HasValue<String> getDeleteConnectionString() {
		// TODO Auto-generated method stub
		return viewConnectionName;
	}


	@Override
	public HasValue<String> getEditConnectionName() {
		// TODO Auto-generated method stub
		return editConnectionName;
	}


	@Override
	public  String getEditConnectionType() {
		// TODO Auto-generated method stub
		return presenter.getConnectionTypeList().get(editConnectionTypeList.getSelectedIndex());
	}


	@Override
	public HasValue<String> getEditUserName() {
		// TODO Auto-generated method stub
		return editUserName;
	}


	@Override
	public HasValue<String> getEditDataSource() {
		// TODO Auto-generated method stub
		return editRepository;
	}



	@Override
	public HasValue<String> getEditDomain() {
		// TODO Auto-generated method stub
		return editDomain;
	}



	@Override
	public HasValue<String> getEditPort() {
		// TODO Auto-generated method stub
		return editPort;
	}



	@Override
	public HasValue<String> getEditURL() {
		// TODO Auto-generated method stub
		return editConnectionURL;
	}



	@Override
	public HasValue<String> getEditPsw() {
		// TODO Auto-generated method stub
		return editPsw;
	}



	@Override
	public HasValue<String> getNewDomain() {
		// TODO Auto-generated method stub
		return newDomain;
	}



	@Override
	public HasValue<String> getNewPort() {
		// TODO Auto-generated method stub
		return newPort;
	}



	@Override
	public HasValue<String> getNewURL() {
		// TODO Auto-generated method stub
		return newConnectionURL;
	}



	@Override
	public HasValue<String> getNewPsw() {
		// TODO Auto-generated method stub
		return newPsw;
	}
	 
	 
	 
}
